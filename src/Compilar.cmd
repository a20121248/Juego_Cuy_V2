set path=%path%;C:\Program Files\Java\jdk1.8.0_66\bin
javac Model/*.java Controller/*java View/*java
jar cvf Libreria.jar Model/*.class Controller/*.class View/*.class
javac -cp Libreria.jar Juego/*.java
jar cvfm Juego_Cuy.jar MANIFEST.MF Juego/*.class *.txt