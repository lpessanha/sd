/**
 * 
 */
package servidor;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Luiz Felipe
 *
 */
public class Servidor implements LeitorEscritorRemoteInterface {

	/**
	 * 
	 */
	public Servidor() {
	}

	/* (non-Javadoc)
	 * @see servidor.LeitorEscritorRemoteInterface#escrever(java.lang.String)
	 */
	@Override
	public void escrever(String texto) throws RemoteException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see servidor.LeitorEscritorRemoteInterface#ler()
	 */
	@Override
	public void ler() throws RemoteException {
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/**
		 * Security Manager para proteger o sistema de acesso
		 * não autorizado a recursos desnecessários. Garante que operações
		 * realiazadas pelo código baixado, estão sujeitas a uma política de 
		 * segurança
		 */
		
		if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "Servidor";
            LeitorEscritorRemoteInterface sv = new Servidor();
            /**
             * exporta o objeto para que ele possa receber chamadas remotas.
             * retorna um stub
             */
            LeitorEscritorRemoteInterface stub =
                (LeitorEscritorRemoteInterface) UnicastRemoteObject.exportObject(sv, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("Servidor pronto!");
        } catch (Exception e) {
            System.err.println("Servidor exception:");
            e.printStackTrace();
        }

	}

}
