                                                    ***PROJET DE MODELISATION - Groupe i6***

**Membres :**
- DEROUBAIX François
- CALOT Lohan
- SANNIER Alban
- DORANGE Martin

**Répartition du travail :**
- François : création de l'interface, gestion de l'affichage et hiérarchie du projet
- Lohan : architecture, maquette et manipulations du modèle (Zoom, translation, rotation, centre)
- Alban : maquette, gestion de l'affichage
- Martin : lecture des fichiers ply

**Précisions :**
- Par manque de communication entre certains membres de l'équipe et de documentation, nous n'avons pu utiliser le Parser de Martin par conséquent Alban a dû en recréer un, ce qui nous a évidemment retardé et a généré un parser instable
- Les fichiers volumineux ne fonctionnent pas encore, à l'exception du crâne s'il est représenté en tant que nuage de points
- Malgré deux approches différentes sur le calcul du centre de gravité du modèle, le centre supposé approche du centre réel mais reste encore trop éloigné. Cela veut donc dire que le modèle n'est pas encore centré et que les rotations/zoom ont un comportement anormal