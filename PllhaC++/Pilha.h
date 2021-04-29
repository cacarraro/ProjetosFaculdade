//
// Created by Camila on 18/08/2020.
//

#ifndef PLLHAC___PILHA_H
#define PLLHAC___PILHA_H


class Pilha {
    public:
        virtual int desempilha() = 0;
        virtual void empilha(int dado) = 0;
        virtual bool vazia() const = 0 ;
        virtual ~Pilha(){}
};


#endif //PLLHAC___PILHA_H
