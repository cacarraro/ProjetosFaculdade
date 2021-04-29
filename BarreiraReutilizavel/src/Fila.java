public class Fila {
    String[] elementos;
    int ultimo;
    int primeiro;

    public Fila(int capacidade)
    {
        elementos = new String[capacidade];
        ultimo = -1;
        primeiro = -1;
    }

    public boolean vazia() {
        if (ultimo == -1)
        {
            return true;
        }
        else {
            return false;
        }
    }

    public String primeiro() {
        if (!vazia()) {
            return elementos[primeiro];
        }
        else return "Fila vazia";
    }

    public String ultimo() {
        if (!vazia())
        {
            return elementos[ultimo];
        }
        else return "Fila vazia";
    }

    public boolean cheia() {
        return (ultimo == elementos.length);
    }

    public void adiciona(String elemento)
    {
        if (vazia())
        {
            primeiro++;
            ultimo++;
            elementos[ultimo] = elemento;
        } else if (ultimo < elementos.length)
        {
            ultimo++;
            elementos[ultimo] = elemento;
        }
        else if (cheia())
        {
            System.out.println("Fila cheia !!!");
        }
    }
    public String remove() {
        if (!vazia())
        {
            String aux = elementos[primeiro];
            for (int c = 0; c < elementos.length - 1; c++) {
                elementos[c] = elementos[c + 1];
            }
            ultimo--;
            return aux;
        }
        else return "Fila Vazia";
    }
}
