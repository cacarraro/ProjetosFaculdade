import javax.naming.OperationNotSupportedException;

public class PilhaImutavel implements Pilha
{
    private Pilha pilha;

    public PilhaImutavel(Pilha pilha){
        this.pilha = pilha;
    }

    @Override
    public void empilha(int valor)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public int desempilha()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean vazia()
    {
        return pilha.vazia();
    }
}
