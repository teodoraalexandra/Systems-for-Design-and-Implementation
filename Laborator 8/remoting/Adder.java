
import java.rmi.*;
import java.rmi.server.*;

public class Adder extends UnicastRemoteObject implements AddServerInterface {
	Adder() throws RemoteException {
		super();
	}

	public void run() {
		System.out.println("dauwduyasdusdasjgf");
	}
}