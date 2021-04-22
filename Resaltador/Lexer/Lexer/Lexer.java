package Lexer;

import java.io.File; 
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner; 
import java.util.Vector;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Lexer.Token.Tipo;


public class Lexer {
    static ArrayList<Token> tokens = new ArrayList<>();

    private static Vector<String> leerArchivo(String nombreArchivo){
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

    private static void escribirArchivoHTML(){
        try {
            String inicioHTML = """
            <!DOCTYPE html>
            <html lang= "en">
            <head> 
                <meta charset="UTF-8">
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Resaltador lexico</title>
            </head>
            <body>
            """;
            String finalHTML = "\n</body>\n</html>";
            FileWriter escritor = new FileWriter("archivoResaltador.html");
            escritor.write(inicioHTML);
            for (Token token: tokens){
                escritor.write("\t<span style=\"color:"+ token.getColor() +"\">" + token.getValor()  + "</span>\n");
            }
            escritor.write(finalHTML);
            escritor.close();
        } catch (IOException e) {
            System.out.println("Error, no se pudo generar archivo html");
            e.printStackTrace();
        }
    }

    private static String definirColorStyle(Tipo tipoToken){
        if(tipoToken.equals(Tipo.NUMERO) || tipoToken.equals(Tipo.NUMERO_EXPONENCIAL)){
            return "skyblue";
        } else if (tipoToken.equals(Tipo.LOGICO)){
            return "darkgoldenrod";
        } else if (tipoToken.equals(Tipo.SIMBOLO)){
            return "forestgreen";
        } else if (tipoToken.equals(Tipo.OPERADOR)){
            return "red";
        } else if (tipoToken.equals(Tipo.VARIABLE)){
            return "gray";
        } else if (tipoToken.equals(Tipo.ESPECIAL)){
            return "coral";
        } else if (tipoToken.equals(Tipo.COMENTARIO)){
            return "plum";
        } else if (tipoToken.equals(Tipo.PALABRA_RESERVADA)){
            return "saddlebrown";
        }
        return "purple";
    }

    private static boolean matches(String palabra, StringTokenizer st){
        boolean matched = false;
        for (Tipo tokenTipo : Tipo.values()) {
            Pattern patron = Pattern.compile(tokenTipo.patron);
            Matcher matcher = patron.matcher(palabra);
            if(matcher.find()) {
                Token tk = new Token();
                tk.setTipo(tokenTipo);
                tk.setValor(palabra);
                matched = true;
                if (tokenTipo == Tipo.VARIABLE){
                    patron = Pattern.compile(Tipo.PALABRA_RESERVADA.patron);
                    matcher = patron.matcher(palabra);
                    if(matcher.find()){
                        tk.setTipo(Tipo.PALABRA_RESERVADA);
                    } 
                } else if(tokenTipo == Tipo.COMENTARIO) {
                    while(!(palabra.equals("\n")) && st.hasMoreTokens()){
                        palabra = st.nextToken();
                        tk.setValor(tk.getValor() + " " + palabra);
                    }
                    
                } 
                tk.setColor(definirColorStyle(tk.getTipo()));
                tokens.add(tk);
                break;
            }
        }
        return matched;
    }

    /*separar una linea de texto pegada por los diferentes tipos de tokens ahora separada por espacios */
    private static String split(String linea){
        String nvaLinea = "";
        // String lexema = "";
        // int estado = 0;
        int i=0;
        while (i<linea.length()){
            /*lexema = linea.charAt(i);
            //identificar tipos de lexema
            linea.codePointAt(i) ascii


            nvaLinea += lexema + " ";*/
            i++;
        }
        return nvaLinea;
    }


    //agregar un salto de linea al final de cada input y en escribirHTML identificar si \n y agregar br antes 
    private static void lexer(String input) {
        final StringTokenizer st = new StringTokenizer(input);

        while(st.hasMoreTokens()) {
            String palabra = st.nextToken();
            boolean matched = false;

            matched = matches(palabra, st);

            if (!matched) {
                //checar si el error contiene tokens validos
                String nvoInput = split(palabra);
                System.out.println(nvoInput);
                lexer(nvoInput);
                //System.out.printf("%-20s %-20s %n", "ERROR", palabra);
            }
        }
    }

    private static void lexerAritmetico(String archivo){
        Vector<String> texto = leerArchivo(archivo);
        for(String linea: texto){
            lexer(linea);
        }

        for (Token token: tokens){
            System.out.printf("%-20s %-20s %n", token.getTipo(), token.getValor());
        }
    }

    public static void main(String[] args) {
        String nombreArchivo = "expresiones.txt";
        lexerAritmetico(nombreArchivo);
        escribirArchivoHTML();
    }   
}
