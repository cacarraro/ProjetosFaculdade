import java.util.concurrent.Semaphore;

public class Calculo extends Thread
{
    private int id;

    protected Matriz matrizA;
    protected Matriz matrizB;
    protected Matriz matrizC;
    protected int inicio;
    protected int fim;

    protected Semaphore acabou;

    public Calculo(int id, Matriz matrizA, Matriz matrizB, Matriz matrizC, int inicio, int fim, Semaphore acabou)
    {
        this.id = id;

        this.matrizA = matrizA;
        this.matrizB = matrizB;
        this.matrizC = matrizC;
        this.inicio = inicio;
        this.fim = fim;

        this.acabou = acabou;
    }

    //faz a multiplicacao das matrizes dentro do intervalo proposto e preenche a matrizC multiplicada
    public void multiplicacao()
    {
        if (matrizA.coluna == matrizB.linha)
        {
            int tamanho = fim - inicio;

            int n = 0;
            while (n <= tamanho)
            {
                for (int i = 0; i < matrizC.linha; i++)
                {
                    for (int j = 0 /*cInicio*/; j < /*cFim*/ matrizC.coluna; j++)
                    {
                        if (n >= inicio && n <= fim)
                        {
                            for (int k = 0; k < matrizA.coluna; k++)
                            {
                                matrizC.matrix[i][j] += (matrizA.matrix[i][k] * matrizB.matrix[k][j]);
                            }
                        }
                        n++;
                    }
                }
            }
        }
    }


    public void run()
    {
        try {
            System.out.println("Thread: " + id + " iniciando...");
//            System.out.println("id: " + id + " inicio: " + inicio + " fim: " + fim);
//            System.out.println("tamanho: " + (fim - inicio));
            multiplicacao();
            Thread.sleep(100);
            acabou.release();
            System.out.println("Thread: " + id + " finalizando.");

        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
