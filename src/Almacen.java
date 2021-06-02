
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alejandro.degante
 */
public class Almacen {
    private final List<String> espacio;
    
    Almacen() {
        this.espacio = new ArrayList<>();
    }
    
    synchronized String consumir() { 
        while (this.espacio.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Almacen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.espacio.remove(0);
    }
    
    synchronized void producir(String producto) {
        this.espacio.add(producto);
        notifyAll();
    }
    
    int getNumeroArchivos(){
        return this.espacio.size();
    }
}
