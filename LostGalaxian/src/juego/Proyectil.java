package juego;

import java.awt.Color;

import entorno.Entorno;

public class Proyectil {
	
	double x;
	double y;
	double velocidad;
	
	public Proyectil(double x, double y, double velocidad) {
		setX(x);
		setY(y);
		setVelocidad(velocidad);
	}
	
	public void moverse() {
		this.y -= getVelocidad();
	}
	
	public void dibujarse(Entorno entorno) {
		entorno.dibujarRectangulo(this.x, this.y, 5, 20, 0, Color.YELLOW);
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
	
	public double getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(double velocidad) {
		this.velocidad = velocidad;
	}
}