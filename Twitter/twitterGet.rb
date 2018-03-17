require 'twitter'

data = Hash.new
File.readlines("/Users/admin/Desktop/SCG/graalvm-0.32/Contents/Home/examples/rnode/twitterToken.txt").each do |line|
  var,val = line.chomp.split("=")
  data[var] = val
end

client = Twitter::REST::Client.new do |config|
  config.consumer_key        = data['consumer_key']
  config.consumer_secret     = data['consumer_secret']
  config.access_token        = data['access_token']
  config.access_token_secret = data['access_token_secret']
end

tweets = client.user_timeline('scgbern', count: 20)
tweets.each { |tweet| puts tweet.full_text }