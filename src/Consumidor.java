import Resaltador.Lexer;
import Resaltador.Token;
import java.util.ArrayList;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alejandro.degante
 */


public class Consumidor extends Thread {
    int fileName;
    Almacen almacen;
    boolean bandera;
    int numArchivos;
    
    Consumidor(int fileName, Almacen almacen, int numArchivos) {
        this.fileName = fileName;
        this.almacen = almacen;
        this.bandera = true;
        this.numArchivos = numArchivos;
    }
    
    @Override
    public void run() {
        String producto;
        
        for (int i = 0; i < this.numArchivos; i++) {
            producto = this.almacen.consumir();
            if (!"".equals(producto)){
                System.out.println("Consume (" + this.fileName + "): " + producto);
                
                Lexer lx = new Lexer(new ArrayList<Token>());
                lx.lexerAritmetico(producto);
            }
        }
    }
    
    public void detener() {
        this.bandera = false;
    }
}

