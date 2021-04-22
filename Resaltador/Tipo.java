

public enum Tipo {
    NUMERO("[0-9]+"),
    OPERADOR_BINARIO("[*|/|+|-]");
    // SUMA("[+]"),
    // RESTA("-"),
    // MULTIPLICACION("*"),
    // DIVISION("/"),
    // POTENCIA("^");

    public final String patron;
    
    Tipo(String a){
        this.patron = a;
    }
}