//
// Created by Camila on 18/08/2020.
//

#include "PilhaEstatica.h"

PilhaEstatica::PilhaEstatica(int max)
: dados(new int[max]), topo(-1), max(max){
}

bool PilhaEstatica::vazia() const {
    return topo == -1;
}

void PilhaEstatica::empilha(int valor) {
    topo = topo +1;
    dados[topo] = valor;
}

int PilhaEstatica::desempilha() {
    int dado = dados[topo];
    topo = topo -1;
    return dado;
}

PilhaEstatica::~PilhaEstatica() {
    delete [] dados;
}

//PilhaEstatica::PilhaEstatica(const PilhaEstatica &outra) {
//    this->topo = outra.topo;
//    this->max = outra.max;
//
//    this->dados = new int[max];
//    for (int i = 0; i < max; i++) {
//        this->dados[i] = outra.dados[i];
//    }
//}

//PilhaEstatica &PilhaEstatica::operator=(const PilhaEstatica &outra) {
//    if(&outra == this) return *this;
//
//    this->topo = outra.topo;
//    this->max = outra.max;
//
//    delete [] dados;
//    this->dados = new int[max];
//    for (int i = 0; i < max; i++) {
//        this->dados[i] = outra.dados[i];
//    }
//    return *this;
//}
