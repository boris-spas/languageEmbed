require "twitter"

def getTweets(searchTerm, tweetCount)
  puts "RUBY START"
  begin
    data = Hash.new
    File.readlines(File.join(File.expand_path(File.dirname(__FILE__)), "twitterToken.txt")).each do |line|
      var, val = line.chomp.split("=")
      data[var] = val
    end

    client = Twitter::REST::Client.new do |config|
      config.consumer_key = data["consumer_key"]
      config.consumer_secret = data["consumer_secret"]
      config.access_token = data["access_token"]
      config.access_token_secret = data["access_token_secret"]
    end

    twitterData = Array.new

    puts "RUBY TWITTER CALL"
    if searchTerm[0] == "@"
      tweets = client.user_timeline(searchTerm, count: tweetCount)
    else
      tweets = client.search(searchTerm, lang: "en").take(tweetCount)
    end
    puts "RUBY TWITTER END"

    tweets.each { |tweet| twitterData.push(tweet.full_text) }
    twitterData
  rescue => e
    raise StandardError.new(e.inspect.tr(">", "").tr("<", "") << "<br>" << e.backtrace.join("\n"))
  end
end

method(:getTweets)
