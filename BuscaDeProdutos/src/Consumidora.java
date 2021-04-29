import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Consumidora
{
    public static String incializa()
    {
        Scanner input = new Scanner(System.in);
        System.out.println( "Seja bem vinde! \n" +
                            "Digite aqui o que está procurando: \n");

        String produto = input.next();
        return produto;
    }

    public static void main(String[] args) {
        try {
            // faz a conexao com o servidor
            Socket sCon = new Socket("127.0.0.1", 7000);
            System.out.println( "Consumidor: conexao feita" );

            DataOutputStream saida = new DataOutputStream( sCon.getOutputStream()); // para enviar
            DataInputStream entrada = new DataInputStream( sCon.getInputStream());  // para receber

            saida.writeUTF("con");

            saida.writeUTF(incializa());
            System.out.println("Procura enviada, aguardando servidor...");

            String resultado = entrada.readUTF();
            System.out.println("Lista de produtos recebida: ");
            System.out.println(resultado);

            entrada.close();
            saida.close();
            sCon.close();
        }
        catch (IOException e) {

            e.printStackTrace();
        }
    }
}
