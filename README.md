# AncorToDemocrat - GUI  
Outil graphique pour AncotToDemocrat.  

## Environnement:  
- Java  
- git  
- make  

## Installation  
Cloner le répertoire:  
`git clone https://github.com/ancortodemocrat/ancortodemocrat-gui.git`  
`cd ancortodemocrat-gui`  
L'executable est disponnible: `ancor2-gui.jar`  
### Compilation  
Si besoin de recompiler:  
`make`  

## Commandes  
Commande générale:  
`java -jar ancor2-gui.jar <module> [args...]`  
Aide disponnible:  
`java -jar ancor2-gui.jar --help`  
`java -jar ancor2-gui.jar <module> --help`  

## Modules  
#### Concordancier  
`java -jar ancor2-gui.jar concordancier`    
`java -jar ancor2-gui.jar concordancier --help`  
- Possibilité de spécifier les données à traiter lors de l'appel (voir `--help`)  
