/**
* <h1>Almacenar propiedades y métodos de Token</h1>
* La clase Token contiene las propiedades principales de un token, 
* la definición de cada tipo de token mediante expresiones regulares
* y los métodos de setters y getters.
*
* @author  Lizbeth Maribel Melendez Delgado 
* @author  Gerardo Novelo de Anda
* @version 1.0, 25/04/21
*/

package Resaltador;

/** */
public class Token {
    private Tipo tipo;
    private String valor;
    private String color;
    private String estilo;

    /* Getters */
    public Tipo getTipo() {
        return this.tipo;
    }

    public String getValor() {
        return this.valor;
    }

    public String getColor() {
        return color;
    }

    public String getEstilo() {
        return estilo;
    }
    
    /* Setters */
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
    public void setColor(String color) {  
        this.color = color;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    /* Expresiones regulares de cada Tipo de token */
    enum Tipo {
        NUMERO("^[0-9]+[\\.]?[0-9]*$"), 
        NUMERO_EXPONENCIAL("^[0-9]+\\.?[0-9]*[E|e]-?[0-9][0-9]*$"),
        LOGICO("^#[t|f]$"), 
        SIMBOLO("^'[\\w|\\s|[^\\w]]*$"), 
        OPERADOR("^[*|/|+|-|[-^]|[-=]]$"), 
        VARIABLE("^[A-Za-z]+\\w*$"), 
        ESPECIAL("^[(|)]$"), 
        COMENTARIO("^;[\\w|\\s|[^\\w]| ]*$"), 
        PALABRA_RESERVADA("^define$"); 
    
        public final String patron;
        
        Tipo(String s) {
            this.patron = s;
        }
    }   
}
