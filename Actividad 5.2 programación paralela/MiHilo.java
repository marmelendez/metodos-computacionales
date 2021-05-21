/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lizbethmelendez
 */
public class MiHilo extends Thread {
    int id;
    int inicio;
    int fin;
    boolean bandera;
    private long resultado;
    
    MiHilo(int id, int inicio, int fin, boolean bandera) {
        this.id = id;
        this.inicio = inicio;
        this.fin = fin;
        this.bandera = bandera;
    }
    
    @Override
    public void run() {
        long suma = 0;
       
        for(int i = this.inicio ; i < this.fin && this.bandera; i++) {
            if (esNumeroPrimo(i)){
                suma += i;
            }
        }
        this.resultado = suma;
    }
    
    public boolean esNumeroPrimo(int n) {
        if (n < 2){
            return false;
        } else {
            for (int i = 2; i <= Math.sqrt(n); i++){
                if (n % i == 0){
                    return false;
                }
            }
        }
        return true;
    }
    
    public long getResultado() {
        return this.resultado;
    }
}
