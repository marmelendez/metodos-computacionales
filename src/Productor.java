
import java.io.File;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alejandro.degante
 */
public class Productor extends Thread{
    int id;
    Almacen almacen;
    boolean bandera;
    
    Productor(int id, Almacen almacen) {
        this.id = id;
        this.almacen = almacen;
        this.bandera = true;
    }
    
    void getArchivos() {
        File directorio = new File("./Archivos");
        File[] archivos = directorio.listFiles();
                
        for(File f : archivos) {
            String nombre = f.getName();
            if (nombre.contains(".txt")){
                this.almacen.producir(nombre);
            }
        }
    }
   
    
    @Override
    public void run() {
        System.out.println("Productor " + this.id);
        getArchivos();
    }
    
    public void detener() {
        this.bandera = false;
    }
}
