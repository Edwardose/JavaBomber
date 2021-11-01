package ClassiMadre;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import it.uniroma1.metodologie2018.javabomber.JavaBomber;
import it.uniroma1.metodologie2018.javabomber.entities.BomberMan;
import it.uniroma1.metodologie2018.javabomber.screens.PlayScreen;
/**
 * classe madre dei powerUp
 * @author Edoardo
 *
 */
public class PowerUp extends Sprite{
	
	protected FixtureDef fdef = new FixtureDef();
	protected Body body;
	protected Fixture fixture;
	protected BodyDef bdef = new BodyDef();
	protected boolean rimuovi = false;
	protected boolean attiva = false;
	protected World world;
	PolygonShape shape2 = new PolygonShape();
	
	public PowerUp(World world,PlayScreen screen,float posizioneX,float posizioneY) {
		this.world=world;
		bdef.position.set(posizioneX/BomberMan.PPM,posizioneY/BomberMan.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		fdef.isSensor = true;
		body = world.createBody(bdef);
		shape2.setAsBox((float) (21/BomberMan.PPM), 21/BomberMan.PPM);
		fdef.shape=shape2;
		fdef.filter.categoryBits = JavaBomber.POWER_UP;
		fdef.filter.maskBits = JavaBomber.BOMBER_BIT;
		body.createFixture(fdef).setUserData(this);
		fixture = body.createFixture(fdef);
		fixture.setUserData(this);
	}
	
	public Body getBody() {
		return body;
	}
	
	public BodyDef getBodyDef() {
		return bdef;
	}
	
}
