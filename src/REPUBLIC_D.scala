
import hevs.graphics.FunGraphics
import hevs.graphics.utils.GraphicsBitmap

import java.awt.Color
import java.awt.event.{KeyAdapter, KeyEvent}
import javax.sound.sampled.{AudioSystem, Clip}


object test2 extends App {

  class Snake(var maxLignes: Int = 20, var maxColonnes: Int = 30) {

    //------------------------------------------------------------------------------------
    // Création du tableau de jeux :
    var grille: Array[Array[Int]] = Array.fill(maxLignes, maxColonnes) {
      0
    }

    // définition des objets sur la grille (chiffres pour les reconnaîtres
    var jeu: String = "Marche"


    val teteSerpent: Int = 1
    val proie: Int = -1
    val obstacleMortel = -2
    val reducteurDeSerpent = -3

    val longueurInitSerpent: Int = 3
    var tailleSerpent: Int = longueurInitSerpent


    val nombresdeProiesInit: Int = 3

    var nombresDeProiesMangees: Int = 0
    var nombresDeReducteurs: Int = 0

    // Musique
    var background_music: AudioPlayer = new AudioPlayer("res/Density & Time - MAZE  NO COPYRIGHT 8-bit Music.wav", 30000)
    var snake_sound: AudioPlayer = new AudioPlayer("res/bruitage_couleuvre.wav", 2000)

    // position d'apparition de la tête du Serpent (Aléatoire)

    var positionLignesInit: Int = (math.random() * (maxLignes - 1)).toInt
    var positionColonnesInit: Int = (math.random() * (maxColonnes - 1)).toInt
    var orientationInitTete: Char = 'E'


    grille(positionLignesInit)(positionColonnesInit) = teteSerpent

    //pour générer la queue du serpent initiale sur la grille
    def creerSerpent(): Unit = {
      for (i: Int <- 1 until longueurInitSerpent) {
        var positionQueueInitSerpentN: Int = positionColonnesInit + i

        grille(positionLignesInit)(horsGrille(positionColonnesInit + i, maxColonnes)) = (i + 1)
      }
    }

    creerSerpent()

    // générer les proies de manière aléatoire

    def creerProies(nbProies: Int) {
      var countProieInit: Int = 1
      while (countProieInit <= nbProies) {

        var yJ: Int = (math.random() * (maxLignes - 1)).toInt
        var xJ: Int = (math.random() * (maxColonnes - 1)).toInt

        if (grille(yJ)(xJ) < 1 && grille(yJ)(xJ) != obstacleMortel && grille(yJ)(xJ) != reducteurDeSerpent) {
          grille(yJ)(xJ) = proie
          countProieInit += 1
        }
      }
    }

    creerProies(nombresdeProiesInit)
    creerReducteur(1)


    // création d'obstacles mortels
    def creerObstacles(nombresdObstaclesMortels: Int) {
      var countObstacleInit: Int = 1
      while (countObstacleInit <= nombresdObstaclesMortels) {

        var yJ: Int = (math.random() * (maxLignes - 1)).toInt
        var xJ: Int = (math.random() * (maxColonnes - 1)).toInt

        if (grille(yJ)(xJ) < 1 && grille(yJ)(xJ) != proie && grille(yJ)(xJ) != reducteurDeSerpent) {
          grille(yJ)(xJ) = obstacleMortel
          countObstacleInit += 1
        }
      }
    }

    // création de réducteurs (permet de réduire la taille du Serpent)
    def creerReducteur(nombresReducteurs: Int) {
      var countReducteurInit: Int = 1
      while (countReducteurInit <= nombresReducteurs) {

        var yR: Int = (math.random() * (maxLignes - 1)).toInt
        var xR: Int = (math.random() * (maxColonnes - 1)).toInt

        if (grille(yR)(xR) < 1 && grille(yR)(xR) != proie && grille(yR)(xR) != obstacleMortel) {
          grille(yR)(xR) = reducteurDeSerpent
          countReducteurInit += 1
        }
      }
      nombresDeReducteurs = 1
    }

    def suppression(Quoi: Int, ActiveDest: Boolean): Unit = {
      if (ActiveDest == true) {
        var valCase1: Int = 0
        for (i <- grille.indices) {
          for (j <- grille(i).indices) {
            valCase1 = grille(i)(j)
            if (valCase1 == Quoi) {
              grille(i)(j) = 0
              nombresDeReducteurs = 0
            }
          }
        }
      }
    }


    //---------------------------------------------------------------------------------
    // Bouger le Serpent
    def bouger(dir: Char): Unit = {


      var posY: Int = chercheValeurDansLeTableau(teteSerpent)(0)
      var posX: Int = chercheValeurDansLeTableau(teteSerpent)(1)

      suivreTete()

      dir match {
        case 'D' => {
          mangerParTete(horsGrille(posY, maxLignes), horsGrille(posX + 1, maxColonnes))
          orientationInitTete = 'O'
        }
        case 'G' => {
          mangerParTete(horsGrille(posY, maxLignes), horsGrille(posX - 1, maxColonnes))
          orientationInitTete = 'E'
        }
        case 'H' => {
          mangerParTete(horsGrille(posY - 1, maxLignes), horsGrille(posX, maxColonnes))
          orientationInitTete = 'N'
        }
        case 'B' => {
          mangerParTete(horsGrille(posY + 1, maxLignes), horsGrille(posX, maxColonnes))
          orientationInitTete = 'S'
        }
      }


    }


    // fonction qui agit en fonction de ce que trouve la tete
    def mangerParTete(posLigne: Int, posColonne: Int): Unit = {
      var quoiMange: Int = grille(posLigne)(posColonne)

      if (quoiMange == proie) {
        tailleSerpent += 1
        grille(posLigne)(posColonne) = teteSerpent
        //println("Mangé")
        nombresDeProiesMangees += 1
        creerProies(1)
      }
      else if (quoiMange > 1 || quoiMange == obstacleMortel) {
        jeu = "Fini"
        println(jeu)
      }
      else if (quoiMange == reducteurDeSerpent) {

        grille(posLigne)(posColonne) = teteSerpent

        tailleSerpent = (tailleSerpent / 2)

        if (tailleSerpent <= longueurInitSerpent) {
          tailleSerpent = longueurInitSerpent
        }

        var valCase: Int = 0

        for (i <- grille.indices) {
          for (j <- grille(i).indices) {
            valCase = grille(i)(j)
            if (valCase > 0 && valCase >= tailleSerpent) {
              grille(i)(j) = 0
            }
          }
        }
        nombresDeReducteurs = 0


      }
      else {
        //suivreTete()
        grille(posLigne)(posColonne) = teteSerpent
      }


    }

    // faire suivre la queue du Serpent
    def suivreTete(): Unit = {
      var valCase: Int = 0
      for (i <- grille.indices) {
        for (j <- grille(i).indices) {
          valCase = grille(i)(j)
          if (valCase > 0 && valCase < tailleSerpent) {
            grille(i)(j) = valCase + 1
          }
          else if (valCase == tailleSerpent) {
            grille(i)(j) = 0
          }
        }
      }
    }


    // gestion des zones hors grille
    def horsGrille(pos: Int, maxVal: Int): Int = {
      // Fonction : Corrige les positions hors grille

      var posMaxCorrige: Int = maxVal - 1

      if (pos < 0) {
        return posMaxCorrige - (-pos - 1)
      }

      if (pos > posMaxCorrige) {
        return (0 + ((pos - posMaxCorrige) - 1))
      }
      else {
        return pos
      }
    }

    // Affichage de la grille, pour visu (pour controler, avant d'avoir le rendu par fub graphic)
    def affichageGrille(): Unit = {
      var text: String = ""
      for (i <- grille.indices) {
        for (j <- grille(i).indices) {
          if (StringUtils.length(grille(i)(j).toString) == 1) {
            text += s" ${grille(i)(j)} "
          }
          else {
            text += s"${grille(i)(j)} "
          }
        }
        text += "\n"
      }
      println(text)
    }

    //Menus

    // Lancement de la musique
    background_music.play()

    // Affichage du Jeu
    def affichageDuJeu(): Unit = {

      var jeuInit: Int = 1

      var compilation: String = "Marche"

      val pixelsSize: Int = 20


      val height: Int = maxLignes
      val width: Int = maxColonnes

      var toucheSauv: Char = 'G'

      var nbObstaclesMortels: Int = 0

      var compteur: Int = 0

      var compteursauv: Int = 0

      var start: Boolean = true

      var startReponse: String = "Marche"

      val grilleJeu: FunGraphics = new FunGraphics(width * pixelsSize + 7 * pixelsSize, height * pixelsSize)

      // Pour dessiner
      def dessinerCorpsSerpent(i: Int, j: Int): Unit = {
        val b = new GraphicsBitmap("/res/Corps.jpg")

        grilleJeu.drawTransformedPicture(j * pixelsSize + pixelsSize / 2, i * pixelsSize + pixelsSize / 2, 0, pixelsSize / 399, b)
      }

      def desssinerProie(i: Int, j: Int): Unit = {
        val c = new GraphicsBitmap("/res/pommes.jpg")

        grilleJeu.drawTransformedPicture(j * pixelsSize + pixelsSize / 2, i * pixelsSize + pixelsSize / 2, 0, pixelsSize / 462, c)
      }

      def dessinerObstacleMortel(i: Int, j: Int) {
        val d = new GraphicsBitmap("/res/bombes.jpeg")

        grilleJeu.drawTransformedPicture(j * pixelsSize + pixelsSize / 2, i * pixelsSize + pixelsSize / 2, 0, pixelsSize / 155, d)
      }


      def dessinerReducteurDeSerpent(i: Int, j: Int): Unit = {
        val e = new GraphicsBitmap("/res/cadeau.jpg")

        grilleJeu.drawTransformedPicture(j * pixelsSize + pixelsSize / 2, i * pixelsSize + pixelsSize / 2, 0, pixelsSize / 720, e)
      }

      def dessinerTeteSerpent(i: Int, j: Int, dirTeteBloque: Boolean = false): Unit = {

        val a = new GraphicsBitmap("/res/snake1.jpg")

        var toucheSauvTete = toucheSauv

        // blocage de la tête pour le menu Start
        if (dirTeteBloque == true) {
          toucheSauvTete = 'G'
        }

        var angle: Double = 0
        toucheSauvTete match {

          case 'G' => angle = 0
          case 'B' => angle = math.Pi / 2 * 3
          case 'D' => angle = math.Pi
          case 'H' => angle = math.Pi / 2

        }

        grilleJeu.drawTransformedPicture(j * pixelsSize + pixelsSize / 2, i * pixelsSize + pixelsSize / 2, angle, pixelsSize / 59, a)
      }

      def affichageScore(couleur: String = "BLACK"): Unit = {
        var couleurAppli: Color = Color.BLACK
        if (couleur != "BLACK") {
          couleurAppli = Color.WHITE
        }
        grilleJeu.drawString(width * pixelsSize + 5, 20, s"Score : ${nombresDeProiesMangees.toString}", couleurAppli, 12)
        grilleJeu.drawString(width * pixelsSize + 5, 35, s"Taille Serpent : ${tailleSerpent.toString}", couleurAppli, 12)
        grilleJeu.drawString(width * pixelsSize + 5, 50, s"Temps : ${compteur.toString}", couleurAppli, 12)
        grilleJeu.drawString(width * pixelsSize + 5, 65, s"Obstacles Mortels : ${nbObstaclesMortels.toString}", couleurAppli, 12)
        grilleJeu.drawString(width * pixelsSize + 5, 80, s"Réducteur : ${nombresDeReducteurs.toString}", couleurAppli, 12)
        grilleJeu.drawString(width * pixelsSize + 5, 110, s"Press 's' . Stop/Start :", couleurAppli, 12)
        grilleJeu.drawString(width * pixelsSize + 5, 125, s"${startReponse}", couleurAppli, 12)
        grilleJeu.drawString(width * pixelsSize + 5, 140, s"Press 'e' . Exit ", couleurAppli, 12)
      }

      def serpentDecoratif(compteur0: Int): Unit = {

        var compteur: Int = compteur0

        toucheSauv = 'G'
        var positionXRandom: Int = 14


        dessinerTeteSerpent(positionXRandom, compteur, false)
        var corps1: Int = compteur + 1
        var corps2: Int = corps1 + 1
        var corps3: Int = corps2 + 1
        var corps4: Int = corps3 + 1
        var corps5: Int = corps4 + 1
        var corps6: Int = corps5 + 1
        var corps7: Int = corps6 + 1
        var corps8: Int = corps7 + 1
        var corps9: Int = corps8 + 1
        var corps10: Int = corps9 + 1

        dessinerCorpsSerpent(positionXRandom, (corps1))
        dessinerCorpsSerpent(positionXRandom, (corps2))
        dessinerCorpsSerpent(positionXRandom, (corps3))
        dessinerCorpsSerpent(positionXRandom, (corps4))
        dessinerCorpsSerpent(positionXRandom, (corps5))
        dessinerCorpsSerpent(positionXRandom, (corps6))
        dessinerCorpsSerpent(positionXRandom, (corps7))
        dessinerCorpsSerpent(positionXRandom, (corps8))
        dessinerCorpsSerpent(positionXRandom, (corps9))
        dessinerCorpsSerpent(positionXRandom, (corps10))

      }

      grilleJeu.setKeyManager(new KeyAdapter() { // Will be called when a key has been pressed

        override def keyPressed(e: KeyEvent): Unit = {


          if (e.getKeyChar == 'w') {
            println("Wahoooooo tu es trop fort !")
          }
          if (e.getKeyCode == KeyEvent.VK_RIGHT) {
            if (orientationInitTete != 'E') {
              toucheSauv = 'D'
              snake_sound.play()
            }
          }
          if (e.getKeyCode == KeyEvent.VK_LEFT) {
            if (orientationInitTete != 'O') {
              toucheSauv = 'G'
              snake_sound.play()
            }
          }
          if (e.getKeyCode == KeyEvent.VK_UP) {
            if (orientationInitTete != 'S') {
              toucheSauv = 'H'
              snake_sound.play()
            }
          }
          if (e.getKeyCode == KeyEvent.VK_DOWN) {
            if (orientationInitTete != 'N') {
              toucheSauv = 'B'
              snake_sound.play()
            }
          }
          if (e.getKeyChar == 's') {
            if (jeu == "Marche") {
              if (start == true) {
                start = false
                startReponse = "En pause"
              }
              else {
                start = true
                startReponse = "En marche !"
              }
            }
          }

          if (e.getKeyChar == 'e') {
            if (jeu == "Fini") {
              compilation = "Fin"
              println(compilation)
            }
            else {
              jeu = "Fini"
              start = true
            }
          }
          if (e.getKeyChar == 'p') {
            if (jeu != "Marche") {
              jeu = "Marche"

              // refaire la grille pour recommencer
              for (b: Int <- reducteurDeSerpent until proie) {
                suppression(b, true)
              }
              for (c: Int <- longueurInitSerpent + 1 to tailleSerpent) {
                suppression(c, true)

              }
              tailleSerpent = longueurInitSerpent
              // initialiser les valeurs
              compteur = 0
              nombresDeProiesMangees = 0


            }
          }

        }
      })

      //--------------------------------------------------------------------------------------------------------
      // Pour Dessiner le jeu
      def dessiner(): Unit = {
        // Pour eviter le scintillement
        grilleJeu.frontBuffer.synchronized {

          grilleJeu.clear()
          //draw our object

          // DELIMITATION DE LA ZONE DE JEUX. PARTI A DROITE POUR AFFICHER SCORE, NIVEAU ET ...
          grilleJeu.drawRect(0, 0, width * pixelsSize, height * pixelsSize)

          affichageScore()

          // Pour dessiner le jeu
          for (i <- grille.indices) {
            for (j <- grille(i).indices) {

              var valeur: Int = grille(i)(j)

              if (valeur == teteSerpent) {


                dessinerTeteSerpent(i, j)
              }
              else if (valeur >= 2) {

                dessinerCorpsSerpent(i, j)
              }
              else if (valeur == proie) {

                desssinerProie(i, j)
              }
              else if (valeur == obstacleMortel) {

                dessinerObstacleMortel(i, j)
              }
              else if (valeur == reducteurDeSerpent) {

                dessinerReducteurDeSerpent(i, j)
              }


            }
          }

        }
      }

      var compteurMenu: Int = maxColonnes + 10

      while (compilation == "Marche" || jeuInit == 1) {
        jeuInit = 0
        while (jeu == "Marche") {

          if (start == true) {

            // Afficher le jeu
            dessiner()
            //affichageGrille()


            //faire avancer automatiquement le serpent

            bouger(toucheSauv)

            compteur += 1

            // Faire disparaitre le réducteur au bout de x temps
            if (compteur % 30 == 0) {
              suppression(reducteurDeSerpent, true)
            }

            // Regle pour faire apparaitre un obstacle mortel
            if (compteur >= 100 && compteur % 40 == 0) {
              creerObstacles(1)
              nbObstaclesMortels += 1
              compteursauv = compteur
            }

            //Regle pour faire apparaitre un reducteur de serpent
            if (compteur >= 200 && (math.random() * 30).toInt == 24 && nombresDeReducteurs < 1 && compteursauv != compteur) {
              creerReducteur(1)

            }
            // mémorisation du score
            var memoryScore: Array[Int] = Array.ofDim(10)
            //memoryScore() = nombresDeProiesMangees

            //refresh the screen at XXX FPS
            grilleJeu.syncGameLogic(4)
          }
          //Pour que le clavier puisse fonctionner
          else {
            Thread.sleep(10)


            var sauvegardeDirectionDeLAtete = toucheSauv

            // Menu Pause

            while (start == false && jeu == "Marche") {


              // pour eviter le scintillement
              grilleJeu.frontBuffer.synchronized {

                // Ajout du Menu Pause
                grilleJeu.drawFillRect(0, 0, width * pixelsSize + 7 * pixelsSize, height * pixelsSize / 2)
                grilleJeu.drawString(pixelsSize + 5, 80, s"${startReponse}", Color.white, 64)

                affichageScore("WHITE")
                // Dessiner un serpent pour la décoration dans le Menu Pause

              }

            }


            toucheSauv = sauvegardeDirectionDeLAtete
          }
        }
        grilleJeu.clear()

        // Apparition du Menu
        grilleJeu.frontBuffer.synchronized {

          grilleJeu.drawRect(0, 0, width * pixelsSize + 7 * pixelsSize, height * pixelsSize)
          grilleJeu.drawString(pixelsSize + 5, 80, s"Menu", Color.black, 64)


          var decallageYSerpentDeco: Int = 0

          if (compteurMenu == -10) {
            compteurMenu = maxColonnes + 10

          }
          grilleJeu.drawString(compteurMenu * 10, 120, s"Press 'p' : Nouvelle partie", Color.RED, 32)
          serpentDecoratif(compteurMenu)
          compteurMenu -= 1
        }
        grilleJeu.syncGameLogic(4)
      }
      grilleJeu.clear()

      grilleJeu.drawString(pixelsSize + 5, 80, s"Fermer la fenêtre", Color.black, 64)

    }

    // fonction pour chercher une valeur dans le tableau
    def chercheValeurDansLeTableau(valCherch: Int): Array[Int] = {

      var resultpos: Array[Int] = new Array(2)

      for (i <- grille.indices) {
        for (j <- grille(i).indices) {
          if (grille(i)(j) == valCherch) {
            resultpos(0) = i
            resultpos(1) = j
          }
        }
      }
      return resultpos
    }
  }

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

    def play(): Unit = {
      // Open stream and play
      try {
        if (!audioClip.isOpen) audioClip.open()
        audioClip.stop()
        audioClip.setFramePosition(0)
        audioClip.start()
        audioClip.loop(-1)
        Thread.sleep(time)
      } catch {
        case e: Exception =>
          e.printStackTrace()
          println("hello")
      }
    }
  }

  var x: Snake = new Snake(30, 20)

  x.affichageDuJeu()

  /*
    x.affichageGrille()
    x.bouger('B')
    println("B")
    x.affichageGrille()
    x.bouger('B')
    println("B")
    x.affichageGrille()
    x.bouger('G')
    println("G")
    x.affichageGrille()
    x.bouger('G')
    println("G")
    x.affichageGrille()
    x.bouger('B')
    println("B")
    x.affichageGrille()
    x.bouger('B')
    println("B")
    x.affichageGrille()
    x.bouger('D')
    println("D")
    x.affichageGrille()
    x.bouger('D')
    println("D")
    x.affichageGrille()
    x.bouger('H')
    println("H")
    x.affichageGrille()
    x.bouger('H')
    println("H")
    x.affichageGrille()
    x.bouger('H')
    println("H")
    x.affichageGrille()
  */


}