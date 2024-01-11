
![image](https://github.com/Algath/Projet-Snake/assets/153815619/dcd03acf-bcd5-4371-a06c-2a14385bc349)

Snake .

// Lancement du Jeu : 

Lancer IntelliJ IDEA (Compilateur)

Cliquer dans le dossier src sur "REPUBLIC_D"

Cliquer faire le run (compiler "object test" extends App")

La partie sera lancer directement

_____________________________________________________________________________________________________________________________________________________________________________

// Commandes du jeu :

Utiliser les flêches directionnelles du clavier pour jouer 
Le serpent se dirige : 
- vers le haut de la fenêtre, si la flêche HAUT à été appuyée ;
- vers le bas de la fenêtre, si la flêche BAS à été appuyée ;
- vers la droite de la fenêtre, si ...
- vers la gauche de la fenêtre, si ...

Après avoir appuyé sur une touche directionnelle, le serpent avance automatiquement dans cette même direction.

NB 1: Le reférentiel droite, gauche, bas et haut et celui de la fenêtre d'affichage.

Exemple : si le serpent va vers le bas, appuyer sur la flêche DROITE fera que le serpent tournera sur sa gauche ;
          si le serpent va vers la gauche, appuyer sur la flêche HAUT fera que le serpent tournera sur sa droite.

NB 2 : Le serpent ne peut pas revenir sur ses pas.
Exemple : si le serpent va vers la droite, appuyer sur la flêche GAUCHE n'aura aucune action (désactivée) ;
          si le serpent va vers le bas, appuyer sur la flêche HAUT n'aura aucune action (désactivée).

Autres touches

Touche 'w' : Affiche "Wahoooooo tu es trop fort !", dans la console du compilateur

En pleine partie ou depuis le "Menu Pause"
Touche 's' : "stop/start" : Permet de mettre le jeu en pause (on est dans le "menu pause") et si on appuie de nouveau sur la touche permet de sortir de la pause.
Touche 'e' : "Exit" : Permet d'aller au "Menu Principal" (Pour relancer une nouvelle partie).

Depuis le "Menu Principale"
Touche 'e' : Arrête le Jeu (disparition du Menu) et invitation à fermer la fenêtre de Jeu.
Touche 'p' : Permet de relancer une nouvelle partie. 


_____________________________________________________________________________________________________________________________________________________________________________

// Règle du jeu :

Objectif : 
- Manger les Pommes (contribue au Score de la partie) ;
- Eviter les Bombes (si une est mangée, alors la partie est perdue) ;
- Manger les cadeaux, pour réduire la taille du serpent par 2 ( mais la taille ne peut le réduire plus bas que 3) ;
- Eviter que le serpent ce mange lui-même. 

Apparition des objets :
- Ils apparaîssent à des positions aléatoires sur la grille ;
- Les Pommes sont toujours au nombre de 3, tout au long de la partie.
  Le fait d'en manger une, entraîne une nouvelle sur la grille ;
- Les Bombes apparaîssent une par une, à partir de 100 périodes (appelé "temps" dans  le jeu), pour rester définitivement.
  Après une bombe est ajoutée, tous les multiples de 40 de période (ex : 120, 160, 200, 240, 280, 320, 360, 400...) ;
- Un Cadeau apparaît à partir de 200 périodes, de manière aléatoire dans le temps.
  Celui-ci reste durant une durée aléatoire, ne pouvant excéder une durée de 30 périodes.
  Il ne peut pas avoir qu'au plus que 1 cadeau sur la grille ;

 Fin de partie :
 Le jeu est perdu si le serpent se mange lui-même ou mange une Bombe.

 _____________________________________________________________________________________________________________________________________________________________________________

  Crédit :

  Maroua & Florian

  Sion, 2024
  




                      
                      



puis avance automatiquement dans cette même direction.
De même pour les autres directions

