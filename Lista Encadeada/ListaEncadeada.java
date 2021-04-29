package Tde3;

public class ListaEncadeada
{
    private No primeiro;
    private No ultimo;

    No Lista;


    public ListaEncadeada()
    {
        Lista = null;
    }

    protected boolean listaVazia()
    {
        return (primeiro == null);
    }

    protected void inserePrimeiro(int elemento)
    {
        No novo = new No(elemento);

        if (listaVazia())
        {
            primeiro = novo;
            ultimo = novo;
        }
        else
        {
            novo.setProximo(primeiro);
            primeiro = novo;
        }
    }

    protected void insereDepois(No antes, int elemento)
    {
        if (listaVazia())
        {
            inserePrimeiro(elemento);
        }
        else
        {
            No novo = new No(elemento);
            novo.setProximo(antes.getProximo());
            antes.setProximo(novo);
        }
    }

    protected void insereUltimo(int elemento)
    {
        if (listaVazia())
        {
            inserePrimeiro(elemento);
        }
        else
        {
            No novo = new No(elemento);

            ultimo.setProximo(novo);
            ultimo = novo;
        }
    }

    protected void insereOrdenado(int elemento)
    {
        if (listaVazia())
        {
            inserePrimeiro(elemento);
        }
        else if (elemento <= primeiro.getDado())
        {
            inserePrimeiro(elemento);
        }
        else if (elemento >= ultimo.getDado())
        {
           insereUltimo(elemento);
        }
        else
        {
            No p = primeiro;
            while (p != null)
            {
//                System.out.println(p.getDado());
                if (p.getDado() <= elemento)
                {
                    if (p.getProximo().getDado() >= elemento)
                    {
                        insereDepois(p, elemento);
                        break;
                    }
                    else
                    {
                        p = p.getProximo();
                    }
                }
            }
        }
    }

    protected void mostraLista()
    {
        No p = primeiro;
        while (p != null)
        {
            System.out.println(p.getDado());
            p = p.getProximo();
        }
    }

    protected int retiraPrimeiro()
    {
        No aux = primeiro;
        primeiro = primeiro.getProximo();

        return aux.getDado();
    }

    protected int retiraUltimo()
    {
        No atual = primeiro;
        No anterior = null;
        while (atual.getProximo() != null)
        {
            anterior = atual;
            atual = atual.getProximo();
        }
        ultimo = anterior;
        ultimo.setProximo(null);
        return atual.getDado();
    }

    protected int retiraDepois(No anterior)
    {
        No aux = anterior.getProximo();
        anterior.setProximo(aux.getProximo());

        return aux.getDado();
    }

    public No getUltimo()
    {
        return ultimo;
    }

    public No getPrimeiro()
    {
        return primeiro;
    }


}
