import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Servidor
{
    private static int[] Tmax = {2000};

    private static ArrayList<String> historico = new ArrayList<>();
    private static int numCon = 1000;  //para gerar o identificador da busca (de 1001 a 9999)
    private static Semaphore mutex = new Semaphore(1);
    private static Semaphore mutexHist = new Semaphore(1);


    public static void main(String[] args) throws InterruptedException {
        try
        {
            mutexHist.acquire();
            historico.add("     HISTORICO DE BUSCA:\n");
            mutexHist.release();

            ServerSocket scktConAdm = new ServerSocket( 7000 );
            // ** (TCP) **
            while(true)
            {
                System.out.println("Servidor aguardando conexão... ");
                Socket ss = scktConAdm.accept();
                DataInputStream recebe = new DataInputStream(ss.getInputStream());

                String id = recebe.readUTF();   //recebe quem quer se comunicar com o servidor

                System.out.println("Servidor: conexao com "+ id + " realizada! ");

                // **** COMUNICACAO TCP COM A ADMINISTRADORA ****
                if (id.equals("adm"))
                {
                    AtendeAdm atdAdministrador = new AtendeAdm(ss,historico, Tmax, mutex, mutexHist);
                    atdAdministrador.start();
                }
                // **** COMUNICACAO TCP COM A CONSUMIDORA ****
                else if (id.equals("con"))
                {
                    AtendeCon atdConsumidor = new AtendeCon(numCon, ss, historico, Tmax, mutex, mutexHist);
                    atdConsumidor.start();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}