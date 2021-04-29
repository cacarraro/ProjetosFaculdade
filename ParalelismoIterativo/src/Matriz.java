import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Matriz
{
    protected String nome;
    protected int linha;
    protected int coluna;
    protected int numElementos;

    protected double[][] matrix;

    protected Random r = new Random();

    public Matriz(String nome, int linha, int coluna)
    {
        this.nome = nome;
        this.linha = linha;
        this.coluna = coluna;
        this.numElementos = this.linha * this.coluna;
        this.matrix = new double[linha][coluna];
    }

    //gera um arquivo do tamanho do numero de elementos com numeros aleatorios grandes (de 0 até 100000)
    private void geraArquivoAleatorio()
    {
        // escrever no arquivo txt
        try {
            FileWriter myWriter = new FileWriter(this.nome);
            int k = 0;
            while (k < numElementos)
            {
                double i = r.nextInt((int) Math.pow(10,2));
                myWriter.write(i + "  ");
                k++;
            }
            myWriter.close();
            System.out.println("Arquivo matriz:  " + this.nome + " gerado" );
        } catch (IOException e) {
            System.out.println("N foi.");
            e.printStackTrace();
        }
    }

    //le o arquivo e gera uma matriz com seus dados
    public void geraMatriz()
    {
        geraArquivoAleatorio();

        try (Scanner s = new Scanner(new FileReader(this.nome)))
        {
            while (s.hasNext())
            {
                for (int i = 0; i < linha; i++)
                {
                    for (int j = 0; j < coluna; j++)
                    {
                        matrix[i][j] = s.nextDouble();
                    }
                }
            }
        }catch (FileNotFoundException e)
        {
            System.out.println("N achou.");
            e.printStackTrace();
        }
    }
}
