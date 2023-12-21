object consol_snake extends App {
 class Tableau(height: Int  = 8, width: Int = 8) {

   var grid : Array[Array[Int]] = Array.fill(height,width){0}

   def createGrid() : Unit = {}

   var headSnake = 1
   var taille = 3

   var XInitialHead: Int = 3
     //(math.random()*width).toInt
//   var yInitialHead: Int = (math.random()*height).toInt

   grid(XInitialHead)(1) = headSnake

   for(i <- taille){
     grid(3-i)(1) =
   }
 }
}
