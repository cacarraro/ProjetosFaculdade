import java.util.Random;
import java.util.concurrent.Semaphore;

public class Loja extends Thread
{
    private int numPedido = 0;  //contador de pedidos da cada loja
    private String nome;        //nome da loja
    private String[] Catalogo;  //cada loja tem uma copia do catalogo fornecida pela empresa
    private int tempoMin = 10;  //para o calculo de espera entre um
    private int tempoMax = 150; //pedido e outro
    private int[] bufferPedidos;//armazena o index do produto (dentro do catalogo) que foi pedido, é compartilhado entre as lojas e os fabricantes
    private String[] idLF;      //armazena o identificador do pedido, é compartilhado entre as lojas e os fabricantes
    private Semaphore mutexLF, pedidos;
    private Random r = new Random();

    public Loja(String nome, String[] Catalogo, int[] bufferPedidos, String[] idLF,
                Semaphore mutexLF, Semaphore pedidos)
    {
        this.nome = nome;
        this.Catalogo = Catalogo;
        this.bufferPedidos = bufferPedidos;
        this.idLF = idLF;

        this.mutexLF = mutexLF;
        this.pedidos = pedidos;
    }

//    tempo para receber o pedido
    private int tempoSleep()
    {
        //entre 1000 ate 1500 mls
        int t = ((r.nextInt(tempoMax - tempoMin)) + tempoMin) * 100;
        return t;
    }

//    seleciona um produto do catalogo como pedido
    private int recebePedido(){
        int prod = r.nextInt(8);
        numPedido++;
        return prod;
    }

    @Override
    public void run()
    {
        while(true)
        {
            try {
                Thread.sleep(tempoSleep());             //tempo para receber o pedido
                int pordut = recebePedido();            //seleciona um produto do catalogo como pedido

                String id = this.nome + numPedido;      //gera o identificador do pedido

                mutexLF.acquire();                      //mutex compartilhado entre Lojas e Fabricantes
                    bufferPedidos[Empresa.ultimoPedidos] = pordut;  //adiciona o produto na fila de pedidos
                    idLF[Empresa.ultimoPedidos] = id;   //informa o identificador do pedido (para quem ira fabricar)
                    Empresa.ultimoPedidos++;
                    System.out.println("Pedido realizado: " + id + ", produto: " + Catalogo[pordut]);
                    System.out.println();
                mutexLF.release();      //mutex compartilhado entre Lojas e Fabricantes
                pedidos.release();      //sinaliza para os fabricantes que tem pedido a ser produzido

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
