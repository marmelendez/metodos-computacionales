package Lexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class Archivos {

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

    public static void generarArchivoHTML(ArrayList<Token> tokens) {
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
        String valor;
        String color;

        try {
            FileWriter escritor = new FileWriter("resaltador.html");

            escritor.write(inicioHTML);

            for (Token token : tokens) {
                valor = token.getValor();
                color = token.getColor();

                if (valor.equals("<br>")) {
                    escritor.write(valor + "\n");
                } else if (valor.equals("<span>&nbsp<span>")) {
                    escritor.write(valor);
                } else if (color.equals("purple")) {
                    escritor.write("<span style=\"color:" + color + "; text-decoration: underline\">" + valor  + "</span>");
                } else {
                    escritor.write("<span style=\"color:" + color + "\">" + valor  + "</span>");
                }
            }

            escritor.write(finalHTML);
            escritor.close();
        } catch (IOException e) {
            System.out.println("Error, no se pudo generar archivo html");
            e.printStackTrace();
        }
    }   
}
