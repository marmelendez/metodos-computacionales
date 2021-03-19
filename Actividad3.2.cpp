#include<iostream>
#include<fstream>
#include<string>
#include<vector>

using namespace std;

vector<string> leerArchivo(){
    vector<string> expresiones;
    string linea;
    string nombreArchivo = "expresiones.txt";
    ifstream archivo(nombreArchivo.c_str());

    if(archivo.fail()){
        cout<<"Error, no pudimos abrir el archivo de texto :("<<endl;
        exit(1);
    }

    while (!archivo.eof()){
        getline(archivo,linea);
        expresiones.push_back(linea);
    }

    archivo.close();

    return expresiones;
}

void imprimirArchivo(vector<string> expresiones){
    for (int i=0; i<expresiones.size(); i++){
        cout<<expresiones[i]<<endl;
    }
}

void lexerAritmetico(string archivo){

}

int main(){
    vector<string> expresiones = leerArchivo();
    imprimirArchivo(expresiones);
    return 0; 
}