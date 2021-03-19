#include <iostream>
#include <fstream>
#include<vector>

using namespace std;

vector<char> leerArchivo(){
    vector<char> expresiones;
    char c;
    string nombreArchivo = "expresiones.txt";
    ifstream archivo(nombreArchivo.c_str());

    if(archivo.fail()){
        cout<<"Error, no pudimos abrir el archivo de texto"<<endl;
        exit(1);
    }

    while (archivo.get(c))  {
        expresiones.push_back(c);
    } 

    archivo.close();
    
    return expresiones;
}

void imprimirArchivo(vector<char> expresiones){
    for (int i=0; i<expresiones.size(); i++){
        cout<<expresiones[i]<<endl;
    }
}

void lexerAritmetico(string archivo){

}

int main(){
    vector<char> expresiones = leerArchivo();
    imprimirArchivo(expresiones);
    return 0; 
}