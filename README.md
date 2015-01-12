FishAndShark
============
Auteurs:  
Célia CACCIATORE,  
Jonathan GEOFFROY

------------------------------------
Implementation de [Wa-Tor](https://en.wikipedia.org/wiki/Wa-Tor) dans le cadre du cours Intelligence Des Logiciels (IDL), Master 2 Informatique, spécialité IAGL, Université Lille1.

# Exécution #
Afin d'exécuter facilement l'application, un script maven est fourni:

    git clone https://github.com/CeliaDoolaeghe/FishAndShark.git
    cd FishAndShark
    mvn install
    mvn run [args]
    gnuplot *.plot
  
## Arguments ##
Les différents paramètres de la simulation doivent être donné en tant que paramètres de la commande maven:

|                   Paramètre                  | Valeur Conseillée |
|:--------------------------------------------:|:-----------------:|
| taille de la mer                             | 40                |
| nombre de poissons au début de la simulation | 250               |
| nombre de requins au début de la simulation  | 50                |
| temps de gestation des poissons              | 3                 |
| temps de gestation des requins               | 8                 |
| temps avant qu'un requin meurt de faim       | 2                 |
| délai d'attente entre 2 tours                | 200               |

Soit la commande suivante pour exécuter la simulation conseillée:

    mvn run 40 250 50 3 8 2 200
  
## Interface Graphique  ##
L'interface graphique fournie présente l'environnement sous forme de ronds de couleurs:

 * bleu pour un poisson,
 * rouge pour un requin,
 * gris clair pour une case vide.
  
## Génération de graphiques ##
Afin de générer automatiquement les graphiques correspondant à la simulation, deux fichiers gnuplot sont fournis:

 * **simulationFishAndShark.plot** crée un nuage de points en fonction du nombre de poissons et de requins 
 * **simulationTime.plot** crée un diagramme affichant le nombre de poissons et de requins en fonction du temps

# Structure du Projet #

     ./src/main/java/
    └── iagl
        └── idl
            └── fishandshark
                ├── App.java
                ├── graph
                │   ├── CSVLogger.java
                │   ├── FishAndSharksLogger.java
                │   └── FunctionLogger.java
                ├── mas
                │   ├── agent
                │   │   ├── Agent.java
                │   │   ├── Fish.java
                │   │   └── Shark.java
                │   ├── environment
                │   │   ├── Coordinate.java
                │   │   └── Environment.java
                │   └── MAS.java
                └── view
                    ├── BoardPanel.java
                    ├── FishAndSharkFrame.java
                    └── StatusPanel.java

## MAS: Système Multi-Agent ##
Afin d'obetnir une simulation qui soit **Multi-Agent**, la classe *MAS.java* récupère tous les *Agent*s contenus dans l'*Environment* afin de leur permettre de jouer leurs actions.  

## Environment: Modèle ##
Le modèle est entièrement contenu dans la classe *Environment.java*, qui référence non seulement tous les agents, mais également leur emplacement dans la mer.  
De plus, cette classe possède plusieurs **helpers** afin d'informer les *Agent*s sur l'état de l'environnement, comme par exemple la liste de ses voisins, ou encore la prochaine place libre proche de lui.

## Agent: Comportement ##
Chaque *Agent* a son propre comportement ; il possède une méthode *doIt()* qui lui permet de jouer son tour dans la simulation. Cette méthode est donc l'implémentation du comportement de cet *Agent*.  
On trouve dans cette implémentation deux types d'*Agent*s: les poissons (*Fish*) et les requins (*Sharks*).

## Fish: Poisson ##
Le comportement d'un poisson est de tenter de se déplacer si possible, c'est-à-dire si au moins une case adjacente est libre. Le poisson essaye ensuite dans le même tour de donner vie à un autre poisson si cela lui est possible.

## Shark: Requin ##
En premier, le requin vérifie qu'il n'est pas déjà mort de faim. Si tel est le cas, il se supprime de l'environnement. Dans le cas contraire, il tente d'abord de manger un poisson si celui-ci est sur une case adjacente. Il tente ensuite de faire naitre un nouveau requin, avant de se déplacer si cela lui est possible, en suivant les mêmes règles que pour le poisson.