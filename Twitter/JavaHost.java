import org.graalvm.polyglot.*;
import java.io.File;

public class JavaHost{

    private Context context;

    final Value rbTwitterGet;
    final Value rSentAna;
    private File srcFile;
    private Source source;

    public static void main(String[] args) throws Exception{
      
        Context context = Context.newBuilder("R", "ruby").allowAllAccess(true).build();
        
        File srcFile = new File("twitterGet.rb");
        Source source = Source.newBuilder(Source.findLanguage(srcFile), srcFile).build();
        Value rbTwitterGet = context.eval(source);
        if(!rbTwitterGet.canExecute()){
            System.out.println("problems executing rsentAna");
        }

        System.out.println(rbTwitterGet.execute("@realDonaldTrump", 500).getArrayElement(1).asString());
        srcFile = new File("sentimentAnalysis.r");
        System.out.println(Source.findLanguage(srcFile));
        Source rsource = Source.newBuilder(Source.findLanguage(srcFile), srcFile).build();
        Value rSentAna = context.eval(rsource);
        if(!rSentAna.canExecute()){
            System.out.println("problems executing rsentAna");
        }

    }
    public JavaHost() throws Exception{
        context = Context.newBuilder("R", "ruby").allowAllAccess(true).build();
        
        srcFile = new File("twitterGet.rb");
        source = Source.newBuilder(Source.findLanguage(srcFile), srcFile).build();
        rbTwitterGet = context.eval(source);
        if(!rbTwitterGet.canExecute()){
            throw new EvalException("twitterGet.rb");
        }

        srcFile = new File("sentimentAnalysis.r");
        source = Source.newBuilder(Source.findLanguage(srcFile), srcFile).build();
        rSentAna = context.eval(source);
        if(!rSentAna.canExecute()){
            throw new EvalException("sentimentAnalayis.r");
        }
    }

    public String tweetSentiment(String searchTerm, int tweetCount){
        try{
            Value tweets = rbTwitterGet.execute(searchTerm, tweetCount);
            String tweetMessages = (tweets.hasArrayElements()) ? buildTweetString(tweets) : "";
            return  tweetMessages + rSentAna.execute(tweets).asString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    public String tweetSentiment(Value json){
        try{
            Value tweets = rbTwitterGet.execute(json.getMember("searchTerm").asString(), json.getMember("tweetCount").asInt());
            String tweetMessages = (tweets.hasArrayElements()) ? buildTweetString(tweets) : "";
            return  tweetMessages + rSentAna.execute(tweets).asString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String buildTweetString(Value tweets){
        String tweetMessages = "";
        for(int i = 0; i < tweets.getArraySize() && i < 10 ; i++){
            tweetMessages += tweets.getArrayElement(i).asString();
            tweetMessages += "<br>";
        }
        return tweetMessages;
    }

    private class EvalException extends Exception{
        public EvalException(String message){
            super(message);
        }
    }
}
