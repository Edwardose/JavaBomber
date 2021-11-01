package it.uniroma1.metodologie2018.javabomber.entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import ClassiMadre.InteractiveTileObject;
import it.uniroma1.metodologie2018.javabomber.JavaBomber;


/**
 * classe Muro (controllare Superclasse)
 * @author Edoardo
 *
 */
public class Muro extends InteractiveTileObject{

	public Muro(World world, TiledMap map, com.badlogic.gdx.math.Rectangle rect) {
		super(world, map, rect);
		
		
		setCategoryFilter(JavaBomber.WALL_BIT);
	}

}
