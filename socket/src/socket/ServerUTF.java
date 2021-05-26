package socket;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerUTF {
	private static Database db = new Database();

	public static String getMenu () {
		String menu = "\n"
				+ "1) Imprimir listas\n"
				+ "2) Imprimir lista\n"
				+ "3) Criar lista\n"
				+ "4) Adicionar item em lista\n"
				+ "5) Remover item de lista\n"
				+ "6) Mostrar último valor adicionado\n"
				+ "7) Fechar conexão\n"
				+ "Opção:";
		return menu;
	}

	public static int convertOption (String option) {
		try {
			return Integer.parseInt(option);
		} catch (NumberFormatException exception) {
			return -1;
		}
	}

	public static void handleShowMenu(DataOutputStream outputStream) throws IOException {
		outputStream.writeUTF(getMenu());
	}

	public static void handlePrintLists (DataOutputStream outputStream) throws IOException {
		outputStream.writeUTF(db.getLists() + "\n" + getMenu());
	}

	public static void handlePrintList (DataInputStream inputStream, DataOutputStream outputStream) throws IOException {
		outputStream.writeUTF("Coloque o nome da lista:");
		String message = inputStream.readUTF();
		outputStream.writeUTF(db.getListValues(message) + "\n" + getMenu());
	}

	public static void handleAddList (DataInputStream inputStream, DataOutputStream outputStream, Database db) throws IOException {
		outputStream.writeUTF("Coloque o nome da lista:");
		String message = inputStream.readUTF();
		db.addList(message);
		outputStream.writeUTF(getMenu());
	}

	public static void handleAddItemToList (DataInputStream inputStream, DataOutputStream outputStream, Database db) throws IOException {
		outputStream.writeUTF("Coloque o nome da lista:");
		String message = inputStream.readUTF();
		String listName = message;
		outputStream.writeUTF("Coloque o valor:");
		message = inputStream.readUTF();
		String value = message;
		db.addItemToList(listName, value);
		outputStream.writeUTF(getMenu());
	}

	public static void handleRemoveItemFromList (DataInputStream inputStream, DataOutputStream outputStream, Database db) throws IOException {
		outputStream.writeUTF("Coloque o nome da lista:");
		String message = inputStream.readUTF();
		String listName = message;
		outputStream.writeUTF("Coloque o valor:");
		message = inputStream.readUTF();
		String value = message;
		db.removeItemFromList(listName, value);
		outputStream.writeUTF(getMenu());
	}

	public static void handleShowLastValue (DataOutputStream outputStream) throws IOException {
		outputStream.writeUTF(db.getLastValue() + "\n" + getMenu());
	}

	public static void handleClose(DataOutputStream outputStream) throws IOException {
		outputStream.writeUTF("bye");
	}

	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(1234);

		System.out.println("Aguardando conexões...");

		Socket connection = server.accept();
		System.out.println("Cliente conectado " + connection);

		DataInputStream inputStream = new DataInputStream(new BufferedInputStream(connection.getInputStream()));
		DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
		
		int option = 0;
		String message;

		while (true) {
			switch (option) {
			case 0:
				handleShowMenu(outputStream);
				break;
			case 1:
				handlePrintLists(outputStream);
				break;
			case 2:
				handlePrintList(inputStream, outputStream);
				break;
			case 3:
				handleAddList(inputStream, outputStream, db);
				break;
			case 4:
				handleAddItemToList(inputStream, outputStream, db);
				break;
			case 5:
				handleRemoveItemFromList(inputStream, outputStream, db);
				break;
			case 6:
				handleShowLastValue(outputStream);
				break;
			default:
				handleClose(outputStream);
				option = -1;
			}

			if (option == -1) {
				break;
			}

			message = inputStream.readUTF();
			option = convertOption(message);
		}

		inputStream.close();
		outputStream.close();
		connection.close();
		server.close();
		System.out.println("Fim de execução");
	}
}
