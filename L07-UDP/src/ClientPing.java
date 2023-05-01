import java.io.IOException;
import java.net.*;

public class ClientPing {
    
    private final int BUFFER_SIZE = 4;
    private final String PING = "PING";
    private final byte [] pingBuffer;

    /*
     * porta su cui è in ascolto il server
     */
    private final int serverPort;

    /*
     *  hostname del server 
     */
     private final String serverName;

     /*
      * attesa per la ricezione di una risposta dal server
      */
      private final int timeout;
    
      /**
       * @param port porta su cui è in ascolto il server
       * @param serverName hostname del server
       * @param timeout tempo di attesa per la ricezione di una risposta dal server
       */
      public ClientPing (int port, String serverName, int timeout){
        this.serverPort = port;
        this.serverName = serverName;
        this.timeout = timeout;
        this.pingBuffer = PING.getBytes();
      }

      public void start () {
        try (DatagramSocket clientSocket = new DatagramSocket()){

            DatagramPacket packetToSend = new DatagramPacket(pingBuffer,pingBuffer.length, InetAddress.getByName(this.serverName),this.serverPort);
            System.out.printf("Invio al server la stringa: %s", PING);
            clientSocket.send(packetToSend);
            // imposto il timeout
            clientSocket.setSoTimeout(timeout);

            byte [] buffer = new byte[BUFFER_SIZE];
            DatagramPacket response = new DatagramPacket(buffer,buffer.length);
            clientSocket.receive(response);

            // il client ha ricevuto una risposta dal server
            String msg = new String(response.getData());
            System.out.printf("Ho ricevuto la risposta dal server: %s", msg);
        }
        catch (SocketTimeoutException e) {
            System.out.println("Il client non ha ricevuto una risposta in " + this.timeout + "ms");
        }
        catch (BindException e){
            System.out.println("Porta già occupata.");
        }
        catch (IOException e){
            e.printStackTrace();
        }
      }

}
