/**
* <h1>Resaltador de sintáxis</h1>
* Este programa identifica los tipos de token que contiene un archivo 
* de texto, imprime el valor y tipo de cada token identificado y genera 
* un archivo html con cada tipo de token identificado por un color
*
* @author  Lizbeth Maribel Melendez Delgado 
* @author  Gerardo Novelo de Anda
* @version 1.0, 25/04/21
*/

package Resaltador;

import java.util.Vector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Resaltador.Token.Tipo;


public class Lexer {
    static ArrayList<Token> tokens = new ArrayList<>();
    static String lineaTexto;

    public Lexer(ArrayList<Token> tokens){
        this.tokens = tokens;
    }
    /**
    * Define el color del token (cuando se genere el archivo html) de acuerdo a su tipo
    * @param    tipoToken Representa un Tipo válido del token   
    * @return   String    Devuelve el color del token con base a su tipo
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

    /**
    * Identifica si antes de esa palabra hay espacio(s) en la línea del archivo de texto 
    * donde se encuentra, si es cierto agrega al arreglo un token que representa ese espacio
    * @param    palabra El valor del token identificado
    * @return   void    Devuelve nada
    */
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

    /**
    * Indica si el siguiente carácter en una palabra es un delimitador(separa tokens)
    * si lo es se separa la palabra, si no lo es se sigue tomando como un sola
    * @param    i       Indice del siguiente carácter a evaluar
    * @param    linea   Palabra completa donde se encuentra el carácter
    * @param    type    Tipo de token, con este se define los delimitadores
    *                   0 corresponde a delimitadores para variables, errores y lógicos
    *                   1 corresponde a delimitadores para números
    * @return   boolean Devuelve si el carácter que se encuentra en el índice i 
    *                   de la linea es delimitador o no
    */
    private static boolean esDelimitador(int i, String linea, int type) {
        int codigo = linea.codePointAt(i);
        ArrayList<Integer> delimitador = new ArrayList<>(Arrays.asList(39,40,41,42,43,45,47,59,61,94)); //Arreglo de numeros que representa el codigo ASCII de los simbolos delimitadores

        if (type == 1) { //Tipo 1 es representa los numeros
            delimitador = new ArrayList<>(Arrays.asList(39,40,41,42,43,47,59,61,94));
        }

        return delimitador.contains(codigo); //evalua si el codigo ASCII del simbolo en indice i de linea es delimitador
    }

    /**
    * Indica cuando una palabra coincide con un patrón definido en el enum Tipo 
    * Si hace match define el valor, tipo, color y estilo del Token, evalua 
    * si hay espacios antes de la palabra y agrega el Token al arreglo de Tokens
    * @param    palabra Palabra del archivo de texto con la cual 
    *                   se hará match con el patrón de Tipo
    * @param    str     Contiene todas las palabras de una línea del archivo de texto
    * @return   boolean Si la palabra hizo match con algun patrón o no.
    * @see      Token
    * @see      #definirColor(Tipo)
    * @see      #agregarEspacios(String)
    */
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
                    while (str.hasMoreTokens()) { //Guarda todos los tokens hasta llegar al final de la linea
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

    /**
    * Se llama esta función cuando matchesPatron regrese false. Esta función se 
    * encarga de separar los posibles tokens válidos de la palabra por espacios.
    * @param    palabra Palabra que no hizo match con ningún patrón de Tipo
    * @return   String  Devuelve un string que contiene la misma palabra pero
    *                   ahora separadando los tokens por espacios
    * @see      #matchesPatron(String, StringTokenizer)
    */
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
                } 
                
                if (!matched) { 
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

    /**
    * Separa el input por espacios y evalua si cada palabra del input hace match 
    * con un patrón de Tipo con la funcion matchesPatron. Si no hace match, obtiene 
    * un nuevo input y se vuelve a evaluar, o se define el token como error
    * @param    input       Representa una línea del archivo de texto
    * @param    nvoInput    Nos indica false si el input se mando a llamar desde la 
    *                       función lexerAritmetico, o true si se mando a llamar
    *                       después de separar una palabra en la función obtenerTokens
    * @return   void        Devuelve nada  
    * @see      #matchesPatron(String, StringTokenizer)
    * @see      #lexerAritmetico(String)
    * @see      #obtenerTokens(String)  
    */
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

    /**
    * Imprime los tokens identificados y almacenados en el arreglo de Tokens 
    * si el token no es un espacio o salto de linea
    * @return void Devuelve nada
    */
    private static void imprimirTokens() {
        Tipo tipo;
        String valor;

        for (Token token : tokens) {
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

    /**
    * Manda a leer el archivo de texto y guarda resultado en un vector. Por cada elemento del 
    * vector se llama a la funcion lexer y agrega un token de salto de linea. Al terminar de 
    * identificar los tokens llama a la funcion imprimirTokens y manda a generar el archivo HTML
    * @param    archivo Nombre del archivo de texto a leer
    * @return   void    Devuelve nada
    * @see      #lexer(String, boolean)
    * @see      #imprimirTokens()
    * @see      Archivo#leerArchivoTXT(String)
    * @see      Archivo#generarArchivoHTML(java.util.List)
    */
    public static void lexerAritmetico(String archivo) {
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

        //imprimirTokens();
        Archivo.generarArchivoHTML(tokens, archivo); //Genera archivo .html
        System.out.println("Se ha generado el archivo .html para " + archivo);
    }

    /**
    * Esta es la función principal donde se define el nombre del archivo 
    * de texto y se manda a llamar a la función de lexerArtimetico
    * @param  args  Argumentos que se pasan en terminal
    * @return void  Devuelve nada
    */
    public static void main(String[] args) {
        /*String nombreArchivo = "expresiones.txt";

        lexerAritmetico(nombreArchivo);*/
    }   
}