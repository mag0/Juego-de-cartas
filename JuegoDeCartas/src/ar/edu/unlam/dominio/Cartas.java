package ar.edu.unlam.dominio;

public class Cartas {
	private int numeroDeCarta;
	private Palos paloDeCarta;

	public Cartas(int numero, Palos palo) {
		this.numeroDeCarta = numero;
		this.paloDeCarta = palo;
	}

	public int getNumeroDeCarta() {
		return numeroDeCarta;
	}

	public void setNumeroDeCarta(int numeroDeCarta) {
		this.numeroDeCarta = numeroDeCarta;
	}

	public Palos getPaloDeCarta() {
		return paloDeCarta;
	}

	public void setPaloDeCarta(Palos paloDeCarta) {
		this.paloDeCarta = paloDeCarta;
	}
	
	@Override
	public String toString() { 
//		if (paloDeCarta != Palos.COMODIN) {
//			return numeroDeCarta + " de " + paloDeCarta;
//		} else {
//			return "" + paloDeCarta;
//		}
		return numeroDeCarta + " de " + paloDeCarta;
	}
}