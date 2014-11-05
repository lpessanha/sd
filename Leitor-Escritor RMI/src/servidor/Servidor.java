/**
 * 
 */
package servidor;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Luiz Felipe
 *
 */
public class Servidor implements LeitorEscritorRemoteInterface {
    File file ;
    String caminho;
    FileWriter fw;
    FileReader fr;
    Properties prop = new Properties();

	/**
	 * 
	 */
	public Servidor() {
		try {
			prop.load(Servidor.class.getClassLoader().getResourceAsStream("properties/config.properties"));
			caminho = prop.getProperty("dir");
			file = new File(caminho);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see servidor.LeitorEscritorRemoteInterface#escrever(java.lang.String)
	 */
	@Override
	public void escrever() throws RemoteException {
	
		 try {
			comecaEscrever();
			//executa escrita
			fw = new FileWriter(file);
	        BufferedWriter bw = new BufferedWriter(fw);
	        bw.write(String.valueOf(Math.random()));
	        bw.close();
	        fw.close();
			terminaEscrever();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void comecaEscrever() {
		while(!Controlador.condicaoEscrita()){}
		Controlador.escrevendo = true;
		
	}

	private void terminaEscrever() {
		Controlador.escrevendo = false;		
	}

	/* (non-Javadoc)
	 * @see servidor.LeitorEscritorRemoteInterface#ler()
	 */
	@Override
	public void ler() throws RemoteException {
		String l = "";
		try {
			comecaLer();
			System.out.println("lendo arquivo...");
			//	executa leitura do arquivo
			fr = new FileReader(file);
			BufferedReader buffRead = new BufferedReader(fr);
			while(buffRead != null){
				System.out.println(l);
			}			
			terminaLer();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	private void terminaLer() {
		Controlador.leitores--;
		
	}

	private void comecaLer() {
		while(!Controlador.condicaoLeitura()){}
		Controlador.leitores++;
		
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
