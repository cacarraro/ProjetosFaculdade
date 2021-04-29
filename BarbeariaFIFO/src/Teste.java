import java.util.concurrent.Semaphore;

public class Teste
{
    static int primeiro = 0;
    static int ultimo = 0;

    public static void main(String[] args)
    {
        int[] contadorDeClientes = {0};
        int[] fila = new int[100];

        Semaphore mutex = new Semaphore(1); // para acessar e atualizar o contador de clientes
        Semaphore cliente = new Semaphore(0); //o barbeiro vai esperar o cliente liberar, ai ele acorda
        Semaphore clienteSatisfeito = new Semaphore(0); // cliente libera quando se sente satisfeito
        Semaphore corteConcluido = new Semaphore(0); // depois que o cliente esta satisfeito o barbeiro pode concluir o corte

        int limite = 4;

        Semaphore[] listSem = new Semaphore[100];

        Barbeiro b = new Barbeiro(fila, mutex, cliente, clienteSatisfeito, corteConcluido, listSem);
        b.start();

        int k = 0;
        while (k<100)
        {
            Semaphore s = new Semaphore(0);
            listSem[k] = s;
            Cliente j = new Cliente(k, limite, contadorDeClientes, fila, mutex, cliente, clienteSatisfeito, corteConcluido,listSem[k]);
            j.start();
            k++;
        }
    }

}
