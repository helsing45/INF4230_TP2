# INF4230 - Intelligence artificielle
**TP2 - Algorithme minimax appliqué au jeu Planète_H (Hiver 2017)**


## 1. Objectifs

Implémenter et appliquer l'algorithme minimax avec élagage alpha-beta.
Élaborer et définir une fonction d'évaluation efficace.
Implémenter un algorithme de prise de décision en temps réel (profondeur itérative).
## 2. Planète_H : La bataille!

Vous devez programmer un joueur artificiel pour une variante du jeu Planète_H (un jeu avec adversaire à somme nulle). Il est fortement conseillé d'implémenter votre joueur artificiel à l'aide de l'algorithme minimax avec élagage alpha-beta (alpha-beta pruning) vu en classe.
### 2.1 Suite de l’histoire de la planète H

Après avoir échoué dans sa mission de faire exploser la planète grâce à l’efficacité de la stratégie de défense A* du Htepien, le méchant bonhomme a décidé de revenir en force. Cette fois, il veut pouvoir placer une série de bombes pour faire exploser la planète. Cependant, il faut en poser une série de 5 bombes dans des cases consécutives (en ligne, en colonne ou en diagonale) pour qu’une explosion soit possible. Informer de ce secret, le Htepien s’est donné une stratégie de contre-attaque (en se dotant de tuiles magiques  qu’il peut poser dans les cases) qui consiste non seulement à bloquer les tentatives du méchant bonhomme à placer ses 5 bombes consécutives, mais aussi à faire en sorte que 5 cases consécutives comportant des tuiles magiques (en ligne, en colonne ou en diagonale) inhiberaient définitivement l’action du bonhomme qui n’aura d’autre choix que d’abandonner.
 
Il vous est demandé de développer l’intelligence artificielle du Htepien pour préserver la planète H de cette bataille.
 
Les règles de bataille sont les suivantes :
Les deux adversaires déposent chacun à son tour un objet dans une case (une tuile magique pour le Htepien) ou (une bombe pour l’autre).
La bataille se passe dans un monde H (grille) de dimension fixée arbitrairement. Les tuiles magiques (vertes) et les bombes (rouges) doivent être déposées à l'intérieur des cases et non sur les intersections.
Pour gagner, un des deux adversaires doit aligner exactement cinq (5) éléments (tuiles magiques ou bombes) à l'horizontale, à la verticale ou en diagonale.
La bataille se termine lorsqu'il y a un gagnant ou lorsqu'il n'y a plus de placement possible (grille complète). Dans ce dernier cas, la bataille est déclarée nulle.
### 2.2 Interface graphique

Une interface graphique pour cette version du jeu Planète_H est fournie dans planeteH_2.jar. Le jeu peut être lancé en ligne de commande : «java –jar planeteH_2.jar».

### 2.3 Temps de réflexion limité

Le temps de réflexion est exprimé en temps réel, ou plus précisément selon l'horloge de la machine sur lequel le jeu Planete_H est exécuté.

À partir d'une certaine taille de grille, il n'est pas possible de faire une exploration complète de l'arbre de recherche. Pour cette raison, votre joueur artificiel doit être capable de prendre des décisions imparfaites en temps réel afin de respecter le temps de réflexion alloué.

Pour cela, vous devrez élaborer une fonction d'évaluation permettant d'évaluer les feuilles des branches n'atteignant pas une partie complétée. Vous devez aussi modifier légèrement l'algorithme minimax avec élagage alpha-beta afin que votre joueur puisse prendre des décisions en temps réel. Référez-vous à la section 5.4 du livre de référence pour plus de détails. Cette section introduit une stratégie iterative deepening. D'autres stratégies y sont également mentionnées. Ces stratégies permettent à l'algorithme d'être anytime. Dans le jargon en intelligence artificielle, un algorithme anytime est un algorithme qui permet de trouver rapidement une première solution et de continuer sa recherche afin de l'améliorer. L'algorithme termine dès qu'on demande une réponse (ex.: temps alloué expiré) ou lorsque l'espace de recherche est entièrement visité (la solution optimale est trouvée). En d'autres mots, l'algorithme trouve une solution en tout temps, d'où le nom anytime.

