package servidor;

public class Controlador {
	/**
	 * indica o numero de leitores correntes*/
	static int leitores =0;
	/**
	 * indica se algum escritor está escrevendo no momento */
	static boolean escrevendo = false;
	
	/**
	 * verifica se não tem nenhum leitor e 
	 * se não tem nunnhum escrito acessando
	 * o arquivo
	 * @return true se pode escrever false caso contrário
	 */
	static boolean condicaoEscrita(){
		return leitores == 0 && !escrevendo;
	}
	
	/**
	 * verifica a condição de leitura
	 * @return true caso possa ler
	 * @return false caso exista um escritor acessando o arquivo
	 */
	static boolean condicaoLeitura(){
		return !escrevendo;
	}
	
	
}
