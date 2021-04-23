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

    private static String defineColor(Tipo tipoToken) {
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
        ArrayList<Integer> delimitador = new ArrayList<>(Arrays.asList(39,40,41,42,43,45,47,59,61,94));

        if (type == 1) {
            delimitador = new ArrayList<>(Arrays.asList(39,40,41,42,43,47,59,61,94));
        }

        int codigo = linea.codePointAt(i);
        return delimitador.contains(codigo);
    }

    private static boolean matches(String palabra, StringTokenizer str) {
        boolean matched = false;
        
        for (Tipo tokenTipo : Tipo.values()) {
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

                    if (matcher.find()) {
                        tok.setTipo(Tipo.PALABRA_RESERVADA);
                    }
                    if (!tokens.isEmpty()) {
                        Token prevToken = tokens.get(tokens.size() - 1);

                        if (prevToken.getTipo() == Tipo.COMENTARIO) {
                            tok.setTipo(Tipo.COMENTARIO);
                        }
                    }
                } else if (tokenTipo == Tipo.COMENTARIO) {
                    while (!(palabra.equals("\n")) && str.hasMoreTokens()) { //str.hasMoreTokens()
                        palabra = str.nextToken();
                        tok.setValor(tok.getValor() + " " + palabra);
                    }
                } 
                
                tok.setColor(defineColor(tok.getTipo()));
                agregarEspacios(palabra);
                tokens.add(tok);
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
            
            if (lexema.equals("#")) {
                i++;
                while (i < palabra.length() && !esDelimitador(i, palabra, 0)) {
                    lexema += String.valueOf(palabra.charAt(i));
                    i++; 
                }
                input += lexema + " ";
            } else {
                boolean matched = false;

                for (Tipo tokenTipo : Tipo.values()) {
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

                            while (i < palabra.length()) {
                                lexema += String.valueOf(palabra.charAt(i));
                                i++;
                            }
                        } else if (tokenTipo == Tipo.VARIABLE) {
                            i++;

                            while (i < palabra.length() && !esDelimitador(i, palabra, 0)) {
                                lexema += String.valueOf(palabra.charAt(i));
                                i++; 
                            }
                        } else if (tokenTipo == Tipo.NUMERO) {
                            i++;

                            while (i < palabra.length() && !esDelimitador(i, palabra, 1)) {
                                lexema += String.valueOf(palabra.charAt(i));
                                i++; 
                            }
                        }
                        break;
                    }
                } if (!matched) {
                    i++;

                    while (i < palabra.length() && !esDelimitador(i, palabra, 0)) {
                        lexema += String.valueOf(palabra.charAt(i));
                        i++; 
                    }
                }
                input += lexema + " ";
            }
        }
        return input;
    } 

    private static void agregarEspacios(String palabra) {
        int index = lineaTexto.indexOf(palabra);
        Token tok = new Token();
        tok.setValor("<span>&nbsp<span>");

        System.out.println(palabra);

        for ( int i = index - 1 ; i >= 0; i--) {
           System.out.println("char en index  " + lineaTexto.charAt(i));
            if (lineaTexto.charAt(i) == ' ') {
                tokens.add(tok);
            } else {
                break;
            }
        }
    }

    private static void lexer(String input, boolean nvoInput) {
        StringTokenizer str = new StringTokenizer(input);

        while (str.hasMoreTokens()) {
            String palabra = str.nextToken();
            boolean matched = false;

            matched = matches(palabra, str);
            
            if (!matched) {
                if (!nvoInput) {
                    String inputNvo = obtenerTokens(palabra);
                    
                    lexer(inputNvo, true);
                } else {
                    Token tok = new Token();

                    tok.setColor("purple");
                    tok.setValor(palabra);

                    agregarEspacios(palabra);
                    tokens.add(tok);
                    nvoInput = false;
                }
            }
        }
    }

    private static void imprimirTokens(){
        Tipo tipo;
        String valor;

        for (Token token: tokens) {
            tipo = token.getTipo();
            valor = token.getValor();

            if (!(valor.equals("<br>")) && !(valor.equals("<span>&nbsp<span>"))) {
                if (tipo == null){
                    System.out.printf("%-20s %-20s %n", "ERROR", valor);
                } else {
                    System.out.printf("%-20s %-20s %n", tipo, valor);
                }
            }
        }
    }

    private static void lexerAritmetico(String archivo) {
        Vector<String> texto = Archivos.leerArchivoTXT(archivo);
        Token saltoLinea = new Token();

        for (String linea : texto) {
            lineaTexto = linea;
            System.out.println(linea);
            lexer(linea, false);
            saltoLinea.setValor("<br>");
            tokens.add(saltoLinea);
        }

        imprimirTokens();
        Archivos.generarArchivoHTML(tokens);
    }

    public static void main(String[] args) {
        String nombreArchivo = "expresiones.txt";

        lexerAritmetico(nombreArchivo);
    }   
}