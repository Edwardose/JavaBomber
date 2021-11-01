package it.uniroma1.metodologie2018.javabomber.entities;



import com.badlogic.gdx.physics.box2d.*;

import it.uniroma1.metodologie2018.javabomber.JavaBomber;

/**
 * classe Bomba
 * @author Edoardo
 *
 */

public class Bomb {
	
	public boolean lancioBomba=false;
	public Body body;
	public FixtureDef fdef2 = new FixtureDef();
	public Shape shape2;
	public PolygonShape shape;
	public BodyDef bdef = new BodyDef();
	public Fixture fixtureBomba;
	public boolean prova=false;
	public boolean prova2 = false;
	
	public enum State{ ALTO,BASSO,DESTRA,SINISTRA,NULLO};
	
	public State currentState=State.NULLO;
	
	
	
	public Bomb(World world,float x,float y) {
		bdef.position.set(x,y);
		shape = new PolygonShape();
		
		body = world.createBody(bdef);
		body.setType(BodyDef.BodyType.DynamicBody);
		bdef.type = BodyDef.BodyType.DynamicBody;
		bdef.active = true;
		shape2 = new CircleShape();
		shape2.setRadius(0.16f);
		fdef2.shape = shape2;
		fdef2.filter.categoryBits = JavaBomber.BOMB_BIT;
		fdef2.filter.maskBits = JavaBomber.BOMBER_BIT;
		fdef2.isSensor = true;
		body.createFixture(fdef2).setUserData(this);
		body.setUserData(this);
		body.setActive(true);
		fixtureBomba = body.createFixture(fdef2);
		fixtureBomba.setUserData(this);
		
	}
	
	public void setPosizione(float x) {
		bdef.position.set(x,bdef.position.y);
	}

}
