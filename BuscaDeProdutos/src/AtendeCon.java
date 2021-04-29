import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Semaphore;

public class AtendeCon extends Thread
{
    private int numCon;
    private ArrayList<String> historico;
    private int[] Tmax;
    private Socket ss;
    private Semaphore mutex, mutexHist;

    public AtendeCon(int numCon, Socket ss, ArrayList<String> historico, int[] Tmax, Semaphore mutex, Semaphore mutexHist)
    {
        this.numCon = numCon;
        this.historico = historico;
        this.Tmax = Tmax;
        this.ss = ss;
        this.mutex = mutex;
        this.mutexHist = mutexHist;
    }

    public static String dataEhora()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
//        System.out.println(formatter.format(date));
        return formatter.format(date);
    }

    @Override
    public void run()
    {
        try
        {
            DataInputStream recebe = new DataInputStream(ss.getInputStream());
            DataOutputStream envia = new DataOutputStream(ss.getOutputStream());

            numCon++; //aumenta o numero de consumidores para gerar um identificador unico

            String identificador = "con_" + numCon; //gera o identificador da busca (de 1001 a 9999)
            String palavra = recebe.readUTF();

            String report = "ID Busca: " + identificador + "   Procura: " + palavra + "    Data/Hora: " + dataEhora();
            historico.add(report);


            // **** COMUNICACAO UDP(multicast) COM GRUPO DE LOJAS ****
            int portGrupo = 7447; // grupo
            InetAddress IPGrupo = InetAddress.getByName("224.0.0.4"); //(UDP) grupo

            MulticastSocket scktGrupo = new MulticastSocket();//socket para se comunicar com o grupo

            String idPalavra = identificador + palavra;
            byte[] bufferEnvio = idPalavra.getBytes();

            DatagramPacket procura = new DatagramPacket(bufferEnvio, bufferEnvio.length, IPGrupo, portGrupo); //datagrama a ser enviado para as lojas

            System.out.println("Enviando mensagem para o grupo ..." /* + idPalavra.length()*/);
            scktGrupo.send(procura);
            System.out.println("Enviada! ");

            // **** COMUNICACAO UDP(unicast) COM GRUPO DE LOJAS ****

//                    InetAddress IPServ = InetAddress.getByName("127.0.0.2"); //(UDP) grupo
            int portServ = 7004;

            Trabalhadora t = new Trabalhadora(portServ, Tmax, envia, historico, mutex, mutexHist); //recebe as reespostas das lojas e envia parao consumidor
            t.start();
            t.join();

            envia.close();
            recebe.close();
        }catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }

    }
}
