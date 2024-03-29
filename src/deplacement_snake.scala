import deplacement_snake.offsetB
import hevs.graphics.FunGraphics

import java.awt.event.{KeyAdapter, KeyEvent}

/**
 * This class demonstrate how to implement keyboard events
 * using the FunGraphics library.
 */
object deplacement_snake extends App {

  // Taille de la fenêtre
  val height : Int = 900
  val width : Int = 1080

  val fg: FunGraphics = new FunGraphics(width, height)

  // variables pour les déplacements
  var offsetD: Int = 0
  var offsetG: Int = 0
  var offsetH: Int = 0
  var offsetB: Int = 0

  // sensi
  var sensibilite: Int = 5

  var positionX : Int = 0
  var positionY : Int = 0

  var positionXinit: Int = 400
  var positionYinit: Int = 10

  var droiteAct: Int = 0
  var gaucheAct: Int = 0
  var hautAct: Int = 0
  var basAct: Int = 0

  // Do something when a key has been pressed
  fg.setKeyManager(new KeyAdapter() { // Will be called when a key has been pressed
    override def keyPressed(e: KeyEvent): Unit = {
      if (e.getKeyChar == 'a') {
        println("The key 'A' was pressed")
      }

      if (e.getKeyCode == KeyEvent.VK_RIGHT) {
        offsetD += 1
        droiteAct = 1
        gaucheAct = 0
        hautAct = 0
        basAct = 0
      }

      if (e.getKeyCode == KeyEvent.VK_LEFT) {
        offsetG -= 1
        droiteAct = 0
        gaucheAct = 1
        hautAct = 0
        basAct = 0
      }

      if (e.getKeyCode == KeyEvent.VK_UP) {
        offsetH -= 1
        droiteAct = 0
        gaucheAct = 0
        hautAct = 1
        basAct = 0
      }

      if (e.getKeyCode == KeyEvent.VK_DOWN) {
        offsetB += 1
        droiteAct = 0
        gaucheAct = 0
        hautAct = 0
        basAct = 1
      }
    }
    })


    while (true) {

      positionX = positionXinit + offsetD * sensibilite + offsetG * sensibilite
      positionY = positionYinit + offsetH * sensibilite + offsetB * sensibilite


      if (positionX < 0) {
        positionX = 0
        offsetD = 0
        offsetG = 0
        positionXinit = width
      }
      if (positionX > width) {
        positionX = 0
        offsetD = 0
        offsetG = 0
        positionXinit = 0
      }
      if (positionY < 0) {
        positionY = 0
        offsetH = 0
        offsetB = 0
        positionYinit = height
      }
      if (positionY > height) {
        positionY = 0
        offsetH = 0
        offsetB = 0
        positionYinit = 0
      }


      fg.clear()
      //draw our object
      fg.drawRect(positionX , positionY, 75, 75)
      //refresh the screen at 60 FPS
      fg.syncGameLogic(120)


      if(droiteAct == 1){
        offsetD +=1
      }
      if (gaucheAct == 1) {
        offsetG -= 1
      }
      if (hautAct == 1) {
        offsetH -= 1
      }
      if (basAct == 1) {
        offsetH += 1
      }

    }
}