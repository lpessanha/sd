package servidor;

public class Controlador {
	/**
	 * indica o numero de leitores correntes*/
	static int leitores =0;
	/**
	 * indica se algum escritor est� escrevendo no momento */
	static boolean escrevendo = false;
	
	/**
	 * verifica se n�o tem nenhum leitor e 
	 * se n�o tem nunnhum escrito acessando
	 * o arquivo
	 * @return true se pode escrever false caso contr�rio
	 */
	static boolean condicaoEscrita(){
		return leitores == 0 && !escrevendo;
	}
	
	/**
	 * verifica a condi��o de leitura
	 * @return true caso possa ler
	 * @return false caso exista um escritor acessando o arquivo
	 */
	static boolean condicaoLeitura(){
		return !escrevendo;
	}
	
	
}
