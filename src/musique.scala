import javax.sound.sampled.{AudioSystem, Clip}

object musique extends App {
  class AudioPlayer(path: String) {
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
    var audioClip: Clip = null

    def play(): Unit = {
      // Open stream and play
      try {
        if (!audioClip.isOpen) audioClip.open()
        audioClip.stop()
        audioClip.setMicrosecondPosition(0)
        audioClip.start()
      } catch {
        case e: Exception =>
          e.printStackTrace()
      }
    }
  }

  var musique: AudioPlayer = new AudioPlayer("res/bruitage_couleuvre.wav")
}
