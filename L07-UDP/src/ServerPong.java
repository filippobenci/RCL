import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class ServerPong {
    
    private final int BUFFER_SIZE = 4;
    private final String REPLY = "PONG";
    private final byte[] replyBuffer;

    // Port su cui è in ascolto il server
    private final int port;

    // hostname del server 
    private final String serverName;

    /**
     * @param p porta su cui è in ascolto il server
     * @param hostname nome del server
     */
    public ServerPong (int p, String hostname){
        this.port = p;
        this.serverName = hostname;
        this.replyBuffer = REPLY.getBytes();
    }

    /*
     * avvia il server
     */
    public void start (){
        try (DatagramSocket serverSocket = new DatagramSocket(port)){
            byte[] buffer = new byte[BUFFER_SIZE];
            DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);
            while (true){
                serverSocket.receive(receivedPacket);
                String msg = new String (receivedPacket.getData());

                System.out.printf("Il server ha ricevuto il messaggio: %s\n", msg);

                if(msg == "PING"){
                    DatagramPacket packetToSend = new DatagramPacket(replyBuffer, replyBuffer.length, InetAddress.getByName(serverName), receivedPacket.getPort());
                    serverSocket.send(packetToSend);
                }
            }
        }catch (BindException ex){
            System.out.println("Porta già occupata.");;
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
