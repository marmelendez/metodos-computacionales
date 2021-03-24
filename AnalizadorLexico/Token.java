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

    /* Obtiene índice en la tabla de transición que representa un símbolo */
    public int indiceAlfabeto(char simbolo){
        int indice = -1;
        if (simbolo == 'E' || simbolo == 'e'){
            indice = 11;
        } else if (Character.isLetter(simbolo)){
            indice = 0;
        } else if (Character.isDigit(simbolo)){
            indice = 1;
        } else if (simbolo == '_'){
            indice = 2;
        } else if (simbolo == '='){
            indice = 3;
        } else if (simbolo == '+'){
            indice = 4;
        } else if (simbolo == '-'){
            indice = 5;
        } else if (simbolo == '*'){
            indice = 6;
        } else if (simbolo == '/'){
            indice = 7;
        } else if (simbolo == '^'){
            indice = 8;
        } else if (simbolo == '('){
            indice = 9;
        } else if (simbolo == ')'){
            indice = 10;
        } else if (simbolo == '.'){
            indice = 12;
        }
        return indice;
    }
}
