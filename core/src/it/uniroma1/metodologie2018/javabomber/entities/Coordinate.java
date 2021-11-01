package it.uniroma1.metodologie2018.javabomber.entities;

/**
 * classe Coordinate entità
 * @author Edoardo
 *
 */
public class Coordinate {
	public float x;
	public float y;
	public float tempo;
	public float tempoBomba;
	
	public Coordinate(float x, float y,float tempo,float tempoBomba) {
		this.x=x;
		this.y=y;
		this.tempo=tempo;
		this.tempoBomba=tempoBomba;
	}
	
	public Coordinate(float x, float y) {
		this.x=x;
		this.y=y;
	}
	
	@Override
	public String toString() {
		return ""+x+" "+y;
	}
}
