fs = require('fs');
// #### TO RUN ####
//    /Users/admin/Desktop/SCG/graalvm-0.32/Contents/Home/bin/node --jvm --polyglot server.js
// Load the R module
var modelScript = fs.readFileSync(__dirname + "/model.r", "utf8");
Interop.eval("application/x-r", modelScript);

// Import the function exported from the R module
plotKMeans = Interop.import('plotkmeans');
plotCars = Interop.import('plotcars');
plotWeight = Interop.import('plotheightweight');
predictWeight = Interop.import('predictweight');

// Java
let Example = Java.type('Example');

// Expressjs application:
const express = require('express');
const app = express();


// passing R function as argument to Java which returns the result of R execution...:)
app.get('/java/:arg' ,(req, res) => {
    let s = req.params.arg;
    res.send(Example.tweetsFromRuby(s));
});

app.get('/java/' ,(req, res) => {
    res.send(Example.tweetsFromRuby('arg'));
});

app.get('/lm/predict', function (req, res) {
    console.log('predicting weight');
    res.send('' + predictWeight(parseInt(req.query.height, 10)));
});


//app.use(express.static(__dirname + "/public"));
var port = 12837;
var server = app.listen(port, function() {
    console.log("Server listening on http://localhost:" + port);
});

app.get('/exit', function(req, res) {
    res.status(200).send('');
    server.close();
});
