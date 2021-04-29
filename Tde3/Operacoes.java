package Tde3;

public class Operacoes
{
    protected double moduloLista(ListaEncadeada L)
    {
        double soma = 0;
        No a = L.getPrimeiro();
        while (a != null)
        {
            soma += Math.pow(a.getDado(), 2);
            a = a.getProximo();
        }
        return Math.sqrt(soma);
    }

    protected double produtoListas(ListaEncadeada A, ListaEncadeada B)
    {
        double soma = 0;
        No a = A.getPrimeiro();
        No b = B.getPrimeiro();
        while(a != null && b != null)
        {
            soma += a.getDado() * b.getDado();
            a = a.getProximo();
            b = b.getProximo();
        }
        return soma;
    }

    protected double similaridade(ListaEncadeada A, ListaEncadeada B)
    {
        double multiplica = produtoListas(A, B);
        double multiplicaModulo = moduloLista(A) * moduloLista(B);

        return multiplica/multiplicaModulo;
    }

    protected void intersecao(ListaEncadeada A, ListaEncadeada B)
    {
        ListaEncadeada C = new ListaEncadeada();
        No a = A.getPrimeiro();
        No b = B.getPrimeiro();
        while (a != null)
        {
            while (b != null)
            {
                if (a.getDado() == b.getDado())
                {
                    C.insereOrdenado(a.getDado());
                }
                b = b.getProximo();
            }
            a = a.getProximo();
            b = B.getPrimeiro();
        }
        if (C.listaVazia())
        {
            System.out.println("Não há intersecao");
        }
        C.mostraLista();
    }
}
