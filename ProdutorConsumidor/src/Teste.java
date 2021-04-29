import java.util.concurrent.Semaphore;

public class Teste
{
    public static void main(String[] args)
    {
        Semaphore mutexLF = new Semaphore(1);   // compartilhado pela loja e pela fabrica
        Semaphore mutexFT = new Semaphore(1);   // compartilhado pela fabrica e pela transportadora
        Semaphore pedidos = new Semaphore(0);   // compartilhado pela loja e pela fabrica, avisa a fabrica que um pedido foi concluido e esta pronto para fabricacao
        Semaphore fabricados = new Semaphore(0);// compartilhado pela fabrica e pela transportadora, avisa a transportadora que a fabricaca foi concluida e o pedido pode ser transportado

        int[] bufferPedidos = new int[100];     // fila de pedidos que podem ser fabricados, contendo o indice do produto que foi pedido
        int[] bufferFabricados = new int[100];  // fila de produtos que ja foram fabricados e podem ser transportados, contendo o indice do produto que foi pedido
        String[] idLF = new String[100];        // fila contendo os identificadores do pedido que foi feito e sera fabricado
        String[] idFT = new String[100];        // fila contendo os identificadores do pedido que foi fabricado e sera transportado

        Empresa e = new Empresa("M&M",8,4,2); 

        e.adicionaLoja("A", bufferPedidos,idLF, mutexLF, pedidos);
        e.adicionaLoja("B", bufferPedidos,idLF, mutexLF, pedidos);
        e.adicionaLoja("C", bufferPedidos,idLF, mutexLF, pedidos);
        e.adicionaLoja("D", bufferPedidos,idLF, mutexLF, pedidos);
        e.adicionaLoja("E", bufferPedidos,idLF, mutexLF, pedidos);
        e.adicionaLoja("F", bufferPedidos,idLF, mutexLF, pedidos);
        e.adicionaLoja("G", bufferPedidos,idLF, mutexLF, pedidos);
        e.adicionaLoja("H", bufferPedidos,idLF, mutexLF, pedidos);

        e.adicionaFabricante("A", bufferPedidos, bufferFabricados,idLF, idFT, 4,
                new int[]{600, 200, 1000, 400, 800, 1400, 400, 800}, new int[]{1000, 400, 1200, 600, 1000, 1600, 600, 1000},
                mutexLF, mutexFT, pedidos, fabricados);
        e.adicionaFabricante("B", bufferPedidos, bufferFabricados,idLF,idFT,1,
                new int[]{400, 800, 1200, 800, 200, 1000, 1000, 600}, new int[]{600, 1000, 1400, 1000, 400, 1200, 1200, 800},
                mutexLF, mutexFT, pedidos, fabricados);
        e.adicionaFabricante("C", bufferPedidos, bufferFabricados,idLF,idFT,4,
                new int[]{1000, 1200, 400, 600, 400, 400, 1000, 400}, new int[]{1200, 1400, 600, 800, 600, 600, 1200, 600},
                mutexLF, mutexFT, pedidos, fabricados);
        e.adicionaFabricante("D", bufferPedidos, bufferFabricados,idLF,idFT,4,
                new int[]{800, 600, 400, 1000, 1200, 800, 600, 1200}, new int[]{1000, 800, 600, 1200, 1400, 1000, 800, 1400},
                mutexLF, mutexFT, pedidos, fabricados);

        e.adicionaTransportadora("A", 10, 100, 200, bufferFabricados, idFT, mutexFT, fabricados);
        e.adicionaTransportadora("B", 20, 400, 600, bufferFabricados, idFT, mutexFT, fabricados);

        e.iniciaProcesso();
    }
}
