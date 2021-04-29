//
// Created by Camila on 18/08/2020.
//

#ifndef PLLHAC___PILHAESTATICA_H
#define PLLHAC___PILHAESTATICA_H

#include "Pilha.h"

class PilhaEstatica: public Pilha {
    private:
        int* dados;
        int topo;
        int max;
    public:
        explicit PilhaEstatica(int max);
//        Nao aceita copias: (&)
        PilhaEstatica(const PilhaEstatica& outra) = delete;
        PilhaEstatica& operator = (const PilhaEstatica& outra) =delete;
//
        virtual void empilha(int valor) override;
        virtual int desempilha() override;
        virtual bool vazia() const override;
        virtual ~PilhaEstatica();
};


#endif //PLLHAC___PILHAESTATICA_H
