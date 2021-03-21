package AnalizadorLexico;

import java.util.Vector;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class AnalizadorLexico {
    Vector<String> texto = new Vector<>();
    int[][] tabla = {
        {1, 11, -1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1},
        {1, 1, 1, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16},
        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
        {17, 17, 17, 17, 17, 17, 17, 10, 17, 17, 17, 17, 17},
        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
        {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
        {18, 11, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 12},
        {19, 12, 19, 19, 19, 19, 19, 19, 19, 19, 19, 13, 19},
        {-1, 14, -1, -1, -1, 15, -1, -1, -1, -1, -1, -1, -1},
        {19, 14, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19},
        {-1, 14, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
    };
    
    
    private void leerArchivo(String nombreArchivo){
        try {
            File archivo = new File(nombreArchivo);
            Scanner scan = new Scanner(archivo);
            while (scan.hasNextLine()) {
                this.texto.add(scan.nextLine());
            }
            scan.close();
        }

        catch (FileNotFoundException e) {
            System.out.println("Error, no se encontro el archivo con ese nombre");
            e.printStackTrace();
        }
    }

    private int indiceAlfabeto(char simbolo){
        int indice = -1;
        if (Character.isLetter(simbolo)){
            indice = 0;
        } else if (Character.isDigit(simbolo)){
            indice = 1;
        } else if (simbolo == '_'){
            indice = 2;
        } else if (simbolo == '='){
            indice = 3;
        } else if (simbolo == '+'){
            indice = 4;
        } else if (simbolo == '-'){
            indice = 5;
        } else if (simbolo == '*'){
            indice = 6;
        } else if (simbolo == '/'){
            indice = 7;
        } else if (simbolo == '^'){
            indice = 8;
        } else if (simbolo == '('){
            indice = 9;
        } else if (simbolo == ')'){
            indice = 10;
        } else if (simbolo == 'E' || simbolo == 'e'){
            indice = 11;
        } else if (simbolo == '.'){
            indice = 12;
        }
        return indice;
    }

    private Boolean esFinal(int estadoActual){
        if ((estadoActual>=1 && estadoActual<=12) || estadoActual==15){
            return true;
        } 
        return false;
    } 

    private String tipo(int estadoActual){
        switch (estadoActual){
            case 1: 
                return "Variable";
            case 2:
                return "Asignación";
            case 3: 
                return "Suma";
            case 4:
                return "Resta";
            case 5:
                return "Multiplicación";
            case 6:
                return "División";
            case 7:
                return "Potencia";
            case 8:
                return "Paréntesis que abre";
            case 9:
                return "Paréntesis que cierra";
            case 10:
                return "Comentario";
            case 11:
                return "Entero";
            case 12:
                return "Real";
            case 15:
                return "Real";
            default:
                return "error";
        }
    }

    private void identificarToken(int estadoActual, int alfabeto){
        estadoActual = this.tabla[estadoActual][alfabeto];
        if(esFinal(estadoActual)){
            System.out.println(tipo(estadoActual));
        }
    }



    public void lexerAritmetico(String archivo){
        leerArchivo(archivo);
        String linea;
        char[] charArr;
        int estadoActual = 0;
        int alfabeto;
        for (int i=0; i< this.texto.size();i++){
            linea = this.texto.get(i);
            charArr= linea.toCharArray();
            for (int j=0; j<charArr.length;j++){
                alfabeto = indiceAlfabeto(charArr[j]);
                if (alfabeto >= 0){
                    identificarToken(estadoActual, alfabeto);
                }
            }
        }
    }

    public static void main(String[] args) {
        AnalizadorLexico a = new AnalizadorLexico();
        a.lexerAritmetico("expresiones.txt");
    }
} 
