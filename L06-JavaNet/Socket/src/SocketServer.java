import java.net.*;
import java.io.*;

public class SocketServer {
    public static void main(String[] args) {
        
        int PORT = 6000;
        String filename = "file.txt";
        
        // Check if a port number is given as the first command line argument
        // If not argument is givent, use port number 6000
        if (args.length > 0){
            try {
                PORT = Integer.parseInt(args[0]);
            } catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Need a port number as argument\n");
                System.exit(-1);
            }
        }

        // set up a connection socket
        try (ServerSocket listenSocket = new ServerSocket(PORT)) {
            // listen socket wait for connection request
            while(true){
                // set up the read and write socket
                try (Socket connectionSocket = listenSocket.accept(); 
                     DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream())){
                        // access the requested file
                        File file = new File(filename);
                        
                        // Convert file to a byte array
                        int numOfBytes = (int) file.length();
                        try (FileInputStream inFile = new FileInputStream(filename)){
                            byte[] fileInBytes = new byte[numOfBytes];
                            inFile.read(fileInBytes);
                            outToClient.write(fileInBytes, 0, numOfBytes);
                            outToClient.flush();
                        } catch (FileNotFoundException ex) {
                            String message1 = "File not found";
                            outToClient.writeBytes(message1);
                        }
                } catch (IOException e) {
                e.printStackTrace();
                }
            }   
        } catch (IOException ex1) {
            ex1.printStackTrace();
        }
    }
}
