import javax.sound.sampled.{AudioSystem, Clip, FloatControl}

object musique extends App {
  class AudioPlayer(path: String, time: Int) {
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

    def play(vol: Float): Unit = {
      // Open stream and play
      try {
        new Thread {
          override def run(): Unit = {
            if (!audioClip.isOpen) audioClip.open()
            val gainControl: FloatControl = audioClip.getControl(FloatControl.Type.MASTER_GAIN).asInstanceOf[FloatControl];
            val volume = audioClip.getLevel

            audioClip.stop()
            audioClip.setFramePosition(0)
            print(volume)
            gainControl.setValue(volume/vol)
            audioClip.start()
            audioClip.loop(-1)
            Thread.sleep(time)
          }
        }.start()
      } catch {
        case e: Exception =>
          e.printStackTrace()
          println("hello")
      }
    }

    def playSnakeSound(vol: Float): Unit = {
      if (!audioClip.isRunning) {
        val gainControl: FloatControl = audioClip.getControl(FloatControl.Type.MASTER_GAIN).asInstanceOf[FloatControl]
        val volume = audioClip.getLevel

        audioClip.stop()
        audioClip.setFramePosition(0)
        print(volume)
        gainControl.setValue(volume/vol)
        audioClip.flush()
        audioClip.start()
        Thread.sleep(time)
      }
    }
  }

  var musique: AudioPlayer = new AudioPlayer("res/Density & Time - MAZE  NO COPYRIGHT 8-bit Music.wav", 3000)
  var snake_sound: AudioPlayer = new AudioPlayer("res/bruitage_couleuvre.wav", 2000)

  musique.play(0.06f)
  snake_sound.playSnakeSound(0.2f)
}
