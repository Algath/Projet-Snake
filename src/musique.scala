import javax.sound.sampled.{AudioSystem, Clip}

object musique extends App {
  class AudioPlayer(path: String) {
    var audioClip: Clip = null
    try {
      // Create audio input URL
      val url = this.getClass.getClassLoader.getResource(path)
      val audioStream = AudioSystem.getAudioInputStream(url)
      // Obtain clip
      audioClip = AudioSystem.getClip.asInstanceOf[Clip]
      audioClip.open(audioStream)
    } catch {
      case e: Exception =>
        println(s"File type not supported: ${e.getMessage}")
    }

    def play(): Unit = {
      // Open stream and play
      try {
        if (!audioClip.isOpen) audioClip.open()
        audioClip.stop()
        audioClip.setFramePosition(0)
        audioClip.start()
        audioClip.loop(-1)
        Thread.sleep(2000)
      } catch {
        case e: Exception =>
          e.printStackTrace()
          println("hello")
      }
    }
  }

  var musique: AudioPlayer = new AudioPlayer("res/bruitage_couleuvre.wav")
  musique.play()
}
