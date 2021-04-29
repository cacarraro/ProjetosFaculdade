package Package;

public class No
{
    private int dado;
    private No proximo;

    public No(int seuDado)
    {
        dado = seuDado;
        proximo = null;
    }

    public No getProximo()
    {
        return proximo;
    }

    protected void setProximo(No proximo)
    {
        this.proximo = proximo;
    }

    public int getDado()
    {
        return dado;
    }

}
