package Model;

public class Celda {
    private char sprite;
    private TipoCelda tipo; // 0 = impasable, 1 = terreno para A, 2 = terreno para B, 3 = terreno para ambos
    private int jugador; // -1 = nadie, 0 = jugador A, 1 = jugador B
    private int especial; // 0 = no 1 = solo activado 2 = ambos activado 3 = fin de nivel
    //-1 = solo desactivado -2 = ambos desactivado
    int indiceEspecial;

    public char getSprite() {
        return sprite;
    }

    public Celda(char sprite, TipoCelda tipo) {

        this.sprite = sprite;
        this.tipo = tipo;
        this.jugador = -1;
        this.especial = 0;

    }

    public Celda copy() {
        Celda c = new Celda(sprite, tipo);
        c.setJugador(jugador);
        c.setEspecial(especial);
        c.setIndiceEspecial(indiceEspecial);
        return c;
    }

    public void copy2(Celda c) {
        sprite = c.getSprite();
        especial = c.getEspecial();
        tipo = c.getTipo();
        indiceEspecial = c.getIndiceEspecial();
        jugador = c.getJugador();
    }

    public int getJugador() {
        return jugador;
    }

    public void setJugador(int jugador) {
        this.jugador = jugador;
    }

    public void setTipo(TipoCelda tipo) {
        this.tipo = tipo;
    }

    public void setSprite(char sprite) {
        this.sprite = sprite;
    }

    public TipoCelda getTipo() {
        return tipo;
    }

    public int getIndiceEspecial() {
        return indiceEspecial;
    }

    public int getEspecial() {
        return especial;
    }

    public void setEspecial(int especial) {
        this.especial = especial;
    }

    public void setIndiceEspecial(int indiceEspecial) {
        this.indiceEspecial = indiceEspecial;
    }

    public boolean verificarMovimientoPosible(TipoCelda tipo) {
        //System.out.println(this.tipo);System.out.println(TipoCelda.TERRENO_AMBOS);
        if (this.tipo == TipoCelda.TERRENO_AMBOS) {
            return true;
        }
        if (this.tipo == tipo) {
            return true;
        } else {
            return false;
        }
    }
}