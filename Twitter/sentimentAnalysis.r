library(stringr)
library(plyr)
library(lattice)
library(svglite)

sentimentAnalysis <- function(rbTweets){

tweets <- c()
scores <- c()

for(i in 1:length(rbTweets)){
    x <- toString(rbTweets[i])
    tweets <- c(tweets, x)
}

# todo: add relative path
neg = scan("negativeWords.txt", what="character", comment.char=";")
pos = scan("positiveWords.txt", what="character", comment.char=";")


for(i in 1:length(tweets)){

    tweets[i] = gsub('https://','',tweets[i])
    tweets[i] = gsub('http://','',tweets[i])
    tweets[i] = gsub('[^[:graph:]]', ' ',tweets[i])
    tweets[i] = gsub('[[:punct:]]', '', tweets[i])
    tweets[i] = gsub('[[:cntrl:]]', '', tweets[i])
    tweets[i] = gsub('\\d+', '', tweets[i])
    tweets[i] = str_replace_all(tweets[i],"[^[:graph:]]", " ") 

    tweets[i] = tolower(tweets[i])

    word.list = str_split(tweets[i], '\\s+')
 
    words = unlist(word.list)

    pos.matches = match(words, pos)
    neg.matches = match(words, neg)
 
    pos.matches = !is.na(pos.matches)
    neg.matches = !is.na(neg.matches)
 
    score = sum(pos.matches) - sum(neg.matches)

    scores <- c(scores, score)
}

svg(width=4, height=3)
print(histogram(scores));
grDevices:::svg.off()
#xmlSVG(histogram(scores), standalone = FALSE, height = 7, width = 7)
}
