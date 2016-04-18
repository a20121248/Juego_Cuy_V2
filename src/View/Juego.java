package View;

import Model.*;
import Controller.*;

import java.util.*;
import java.lang.String;
import java.io.IOException;


public class Juego {

    public static void bucleLectura() {
        int teclaPresionada = 0;
        //System.exit(0) finaliza el programa.
        while (true) {
            try {
                teclaPresionada = System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (teclaPresionada == -1) {
                System.exit(0);
            }
            if (teclaPresionada == '\n') {
                continue; //nueva linea
            }
            if (teclaPresionada == 'a') {
                return; // ok, salimos del bucle de lectura
            } else if (teclaPresionada == 'b') {
                System.out.println("El juego termina");
                System.exit(0);
            } else {
                System.out.println("Presione a o b");
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        /* Director's Cut
         * 
         * final String[] historias = {
         "Habia una vez un cuy que jugaba chowdown y se fue de viaje por el Peru buscando chowdonitas.",
         "Conocio a Andrea Guardia y la reto a un chowdown. Fue ggeado.",
         "Fue a la mundial de Pokemon y conocio a Cybertron.",
         "Vencio al jefe final Mascapo y se volvio el mejor chowdownita de la historia."
         };
		
         final String cadenaGameOver = "Has dejado en visto a Hector.";
         final String cadenaJuegoCompletado = "El juego termino, pero el chowdown sigue.";*/
        final String[] historias = {
            "Kiru y Milo conversan. Le nace la pregunta a Kiru y deciden viajar.",
            "Kiru y Milo viajan a Paracas en un auto. Llegan a la playa y empiezan a jugar.",
            "Kiru y Milo se encuentran con Peli el Pelicano. Peli el pelicano no sabe de donde viene Kiru. Kiru y Milo deciden viajar a la sierra.",
            "Kiru y Milo conversan con Dana la Llama. Dana responde la pregunta de Kiru. Kiru se contenta y decide, con Milo, viajar por todos los Andes."
        };

        final String cadenaGameOver = "Has perdido.";
        final String cadenaJuegoCompletado = "Felicitaciones, has terminado el juego.";
        final String[] dialogo = {
            "o	Usa WASD para mover a Kiru y JKLI para mover a Milo"
            + "o	Si ves un lugar para la accion o el duo ¡Parate sobre el! Podras realizar acciones especiales.\n"
            + "o	Solo podras pasar los niveles con la ayuda de las acciones especiales. Para esto, tendras que presionar comandos que se mostraran en un cuadro de dialogo como este.\n"
            + "o	Los comandos deben ser ejecutados en la secuencia correcta sino perderas puntos de vida.\n"
            + "o	Puedes ver los puntos de vida en la parte superior de la pantalla.\n"
            + "o	Para activar los terrenos con acciones especiales duo, tienen que estar sobre ellos Kiru y Milo al mismo tiempo, en los de acciones especiales solo con uno basta.\n",
            "o	En tu aventura, a veces te toparas con animales malos.\n"
            + "o	Estos enemigos te bajaran puntos de vida. Si tus puntos de vida llegan a 0, se acabara el juego.\n"
            + "o	Si un enemigo afecta a un personaje, este no se podra mover. Tendras que usar a su amigo para ayudarlo.\n"

        };

        boolean gameOver = false;
        do {
            InterpreteComandos interp = new InterpreteComandos();
            GestorMapas gestor = new GestorMapas();
            Renderizador rend = new Renderizador();
            if (gameOver) {
                //Se ha perdido por lo menos una vez
                gameOver = false;
                System.out.println("VueLvelo a intentar..");
                gestor.reiniciarVida();
            }

            //INICIO: Pantalla de inicio
            System.out.println("Kiru es un cuy mascota");
            System.out.println("a. Iniciar juego");
            System.out.println("b. Salir del juego");

            //¿Juego nuevo o salir?
            bucleLectura();
            scanner.nextLine();

            cleanWindow();
            //Solicitud de datos para juego nuevo
            System.out.println("Ingrese su nombre");
            String nombre = scanner.nextLine();
            int cantMapas = 3;
            for (int i = 0; i < cantMapas; i++) {

                if (i == 0) {
                    System.out.println("Tutorial");
                } else {
                    System.out.println("Nivel " + i);
                }

                //Pantalla de historia "i"
                System.out.println(historias[i]);
                System.out.print("Presione enter para continuar.");
                scanner.nextLine();
                if (i == 0) {
                    System.out.println(dialogo[0]);
                    System.out.print("Presione enter para continuar.");
                    scanner.nextLine();
                }
                cleanWindow();
                //Tutorial, Nivel 1, Nivel 2
                gestor.cargarMapa(i);
                rend.dibujarMapa(gestor.getMapaActual());
                while (true) {
                    System.out.println("Ingresar comando");
                    String comando = scanner.nextLine();
                    cleanWindow();
                    
                    int valor = interp.interpretarMovimiento(comando);
                    gestor.realizarMovimiento(valor);
                    rend.dibujarMapa(gestor.getMapaActual());
                    String aux = gestor.realizarMovimientoEspecial(valor);
                    if (aux.equals("F")) {
                        break;
                    } else if (!aux.equals("")) {
                        /* ES CELDA ESPECIAL */
                        while (true) {                            
                            rend.mostrarComandos(aux);
                            comando = scanner.nextLine();
                            cleanWindow();                            
                            
                            if (interp.interpretarEspecial(comando, aux)) {
                                break; // Slsvcn estuvo aqui 08/09/2015; acerto el comando, salir del bucle
                            } else {
                                // se equivoco, bajarles vida
                                if (gestor.perderVida(2)) {
                                    System.out.println("Kiru y Milo perdieron 2 puntos de vida."); // siguen vivos
                                } else {
                                    // gg
                                    System.out.println(cadenaGameOver);
                                    gameOver = true;
                                    break;
                                }
                            }
                        }
                        if (gameOver) {
                            break;
                        }

                        while (true) {
                            aux = gestor.ejecutarComando(valor);
                            rend.dibujarMapa(gestor.getMapaActual());
                            scanner.nextLine();
                            
                            if (aux.equals("F") && i == 0) {
                                System.out.println(dialogo[1]);
                                System.out.print("Presione enter para continuar.");
                                scanner.nextLine();
                                //cleanWindow();
                                break;
                            }
                            
                            if (aux.equals("Done")) {
                                break;
                            }
                            
                            cleanWindow();
                        }
                    }
                    if (aux.equals("F")) {
                        break;
                    }
                }
                if (gameOver) {
                    break;
                }
            }
        } while (gameOver);

        //Pantalla de historia 4
        System.out.println(historias[3]);

        //Pantalla de juego completado
        System.out.println(cadenaJuegoCompletado);

        scanner.close();
    }
    
    private static void cleanWindow() throws IOException, InterruptedException{
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); //Limpia ventana
    }    
}