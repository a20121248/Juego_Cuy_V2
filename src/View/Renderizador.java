package View;

import Model.*;

public class Renderizador {

    public void dibujarMapa(Mapa mapa) {
        int ancho = mapa.getAncho();
        int altura = mapa.getAltura();
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < ancho; j++) {
                Celda c = mapa.getCelda(i, j);
                char sprite = c.getSprite();
                System.out.print(sprite);
            }
            System.out.println();
        }
    }

    public void mostrarComandos(String comandos) {
        System.out.println("Ejecute la secuencia de comandos: " + comandos);
    }
}