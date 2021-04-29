import java.util.concurrent.Semaphore;

public class Empresa
{
    private String nome;                                    //nome da empresa
    private Loja[] lojasHabilitadas;                        //conjunto de lojas habilitadas para vender os produtos do catalogo
    private Fabricante[] fabricantesConveniados;            //conjunto de fabricantes habilitados para fabricar os produtos do catalogo
    private Transportadora[] transportadorasConveniadas;    //conjunto de transportadora habilitadas para transportar os pedidos
    protected String[]Catalogo;                             //catalogo contendo os produtos que a empresa oferece

    private int numLojas, numFabricantes, numTransportadoras;   //numero maximo de lojas, fabricantes e transportadoras conveniadas
    private int indexLoja = 0;                                  //mantem o controle de quantas lojas estao conveniadas
    private int indexFabricantes = 0;                           //mantem o controle de quantos fabricantes estao conveniados
    private int indexTransportadoras = 0;                       //mantem o controle de quantas transportadoras estao conveniadas

    static int primeiroPedidos = 0;             //mantem o controle da fila de pedidos
    static int ultimoPedidos = 0;               //mantem o controle da fila de pedidos

    static int primeiroFabricados = 0;          //mantem o controle da fila de fabricados
    static int ultimoFabricados = 0;            //mantem o controle da fila de fabricados

    public Empresa(String nome, int numLojas, int numFabricantes, int numTransportadoras){
        this.nome = nome;
        Catalogo = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};

        this.numLojas = numLojas;
        this.numFabricantes = numFabricantes;
        this.numTransportadoras = numTransportadoras;

        lojasHabilitadas = new Loja[numLojas];
        fabricantesConveniados = new Fabricante[numFabricantes];
        transportadorasConveniadas = new Transportadora[numTransportadoras];
    }

//    adiciona um novo convenio com uma loja
    protected void adicionaLoja(String nome, int[] bufferPedidos, String[] idLF, Semaphore mutexLF, Semaphore pedidos)
    {
        Loja loja = new Loja(nome, this.Catalogo, bufferPedidos, idLF, mutexLF,pedidos);
        lojasHabilitadas[indexLoja] = loja;
        indexLoja++;
    }

//    adiciona um novo convenio com um fabricante
    protected void adicionaFabricante(String nome, int[] bufferPedidos, int[] bufferFabricados, String[] idLF, String[] idFT, int limite, int[] tempoMin, int[] tempoMax,
                                      Semaphore mutexLF, Semaphore mutexFT, Semaphore pedidos, Semaphore fabricados)
    {
        Fabricante fabricante = new Fabricante(nome, this.Catalogo, bufferPedidos, bufferFabricados, idLF, idFT, limite, tempoMin, tempoMax,
                mutexLF, mutexFT, pedidos, fabricados);
        fabricantesConveniados[indexFabricantes] = fabricante;
        indexFabricantes++;
    }

//    adiciona um novo convenio com uma transportadora
    protected void adicionaTransportadora(String nome, int limite, int tempoMin, int tempoMax, int[] bufferFabricados, String[] idFT,
                                          Semaphore mutexFT, Semaphore fabricados)
    {
        Transportadora transportadora = new Transportadora(nome, limite, tempoMin, tempoMax, bufferFabricados, idFT, mutexFT, fabricados);
        transportadorasConveniadas[indexTransportadoras] = transportadora;
        indexTransportadoras++;
    }

//    inicia o funcionamento das vendas
    public void iniciaProcesso()
    {
        for (Loja l:lojasHabilitadas)
        {
            l.start();
        }
        for (Fabricante f: fabricantesConveniados)
        {
            f.start();
        }
        for (Transportadora t:transportadorasConveniadas)
        {
            t.start();
        }
    }
}
