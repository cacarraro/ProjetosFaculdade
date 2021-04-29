import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Administradora
{
    static Scanner input = new Scanner(System.in);

    public static String inicializa()
    {

        System.out.println( "Escolha uma opção: \n " +
                            "Digite a para Visualizar o historico de buscas realizadas pelo servidor. \n" +
                            "Digite b para visualizar o valor atual do tempo maximo de espera. \n" +
                            "Digite c para Atualizar o valor do tempo maximo de espera. \n");
        String opcao = input.next();
        return opcao;
    }

    public static void main(String[] args)
    {
        try {
            // faz a conexao com o servidor
            Socket sAdm = new Socket("127.0.0.1", 7000);
            System.out.println( "Administrador: conexao feita" );

            DataOutputStream saida = new DataOutputStream( sAdm.getOutputStream()); // para enviar
            DataInputStream entrada = new DataInputStream( sAdm.getInputStream()); // para receber

            saida.writeUTF("adm"); // se identifica para o servidor

            boolean continua = true;
            while (continua)
            {
                String opcao = inicializa();

                switch (opcao)
                {
                    case "a":
                    {
                        System.out.println("Opção visualizar o historico de buscas escolhida: ");
                        saida.writeUTF("a");

                        System.out.println("Aguardando servidor... ");
                        int quantos = entrada.readInt();

                        ArrayList<String> historico = new ArrayList<>(quantos);
                        for (int i = 0; i < quantos; i++) {
                            historico.add(entrada.readUTF());
                        }

//                        System.out.println("HISTORICO DE BUSCA: ");
                        for (String s : historico) {
                            System.out.println(s);
                        }

                        System.out.println( "Digite d para escolher outra opcao. \n" +
                                            "Digite e para sair. \n");
                        String prox = input.next();
                        if (prox.equals("d"))
                        {
                            saida.writeUTF("d");
                        } else if (prox.equals("e"))
                        {
                            saida.writeUTF("e");
                            continua = false;
                        }
                        break;
                    }
                    case "b":
                    {
                        System.out.println("Opção visualizar o valor atual do tempo maximo de espera escolhida: ");
                        saida.writeUTF("b");

                        System.out.println("Aguardando servidor... ");
                        int Tmax = entrada.readInt();

                        System.out.println("TEMPO MAXIMO ATUAL: " + Tmax);

                        System.out.println( "Digite d para escolher outra opcao. \n" +
                                            "Digite e para sair. \n");
                        String prox = input.next();

                        if (prox.equals("d"))
                        {
                            saida.writeUTF("d");
                        } else if (prox.equals("e"))
                        {
                            saida.writeUTF("e");
                            continua = false;
                        }
                        break;
                    }
                    case "c":
                    {
                        System.out.println("Opção atualizar o valor do tempo maximo de espera escolhida: ");
                        saida.writeUTF("c");

                        System.out.println("Digite o novo valor para o tempo maximo: ");
                        int novoTempo = input.nextInt();
                        saida.writeInt(novoTempo);

                        System.out.println("Aguardando servidor... ");
                        String resposta = entrada.readUTF();

                        System.out.println("TEMPO MAXIMO: " + resposta);

                        System.out.println( "Digite d para escolher outra opcao. \n" +
                                            "Digite e para sair. \n");
                        String prox = input.next();

                        if (prox.equals("d"))
                        {
                            saida.writeUTF("d");
                        } else if (prox.equals("e"))
                        {
                            saida.writeUTF("e");
                            continua = false;
                        }
                        break;
                    }

                    default:
                        System.out.println("Opção inválida, escolha novamente!!");
                        break;
                }
            }
           entrada.close();
           saida.close();
           sAdm.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
