console.time('serverStart');

const TweetSentimentType = Java.type('TweetSentimentAnalysis');
try {
 var TweetSentimentAnalysis = new TweetSentimentType();
} catch (err) {
 console.error("Problem with TweetSentimentAnalysis " + err);
 process.exit(1);
}

const express = require('express');
const app = express();

const bodyParser = require('body-parser');
const urlencodedParser = bodyParser.urlencoded({
 extended: true
});

var form = '';
form += "<form action='/sentiment'  method='post' name='form1'>";
form += "Twitter Account or Hash Tag: <input type= 'text' name='name'></p>";
form += "Number of Tweets: <input type='text' name='number'></p>";
form += "<input type='submit' value='submit'>";
form += "</form>";


app.get('/sentiment', (req, res) => {
 res.send(form);
});

app.post('/sentiment', urlencodedParser, (req, res) => {
 console.time('Sentiment Analysis');

 if (req.body.name && req.body.number && /^\d+$/.test(req.body.number)) {
  var searchTerm = req.body.name;
  var tweetCount = parseInt(req.body.number);
  var rPlot = '<div style="width:60%;">' + TweetSentimentAnalysis.tweetSentiment(searchTerm, tweetCount) + '</div>';
  res.send(form + rPlot);
 } else {
  res.send(form + "false input");
 }

 console.timeEnd('Sentiment Analysis');
});

app.get('/sentimentjson', (req, res) => {

 var json = {
  "searchTerm": "@scgbern",
  "tweetCount": 100
 };
 var rPlot = TweetSentimentAnalysis.tweetSentiment(json);
 res.send(rPlot);

});


var port = 3000;
var server = app.listen(port, function() {
 console.log("Server listening on http://localhost:" + port);
 console.timeEnd('serverStart');
});
