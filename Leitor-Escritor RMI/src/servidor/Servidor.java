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
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Luiz Felipe
 *
 */
public class Servidor implements LeitorEscritorRemoteInterface {

    File file;
    String caminho;
    FileWriter fw;
    FileReader fr;

    Properties prop = new Properties();
    public static Semaphore mutex = new Semaphore(1);
    public static Semaphore w = new Semaphore(1);

    /**
     * indica o numero de leitores correntes
     */
    static int leitores = 0;
    /**
     * indica se algum escritor esta escrevendo no momento
     */
    static boolean escrevendo = false;

    /**
     * verifica se nao tem nenhum leitor e se nao tem nunnhum escrito acessando
     * o arquivo
     *
     * @return true se pode escrever false caso contrario
     */
    static boolean condicaoEscrita() {
        return leitores == 0 && escrevendo == false;
    }

    /**
     * verifica a condicao de leitura
     *
     * @return true caso possa ler
     * @return false caso exista um escritor acessando o arquivo
     */
    static boolean condicaoLeitura() {
        return escrevendo == false;
    }

    /**
     *
     */
    public Servidor() {
        try {
            System.out.println("localizando arquivo...");
            prop.load(Servidor.class.getClassLoader().getResourceAsStream("config.properties"));
            caminho = prop.getProperty("dir");
            System.out.println("abrindo arquivo...");
            file = new File(caminho);
            fw = new FileWriter(file);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see servidor.LeitorEscritorRemoteInterface#escrever(java.lang.String)
     */
    @Override
    public void escrever(int id) throws RemoteException {

        try {
            w.acquire();
            comecaEscrever(id);
            //executa escrita
            System.out.println("cliente " + id + " escrevendo no arquivo...");
            fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.append("cliente: " + id + "\n");
            bw.append(String.valueOf(Math.random()));
            bw.newLine();
            bw.close();
            fw.close();
            System.out.println("cliente " + id + " fim da escrita");
            terminaEscrever(id);
            w.release();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void comecaEscrever(int id) {
        while (Controlador.condicaoEscrita() == false) {
            System.out.println("cliente " + id + " aguardando para condicao de escrita...");
        }
        Controlador.escrevendo = true;

    }

    private void terminaEscrever(int id) {
        Controlador.escrevendo = false;
        System.out.println("cliente " + id + " terminou a escrita...");
    }

    /* (non-Javadoc)
     * @see servidor.LeitorEscritorRemoteInterface#ler()
     */
    @Override
    public void ler(int id) throws RemoteException {

        String l = "vazia";
        try {

            mutex.acquire();

            leitores = leitores++;
            if (leitores == 1) {
                w.acquire();
            }

            mutex.release();
            comecaLer(id);
            System.out.println("clinte " + id + " lendo arquivo...");
            //	executa leitura do arquivo
            fr = new FileReader(file);
            BufferedReader buffRead = new BufferedReader(fr);
            while (l != null) {
                l = buffRead.readLine();
                System.out.println(l);
            }
            terminaLer(id);

            mutex.acquire(); 

            leitores = leitores--; 
            if (leitores == 0) {
                w.release();
            }
            mutex.release();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void terminaLer(int id) {
        Controlador.leitores--;
        System.out.println("cliente " + id + " terminou de ler.");

    }

    private void comecaLer(int id) {
        while (Controlador.condicaoLeitura() == false) {
            System.out.println("cliente " + id + " aguardando para condicao de leitura...");
        }
        Controlador.leitores++;

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        /**
         * Security Manager para proteger o sistema de acesso nao autorizado a
         * recursos desnecessarios. Garante que operacoes realiazadas pelo
         * codigo baixado, estao sujeitas a uma politica de seguranca
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
            LeitorEscritorRemoteInterface stub
                    = (LeitorEscritorRemoteInterface) UnicastRemoteObject.exportObject(sv, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("Servidor pronto!");
        } catch (Exception e) {
            System.err.println("Servidor exception:");
            e.printStackTrace();
        }

    }

}
