public class Peca
{		
	private int valor_cima;  // valor de cima da peca
	private int valor_baixo; // valor de baixo da peca
	private boolean duplo;   // verificar se os dois valores sao iguais 
	private int soma;        // soma dos dois valores - pontos da peca
	
	public Peca(int valor_cima, int valor_baixo) {
		this.valor_cima = valor_cima;
		this.valor_baixo = valor_baixo;
		this.soma = valor_cima + valor_baixo;
		duplo = (valor_cima == valor_baixo);		
	}

	public int getValor_cima() {
		return valor_cima;
	}

	public void setValor_cima(int valor_cima) {
		this.valor_cima = valor_cima;
	}

	public int getValor_baixo() {
		return valor_baixo;
	}

	public void setValor_baixo(int valor_baixo) {
		this.valor_baixo = valor_baixo;
	}

	public boolean isDuplo() {
		return duplo;
	}

	public void setDuplo(boolean duplo) {
		this.duplo = duplo;
	}

	public int getSoma() {
		return soma;
	}

	public void setSoma(int soma) {
		this.soma = soma;
	}
	
	// ESQUEMA DA PECA
	public void printpeca() {
		if (this.duplo ==  true) {
			System.out.println("|" + this.valor_cima + "|" + this.valor_baixo + "|\n"); // se a peca for dupla fica na horizontal
		}
		else {
			System.out.println("|" + this.valor_cima + "|\n---\n|" + this.valor_baixo + "|\n"); // caso contrario fica na vertical
		}
	}
	
	// INVERTER NUMEROS DA PECA
	public void inverter() {
		int cima = this.valor_cima;
		this.setValor_cima(this.valor_baixo);
		this.setValor_baixo(cima);
	}
	
}
