package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class CeldaEspecial {
    String comandoEspecial;
    List<Integer> direccionX; //solo si es especial
    List<Integer> direccionY;
    List<Integer> liberaX;
    List<Integer> liberaY;
    List<Integer> valorLiberacion;
    List<Integer> indObstaculo;
    List<Celda> reemplazarObstaculo;
    List<Integer> momentoObstaculo;
    int dualOpuesto;

    public CeldaEspecial() {
        dualOpuesto = -1;
        liberaX = new ArrayList<Integer>();
        indObstaculo = null;
        valorLiberacion = new ArrayList<Integer>();
    }

    public String getComandoEspecial() {
        return comandoEspecial;
    }

    public void setComandoEspecial(String comandoEspecial) {
        this.comandoEspecial = comandoEspecial;
    }

    public void setDireccionX(List<Integer> direccionX) {
        this.direccionX = direccionX;
    }

    public void setDireccionY(List<Integer> direccionY) {
        this.direccionY = direccionY;
    }

    public void setDualOpuesto(int dualOpuesto) {
        this.dualOpuesto = dualOpuesto;
    }

    public void setLiberaX(List<Integer> liberaX) {
        this.liberaX = liberaX;
    }

    public void setLiberaY(List<Integer> liberaY) {
        this.liberaY = liberaY;
    }

    public void setValorLiberacion(List<Integer> valorLiberacion) {
        this.valorLiberacion = valorLiberacion;
    }

    public void setIndObstaculo(List<Integer> indObstaculo) {
        this.indObstaculo = indObstaculo;
    }

    public void setReemplazarObstaculo(List<Celda> reemplazarObstaculo) {
        this.reemplazarObstaculo = reemplazarObstaculo;
    }

    public List<Integer> getLiberaX() {
        return liberaX;
    }

    public List<Integer> getLiberaY() {
        return liberaY;
    }

    public List<Integer> getvalorLiberacion() {
        return valorLiberacion;
    }

    public List<Integer> getDireccionX() {
        return direccionX;
    }

    public List<Integer> getDireccionY() {
        return direccionY;
    }

    public List<Integer> getIndObstaculo() {
        return indObstaculo;
    }

    public List<Celda> getReemplazarObstaculo() {
        return reemplazarObstaculo;
    }

    public int getDualOpuesto() {
        return dualOpuesto;
    }

    public abstract void ejecutarEspecial(int mov, Mapa mapaActual, PersonajePrincipal jugador1, PersonajePrincipal jugador2, Celda celdaOriginal0, Celda celdaOriginal1, int indiceMovimientoEspecial);
}