/**
* <h1>Leer y generar archivos</h1>
* El programa Archivos cuenta con dos funciones:
* La primera lee y guarda un archivo de texto (.txt)
* La segunda genera un archivo html (.html)
*
* @author  Lizbeth Maribel Melendez Delgado 
* @author  Gerardo Novelo de Anda
* @version 1.0, 25/04/21
*/

package Lexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class Archivo {

    /**
    * Esta funcion lee un archivo de texto y lo guarda en un vector de strings
    * @param nombreArchivo  String que representa el nombre del archivo a leer
    *                       si hay algun error en el nombre muestra una excepción
    * @return Vector<String> Devuelve un vector que contiene cada linea del archivo de texto,
    *                       si no puede leer un archivo devuelve un vector vacio.
    */
    public static Vector<String> leerArchivoTXT(String nombreArchivo) {
        Vector<String> texto = new Vector<>();

        try {
            File archivo = new File(nombreArchivo);
            Scanner scan = new Scanner(archivo);

            while (scan.hasNextLine()) {
                texto.add(scan.nextLine()); 
            } 

            scan.close();
        } catch (FileNotFoundException e) { 
            System.out.println("Error, no se encontro el archivo con ese nombre");
            e.printStackTrace();
        }

        return texto;
    }

    /**
    * Esta funcion guarda los estilos de cada token que se encuentran en una lista en un archivo html
    * @param tokens Lista de tokens, si esta vacia solo genera un archivo.html con la estructura principal de un HTML
    * @return void  Devuelve nada
    */
    public static void generarArchivoHTML(List<Token> tokens) {
        String inicioHTML = """
            <!DOCTYPE html>
            <html lang= "en">
            <head> 
                <meta charset="UTF-8">
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Resaltador lexico</title>
                <style> 
                body {
                    font-family: Courier New, Arial;
                    font-size: 18px;
                }
                </style>
            </head>
            <body>
            """;
        String finalHTML = "\n</body>\n</html>";

        try {
            FileWriter escritor = new FileWriter("resaltador.html");

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
