package servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LeitorEscritorRemoteInterface extends Remote{
	/**
	 * Metodo que dever ser implementado pelo servidor
	 * para permitir que um cliente escreva no arquivo.
	 * Deve tratar a concorrencia, verificando se n�o existe
	 * nenhum outro cliente lendo ou escrevendo no arquivo.
	 * @param texto texto a ser escrito no arquivo.
	 * @throws RemoteException
	 */
	void escrever(String texto) throws RemoteException;
	
	/**
	 * M�todo que deve ser implementado pelo servidor, 
	 * para permitir que clientes leiam o arquivo. Deve
	 * tratar o problema de concorrencia, n�o permitindo
	 * nenhum cliente de ler o arquivo, caso algum outro cliente 
	 * esteja escrevendo no mesmo.
	 * @throws RemoteException
	 */
	void ler() throws RemoteException;

}
