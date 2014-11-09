

public class Controlador {
	/**
	 * indica o numero de leitores correntes*/
	static int leitores =0;
	/**
	 * indica se algum escritor esta escrevendo no momento */
	static boolean escrevendo = false;
	
	/**
	 * verifica se nao tem nenhum leitor e 
	 * se nao tem nunnhum escrito acessando
	 * o arquivo
	 * @return true se pode escrever false caso contrario
	 */
	static boolean condicaoEscrita(){
		return leitores == 0 && escrevendo==false;
	}
	
	/**
	 * verifica a condicao de leitura
	 * @return true caso possa ler
	 * @return false caso exista um escritor acessando o arquivo
	 */
	static boolean condicaoLeitura(){
		return escrevendo==false;
	}
	
	
}
