# R_JavaHost

Java Host Application reading a CSV file into a String ArrayList, passing the ArrayList to a R function that returns a random element of the ArrayList.

needs graalvm-0.32

## Setup
compile:
path/to/graalvm-0.32/Contents/Home/bin/javac path/to/languageEmbed/R_JavaHost/*.java
run:
path/to/graalvm-0.32/Contents/Home/bin/java -cp path/to/languageEmbed/R_JavaHost/ RPolyglot
