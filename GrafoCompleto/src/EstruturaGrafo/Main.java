package EstruturaGrafo;

//import EstruturaGrafo.Fila.Fila;

public class Main
{
    public static void main(String[] args)
    {
//        int[] adjacentes = new int[tamanhoGrafo];
//        boolean[] visitadesProf = new boolean[tamanhoGrafo];
//        boolean[] visitadesLarg = new boolean[tamanhoGrafo];
//        for (int i = 0; i<tamanhoGrafo; i++)
//        {
//            visitadesProf[i] = false;
//            visitadesLarg[i] = false;
//        }

        int tamanhoGrafo = 7;

        Grafo G = new Grafo(tamanhoGrafo);
        G.insereGrafo();

        System.out.println("---- LIGANDO OS VERTICES -----");
        /* // ** GRAFO Direcionado ** //*/

        G.cria_adjacencia(0, 6, 1);
        G.cria_adjacencia(1, 6, 2);
        G.cria_adjacencia(2, 1, 3);
        G.cria_adjacencia(3, 5, 4);
        G.cria_adjacencia(4, 3, 5);
        G.cria_adjacencia(5, 0, 6);
        G.cria_adjacencia(6, 2, 7);
        G.cria_adjacencia(6, 4, 8);

        System.out.println("---- ADJACENCIAS ----");
        G.imprime_adjacencias();
        System.out.println();

        System.out.println("---- EULERIANO? ----");
        G.EulerDirecionado();
        System.out.println();

        System.out.println("---- MATRIZ DE FECHAMENTO ----");
        G.fechamento();
        System.out.println();

        System.out.println("---- MATRIZ PESOS MENORES CAMINHOS ----");
        G.imprimePesosMenoresCaminhos();
        System.out.println();
        System.out.println("---- MATRIZ MENORES CAMINHOS ----");
        G.imprimeMenoresCaminhos();
        System.out.println();

        System.out.println("---- CENTRALIDADE DE PROXIMIDADE ----");
        System.out.print(G.centralidadeProximidade(0));
        System.out.println();

        System.out.println("---- CENTRALIDADE DE INTERMEDIACAO ----");
        System.out.print(G.centralidadeIntermediacao(0));
        System.out.println();
    }
}
