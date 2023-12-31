import java.util.Scanner


object consol_snake extends App {
  class Tableau(height: Int = 8, width: Int = 8) {

    var grid: Array[Array[Int]] = Array.fill(height, width) {
      0
    }

    def createGrid(): Unit = {}

    def postionSnake(XInitialHead: Int, YInitialHead: Int, taille: Int): Unit = {
      var headSnake = 1
      var apple: Int = -1

      //réinitialisation des positions
      for (i <- grid.indices) {
        for (j <- grid(i).indices) {
          if (grid(i)(j) > 0) {
            grid(i)(j) = 0
          }
        }
      }

      grid(XInitialHead)(YInitialHead) = headSnake

      for (i <- 1 until taille) {
        if (XInitialHead - i >= 0 && XInitialHead - i < height) {
          grid(XInitialHead - i)(YInitialHead) = i + 1
          //revoir mouvement du corps
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


    class Game() {
      var keyboard: Scanner = new Scanner(System.in)
      var snake: Tableau = new Tableau()
      snake.postionSnake(3, 1, 3)
      snake.printGrid()
      var XInitialHead = 3
      var YInitialHead = 1
      var taille = 3


      def startGame() {
        while (true) {
          val input = keyboard.nextLine()
          if (input.length == 1) {
            val direction = input.charAt(0)
            direction match {
              case 'w' | 'W' =>
                XInitialHead += 1 // Modifier la coordonnée Y vers le haut
                snake.postionSnake(XInitialHead, YInitialHead, taille)
              case 'a' | 'A' =>
                YInitialHead -= 1 // Modifier la coordonnée X vers la gauche
                snake.postionSnake(XInitialHead, YInitialHead, taille)
              case 's' | 'S' =>
                XInitialHead -= 1 // Modifier la coordonnée Y vers le bas
                snake.postionSnake(XInitialHead, YInitialHead, taille)
              case 'd' | 'D' =>
                YInitialHead += 1 // Modifier la coordonnée X vers la droite
                snake.postionSnake(XInitialHead, YInitialHead, taille)
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
}
