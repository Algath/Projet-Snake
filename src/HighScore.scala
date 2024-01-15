import java.io._

object HighScore extends App {
  class HighScore() {
    def generateHS(score: Array[Int], utilisateur: Array[String]): String = {
      var result: String = ""

      for (i: Int <- 1 until 11) {
        result += s"$i. ${utilisateur(i-1)} ${score(i - 1)}\n"
      }

      result
    }

    def classement(HS: Array[Int]): Array[Int] = {
      HS.sorted.reverse
    }

    //TODO: lecture du fichier depuis le jeu
    def lectureHS(fileName: String): Unit = { //TODO debugage à faire
      try {
        val fr = new FileReader(fileName)
        val inputReader = new BufferedReader(fr)

        var line = inputReader.readLine()
        println(line)

        line = inputReader.readLine()
        println(line)

        inputReader.close()
      } catch {
        case e: FileNotFoundException =>
          println("File not found !")
          e.printStackTrace()
        case e: Exception =>
          println(s"Something didn't work during read ${e.getMessage}")
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
  val pw = new PrintWriter(new FileOutputStream(file))
  var memoryScore: Array[Int] = Array.ofDim(11)
  var userName: Array[String] = Array.ofDim(11)
  var serpent: Snake = new Snake()


  // TODO adapté l'intégration des scores
  for (c <- memoryScore.indices) {
    memoryScore(c) = serpent.nombresDeProiesMangees
  }

  var classé: Array[Int] = hs.classement(memoryScore)

  println(s"Actual High Score: ${classé(0)}")

  for (c <- userName.indices) {
    userName(c) = hs.askName()
  }
  pw.println(hs.generateHS(classé, userName))
  pw.close()
  hs.lectureHS(file)
}