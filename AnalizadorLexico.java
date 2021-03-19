import java.util.Vector;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class AnalizadorLexico {
    Vector<String>  texto = new Vector<>();
    
    
    private void leerArchivo(String nombreArchivo){
        try {
            File archivo = new File(nombreArchivo);
            Scanner scan = new Scanner(archivo);
            while (scan.hasNextLine()) {
                this.texto.add(scan.nextLine());
            }
            scan.close();
        }

        catch (FileNotFoundException e) {
            System.out.println("Error, no se encontro el archivo con ese nombre");
            e.printStackTrace();
        }
    }

    public void lexerAritmetico(String archivo){
        leerArchivo(archivo);
        for (int i=0; i< this.texto.size();i++){
            String linea = this.texto.get(i);
            char[] ch= linea.toCharArray();
        }
    }

    public static void main(String[] args) {
        AnalizadorLexico a = new AnalizadorLexico();
        a.lexerAritmetico("expresiones.txt");
    }
} 
