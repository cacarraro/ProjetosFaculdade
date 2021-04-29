public class PilhaEstatica implements Pilha,Cloneable
{

    int topo;
    int[] dados;

    public PilhaEstatica(int max)
    {
        topo = -1;
        dados = new int[max];
    }

    public PilhaEstatica(PilhaEstatica outra)
    {
        this.topo = outra.topo;
        this.dados = new int[outra.dados.length];
        for (int i = 0; i < outra.dados.length; i++)
        {
            this.dados[i] = outra.dados[i];
        }
    }

    @Override
    public boolean vazia()
    {
        return topo == -1;
    }

    @Override
    public void empilha(int valor)
    {
            topo++;
            dados[topo] = valor;
    }

    @Override
    public int desempilha()
    {
        if (vazia())
        {
            return -1;
        }
        else
        {
            int dado = dados[topo];
            topo--;
            return dado;
        }
    }

    protected PilhaEstatica clone(){
        try {
            PilhaEstatica clone = (PilhaEstatica) super.clone();
            clone.dados = new int[dados.length];
            for (int i = 0; i < dados.length; i++){
                clone.dados[i] = dados[i];
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
