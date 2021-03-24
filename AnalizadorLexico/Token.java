package AnalizadorLexico;

public class Token {
    
    //ATRIBUTOS
    Tipo tipo;
    String valor;

    //METODOS

    /* Constructor */
    Token (){
        this.tipo = Tipo.ERROR; 
        this.valor = " ";
    }
    /* Getters y setters */
    public String getValor() {
        return this.valor;
    }

    public Tipo getTipo() {
        return this.tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
