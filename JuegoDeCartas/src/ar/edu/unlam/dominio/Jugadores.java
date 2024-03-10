package ar.edu.unlam.dominio;

import java.util.Random;

public class Jugadores {

	private String nombre;
	private boolean[][] mano = new boolean[4][12];
	public static boolean[][] cartaMesa = new boolean[4][12];
	private int puntuacion;
	private final int MAX_PUNTUACION;

	public Jugadores(String nombre, int puntuacionMaxima) {
		this.nombre = nombre;
		this.puntuacion = 0;
		this.MAX_PUNTUACION=puntuacionMaxima;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void repartir(boolean mazo[][], int cartasATomar) {
		int cartasTomadas = 0;
		while (cartasTomadas < cartasATomar) {
			int palo = paloRan();
			int num = numRan();
			if (mazo[palo][num]) {
				this.mano[palo][num] = true;
				mazo[palo][num] = false;
				cartasTomadas++;
			}
		}
	}

	public static void generarCartaInicial(boolean mazo[][]) {
		boolean numeroCorrecto = false;

		while (!numeroCorrecto) {
			int palo = paloRan();
			int num = numRan();
				if (mazo[palo][num]) {
					mazo[palo][num] = false;
					cartaMesa[palo][num] = true;
					numeroCorrecto = true;
			}
		}

	}

	public static boolean[][] getCartaMesa() {
		return cartaMesa;
	}

	public void descartar(boolean mazoDescartado[][], int palo, int num) {
		this.mano[palo][num] = false;
		cartaMesa[palo][num] = true;
	}

	public void intercambio(boolean mazoDescartado[][], int paloMesa, int numMesa) {
		cartaMesa[paloMesa][numMesa] = false;
		mazoDescartado[paloMesa][numMesa] = true;
	}

	public boolean terminarRonda() {
		boolean terminar = true;
		for (int p = 0; p < 4; p++) {
			for (int n = 0; n < 12; n++) {
				if (this.mano[p][n]) {
					terminar = false;
				}
			}
		}
		return terminar;
	}

	public void tirarUnDos(Cartas[][] cartasNombres, boolean mazo[][]) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 12; j++) {
				if (getCartaMesa()[i][j]) {
					for (int p = 0; p < 4; p++) {
						for (int n = 0; n < 12; n++) {
							if (getMano()[p][n]) {
								if (cartasNombres[p][n].getNumeroDeCarta() == 2) {
									descartar(mazo, p, n);
									intercambio(mazo, i, j);
								}

							}
						}
					}
				}
			}
		}
	}

	public boolean tieneDos(Cartas[][] cartasNombres, boolean mazo[][]) {
		boolean tieneDos = false;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 12; j++) {
				if (getCartaMesa()[i][j]) {
					for (int p = 0; p < 4; p++) {
						for (int n = 0; n < 12; n++) {
							if (getMano()[p][n]) {
								if (cartasNombres[p][n].getNumeroDeCarta() == 2) {
									
									tieneDos = true;
									
								}

							}
						}
					}
				}
			}
		}
		return tieneDos;
	}
	
	public boolean mazoVacio(boolean mazo[][]) {
		boolean vacio = true;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 12; j++) {
				if (mazo[i][j]) {
					vacio = false;
				}
			}
		}
		return vacio;
	}

	
	public void reiniciarMazo(boolean mazoDescartado[][], boolean mazo[][]) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 12; j++) {
					if (!getCartaMesa()[i][j]) {
						if (!getMano()[i][j]) {
							mazoDescartado[i][j] = false;
							mazo[i][j] = true;
						}

					}
				}
			}	
	}
	
	public static void reiniciarMesa() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 12; j++) {
					cartaMesa[i][j] = false;
			}
		}	
}

	public boolean coincidePaloONumero(int numeroMano, int paloMano, int numeroMesa, int paloMesa) {
		boolean hayCoincidencia = false;
		if (numeroMano == numeroMesa) {
			hayCoincidencia = true;
		}
		if (paloMano == paloMesa) {
			hayCoincidencia = true;
		}
		return hayCoincidencia;
	}
	
	public boolean terminarJuego() {
		boolean terminoElJuego = false;
		if (this.puntuacion >= MAX_PUNTUACION ) {
			terminoElJuego = true;
		}

		return terminoElJuego;

	}

	public int getMAX_PUNTUACION() {
		return MAX_PUNTUACION;
	}

	public static int paloRan() {
		Random n = new Random();
		return n.nextInt(4 - 0);
	}

	public static int numRan() {
		Random n = new Random();
		return n.nextInt(11 - 0);
	}

	public void sumarPuntos(int puntos) {
		this.puntuacion += puntos;
	}

	public int getPuntuacion() {
		return this.puntuacion;
	}

	public void reset() {
		mano = new boolean[4][12];
		this.puntuacion = 0;
	}

	public boolean[][] getMano() {
		return this.mano;
	}


}