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

app.get('/', () => {
     var resp = JavaHost.tweetSentiment("@scgbern", 100);
     res.send(resp);
});

var port = 3000;
var server = app.listen(port, function() {
    console.log("Server listening on http://localhost:" + port);
    console.timeEnd('serverStart');
});
