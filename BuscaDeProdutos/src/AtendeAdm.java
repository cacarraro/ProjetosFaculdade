import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class AtendeAdm extends Thread
{
    private ArrayList<String> historico;
    private int[] Tmax;
    private Socket ss;
    private Semaphore mutex, mutexHist;

    public AtendeAdm(Socket ss, ArrayList<String> historico, int[] Tmax, Semaphore mutex, Semaphore mutexHist)
    {
        this.ss = ss;
        this.historico = historico;
        this.Tmax = Tmax;
        this.mutex = mutex;
        this.mutexHist = mutexHist;
    }

    @Override
    public void run()
    {
        try {
            DataInputStream recebe = new DataInputStream(ss.getInputStream());
            DataOutputStream envia = new DataOutputStream(ss.getOutputStream());

            boolean continuaAdm = true;
            while (continuaAdm) {
                String opcaoAdm = recebe.readUTF();

                switch (opcaoAdm) {
                    case "a": {
                        System.out.println("Adm quer visualizar o historico de buscas");

                        mutexHist.acquire();
                        envia.writeInt(historico.size());

                        for (String s : historico) {
                            envia.writeUTF(s);
                        }
                        mutexHist.release();
                        System.out.println("HISTORICO ENVIADO À ADM ");

                        String prox = recebe.readUTF();
                        if (prox.equals("e")) {
                            continuaAdm = false;
                        }

                        break;
                    }
                    case "b": {
                        System.out.println("Adm quer visualizar o valor atual do tempo maximo de espera");

                        mutex.acquire();
                        envia.writeInt(Tmax[0]);
                        mutex.release();

                        System.out.println("TEMPO MAXIMO ATUAL ENVIADO À ADM ");

                        String prox = recebe.readUTF();
                        if (prox.equals("e")) {
                            continuaAdm = false;
                        }

                        break;
                    }
                    case "c": {
                        System.out.println("Adm quer atualizar o valor do tempo maximo de espera");
                        int novoTempo = recebe.readInt();
                        mutex.acquire();
                        Tmax[0] = novoTempo;
                        mutex.release();

                        envia.writeUTF("ATUALIZADO! ");

                        System.out.println("TEMPO MAXIMO ATUALIZADO PELA ADM ( " + novoTempo + " )");

                        String prox = recebe.readUTF();
                        if (prox.equals("e")) {
                            continuaAdm = false;
                        }
                        break;
                    }
                }
            }
            envia.close();
            recebe.close();
        }catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
