import java.io.File;
import java.io.IOException;
import java.lang.IllegalArgumentException;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

public final class TweetSentimentAnalyzer {

	private final Context context;
	final Value rbGetTweets;

	public TweetSentimentAnalyzer() throws Exception {
		context = Context.newBuilder("R", "ruby").allowAllAccess(true).build();

		rbGetTweets = executeGuestLanguageFile("twitterGet.rb");
		if (!rbGetTweets.canExecute()) {
			throw new RuntimeException("sentimentAnalayis.r did not return an executable.");
		}
	}

	private Value executeGuestLanguageFile(String path) throws IOException {
		File srcFile = new File(path);
		Source source = Source.newBuilder(Source.findLanguage(srcFile), srcFile).build();
		return context.eval(source);
	}

	public Value plotSentimentOfTweets(String searchTerm, int tweetCount) {
		try {
			Value tweets = rbGetTweets.execute(searchTerm, tweetCount);
			return tweets;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

    public static void main(String[] args) throws Exception {
        System.out.println((new TweetSentimentAnalyzer()).plotSentimentOfTweets("@scgbern", 10));
    }
}
