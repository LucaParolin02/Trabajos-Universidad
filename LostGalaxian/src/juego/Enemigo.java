package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Enemigo {
	
	double x;
	double y;
	double velocidad;
	private double angulo;
	private int direccion;
	Image imagEnemigo;
	
	public Enemigo(double x, double y, double velocidad, int direccion) {
		setX(x);
		setY(y);
		this.velocidad = velocidad;
		this.direccion = direccion;
		this.imagEnemigo = Herramientas.cargarImagen("images/enemigo.gif");
		
	}

	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(this.imagEnemigo, this.x, this.y, 0, 2);
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
			this.x = entorno.ancho() -20;
		   }
	}
	
	public boolean colisionConProyectil(Proyectil proyectil, double maxDist) {
		return (Math.pow((this.x - proyectil.getX()), 2) + Math.pow((this.y - proyectil.getY()), 2) < maxDist);
	}
	
	public boolean colisionConAsteroide(Asteroide asteroide, double maxDist) {
		return (Math.pow((this.x - asteroide.getX()), 2) + Math.pow((this.y - asteroide.getY()), 2) < Math.pow(maxDist,2));
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
	
	public int getDireccion() {
		return direccion;
	}

	public void setDireccion(int direccion) {
		this.direccion = direccion;
	}

	public double getAngulo() {
		return angulo;
	}

	public void setAngulo(double angulo) {
		this.angulo = angulo;
	}
}