
![image](https://github.com/Algath/Projet-Snake/assets/153815619/dcd03acf-bcd5-4371-a06c-2a14385bc349)

Snake .

Le joueur contrôle une longue et fine ligne semblable à un serpent, qui doit slalomer entre les bords de l'écran et les obstacles qui parsèment le niveau. Pour gagner chacun des niveaux, le joueur doit faire manger à son serpent un certain nombre de pastilles similaire à de la nourriture, allongeant à chaque fois la taille du serpent. Alors que le serpent avance inexorablement, le joueur ne peut que lui indiquer une direction à suivre (en haut, en bas, à gauche, à droite) afin d'éviter que la tête du serpent ne touche les murs ou son propre corps, auquel cas il risque de mourir.

Certains clones proposent des niveaux de difficulté dans lesquels varient l'aspect du niveau (simple ou labyrinthique), le nombre de pastilles à manger, l'allongement du serpent ou encore sa vitesse.

// Lancement du Jeu : 

Lancer IntelliJ IDEA (Compilateur) 

![image](https://github.com/Algath/Projet-Snake/assets/153815619/3b94293b-d22c-416f-8d83-56ca08b22eae)


Cliquer dans le dossier src sur "REPUBLIC_D"

![image](https://github.com/Algath/Projet-Snake/assets/153815619/4b43785b-aad6-470e-a31b-07c6aaf9d659)


Cliquer faire le run (compiler "object test" extends App")

![image](https://github.com/Algath/Projet-Snake/assets/153815619/c408f00a-8bc3-4e73-8c87-23eefe74a8e4)


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

En pleine partie ou depuis le "Menu Pause"

![image](https://github.com/Algath/Projet-Snake/assets/153815619/acebf795-09ad-448a-8b44-487c03b74f12)

Touche 's' : "stop/start" : Permet de mettre le jeu en pause (on est dans le "menu pause") et si on appuie de nouveau sur la touche permet de sortir de la pause.

Touche 'e' : "Exit" : Permet d'aller au "Menu Principal" (Pour relancer une nouvelle partie).

Depuis le "Menu Principale"

![image](https://github.com/Algath/Projet-Snake/assets/153815619/e7c4d188-4dda-45aa-ab5a-3e2f99ece663)


Touche 'e' : Arrête le Jeu (disparition du Menu) et invitation à fermer la fenêtre de Jeu.

![image](https://github.com/Algath/Projet-Snake/assets/153815619/4153defd-ef76-4e0d-9fdb-d186e2538d14)

Touche 'p' : Permet de relancer une nouvelle partie.
Touche 'p' : Permet de relancer une nouvelle partie.


// Règle du jeu :

Objectif : 
- <img src= 'src/res/pommes.jpg' width='25'> Manger les Pommes (contribue au Score de la partie) ;
- <img src= 'src/res/bombes.jpeg' width='25'> Eviter les Bombes (si une est mangée, alors la partie est perdue) ;
- <img src= 'src/res/cadeau.jpg' width='25'> Manger les cadeaux, pour réduire la taille du serpent par 2 ( mais la taille ne peut le réduire plus bas que 3) ;
- Eviter que le serpent ce mange lui-même.
  
  ![image](https://github.com/Algath/Projet-Snake/assets/153815619/0f92be1c-af95-4d90-aa04-fe5e1680bf28)
 

Apparition des objets :
- Ils apparaîssent à des positions aléatoires sur la grille ;
- <img src= 'src/res/pommes.jpg' width='25'> Les Pommes sont toujours au nombre de 3, tout au long de la partie.
  Le fait d'en manger une, entraîne une nouvelle sur la grille ;
- <img src= 'src/res/bombes.jpeg' width='25'> Les Bombes apparaîssent une par une, à partir de 100 périodes (appelé "temps" dans  le jeu), pour rester définitivement.
  Après une bombe est ajoutée, tous les multiples de 40 de période (ex : 120, 160, 200, 240, 280, 320, 360, 400...) ;
- <img src= 'src/res/cadeau.jpg' width='25'> Un Cadeau apparaît à partir de 200 périodes, de manière aléatoire dans le temps.
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

