
import java.rmi.*;

public interface AddServerInterface extends Remote {
    public void run()throws RemoteException;
}   