import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

public class Loja2
{
    private static Produto mesa = new Produto("mesa de centro de sala estar", 200);
    private static Produto tv = new Produto("televisao led full hd", 500);
    private static Produto microfone = new Produto("microfone lapela", 90);

    private static Produto[] catalogo = {mesa, tv, microfone};

    private static ArrayList<String> encontraProduto(String palavraParcial) {
        ArrayList<String> catalogoEnvia = new ArrayList<>();

        System.out.println("comecando encotrar produtos: " + palavraParcial);
        for (Produto p: catalogo)
        {
            String compara = p.getNome();

            String[] palabras = compara.split(" ");

            for (String s: palabras)
            {
                if (s.equals(palavraParcial))
                {
                    String prd = (p.getNome() + ": " + p.getPreco());
                    System.out.println("adicionou: " + p.getNome() + ", " + p.getPreco());
                    catalogoEnvia.add(prd);
                }
            }
        }
        return catalogoEnvia;
    }

    public static String removeEspaco(char[] aRemover) {
        String s = new String();
        int si = 0;
        for (char c:aRemover)
        {
            si = c;
            if (si >= 97 && si <= 122)
            {
//                System.out.println(c);
                s += c;
            }
        }
        return s;
    }


    public static void main(String[] args)
    {
        while (true) {
            try {
                // **** COMUNICACAO UDP(multicast) COM SERVIDOR (recebe a palavra parcial para procurar o produto) ****

                int portGrupo = 7447; // grupo
                InetAddress IPGrupo = InetAddress.getByName("224.0.0.4"); //(UDP) grupo
                MulticastSocket scktGrupo = new MulticastSocket(portGrupo);
                scktGrupo.joinGroup(IPGrupo);

                byte[] bufferRecebe = new byte[1000]; //buffer para armazenamento de bytes. (UDP)
                DatagramPacket procura = new DatagramPacket(bufferRecebe, bufferRecebe.length);
                System.out.println("Recebendo palavra para procurar produto...");
                scktGrupo.receive(procura);

                String id = new String(procura.getData(), 0, 8); //pega o id
                String palavraRecebida = new String(procura.getData(), 8, procura.getLength());

                System.out.println("Recebeu: " + id + " " + palavraRecebida); // recebe a comunicacao do servidor, com a a palavra

                char[] pp = palavraRecebida.toCharArray();

                String palavraSemEspaco = removeEspaco(pp);


                // **** COMUNICACAO UDP(unicast) COM SERVIDOR (envia os produtos encontrados) ****
                ArrayList<String> respostaDividida = encontraProduto(palavraSemEspaco);

                String resposta = id + " Loja 2: \n"; //envia primeiro qual é o pedido e a loja, depois os produtos....
                for (String s : respostaDividida)
                {
                    resposta = (resposta + s + "\n");
                }

                InetAddress IPServ = InetAddress.getByName("127.0.0.2"); //(UDP) grupo
                int portServ = 7004;
                DatagramSocket scktServ = new DatagramSocket();
                byte[] bufferResponde = resposta.getBytes();
                DatagramPacket listaEnvio = new DatagramPacket(bufferResponde, bufferResponde.length, IPServ, portServ);
                scktServ.send(listaEnvio); //envia a lista de produtos para o server  (UDP unicast)
                System.out.println("Lista enviada! " + resposta);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}