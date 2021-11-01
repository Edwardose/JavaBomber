package it.uniroma1.metodologie2018.javabomber.entities;

import java.awt.Rectangle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import ClassiMadre.InteractiveTileObject;
import it.uniroma1.metodologie2018.javabomber.JavaBomber;

/**
 * classe Blocco
 * @author Edoardo
 *
 */
public class Blocco extends InteractiveTileObject{
	public Body effe = body;
	public float posizioneX = body.getPosition().x;
	public float posizioneY = body.getPosition().y;
	public boolean rimuovi=false;
	
	public Blocco(World world, TiledMap map, com.badlogic.gdx.math.Rectangle rect) {
		super(world, map, rect);
		
		
		fixture.setUserData(this);

		setCategoryFilter(JavaBomber.BLOCK_BIT);
		
	}
	
	/**
	 * metodo per rimuovere blocco
	 */
	public void setRimuovi() {
		rimuovi = true;
	}

}
