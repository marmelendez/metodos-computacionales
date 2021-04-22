package Lexer;

import java.util.Vector;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Lexer.Token.Tipo;


public class Lexer {
    static ArrayList<Token> tokens = new ArrayList<>();

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
        } 
        return "saddlebrown";
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

    private static boolean esDelimitador(int i, String linea){
        int code = linea.codePointAt(i);
        return (code >= 39 && code <= 43) || code == 45 || code == 47 || code == 59 || code == 61 || code == 94;
    }
    /*separar una linea de texto pegada por los diferentes tipos de tokens ahora separada por espacios */
    private static String split(String linea){
        String nvaLinea = "";
        String lexema;
        int i=0;
        while (i<linea.length()){
            lexema = String.valueOf(linea.charAt(i)); 
            if (lexema.equals("#")){
                i++;
                while (i<linea.length() && !esDelimitador(i, linea)){
                    lexema += String.valueOf(linea.charAt(i));
                    i++; 
                }
                nvaLinea += lexema + " ";
            }else {
                for (Tipo tokenTipo : Tipo.values()) {
                    Pattern patron = Pattern.compile(tokenTipo.patron);
                    Matcher matcher = patron.matcher(lexema);
                    if(matcher.find()) {
                        if (tokenTipo == Tipo.OPERADOR || tokenTipo == Tipo.ESPECIAL){
                            nvaLinea += lexema + " ";
                        } else if (tokenTipo == Tipo.SIMBOLO || tokenTipo == Tipo.COMENTARIO){
                            i++;
                            while (i<linea.length()){
                                lexema += String.valueOf(linea.charAt(i));
                                i++;
                            }
                            nvaLinea += lexema + " ";
                        } else if (tokenTipo == Tipo.VARIABLE){
                            i++;
                            while (i<linea.length() && !esDelimitador(i, linea)){
                                lexema += String.valueOf(linea.charAt(i));
                                i++; 
                            }
                            nvaLinea += lexema + " ";
                        }
                    } 
                }
                i++;
            }
            //identificar tipos de lexema
            // linea.codePointAt(i) ascii
        }
        return nvaLinea;
    } 


    //agregar un salto de linea al final de cada input y en escribirHTML identificar si \n y agregar br antes 
    private static void lexer(String input, boolean alreadySplit) {
        final StringTokenizer st = new StringTokenizer(input);

        while(st.hasMoreTokens()) {
            String palabra = st.nextToken();
            boolean matched = false;

            matched = matches(palabra, st);

            if (!matched) {
                if (!alreadySplit){
                    String nvoInput = split(palabra);
                    System.out.println(nvoInput);
                    lexer(nvoInput, true);
                } else {
                    Token tk = new Token();
                    tk.setColor("purple");
                    tk.setValor(palabra);
                    tokens.add(tk);
                    alreadySplit = false;
                }

                //split false
                // split
                //mandarlo a chechar lexer

                //split true (ya se divio previamente)
                // guardar tokens

                // Token tk = new Token();
                // tk.setColor("purple");
                // tk.setValor(palabra);
                // tokens.add(tk);
                //checar si el error contiene tokens validos si true evaluar tokens, si no mostrar error
                // String nvoInput = split(palabra);
                // System.out.println(nvoInput);
                // lexer(nvoInput);
            }
        }
    }

    private static void imprimirTokens(){
        for (Token token: tokens){
            System.out.printf("%-20s %-20s %n", token.getTipo(), token.getValor());
        }
    }

    private static void lexerAritmetico(String archivo){
        Vector<String> texto = ReadWrite.leerArchivo(archivo);

        for(String linea: texto){
            lexer(linea, false);
            Token saltoLinea = new Token();
            saltoLinea.setValor("<br>");
            tokens.add(saltoLinea);
        }

        //imprimirTokens();
    }

    public static void main(String[] args) {
        String nombreArchivo = "expresiones.txt";
        lexerAritmetico(nombreArchivo);
        ReadWrite.escribirArchivoHTML(tokens);
    }   
}
