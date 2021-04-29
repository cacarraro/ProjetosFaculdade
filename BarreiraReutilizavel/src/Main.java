import java.util.concurrent.Semaphore;

public class Main
{
    public static void main(String[] args)
    {
        int n = 4;                  //numero de threads que precisam chegar na bareira para que ela seja aberta

        int[] contador = {0};       //numero de threads que ja chegou na barreira

        Fila fila = new Fila(n);    //fila de strings, dos nomes dos arquivos

        Semaphore mutexCont = new Semaphore(1);     //mutex que controla o acesso ao contador
        Semaphore mutexFila = new Semaphore(1);     //mutex que controla o acesso à fila
        Semaphore barreiraEntrada = new Semaphore(0);
        Semaphore barreiraSaida = new Semaphore(1);
        Semaphore chegou = new Semaphore(0);        //sinaliza quando a controladora pode comecar a trabalhar

        Combinadora c = new Combinadora(fila,mutexFila, chegou, n);
        int k = 0;
        while (k<n)
        {
            Trabalhadora t = new Trabalhadora(k+1,fila, mutexCont, mutexFila, barreiraEntrada, barreiraSaida, chegou, n, contador);
            t.start();
            k++;
        }
        c.start();

    }
}
