import hevs.graphics.FunGraphics
import java.awt.event.{KeyAdapter, KeyEvent}

/**
 * This class demonstrate how to implement keyboard events
 * using the FunGraphics library.
 */
object deplacement_snake extends App {

  val fg: FunGraphics = new FunGraphics(640, 480)
  var offsetx = 0
  var offsety = 0
  var counter = 0


  // Do something when a key has been pressed
  fg.setKeyManager(new KeyAdapter() { // Will be called when a key has been pressed
    override def keyPressed(e: KeyEvent): Unit = {
      if (e.getKeyChar == 'a') println("The key 'A' was pressed")
      if (e.getKeyCode == KeyEvent.VK_RIGHT) offsetx += 1
      if (e.getKeyCode == KeyEvent.VK_LEFT) offsetx -= 1
      if (e.getKeyCode == KeyEvent.VK_UP) offsety -= 1
      if (e.getKeyCode == KeyEvent.VK_DOWN) offsety += 1
    }
  })

  while (true) {
    fg.clear()
    //draw our object
    fg.drawRect(50 + offsetx * 80+ counter, 50 + offsety * 80, 75, 75)
    //refresh the screen at 60 FPS
    fg.syncGameLogic(60)

    counter += 4
  }

}