package chat;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ChatDistribuido extends Remote{
    public void sendMessage(String message) throws RemoteException;
    public ArrayList<String> getMessages() throws RemoteException;
    public int getMessagesCount() throws RemoteException;
    public String getLastMessage() throws RemoteException;
}
