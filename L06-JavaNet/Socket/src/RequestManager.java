import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.StringTokenizer;

public class RequestManager implements Runnable {
    
    private final Socket client;
    private static final String VERSION = "HTTP/1.0 ";
	private static final String STATUS_SUCCESS = "200 OK";
	private static final String STATUS_NOT_FOUND = "404 Not Found";
	private static final String MESSAGE_ERROR = "File not found";
	private static final String CONTENT_TYPE = "Content-Type: ";
	private static final String CONTENT_LENGTH = "Content-Length: ";
	private static final String CR = "\r\n";

    public RequestManager (Socket client){
        this.client = client;
    }

    @Override
    public void run() {
        // associa gli stream di input ed output al socket
        try (BufferedReader inFromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        DataOutputStream outToClient = new DataOutputStream(client.getOutputStream()))
        {
            String requestMessageLine = inFromClient.readLine();

            if(requestMessageLine == null){
                System.out.println("Message null.\n");
                client.close();
                return;
            }

            StringTokenizer tokenizedLine = new StringTokenizer(requestMessageLine);
            // Stampa la richiesta del client
            System.out.println("Request:\n" + requestMessageLine);

            while (requestMessageLine.length() >= 3){
                System.out.println(requestMessageLine);
                requestMessageLine = inFromClient.readLine();
            }
            System.out.println("#################");

            if (tokenizedLine.nextToken().equals("GET")){           // il client ha effettuato una richiesta GET
                String filename = tokenizedLine.nextToken();

                if(filename.startsWith("/")){
                    filename.substring(1);
                }

                File file = new File(filename);
                
                StringBuilder response = new StringBuilder();
                byte [] messageResponse;

                if(file.exists()){
                    int numOfBytes = (int)file.length();
                    response.append(VERSION).append(STATUS_SUCCESS).append(CR);

                    String mimeType = URLConnection.guessContentTypeFromName(file.getName());
                    if(mimeType == null){
                        response.append(CONTENT_TYPE).append("text/plain").append(CR);
                    }
                    else {
                        response.append(CONTENT_TYPE).append(mimeType).append(CR);
                    }

                    response.append(CONTENT_LENGTH).append(numOfBytes).append(CR);

                    FileInputStream inFile = new FileInputStream(filename);
                    messageResponse = new byte[numOfBytes];

                    inFile.read(messageResponse);
                    inFile.close();
                }
                else {
                    response.append(VERSION).append(STATUS_NOT_FOUND).append(CR);
					response.append(CONTENT_TYPE).append("text/plain").append(CR);
					response.append(CONTENT_LENGTH).append(MESSAGE_ERROR.length()).append(CR);
					messageResponse = MESSAGE_ERROR.getBytes();
                }
                response.append(CR);

                outToClient.writeBytes(response.toString());
                outToClient.write(messageResponse, 0, messageResponse.length);

                System.out.println("------------------------");
				System.out.printf("Response:\n%s%s\n", response.toString(), new String(messageResponse));
				System.out.println("------------------------");

            }
            else {
                System.out.println("Bad Request message");
            }

        } catch (IOException ex){
            ex.printStackTrace();
        }
        finally {
            try{
                client.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
            
    }
}
