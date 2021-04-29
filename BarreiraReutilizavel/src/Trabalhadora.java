import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Trabalhadora extends Thread
{
    private int numArquivo = 0;         //numero de arquivos gerados por cada trabalhadora, compoe o nome unico de cada arquivo

    private int idTrab;                 //numero que idenifica a trabalhadora, compoe o nome unico de cada arquivo

    private ArrayList<Integer> armazenamento;   //array que armazena os numeros aleatorios desordenados, e que posteriormente é ordenada

    protected Fila fila;                //fila onde a trabalhadora insere o nome do arquivo ja ordenado

    private Random r = new Random();

    protected Semaphore mutexCont, mutexFila, barreiraEntrada, barreiraSaida, chegou;

    protected int n;                    //numero de threads que precisam chegar na bareira para que ela seja aberta

    protected int[] contador;           //numero de threads que ja chegou na barreira


    public Trabalhadora(int idTrab, Fila fila, Semaphore mutexCont, Semaphore mutexFila, Semaphore barreiraEntrada, Semaphore barreiraSaida, Semaphore chegou, int n, int[] contador)
    {
        this.idTrab = idTrab;

        this.fila = fila;

        this.mutexCont = mutexCont;
        this.mutexFila = mutexFila;
        this.barreiraEntrada = barreiraEntrada;
        this.barreiraSaida = barreiraSaida;
        this.chegou = chegou;

        this.n = n;

        this.contador = contador;
    }

//    Gera um arquivo com um milhão (10^6) de números inteiros aleatórios entre 0 e 10^7.
    public void criaArquivoAleatorio(String nome)
    {
        // escrever no arquivo txt
        try {
            FileWriter myWriter = new FileWriter(nome);
            int k = 0;
            while (k < Math.pow(10,6))
            {
                int i = r.nextInt((int) Math.pow(10,7));
                myWriter.write(i + "  ");
                k++;
            }
            myWriter.close();
            System.out.println("Arquivo aleatorio num:  " + numArquivo + " gerado pela Trabalhadora: " + idTrab);
        } catch (IOException e) {
            System.out.println("N foi.");
            e.printStackTrace();
        }
    }

//    insere todos os numeros do arquivo aleatorio desordenado no array de armazenamento que será ordenado
    public void arqToArray(String nome)
    {
        try (Scanner s = new Scanner(new FileReader(nome)))
        {
            armazenamento = new ArrayList<>((int)Math.pow(10,6));
            while (s.hasNext())
            {
                armazenamento.add(s.nextInt());
            }
        }catch (FileNotFoundException e)
        {
            System.out.println("N achou.");
            e.printStackTrace();
        }
    }

//    ordena o array de armaznamento
    public void ordenaArquivo()
    {
       Collections.sort(armazenamento);
    }

//    insere os valores ja ordenados do array num novo arquivo agora em ordem crescente
    public void criaArquivoOrdenado(String nome)
    {
        // escrever no arquivo txt
        try {
            FileWriter myWriter = new FileWriter(nome);
            for (int i: armazenamento)
            {
                myWriter.write(i + "  ");
            }
            myWriter.close();
            System.out.println("Arquivo ordenado num:  " + numArquivo + " gerado pela Trabalhadora: " + idTrab);
        } catch (IOException e) {
            System.out.println("N foi.");
            e.printStackTrace();
        }
    }


//    apos gerar o arquivo ordenado, aumenta o numero de arquivos gerados
    public void atualizaNumArquivo()
    {
        numArquivo++;
    }

    @Override
    public void run()
    {
        try {
            while (true)
            {
                String nomeRand = "Aqr_" + numArquivo + "_Thrd_" + idTrab + ".txt";
                String nomeOrd = "Aqr_" + numArquivo + "_Thrd_" + idTrab + "_Ordenado.txt";

                criaArquivoAleatorio(nomeRand);             //gera arquivo aleatorio desordenado
                arqToArray(nomeRand);                       //insere os valores no array
                ordenaArquivo();                            //ordena os valores do array
                criaArquivoOrdenado(nomeOrd);               //gera arquivo com os valores ordenados
                atualizaNumArquivo();                       //atualiza o numero de arquivos gerados

                Thread.sleep(1500);
                mutexCont.acquire();                        //mutex que controla o acesso ao contador
                    contador[0] = contador[0] + 1;          //avisa que chegou no contador
                    Thread.sleep(1500);

                    if (contador[0] == n)                   //se todas as threads chegaram comeca a liberar as barreiras
                    {
                        System.out.println("contador = " + n);
                        barreiraSaida.acquire();
                        barreiraEntrada.release();
                                          //avisa a combinadora que todas athreads ja chegaram
                        System.out.println("Combinadora liberada pela trabalhadora");
                    }
                mutexCont.release();
                barreiraEntrada.acquire();
                barreiraEntrada.release();

                mutexFila.acquire();                    //mutex que controla o acesso à fila
                fila.adiciona(nomeOrd);             //insere o nome do arquivo ordenado na fila
                mutexFila.release();
                chegou.release();
                System.out.println("Ponto critico barreira, Trabalhadora: " + idTrab);

                Thread.sleep(1500);
                mutexCont.acquire();                        //mutex que controla o acesso ao contador
                    contador[0] = contador[0] -1;           //subtrai cada thread, para que elas possam repetir o processo
                    if (contador[0] == 0)                   //se todas ja passaram e atualizaram o contador
                    {                                       //reordena as barreiras para que nao fiquem abertas e o processo possa ser repetido
                        barreiraEntrada.acquire();
                        barreiraSaida.release();
                    }
                mutexCont.release();
                barreiraSaida.acquire();                    //reordena as barreiras para que nao fiquem abertas e o processo possa ser repetido
                barreiraSaida.release();
            }
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
