import org.graalvm.polyglot.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class JavaHost{

    private Context context;

    final Value rbTwitterGet;
    final Value rSentAna;
    private File srcFile;
    private Source source;

    public JavaHost() throws Exception{
        context = Context.create();
        
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

    public String tweetSentiment(String searchTerm, int tweetCount) throws Exception{
        try{
            Value tweets = rbTwitterGet.execute(searchTerm, tweetCount);
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
