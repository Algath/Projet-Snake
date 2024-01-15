import java.io._

object HighScore extends App {
  class HighScore() {
    def generateHS(score: Array[Int]): String = {
      var file: String = "src/res/HighScore.txt"
      val pw = new PrintWriter(new FileOutputStream(file))
      var result: String = ""

      for (i: Int <- 1 until 11) {
        result += s"$i. ${score(i - 1)}\n"
      }
      pw.println(result)
      pw.close()
      result
    }

    def classement(HS: Array[Int]): Array[Int] = {
      HS.sorted.reverse
    }

    //TODO: lecture du fichier depuis le jeu
    def lectureHS(fileName: String): Array[String] = { //TODO debugage à faire
      var ranting: Array[String] = scala.io.Source.fromFile(fileName).getLines().toArray
      try {
          for (i <- ranting.indices) {
            println(ranting(i))
          }
        ranting
      } catch {
        case e: FileNotFoundException =>
          println("File not found !")
          e.printStackTrace()
          ranting
        case e: Exception =>
          println(s"Something didn't work during read ${e.getMessage}")
          ranting
      }
    }

    def askName(): String = {
      var nomUtilisateur: String = ""

      println(s"Entrez votre nom: ")
      nomUtilisateur = Input.readString()

      nomUtilisateur
    }
  }

  var hs: HighScore = new HighScore
  var file: String = "src/res/HighScore.txt"
  var memoryScore: Array[Int] = Array.ofDim(11)
  var userName: Array[String] = Array.ofDim(11)
  var serpent: Snake = new Snake()

  // TODO adapté l'intégration des scores
  for (c <- memoryScore.indices) {
    memoryScore(c) = serpent.nombresDeProiesMangees
  }

  var classé: Array[Int] = hs.classement(memoryScore)

  for (c <- userName.indices) {
    userName(c) = hs.askName()
  }
  println(s"Actual High Score: ${classé(0)}")
  hs.generateHS(classé)
  hs.lectureHS(file)
}