import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Semaphore;

public class Trabalhadora extends Thread
{
    private int portServ;
    private int[] Tmax;
    private DataOutputStream envia;
    private ArrayList<String> historico;
    private Semaphore mutex;
    private Semaphore mutexHist;

    public Trabalhadora(int portServ, int[] Tmax, DataOutputStream envia, ArrayList<String> historico, Semaphore mutex, Semaphore mutexHist)
    {
        this.portServ = portServ;
        this.Tmax = Tmax;
        this.envia = envia;
        this.historico = historico;
        this.mutex = mutex;
        this.mutexHist = mutexHist;
    }

    public String dataEhora()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
//        System.out.println(formatter.format(date));
        return formatter.format(date);
    }

    public void run()
    {
        try {
            ArrayList<String> recebeLista = new ArrayList<String>();
            String id = "";
            byte[] buffer = new byte[1000];
            DatagramSocket scktLoja = new DatagramSocket(portServ);

            try {
                mutex.acquire();
                scktLoja.setSoTimeout(Tmax[0]);
                mutex.release();

                int i = 0;
                boolean cont = true;
                while (cont)
                {
                    DatagramPacket listaResposta = new DatagramPacket(buffer, buffer.length);

                    scktLoja.receive(listaResposta);
                    id = new String(listaResposta.getData(), 0, 8);
                    String lista = new String(listaResposta.getData(), 8, listaResposta.getLength());
                    System.out.println("Servidor recebeu " + i + " respostas!");
                    i++;
                    recebeLista.add(lista);
                    if (i == 3)
                    {
                        cont = false;
                    }
                }
                scktLoja.close();

                String respostaCon = "";
                for (String s : recebeLista) {
                    respostaCon = respostaCon + s;
                }

                envia.writeUTF(respostaCon); //envia pro consumidor...
                String report = "ID Busca: " + id + "\nResposta:\n" + respostaCon + "Data/Hora: " + dataEhora() + "\n";
                mutexHist.acquire();
                historico.add(report);
                mutexHist.release();

            envia.close();
            } catch (SocketTimeoutException tEx)
            {
                scktLoja.close();
                String respostaCon = "";
                System.out.println(recebeLista.size());
                for (String s : recebeLista)
                {
                    System.out.println("formando resposta");
                    respostaCon = respostaCon + s;
                }

                try {
                    envia.writeUTF(respostaCon); //envia pro consumidor...
                    String report = "ID Busca: " + id + "\nResposta:\n" + respostaCon + "Data/Hora: " + dataEhora();
                    mutexHist.acquire();
                    historico.add(report);
                    mutexHist.release();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }catch (SocketException se)
        {
            se.printStackTrace();
        }
    }
}
