import java.io.{FileOutputStream, PrintWriter}

object HighScore extends App {
  class HighScore() {
    def generateHS(score: Array[Int]): String = {
      var rank: Int = 0
      var result: String = ""

      for (i: Int <- 1 until 11) {
        result += s"$i. ${score(i)}\n"
      }

      result
    }

    def classement(old: Int, news: Int, HS: Array[Int]): Unit = {
      var oldHighScore: Int = old
      var newHighScore: Int = news

      if (newHighScore > oldHighScore) HS(2) = newHighScore

    }
  }

  var hs: HighScore = new HighScore
  val pw = new PrintWriter(new FileOutputStream("src/res/HighScore.txt"))
  var memoryScore: Array[Int] = Array.ofDim(11)

  for (c <- 0 until memoryScore.length) {
    memoryScore(c) = 3 + c
  }

  pw.println(hs.generateHS(memoryScore))
  pw.close()
}

