# Twitter 
Description will follow

## Setup:

### For Ubuntu:

Currently it is not running and work in progress. It will be updated.

1. Download GraalVM:<br/>
curl -L  https://github.com/oracle/graal/releases/download/vm-1.0.0-rc1/graalvm-ce-1.0.0-rc1-linux-amd64.tar.gz --output graalvm.tar.gz

2. Unzip:<br/>
tar -xvzf graalvm.tar.gz

3. Add GraalVM /bin folder to PATH:<br/>
export PATH=/path/to/graalvm/bin:$PATH

4. Get ruby + R:<br/>
gu -c install org.graalvm.ruby
gu -c install org.graalvm.R

5. Install Tools:<br/>
apt-get update<br/>
sudo apt-get install git gcc g++ libcurl4-openssl-dev libssl-dev libgomp1 bzip2 make clang llvm libc++-dev

6. Install R packages:<br/>
R --jvm<br/>
- install.packages("stringr")<br/>
- install.packages("lattice")

7. install ruby Twitter gem:
ruby -Sgem install twitter -v 5.17.0<br/>
Comment line 2 in: <br/> path/to/graalvm/jre/languages/ruby/lib/ruby/gems/2.3.0/gems/http_parser.rb-0.6.0/lib/http_parser.rb<br/>
\#require 'ruby_http_parser'<br/>
e.g. nano /path/to/graalvm/jre/languages/ruby/lib/ruby/gems/2.3.0/gems/http_parser.rb-0.6.0/lib/http_parser.rb

8. Clone project repo:<br/>
git clone https://github.com/boris-spas/languageEmbed.git

9. Install node modules:<br/>
npm install

10. compile Java<br/>
javac *.java

11. Get Twitter Token:<br/>
Save your twitter Token with the following structure in the languageEmbed/Twitter directory as .txt file:<br/>
consumer_key=<br/>
consumer_secret=<br/>
access_token=<br/>
access_token_secret=<br/>

If you need a token follow these instructions<br/> https://developer.twitter.com/en/docs/basics/authentication/guides/access-tokens:<br/>
Generating access tokens:<br/>
- Login to the apps.twitter.com interface using your Twitter credentials
- Create an app or open an existing app that you would like to create access tokens for
- Navigate to the 'Keys and Access Tokens' page
- Scroll down and click on the 'Create my access token' button
- Take note of your access token as you will use in to access certain API endpoints

12. open browser at http://localhost:3000/sentiment

### macOS
