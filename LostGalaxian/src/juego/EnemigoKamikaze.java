package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class EnemigoKamikaze {
	
	double x;
	double y;
	double velocidad;
	private double angulo;
	Image imagEnemigo;
	
	public EnemigoKamikaze(double x, double y, double velocidad) {
		setX(x);
		setY(y);
		this.velocidad = velocidad;
		this.imagEnemigo = Herramientas.cargarImagen("images/kamikaze.gif");
	}

	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(this.imagEnemigo, this.x, this.y, this.angulo - Math.PI/4 *2, 1.5);
	}
	
	public void moverse(double x2, double y2) 
	{
		this.angulo = Math.atan2(y2-this.y, x2 - this.x);
		this.y+= this.velocidad*2 * Math.sin(this.angulo);
		this.x+= this.velocidad*2 * Math.cos(this.angulo);
	}
	
	public boolean colisionConProyectil(Proyectil proyectil, double maxDist) {
		return (Math.pow((this.x - proyectil.getX()), 2) + Math.pow((this.y - proyectil.getY()), 2) < maxDist);
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
	

	public double getAngulo() {
		return angulo;
	}

	public void setAngulo(double angulo) {
		this.angulo = angulo;
	}
}