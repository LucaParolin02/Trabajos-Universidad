package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class JefeFinal {
	double x;
	double y;
	double ancho;
	double alto;
	double velocidad;
	private int direccion;
	int vida;
	Image imagJefe;
	
	public JefeFinal(double x, double y, double velocidad, int direccion, double ancho, double alto, int vida) {
		setX(x);
		setY(y);
		this.ancho = ancho;
		this.alto = alto;
		this.vida = vida;
		this.velocidad = velocidad;
		this.direccion = direccion;
		this.imagJefe = Herramientas.cargarImagen("images/jefe.png");
		
	}

	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(this.imagJefe, this.x, this.y, 0, 2);
	}
	
	public void moverse(Entorno entorno) 
	{
		if (this.y < 40) {
			this.y += this.velocidad;
		}else {

		this.x+= this.velocidad * this.direccion;
		if (this.x < 120 )
		   {
			this.direccion *= -1;
			this.x = 120; 
		   }
		if (this.x > entorno.ancho()-120 )
		   {
			this.direccion *= -1;
			this.x = entorno.ancho()-120;
		   }
		}
	}
	
	public boolean colisionConProyectil(Proyectil p) 
    {
        return (p.getY() >= this.y - (this.alto / 2) && p.getY() <= this.y + (this.alto / 2) &&
            p.getX() >= this.x - (this.ancho / 2) && p.getX() <= this.x + (this.ancho / 2));
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
}