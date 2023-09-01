import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Cliente extends Thread {
	
    private Socket s;
    private BufferedReader socketIn;
    private PrintWriter socketOut;
    private String host;
    private Integer porta;
    private static Queue<Personagem> personagens = new ConcurrentLinkedQueue<Personagem>(); 
    
	
    public Cliente(String host, Integer porta) {
    	this.host = host;
    	this.porta = porta;
    }

    public void iniciarFluxos() {
    	try {
    		s = new Socket(host, porta);
        	socketIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
        	socketOut = new PrintWriter(s.getOutputStream(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void run() {
        try {
	        while(true){
	        	String linha = socketIn.readLine();
	        	String posicao[] = linha.split(",");
	        	Personagem personagem = new Personagem();
	        	
	        	if (posicao[0].equals("NEW")) {
		        	personagem.setId(Integer.parseInt(posicao[1]));
		        	personagem.setVida(Integer.parseInt(posicao[2]));
		        	personagem.setPosX(Integer.parseInt(posicao[3]));
		        	personagem.setPosY(Integer.parseInt(posicao[4]));
		        	personagem.setAparencia(Integer.parseInt(posicao[5]));
		        	personagem.setAction(Action.NEW);
		    		getPersonagens().add(personagem);
	        	} else if (posicao[0].equals("ANDOU")) {
		        	personagem.setId(Integer.parseInt(posicao[1]));
		        	personagem.setVida(Integer.parseInt(posicao[2]));
		        	personagem.setPosX(Integer.parseInt(posicao[3]));
		        	personagem.setPosY(Integer.parseInt(posicao[4]));
		        	personagem.setAparencia(Integer.parseInt(posicao[5]));
		        	personagem.setAction(Action.ANDANDO_FRENTE);
	        	} else if (posicao[0].equals("ATACOU")) {
		        	personagem.setId(Integer.parseInt(posicao[1]));
		        	personagem.setVida(Integer.parseInt(posicao[2]));
		        	personagem.setPosX(Integer.parseInt(posicao[3]));
		        	personagem.setPosY(Integer.parseInt(posicao[4]));
		        	personagem.setAparencia(Integer.parseInt(posicao[5]));
		        	personagem.setAction(Action.ATACANDO);
	        	} else if (posicao[0].equals("APANHOU")) {
		        	personagem.setId(Integer.parseInt(posicao[1]));
		        	personagem.setVida(Integer.parseInt(posicao[2]));
		        	personagem.setPosX(Integer.parseInt(posicao[3]));
		        	personagem.setPosY(Integer.parseInt(posicao[4]));
		        	personagem.setAparencia(Integer.parseInt(posicao[5]));
		        	personagem.setAction(Action.APANHANDO);
	        	} else if (posicao[0].equals("MORREU")) {
		        	personagem.setId(Integer.parseInt(posicao[1]));
		        	personagem.setVida(Integer.parseInt(posicao[2]));
		        	personagem.setPosX(Integer.parseInt(posicao[3]));
		        	personagem.setPosY(Integer.parseInt(posicao[4]));
		        	personagem.setAparencia(Integer.parseInt(posicao[5]));
		        	personagem.setAction(Action.PERDEU);
	        	}
	        	atualizaPersonagem(personagem);
	        }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void keyPressed(int key){
        socketOut.println(key);
    }
 
	public void atualizaPersonagem(Personagem personagem) {
		for (Personagem p : getPersonagens()) {
			if (personagem.getId() == p.getId()) {
				p.setPosX(personagem.getPosX());
				p.setPosY(personagem.getPosY());
				p.setAparencia(personagem.getAparencia());
				p.setVida(personagem.getVida());
				if (personagem.getVida() == 0 && personagem.getAction() == Action.NEW) {
					System.out.println("morto");
					p.setAction(Action.MORTO);
				} else if(personagem.getVida() < 100 && personagem.getAction() == Action.NEW) {
					p.setAction(Action.PARADO);
				} else {
					p.setAction(personagem.getAction());
				}
			}
		}
	}

	public static Queue<Personagem> getPersonagens() {
		return personagens;
	}
	
}
