console.time('serverStart');

const JavaHostType = Java.type('JavaHost');
try{
    var JavaHost = new JavaHostType();
} catch (err) {
    console.error("Problem with JavaHost");
    process.exit(1);
}

const express = require('express');
const app = express();

const bodyParser = require('body-parser');
const urlencodedParser = bodyParser.urlencoded({ extended: true });

var form ='';
    form += "<form action='/sentiment'  method='post' name='form1'>";
    form += "Twitter Account Name:<input type= 'text' name='name'></p>";
    form += "Number of Tweets:<input type='text' name='number'></p>";
    form += "<input type='submit' value='submit'>";
    form += "</form>";


app.get('/sentiment', (req, res) => {
    res.send(form);
  });

app.post('/sentiment', urlencodedParser ,(req, res) => {
    console.time('rAnalysis');

    if(req.body.name && req.body.number && /^\d+$/.test(req.body.number)){
        var searchTerm = req.body.name;
        var tweetCount = parseInt(req.body.number);
        var rPlot = JavaHost.tweetSentiment(searchTerm, tweetCount);
        res.send(form + rPlot);
    }
    else {
        res.send(form + "false input");
    }

    console.timeEnd('rAnalysis');
});

app.get('/sentimentjson', () => {

    var json = {"searchTerm":"@scgbern", "tweetCount":100};
    var rPlot = JavaHost.tweetSentiment(json);
    res.send(rPlot);

});


var port = 3000;
var server = app.listen(port, function() {
    console.log("Server listening on http://localhost:" + port);
    console.timeEnd('serverStart');
});