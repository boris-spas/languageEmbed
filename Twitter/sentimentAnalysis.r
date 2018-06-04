library(stringr)
library(lattice)

wordsInString <- function(tweet) {
  tweet = gsub('https://', '', tweet)
  tweet = gsub('http://', '', tweet)
  tweet = gsub('[^[:graph:]]', ' ', tweet)
  tweet = gsub('[[:punct:]]', '', tweet)
  tweet = gsub('[[:cntrl:]]', '', tweet)
  tweet = gsub('\\d+', '', tweet)
  tweet = str_replace_all(tweet, "[^[:graph:]]", " ")
  tweet = tolower(tweet)
  
  word.list = str_split(tweet, '\\s+')
  words = unlist(word.list)
  
  return(words)
}

sentimentAnalysis <- function(tweets) {
  scores <- c()
  
  neg = scan("negativeWords.txt", what = "character", quiet = TRUE)
  pos = scan("positiveWords.txt", what = "character", quiet = TRUE)
  
  for (i in 1:length(tweets)) {
    words <- wordsInString(tweets[i])
    
    pos.matches = match(words, pos)
    neg.matches = match(words, neg)
    pos.matches = !is.na(pos.matches)
    neg.matches = !is.na(neg.matches)
    
    score = sum(pos.matches) - sum(neg.matches)
    scores <- c(scores, score)
  }
  
  svg(width = 4, height = 3)
  print(histogram(scores))
  grDevices:::svg.off()
}
