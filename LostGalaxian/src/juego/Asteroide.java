package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Asteroide {
	
	double x;
	double y;
	double velocidad;
	private double angulo;
	private int direccion;
	Image imagAsteroide;
	
	public Asteroide(double x, double y, double velocidad, int direccion) {
		setX(x);
		setY(y);
		this.velocidad = velocidad;
		this.direccion = direccion;
		this.imagAsteroide = Herramientas.cargarImagen("images/asteroide.png");
	}
	
	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(this.imagAsteroide, this.x, this.y, this.y * 0.1, 1.3);
	}

	
	public void moverse(Entorno entorno) 
	{
		this.angulo = Math.PI/2 + Math.PI/4 * this.direccion;
		this.y+= this.velocidad*2 * Math.sin(this.angulo);
		this.x+= this.velocidad*2 * Math.cos(this.angulo);	
		if (this.x < 20 )
		   {
			this.direccion *= -1;
			this.x = 20; 
		   }
		if (this.x > entorno.ancho() -20 )
		   {
			this.direccion *= -1;
			this.x = entorno.ancho() -20 ;
		   }
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

	public int getDireccion() {
		return direccion;
	}

	public void setDireccion(int direccion) {
		this.direccion = direccion;
	}

	public void setY(double y) {
		this.y = y;
	}
}