
import hevs.graphics.FunGraphics

import java.awt.Color
import java.awt.event.{KeyAdapter, KeyEvent}
import java.util.Scanner

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

    // position d'apparition de la tête du Serpent (Aléatoire)

    var positionLignesInit: Int = (math.random() * (maxLignes - 1)).toInt
    var positionColonnesInit: Int = (math.random() * (maxColonnes - 1)).toInt
    var orientationInitTete: Char = 'E'


    grille(positionLignesInit)(positionColonnesInit) = teteSerpent

    //pour générer la queue du serpent initiale sur la grille
    for (i: Int <- 1 until longueurInitSerpent) {
      var positionQueueInitSerpentN: Int = positionColonnesInit + i

      grille(positionLignesInit)(horsGrille(positionColonnesInit + i, maxColonnes)) = (i + 1)
    }

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

    def suppression(Quoi : Int ,ActiveDest : Boolean) : Unit ={
      if(ActiveDest == true){
        var valCase1 : Int = 0
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


      var posY: Int = chercheValeurDansLeTableau(1)(0)
      var posX: Int = chercheValeurDansLeTableau(1)(1)

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

      if (quoiMange == -1) {
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

        tailleSerpent = (tailleSerpent / 2).toInt

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

    // Affichage de la grille, pour visu
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

    // Affichage du Jeu
    def affichageDuJeu(): Unit = {

      val pixelsSize: Int = 20


      val height: Int = maxLignes
      val width: Int = maxColonnes

      var toucheSauv: Char = 'G'

      var sensibilite: Int = 70000000

      var nbObstaclesMortels: Int = 0

      var compteur: Int = 0

      var compteursauv: Int = 0

      val grilleJeu: FunGraphics = new FunGraphics(width * pixelsSize + 7 * pixelsSize, height * pixelsSize)


      grilleJeu.setKeyManager(new KeyAdapter() { // Will be called when a key has been pressed
        override def keyPressed(e: KeyEvent): Unit = {


          if (e.getKeyChar == 't') {
            println("Test 'A' was pressed")
          }
          if (e.getKeyCode == KeyEvent.VK_RIGHT) {
            if (orientationInitTete != 'E') {
              toucheSauv = 'D'
              bouger(toucheSauv)
            }
          }
          if (e.getKeyCode == KeyEvent.VK_LEFT) {
            if (orientationInitTete != 'O') {
              toucheSauv = 'G'
              bouger(toucheSauv)
            }
          }
          if (e.getKeyCode == KeyEvent.VK_UP) {
            if (orientationInitTete != 'S') {
              toucheSauv = 'H'
              bouger(toucheSauv)
            }
          }
          if (e.getKeyCode == KeyEvent.VK_DOWN) {
            if (orientationInitTete != 'N') {
              toucheSauv = 'B'
              bouger(toucheSauv)
            }
          }


        }
      })

      while (jeu == "Marche") {

        grilleJeu.clear()
        //draw our object

        // DELIMITATION DE LA ZONE DE JEUX. PARTI A DROITE POUR AFFICHER SCORE, NIVEAU ET ...
        grilleJeu.drawRect(0, 0, width * pixelsSize, height * pixelsSize)
        grilleJeu.drawString(width * pixelsSize + 5, 20, s"Score : ${nombresDeProiesMangees.toString}", Color.CYAN, 12)
        grilleJeu.drawString(width * pixelsSize + 5, 35, s"Taille Serpent : ${tailleSerpent.toString}", Color.BLACK, 12)
        grilleJeu.drawString(width * pixelsSize + 5, 50, s"Temps : ${compteur.toString}", Color.DARK_GRAY, 12)
        grilleJeu.drawString(width * pixelsSize + 5, 65, s"Obstacles Mortels : ${nbObstaclesMortels.toString}", Color.RED, 12)
        grilleJeu.drawString(width * pixelsSize + 5, 80, s"Réducteur : ${nombresDeReducteurs.toString}", Color.BLUE, 12)


        for (i <- grille.indices) {
          for (j <- grille(i).indices) {

            var valeur: Int = grille(i)(j)

            if (valeur > 0) {
              grilleJeu.drawFilledCircle(j * pixelsSize, i * pixelsSize, pixelsSize)
            }

            else if (valeur == proie) {
              grilleJeu.drawCircle(j * pixelsSize, i * pixelsSize, pixelsSize)
            }
            else if (valeur == obstacleMortel) {
              grilleJeu.drawFillRect(j * pixelsSize, i * pixelsSize, pixelsSize, pixelsSize)
            }
            else if (valeur == reducteurDeSerpent) {
              grilleJeu.drawRect(j * pixelsSize, i * pixelsSize, pixelsSize, pixelsSize)
            }
          }
        }
        //affichageGrille()

        //refresh the screen at XXX FPS
        grilleJeu.syncGameLogic(120)

        // ralentir le jeux
        for (i: Int <- 0 to sensibilite) {
          if (sensibilite == i) {
            //faire avancer automatiquement le serpent
            bouger(toucheSauv)

            compteur += 1

            // Faire disparaitre le réducteur au bout de x temps
            if(compteur % 20 == 0) {
              suppression(reducteurDeSerpent,true)
            }

            // Regle pour faire apparaitre un obstacle mortel
            if (compteur >= 100 && compteur % 40 == 0) {
              creerObstacles(1)
              nbObstaclesMortels += 1
              compteursauv = compteur
            }

            //Regle pour faire apparaitre un reducteur de serpent
            if (compteur >= 200 && (math.random()*30).toInt == 24 && nombresDeReducteurs < 1 && compteursauv != compteur) {
              creerReducteur(1)

            }


          }
        }
      }
      println("Tu es nul !!!!!")
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

  var x: Snake = new Snake()

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