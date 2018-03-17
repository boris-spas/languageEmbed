/**
 * Copyright (C) 2012 - 2018 Oracle and/or its affiliates. All rights reserved.
 */
import org.graalvm.polyglot.*;
import java.util.ArrayList;
import java.io.File;
public class Example {

    public static void main(String[] args) throws Exception{
        Context context = Context.create();
        
        File file = new File(Example.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "twitterGet.rb");

        Source rbSource = Source.newBuilder(Source.findLanguage(file), file).build();
        
        System.out.println(Source.findLanguage(file));
        Value rbTwitter = context.eval(rbSource);
        System.out.println(rbTwitter.execute);

    }

  //  public static Value greet(Value name) throws Exception {
  //      return name.execute();
  //  }
}
