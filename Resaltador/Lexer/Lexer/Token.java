package Lexer;

public class Token {
    private Tipo tipo;
    private String valor;
    private String color;

    public Tipo getTipo(){
        return this.tipo;
    }

    public String getValor() {
        return this.valor;
    }

    public String getColor() {
        return color;
    }
    
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
    public void setColor(String color) {  
        this.color = color;
    }

    enum Tipo {
        NUMERO("^[0-9]+[\\.]?[0-9]*$"), //*[E|e][-|[0-9]][0-9]*$   [^[0-9]+[\\.][E|e][-|[0-9]][0-9]*$[0-9]*$]
        NUMERO_EXPONENCIAL("^[0-9]+\\.?[0-9]*[E|e]-?[0-9][0-9]*$"),
        LOGICO("^#[t|f]$"), 
        SIMBOLO("^'[A-Za-z]*$"), 
        OPERADOR("^[*|/|+|-|[-^]|[-=]]$"), 
        VARIABLE("^[A-Za-z]+\\w*$"), 
        ESPECIAL("^[(|)]$"), 
        COMENTARIO("^;[\\w|\\s|[^\\w]]*$"), 
        PALABRA_RESERVADA("^define$"); 
    
        public final String patron;
        
        Tipo(String s){
            this.patron = s;
        }
    }
    
}
