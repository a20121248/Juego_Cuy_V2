package Model;

import java.util.List;
import java.util.Scanner;

public class AccionDual extends CeldaEspecial {
    public void ejecutarEspecial(int mov, Mapa mapaActual, PersonajePrincipal jugador1, PersonajePrincipal jugador2, Celda celdaOriginal0, Celda celdaOriginal1, int i) {
        CeldaEspecial celdaEsp = this;
        int dualOpuesto = celdaEsp.getDualOpuesto();
        CeldaEspecial celdaEsp2 = mapaActual.getEspecial(dualOpuesto);
        List<Integer> movX1;
        List<Integer> movY1;
        List<Integer> movX2;
        List<Integer> movY2;
        if (mov < 4) {
            movX1 = celdaEsp.getDireccionX();
            movY1 = celdaEsp.getDireccionY();
            movX2 = celdaEsp2.getDireccionX();
            movY2 = celdaEsp2.getDireccionY();
        } else {
            movX1 = celdaEsp2.getDireccionX();
            movY1 = celdaEsp2.getDireccionY();
            movX2 = celdaEsp.getDireccionX();
            movY2 = celdaEsp.getDireccionY();

        }

        mapaActual.retornarCelda(jugador1.getPosX(), jugador1.getPosY(), celdaOriginal0);
        celdaOriginal0.copy2(mapaActual.getCelda(movX1.get(i), movY1.get(i)));
        mapaActual.retornarCelda(jugador2.getPosX(), jugador2.getPosY(), celdaOriginal1);
        celdaOriginal1.copy2(mapaActual.getCelda(movX2.get(i), movY2.get(i)));
        jugador1.setPosX(movX1.get(i));
        jugador1.setPosY(movY1.get(i));
        jugador2.setPosX(movX2.get(i));
        jugador2.setPosY(movY2.get(i));
        mapaActual.modificarPosJugador(0, jugador1.getPosX(), jugador1.getPosY());
        mapaActual.modificarPosJugador(1, jugador2.getPosX(), jugador2.getPosY());

    }
}