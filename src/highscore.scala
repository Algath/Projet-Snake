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

    def classement(HS: Array[Int]): Array[Int] = {
      HS.sorted.reverse
    }

    //TODO: lecture du fichier depuis le jeu, apparition du meilleur score dans la fenêtre de jeu, ajout username, google docs?
  }

  var hs: HighScore = new HighScore
  val pw = new PrintWriter(new FileOutputStream("src/res/HighScore.txt"))
  var memoryScore: Array[Int] = Array.ofDim(11)

  for (c <- 0 until memoryScore.length) {
    memoryScore(c) = (math.random()*10).toInt
    println(memoryScore(c))
  }

  var classé: Array[Int] = hs.classement(memoryScore)

  pw.println(hs.generateHS(classé))
  pw.close()
}