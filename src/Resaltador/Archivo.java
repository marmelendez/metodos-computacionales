/**
 * <h1>Leer y generar archivos</h1>
 * El programa Archivos cuenta con dos funciones: La primera lee y guarda un
 * archivo de texto (.txt) La segunda genera un archivo html (.html)
 *
 * @author Lizbeth Maribel Melendez Delgado
 * @author Gerardo Novelo de Anda
 * @version 1.0, 25/04/21
 */
package Resaltador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class Archivo {

    /**
     * Esta función lee un archivo de texto y lo guarda en un vector de strings
     *
     * @param nombreArchivo Representa el nombre del archivo a leer, si hay
     * algún error en el nombre muestra una excepción
     * @return Vector<String> Devuelve un vector que contiene cada línea del
     * archivo de texto, si no puede leer un archivo devuelve un vector vacío.
     */
    public static Vector<String> leerArchivoTXT(String nombreArchivo) {
        Vector<String> texto = new Vector<>();

        try {
            File archivo = new File("./Archivos/" + nombreArchivo);
            Scanner scan = new Scanner(archivo);

            while (scan.hasNextLine()) {
                texto.add(scan.nextLine());
            }

            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error, no se encontro el archivo con ese nombre");
        }

        return texto;
    }

    /**
     * Esta función guarda los estilos de cada token que se encuentran en una
     * lista en un archivo html
     *
     * @param tokens Lista de tokens, si esta vacía solo genera un archivo.html
     * con la estructura principal
     * @param archivo
     */
    public static void generarArchivoHTML(List<Token> tokens, String archivo) {
        /*Obtener nombre del archivo*/
        String[] archivoSplit = archivo.split("\\.");
        String nombreArchivo = archivoSplit[0];
        
        /* Estructura principal de un html */
        String inicioHTML = "<!DOCTYPE html> \n<html lang= \"en\"> /<head> \n<meta charset=\"UTF-8\">" + 
                "\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"> \n<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" + 
                "\n<title>Resaltador lexico</title> \n<style> \nbody { \nfont-family: Courier New, Arial; \nfont-size: 18px;" + 
                "\n } \n</style> \n</head> \n<body>";
        String finalHTML = "\n</body>\n</html>";

        try {
            FileWriter escritor = new FileWriter(nombreArchivo + ".html");

            escritor.write(inicioHTML);

            for (Token token : tokens) { //Obtiene el estilo de cada token generado para guardarlo en el archivo .html
                escritor.write(token.getEstilo());
            }

            escritor.write(finalHTML);
            escritor.close();
        } catch (IOException e) {
            System.out.println("Error, no se pudo generar archivo html");
            e.printStackTrace();
        }

    }
}
