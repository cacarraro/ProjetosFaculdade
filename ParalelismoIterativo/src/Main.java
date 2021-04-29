import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Semaphore;

public class Main
{
    public static void iniciaCalculo(int p, int quantos, int tamanho, Matriz matrizA, Matriz matrizB, Matriz matrizC, Semaphore acabou)
    {
        for (int h = 0; h < p; h++)
        {
            int inicio = h * quantos;
            int fim = ((h + 1) * quantos)-1;
            if (h == p-1)
            {
                int fimU = ((h + 1) * quantos) + (tamanho % p) -1;
                Calculo c = new Calculo(h, matrizA, matrizB, matrizC, inicio, fimU, acabou);
                c.start();
            }else {
                Calculo c = new Calculo(h, matrizA, matrizB, matrizC, inicio, fim, acabou);
                c.start();
            }
        }
    }

    public static void geraResultado(Matriz matrizC)
    {
        try {
            FileWriter myWriter = new FileWriter(matrizC.nome);

            for (int i = 0; i < matrizC.linha; i++)
            {
                for (int j = 0; j < matrizC.coluna; j++)
                {
                    double k = matrizC.matrix[i][j];
                    myWriter.write(k + "  ");
                }
            }
            myWriter.close();
            System.out.println("Arquivo final:  " + matrizC.nome + " gerado");
        } catch (IOException e) {
            System.out.println("N foi.");
            e.printStackTrace();
        }
    }
    public static void main(String[] args)
    {
        Semaphore acabou = new Semaphore(0);

        /* NUMERO DE PROCESSADORES */
        int p = Runtime.getRuntime().availableProcessors()/2; //é dividido pode 2 pois a funcao conta os processadores logicos tambem, dobrando o valor
        System.out.println("Processadores: " + p);

        /* PRIMEIRA EXECUÇÃO !!! */
        String nomeA1 = "MtxA1.txt";
        int linhaA1 = 1000;
        int colunaA1 = 1000;

        String nomeB1 = "MtxB1.txt";
        int linhaB1 = colunaA1;
        int colunaB1 = 1000;

        String nomeC1 = "MtxC1.txt";

        Matriz matrizA1 = new Matriz(nomeA1, linhaA1, colunaA1);
        Matriz matrizB1 = new Matriz(nomeB1, linhaB1, colunaB1);
        Matriz matrizC1 = new Matriz(nomeC1, linhaA1, colunaB1);

        /* A e B devem ser números reais (double) lidos de dois arquivos */
        matrizA1.geraMatriz();
        matrizB1.geraMatriz();

        /* 2. Cada uma das threads deve ser responsável por calcular aproximadamente a mesma quantidade de elementos da matriz C.
            Isto é, cada thread deve calcular aproximadamente (m.n)/p elementos. */
        int tamanho1 = linhaA1 * colunaB1;
        int quantos1 = tamanho1/p;

        iniciaCalculo(p, quantos1, tamanho1, matrizA1, matrizB1, matrizC1, acabou);
        try {
            acabou.acquire(p);
            geraResultado(matrizC1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /* SEGUNDA EXECUÇÃO !!! */
        String nomeA2 = "MtxA2.txt";
        int linhaA2 = 2000;
        int colunaA2 = 2000;

        String nomeB2 = "MtxB2.txt";
        int linhaB2 = colunaA2;
        int colunaB2 = 2000;

        String nomeC2 = "MtxC2.txt";

        Matriz matrizA2 = new Matriz(nomeA2, linhaA2, colunaA2);
        Matriz matrizB2 = new Matriz(nomeB2, linhaB2, colunaB2);
        Matriz matrizC2 = new Matriz(nomeC2, linhaA2, colunaB2);

        /* A e B devem ser números reais (double) lidos de dois arquivos */
        matrizA2.geraMatriz();
        matrizB2.geraMatriz();

        /* 2. Cada uma das threads deve ser responsável por calcular aproximadamente a mesma quantidade de elementos da matriz C.
            Isto é, cada thread deve calcular aproximadamente (m.n)/p elementos. */
        int tamanho2 = linhaA2 * colunaB2;
        int quantos2 = tamanho2/p;

        iniciaCalculo(p, quantos2, tamanho2, matrizA2, matrizB2, matrizC2, acabou);
        try {
            acabou.acquire(p);
            geraResultado(matrizC2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /* TERCEIRA EXECUÇÃO !!! */
        String nomeA3 = "MtxA3.txt";
        int linhaA3 = 1000;
        int colunaA3 = 2000;

        String nomeB3 = "MtxB3.txt";
        int linhaB3 = colunaA3;
        int colunaB3 = 1000;

        String nomeC3 = "MtxC3.txt";

        Matriz matrizA3 = new Matriz(nomeA3, linhaA3, colunaA3);
        Matriz matrizB3 = new Matriz(nomeB3, linhaB3, colunaB3);
        Matriz matrizC3 = new Matriz(nomeC3, linhaA3, colunaB3);

        /* A e B devem ser números reais (double) lidos de dois arquivos */
        matrizA3.geraMatriz();
        matrizB3.geraMatriz();

        /* 2. Cada uma das threads deve ser responsável por calcular aproximadamente a mesma quantidade de elementos da matriz C.
            Isto é, cada thread deve calcular aproximadamente (m.n)/p elementos. */
        int tamanho3 = linhaA3 * colunaB3;
        int quantos3 = tamanho3/p;

        iniciaCalculo(p, quantos3, tamanho3, matrizA3, matrizB3, matrizC3, acabou);
        try {
            acabou.acquire(p);
            geraResultado(matrizC3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /* QUARTA EXECUÇÃO !!! */
        String nomeA4 = "MtxA4.txt";
        int linhaA4 = 2000;
        int colunaA4 = 4000;

        String nomeB4 = "MtxB4.txt";
        int linhaB4 = colunaA4;
        int colunaB4 = 2000;

        String nomeC4 = "MtxC4.txt";

        Matriz matrizA4 = new Matriz(nomeA4, linhaA4, colunaA4);
        Matriz matrizB4 = new Matriz(nomeB4, linhaB4, colunaB4);
        Matriz matrizC4 = new Matriz(nomeC4, linhaA4, colunaB4);

        /* A e B devem ser números reais (double) lidos de dois arquivos */
        matrizA4.geraMatriz();
        matrizB4.geraMatriz();

        /* 2. Cada uma das threads deve ser responsável por calcular aproximadamente a mesma quantidade de elementos da matriz C.
            Isto é, cada thread deve calcular aproximadamente (m.n)/p elementos. */
        int tamanho4 = linhaA4 * colunaB4;
        int quantos4 = tamanho4/p;

        iniciaCalculo(p, quantos4, tamanho4, matrizA4, matrizB4, matrizC4, acabou);
        try {
            acabou.acquire(p);
            geraResultado(matrizC4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
