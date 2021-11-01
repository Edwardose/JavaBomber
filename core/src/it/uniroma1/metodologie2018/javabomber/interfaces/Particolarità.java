package it.uniroma1.metodologie2018.javabomber.interfaces;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ClassiMadre.SfornaNemici;
import ClassiMadre.SfornaNemici.State;

/**
 * interfaccia particolarità dei personaggi
 * @author Edoardo
 *
 */
public interface Particolarità {
	public abstract void defineEnemy(float posizioneX,float posizioneY);
	
	public abstract void update(float dt);
	
	public abstract void muovi();
	
	public TextureRegion muovi(State x,float dt);
	
	public abstract void disegnaDestra();
	
	public abstract void disegnaMorte();
	
	public void disegnaAlto();
	
	public void disegnaBasso();
}
