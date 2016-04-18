package View;

import Model.*;
import Controller.*;

import java.util.*;
import java.lang.String;
import java.io.IOException;

public class Juego {
    final Scanner scan;
    final String[] txt_Historia = new String[4];
    final String[] txt_Dialogo = new String [2];
    final String cadenaGameOver;
    final String cadenaJuegoCompletado;
    private String nombre;

    InterpreteComandos interp;
    GestorMapas gestor;
    Renderizador rend;
    
    public void start() throws IOException, InterruptedException {
        while (true) {
            if (bienvenida()) {
                break;
            }
            boolean gameOver = false;
            do {
                System.out.println("Ingrese tu nombre papu: ");
                nombre = scan.nextLine();
                nombre = scan.nextLine();

                for (int i = 0; i < 3; ++i) { // numero de niveles
                    cleanWindow();
                    gameOver = jugar_Nivel(i);
                    if (gameOver) {
                        break;
                    }
                }
            } while (gameOver);
            cleanWindow();
            System.out.println("\n G A M E   O V E R !");
            System.out.print("Presione ENTER para volver al menu de bienvenida.");
            scan.nextLine();
        }
    }

    public boolean jugar_Nivel(int nivel) throws IOException, InterruptedException {
        // Nivel "i": Tutorial || Nivel1 || Nivel2
        boolean gameOver = false;

        if (nivel == 0) {
            System.out.println("Tutorial");
        } else {
            System.out.println("Nivel " + nivel);
        }

        // Mensaje para nivel "i"
        System.out.println(txt_Historia[nivel]);
        System.out.print("Presione enter para continuar...");
        scan.nextLine();
        
        cleanWindow();
        if (nivel == 0 ) {
            System.out.println(txt_Dialogo[0]);
            System.out.print("Presione enter para continuar...");
            scan.nextLine();
        }        
        
        cleanWindow();

        // Cargar mapa de nivel "i"
        gestor.cargarMapa(nivel);
        rend.dibujarMapa(gestor.getMapaActual());

        while (true) {
            System.out.println("Ingresar comando:");
            String comando = scan.nextLine();
            cleanWindow();

            int valor = interp.interpretarMovimiento(comando);
            gestor.realizarMovimiento(valor);
            rend.dibujarMapa(gestor.getMapaActual());
            String aux = gestor.realizarMovimientoEspecial(valor);

            // Verifico que no halla llego al fin
            if (aux.equals("F")) { /* ES EL FINAL DEL NIVEL */

                break;
            } else if (!aux.equals("")) { /* ES UNA CELDA DE ACCION */

                gameOver = cayo_CeldaEspecial(aux, comando, nivel, valor);
            }

            if (gameOver) {
                break;
            }
        }
        return gameOver;
    }

    public boolean cayo_CeldaEspecial(String aux, String comando, int nivel, int valor) throws IOException, InterruptedException {
        while (true) {
            rend.mostrarComandos(aux);
            comando = scan.nextLine();
            if (interp.interpretarEspecial(comando, aux)) {
                cleanWindow();
                break;
            } else {
                // se equivoco, bajarles vida
                if (gestor.perderVida(2)) {
                    System.out.println("Kiru y Milo perdieron 2 puntos de vida."); // siguen vivos
                } else {
                    System.out.println(cadenaGameOver);
                    return true; // gameOver = true;
                }
            }
        }

        while (true) {
            aux = gestor.ejecutarComando(valor);
            rend.dibujarMapa(gestor.getMapaActual());
            scan.nextLine();

            if (aux.equals("F") && nivel == 0) {
                System.out.println(txt_Dialogo[1]);
                System.out.print("Presione enter para continuar...");
                scan.nextLine();
                cleanWindow();
                break;
            }

            if (aux.equals("Done"))
                break;
            cleanWindow();
        }
        return false;
    }

    public void cleanWindow() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); //Limpia ventana
    }

    public boolean bienvenida() throws IOException, InterruptedException {
        cleanWindow();
        System.out.println("\n\n");        
        System.out.println("        __   __             _              _                    _       ");
        System.out.println("        \\ \\ / /            | |            | |                  | |      ");
        System.out.println("         \\ V /           __| |  ___     __| |  ___   _ __    __| |  ___ ");
        System.out.println("          \\ /           / _` | / _ \\   / _` | / _ \\ | '_ \\  / _` | / _ \\");
        System.out.println("          | | _  _  _  | (_| ||  __/  | (_| || (_) || | | || (_| ||  __/");
        System.out.println("          \\_/(_)(_)(_)  \\__,_| \\___|   \\__,_| \\___/ |_| |_| \\__,_| \\___|");
        System.out.println("\n");
        System.out.println("                                                         ___  ");
        System.out.println("                                                        |__ \\ ");
        System.out.println("                       ___   ___   _   _    _   _   ___    ) |");
        System.out.println("                      / __| / _ \\ | | | |  | | | | / _ \\  / / ");
        System.out.println("                      \\__ \\| (_) || |_| |  | |_| || (_) ||_|  ");
        System.out.println("                      |___/ \\___/  \\__, |   \\__, | \\___/ (_)  ");
        System.out.println("                                    __/ |    __/ |            ");
        System.out.println("                                   |___/    |___/             ");
        
 
        scan.nextLine();
        cleanWindow();
        System.out.println("Bievenido al juego");
        System.out.println("(Presiona ENTER para continuar...)");

        scan.nextLine();
        do {
            cleanWindow();
            System.out.println("1. Empezar el juego");
            System.out.println("2. Salir");
            int opc = 0;
            try {
                opc = scan.nextInt();
            } catch (java.util.InputMismatchException ex) {
                scan.nextLine();
            }

            if (opc == 1) {
                return false;
            } else if (opc == 2) {
                System.out.println("Desea salir? (1. Si, 2. No)");
                int salir = 0;
                try {
                    salir = scan.nextInt();
                } catch (java.util.InputMismatchException ex2) {
                    scan.nextLine();
                }
                if (salir == 1) {
                    return true;
                }
            }
        } while (true);
    }
    
    public Juego() {
        scan = new Scanner(System.in);
        
        interp = new InterpreteComandos();
        gestor = new GestorMapas();
        rend = new Renderizador(); 
        
        cadenaGameOver = "Has perdido.";
        cadenaJuegoCompletado = "Felicitaciones, has terminado el juego.";        
        
        txt_Historia[0] = "- Kiru y Milo conversan.\nLe nace la pregunta a Kiru y deciden viajar.";
        txt_Historia[1] = "- Kiru y Milo viajan a Paracas en un auto.\nLlegan a la playa y empiezan a jugar.";
        txt_Historia[2] = "- Kiru y Milo se encuentran con Peli el Pelicano.\nPeli el pelicano no sabe de donde viene Kiru. Kiru y Milo deciden viajar a la sierra.";
        txt_Historia[3] = "- Kiru y Milo conversan con Dana la Llama.\nDana responde la pregunta de Kiru. Kiru se contenta y decide, con Milo, viajar por todos los Andes.";
        
        txt_Dialogo[0] = "- Usa WASD para mover a Kiru y JKLI para mover a Milo.\n"
                       + "- Si ves un lugar para la accion o el duo... Parate sobre el!!\n"
                       + "  Podras realizar acciones especiales.\n"
                       + "- Solo podras pasar los niveles con la ayuda de las acciones especiales. Para\n"
                       + "  esto, tendras que presionar comandos que se mostraran en un cuadro de\n"
                       + "  dialogo como este.\n"
                       + "- Los comandos deben ser ejecutados en la secuencia correcta, sino\n"
                       + "  perderas puntos de vida.\n"
                       + "- Puedes ver los puntos de vida en la parte superior de la pantalla.\n"
                       + "- Para activar los terrenos con acciones especiales duo, tienen que estar sobre\n"
                       + "  ellos Kiru y Milo al mismo tiempo, en los de acciones especiales solo con uno\n"
                       + "  basta.\n";
        
        txt_Dialogo[1] = "- En tu aventura, a veces te toparas con animales malos.\n"
                       + "- Estos enemigos te bajaran puntos de vida. Si tus puntos de vida llegan a 0, se acabara el juego.\n"
                       + "- Si un enemigo afecta a un personaje, este no se podra mover. Tendras que usar a su amigo para ayudarlo.\n";
    }
}
