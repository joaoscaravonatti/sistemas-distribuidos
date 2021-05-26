package socket;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientUTF {

	public static void main(String[] args) throws IOException {
		int port = 1234;
		String ip = "127.0.0.1";
		Scanner scanner = new Scanner(System.in);
		
		Socket connection = new Socket(ip, port);

		DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
		DataInputStream inputStream = new DataInputStream(new BufferedInputStream(connection.getInputStream()));

		while (true) {
			String message = inputStream.readUTF();
			System.out.println(message);
			
			if (message.equals("bye")) {
				break;
			}

			outputStream.writeUTF(scanner.nextLine());
			outputStream.flush();
		}
		
		
		outputStream.close();
		inputStream.close();
		connection.close();
		scanner.close();
	}

}
