package Projeto;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Matriz implements Serializable {
    private final String nome;
    private final int linha;
    private final int coluna;
    private final int numElementos;

    private final double[][] matrix;

    protected Random r = new Random();

    public Matriz(String nome, int linha, int coluna) {
        this.nome = nome;
        this.linha = linha;
        this.coluna = coluna;
        this.numElementos = this.linha * this.coluna;
        this.matrix = new double[linha][coluna];
    }

    //gera um arquivo do tamanho do numero de elementos com numeros aleatorios grandes (de 0 até 100000)
    private void geraArquivoAleatorio() {
        // escrever no arquivo txt
        try {
            FileWriter myWriter = new FileWriter(this.nome);
            int k = 0;
            while (k < numElementos) {
                double i = r.nextInt((int) Math.pow(10, 2));
                myWriter.write(i + "   ");
                k++;
            }
            myWriter.close();
            System.out.println("Arquivo matriz:  " + this.nome + " gerado");
        } catch (IOException e) {
            System.out.println("N foi.");
            e.printStackTrace();
        }
    }

    //le o arquivo e gera uma matriz com seus dados
    public void geraMatriz() {
        geraArquivoAleatorio();

        try (Scanner s = new Scanner(new FileReader(this.nome))) {
            while (s.hasNext()) {
                for (int i = 0; i < linha; i++) {
                    for (int j = 0; j < coluna; j++) {
                        matrix[i][j] = s.nextDouble();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("N achou.");
            e.printStackTrace();
        }
    }

    public int getLinha(){
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public String getNome()
    {
        return nome;
    }

    public double[][] getMatrix() {
        return matrix;
    }
}
