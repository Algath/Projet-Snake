import java.util.Scanner


object consol_snake extends App {
  class Tableau(height: Int = 8, width: Int = 8) {

    var grid: Array[Array[Int]] = Array.fill(height, width) {
      0
    }

    def createGrid(): Unit = {}

    var headSnake = 1
    var taille = 3

    var XInitialHead: Int = 3
    //(math.random()*width).toInt
    var yInitialHead: Int = 1

    grid(XInitialHead)(yInitialHead) = headSnake

    for (i <- 1 until taille) {
      grid(XInitialHead - i)(yInitialHead) = i + 1
    }
    //define apple
    var apple: Int = -1

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

  class Game(){

    var keyboard : Scanner = new Scanner(System.in)
    keyboard






  }

  var x : Tableau = new Tableau()
  x.printGrid()

}
