package ar.edu.unlam.interfaz;

import java.util.Scanner;
import ar.edu.unlam.dominio.Cartas;
import ar.edu.unlam.dominio.Palos;
import ar.edu.unlam.dominio.Jugadores;

public class PruebaJodete {

	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		Cartas[][] cartasNombres = new Cartas[4][12];
		boolean[][] mazo = new boolean[4][12];
		boolean[][] mazoDescartado = new boolean[4][12];
		int puntuacionMaxima = 100;
		int cartasARepartir = 5;
		int rta = 0;

		for (int p = 0; p < 4; p++) {
			for (int n = 0; n < 12; n++) {
				cartasNombres[p][n] = new Cartas(n + 1, Palos.values()[p]);
				mazo[p][n] = true;
				mazoDescartado[p][n] = false;
			}
		}

		// PANTALLA DE INICIO

		System.out.println("Bienvenido al JODETE. Hecho por el grupo 'ARRAY TEAM'\n ");
		System.out.println("Ingresa tu nombre:");
		String nombreJugador = teclado.nextLine();
		System.out.println("\nSuerte " + nombreJugador);

		// Menu principal

		while (rta != 5) {
			System.out.println("\n" + nombreJugador + ", antes de empezar elige una opcion: \n"
					+ "\n1. Ver las reglas\n2. Ver el mazo\n3. Ver cartas especiales\n4. Cambiar configuracion de la partida \n5. Vamo a juga");
			rta = teclado.nextInt();
			switch (rta) {
			case 1:
				reglasDelJodete();
				break;
			case 2:
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 12; j++) {
						if (mazo[i][j]) {
							System.out.println(cartasNombres[i][j]);
						}
					}
				}
				System.out.println("\nEn otras palabras, sera la baraja española SIN comodín");
				break;
			case 3:
				cartasEspeciales();
				break;
			case 4:
				boolean todoCorrecto = false;
				while (!todoCorrecto) {
					System.out.println("Puntos de victoria? (50 - 100): ");
					puntuacionMaxima = teclado.nextInt();
					if (puntuacionMaxima < 50 || puntuacionMaxima > 100) {
						System.out.println("Tenes que elegir entre 50 y 100\n");
					} else {
						todoCorrecto = true;
					}
				}
				todoCorrecto = false;
				while (!todoCorrecto) {
					System.out.println("Cartas a repartir? (5 - 15): ");
					cartasARepartir = teclado.nextInt();
					if (cartasARepartir < 5 || cartasARepartir > 15) {
						System.out.println("Tenes que elegir entre 5 y 15\n");
					} else {
						todoCorrecto = true;
					}
				}
				break;
			case 5:
				break;
			default:
				System.out.println("Proba con otro numero.");
				break;
			}

		}

		Jugadores[] jugadores = { new Jugadores(nombreJugador, puntuacionMaxima),
				new Jugadores("Francia", puntuacionMaxima) };

		System.out.println("\nEL JUEGO ESTA POR COMENZAR\n");

		int rondas = 0;
		boolean turno = false;
		boolean agarroDelMazo = false;
		boolean jugador1FinalizoRonda = false;
		boolean come2 = false;
		boolean come4 = false;
		boolean come6 = false;
		boolean come8 = false;

		while (!jugadores[0].terminarJuego() && !jugadores[1].terminarJuego()) {
			rondas++;
			Jugadores.reiniciarMesa();

			System.out.println("\nRONDA " + rondas);
			System.out.println("\nRepartiendo las cartas...\n");

			for (int i = 0; i < jugadores.length; i++) {
				jugadores[i].repartir(mazo, cartasARepartir);
			}
			Jugadores.generarCartaInicial(mazo);

			while (!jugadores[0].terminarRonda() && !jugadores[1].terminarRonda()) {

//				TURNO DEL JUGADOR HUMANO

				System.out.println(
						"________________________________________________________________________________________________________________________________________________________________________________________________________\n");
				System.out.println(
						"Cantidad cartas Francia: " + (mostrarCantidadCartasMano(jugadores[1], cartasNombres) - 1));
				System.out.println("\nTurno de " + nombreJugador + "\n\nTus cartas:");
				for (int i = 1; i < mostrarCantidadCartasMano(jugadores[0], cartasNombres); i++) {
					System.out.println(i + ". [" + mostrarCartasManoX(jugadores[0], cartasNombres, i) + "] ");
				}
				System.out.println("\n");

				mostrarCartaMesa(jugadores[1], cartasNombres);

				if (come2 || come4 || come6 || come8) {
					turno = true;
					if (come8) {
						System.out.println("\nPerdiste la guerra bobo, jodete\n");
						jugadores[0].repartir(mazo, 8);
						come8 = false;
						System.out.println("Te comes 8 " + nombreJugador);

					}
					if (come6) {
						come6 = false;
						System.out.println("\nTe tiraron un dos, tenes un dos? \n 1. Tirar un dos\n 2. Comerse seis\n");
						int opcion = teclado.nextInt();
						if (opcion == 1) {
							if (jugadores[0].tieneDos(cartasNombres, mazo)) {
								jugadores[0].tirarUnDos(cartasNombres, mazo);
								come8 = true;
								System.out.println(nombreJugador + " tiro otro dos");
								actualizaDos();
							} else {
								System.out.println("\nNo tenes dos, porque mentis?\nTe comes 8");
								jugadores[0].repartir(mazo, 8);
							}

						} else {
							jugadores[0].repartir(mazo, 6);
							System.out.println(nombreJugador + " te comes seis");
						}

					}
					if (come4) {
						come4 = false;
						System.out.println(
								"\nTe tiraron otro dos., tenes un dos? \n 1. Tirar otro dos\n 2. Comerse cuatro\n");
						int opcion = teclado.nextInt();
						if (opcion == 1) {
							if (jugadores[0].tieneDos(cartasNombres, mazo)) {
								jugadores[0].tirarUnDos(cartasNombres, mazo);
								come6 = true;
								System.out.println(nombreJugador + " tiro otro dos");
								actualizaDos();
							} else {
								System.out.println("\nNo tenes dos, porque mentis?\nTe comes 6");
								jugadores[0].repartir(mazo, 6);
							}

						} else {
							jugadores[0].repartir(mazo, 4);
							System.out.println(nombreJugador + " te comes cuatro");
						}

					}
					if (come2) {
						come2 = false;
						System.out.println("\nTe tiraron un dos., tenes un dos? \n 1. Tirar un dos\n 2. Comerse dos\n");
						int opcion = teclado.nextInt();
						if (opcion == 1) {
							if (jugadores[0].tieneDos(cartasNombres, mazo)) {
								jugadores[0].tirarUnDos(cartasNombres, mazo);
								come4 = true;
								System.out.println(nombreJugador + " tiro otro dos");
								actualizaDos();
							} else {
								System.out.println("\nNo tenes dos, porque mentis?\nTe comes 4.");
								jugadores[0].repartir(mazo, 4);
							}

						} else {
							jugadores[0].repartir(mazo, 2);
							System.out.println(nombreJugador + " te comes dos");
						}

					}

				}
				boolean finBucle = false;
				while (!turno) {
					int opcion = 0;
					System.out.println("\n\n1.Levantar una carta \n2.Tirar \n3.Pasar");
					opcion = teclado.nextInt();
					switch (opcion) {
					case 1:
						if (!agarroDelMazo) {
							jugadores[0].repartir(mazo, 1);
							System.out.println("\nEntregando nueva carta...\n");
							System.out.println("Tus nuevas cartas:\n");
							agarroDelMazo = true;
							for (int i = 1; i < mostrarCantidadCartasMano(jugadores[0], cartasNombres); i++) {
								System.out
										.println(i + ". [" + mostrarCartasManoX(jugadores[0], cartasNombres, i) + "] ");
							}
						} else {
							System.out.println("\nSolo podes agarrar del mazo una vez\n");
						}
						break;
					case 2:
						boolean cartaTirada = false;

						boolean revisarCartaTirada = false;

						while (!finBucle) {
							int paloCartaTirada = 0;
							int numeroCartaTirada = 0;
							int cantidad = 1;
							System.out.println("\nQue carta vas a tirar?");
							opcion = teclado.nextInt();
							int i = getPosicionFilaCartaMesa(jugadores[0], cartasNombres);
							int j = getPosicionColumnaCartaMesa(jugadores[0], cartasNombres);
							for (int p = 0; p < 4; p++) {
								for (int n = 0; n < 12; n++) {
									if (jugadores[0].getMano()[p][n]) {
										if (!cartaTirada) {
											if (opcion == cantidad) {
												if (jugadores[0].coincidePaloONumero(p, n, i, j)) {
													jugadores[0].descartar(mazoDescartado, p, n);
													jugadores[0].intercambio(mazoDescartado, i, j);
													paloCartaTirada = p;
													numeroCartaTirada = n;
													revisarCartaTirada = true;
													System.out.println(
															nombreJugador + " tiro un [" + cartasNombres[p][n] + "]");

												} else {
													System.out.println("\nTiraste mal, te comes dos.\n");
													jugadores[0].repartir(mazo, 2);
													finBucle = true;

												}
												cartaTirada = true;
											}
											cantidad++;

										}
									}
								}
							}
							turno = true;

							if (revisarCartaTirada) {

								if (cartasNombres[paloCartaTirada][numeroCartaTirada].getNumeroDeCarta() == 2) {
									come2 = true;
									finBucle = true;

								} else if (cartasNombres[paloCartaTirada][numeroCartaTirada].getNumeroDeCarta() == 4
										&& !jugadores[0].terminarRonda()) {
									System.out.println("\nSaltea jugador...\n");
									for (int e = 1; e < mostrarCantidadCartasMano(jugadores[0], cartasNombres); e++) {
										System.out.println(
												e + ". [" + mostrarCartasManoX(jugadores[0], cartasNombres, e) + "] ");
									}
									turno = false;
									finBucle = false;
									agarroDelMazo = false;
									cartaTirada = false;
								} else if (cartasNombres[paloCartaTirada][numeroCartaTirada].getNumeroDeCarta() == 7) {
									System.out.println("\nTira de nuevo...\n");
									for (int e = 1; e < mostrarCantidadCartasMano(jugadores[0], cartasNombres); e++) {
										System.out.println(
												e + ". [" + mostrarCartasManoX(jugadores[0], cartasNombres, e) + "] ");
									}
									finBucle = false;
									agarroDelMazo = false;
									turno = false;
									cartaTirada = false;
								} else if (cartasNombres[paloCartaTirada][numeroCartaTirada].getNumeroDeCarta() == 12
										&& !jugadores[0].terminarRonda()) {
									System.out.println("\nCambia el sentido...\n");
									for (int e = 1; e < mostrarCantidadCartasMano(jugadores[0], cartasNombres); e++) {
										System.out.println(
												e + ". [" + mostrarCartasManoX(jugadores[0], cartasNombres, e) + "] ");
									}
									finBucle = false;
									agarroDelMazo = false;
									turno = false;
									cartaTirada = false;
								} else if (cartasNombres[paloCartaTirada][numeroCartaTirada].getNumeroDeCarta() == 10
										&& !jugadores[0].terminarRonda()) {
									System.out.println("\nPodes cambiar de palo...\n");

									System.out.println("A que palo queres cambiar 1.ESPADA 2.COPA 3.ORO 4.BASTO ");
									opcion = teclado.nextInt();
									boolean cambiopalo = false;

									while (!cambiopalo) {
										switch (opcion) {
										case 1:
											cambioDePalo(1, Palos.ESPADA);
											cambiopalo = true;
											break;
										case 2:
											cambioDePalo(2, Palos.COPA);
											cambiopalo = true;
											break;
										case 3:
											cambioDePalo(3, Palos.ORO);
											cambiopalo = true;
											break;
										case 4:
											cambioDePalo(4, Palos.BASTO);
											cambiopalo = true;
											break;
										default:
											System.out.println("Elegi bien bobo");
										}
									}

									finBucle = true;
									agarroDelMazo = false;
									turno = true;
								} else {
									System.out.println();

									mostrarCartaMesa(jugadores[1], cartasNombres);
									System.out.println();
									finBucle = true;
								}
							}
						}

						break;
					case 3:
						if (agarroDelMazo) {
							System.out.println("Paso\n");
							turno = true;
						} else {
							turno = false;
							System.out.println("Primero debes agarrar del mazo capo");
						}

						break;
					default:
						System.out.println(
								"Metiste cualquier numero pa, intenta de nuevo y no trolees pq te hago comer diez cartas");
					}
				}

				if (jugadores[0].terminarRonda()) {
					jugador1FinalizoRonda = true;
					System.out.println(nombreJugador + " te quedaste sin cartas, ganaste la ronda " + rondas);
				}

				if (jugadores[0].mazoVacio(mazo)) {
					jugadores[0].reiniciarMazo(mazoDescartado, mazo);
					System.out.println("Mezclando mazo...");
				}
				agarroDelMazo = false;
				turno = false;

//			TURNO DEL BOT

				if (!jugador1FinalizoRonda) {
					System.out.println("\nTurno de Fracia:\n");
					boolean cartaTirada = false;
					finBucle = false;
					if (come2 || come4 || come6 || come8) {
						if (come8) {
							come8 = false;
							jugadores[1].repartir(mazo, 8);
							System.out.println("Fracia se come ocho, F");
						}
						if (come6) {
							come6 = false;
							if (jugadores[1].tieneDos(cartasNombres, mazo)) {
								jugadores[1].tirarUnDos(cartasNombres, mazo);
								come8 = true;

								System.out.println("Tira otro 2");
								finBucle = true;
							} else {
								jugadores[1].repartir(mazo, 6);
								System.out.println("Fracia se come seis");

							}
						}
						if (come4) {
							come4 = false;
							if (jugadores[1].tieneDos(cartasNombres, mazo)) {
								jugadores[1].tirarUnDos(cartasNombres, mazo);
								come6 = true;
								System.out.println("Tira otro 2");
								finBucle = true;
							} else {
								jugadores[1].repartir(mazo, 4);
								System.out.println("Fracia se come cuatro");

							}
						}
						if (come2) {
							come2 = false;
							if (jugadores[1].tieneDos(cartasNombres, mazo)) {
								jugadores[1].tirarUnDos(cartasNombres, mazo);
								come4 = true;
								System.out.println("Tira un 2");
								finBucle = true;
							} else {
								jugadores[1].repartir(mazo, 2);
								System.out.println("Fracia se come dos");

							}
						}

					}

					while (!finBucle) {
						int paloCartaTirada = 0;
						int numeroCartaTirada = 0;
						int i = getPosicionFilaCartaMesa(jugadores[1], cartasNombres);
						int j = getPosicionColumnaCartaMesa(jugadores[1], cartasNombres);
						for (int p = 0; p < 4; p++) {
							for (int n = 0; n < 12; n++) {
								if (jugadores[1].getMano()[p][n]) {

									if (!cartaTirada) {
										if (jugadores[1].coincidePaloONumero(p, n, i, j)) {
											jugadores[1].descartar(mazoDescartado, p, n);
											jugadores[1].intercambio(mazoDescartado, i, j);
											paloCartaTirada = p;
											numeroCartaTirada = n;
											cartaTirada = true;
											System.out.println("\nFracia tira un " + cartasNombres[p][n]);
										}

									}

								}

							}
						}

						if (cartaTirada) {

							if (cartasNombres[paloCartaTirada][numeroCartaTirada].getNumeroDeCarta() == 2) {
								come2 = true;
								finBucle = true;

							} else if (cartasNombres[paloCartaTirada][numeroCartaTirada].getNumeroDeCarta() == 4) {
								System.out.println("\nSaltea jugador...\n");
								agarroDelMazo = true;
								turno = false;
								cartaTirada = false;
							} else if (cartasNombres[paloCartaTirada][numeroCartaTirada].getNumeroDeCarta() == 7) {
								System.out.println("\nTira de nuevo...\n");
								agarroDelMazo = true;
								turno = false;
								cartaTirada = false;
							} else if (cartasNombres[paloCartaTirada][numeroCartaTirada].getNumeroDeCarta() == 12) {
								System.out.println("\nCambia sentido el sentido...\n");
								agarroDelMazo = true;
								turno = false;
								cartaTirada = false;
							} else if (cartasNombres[paloCartaTirada][numeroCartaTirada].getNumeroDeCarta() == 10
									&& !jugadores[0].terminarRonda()) {

								int palo = Jugadores.paloRan();
								for (int v = 0; v < 4; v++) {
									for (int b = 0; b < 12; b++) {
										if (Jugadores.getCartaMesa()[v][b]) {
											Jugadores.cartaMesa[v][b] = false;
											Jugadores.cartaMesa[palo][b] = true;
										}
									}
								}
								switch (palo) {
								case 0:
									System.out.println("Fracia cambio a espada");
									break;
								case 1:
									System.out.println("Fracia cambio a copa");
									break;
								case 2:
									System.out.println("Fracia cambio a oro");
									break;
								case 3:
									System.out.println("Fracia cambio a basto");
									break;
								}

								finBucle = true;
								turno = false;
							} else {
								finBucle = true;
							}
						}

						if (!cartaTirada && agarroDelMazo) {
							System.out.println("Fracia pasa");
							finBucle = true;
						}

						if (!cartaTirada && !agarroDelMazo) {
							jugadores[1].repartir(mazo, 1);
							agarroDelMazo = true;
							System.out.println("Fracia agarra del mazo\n");
						}

					}

					agarroDelMazo = false;

					if (jugadores[1].terminarRonda()) {
						System.out.println("Francia se quedo sin cartas, gana la ronda " + rondas);
					}

					if (jugadores[0].mazoVacio(mazo)) {
						jugadores[0].reiniciarMazo(mazoDescartado, mazo);
						System.out.println("\nMezclando mazo...\n");
					}
				}
				jugador1FinalizoRonda = false;

			}
			jugadores[0].sumarPuntos(puntosMano(jugadores[0], cartasNombres));
			jugadores[1].sumarPuntos(puntosMano(jugadores[1], cartasNombres));
			System.out.println("\nFINALIZO LA RONDA\n");
			System.out.println("\nPuntuacion " + nombreJugador + ": [" + jugadores[0].getPuntuacion() + "] Puntos");
			System.out.println("\nPuntuacion Fracia: [" + jugadores[1].getPuntuacion() + "] Puntos");
			System.out.println("\n..............................................\n");
		}

		if (jugadores[0].getPuntuacion() < jugadores[1].getPuntuacion()) {
			System.out.println("\nFIN DEL JUEGO. GANASTE!!!:) \nFRANCIA QUEDO SEGUNDO OTRA VEZ ");
		} else {
			System.out.println("\nFIN DEL JUEGO. JODETE :( ");
		}

		System.out.println("\nGRACIAS POR JUGAR.\n");

		teclado.close();
	}

	public static void cambiarSentido(int sentido) {
		sentido *= (-1);
	}

	public static void mostrarCartasMano(Jugadores jugadores, Cartas[][] cartasNombres) {

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 12; j++) {
				if (jugadores.getMano()[i][j]) {
					System.out.println(cartasNombres[i][j]);
				}
			}
		}

	}

	public static int puntosMano(Jugadores jugadores, Cartas[][] cartasNombres) {

		int puntos = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 12; j++) {
				if (jugadores.getMano()[i][j]) {
					puntos += cartasNombres[i][j].getNumeroDeCarta();
				}
			}
		}
		return puntos;

	}

	public static void mostrarManoBooleana(Jugadores jugadores, Cartas[][] cartasNombres) {

		for (int i = 0; i < 4; i++) {
			System.out.println();
			for (int j = 0; j < 12; j++) {
				if (jugadores.getMano()[i][j]) {
					System.out.print(true + " ");
				} else {
					System.out.print(false + " ");
				}
			}
		}

	}

	public static int mostrarCantidadCartasMano(Jugadores jugadores, Cartas[][] cartasNombres) {

		int cantidad = 1;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 12; j++) {
				if (jugadores.getMano()[i][j]) {
					cantidad++;
				}
			}
		}

		return cantidad;

	}

	public static Cartas mostrarCartasManoX(Jugadores jugadores, Cartas[][] cartasNombres, int carta) {

		int cantidad = 1;
		boolean cartaCorrecta = false;
		Cartas cartaX = null;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 12; j++) {
				if (jugadores.getMano()[i][j]) {
					if (i < 4 || j < 2) {
						if (!cartaCorrecta) {
							if (cantidad == carta) {
								cartaX = cartasNombres[i][j];
								cartaCorrecta = true;
							}
						}

						cantidad++;
					}

				}
			}
		}
		return cartaX;

	}

	public static void mostrarCartaMesa(Jugadores jugadores, Cartas[][] cartasNombres) {

		System.out.print("Mesa: [");
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 12; j++) {
				if (Jugadores.getCartaMesa()[i][j]) {
					System.out.print(cartasNombres[i][j]);
				}
			}
		}
		System.out.println("]");

	}

	public static void mostrarPaloYNumeroDeCarta(Jugadores jugadores, Cartas[][] cartasNombres) {

		for (int i = 0; i <= 4; i++) {
			for (int j = 0; j < 12; j++) {
				if (Jugadores.getCartaMesa()[i][j]) {
					for (int p = 0; p < 4; p++) {
						for (int n = 0; n < 12; n++) {
							if (jugadores.getMano()[p][n]) {

								System.out.print(cartasNombres[p][n] + " - ");
								System.out.println(jugadores.coincidePaloONumero(p, n, i, j));
							}
						}
					}

				}
			}

		}

	}

	public static void mostrarMazoConCartas(boolean mazo[][], Cartas[][] cartasNombres) {

		for (int i = 0; i < 4; i++) {
			System.out.println("");
			for (int j = 0; j < 12; j++) {
				if (mazo[i][j] != false) {
					System.out.print(cartasNombres[i][j] + " - ");
				}
			}

		}

	}

	public static void mostrarMazoBooleano(boolean mazo[][], Cartas[][] cartasNombres) {

		System.out.print("Mazo boolean:");
		for (int i = 0; i < 4; i++) {
			System.out.println("");
			for (int j = 0; j < 12; j++) {
				System.out.print(mazo[i][j] + " - ");
			}

		}

	}

	public static int getPosicionFilaCartaMesa(Jugadores jugadores, Cartas[][] cartasNombres) {

		int posicion = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 12; j++) {
				if (Jugadores.getCartaMesa()[i][j]) {
					posicion = i;
				}
			}
		}

		return posicion;

	}

	public static int getPosicionColumnaCartaMesa(Jugadores jugadores, Cartas[][] cartasNombres) {

		int posicion = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 12; j++) {
				if (Jugadores.getCartaMesa()[i][j]) {
					posicion = j;
				}
			}
		}

		return posicion;

	}

	public static void cambioDePalo(int opcion, Palos palo) {
		System.out.println("Cambio a " + palo);
		for (int v = 0; v < 4; v++) {
			for (int b = 0; b < 12; b++) {
				if (Jugadores.getCartaMesa()[v][b]) {
					Jugadores.cartaMesa[v][b] = false;
					Jugadores.cartaMesa[opcion - 1][b] = true;
				}
			}
		}
	}

	public static void reglasDelJodete() {
		System.out.println("¿En qué consiste el Jodete?\r\n" + "\r\n"
				+ "Jodete es un juego de cartas que consiste en descartarse todas las cartas de tu mano. \r\n"
				+ "Pueden jugar 4 en total. Pero acá vamos a jugar solo contra Francia.\r\n" + "\r\n"
				+ "¿Cómo inicia el juego? \r\n" + "\r\n"
				+ "Los jugadores inician con 5 cartas en su mano y una carta en la mesa. \r\n"
				+ "Las cuales las vas a ver por consola. Claro que no vas a ver las cartas de Francia \r\n"
				+ "pero vas a ver la cantidad total que tiene. \r\n" + "\r\n" + "¿Cómo le ganas a Francia? \r\n"
				+ "\r\n" + "El primero que se quede sin cartas gana la ronda. Las cartas que no fueron descartadas \r\n"
				+ "serán sumadas, ahora, el que sume más de 100 pierde ¡trata de descartar primero las \r\n"
				+ "más grande dentro de lo posible! \r\n" + "\r\n" + "¿Cómo descartas tus cartas? \r\n" + "\r\n"
				+ "La carta que salga al azar del mazo, con la que iniciamos en nuestra mesa de juego \r\n"
				+ "será la carta que nos dice que podemos tirar. Esta carta será reemplazada por la siguiente \r\n"
				+ "carta que descarte el jugador en su turno. Podes descartar una carta que tenga \r\n"
				+ "el mismo numero o palo que la carta de la mesa. \r\n" + "\r\n" + "POR EJEMPLO: \r\n\n"
				+ "iniciamos con la Carta 1 de ORO, entonces podes descartar una carta que sea de ORO o que tenga el número 1. \r\n"
				+ "Siguiendo en este caso imaginemos que tenes la Carta 1 de COPA y decidís descartarla. \r\n"
				+ "El siguiente jugador tendrá que tirar una carta de COPA o un 1. ¿Qué pasa entonces si no tenes una carta \r\n"
				+ "que cumpla con las condiciones para descartas? En ese caso, levantas una carta del Mazo. \r\n"
				+ "Y si al levantar una carta y esa carta tampoco cumple entonces vas a tener que “PASAR”. \r\n"
				+ "Vos elegís que hacer, si levantar una carta, si pasar, o si tirar. Si decidís tirar \r\n"
				+ "vas a elegir de tu mano, ósea tus cartas van a estar enumeradas. \r\n"
				+ "Para que ingresando un numero puedas elegir cual tirar.\r\n" + "\r\n" + "¿Cartas especiales? \r\n"
				+ "\r\n"
				+ "Claro que hay cartas especiales. Las cuales te ayudar a descartar más cartas en un solo turno, \r\n"
				+ "cambiar el rumbo de la ronda, y también hacer que Francia tengan que comerse cartas que tal vez no esperaba. \r\n"
				+ "Claro que también te pueden hacer comer a vos. Vamos a estar jugando con las siguientes cartas especiales, \r\n"
				+ "claro que solo se van a poder descartar cuando el palo de la carta en la mesa sea el mismo. \r\n"
				+ "\r\n" + "2 HACE COMER 2 CARTAS AL SIGUIENTE JUGADOR\r\n" + "\r\n" + "4 SALTEA JUGADOR \r\n" + "\r\n"
				+ "7 TIRA DE NUEVO\r\n" + "\r\n" + "10 CAMBIA DE PALO \r\n"
				+ "Y acá para elegir que palo queres cambiar va a ser con un menú numérico también.\r\n" + "\r\n"
				+ "Y por último el 12 CAMBIA EL SENTIDO DE LOS TURNOS. \r\n" + "\r\n"
				+ "Prepárate para las guerras de cartas\r\n" + "\r\n"
				+ "Si Francia quiere que te comas 2, vos podés esquivarlas y dárselas de vuelta teniendo un 2, \r\n"
				+ "Francia en este caso se puede comer 4. En consola te vamos a preguntar si tenes un dos \r\n"
				+ "o que queres hacer en esa situación, te aconsejamos que no mientas. \r\n"
				+ "Porque cuidado. que existe la posibilidad de que te comas una gran suma de cartas. \r\n" + "\r\n"
				+ "Bien.\r\n" + "\r\n" + "¿Tamos?" + "");

	}

	public static void cartasEspeciales() {
		System.out.println("- 2 HACE COMER 2 CARTAS AL SIGUIENTE JUGADOR\r\n" + "\r\n" + "- 4 SALTEA JUGADOR \r\n"
				+ "\r\n" + "- 7 TIRA DE NUEVO\r\n" + "\r\n" + "- 10 CAMBIA DE PALO \r\n" + "\r\n"
				+ "- 12 CAMBIA EL SENTIDO DE LOS TURNOS. \r\n");
		;

	}
	

	public static void actualizaDos() {
		for (int v = 0; v < 4; v++) {
			for (int b = 0; b < 12; b++) {
				if (Jugadores.getCartaMesa()[v][b]) {
					Jugadores.cartaMesa[v][b] = false;
					Jugadores.cartaMesa[v][b] = true;
				}
			}
		}
	}
}
