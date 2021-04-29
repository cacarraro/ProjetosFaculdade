package EstruturaGrafo.ListaEncadeada;

public class ListaEncadeada
{
    private NoL primeiro;
    private NoL ultimo;

    public ListaEncadeada()
    {
        primeiro = null;
        ultimo = null;
    }

    public boolean listaVazia()
    {
        return (primeiro == null);
    }

    public void inserePrimeiro(int dado, double peso)
    {
        NoL novo = new NoL(dado, peso);

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

    public void insereDepois(NoL antes, int dado, double peso)
    {
        if (listaVazia())
        {
            inserePrimeiro(dado, peso);
        }
        else
        {
            NoL novo = new NoL(dado, peso);
            novo.setProximo(antes.getProximo());
            antes.setProximo(novo);
        }
    }

    public void insereUltimo(int dado, double peso)
    {
        if (listaVazia())
        {
            inserePrimeiro(dado, peso);
        }
        else
        {
            NoL novo = new NoL(dado, peso);

            ultimo.setProximo(novo);
            ultimo = novo;
        }
    }

    public void insereOrdenado(int elemento, double peso)
    {
        if (listaVazia())
        {
            inserePrimeiro(elemento, peso);
        }
        else if (elemento <= primeiro.getDado())
        {
            inserePrimeiro(elemento, peso);
        }
        else if (elemento >= ultimo.getDado())
        {
            insereUltimo(elemento, peso);
        }
        else
        {
            NoL p = primeiro;
            while (p != null)
            {
//                System.out.println(p.getDado());
                if (p.getDado() <= elemento)
                {
                    if (p.getProximo().getDado() >= elemento)
                    {
                        insereDepois(p, elemento, peso);
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


    //    Mostra o Nome do No do Grafo e o peso da aresta formada inseridos no NóL da Lista
    public void mostraListaCompleta()
    {
        NoL p = primeiro;
        while (p != null)
        {
            System.out.println("N: " + p.getDado() + ", P: " + p.getPeso());
            p = p.getProximo();
        }
    }


    public NoL mostraElemento(int serMostrado)
    {
        if (existeElemento(serMostrado))
        {
            NoL aux = primeiro;
            while (aux != null) {
                if (aux.getDado() == serMostrado) {
                    return aux;
                } else aux = aux.getProximo();
            }
        }
            System.out.println("elemento nao encontrado");
            return null;

    }

//    Retorna o No que foi retirado da lista
    public NoL retiraPrimeiro()
    {
        NoL aux = primeiro;
        primeiro = primeiro.getProximo();

        return aux;
    }

//    Retorna o No que foi retirado da lista
    public NoL retiraUltimo()
    {
        NoL atual = primeiro;
        NoL anterior = null;
        while (atual.getProximo() != null)
        {
            anterior = atual;
            atual = atual.getProximo();
        }
        ultimo = anterior;
        ultimo.setProximo(null);
        return atual;
    }

//    Remove o No da Lista que contem o Nó do Grafo que foi passado como parametro
    public void retiraAtual(int dado)
    {
        if (!listaVazia()) {
            if (dado == primeiro.getDado()) {
                retiraPrimeiro();
            } else {
                NoL aux = primeiro;
                while (aux.getProximo() != null) {
                    if (aux.getProximo().getDado() == dado) {
                        retiraDepois(aux);
                    } else aux = aux.getProximo();
                }
            }
        }
    }

//    Retorna o No que foi retirado da lista
    public NoL retiraDepois(NoL anterior)
    {
        NoL aux = anterior.getProximo(); // anterior -> proximo // aux = proximo// anterior.proximo = proximo.proximo
        anterior.setProximo(aux.getProximo());

        return aux;
    }

//    Percorre a Lista para conferir se o No do Grafo passado como parametro ja esta na lista
    public boolean existeElemento(int dado)
    {
        if (listaVazia())
        {
            return false;
        }
        else
            {
                NoL aux = primeiro;
                while (aux != null)
                {
                    if (aux.getDado() == dado)
                    {
                        return true;
                    } else aux = aux.getProximo();
                }
                return false;
            }
    }

    public NoL getUltimo()
    {
        NoL aux = ultimo;
        return aux;
    }

    public NoL getPrimeiro()
    {
        NoL aux = primeiro;
        return aux;
    }

    public int getDado(NoL queroDado)
    {
        return queroDado.getDado();
    }

    public NoL getProximoL(NoL n)
    {
        return n.getProximo();
    }
}
