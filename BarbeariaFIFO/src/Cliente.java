import java.util.Random;
import java.util.concurrent.Semaphore;

public class Cliente extends Thread
{
    protected int limite;
    protected int id;
    protected int[] contadorDeClientes;
    protected int[] fila;

    protected Semaphore mutex, cliente, clienteSatisfeito, corteConcluido, individual;

    private Random r = new Random();

    public Cliente(int id, int limite, int[] contadorDeClientes, int[] fila,
                   Semaphore mutex, Semaphore cliente, Semaphore clienteSatisfeito, Semaphore corteConcluido, Semaphore individual)
    {
        this.id =id;

        this.limite = limite;
        this.contadorDeClientes = contadorDeClientes;
        this.fila = fila;

        this.mutex = mutex;
        this.cliente = cliente;
        this.clienteSatisfeito = clienteSatisfeito;
        this.corteConcluido = corteConcluido;

        this.individual = individual;
    }

    protected void desistir()
    {
       System.out.println("         Cliente Largou os Bets e foi embora evitando aglomeracoes!");
    }

    protected int terCabeloCortado(){
        int i = r.nextInt(20) *1000;
        return i;
    }

    public void run()
    {
        try
        {
            int k = r.nextInt(20);
            Thread.sleep(k * 10000);
            mutex.acquire();
                if (contadorDeClientes[0] == limite)
                {
                    mutex.release();
                    desistir();
                }
                else {
                    System.out.println("Cliente entrou pra fila");
                    fila[Teste.ultimo] = this.id;
                    contadorDeClientes[0]++;
                    Teste.ultimo++;
            mutex.release();
                    cliente.release(); //funciona, barbeiro acorda
                    individual.acquire();
                        System.out.println("Cliente comecou a ter cabelo cortado");
                        Thread.sleep(terCabeloCortado());
                        clienteSatisfeito.release();
                        System.out.println("Cliente se sente satisfeito");
                    corteConcluido.acquire();
                    System.out.println("Corte do cliente concluido");

                    mutex.acquire();
                        contadorDeClientes[0]--;
                    mutex.release();
                }

        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
