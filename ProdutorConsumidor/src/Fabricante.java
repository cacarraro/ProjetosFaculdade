import java.util.Random;
import java.util.concurrent.Semaphore;

public class Fabricante extends Thread
{
    private String nome;            //nome do fabricante
    private String[] Catalogo;      //cada fabricante tem uma copia do catalogo fornecida pela empresa
    private int limite;             //limite de pedidos que podem ser produzidos por um fabricante
    private int tempoMin[];         //contem os tempos minimos para produzir cada produto (respectivo so catalogo)
    private int tempoMax[];         //contem os tempos maximos para produzir cada produto (respectivo so catalogo)
    private int[] bufferPedidos;    //armazena o index do produto (dentro do catalogo) que foi pedido, é compartilhado entre as lojas e os fabricantes
    private int[] bufferFabricados; //armazena o index do produto (dentro do catalogo) que foi pedido, é compartilhado entre os fabricantes e as transportadoras
    private String[] idLF;          //armazena o identificador do pedido, é compartilhado entre as lojas e os fabricantes
    private String[] idFT;          //armazena o identificador do pedido, é compartilhado entre os fabricantes e as transportadoras
    private Semaphore mutexLF, mutexFT, pedidos, fabricados, espaco;
    private Random r = new Random();

    public Fabricante(String nome, String[] Catalogo, int[] bufferPedidos, int[] bufferFabricados,
                      String[] idLF, String[] idFT, int limite, int[] tempoMin, int[] tempoMax,
                      Semaphore mutexLF, Semaphore mutexFT, Semaphore pedidos, Semaphore fabricados)
    {
        this.nome = nome;
        this.Catalogo = Catalogo;
        this.limite = limite;
        this.tempoMin = tempoMin;
        this.tempoMax = tempoMax;
        this.bufferPedidos = bufferPedidos;
        this.bufferFabricados = bufferFabricados;
        this.idLF = idLF;
        this.idFT = idFT;

        this.mutexLF = mutexLF;
        this.mutexFT = mutexFT;
        this.pedidos = pedidos;
        this.fabricados = fabricados;
        espaco = new Semaphore(this.limite);
    }

//    tempo para fabricar o produto
    private int fabricar(int k){
        int tMin = tempoMin[k];
        int tMax = tempoMax[k];
        //entre ... ate ... mls (depende do produto e do fabricante)
        int t = (r.nextInt(tMax - tMin) + tMin) * 5 ;
        return t;
    }

    @Override
    public void run()
    {
        while (true){
            try {
                espaco.acquire();       //verifica se o fabricante nao atingiu seu limite de pedidos
                pedidos.acquire();      //espera um pedido ser adicionado no buffer de pedidos
                mutexLF.acquire();      //mutex compartilhado entre Lojas e Fabricantes
                    int k = bufferPedidos[Empresa.primeiroPedidos]; //recebe o index do produto (dentro do catalogo) a ser produzido
                    String id = idLF[Empresa.primeiroPedidos];      //recebe o identificador do pedido a ser produzido
                    Empresa.primeiroPedidos++;                      //remove o produto na fila de pedidos
                    Thread.sleep(fabricar(k)); //tempo de fabricacao do produto
                    System.out.println("Pedido fabricado: " + id + ", Produto "+ Catalogo[k] + ", Pela fabrica: " + this.nome);
                mutexLF.release();      //mutex compartilhado entre Lojas e Fabricantes
                espaco.release();       //informa que o fabricante finalizou mais uma producao e pode continuar recebendo

                mutexFT.acquire();      //mutex compartilhado entre Fabricantes e Transportadoras
                    bufferFabricados[Empresa.ultimoFabricados] = k; //adiciona o produto na fila de transporte
                    idFT[Empresa.ultimoFabricados] = id;            //informa o identificador do pedido (para quem ira transportar)
                    Empresa.ultimoFabricados++;
                    System.out.println("Pedido: " + id  + ", produto: " + Catalogo[k] + ", enviado para transportadora.");
                    System.out.println("Tempo de demora do fabricante para gerar o produto " + Catalogo[k] + ": " + fabricar(k));
                    System.out.println(" ");
                mutexFT.release();      //mutex compartilhado entre Fabricantes e Transportadoras
                fabricados.release();   //sinaliza para as transportadoras que tem pedido a ser transportado

            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
