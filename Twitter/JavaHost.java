import org.graalvm.polyglot.*;
import java.io.File;

public class JavaHost{

    private Context context;

    final Value rbTwitterGet;
    private File srcFile;
    private Source source;

    public static void main(String[] args) throws Exception{
        JavaHost host = new JavaHost();
        System.out.println(host.tweetSentiment("@scgbern", 100));
    }
    public JavaHost() throws Exception{
        context = Context.newBuilder("R", "ruby").allowAllAccess(true).build();

        srcFile = new File("twitterGet.rb");
        source = Source.newBuilder(Source.findLanguage(srcFile), srcFile).build();
        rbTwitterGet = context.eval(source);
        if(!rbTwitterGet.canExecute()){
            throw new EvalException("twitterGet.rb");
        }
    }

    public String tweetSentiment(String searchTerm, int tweetCount){
        try{
            Value tweets = rbTwitterGet.execute(searchTerm, tweetCount);
            String tweetMessages = (tweets.hasArrayElements()) ? buildTweetString(tweets) : "";
            return  tweetMessages;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String tweetSentiment(Value json){
        try{
            Value tweets = rbTwitterGet.execute(json.getMember("searchTerm").asString(), json.getMember("tweetCount").asInt());
            String tweetMessages = (tweets.hasArrayElements()) ? buildTweetString(tweets) : "";
            return  tweetMessages;
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
