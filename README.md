# PROJET DE MODELISATION - Groupe i6

# Membres :

- DEROUBAIX François
- CALOT Lohan
- SANNIER Alban

# Répartition du travail :

François : Création de l'intégralité de l'interface utilisateur, des caméras, gestion de l'affichage et hiérarchie du projet (MCV). Source inépuisable d'inspiration, "Tel le jour faiblit, François est toujours très très clair"
Lohan : Architecture du projet, débug, maquette, lumières et manipulations du modèle (Zoom, translation, rotation, centre). "là où y'a de l'ombre, il existe heureusement le scalaire du vecteur normal à la face et du vecteur lumière"
Alban : Création intégrale du Parser, implémentation de multithreading, travaux sur le GraphicContext. "Quand Alban parse, les ply trépassent"

# Description :

Cette application permet de modéliser en trois dimensions un fichier PLY.
Grâce à ce modélisateur, il est possible de manipuler l'objet, c'est à dire le déplacer, le zoomer ou le faire pivoter.

Dans un premier temps, l'application propose de charger les différents fichiers PLY présents dans le dossier exemples ou bien à un autre emplacement. Dans cette première interface, il est possible de rechercher un nom de fichier.
Après avoir chargé le fichier, l'application propose une fenêtre de visualisation de l'objet et un panneau vertical avec les différentes commandes, qui possèdent leurs raccourcis (voir la fenêtre aide).
Parmi ces commandes, l'utilisateur trouvera des flèches pour déplacer l'objet et d'autres pour le pivoter, ainsi qu'un bouton pour activer les lumières afin de créer un relief sur l'objet, de choisir la couleur du modèle et de créer une seconde caméra.

# Précisions :

Ce projet a été créé par 3 étudiants en 2ème année de DUT Informatique à l'IUT A de Villeneuve d'Ascq.

La documentation est disponible à sources-du-projet/documentation/apidocs/index.html (à ouvrir avec un navigateur)
D'un côté plus technique, ce projet contient 3 design patterns (Subject/Listener, Factory et MVC). À noter que par souci de performances et de fluidité, l'architecture composée de classes a été recréée avec des structures moins gourmandes en ressources comme les tableaux.

Avant tout ce qui rend ce projet unique, c'est premièrement sa structure optimisée et repensée puis deuxièmement ses choix en terme de customisations comme le choix de la couleur du modèle et la possibilité d'avoir un second aperçu.

Ce projet et son programme source sont en libre accès, soit Open Source.

## Exécution

La version de java optimale est la version 11.0.8 ou une version supérieur.

Vous devez télécharger le repo, l'extraire, et lancer le Modelizator.jar en double cliquant dessus.

vous pouvez aussi en vous rendant dans votre invite de commande et dans le dossier sources-du-projet exécuter la commande suivant :

> java -jar Modelizator.jar