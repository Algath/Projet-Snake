
import hevs.graphics.FunGraphics
import hevs.graphics.utils.GraphicsBitmap

import java.awt.Color
import java.awt.event.{KeyAdapter, KeyEvent}

class Snake(var maxLignes: Int = 20, var maxColonnes: Int = 30) {

  //------------------------------------------------------------------------------------
  // Création du tableau de jeux :
  var grille: Array[Array[Int]] = Array.fill(maxLignes, maxColonnes) {
    0
  }

  // définition des objets sur la grille (valeurs numériques)
  var jeu: String = "Marche"

  val teteSerpent: Int = 1
  val proie: Int = -1
  val obstacleMortel = -2
  val reducteurDeSerpent = -3

  val longueurInitSerpent: Int = 3
  var tailleSerpent: Int = longueurInitSerpent

  // Définition des variables pour la gestion des objets
  val nombresdeProiesInit: Int = 3

  var nombresDeProiesMangees: Int = 0
  var nombresDeReducteurs: Int = 0

  // Musique
  var background_music: SnakeSoundPlayer = new SnakeSoundPlayer("res/Density & Time - MAZE  NO COPYRIGHT 8-bit Music.wav", 30000)
  var snake_sound: SnakeSoundPlayer = new SnakeSoundPlayer("res/bruitage_couleuvre.wav", 2000)

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
  creerProies(nombresdeProiesInit)
  creerReducteur(1)

  //Menus TODO
  // Lancement de la musique
  background_music.play(0.06f)

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

  // Déplacement du Serpent
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

  // Comportement de la tête vis a vis de son positionnement dans l'espace
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
      var valCase: Int = 0

      grille(posLigne)(posColonne) = teteSerpent
      tailleSerpent = (tailleSerpent / 2)
      if (tailleSerpent <= longueurInitSerpent) {
        tailleSerpent = longueurInitSerpent
      }
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
  /* TODO: Effacer cette fonction
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
  }*/

  // Affichage du Jeu
  def affichageDuJeu(): Unit = {
    // Variable de lancement du jeu
    var jeuInit: Int = 1
    var compilation: String = "Marche"
    val pixelsSize: Int = 20

    // Définition des dimensions du plateau de jeu
    val height: Int = maxLignes
    val width: Int = maxColonnes
    val grilleJeu: FunGraphics = new FunGraphics(width * pixelsSize + 7 * pixelsSize, height * pixelsSize)

    // TODO
    var toucheSauv: Char = 'G'

    // Variables pour le menu du côté droite
    var nbObstaclesMortels: Int = 0
    var compteur: Int = 0
    var compteursauv: Int = 0

    // Variable disant si le jeu est en cours ou non
    var start: Boolean = true
    var startReponse: String = "Marche"

    // Apparence des éléments s'affichant dans le jeu
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
      var angle: Double = 0

      // blocage de la tête pour le menu Start
      if (dirTeteBloque == true) {
        toucheSauvTete = 'G'
      }

      // Gestion du positionnement visuel de la tête
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

    // TODO
    def serpentDecoratif(compteur0: Int): Unit = {
      var compteur: Int = compteur0
      var positionXRandom: Int = 14
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

      toucheSauv = 'G'

      dessinerTeteSerpent(positionXRandom, compteur, false)
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
        snake_sound.playSnakeSound(0.2f)

        if (e.getKeyCode == KeyEvent.VK_RIGHT) {
          if (orientationInitTete != 'E') {
            toucheSauv = 'D'
          }
        }
        if (e.getKeyCode == KeyEvent.VK_LEFT) {
          if (orientationInitTete != 'O') {
            toucheSauv = 'G'
          }
        }
        if (e.getKeyCode == KeyEvent.VK_UP) {
          if (orientationInitTete != 'S') {
            toucheSauv = 'H'
          }
        }
        if (e.getKeyCode == KeyEvent.VK_DOWN) {
          if (orientationInitTete != 'N') {
            toucheSauv = 'B'
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

            // Reload de la grille pour la nouvelle partie
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

    // Affichage du jeu
    def dessiner(): Unit = {
      // Pour eviter le scintillement
      grilleJeu.frontBuffer.synchronized {
        grilleJeu.clear()
        //draw our object TODO

        // Délimitation des différentes zones graphiques du jeu
        grilleJeu.drawRect(0, 0, width * pixelsSize, height * pixelsSize)
        affichageScore()

        // Pour afficher le jeu
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
    // TODO
    var compteurMenu: Int = maxColonnes + 10
    //TODO
    while (compilation == "Marche" || jeuInit == 1) {
      jeuInit = 0
      while (jeu == "Marche") {
        if (start == true) {
          // Afficher le jeu
          dessiner()
          //affichageGrille() TODO
          // Déplacement automatique du serpent
          bouger(toucheSauv)
          compteur += 1
          // Disparition du réducteur au bout de x temps
          if (compteur % 30 == 0) {
            suppression(reducteurDeSerpent, true)
          }
          // Conditions pour l'apparition d'un obstacle mortel
          if (compteur >= 100 && compteur % 40 == 0) {
            creerObstacles(1)
            nbObstaclesMortels += 1
            compteursauv = compteur
          }
          // Conditions pour l'apparition d'un reducteur de serpent
          if (compteur >= 200 && (math.random() * 30).toInt == 24 && nombresDeReducteurs < 1 && compteursauv != compteur) {
            creerReducteur(1)
          }
          /* TODO HighScore
          // mémorisation du score
          var memoryScore: Array[Int] = Array.ofDim(11)
          */

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
              //grilleJeu.drawString(pixelSize + 5, 80, s"Actual High Score: ${classé(0)}", Color.red, 64) TODO
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
        var decallageYSerpentDeco: Int = 0 // TODO variable inutile

        grilleJeu.drawRect(0, 0, width * pixelsSize + 7 * pixelsSize, height * pixelsSize)
        grilleJeu.drawString(pixelsSize + 5, 80, s"Menu", Color.black, 64)
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

  // Sert à trouver les valeurs correspondant aux différents éléments du jeu
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

object SnakeZanadSauzeat extends App {
  var x: Snake = new Snake(30, 20)
  x.affichageDuJeu()
}