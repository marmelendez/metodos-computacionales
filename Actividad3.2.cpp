#include<iostream>
#include<fstream>
#include<string>
#include<vector>

using namespace std;

vector<string> leerArchivo(string nombreArchivo){
    vector<string> expresiones;
    string linea;
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

char* str2Char(string miString){
    char* charArr = &miString[0];
    return charArr;
}

void lexerAritmetico(string archivo){
    vector<string> expresiones = leerArchivo(archivo);
}

int main(){
    string nombreArchivo = "expresiones.txt";
    lexerAritmetico(nombreArchivo);
    return 0; 
}