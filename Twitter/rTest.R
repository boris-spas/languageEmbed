library(lattice)

model <- lm(weight~height, data=women)

plotheightweitgh <- function(x, y, z) {
  svg(width=10, height=8)
  print(xyplot(weight ~ height, data = women,
    panel = function(x, y) {
      panel.xyplot(x, y, cex=2, pch=19)
      panel.abline(model)
  }));
  grDevices:::svg.off()
}