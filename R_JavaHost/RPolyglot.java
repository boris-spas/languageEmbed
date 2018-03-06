import org.graalvm.polyglot.*;
import java.util.ArrayList;

import java.io.File;

public class RPolyglot {
    public static void main(String[] args) throws Exception {

        Context context = Context.create();

        ArrayList<String[]> trumpTweets = CSV.importCSV(RPolyglot.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "trump_tweets.csv");
        
        File file = new File(RPolyglot.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "RScript.R");

        Source source = Source.newBuilder(Source.findLanguage(file), file).build();
        
        Value rScript = context.eval(source);
        
        System.out.println(rScript.execute(trumpTweets));
    }
}