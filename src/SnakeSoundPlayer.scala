import javax.sound.sampled.{AudioSystem, Clip, FloatControl}

class SnakeSoundPlayer(path: String) {
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
      if (!audioClip.isOpen) audioClip.open()
      val gainControl: FloatControl = audioClip.getControl(FloatControl.Type.MASTER_GAIN).asInstanceOf[FloatControl];
      val volume = audioClip.getLevel

      audioClip.stop()
      audioClip.setFramePosition(0)
      gainControl.setValue(volume / vol)
      audioClip.start()
      audioClip.loop(-1)
    }catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

  def playSnakeSound(vol: Float): Unit = {
    try {
      if (!audioClip.isRunning) {
        val gainControl: FloatControl = audioClip.getControl(FloatControl.Type.MASTER_GAIN).asInstanceOf[FloatControl]
        val volume = audioClip.getLevel

        audioClip.stop()
        audioClip.setFramePosition(0)
        gainControl.setValue(volume / vol)
        audioClip.flush()
        audioClip.start()
      }
    }catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }
}