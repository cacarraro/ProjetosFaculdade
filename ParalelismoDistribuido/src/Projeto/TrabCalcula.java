package Projeto;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TrabCalcula extends Thread
{
    private final Matriz mtrxA;
    private final Matriz mtrxB;
    private final int inicio;
    private final int fim;

    private final Socket socket;

    private final double[][] result;

    public TrabCalcula(Matriz mtrxA, Matriz mtrxB, int inicio, int fim, Socket socket)
    {
        this.mtrxA = mtrxA;
        this.mtrxB = mtrxB;
        this.inicio = inicio;
        this.fim = fim;

        this.socket = socket;

        result = new double[mtrxA.getLinha()][mtrxB.getColuna()];
    }

    public void multiplica()
    {
        System.out.println("multiplicando... ");
        if (mtrxA.getColuna() == mtrxB.getLinha())
        {
            int tamanho = fim - inicio; //encontra o numero de elementos que este calculador vai calcular

            int n = 0;
            while (n <= tamanho) // para quando chegar no ultimo elemento que este claculador precisa calcular
            {
                for (int i = 0; i < mtrxA.getLinha(); i++) // numero de linhas de mtrxC (igual ao numero de linhas de mtrxA)
                {
                    for (int j = 0; j<mtrxB.getColuna(); j++) // numero de colunas de mtrxC (igual ao numero de colunas de mtrxB)
                    {
                        if (n >= inicio && n <= fim) // se esta dentro do intervalo que este calculador deve calcular
                        {
                            for (int k = 0; k < mtrxA.getColuna(); k++) //faz a multiplicação dessa parte da matriz
                            {
                                result[i][j] += (mtrxA.getMatrix()[i][k] * mtrxB.getMatrix()[k][j]);
                            }
                        }
                        n++; //contagem dos elementos
                    }
                }
            }
        }
    }

    public void run()
    {
        try
        {
            System.out.println("\nTRABALHADORA CALCULA...");

            System.out.println("\nMatriz a");
//            for (double[] d:mtrxA.getMatrix())
//            {
//                for (double dd: d)
//                {
//                    System.out.print(dd + " ");
//                }
//                System.out.println();
//            }

            System.out.println("\nMatriz b");

//            for (double[] d:mtrxB.getMatrix())
//            {
//                for (double dd: d)
//                {
//                    System.out.print(dd + " ");
//                }
//                System.out.println();
//            }

            multiplica();

            System.out.println();
//            for (double[] d:result)
//            {
//                for (double dd: d)
//                {
//                    System.out.print(dd + " ");
//                }
//                System.out.println();
//            }
//            System.out.println();

            //cria a saida de objeto
            ObjectOutputStream envia = new ObjectOutputStream(socket.getOutputStream());
            /* envia mensagem com a resposta (parte calculada da matriz C) */
            envia.writeObject(result);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
