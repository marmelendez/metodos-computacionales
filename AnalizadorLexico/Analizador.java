package AnalizadorLexico;

import java.util.Vector;

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

    public static void main(String[] args) {
        
    }
}