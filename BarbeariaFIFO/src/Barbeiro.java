import java.util.Random;
import java.util.concurrent.Semaphore;

public class Barbeiro extends Thread
{
    protected Semaphore mutex, cliente, clienteSatisfeito, corteConcluido;
    protected int[] fila;

    protected Semaphore[] listSem;

    private Random r = new Random();

    public Barbeiro(int[] fila, Semaphore mutex, Semaphore cliente, Semaphore clienteSatisfeito,Semaphore corteConcluido ,Semaphore[] listSem)
    {
        this.fila = fila;

        this.mutex = mutex;
        this.cliente = cliente;
        this.clienteSatisfeito =clienteSatisfeito;
        this.corteConcluido = corteConcluido;

        this.listSem = listSem;
    }

    private int cortarCabelo(){
        int i = r.nextInt(20) *1000;
        return i;
    }

    public void run()
    {
        while (true)
        {
            try {
                cliente.acquire();
                System.out.println("    Barbeiro acordou");
                    mutex.acquire();
                        int sem =  fila[Teste.primeiro];
                        Teste.primeiro++;
                        System.out.println("    Barbeiro tirou da fila");
                    mutex.release();
                    listSem[sem].release();
                    System.out.println("    Barbeiro comecou a cortar cabelo");
                        Thread.sleep(cortarCabelo());
                        System.out.println("    Barbeiro satisfeito");
                        clienteSatisfeito.acquire();
                        System.out.println("    Cliente do barbeiro satisfeito");
                    corteConcluido.release();
                    System.out.println("    Corte do barbeiro concluido");
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
