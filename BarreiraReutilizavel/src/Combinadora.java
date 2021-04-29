import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Combinadora extends Thread
{
    private static int numArqFinais = 0;        //numero de arquivos gerados pela controladora, compoe o nome unico de cada arquivo

    private ArrayList<Integer> armazenamento;   //array que armazena os numeros dos arquivos, e depois "sofre" o merge

    protected Semaphore mutexFila, chegou;

    protected Fila fila;                        //fila onde a remove os nomes dos arquivos que serao inseridos no array de armazenamento

    protected int n;                             //numero de threads que precisam chegar na bareira para que a conroladora seja liberada

    public Combinadora(Fila fila, Semaphore mutexFila, Semaphore chegou, int n)
    {
        this.fila = fila;

        this.mutexFila = mutexFila;
        this.chegou = chegou;

        this.n = n;
    }

//    percorre o arquivo e insere os numeros no array de armazenamento
    public void removeArquivos(String nome, ArrayList armaz)
    {
        try (Scanner s = new Scanner(new FileReader(nome)))
        {
            while (s.hasNext())
            {
                armaz.add(s.nextInt());
            }
        }catch (FileNotFoundException e)
        {
            System.out.println("N achou.");
            e.printStackTrace();
        }
    }

    //    MergeSort iterativo
    public void MergeSort(ArrayList<Integer> arr, int n)
    {
        //tamanho atual que varia de 1 a n/2
        int tam;
        //index do inicio do merge na subarray esquerda
        int inicioEsq;

        //junta as subarrays de tamanho 1, cria as subarray ordenadas de tamanho 2, junta e cria de 4 ...
        for (tam = 1; tam <= n-1; tam = 2*tam)
        {
            // ponto de inico das subarrays do tamanho atual
            for (inicioEsq = 0; inicioEsq < n-1; inicioEsq+= 2*tam)
            {
                // encontra o ponto de final da subarray esquerda
                int meio = Math.min(inicioEsq + tam - 1, n-1);

                int fimDireita = Math.min(inicioEsq + 2 * tam - 1, n - 1);

                // faz o merge das subarrays
                merge(arr, inicioEsq, meio, fimDireita);
            }
        }
    }

    // faz a juncao/comparacao (merge) das subarrays
    public void merge(ArrayList<Integer> arr, int esq, int meio, int dir)
    {
        int i , j, k;
        int n1 = meio - esq + 1;
        int n2 = dir - meio;

        //cria as subarrays auxiliares (para separar em subarrays)
        int Esq[] = new int[n1];
        int Dir[] = new int[n2];

        // copia os dados pras subarrays auxiliares
        for (i = 0; i < n1; i++)
        {
            Esq[i] = arr.get(esq + i);
        }
        for (j = 0; j < n2; j++)
        {
            Dir[j] = arr.get(meio + 1 + j);
        }

        // junta as subarrays na array principal com os dados ordenados
        i = 0;
        j = 0;
        k = esq;

        while (i < n1 && j < n2)
        {
            if (Esq[i] <= Dir[j])
            {
                arr.set(k, Esq[i]);
                i++;
            }
            else
            {
                arr.set(k, Dir[j]);
                j++;
            }
            k++;
        }

        // se sobrarem elementos na subarray da esquerda sao copiado pra array principal
        while (i < n1)
        {
            arr.set(k, Esq[i]);
            i++;
            k++;
        }

        // se sobrarem elementos na subarray da direita sao copiado pra array principal
        while (j < n2)
        {
            arr.set(k, Dir[j]);
            j++;
            k++;
        }
    }

//    insere os valores ja ordenados pelo merge no arquivo final
    public void criaArquivoFinal()
    {
        // escrever no arquivo txt
        try {
            FileWriter myWriter = new FileWriter("Aqr_Final_" + numArqFinais + ".txt");
            int k = 0;
            while (k < armazenamento.size())
            {
                myWriter.write(armazenamento.get(k) + "  ");
                k++;
            }
            myWriter.close();
            System.out.println("Arquivo final num: " + numArqFinais + " gerado pela combinadora");
        } catch (IOException e) {
            System.out.println("N foi.");
            e.printStackTrace();
        }
    }

//    apos gerar o arquivo final (ordenado), aumenta o numero de arquivos finais gerados
    public void atualizaNumArquivo()
    {
        numArqFinais++;
    }

    @Override
    public void run()
    {
        try {
            while (true)
            {
                chegou.acquire(4);                                   //espera a sinalizacao de que todas as threads chegaram
                armazenamento = new ArrayList<>();                  //inicializa o array de armazenamento
                System.out.println("Combinadora liberada");
                Thread.sleep(100);
                mutexFila.acquire();                                //mutex que controla o acesso à fila
                System.out.println("Combinadora removendo da fila");
                int k=0;
                while (k<n)                                         //remove os arquivos da fila e insere o conteudo no array de armazenamento
                {                                                   //fazendo com que o array contenha os valores de todos os arquivos ordenados pelas trabalhadoras
                    removeArquivos(fila.remove(), armazenamento);
                    k++;
                }
                mutexFila.release();
                System.out.println("Combinadora indo para o merge");
                MergeSort(armazenamento, armazenamento.size());     //faz o merge do array de armazenamento
                System.out.println("Combinadora gerando arquivo final");
                criaArquivoFinal();                                 //gera o arquivo contendo os valores apos o merge
                atualizaNumArquivo();                               //atualiza o numero de arquivos gerados
            }
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
