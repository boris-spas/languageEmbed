console.time('serverStart');

const app = require('express')();
const bodyParser = require('body-parser');
const urlencodedParser = bodyParser.urlencoded({extended: true});

app.get('/sentimentapi', (req, res) => {
    var json = {
        "searchTerm": req.query.q,
        "tweetCount": parseInt(req.query.n)
    };
    console.log(json);
    const TweetSentimentType = Java.type('TweetSentimentAnalyzer');
    var tweetSentimentAnalyzer = new TweetSentimentType();
    res.send(tweetSentimentAnalyzer.plotSentimentOfTweets(req.query.q, parseInt(req.query.n)));
});

var port = 3000;
var server = app.listen(port, function() {
    console.log("Server listening on http://localhost:" + port);
    console.timeEnd('serverStart');
});
