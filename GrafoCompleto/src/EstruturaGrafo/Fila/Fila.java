package EstruturaGrafo.Fila;

public class Fila
{
    private int[] elementos;
    private int ultimo;
    private int inicio;

    @SuppressWarnings("unchecked")
    public Fila(int capacidade) {
        elementos = new int[capacidade];
        ultimo = 0;
        inicio = 0;
    }

    public boolean vazia() {
        if (ultimo == 0) {
            return true;
        } else
            {
            return false;
            }
    }

    public int primeiro()
    {
        return elementos[0];
    }

    public int ultimo() {
        return elementos[ultimo - 1];
    }

    public boolean cheia() {
        return (ultimo == elementos.length);
    }

    public void adiciona(int elemento)
    {
        if (!cheia())
        {
        elementos[ultimo] = elemento;
        ultimo++;
        }else {
        System.out.println("Fila cheia elemento nao adicionado");
        }
    }

    public int remove()
    {
        int aux = elementos[0];
        for (int c = 0; c < elementos.length - 1; c++) {
            elementos[c] = elementos[c + 1];
        }
        ultimo--;
        return aux;
    }

    public boolean contem(int dado)
    {
        for (int i = 0; i < ultimo; i++)
        {
            if (elementos[i] == dado)
            {
                return true;
            }
        }
        return false;
    }
}