package juego;

import java.awt.Color;

import entorno.Entorno;

public class ProyectilEnemigo {
	
	double x;
	double y;
	double velocidad;
	
	
	public ProyectilEnemigo(double x, double y, double velocidad) {
		setX(x);
		setY(y);
		setVelocidad(velocidad);
	}
	
	public void moverse() {
		this.y += getVelocidad();
	}
	
	public void dibujarse(Entorno entorno) {
		entorno.dibujarRectangulo(this.x, this.y, 5, 20, 0, Color.GREEN);
	}
	
	public boolean colisionConAsteroide(Asteroide asteroide, double maxDist) {
		return (Math.pow((this.x - asteroide.getX()), 2) + Math.pow((this.y - asteroide.getY()), 2) <= maxDist);
	}
	
	public boolean colisionConEnemigo(Enemigo enemigo, double maxDist) {
		return (Math.pow((this.x - enemigo.getX()), 2) + Math.pow((this.y - enemigo.getY()), 2) <= maxDist);
	}
	
	public boolean colisionConNave(Nave nave, double maxDist) {
		return (Math.pow((this.x - nave.getX()), 2) + Math.pow((this.y - nave.getY()), 2) < maxDist);
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