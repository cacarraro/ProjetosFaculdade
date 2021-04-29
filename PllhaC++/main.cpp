#include <iostream>

#include "PilhaEstatica.h"

using namespace std;

void informaVazia(const Pilha& p){
    if (p.vazia()){
        cout << "Pilha vazia!" << endl;
    } else{
        cout << "Pilha contem elementos!" << endl;
    }
}

void desempilhaTudo(Pilha& p){
    while (!p.vazia()){
        cout << p.desempilha() << endl;
    }
//    informaVazia(p);
}

int main(int argc, char *argv[])
{
    PilhaEstatica p(10);
    p.empilha(10);
    p.empilha(-20);
    desempilhaTudo(p);

    informaVazia(p);
    p.empilha(30);
    informaVazia(p);
    desempilhaTudo(p);
}
