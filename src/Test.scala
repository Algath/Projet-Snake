import hevs.graphics.FunGraphics
import java.awt.Color
import java.awt.event.{KeyAdapter, KeyEvent}

/**
 * A sample game loop using explicit synchronization
 * to prohibit display flicker
 */
object Blink extends App {

  val fg = new FunGraphics(500, 500)
  var pressedUp = false; var pressedDown = false
  var size = 1; var i = 1
  var direction = 1

  // Do something when a key has been pressed
  fg.setKeyManager(new KeyAdapter() { // Will be called when a key has been pressed
    override def keyPressed(e: KeyEvent): Unit = {
      if (e.getKeyCode == KeyEvent.VK_LEFT) pressedUp = true
      if (e.getKeyCode == KeyEvent.VK_RIGHT) pressedDown = true
    }

    override def keyReleased(e: KeyEvent): Unit = {
      if (e.getKeyCode == KeyEvent.VK_LEFT) pressedUp = false
      if (e.getKeyCode == KeyEvent.VK_RIGHT) pressedDown = false
    }
  })

  while (true) {
    // Logic
    if (pressedUp) size += 1
    if (pressedDown) size = if (size == 0) 0
    else size - 1

    i += direction
    if (i > 100 || i <= 0) direction *= -1

    // Drawing
    fg.frontBuffer.synchronized{
      fg.clear(Color.white)
      fg.setColor(Color.red)
      fg.drawFilledOval(10, 10, 100 + size, 100 + size)
      fg.drawString(50, 250, "FunGraphics " + FunGraphics.version)
      fg.setColor(Color.yellow)
      fg.drawFillRect(50 + i, 50 - i, 100 + i, 100 + i)
    }

    // FPS sync
    fg.syncGameLogic(60)
  }
}