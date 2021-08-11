package chat;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Cliente {
	public static ChatDistribuido stub;

	public static void main(String[] args) {
		try {
			Scanner keyboard = new Scanner(System.in);
			Registry registro = LocateRegistry.getRegistry("127.0.0.1", 12345);
			stub = (ChatDistribuido) registro.lookup("Chat");
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						try {
							stub.sendMessage("Cliente: " + keyboard.nextLine());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}).start();

			new Thread(new Runnable() {
				public void run() {
					try {
						int lastCount = stub.getMessagesCount();
						while (true) {
							int currentCount = stub.getMessagesCount();
							if (lastCount != currentCount) {
								System.out.println(stub.getLastMessage());
								lastCount = currentCount;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}).start();
		} catch (Exception e) {
			System.err.println("erro: " + e.toString());
		}
	}
}
