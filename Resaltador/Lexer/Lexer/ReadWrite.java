package Lexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class ReadWrite {
    public static Vector<String> leerArchivo(String nombreArchivo){
        Vector<String> texto = new Vector<>();
        try {
            File archivo = new File(nombreArchivo);
            Scanner scan = new Scanner(archivo);
            while (scan.hasNextLine()) { //Mientras exista una línea siguiente
                texto.add(scan.nextLine()); //Guarda la línea como string en el vector
            }
            scan.close();
        }

        catch (FileNotFoundException e) { //Si recibe la excepción FileNotFoundException le informa al usuario
            System.out.println("Error, no se encontro el archivo con ese nombre");
            e.printStackTrace();
        }
        return texto;
    }

    public static void escribirArchivoHTML(ArrayList<Token> tokens){
        try {
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
                    font-size: 24px;
                }
                </style>
            </head>
            <body>""";
            String finalHTML = "\n</body>\n</html>";
            FileWriter escritor = new FileWriter("resaltador.html");
            escritor.write(inicioHTML);
            for (Token token: tokens){
                if (token.getValor().equals("<br>")){
                    escritor.write(token.getValor());
                } else if(token.getColor().equals("purple")){
                    escritor.write("\n\t<span style=\"color: "+ token.getColor() +"; text-decoration: underline\">" + token.getValor()  + "</span>");
                } else{
                    escritor.write("\n\t<span style=\"color:"+ token.getColor() +"\">" + token.getValor()  + "</span>");
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
