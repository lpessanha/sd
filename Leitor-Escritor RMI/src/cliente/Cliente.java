/**
 * 
 */
package cliente;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import servidor.LeitorEscritorRemoteInterface;

/**
 * @author Luiz Felipe
 *
 */
public abstract  class Cliente {
	
	int id;
	public static int count =1;
	
	public Cliente(){
		id = count;
		count ++;
	}
	
	public  abstract void executa();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "Compute";
            Registry registry = LocateRegistry.getRegistry(args[0]);
            LeitorEscritorRemoteInterface sv = (LeitorEscritorRemoteInterface) registry.lookup(name);
            //chamada para escrita ou leitura no servidor

            
        } catch (Exception e) {
            System.err.println("ComputePi exception:");
            e.printStackTrace();
        }

	}
	

}
