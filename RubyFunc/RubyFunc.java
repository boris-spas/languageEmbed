import org.graalvm.polyglot.*;
import java.io.File;

public class RubyFunc {

    public static void main(String[] args) throws Exception {
        Context context = Context.create();
        File file = new File("rubyFunction.rb");
        Source rbSource = Source.newBuilder(Source.findLanguage(file), file).build();
        Value rbFunction = context.eval(rbSource);
        if(rbFunction.canExecute()){
            System.out.println("can execute " + rbFunction);

        } else {
            System.out.println("cannot execute" + rbFunction);
        }
        rbFunction.execute();
    }
}

