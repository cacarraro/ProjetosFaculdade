import java.util.Random;
import java.util.concurrent.Semaphore;

public class Transportadora extends Thread
{
    private String nome;        //nome da transportadora
    private int limite;         //limite de transporte que podem ser feios por uma transportadora
    private int tempoMin;       //tempo minimo que a transportadora leva para transportar determinado produto
    private int tempoMax;       //tempo maximo que a transportadora leva para transportar determinado produto
    private int[] bufferFabricados; //armazena o index do produto (dentro do catalogo) que foi pedido, é compartilhado entre os fabricantes e as transportadoras
    private String[] idFT;          //armazena o identificador do pedido, é compartilhado entre os fabricantes e as transportadoras
    private Semaphore mutexFT, fabricados, espacos;
    private Random r = new Random();


    public Transportadora(String nome, int limite, int tempoMin, int tempoMax, int[] bufferFabricados, String[] idFT,
                          Semaphore mutexFT, Semaphore fabricados)
    {
        this.nome = nome;
        this.limite = limite;
        this.tempoMax = tempoMax;
        this.tempoMin = tempoMin;
        this.bufferFabricados = bufferFabricados;
        this.idFT = idFT;

        this.mutexFT = mutexFT;
        this.fabricados = fabricados;
        espacos = new Semaphore(this.limite);
    }

//    tempo para transportar o produto
    private int transporta(){
        //entre ... ate ... mls (depende da transportadora)
        int t = (r.nextInt(tempoMax - tempoMin) + tempoMin) * 5 ;
        return t;
    }

    @Override
    public void run()
    {
        while (true){
            try {
                espacos.acquire();          //verifica se a transportadora nao atingiu seu limite de pedidos
                fabricados.acquire();       //espera um pedido ser adicionado no buffer de fabricados
                mutexFT.acquire();          //mutex compartilhado entre Fabricantes e Transportadoras
                int k = bufferFabricados[Empresa.primeiroFabricados];   //recebe o index do produto (dentro do catalogo que a transportadora nao tem acesso!) a ser transportado
                String id = idFT[Empresa.primeiroFabricados];           //recebe o identificador do pedido a ser transportado
                Empresa.primeiroFabricados++;                           //remove o produto na fila de fabricados
                Thread.sleep(transporta()); //tempo de transporte do produto
                System.out.println("Pedido: "+ id +" enviado com sucesso pela transportadora: " + this.nome);
                System.out.println("Tempo de demora da transportadora: " + transporta());
                System.out.println(" ");
                mutexFT.release();          //mutex compartilhado entre Fabricantes e Transportadoras
                espacos.release();          //informa que a transportadora finalizou mais uma entrega e pode continuar fazendo outras entregas

            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
