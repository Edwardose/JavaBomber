package it.uniroma1.metodologie2018.javabomber.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import ClassiMadre.PowerUp;
import it.uniroma1.metodologie2018.javabomber.JavaBomber;
import it.uniroma1.metodologie2018.javabomber.screens.PlayScreen;
/**
 * classe POWERUP
 * @author Edoardo
 *
 */
public class PowerUpDiminuisciTempo extends PowerUp{

	public boolean attiva = false;
	public boolean rimuovi = false;
	
	public float posizionamentoX;
	public float posizionamentoY;
	
	public PowerUpDiminuisciTempo (World world, PlayScreen screen, float posizioneX, float posizioneY) {
		super(world, screen, posizioneX, posizioneY);
		this.posizionamentoX = posizioneX;
		this.posizionamentoY = posizioneY;
	}
	
	public void attivazione() {
		attiva=true;
	}

}
