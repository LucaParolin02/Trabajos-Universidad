package juego;


import java.util.Random;

import java.awt.Color;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

import java.awt.Image;
import java.util.LinkedList;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;

	// Variables y métodos propios de cada grupo
	private Nave nave;
	private Proyectil proyectil;
	private LinkedList<Enemigo> enemigos;
	private LinkedList<EnemigoKamikaze> kamikazes;
	private LinkedList<Asteroide> asteroides;
	private LinkedList<ProyectilEnemigo> proyectilesEnemigos;
	private JefeFinal jefe;
	Image imgFondo;
	Image imgPerdiste;
	Image imgGanaste;
	
	private LinkedList<Image> imgVidas;
	
	private long tiempoTranscurrido;
	private int enemigosEliminados;
	private int kamikazesEliminados;
	private boolean gano;
	


	Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Lost Galaxian - Grupo 3 - v1", 800, 600);
		
		// Inicializar lo que haga falta para el juego
		// ...
		this.nave = new Nave(entorno.ancho()/2, entorno.alto()-100, 8);
		this.proyectil = null;
		this.enemigosEliminados = 0;
		this.enemigos = new LinkedList<>();
		this.kamikazes = new LinkedList<>();
		this.asteroides = new LinkedList<>();
		this.proyectilesEnemigos = new LinkedList<>();
		this.imgVidas = new LinkedList<>();
		imgFondo = Herramientas.cargarImagen("images/fondo.png");
		imgPerdiste = Herramientas.cargarImagen("images/perdiste.png");
		imgGanaste = Herramientas.cargarImagen("images/ganaste.png");
		
		crearEnemigos();
		crearKamikazes();
		crearAsteroides();
		
		// Inicia el juego!
		this.entorno.iniciar();
		this.tiempoTranscurrido = 0;
		this.enemigosEliminados = 0;
		this.kamikazesEliminados = 0;
		this.gano = false;
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y por lo
	 * tanto es el método más importante de esta clase. Aquí se debe actualizar el
	 * estado interno del juego para simular el paso del tiempo (ver el enunciado
	 * del TP para mayor detalle).
	 */
	public void tick() {
		this.tiempoTranscurrido += 1;
		
		entorno.dibujarImagen(imgFondo, entorno.ancho()/2, entorno.alto()/2, 0, 0.002 * entorno.alto()); // dibuja el fondo cada tick
		
		if (this.nave != null && gano == false) {
			//Controles del movimiento de la nave
			if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) && nave.getX() > 30) {
				nave.moverIzquierda();
			}else
			if (entorno.estaPresionada(entorno.TECLA_DERECHA) && nave.getX() < entorno.ancho() - 30) {
				nave.moverDerecha();
			}else
			if (entorno.estaPresionada('a') && nave.getX() > 20) {
				nave.moverIzquierda();
			}else
			if (entorno.estaPresionada('d') && nave.getX() < entorno.ancho() - 20) {
				nave.moverDerecha();
			}
			//Disparo de la nave
			if (this.proyectil == null && entorno.estaPresionada(entorno.TECLA_ESPACIO)) {
				this.proyectil = new Proyectil(this.nave.getX(), this.nave.getY()-10, 10);
			}
			
			//Mueve los proyectiles enemigos
			for (ProyectilEnemigo proyectilEnem : proyectilesEnemigos) {
				proyectilEnem.moverse();
			}
			
			//Mueve los Enemigos y cambia sus direcciones cada cierto tiempo o cuando colisiona con asteroides 
			//y dispara cada cierto tiempo.
			for (Enemigo enemigo : enemigos) {
				//cambia la direccion cada cierto tiempo o cuando colisiona con asteroides
				for (Asteroide asteroide: this.asteroides) {
					if (enemigo.colisionConAsteroide(asteroide, 50) || tiempoTranscurrido % 100 == 0) {
						enemigo.setDireccion(enemigo.getDireccion()*-1);
						break;
					}
				}
				
				//dispara cada cierto tiempo.
				if(tiempoTranscurrido % 100 == 0 && enemigo.getY() > 0) {
					ProyectilEnemigo proyectilEnemigo = new ProyectilEnemigo(enemigo.x, enemigo.y + 20, 5);
					proyectilesEnemigos.add(proyectilEnemigo);
				}
				
				enemigo.moverse(entorno);
			}
			
			//Mueve  los asteroides
			for (Asteroide asteroide : asteroides) {
				asteroide.moverse(entorno);
			}
			//Mueve los kamikazes
			for (EnemigoKamikaze kamikaze : kamikazes) {
				kamikaze.moverse(this.nave.x, this.nave.y);
			}
			
			//Mueve el proyectil de la nave
			if(this.proyectil != null) {
				this.proyectil.moverse();
				if (this.proyectil.getY() < 10) {
					this.proyectil = null;
				}
			}
			
			//Mueve el jefe final, cambia su direccion cuando colisiona con el borde y dispara cada cierto tiempo.
			//Y destruye al jefe cuando se queda sin vidas.
			if (this.jefe != null) {
				this.jefe.moverse(entorno);
				if(tiempoTranscurrido % 50 == 0 && jefe.getY() > 0) {
					ProyectilEnemigo proyectilEnemigo = new ProyectilEnemigo(this.jefe.x, this.jefe.y + 20, 5);
					proyectilesEnemigos.add(proyectilEnemigo);
					
					ProyectilEnemigo proyectilEnemigo1 = new ProyectilEnemigo(this.jefe.x - 100, this.jefe.y + 20, 5);
					proyectilesEnemigos.add(proyectilEnemigo1);
					
					ProyectilEnemigo proyectilEnemigo2 = new ProyectilEnemigo(this.jefe.x + 100, this.jefe.y + 20, 5);
					proyectilesEnemigos.add(proyectilEnemigo2);
				}
				
				//Le resta una vida cuando colisiona con el proyectil de la nave
				if (this.proyectil != null && this.jefe.colisionConProyectil(this.proyectil)) {
					this.jefe.vida--;
					this.imgVidas.removeLast();
					this.proyectil = null;
				}
				
				//Destruye al jefe final cuando se queda sin vidas
				if (this.jefe.vida <= 0) {
					this.jefe = null;
					this.gano = true;
				}
			}
			
			//Controla las collisions y destrucciones de los enemigos
			for (Enemigo enemigo : this.enemigos) {
				if (this.nave != null && enemigo.colisionConNave(this.nave, 1500)) {
					this.nave = null;
					break;
				}
				
				//Elimina enemigo y proyectil cuando colisionan.
				if(this.proyectil != null) {
					if (enemigo.colisionConProyectil(this.proyectil, 600)) {
						this.proyectil = null;
						this.enemigos.remove(enemigo);
						this.enemigosEliminados++;
						break;
					}
				}
				
				//Elimina a los enemigos que bajan de la pantalla.
				if (enemigo.getY() > entorno.alto() + 15) {
					this.enemigos.remove(enemigo);
					break;
				}
			}
			
			//Controla las colisiones y destrucciones de los kamikazes
			for (EnemigoKamikaze kamikaze : this.kamikazes) {
				if (this.nave != null && kamikaze.colisionConNave(this.nave, 1500)) {
					this.nave = null;
					break;
				}
				
				//Elimina kamikaze y proyectil cuando colisionan.
				if(this.proyectil != null) {
					if (kamikaze.colisionConProyectil(this.proyectil, 600)) {
						this.proyectil = null;
						this.kamikazes.remove(kamikaze);
						this.kamikazesEliminados++;
						break;
					}
				}
				
				//Elimina a los kamikazes que bajan de la pantalla.
				if (kamikaze.getY() > entorno.alto() + 15) {
					this.kamikazes.remove(kamikaze);
					break;
				}
			}
			
			//Controla las colisiones y destrucciones de los asteroides
			for (Asteroide asteroide : this.asteroides) {
				if (this.nave != null && asteroide.colisionConNave(this.nave, 1500)) {
					this.nave = null;
					break;
				}
					
				//Elimina proyectil cuando colisionan.
				if(this.proyectil != null) {
					if (asteroide.colisionConProyectil(this.proyectil, 600)) {
						this.proyectil = null;
						break;
					}
				}
				
				//Elimina a los asteroides que bajan de la pantalla.
				if (asteroide.getY() > entorno.alto() + 15) {
					this.asteroides.remove(asteroide);
					break;
				}
			}
			
			//Controla las colisiones y destrucciones de los proyectiles enemigos
			for (ProyectilEnemigo proyectilEnem : proyectilesEnemigos) {
				if (this.nave != null && proyectilEnem.colisionConNave(this.nave, 600)) {
					this.nave = null;
					proyectilesEnemigos.remove(proyectilEnem);
					break;
				}
				
				if (proyectilEnem.getY() > entorno.alto() + 15) {
					this.proyectilesEnemigos.remove(proyectilEnem);
					break;
				}
			}

			//Cuando la lista de enemigos es menor que 4, vuelve a crear un enemigo
			if (this.enemigos.size() < 4) {
				Random gen = new Random();
				int[] direcciones = {-1, 1};
				Enemigo enemigo = new Enemigo(gen.nextInt(entorno.ancho() - 50), - 40, 1, direcciones[gen.nextInt(2)]);
				enemigos.add(enemigo);
			}
			
			//Cuando la lista de kamikazes es menor que 2, vuelve a crear un kamikaze
			if (this.kamikazes.size() < 2) {
				Random gen = new Random();
				EnemigoKamikaze kamikaze = new EnemigoKamikaze(gen.nextInt(entorno.ancho() - 50), - 40, 1);
				
				this.kamikazes.add(kamikaze);
			}
			
			//Cuando la lista asteroides es menor que 6, vuelve a crear un asteroide
			if (this.asteroides.size() < 6) {
				Random gen = new Random();
				int[] direcciones = {-1, 1};
				Asteroide asteroide = new Asteroide(gen.nextInt(entorno.ancho() - 50), - 40, 0.5, direcciones[gen.nextInt(2)]);
				this.asteroides.add(asteroide);
			}
			
		} else {			
			if (entorno.estaPresionada('r')) {
				//Eliminar todos los objetos
				enemigos.clear();
				kamikazes.clear();
				asteroides.clear();
				proyectilesEnemigos.clear();
				this.proyectil = null;
				this.jefe = null;
				this.imgVidas.clear();
				
				//Crear objetos y reiniciar variables en 0
				crearAsteroides();
				crearEnemigos();
				crearKamikazes();
				this.nave = new Nave(entorno.ancho()/2, entorno.alto()-100, 8);
				this.tiempoTranscurrido = 0;
				this.enemigosEliminados = 0;
				this.kamikazesEliminados = 0;
				this.gano = false;
			}
		}
		
		//Dibujar Objetos
		dibujarObjetos();
		
		int enemCont = enemigosEliminados + kamikazesEliminados; //el total de enemigos
		
		//Crea el jefe final cuando los enemigos eliminados sean 6 o mas.
		if (enemCont >= 6) {
			if (gano == false && this.jefe == null) {
				this.jefe = new JefeFinal(entorno.ancho()/ 2, -40, 1, 1, 270, 70,5);
				
				for (int i = 0; i < this.jefe.vida; i++) {
					Image imgVida = Herramientas.cargarImagen("images/vida.png");
					imgVidas.add(imgVida);
				}
			}
		}
		
		entorno.cambiarFont("Fixedsys", 20, Color.WHITE);
		entorno.escribirTexto("Enemigos Eliminados:" + enemCont , 20, entorno.alto()-40);
		
		//Muestra el cartel perdiste cuando muere la nave
		if (this.nave == null) {
			entorno.dibujarImagen(imgPerdiste, entorno.ancho()/2, entorno.alto()/2, 0, 0.5);
		}
		
		//Muestra el cartel ganaste cuando gano es verdadero
		if (gano) {
			entorno.dibujarImagen(imgGanaste, entorno.ancho()/2, entorno.alto()/2, 0, 0.7);
		}
	}
	
	void dibujarObjetos() {
		if(this.proyectil != null) {
			this.proyectil.dibujarse(entorno);
		}
		
		for (ProyectilEnemigo proyectilEnem : proyectilesEnemigos) {
			proyectilEnem.dibujarse(entorno);
		}
		
		for (Enemigo enemigo : enemigos) {
			enemigo.dibujarse(entorno);
		}
		
		for (Asteroide asteroide : asteroides) {
			asteroide.dibujarse(entorno);
		}
		
		for (EnemigoKamikaze kamikaze : kamikazes) {
			kamikaze.dibujarse(entorno);
		}
		
		if (this.nave != null) {
			this.nave.dibujarse(entorno);
		}
		
		if (this.jefe != null) {
			this.jefe.dibujarse(entorno);
			for (int i = 0; i < imgVidas.size(); i++) {
				entorno.dibujarImagen(imgVidas.get(i), entorno.ancho() - 130 + i * 30, 10, 0, 0.01);
			}
		}
	}

	void crearEnemigos() {
		Random gen = new Random();
		
		//Crea a los enemigos a distintas alturas, con direcciones aleatorias.
		for (int i = 0; i < 4; i++) {
			int[] direcciones = {-1, 1};
			Enemigo enemigo = new Enemigo(gen.nextInt(entorno.ancho() - 50), i * -100 - 40, 1, direcciones[gen.nextInt(2)]);		
			this.enemigos.add(enemigo);
		}
	}
	
	void crearKamikazes() {
		Random gen = new Random();
		
		//Crea a los enemigos a distintas alturas, con direcciones aleatorias.
		for (int i = 0; i < 2; i++) {
			EnemigoKamikaze kamikaze = new EnemigoKamikaze(gen.nextInt(entorno.ancho() - 50), i * -100 - 40, 1);
			
			this.kamikazes.add(kamikaze);
		}
	}
	
	void crearAsteroides() {
		Random gen = new Random();
		
		//Crea a los asteroides a distintas alturas.
		for(int i = 0; i < 6 ; i++) {
			int[] direcciones = {-1, 1};
			Asteroide asteroide = new Asteroide(gen.nextInt(entorno.ancho() - 50), i * -120 - 40, 0.5, direcciones[gen.nextInt(2)]);
			this.asteroides.add(asteroide);
		}
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}