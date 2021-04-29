package Package;

public class Teste
{
    public static void main(String[] args)
    {
        Operacoes op = new Operacoes();

        ListaEncadeada X = new ListaEncadeada();
        X.inserePrimeiro(1);
        X.insereUltimo(2);
        X.insereUltimo(3);
        X.insereUltimo(4);
        X.inserePrimeiro(0);
        X.insereDepois(X.getPrimeiro(), 8);
        X.retiraDepois(X.getPrimeiro());
        X.retiraUltimo();

        System.out.println(" Lista Encadeada X: ");
        X.mostraLista();

        System.out.println(" Similaridade entre as Listas Encadadas X e X: ");
        System.out.println(op.similaridade(X,X));

        ListaEncadeada Y = new ListaEncadeada();
//        Y.insereUltimo(3);
        Y.inserePrimeiro(6);
        Y.insereOrdenado(7);
        Y.inserePrimeiro(5);
        Y.insereUltimo(3);
        System.out.println(" Lista Encadeada Y: ");
        Y.mostraLista();

        System.out.println(" Similaridade entre as Listas Encadeadas Y e Y: ");
        System.out.println(op.similaridade(Y,Y));

        System.out.println(" Similaridade entre as Listas Encadeadas X e Y: ");
        System.out.println(op.similaridade(X,Y));

        System.out.println(" Interseção listas Y e X com elementos ordenados:");
        op.intersecao(Y, X);

        System.out.println(" Interseção listas X e X com elementos ordenados:");
        op.intersecao(X, X);

        System.out.println(" Interseção listas Y e Y com elementos ordenados:");
        op.intersecao(Y, Y);

        System.out.println(" Interseção listas X e Y com elementos ordenados:");
        op.intersecao(X, Y);

    }
}
