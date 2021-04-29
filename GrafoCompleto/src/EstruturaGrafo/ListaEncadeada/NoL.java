package EstruturaGrafo.ListaEncadeada;

public class NoL
{
    private int dado;
    private double peso;
    private NoL proximo;

    public NoL(int dado, double peso)
    {
        this.dado = dado;
        this.peso = peso;
        proximo = null;
    }

//    Retorna o proximo No da lista
    public NoL getProximo()
    {
        return proximo;
    }

//    Define o proximo No na lista
    protected void setProximo(NoL proximo)
    {
        this.proximo = proximo;
    }

//    Retorna o No do Grafo que foi inserido dentro do No da Lista
    protected int getDado()
    {
        int copia = dado;
        return copia;
    }

//    Retorna o peso da aresta formada no Grafo e adicionada ao No da Lista
    public double getPeso()
    {
        double cop = peso;
        return cop;
    }

}
