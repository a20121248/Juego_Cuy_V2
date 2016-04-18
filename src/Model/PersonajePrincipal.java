package Model;

public class PersonajePrincipal extends Personaje {
    private static int vida = 10;

    public static boolean perderVida(int v) {
        vida -= v;
        return (vida > 0);
    }

    public static void reiniciarVida() {
        vida = 10;
    }

    public PersonajePrincipal(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public boolean verificarPosX(int dir, int bound1, int bound2, Mapa mapaActual, TipoCelda tipo) {

        int aux = posX;
        if (dir == 0) {
            aux++;
        } else {
            aux--;
        }
        if (aux < bound1 || aux >= bound2) {
            return false;
        }
        Celda c = mapaActual.getCelda(aux, posY);
        if (!c.verificarMovimientoPosible(tipo)) {
            return false;
        }
        posX = aux;
        return true;
    }

    public boolean verificarPosY(int dir, int bound1, int bound2, Mapa mapaActual, TipoCelda tipo) {
        int aux = posY;
        if (dir == 0) {
            aux++;
        } else {
            aux--;
        }
        if (aux < bound1 || aux >= bound2) {
            return false;
        }
        Celda c = mapaActual.getCelda(posX, aux);
        if (!c.verificarMovimientoPosible(tipo)) {
            return false;
        }
        posY = aux;
        return true;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAltura() {
        return altura;
    }

    public char getSprite() {
        return sprite;
    }
}