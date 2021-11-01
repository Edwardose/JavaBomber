package it.uniroma1.metodologie2018.javabomber;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



import it.uniroma1.metodologie2018.javabomber.screens.PlayScreen;

/**
 * 
 * @author Edoardo
 *
 */
public class JavaBomber extends Game{
	
	public SpriteBatch batch;
	
	//altezza , larghezza mappa del gioco
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 400;
	
	//short per le particolarità dei personaggio e collisioni
	public static final short ENEMY_BIT = 2;
	public static final short BLOCK_BIT = 4;
	public static final short BOMBER_BIT = 8;
	public static final short DIED_BIT = 0;
	public static final short BOMB_BIT = 32;
	public static final short WALL_BIT = 1;
	public static final short POWER_UP = 16;
	public static final short ENEMY_ORIGINAL = 64;
	public static final short BOSS = 128;
	public static final short BULLET = 256;
	
	
	
	
	
	
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
		
	}
	
	/**
	 * renderizza
	 */
	@Override
	public void render() {
		super.render();
	}
	
}
















