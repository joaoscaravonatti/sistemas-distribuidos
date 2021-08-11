package chat;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class Chat implements ChatDistribuido{
    private ArrayList<String> messages = new ArrayList<String>();

    public ArrayList<String> getMessages() throws RemoteException {
    	return this.messages;
    }

    public void sendMessage(String message) throws RemoteException {
    	this.messages.add(message);
    }
    
    public int getMessagesCount() throws RemoteException {
    	return this.messages.size();
    }

    public String getLastMessage() throws RemoteException {
    	return this.messages.get(this.messages.size() - 1);
    }
}