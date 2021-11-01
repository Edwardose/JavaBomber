package it.uniroma1.metodologie2018.javabomber.world;

import java.awt.Rectangle;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import it.uniroma1.metodologie2018.javabomber.entities.Blocco;
import it.uniroma1.metodologie2018.javabomber.entities.BomberMan;
import it.uniroma1.metodologie2018.javabomber.entities.Enemy;
import it.uniroma1.metodologie2018.javabomber.entities.Muro;
	
/**
 * creazione mappa
 * @author Edoardo
 *
 */
public class B2WorldCreator {
	public Array<Blocco> blocchi = new Array<Blocco>();
	
	public B2WorldCreator(World world,TiledMap map) {
		
		for(MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
			com.badlogic.gdx.math.Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			blocchi.add(new Blocco(world,map,rect));
			
			
		}
		
		for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
			com.badlogic.gdx.math.Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			
			new Muro(world,map,rect);
		}
		
	}

}
