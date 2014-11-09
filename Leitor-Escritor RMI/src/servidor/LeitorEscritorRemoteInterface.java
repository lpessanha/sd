

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LeitorEscritorRemoteInterface extends Remote{
	/**
	 * Metodo que dever ser implementado pelo servidor
	 * para permitir que um cliente escreva no arquivo.
	 * Deve tratar a concorrencia, verificando se nao existe
	 * nenhum outro cliente lendo ou escrevendo no arquivo.
	 * @throws RemoteException
	 */
	void escrever(int id) throws RemoteException;
	
	/**
	 * Metodo que deve ser implementado pelo servidor, 
	 * para permitir que clientes leiam o arquivo. Deve
	 * tratar o problema de concorrencia, nao permitindo
	 * nenhum cliente de ler o arquivo, caso algum outro cliente 
	 * esteja escrevendo no mesmo.
	 * @throws RemoteException
	 */
	void ler(int id) throws RemoteException;

}
