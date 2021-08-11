import java.util.ArrayList;
import java.util.Scanner;

public class Jogador
{
	private String nome;                  // nome do jogador
	private ArrayList<Peca> maojogador;   // pecas na mao do jogador
	private boolean robot;                // jogador humano (false) ou robot (true)
	private int pontosacumulados;         // pontos acumulados ao longo do jogo
	private int pontos;                   // pontos de cada partida
	private boolean joga;                 // jogador pode jogar ou não. Quando os 4 jogadores passam a partida acaba.
	
	public Jogador(String nome, boolean robot) {
		this.nome = nome;
		this.pontos = 0;
		this.pontosacumulados = 0;
		this.maojogador = new ArrayList<Peca>();
		this.robot = robot;
		this.joga = true;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public ArrayList<Peca> getMaojogador() {
		return maojogador;
	}

	public void setMaojogador(ArrayList<Peca> maojogador) {
		this.maojogador = maojogador;
	}

	public boolean isRobot() {
		return robot;
	}

	public void setRobot(boolean robot) {
		this.robot = robot;
	}

	public int getPontosacumulados() {
		return pontosacumulados;
	}

	public void setPontosacumulados(int pontosacumulados) {
		this.pontosacumulados = pontosacumulados;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	public boolean isJoga() {
		return joga;
	}

	public void setJoga(boolean joga) {
		this.joga = joga;
	}
	
	private static Scanner scan = new Scanner(System.in);
	
	// JOGADOR HUMANO - ESCOLHA DA PECA + VALIDACAO
	private int escolhahumano(ArrayList<Peca> tabuleiro, int njogadas) {
		int inteiro = 0;
		boolean valido = false;
		
		while (valido == false) {
			System.out.print("Jogada escolhida: ");
			if (scan.hasNextInt()) {
				inteiro = scan.nextInt();
			}	
			if (inteiro >= 0 && inteiro <= maojogador.size()) {
				if (njogadas == 0 || inteiro == maojogador.size()){
					valido = true;
				}
				else {	
					Peca peca = maojogador.get(inteiro);
					int baixo = tabuleiro.get(tabuleiro.size()-1).getValor_baixo();
					int cima = tabuleiro.get(0).getValor_cima();
					if (baixo == peca.getValor_baixo() || baixo == peca.getValor_cima() || cima == peca.getValor_baixo() || cima == peca.getValor_cima()) {
						valido = true;					
					}	
				}
			}
			if (valido == false) {
				System.out.println("Jogada invalida. Tente novamente!");	
			}	
			scan.nextLine();
			System.out.println();
		}
		return inteiro;
	}
	
	// JOGADOR HUMANO - MOSTRAR AS PECAS + OPCAO DE PASSAR
	private void mostraropcoes() {
		System.out.println("----------------- As suas pecas -----------------\n");	
		int i = 0;
		for (Peca peca: maojogador) {
			System.out.println(i + ") ");
			peca.printpeca();	
			i++;
		}
		System.out.println(i++ + ")\nPassar\n");		
	}
		
	// ROBOT - ESCOLHE A PECA COM MAIS PONTOS
	private Peca escolharobot(int njogadas, ArrayList<Peca> tabuleiro) {
		int soma = 0;
		Peca pecamaior = null;	
		if (njogadas == 0) { // na primeira jogada da partida os robots escolhe a peca com mais pontos
			for (Peca peca : maojogador) {
				if (peca.getSoma() > soma) {
					soma = peca.getSoma();
					pecamaior = peca;
				}
			}
		}
		else { // nas outras jogadas o robot escolhe a peca com mais pontos que pode ser jogada numa das extremidades do tabuleiro
			int baixo = tabuleiro.get(tabuleiro.size()-1).getValor_baixo();
			int cima = tabuleiro.get(0).getValor_cima();
			for (Peca peca: maojogador){
				if (peca.getSoma() > soma) {
					if (baixo == peca.getValor_baixo() || baixo == peca.getValor_cima() || cima == peca.getValor_baixo() || cima == peca.getValor_cima() )  {
						soma = peca.getSoma();
						pecamaior = peca;
					}			
				}	
			}
		}
		return pecamaior;		
	}
	
	// ADICIONA A PECA AO TABULEIRO
	private void jogartabuleiro (Peca peca, int njogadas, ArrayList<Peca> tabuleiro) {
		if (njogadas != 0) {
			int baixo = tabuleiro.get(tabuleiro.size()-1).getValor_baixo(); // valor da peca que esta na extremidade de baixo do tabuleiro
			int cima = tabuleiro.get(0).getValor_cima();                    // valor da peca que esta na extremidade de cima do tabuleiro
			if (baixo == peca.getValor_baixo() || baixo == peca.getValor_cima()) {
				if (baixo == peca.getValor_baixo()) {
					peca.inverter();	
				}
				tabuleiro.add(peca); // fica em baixo (no tabuleiro)
			}	
			else {
				if (cima == peca.getValor_cima()) { 
					peca.inverter();
				}
				tabuleiro.add(0,peca); // fica em cima (no tabuleiro)
			}		
		}
		else if (njogadas == 0) {
			tabuleiro.add(peca);		
		}	
		maojogador.remove(maojogador.indexOf(peca));
		setJoga(true);
	}
	
	// JOGADA
	public void jogar(int njogadas, ArrayList<Peca> tabuleiro) {
		if (this.robot == true) { // ROBOT
			Peca pecamaior = escolharobot(njogadas, tabuleiro);
			if (pecamaior == null) {
				System.out.println("O jogador " + this.getNome() + " passou!\n"); 
				this.setJoga(false);
			}
			else {
				System.out.println("O jogador " + this.getNome() + " jogou!\n"); 
				jogartabuleiro(pecamaior, njogadas, tabuleiro);
			}
		}
		
		else if (this.robot == false) { // HUMANO	
			System.out.println("E a sua vez!\n");
			if (njogadas != 0) {
				System.out.println("------------------ Mesa de jogo -----------------\n");
				for (Peca peca: tabuleiro) {
					peca.printpeca();	
				}
			}
			mostraropcoes();
			int jogada = escolhahumano(tabuleiro, njogadas);
			if (jogada == maojogador.size()) {
				System.out.println("Passou!\n");
				this.setJoga(false);;
			}
			else {
				Peca peca = maojogador.get(jogada);
				jogartabuleiro(peca, njogadas, tabuleiro);
				System.out.println("Jogou!\n");	
			}
		}
	}
	
	// PONTOS DO JOGADOR - SOMA DOS PONTOS DAS PECAS QUE O JOGADOR TEM NA MAO
	public int somamao() {
		int soma = 0;
		if (maojogador.size() == 1 && maojogador.get(0).getValor_cima() == 0  && maojogador.get(0).getValor_baixo() == 0) {
			soma = 10; // se a peca |0|0| for a unica peca existente na mao de um jogador, nao vale 0 mas 10 pontos
		}
		else { // nos outros casos os pontos da peca sao a soma dos valores das suas faces
			for (Peca p: maojogador){
			soma = soma + p.getSoma(); 
			}
		}
		return soma;
	}	
	
}