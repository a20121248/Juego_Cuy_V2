package Controller;

import Model.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.List;

public class GestorMapas {

    List<Mapa> mapas;
    Mapa mapaActual;
    PersonajePrincipal jugador1, jugador2;
    boolean fin1, fin2;
    int indMapaActual;
    int cant;
    String comandoActual;
    Celda celdaDual, celdaOriginal0, celdaOriginal1;
    int dual;
    int tipoDual; // 0 = jugador1, 1= jugador2
    int indMovimientoEspecial;
    CeldaEspecial espAux;

    private Mapa Leer_Mapa(List<Objeto> obstaculos, int i1, int j1, int i2, int j2, BufferedReader inputStream, ArrayList<String> lineasMapa0, String nombre) {
        boolean[][] visit = new boolean[20][20];
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 16; j++) {
                visit[i][j] = false;
            }
        }
        //Lectura de archivos de texto
        InputStream in = getClass().getResourceAsStream(nombre);
        inputStream = new BufferedReader(new InputStreamReader(in));

        String linea;
        try {
            while ((linea = inputStream.readLine()) != null) {
                if (linea.equals("")) {
                    break;
                }
                lineasMapa0.add(linea);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Mapa m = new Mapa(lineasMapa0.get(0).length(), lineasMapa0.size(), i1, j1, i2, j2);
				//m = new Mapa(16,12,5,9,15,15);

        for (int i = 0; i < lineasMapa0.size(); i++) {
            ArrayList<Celda> fila = new ArrayList<Celda>();
            for (int j = 0; j < lineasMapa0.get(0).length(); j++) {
                if (lineasMapa0.get(i).charAt(j) == 'S') {
                    Celda celda = new Celda(lineasMapa0.get(i).charAt(j), TipoCelda.TERRENO_A);
                    fila.add(celda);
                } else if (lineasMapa0.get(i).charAt(j) == 'N') {
                    Celda celda = new Celda(lineasMapa0.get(i).charAt(j), TipoCelda.TERRENO_B);
                    fila.add(celda);
                } else if (lineasMapa0.get(i).charAt(j) == 'A') {
                    Celda celda = new Celda('S', TipoCelda.TERRENO_A);
                    fila.add(celda);
                } else if (lineasMapa0.get(i).charAt(j) == 'B') {
                    Celda celda = new Celda('N', TipoCelda.TERRENO_B);
                    fila.add(celda);
                } else if (lineasMapa0.get(i).charAt(j) == 'C' || lineasMapa0.get(i).charAt(j) == 'D') {
                    Celda celda = new Celda(lineasMapa0.get(i).charAt(j), TipoCelda.TERRENO_AMBOS);
                    fila.add(celda);
                } else {
                    Celda celda = new Celda(lineasMapa0.get(i).charAt(j), TipoCelda.IMPASABLE);
                    fila.add(celda);
                }
            }
            m.addFila(fila);
        }
        Celda aux;
        Objeto b;
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 16; j++) {
                //System.out.println(i);System.out.println(j);
                aux = m.getCelda(i, j);
                char c = aux.getSprite();
                //System.out.println(c);
                if (visit[i][j] == false && c != ' ' && c != 'A' && c != 'B' && c != 'S' && c != 'N' && c != 'D' && c != 'C') {
                    int contF = 1, contC = 1; //contador filas contador columnas
                    while (i + contF < 12 && m.getCelda(i + contF, j).getSprite() == c) {
                        contF++;
                    }
                    while (j + contC < 16 && m.getCelda(i, contC + j).getSprite() == c) {
                        contC++;
                    }
                    for (int k = 0; k < contF; k++) {
                        for (int l = 0; l < contC; l++) {
                            visit[i + k][j + l] = true;
                        }
                    }
                    b = new Objeto(i, j, contC, contF, c);
                    obstaculos.add(b);
                }
            }
        }
        m.setObstaculos(obstaculos);
        for (int i = 0; i < obstaculos.size(); i++) {
            Objeto bb = obstaculos.get(i);
            int altura = bb.getAltura();
            int ancho = bb.getAncho();
            int posX = bb.getPosX();
            int posY = bb.getPosY();
            char sprite = bb.getSprite();
            for (int j = 0; j < altura; j++) {
                for (int k = 0; k < ancho; k++) {
                    aux = m.getCelda(posX + j, posY + k);
                    aux.setTipo(TipoCelda.IMPASABLE);
                    aux.setSprite(sprite);
                }
            }
        }
        int cantCeldasEspeciales = 0;
        try {
            while ((linea = inputStream.readLine()) != null) {
                if (linea.equals("")) {
                    break;
                }
                cantCeldasEspeciales = Integer.parseInt(linea);

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<CeldaEspecial> listaCeldaEsp = new ArrayList<CeldaEspecial>();
				//System.out.println("KEK");
        //System.out.println(cantCeldasEspeciales);
        for (int i = 0; i < cantCeldasEspeciales; i++) {
            //System.out.println(i);
            int x, y, esp;
            List<Integer> direccion;

            try {
                CeldaEspecial celdaEsp;
                x = Integer.parseInt(inputStream.readLine());
                y = Integer.parseInt(inputStream.readLine());
                aux = m.getCelda(x, y);
                esp = Integer.parseInt(inputStream.readLine());
                aux.setEspecial(esp);
                if (esp == 1 || esp == -1) {
                    celdaEsp = new AccionSimple();
                } else {
                    celdaEsp = new AccionDual();
                }
                if (esp == 1 || esp == -1) {
                    aux.setSprite('C');
                } else {
                    aux.setSprite('D');
                }

                aux.setIndiceEspecial(Integer.parseInt(inputStream.readLine()));
                celdaEsp.setComandoEspecial(inputStream.readLine());
                direccion = new ArrayList<Integer>();
                int cantMov = Integer.parseInt(inputStream.readLine());
                for (int j = 0; j < cantMov; j++) {
                    direccion.add(Integer.parseInt(inputStream.readLine()));
                }
                celdaEsp.setDireccionX(direccion);
                direccion = new ArrayList<Integer>();
                for (int j = 0; j < cantMov; j++) {
                    direccion.add(Integer.parseInt(inputStream.readLine()));
                }
                celdaEsp.setDireccionY(direccion);

                List<Integer> liberaX = new ArrayList<Integer>();
                List<Integer> liberaY = new ArrayList<Integer>();
                List<Integer> valorLiberacion = new ArrayList<Integer>();

                int cantLibera = Integer.parseInt(inputStream.readLine());

                for (int j = 0; j < cantLibera; j++) {
                    liberaX.add(Integer.parseInt(inputStream.readLine()));
                }
                for (int j = 0; j < cantLibera; j++) {
                    liberaY.add(Integer.parseInt(inputStream.readLine()));
                }
                for (int j = 0; j < cantLibera; j++) {
                    valorLiberacion.add(Integer.parseInt(inputStream.readLine()));
                }

                celdaEsp.setLiberaX(liberaX);
                celdaEsp.setLiberaY(liberaY);
                celdaEsp.setValorLiberacion(valorLiberacion);

                int cantDestruye = Integer.parseInt(inputStream.readLine());

                if (cantDestruye == 1) {
                    List<Integer> indObstaculo = new ArrayList<Integer>();
                    List<Celda> reemplazarObstaculo = new ArrayList<Celda>();
                    indObstaculo.add(-1);
                    indObstaculo.add(-1);
                    indObstaculo.add(-1);
                    indObstaculo.add(2);
                    reemplazarObstaculo.add(null);
                    reemplazarObstaculo.add(null);
                    reemplazarObstaculo.add(null);
                    reemplazarObstaculo.add(new Celda('N', TipoCelda.TERRENO_B));
                    celdaEsp.setIndObstaculo(indObstaculo);
                    celdaEsp.setReemplazarObstaculo(reemplazarObstaculo);
                }

                if (esp == 2 || esp == -2) {
                    celdaEsp.setDualOpuesto(Integer.parseInt(inputStream.readLine()));
                }

                listaCeldaEsp.add(celdaEsp);
                //System.out.println(cantDestruye);
                inputStream.readLine();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        m.setListaEspecial(listaCeldaEsp);
        try {
            try {
                while ((linea = inputStream.readLine()) != null) {
                    lineasMapa0.add(linea);
                    if (linea.equals("")) {
                        break;
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return m;
    }

    private void anadirMapas() throws IOException {
        //16 de ancho, 12 de altura
        Mapa m;
        List<Objeto> obstaculos;
        ArrayList<String> lineasMapa0 = new ArrayList<String>();
        BufferedReader inputStream = null;
        obstaculos = new ArrayList<Objeto>();
        m = Leer_Mapa(obstaculos, 2, 11, 0, 0, inputStream, lineasMapa0, "/MapaTutorial.txt");
        for (int i = 5; i < 9; i++) {
            Celda aux = m.getCelda(i, 15);
            aux.setTipo(TipoCelda.TERRENO_A);
            aux.setEspecial(3);
            aux.setSprite('o');
        }

        mapas.add(m);

        // Nivel 1
        lineasMapa0 = new ArrayList<String>();
        inputStream = null;
        obstaculos = new ArrayList<Objeto>();
        m = Leer_Mapa(obstaculos, 5, 9, 15, 15, inputStream, lineasMapa0, "/MapaNivel1.txt");

        for (int i = 3; i < 7; i++) {
            Celda auxi = m.getCelda(i, 0);
            auxi.setTipo(TipoCelda.TERRENO_A);
            auxi.setEspecial(3);
        }
        for (int i = 8; i < 12; i++) {
            Celda auxi = m.getCelda(i, 0);
            auxi.setTipo(TipoCelda.TERRENO_B);
            auxi.setEspecial(3);
        }
        mapas.add(m);
        //Nivel 2
        lineasMapa0 = new ArrayList<String>();
        inputStream = null;
        obstaculos = new ArrayList<Objeto>();
        m = Leer_Mapa(obstaculos, 5, 10, 0, 0, inputStream, lineasMapa0, "/MapaNivel2.txt");

        for (int j = 12; j < 16; j++) {
            Celda aux2 = m.getCelda(6, j);
            aux2.setTipo(TipoCelda.TERRENO_A);
            aux2.setEspecial(0);
            aux2.setSprite('o');
            aux2 = m.getCelda(7, j);
            aux2.setTipo(TipoCelda.TERRENO_B);
            aux2.setEspecial(0);
            aux2.setSprite('o');
        }

        Celda aux2 = m.getCelda(6, 15);
        aux2.setEspecial(3);
        aux2 = m.getCelda(7, 15);
        aux2.setEspecial(3);

        mapas.add(m);

    }

    public GestorMapas() {
        mapas = new ArrayList<Mapa>();
        try {
            anadirMapas();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        cant = mapas.size();
        fin1 = false;
        fin2 = false;
    }

    public void cargarMapa(int index) {
        if (index > cant) {
            return;
        }
        mapaActual = mapas.get(index).copy();
        indMapaActual = index;

        jugador1 = new PersonajePrincipal(mapaActual.posX1, mapaActual.posY1);
        Celda c = mapaActual.getCelda(jugador1.getPosX(), jugador1.getPosY());
        celdaOriginal0 = c.copy();
        c.setJugador(0);
        c.setSprite('A');
        jugador2 = new PersonajePrincipal(mapaActual.posX2, mapaActual.posY2);
        c = mapaActual.getCelda(jugador2.getPosX(), jugador2.getPosY());
        celdaOriginal1 = c.copy();
        c.setJugador(1);
        c.setSprite('B');
    }

    public void realizarMovimiento(int mov) {

        Celda c, c2 = null;
        int posXAux, posYAux;
        if (mov == -1) {
            return;
        }

        if (mov < 4) {
            c = celdaOriginal0;
            posXAux = jugador1.getPosX();
            posYAux = jugador1.getPosY();
        } else {
            c = celdaOriginal1;
            posXAux = jugador2.getPosX();
            posYAux = jugador2.getPosY();
        }
        if (mov == 0) {
            c2 = mapaActual.getCelda(jugador1.getPosX() - 1, jugador1.getPosY());
        } else if (mov == 1) {
            c2 = mapaActual.getCelda(jugador1.getPosX(), jugador1.getPosY() - 1);
        } else if (mov == 2) {
            c2 = mapaActual.getCelda(jugador1.getPosX() + 1, jugador1.getPosY());
        } else if (mov == 3) {
            c2 = mapaActual.getCelda(jugador1.getPosX(), jugador1.getPosY() + 1);
        } else if (mov == 4) {
            c2 = mapaActual.getCelda(jugador2.getPosX() - 1, jugador2.getPosY());
        } else if (mov == 5) {
            c2 = mapaActual.getCelda(jugador2.getPosX(), jugador2.getPosY() - 1);
        } else if (mov == 6) {
            c2 = mapaActual.getCelda(jugador2.getPosX() + 1, jugador2.getPosY());
        } else if (mov == 7) {
            c2 = mapaActual.getCelda(jugador2.getPosX(), jugador2.getPosY() + 1);
        }
        if (c2 != null) {
            c2 = c2.copy();
        }
        boolean aux = true;
        if (mov == 0) {
            aux = jugador1.verificarPosX(1, 0, mapaActual.getAltura(), mapaActual, TipoCelda.TERRENO_A);
        } else if (mov == 1) {
            aux = jugador1.verificarPosY(1, 0, mapaActual.getAncho(), mapaActual, TipoCelda.TERRENO_A);
        } else if (mov == 2) {
            aux = jugador1.verificarPosX(0, 0, mapaActual.getAltura(), mapaActual, TipoCelda.TERRENO_A);
        } else if (mov == 3) {
            aux = jugador1.verificarPosY(0, 0, mapaActual.getAncho(), mapaActual, TipoCelda.TERRENO_A);
        } else if (mov == 4) {
            aux = jugador2.verificarPosX(1, 0, mapaActual.getAltura(), mapaActual, TipoCelda.TERRENO_B);
        } else if (mov == 5) {
            aux = jugador2.verificarPosY(1, 0, mapaActual.getAncho(), mapaActual, TipoCelda.TERRENO_B);
        } else if (mov == 6) {
            aux = jugador2.verificarPosX(0, 0, mapaActual.getAltura(), mapaActual, TipoCelda.TERRENO_B);
        } else if (mov == 7) {
            aux = jugador2.verificarPosY(0, 0, mapaActual.getAncho(), mapaActual, TipoCelda.TERRENO_B);
        }
        if (!aux) {
            return;
        }

        mapaActual.retornarCelda(posXAux, posYAux, c);

        if (mov < 4) {
            celdaOriginal0 = c2.copy();
            fin1 = false;
            if (tipoDual == 0) {
                dual = -1;
            }
            mapaActual.modificarPosJugador(0, jugador1.getPosX(), jugador1.getPosY());
        } else {
            celdaOriginal1 = c2.copy();
            fin2 = false;
            if (tipoDual == 1) {
                dual = -1;
            }
            mapaActual.modificarPosJugador(1, jugador2.getPosX(), jugador2.getPosY());
        }
    }

    public String realizarMovimientoEspecial(int mov) {
        Celda c;
        if (mov == -1) {
            return "";
        }
        if (mov < 4) {
            c = mapaActual.getCelda(jugador1.getPosX(), jugador1.getPosY());
        } else {
            c = mapaActual.getCelda(jugador2.getPosX(), jugador2.getPosY());
        }
        int especial = c.getEspecial();
        int ind = c.getIndiceEspecial();
        if (especial == 0) {
            return "";
        } else if (especial == 1) { //comando solo
            indMovimientoEspecial = 0;
            CeldaEspecial celdaEsp = mapaActual.getEspecial(ind);
            comandoActual = celdaEsp.getComandoEspecial();
            List<Integer> liberaX = celdaEsp.getLiberaX();
            List<Integer> liberaY = celdaEsp.getLiberaY();
            List<Integer> valorLiberacion = celdaEsp.getvalorLiberacion();

            for (int i = 0; i < liberaX.size(); i++) {
                Celda c2 = mapaActual.getCelda(liberaX.get(i), liberaY.get(i));
                c2.setEspecial(valorLiberacion.get(i));
            }
            if (mov < 4) {
                realizarMovimientoEspecial(4);  //se verifica si el otro personaje ya esta en un dual
            } else {
                realizarMovimientoEspecial(0);       //que se activo ahora
            }
            c.setEspecial(0);
            if (mov < 4) {
                celdaOriginal0.setEspecial(0);
            } else {
                celdaOriginal1.setEspecial(0);
            }

            return comandoActual;
        } else if (especial == 2) { //comando dual

            CeldaEspecial celdaEsp = mapaActual.getEspecial(ind);
            int dualOpuesto = celdaEsp.getDualOpuesto();
            if (dual == dualOpuesto) {
                comandoActual = celdaEsp.getComandoEspecial();
                celdaOriginal0.setEspecial(0);
                celdaOriginal1.setEspecial(0);
                c.setEspecial(0);
                celdaDual.setEspecial(0);
            } else {
                indMovimientoEspecial = 0;
                dual = ind;
                celdaDual = c;
                if (mov < 4) {
                    tipoDual = 0;
                } else {
                    tipoDual = 1;
                }
                return "";
            }
            return comandoActual;
        } else if (especial == 3) { //llego a fin de mapa
            if (mov < 4) {
                if (fin2) {
                    return "F";
                } else {
                    fin1 = true;
                }
            } else {
                if (fin1) {
                    return "F";
                } else {
                    fin2 = true;
                }
            }
        }
        return "";
    }

    public String ejecutarComando(int mov) {
        Celda c;
        Scanner scanner = new Scanner(System.in);
        if (mov < 4) {
            c = mapas.get(indMapaActual).getCelda(jugador1.getPosX(), jugador1.getPosY());
        } else {
            c = mapas.get(indMapaActual).getCelda(jugador2.getPosX(), jugador2.getPosY());
        }
        CeldaEspecial celdaEspp;
        if (indMovimientoEspecial == 0) {
            int especial = c.getEspecial();
            int ind = c.getIndiceEspecial();
            celdaEspp = mapaActual.getEspecial(ind);
            espAux = celdaEspp;
        } else {
            celdaEspp = espAux;
        }
        celdaEspp.ejecutarEspecial(mov, mapaActual, jugador1, jugador2, celdaOriginal0, celdaOriginal1, indMovimientoEspecial);
        indMovimientoEspecial += 1;
        List<Integer> aux = celdaEspp.getDireccionX();
        List<Integer> auy = celdaEspp.getDireccionY();
        if (celdaEspp.getDireccionX().size() == indMovimientoEspecial) {
            if (realizarMovimientoEspecial(0).equals("F")) {
                return "F";
            }
            if (realizarMovimientoEspecial(4).equals("F")) {
                return "F";
            }
            return "Done";
        }
        return "";
    }

    public void reiniciarVida() {
        PersonajePrincipal.reiniciarVida();
    }

    public boolean perderVida(int v) {
        return PersonajePrincipal.perderVida(v);
    }

    public Mapa getMapaActual() {
        return mapaActual;
    }
}
