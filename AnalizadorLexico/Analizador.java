package AnalizadorLexico;

import java.util.Vector;
import java.io.File; 
import java.io.FileNotFoundException;  
import java.util.Scanner; 


public class Analizador {
    //ATRIBUTOS
    int alfabeto; //Columnas de la tabla: representa simbolos
    int estado; //Filas de la tabla: representa estado actual
    boolean flag; //Control de impresiones de tokens y evalución con estado inicial
    char[] charArr; //Arreglo de chars de cada línea de entrada
    Token token = new Token(); //Token: contiene tipo y valor del token
    Vector<String> texto = new Vector<>(); //Vector que almacena cada línea del archivo de entrada .txt
    int[][] tabla = { //Tabla de transición (vease referencia )
        {1, 10, -1, 2, 3, 4, 5, 6, 7, 8, 9, 1, -1},
        {1, 1, 1, -1, -1, -1, -1, -1, -1, -1, -1, 1, -1},
        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1, 13, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
        {-1, 10, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 11},
        {-1, 11, -1, -1, -1, -1, -1, -1, -1, -1, -1, 14, -1},
        {-1, 12, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
        {13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13},
        {-1, 12, -1, -1, -1, 15, -1, -1, -1, -1, -1, -1, -1},
        {-1, 12, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
    };

    //METODOS

    /* Lee un nombreArchivo que representa la ruta donde se encuentra el archivo
    Guarda contenido del archivo en un vector<String>
    Si no puede abrirlo manda una excepción */
    private void leerArchivo(String nombreArchivo){
        try {
            File archivo = new File(nombreArchivo);
            Scanner scan = new Scanner(archivo);
            while (scan.hasNextLine()) { //Mientras exista una línea siguiente
                this.texto.add(scan.nextLine()); //Guarda la línea como string en el vector
            }
            scan.close();
        }

        catch (FileNotFoundException e) { //Si recibe la excepción FileNotFoundException le informa al usuario
            System.out.println("Error, no se encontro el archivo con ese nombre");
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Analizador a = new Analizador();
        a.leerArchivo("expresiones.txt");
        
    }
}