public class main
{
    public static void informaVazia(Pilha p)
    {
        if (p.vazia()){
            System.out.println("Pilha vazia!");
        } else {
            System.out.println("Pilha contem elementos!");
        }
    }

    public static void  desempilhaTudo(Pilha p){
        while (!p.vazia()){
            System.out.println(p.desempilha());
        }
    }
    public static void main(String[] args)
    {
        PilhaEstatica p = new PilhaEstatica(10);
        p.empilha(10);
        p.empilha(-20);
        desempilhaTudo(new PilhaEstatica(p));
        informaVazia(new PilhaImutavel(p));
        p.empilha(30);
//        informaVazia(p);
        desempilhaTudo(p.clone());
        informaVazia(new PilhaImutavel(p));
    }
}
