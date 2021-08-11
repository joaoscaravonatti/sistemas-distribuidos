package chat;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Servidor {
	public static void main(String[] args) {
		try {
			Scanner keyboard = new Scanner(System.in);
			Chat c = new Chat();
			System.setProperty("java.rmi.server.hostname", "127.0.0.1");
			ChatDistribuido stub = (ChatDistribuido) UnicastRemoteObject.exportObject(c, 0);
			Registry registro = LocateRegistry.createRegistry(12345);
			registro.bind("Chat", stub);

			new Thread(new Runnable() {
				public void run() {
					while (true) {
						try {
							stub.sendMessage("Servidor: " + keyboard.nextLine());
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
							Thread.sleep(500);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
