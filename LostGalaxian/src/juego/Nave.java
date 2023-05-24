package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Nave {
	
	double x;
	double y;
	double velocidad;
	Image imgNave;
	
	public Nave(double x, double y, double velocidad) {
		setX(x);
		setY(y);
		this.velocidad = velocidad;
		this.imgNave = Herramientas.cargarImagen("images/nave.gif");
	}
	
	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(imgNave, this.x, this.y+5, 0, 2);
	}
	
	public void moverIzquierda() {
		this.x -= this.velocidad;
	}
	
	public void moverDerecha() {
		this.x += this.velocidad;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	
}