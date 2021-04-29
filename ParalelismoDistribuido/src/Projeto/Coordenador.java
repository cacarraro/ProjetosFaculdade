package Projeto;

import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

/* multiplique duas matrizes A(m,k) e B(k,n) produzindo uma matriz C(m,n) como resultado.
Os valores dos elementos das matrizes A e B devem ser números reais (double) lidos de dois arquivos, um para cada matriz. */
public class Coordenador
{
    private static Matriz mtrxA;
    private static Matriz mtrxB;
    private static Matriz mtrxC;

    private static int contador = 0;

    private static Integer inicio1;
    private static Integer fim1;
    private static Integer inicio2;
    private static Integer fim2;
    private static Integer inicio3;
    private static Integer fim3;
    private static Integer inicio4;
    private static Integer fim4;


    /* 1. carrega as matrizes A e B (a partir dos dados dos arquivos previamente gerados)
       2. aloca espaço em memória para armazenar a matriz C  */
    public static void inicializa()
    {
        Random r = new Random();
        int lin = (400); // entre 400 e 1000
        int col = (400); //IGUAL A LINHA DE B
        mtrxA = new Matriz(("mtxA_"  + contador + ".txt"),lin,col);
        mtrxA.geraMatriz();
        int col2 = (400);
        mtrxB = new Matriz(("mtxB_" + contador+ ".txt"),col,col2);
        mtrxB.geraMatriz();

        mtrxC = new Matriz(("mtxC_" + contador), mtrxA.getLinha(), mtrxB.getColuna());
        contador++;
    }

    /* 3. faz a divisão do trabalho de cálculo da matriz C, determinando os intervalos da matriz C que cada Calculador
            deverá resolver, de forma que cada Calculador determine aproximadamente a mesma quantidade de
            elementos da matriz C */
    public static void divide()
    {
        //calcula o tamanho da matriz C resultado
        int tamanho = mtrxA.getLinha() * mtrxB.getColuna();
        //calcula quantos elementos cada calculador deve calcular
        int quantos = tamanho/4;

        //inicio e fim do calculador 1:
        inicio1 = 0 ;
        fim1 = (quantos) -1;        //calcula a parte igualmente dividida

        //inicio e fim do calculador 2:
        inicio2 = quantos;
        fim2 = (2 * quantos) -1;    //calcula a parte igualmente dividida

        //incio e fim do calculador 3:
        inicio3 = 2 * quantos;
        fim3 = (3 * quantos) -1;    //calcula a parte igualmente dividida

        //inicio e fim do calculador 4:
        inicio4 = 3 * quantos;
        fim4 = ((4 * quantos) + (tamanho % 4)) -1; //calcula a parte igualmente dividida MAIS o resto da divisao
    }

    //preenche a matriz no intervalo determinado, e com os valores recebidos dos Calculadores
    public static void preencheMatrizC(int index, double[][] result, int fim, int inicio)
    {
        int tamanho = fim - inicio;
        while (index <= tamanho)
        {
            for (int i = 0; i < mtrxC.getLinha(); i++)
            {
                for (int j = 0; j < mtrxC.getColuna(); j++)
                {
                    if (index >= inicio && index <= fim) // se esta dentro do intervalo que este calculador deve calcular
                    {
                        mtrxC.getMatrix()[i][j] = result[i][j];
                    }
                    index++;
                }
            }
        }
    }

    //gera arquivo da matriz resultado (matriz c)
    public static void geraArrquivoResultado()
    {
        try {
            FileWriter myWriter = new FileWriter(mtrxC.getNome());

            for (int i = 0; i < mtrxC.getLinha(); i++)
            {
                for (int j = 0; j < mtrxC.getColuna(); j++)
                {
                    double k = mtrxC.getMatrix()[i][j];
                    myWriter.write(k + "  ");
                }
            }
            myWriter.close();
            System.out.println("Arquivo final:  " + mtrxC.getNome() + " gerado");
        } catch (IOException e) {
            System.out.println("N foi.");
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        try {
            int k = 0;
            while (k < 1)
            {
                inicializa();

                //Comunicação TCP, pois garante que as mesnsagens sejam recebidas
                //TCP ENVIAR;
                //cria 4 sockets, para comunicar cada calculador qual o inicio e o fim da parte que eles devem calcular
                Socket sckt1 = new Socket("127.0.0.1", 9090);
                Socket sckt2 = new Socket("127.0.0.1", 9091);
                Socket sckt3 = new Socket("127.0.0.1", 9092);
                Socket sckt4 = new Socket("127.0.0.1", 9093);

                // cria as 4 saidas de objetos
                ObjectOutputStream saidaObj1 = new ObjectOutputStream(sckt1.getOutputStream());
                ObjectOutputStream saidaObj2 = new ObjectOutputStream(sckt2.getOutputStream());
                ObjectOutputStream saidaObj3 = new ObjectOutputStream(sckt3.getOutputStream());
                ObjectOutputStream saidaObj4 = new ObjectOutputStream(sckt4.getOutputStream());

                divide();

                /* 4. envia mensagem para cada Calculador contendo a matriz A, a matriz B e os índices que determinam o intervalo
                    a ser calculado (linha inicial, linha final, coluna inicial e coluna final) */
                // envia as informaçoes do calculador 1 para ele:
                saidaObj1.writeObject(mtrxA);
                saidaObj1.writeObject(mtrxB);
                saidaObj1.writeObject(inicio1);
                saidaObj1.writeObject(fim1);
                System.out.println("enviou 1");

                // envia as informaçoes do calculador 2 para ele:
                saidaObj2.writeObject(mtrxA);
                saidaObj2.writeObject(mtrxB);
                saidaObj2.writeObject(inicio2);
                saidaObj2.writeObject(fim2);
                System.out.println("enviou 2");

                // envia as informaçoes do calculador 3 para ele:
                saidaObj3.writeObject(mtrxA);
                saidaObj3.writeObject(mtrxB);
                saidaObj3.writeObject(inicio3);
                saidaObj3.writeObject(fim3);
                System.out.println("enviou 3");

                // envia as informaçoes do calculador 4 para ele:
                saidaObj4.writeObject(mtrxA);
                saidaObj4.writeObject(mtrxB);
                saidaObj4.writeObject(inicio4);
                saidaObj4.writeObject(fim4);
                System.out.println("enviou 4");


                /* 5. aguarda as mensagens com a resposta (parte calculada da matriz C) de cada Calculador,
                    fazendo o preenchimento da matriz C */

                // cria as 4 entradas de objetos
                ObjectInputStream entradaObj1 = new ObjectInputStream(sckt1.getInputStream());
                ObjectInputStream entradaObj2 = new ObjectInputStream(sckt2.getInputStream());
                ObjectInputStream entradaObj3 = new ObjectInputStream(sckt3.getInputStream());
                ObjectInputStream entradaObj4 = new ObjectInputStream(sckt4.getInputStream());

                //recebe matrizes preenchidas no intervalo do Calculador
                double[][] result1 = (double[][]) entradaObj1.readObject();
                System.out.println("GR recebeu 1: ");
//                for (double[] d : result1) {
//                    for (double dd : d) {
//                        System.out.print(dd + " ");
//                    }
//                    System.out.println();
//                }
                int index = 0;
                //preenche matriz C
                preencheMatrizC(index, result1, fim1, inicio1);

                //recebe matrizes preenchidas no intervalo do Calculador
                double[][] result2 = (double[][]) entradaObj2.readObject();
                System.out.println("GR recebeu 2: ");
//                for (double[] d : result2) {
//                    for (double dd : d) {
//                        System.out.print(dd + " ");
//                    }
//                    System.out.println();
//                }
                //preenche matriz C
                preencheMatrizC(index, result2, fim2, inicio2);

                //recebe matrizes preenchidas no intervalo do Calculador
                double[][] result3 = (double[][]) entradaObj3.readObject();
                System.out.println("GR recebeu 3: ");
//                for (double[] d : result3) {
//                    for (double dd : d) {
//                        System.out.print(dd + " ");
//                    }
//                    System.out.println();
//                }
                //preenche matriz C
                preencheMatrizC(index, result3, fim3, inicio3);

                //recebe matrizes preenchidas no intervalo do Calculador
                double[][] result4 = (double[][]) entradaObj4.readObject();
                System.out.println("GR recebeu 4: ");
//                for (double[] d : result4) {
//                    for (double dd : d) {
//                        System.out.print(dd + " ");
//                    }
//                    System.out.println();
//                }

                //preenche matriz C
                preencheMatrizC(index, result4, fim4, inicio4);

//                for (double[] d:mtrxC.getMatrix())
//                {
//                    for (double dd:d)
//                    {
//                        System.out.print(dd + " ");
//                    }
//                    System.out.println();
//                }

                /*  6. gera o arquivo correspondente à matriz C */
                geraArrquivoResultado();

                k++;
                sckt1.close();
                sckt2.close();
                sckt3.close();
                sckt4.close();
            }


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
