import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Jogo 
{
	private ArrayList<Peca> tabuleiro;             // Tabuleiro com as pecas
	private Jogador humano;                        // Jogador Humano
	private Jogador robot1;                        // Robot 1                      
	private Jogador robot2;                        // Robot 2
	private Jogador robot3;                        // Robot 3
	private ArrayList<Jogador> ordemjogo;          // Lista com jogadores que define a ordem do jogo 
	private ArrayList<Jogador> ordemdistribuicao;  // Lista com jogadores que define a ordem de distribuicao das pecas
	private Jogador vencedor;                      // Vencedor de cada partida
	private int njogadas;                          // Numero de jogadas
	private int npartida;                          // Numero de partidas
	
	public Jogo () {	
		this.tabuleiro = new ArrayList<Peca>();		
		this.vencedor = null;
		this.njogadas = 0;
		this.npartida = 1;
		
		System.out.print("Nome do jogador: ");	
		String nome = scan.nextLine();
		this.humano = new Jogador(nome, false); 
		this.robot1 = new Jogador("Robot 1", true);
		this.robot2 = new Jogador("Robot 2", true);
		this.robot3 = new Jogador("Robot 3", true);
		
		this.ordemjogo = new ArrayList<Jogador>(); 
		this.ordemdistribuicao = new ArrayList<Jogador>();	
		ordemjogo.add(humano);
		ordemjogo.add(robot1);
		ordemjogo.add(robot2);
		ordemjogo.add(robot3);
		ordemdistribuicao.addAll(ordemjogo);				
	}
	
	private static Scanner scan = new Scanner(System.in);
	private static Random rand = new Random();
	
	public ArrayList<Peca> getTabuleiro() {
		return tabuleiro;
	}

	public void setTabuleiro(ArrayList<Peca> tabuleiro) {
		this.tabuleiro = tabuleiro;
	}

	public Jogador getHumano() {
		return humano;
	}

	public void setHumano(Jogador humano) {
		this.humano = humano;
	}

	public Jogador getRobot1() {
		return robot1;
	}

	public void setRobot1(Jogador robot1) {
		this.robot1 = robot1;
	}

	public Jogador getRobot2() {
		return robot2;
	}

	public void setRobot2(Jogador robot2) {
		this.robot2 = robot2;
	}

	public Jogador getRobot3() {
		return robot3;
	}

	public void setRobot3(Jogador robot3) {
		this.robot3 = robot3;
	}

	public ArrayList<Jogador> getOrdemjogo() {
		return ordemjogo;
	}

	public void setOrdemjogo(ArrayList<Jogador> ordemjogo) {
		this.ordemjogo = ordemjogo;
	}

	public ArrayList<Jogador> getOrdemdistribuicao() {
		return ordemdistribuicao;
	}

	public void setOrdemdistribuicao(ArrayList<Jogador> ordemdistribuicao) {
		this.ordemdistribuicao = ordemdistribuicao;
	}

	public Jogador getVencedor() {
		return vencedor;
	}

	public void setVencedor(Jogador vencedor) {
		this.vencedor = vencedor;
	}

	public int getNjogadas() {
		return njogadas;
	}

	public void setNjogadas(int njogadas) {
		this.njogadas = njogadas;
	}

	public int getNpartida() {
		return npartida;
	}

	public void setNpartida(int npartida) {
		this.npartida = npartida;
	}	
	
	// CICLO DA PARTIDA - a partida acaba quando todos os jogadores passam ou quando algum fica sem pecas
	private boolean ciclopartida() {
		boolean continua = true;
		if (humano.isJoga() == false && robot1.isJoga() == false && robot2.isJoga() == false && robot3.isJoga() == false) {
			continua = false;
		}
		else if (humano.getMaojogador().size() == 0 || robot1.getMaojogador().size() == 0 || robot2.getMaojogador().size() == 0 || robot3.getMaojogador().size() == 0) {
			continua = false;	
		}
		return continua;	
	}

	// CICLO DO JOGO - o jogo acaba quando algum jogador obtem ou ultrapassa os 50 pontos 
	public boolean ciclojogo() {
		boolean continua = true;
		if (humano.getPontosacumulados() >= 50 || robot1.getPontosacumulados() >= 50 || robot2.getPontosacumulados() >= 50 || robot3.getPontosacumulados() >= 50) {
			continua = false;
		}	
		return continua;
	}
	
	// CRIAR AS 28 PECAS
	public void criarpecas() {	
		for(int i = 0; i < 7; i++)  {
			for(int j = i; j < 7; j++) {
				tabuleiro.add(new Peca(i,j)); //todas as pecas sao adicionadas ao tabuleiro
			}
		}
	}
	
	// ORDEM DO JOGO
	public void ordemjogo() {
		System.out.println("\n******************* PARTIDA " + npartida + " *******************\n");
		if (vencedor == null) { 
			int n = rand.nextInt(4);
			Collections.rotate(ordemjogo, ordemjogo.size() - n); // na primeira partida o primeiro a comecar e escolhido de forma aleatoria
		}
		else {
			Collections.rotate(ordemjogo, ordemjogo.size() - ordemjogo.indexOf(vencedor)); // nas partidas seguintes o primeiro e o vencedor da partida anterior
		}			
		System.out.println("Ordem jogo: "); // mostrar ordem do jogo
		int i = 1;
		for (Jogador jogador : ordemjogo) {
			System.out.println(i + " - " + jogador.getNome()); 
			i++;
		}
		System.out.println();
	}
		
	// ORDEM DA DISTRIBUIÇÃO DAS PEÇAS + DIVIDIR AS PECAS
	public void ordemdistribuicao() {
		if (vencedor == null) {  
			int n = rand.nextInt(4); 
			Collections.rotate(ordemdistribuicao, ordemdistribuicao.size() - n); // na primeira partida o distribuidor e escolhido de forma aleatoria
		}
		else {
			Collections.rotate(ordemdistribuicao, ordemdistribuicao.size() - 1); // nas partidas seguintes e o jogador ao lado (no sentido direto) que distribui
		}
		Jogador distribuidor = ordemdistribuicao.get(0);		
		distribuidor.getMaojogador().addAll(tabuleiro);  // as pecas ficam todas na mao do distribuidor e ele baralha-as
		Collections.shuffle(distribuidor.getMaojogador());
		
		System.out.println("Ordem distribuicao: ");
		System.out.println("Distribuidor: " + distribuidor.getNome());	
		for (int i = 1; i < 4; i++) {
			Jogador jogador = ordemdistribuicao.get(i);
			System.out.println(i + " - " + jogador.getNome());
			for (int j = 0; j < 7; j++) {                        // o distribuir divide as pecas pelos outros jogadores e fica com as ultimas 7
				jogador.getMaojogador().add(distribuidor.getMaojogador().get(0));
				distribuidor.getMaojogador().remove(0);
			}
		}
		tabuleiro.clear();
	}
	
	// PARTIDA
	public void partida() {
		System.out.println("\n-------------------- Comecou! -------------------\n");
		while (ciclopartida() == true) {  
			for(Jogador jogador: ordemjogo) {
				if (ciclopartida() == true && ciclojogo() == true) {
					jogador.jogar(njogadas, tabuleiro);
				}
				if (jogador.isJoga() == true) {
					setNjogadas(this.njogadas + 1);
				}
			}
		}
		atualizarpontos();
		vencedorpartida();
		setNpartida(this.npartida + 1);
		recomecar();
	}
	
	// ATUALIZAR PONTOS NO FINAL DA PARTIDA
	private void atualizarpontos() {
		System.out.println("--------------- FINAL DA PARTIDA " + npartida + " ---------------\n");
		System.out.println("Numero de jogadas realizadas: " + njogadas);
		
		System.out.println("\nPontos final da partida " + npartida + ": "); // mostrar pontos obtidos durante a partida
		for (Jogador jogador: ordemjogo) { 
			jogador.setPontos(jogador.somamao());
			System.out.println(" * " + jogador.getNome() + ": " + jogador.getPontos() + " pontos" ); 
		}
		
		System.out.println("\nPontos acumulados ao longo do jogo: "); // mostrar pontos acumulados: pontos partida + pontos das partidas anteriores
		for (Jogador jogador : ordemjogo) {
			jogador.setPontosacumulados(jogador.getPontosacumulados() + jogador.getPontos());
			System.out.println(" * " + jogador.getNome() + ": " + jogador.getPontosacumulados() + " pontos" );
		}
		System.out.println();
	}
	
	// DEFINIR VENCEDOR DA PARTIDA + VERIFICAR EXISTENCIA DE EMPATE
	private void vencedorpartida() {
		int pontos = ordemjogo.get(0).getPontos();
		for (Jogador jogador : ordemjogo) {
			if (jogador.getPontos() <= pontos) { // o vencedor da partida e o jogador com menor numero de pontos
				pontos = jogador.getPontos();
				setVencedor(jogador);
			}
		}
		boolean empate = false;
		String frase1 = "Empate! Os vencedores da partida sao: ";
		String frase2 = "Vencedor da partida: ";
		for (Jogador jogador : ordemjogo) {
			if (jogador.getNome().equals(vencedor.getNome()) == false && jogador.getPontos() == vencedor.getPontos()) { // verificar se houve empate
				empate = true;
				frase1 = frase1 + jogador.getNome() + ", ";
			}
		}
		if (empate == false) {
			System.out.println(frase2 + vencedor.getNome() + " com " + vencedor.getPontos() + " pontos.");
		}
		else {
			System.out.println(frase1.substring(0,frase1.length()-2) + " e " + vencedor.getNome() + ", com " + vencedor.getPontos() + " pontos.");
		}
	}
	
	// NOVA PARTIDA
	private void recomecar() {	
		tabuleiro.clear(); // retirar todas as pecas do tabuleiro
		setNjogadas(0);
		for (Jogador jogador : ordemjogo) {
			jogador.setJoga(true);
			jogador.setPontos(0);  // todos os jogadores ficam com 0 pontos
			jogador.getMaojogador().clear(); // retirar todas as pecas que sobraram na mao dos jogadores
		}	
	}
		
	// PONTOS FINAL DO JOGO 
	public void pontosfinal() {
		System.out.println("Numero de partidas realizadas: " + (npartida-1));	
		System.out.println("\nPontos finais: ");
		for (Jogador jogador : ordemjogo) {	
			System.out.println(" * " + jogador.getNome() + ": " + jogador.getPontosacumulados() + " pontos" );			
		}
		System.out.println();
	}
	
	// DEFINIR VENCEDOR DO JOGO + VERIFICAR EXISTENCIA DE EMPATE
	public void vencedorjogo() {
		int pontosacumulados = ordemjogo.get(0).getPontosacumulados();
		for (Jogador jogador : ordemjogo) {	
			if (jogador.getPontosacumulados() <= pontosacumulados) { // o vencedor do jogo e o jogador com menor numero de pontos
				pontosacumulados = jogador.getPontosacumulados();
				setVencedor(jogador);
			}
		}	
		boolean empate = false;
		String frase1 = "Empate! Os vencedores do jogo sao: ";
		String frase2 = "Vencedor do jogo: ";
		for (Jogador jogador : ordemjogo) {
			if (jogador.getNome().equals(vencedor.getNome()) == false && jogador.getPontosacumulados() == vencedor.getPontosacumulados()) { // verificar se houve empate
				empate = true;
				frase1 = frase1 + jogador.getNome() + ", ";
			}
		}
		if (empate == false) {
			System.out.println(frase2 + vencedor.getNome() + " com " + vencedor.getPontosacumulados() + " pontos.");
		}
		else {
			System.out.println(frase1.substring(0,frase1.length()-2) + " e " + vencedor.getNome() + "; com " + vencedor.getPontosacumulados() + " pontos.");
		}
	}

}