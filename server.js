console.time('serverStart');

const TweetSentimentType = Java.type('TweetSentimentAnalyzer');
try {
    var tweetSentimentAnalyzer = new TweetSentimentType();
} catch (err) {
    console.error("Problem with TweetSentimentAnalyzer " + err);
    process.exit(1);
}

const app = require('express')();
const bodyParser = require('body-parser');
const urlencodedParser = bodyParser.urlencoded({extended: true});

var form = '';
form += "<form action='/sentiment'  method='get'>";
form += "Twitter Account or Hash Tag: <input type= 'text' name='name'></p>";
form += "Number of Tweets: <input type='text' name='number'></p>";
form += "<input type='submit' value='submit'>";
form += "</form>";


app.get('/sentiment', (req, res) => {
    if (req.query.length == 0) {
        res.send(form);
    } else {
        if (req.query.name && req.query.number && /^\d+$/.test(req.query.number)) {
            var searchTerm = req.query.name;
            var tweetCount = parseInt(req.query.number);
            var rPlot = '<div style="width:60%;">' + tweetSentimentAnalyzer.plotSentimentOfTweets(searchTerm, tweetCount) + '</div>';
            res.send(form + rPlot);
        } else {
            res.send(form + "false input");
        }
    }
});

app.post('/sentiment', urlencodedParser, (req, res) => {
    console.time('Sentiment Analysis');

    if (req.body.name && req.body.number && /^\d+$/.test(req.body.number)) {
        var searchTerm = req.body.name;
        var tweetCount = parseInt(req.body.number);
        var rPlot = '<div style="width:60%;">' + tweetSentimentAnalyzer.plotSentimentOfTweets(searchTerm, tweetCount) + '</div>';
        res.send(form + rPlot);
    } else {
        res.send(form + "false input");
    }

    console.timeEnd('Sentiment Analysis');
});

app.get('/sentimentapi', (req, res) => {
    var json = {
        "searchTerm": req.query.q,
        "tweetCount": parseInt(req.query.n)
    };
    res.send(tweetSentimentAnalyzer.plotSentimentOfTweets(json));

});

var port = 3000;
var server = app.listen(port, function() {
    console.log("Server listening on http://localhost:" + port);
    console.timeEnd('serverStart');
});
