***PROJET DE MODELISATION - Groupe i6***

**Membres :**
- DEROUBAIX François
- CALOT Lohan
- SANNIER Alban
- DORANGE Martin

**Répartition du travail :**
- François : création de l'interface, gestion de l'affichage et hiérarchie du projet
- Lohan : architecture du projet, maquette et manipulations du modèle (Zoom, translation, rotation, centre)
- Alban : maquette, gestion de l'affichage, lecture fichiers ply (stable)
- Martin : lecture des fichiers ply

**Précisions :**
- Par manque de communication entre certains membres de l'équipe et de documentation, nous n'avons pu utiliser le Parser de Martin par conséquent Alban a dû en recréer un, ce qui nous a évidemment retardé et a généré un parser instable
- Les fichiers volumineux ne fonctionnent pas encore, à l'exception du crâne s'il est représenté en tant que nuage de points
- Malgré deux approches différentes sur le calcul du centre de gravité du modèle, le centre supposé approche du centre réel mais reste encore trop éloigné. Cela veut donc dire que le modèle n'est pas encore centré et que les rotations/zoom ont un comportement anormal


#"UPDATE 01:44
- nous avons réussi à faire fonctionné le parser
- les fichiers de taille convenable se load facilement et ne font pas planté le programme
- les faces se coloris bien (mais or propriété du ply) le get de couleur sera à ajouter
- le jar se trouve dans sources-du-projet mais est fonctionnel (à voir ensuite pour réussir à changer l'emplacement)


## Exécution
La version de java optimale est la version 11.0.8 ou une version supérieur.
> vous devez télécharger le repo, l'extraire, et lancer le Modelizator.jar en double cliquant qui est présent dans le dossier sources-du-projet 

vous pouvez aussi en vous rendant dans votre invite de commande et dans le dossier sources-du-projet exécuter la commande suivant :

>java -jar Modelizator.jar
