import org.graalvm.polyglot.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
public class Host{

    /**
     * Todo:
     * if executable;
     */

    static Context context;

    static Value rubyTwitterGet;
    static Value rReturnRandomElement;

    static Value rTest;

    public static void main(String[] args) throws Exception{
        try {
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //tweetsFromRuby(null);
        //rubyFunction();
        if(rubyTwitterGet.canExecute()){
            try {
                System.out.println(rubyTwitterGet.execute("scgbern"));
            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }

    public static void initialize() throws Exception{
        context = Context.create();
        
        File srcFile;
        Source source;
        
        srcFile = new File("twitterGet.rb");
        source = Source.newBuilder(Source.findLanguage(srcFile), srcFile).build();
        rubyTwitterGet = context.eval(source);

        srcFile = new File("RScript.r");
        source = Source.newBuilder(Source.findLanguage(srcFile), srcFile).build();
        rReturnRandomElement = context.eval(source);

        srcFile = new File("rTest.R");
        source = Source.newBuilder(Source.findLanguage(srcFile), srcFile).build();
        rTest = context.eval(source);

    }

    public static Value tweetsFromRuby(Value arg) throws Exception {

        return rReturnRandomElement.execute(rubyTwitterGet.execute(("scgbern")));
    }

    public static Value rTest() throws Exception{
        return rTest.execute();
    }

    public static void rubyFunction() throws Exception {
        
        Context context = Context.create();
        
        File file = new File("rubyFunction.rb");

        Source rbSource = Source.newBuilder(Source.findLanguage(file), file).build();
        
        
        Value rbFunction = context.eval(rbSource);
        if(rbFunction.canExecute()){
            System.out.println("Source.findLanguage(file)");
        }else{
            System.out.println(rbFunction.execute());
        }
        
    }
}
