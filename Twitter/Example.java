import org.graalvm.polyglot.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
public class Example {

    public static void main(String[] args) throws Exception{
        //tweetsFromRuby(null);

        rubyFunction();

    }

    public static void initialize(){

    }

    public static String tweetsFromRuby(Value arg) throws Exception {
        Context context = Context.create();
        
        // rb
        File rbfile = new File(Example.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "twitterGet.rb");
        Source rbSource = Source.newBuilder(Source.findLanguage(rbfile), rbfile).build();

        Value rbTwitter = context.eval(rbSource);

        List twitterData = rbTwitter.as(List.class);
        String[] tD = new String[twitterData.size()];

        for(Object t : twitterData){
            tD[twitterData.indexOf(t)] = t.toString();
        }

        // r
        File rfile = new File(Example.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "RScript.r");
        Source rSource = Source.newBuilder(Source.findLanguage(rfile), rfile).build();
        
        Value rSortScript = context.eval(rSource);


        ArrayList<String[]> rData = new ArrayList<String[]>();
        rData.add(tD);
        Value randomTweet = rSortScript.execute((Object)rbTwitter); 
        //List sortedTweets = rSortScript.execute(rData).as(List.class); 
        
        System.out.println(randomTweet.asString());

        return null;
    }

    public static void rubyFunction() throws Exception {
        
        Context context = Context.create();
        
        File file = new File(Example.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "rubyFunction.rb");

        Source rbSource = Source.newBuilder(Source.findLanguage(file), file).build();
        
        
        Value rbFunction = context.eval(rbSource);
        if(rbFunction.canExecute()){
            System.out.println("Source.findLanguage(file)");
        }else{
            System.out.println(rbFunction.execute());
        }
        
    }
}
