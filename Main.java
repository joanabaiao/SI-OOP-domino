/**
 * @author Filipe Lopes Amado (2017254990) && Joana Sofia Baiao Vieira (2017260526)
 */

public class Main
{	
	public static void main(String[] args)
	{
		System.out.println("\n**************** JOGO DO DOMINO *****************\n");

		System.out.println("Objetivo: colocar todas as pecas na mesa antes dos"
				+ "\nseus adversarios e jogar as pecas com mais pontos.");
		
		System.out.println("\n****************** NOVO JOGO ********************\n");
		
		Jogo jogo = new Jogo();
		
		while (jogo.ciclojogo() == true)
		{
			jogo.criarpecas();
			jogo.ordemjogo();
			jogo.ordemdistribuicao();
			jogo.partida();
		}
		
		System.out.println("\n----------------- FINAL DO JOGO ------------------\n");
		jogo.pontosfinal();	
		jogo.vencedorjogo();	
	}	
}
