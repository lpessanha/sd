/**
 * 
 */
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;



/**
 * @author Luiz Felipe
 *
 */
public class Cliente implements Runnable {
	
	int id;
	public static int count =1;
	int leitorOuEscritor;
	
	public Cliente(int leitorOuEscritor){
		id = count;
		count ++;
		this.leitorOuEscritor = leitorOuEscritor;
	}
	
	public void executa(LeitorEscritorRemoteInterface sv) throws RemoteException{
		if(this.leitorOuEscritor == 1){
			sv.escrever(id);
		}else
			sv.ler(id);
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        Cliente c1 = new Cliente(1);
        Cliente c2 = new Cliente(2);
        Cliente c3 = new Cliente(1);
        Cliente c4 = new Cliente(2);

        Thread t1 = new Thread(c1);
        Thread t2 = new Thread(c2);
        Thread t3 = new Thread(c3);
        Thread t4 = new Thread(c4);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

	}

	@Override
	public void run() {
		if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "Servidor";
            Registry registry = LocateRegistry.getRegistry();
		System.out.println("Cliente "+id+" localizando servidor...");
            LeitorEscritorRemoteInterface sv = (LeitorEscritorRemoteInterface) registry.lookup(name);
		System.out.println("Cliente "+id+" servidor localizado.");
		System.out.println("Cliente "+id+" executando comando...");
            //chamada para escrita ou leitura no servidor
            executa(sv);
		System.out.println("Cliente "+id+" execucao finalizada.");
        } catch (Exception e) {
            System.err.println("exception:");
            e.printStackTrace();
        }
	}
	

}
