package Model;

import java.util.List;

public class AccionSimple extends CeldaEspecial {

    public void ejecutarEspecial(int mov, Mapa mapaActual, PersonajePrincipal jugador1, PersonajePrincipal jugador2, Celda celdaOriginal0, Celda celdaOriginal1, int i) {
        CeldaEspecial celdaEsp = this;
        List<Integer> movX = celdaEsp.getDireccionX();
        List<Integer> movY = celdaEsp.getDireccionY();

        if (mov < 4) {
            mapaActual.retornarCelda(jugador1.getPosX(), jugador1.getPosY(), celdaOriginal0);
            //celdaOriginal0 = mapaActual.getCelda(movX.get(i),movY.get(i)).copy();
            celdaOriginal0.copy2(mapaActual.getCelda(movX.get(i), movY.get(i)));

            jugador1.setPosX(movX.get(i));
            jugador1.setPosY(movY.get(i));
            mapaActual.modificarPosJugador(0, jugador1.getPosX(), jugador1.getPosY());

            List<Integer> indObstaculo = celdaEsp.getIndObstaculo();
            List<Celda> reemplazarObstaculo = celdaEsp.getReemplazarObstaculo();
            if (indObstaculo != null) {
                Integer indObst = indObstaculo.get(i);
                if (indObst != -1) {
                    Objeto bb = mapaActual.getObstaculo(indObstaculo.get(i));
                    int altura = bb.getAltura();
                    int ancho = bb.getAncho();
                    int posX = bb.getPosX();
                    int posY = bb.getPosY();
                    Celda celdaReemplazar = reemplazarObstaculo.get(i);
                    for (int j = 0; j < altura; j++) {
                        for (int k = 0; k < ancho; k++) {
                            Celda aux = mapaActual.getCelda(posX + j, posY + k);
                            aux.setSprite(celdaReemplazar.getSprite());
                            aux.setTipo(celdaReemplazar.getTipo());
                        }
                    }
                }
            }
        } else {
            mapaActual.retornarCelda(jugador2.getPosX(), jugador2.getPosY(), celdaOriginal1);
            celdaOriginal1.copy2(mapaActual.getCelda(movX.get(i), movY.get(i)));
            jugador2.setPosX(movX.get(i));
            jugador2.setPosY(movY.get(i));
            mapaActual.modificarPosJugador(1, jugador2.getPosX(), jugador2.getPosY());

            List<Integer> indObstaculo = celdaEsp.getIndObstaculo();
            List<Celda> reemplazarObstaculo = celdaEsp.getReemplazarObstaculo();
            if (indObstaculo != null) {
                Integer indObst = indObstaculo.get(i);
                if (indObst != -1) {
                    Objeto bb = mapaActual.getObstaculo(indObstaculo.get(i));
                    int altura = bb.getAltura();
                    int ancho = bb.getAncho();
                    int posX = bb.getPosX();
                    int posY = bb.getPosY();
                    Celda celdaReemplazar = reemplazarObstaculo.get(i);
                    for (int j = 0; j < altura; j++) {
                        for (int k = 0; k < ancho; k++) {
                            Celda aux = mapaActual.getCelda(posX + j, posY + k);
                            aux.setSprite(celdaReemplazar.getSprite());
                            aux.setTipo(celdaReemplazar.getTipo());
                        }
                    }
                }
            }

        }

    }
}