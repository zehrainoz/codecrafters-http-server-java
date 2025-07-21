import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
  public static void main(String[] args) {

    System.out.println("Logs from your program will appear here!");

    try {
      ServerSocket serverSocket = new ServerSocket(4221);

    //   // Since the tester restarts program quite often, setting SO_REUSEADDR
    //   // ensures that we don't run into 'Address already in use' errors
      serverSocket.setReuseAddress(true);

      // Wait for connection from client.
      Socket clientSocket = serverSocket.accept();
      System.out.println("accepted new connection");

      BufferedReader requestReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      String request = requestReader.readLine();

      //Divide http request to get request line
      String[] requestParts = request.split("\r\n");

      //Divide request line to get URL path
      String[] requestLine = requestParts[0].split(" ");

      String pathURL = requestLine[1];

      OutputStream outputStream = clientSocket.getOutputStream();
      String response;

      if(pathURL.equals("/")){
        response = "HTTP/1.1 200 OK\r\n\r\n";
      }
      else{
        response = "HTTP/1.1 404 Not Found\r\n\r\n";
      }

      outputStream.write(response.getBytes());
      outputStream.flush();
      clientSocket.close();

      //Thread.sleep(5000);

    } catch (IOException e) {
       System.out.println("IOException: " + e.getMessage());
    }
  }
}
