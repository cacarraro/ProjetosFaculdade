package Projeto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/* responsável por calcular parte da matriz C  */
public class Calculador_1 {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(9090);

            int k = 0;
            while (k < 1)
            {
                //TCP RECEBER
                System.out.println("aguardando conexao");
                Socket socket = server.accept();
                System.out.println("conexao feita");

                // cria o leitor para receber os dados
                ObjectInputStream leitor = new ObjectInputStream(socket.getInputStream());

                /* recebe mensagem contendo: a matriz A, a matriz B e os índices que determinam o intervalo a ser calculado */
                Matriz mtrxA = (Matriz) leitor.readObject();
                System.out.println("recebeu matriz a 1");
                Matriz mtrxB = (Matriz) leitor.readObject();
                System.out.println("recebeu matriz b 1");
                Integer inicio = (Integer) leitor.readObject();
                System.out.println("recebeu inicio 1: " + inicio);
                Integer fim = (Integer) leitor.readObject();
                System.out.println("recebeu fim 1: " + fim);

                /* cria e inicia a Thread que faz a multiplicacao no intervalo desejado */
                TrabCalcula t = new TrabCalcula(mtrxA, mtrxB, inicio, fim, socket);
                t.start();

                k++;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
