package Juego;

import Model.*;
import View.*;
import Controller.*;
import java.io.IOException;

public class Principal {
    static Juego juego;
    
    public static void main(String[] args) throws IOException, InterruptedException {
        juego = new Juego();
        juego.start();
    }
}