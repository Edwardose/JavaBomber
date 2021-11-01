package ClassiMadre;

import java.awt.Rectangle;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import it.uniroma1.metodologie2018.javabomber.entities.BomberMan;

/**
 * creazione oggetti per la mappa
 * @author Edoardo
 *
 */
public abstract class InteractiveTileObject {
	protected World world;
	protected TiledMap map;
	protected TiledMapTile tile;
	protected com.badlogic.gdx.math.Rectangle bounds;
	protected Body body;
	protected Fixture fixture;
	protected FixtureDef fdef = new FixtureDef();
	
	public InteractiveTileObject(World world,TiledMap map,com.badlogic.gdx.math.Rectangle rect) {
		this.world = world;
		this.map = map;
		this.bounds = rect;
		
		BodyDef bdef = new BodyDef();
		
		PolygonShape shape = new PolygonShape();
		
		bdef.type = BodyDef.BodyType.StaticBody;
		bdef.position.set((float)(rect.getX()+rect.getWidth()/2)/BomberMan.PPM, (float)(rect.getY()+ rect.getHeight() /2)/BomberMan.PPM);
		
		body = world.createBody(bdef);
		shape.setAsBox((float)rect.getWidth()/2/BomberMan.PPM, (float)rect.getHeight() /2/BomberMan.PPM);
		fdef.shape = shape;
		
		fixture = body.createFixture(fdef);
		
		
	}
	
	/**
	 * cambio di specialità degli oggetti
	 * @param filterBit
	 */
	public void setCategoryFilter(short filterBit) {
		Filter filter = new Filter();
		filter.categoryBits = filterBit;
		fixture.setFilterData(filter);
	}
	/**
	 * cambio di specialità degli oggetti
	 * @param filterBit
	 */
	public void setMaskFilter(short filterBit) {
		Filter filter = new Filter();
		filter.categoryBits = filterBit;
		fixture.setFilterData(filter);
	}
}
