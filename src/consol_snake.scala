import java.util.Scanner

object consol_snake extends App {
  class Tableau(width: Int = 8, height: Int = 8) {

    var grid: Array[Array[Int]] = Array.fill(width, height) (0)

    //def createGrid(): Unit = {}

    def postionSnake(XInitialHead: Int, YInitialHead: Int, taille: Int, deplacementx : Int = 1, deplacementy : Int): Unit = {
      var headSnake = 1
      var apple: Int = -1

      //réinitialisation des positions
      for (i <- grid.indices) {
        for (j <- grid(i).indices) {
          if (grid(i)(j) > 0) {
            grid(i)(j) -= 1
          }
        }
      }

      if (XInitialHead >= 0 && XInitialHead < width && YInitialHead >= 0 && YInitialHead < height) {
        grid(XInitialHead)(YInitialHead) = headSnake
      }


      //gestion du mouvement du corps
      for (i <- 2 to  taille) {
        if (XInitialHead + i - deplacementx >= 0 && XInitialHead + i - deplacementx < width && YInitialHead >= 0 && YInitialHead < height) {
          grid(XInitialHead + i - deplacementx)(YInitialHead) = i // Corps du serpent
        }
      }
    }

    def printGrid(): Unit = {
      var text: String = ""
      for (i <- grid.indices) {
        for (j <- grid(i).indices) {
          text += s"${grid(i)(j)}"
        }
        text += "\n"
      }
      println(text)
    }
  }
  class Game() {
    var keyboard: Scanner = new Scanner(System.in)
    var snake: Tableau = new Tableau()
    snake.postionSnake(3, 1, 3,1,0)
    snake.printGrid()
    var XInitialHead = 3
    var YInitialHead = 1
    var taille = 3
    var deplacementx = 1
    var deplacementy = 1


    def startGame() {
      while (true) {
        val input = keyboard.nextLine()
        if (input.length == 1) {
          val direction = input.charAt(0)
          direction match {
            case 'w' | 'W' =>
              XInitialHead -= 1 // Modifier la coordonnée X vers le haut
              snake.postionSnake(XInitialHead, YInitialHead, taille,deplacementx, deplacementy)
            case 'a' | 'A' =>
              YInitialHead -= 1 // Modifier la coordonnée Y vers la gauche
              snake.postionSnake(XInitialHead, YInitialHead, taille,deplacementx, deplacementy)
            case 's' | 'S' =>
              XInitialHead += 1 // Modifier la coordonnée X vers le bas
              snake.postionSnake(XInitialHead, YInitialHead, taille, deplacementx, deplacementy)
            case 'd' | 'D' =>
              YInitialHead += 1 // Modifier la coordonnée Y vers la droite
              snake.postionSnake(XInitialHead, YInitialHead, taille,deplacementx, deplacementy)
            case _ => println("Commande invalide")
          }
          println(s"Position du personnage : (${XInitialHead}, ${YInitialHead})")
          snake.printGrid()
        } else {
          println("Commande invalide")
        }
      }
    }
  }
  var play: Game = new Game()
  play.startGame()
}
