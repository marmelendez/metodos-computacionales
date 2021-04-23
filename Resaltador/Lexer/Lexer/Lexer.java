/** 
*
*/

package Lexer;

import java.util.Vector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Lexer.Token.Tipo;

public class Lexer {
    static ArrayList<Token> tokens = new ArrayList<>();
    static String lineaTexto;

    /*
    *
    */
    private static String definirColor(Tipo tipoToken) {
        if (tipoToken.equals(Tipo.NUMERO)  
            || tipoToken.equals(Tipo.NUMERO_EXPONENCIAL)) {
            return "skyblue";
        } else if (tipoToken.equals(Tipo.LOGICO)) {
            return "darkgoldenrod";
        } else if (tipoToken.equals(Tipo.SIMBOLO)) {
            return "forestgreen";
        } else if (tipoToken.equals(Tipo.OPERADOR)) {
            return "red";
        } else if (tipoToken.equals(Tipo.VARIABLE)) {
            return "gray";
        } else if (tipoToken.equals(Tipo.ESPECIAL)) {
            return "coral";
        } else if (tipoToken.equals(Tipo.COMENTARIO)) {
            return "plum";
        } 
        return "saddlebrown";
    }

    private static boolean esDelimitador(int i, String linea, int type) {
        ArrayList<Integer> delimitador = new ArrayList<>(Arrays.asList(39,40,41,42,43,45,47,59,61,94)); //Arreglo de numeros que representa el codigo ASCII de los simbolos delimitadores

        if (type == 1) { //Tipo 1 es representa los numeros
            delimitador = new ArrayList<>(Arrays.asList(39,40,41,42,43,47,59,61,94));
        }

        int codigo = linea.codePointAt(i);
        return delimitador.contains(codigo); //evalua si el codigo ASCII del simbolo en indice i de linea es delimitador
    }

    private static boolean matchesPatron(String palabra, StringTokenizer str) {
        boolean matched = false;
        
        for (Tipo tokenTipo : Tipo.values()) { //Se realiza un match con todos los patrones declarados en enum Tipo
            Pattern patron = Pattern.compile(tokenTipo.patron);
            Matcher matcher = patron.matcher(palabra);
            
            if (matcher.find()) {
                Token tok = new Token();
                tok.setTipo(tokenTipo);
                tok.setValor(palabra);
                matched = true;

                if (tokenTipo == Tipo.VARIABLE) {
                    patron = Pattern.compile(Tipo.PALABRA_RESERVADA.patron);
                    matcher = patron.matcher(palabra);

                    if (matcher.find()) { //Si la variable es palabra reservada cambia el Tipo
                        tok.setTipo(Tipo.PALABRA_RESERVADA);
                    }

                    if (!tokens.isEmpty()) { 
                        Token prevToken = tokens.get(tokens.size() - 1);

                        if (prevToken.getTipo() == Tipo.COMENTARIO) { //Verifica que el token previo sea un comentario
                            tok.setTipo(Tipo.COMENTARIO); // Si lo es cambia el Tipo
                        }
                    }
                } else if (tokenTipo == Tipo.COMENTARIO) {
                    while (!(palabra.equals("\n")) && str.hasMoreTokens()) { //Guarda todos los tokens hasta llegar al final de la linea
                        palabra = str.nextToken();
                        tok.setValor(tok.getValor() + " " + palabra);
                    }
                } 
                
                tok.setColor(definirColor(tok.getTipo()));
                tok.setEstilo("<span style=\"color:" + tok.getColor() + "\">" + tok.getValor()  + "</span>");
                agregarEspacios(palabra); //Checa si no hay espacio(s) antes del token
                tokens.add(tok); //Agrega el nuevo token al arreglo 
                break;
            }
        }
        return matched;
    }

    private static String obtenerTokens(String palabra) {
        String input = "";
        String lexema;
        int i = 0;

        while (i < palabra.length()) {
            lexema = String.valueOf(palabra.charAt(i)); 
            
            if (lexema.equals("#")) { //Evalua si es de Tipo LOGICO
                i++;

                while (i < palabra.length() && !esDelimitador(i, palabra, 0)) { //Añade el siguiente caracter en lexema hasta encontrar un delimitador
                    lexema += String.valueOf(palabra.charAt(i));
                    i++; 
                }

                input += lexema + " ";
            } else {
                boolean matched = false;

                for (Tipo tokenTipo : Tipo.values()) { //Se realiza un match con todos los patrones declarados en enum Tipo
                    lexema = String.valueOf(palabra.charAt(i));
                    Pattern patron = Pattern.compile(tokenTipo.patron);
                    Matcher matcher = patron.matcher(lexema);

                    if (matcher.find()) {
                        matched = true;

                        if (tokenTipo == Tipo.OPERADOR 
                            || tokenTipo == Tipo.ESPECIAL) { 
                            i++;
                        } else if (tokenTipo == Tipo.SIMBOLO 
                            || tokenTipo == Tipo.COMENTARIO) {
                            i++;

                            while (i < palabra.length()) { //Añade cada caracter a lexema hasta llegar al final de la palabra
                                lexema += String.valueOf(palabra.charAt(i));
                                i++;
                            }
                        } else if (tokenTipo == Tipo.VARIABLE) {
                            i++;

                            while (i < palabra.length() && !esDelimitador(i, palabra, 0)) { //Añade cada caracter a lexema hasta llegar a un delimitador o al final de la palabra
                                lexema += String.valueOf(palabra.charAt(i));
                                i++; 
                            }
                        } else if (tokenTipo == Tipo.NUMERO) {
                            i++;

                            while (i < palabra.length() && !esDelimitador(i, palabra, 1)) { //Añade cada caracter a lexema hasta llegar a un delimitador o al final de la palabra
                                lexema += String.valueOf(palabra.charAt(i));
                                i++; 
                            }
                        }
                        break;
                    }
                } if (!matched) { 
                    i++;

                    while (i < palabra.length() && !esDelimitador(i, palabra, 0)) { //Añade cada caracter a lexema hasta llegar a un delimitador o al final de la palabra
                        lexema += String.valueOf(palabra.charAt(i));
                        i++; 
                    }
                }
                input += lexema + " "; //Agrega el lexema que se genero al nuevo input que evaluara la funcion matchesPatron
            }
        }
        return input;
    } 

    private static void agregarEspacios(String palabra) {
        int index = lineaTexto.indexOf(palabra);
        Token tok = new Token();
        tok.setValor("espacio");
        tok.setEstilo("<span>&nbsp<span>");

        for (int i = index - 1 ; i >= 0; i--) { //Evalua cada caracter de una linea del texto a partir del anterior a donde inicia la palabra

            if (lineaTexto.charAt(i) == ' ') { 
                tokens.add(tok);
            } else { //Si encuentra un caracter que no se espacio se termina el ciclo
                break;
            }

        }
    }

    private static void lexer(String input, boolean nvoInput) {
        StringTokenizer str = new StringTokenizer(input);

        while (str.hasMoreTokens()) { 
            String palabra = str.nextToken();
            boolean matched = false;

            matched = matchesPatron(palabra, str);
            
            if (!matched) {
                if (!nvoInput) { //Si no ha generado un nuevo input con la palabra que no hizo match
                    String inputNvo = obtenerTokens(palabra); //Genera ese input con posibles nuevos tokens
                    
                    lexer(inputNvo, true); //Vuelve a llamar a esta misma función
                } else { //Si ya se evaluo el nuevo input
                    /* se añade token de ERROR */
                    Token tok = new Token();
                    
                    tok.setValor(palabra);
                    tok.setColor("purple");
                    tok.setEstilo("<span style=\"color:" + tok.getColor() + "; text-decoration: underline\">" + tok.getValor()  + "</span>");
                    agregarEspacios(palabra); //Checa si no hay espacio(s) antes del token
                    tokens.add(tok); //Agrega el token al arreglo
                    nvoInput = false; 
                }
            }
        }
    }

    private static void imprimirTokens() {
        Tipo tipo;
        String valor;

        for (Token token: tokens) {
            tipo = token.getTipo();
            valor = token.getValor();

            if (!(valor.equals("salto")) && !(valor.equals("espacio"))) { //Si es un salto o espacio no corresponde en si a un token por lo tanto no se imprime
                if (tipo == null) { 
                    System.out.printf("%-20s %-20s %n", "ERROR", valor);
                } else {
                    System.out.printf("%-20s %-20s %n", tipo, valor);
                }
            }
        }
    }

    private static void lexerAritmetico(String archivo) {
        Vector<String> texto = Archivo.leerArchivoTXT(archivo); //Lee archivo .txt
        /* se crea token de salto de linea */
        Token saltoLinea = new Token();
        saltoLinea.setValor("salto");
        saltoLinea.setEstilo("<br> \n");

        for (String linea : texto) { //Por cada string del vector texto llama a funcion lexer y agrega salto de linea
            lineaTexto = linea;
            lexer(linea, false); 
            tokens.add(saltoLinea);
        } 

        imprimirTokens();
        Archivo.generarArchivoHTML(tokens); //Genera archivo .html
    }

    public static void main(String[] args) {
        String nombreArchivo = "expresiones.txt";

        lexerAritmetico(nombreArchivo);
    }   
}