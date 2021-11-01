package it.uniroma1.metodologie2018.javabomber.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import ClassiMadre.SfornaNemici;
import ClassiMadre.SfornaNemici.State;
import Tools.Costanti;
import it.uniroma1.metodologie2018.javabomber.JavaBomber;
import it.uniroma1.metodologie2018.javabomber.interfaces.Particolarità;
import it.uniroma1.metodologie2018.javabomber.screens.PlayScreen;

/**
 * classe proiettile del boss
 * @author Edoardo
 *
 */
public class Proiettile extends SfornaNemici implements Particolarità{
	
	public Proiettile(World world,PlayScreen screen,float posizioneX,float posizioneY) {
		this.world=world;
		this.posizioneX=posizioneX;
		this.posizioneY=posizioneY;
		atlas = new TextureAtlas(Costanti.atlasBullet);
		spriteBasso = atlas.createSprites();
		framesBasso = riempiFrames(spriteBasso, framesBasso);
		basso = new Animation<TextureRegion>(0.1f,framesBasso);
		framesBasso.clear();
		defineEnemy(posizioneX,posizioneY);
		currentState = State.DOWN;
	}
	
	@Override
	public void defineEnemy(float posizioneX, float posizioneY) {


		bdef.position.set(posizioneX/BomberMan.PPM,(posizioneY/BomberMan.PPM));
		bdef.type = BodyDef.BodyType.DynamicBody;
		
		b2body = world.createBody(bdef);
		shape.setAsBox((float) (20/BomberMan.PPM), 20/BomberMan.PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = JavaBomber.BULLET;
		fdef.filter.maskBits = JavaBomber.BLOCK_BIT | JavaBomber.WALL_BIT | JavaBomber.BOMB_BIT | JavaBomber.BOMBER_BIT | JavaBomber.BLOCK_BIT;
		b2body.createFixture(fdef).setUserData(this);
		fixture = b2body.createFixture(fdef);
		fixture.setUserData(this);
	}

	@Override
	public void update(float dt) {
		setPosition(b2body.getPosition().x - getWidth()/2, (b2body.getPosition().y - getHeight()/2));
		
	}

	@Override
	public void muovi() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TextureRegion muovi(State x, float dt) {
		if(x==State.DOWN) {
			b2body.applyLinearImpulse(new Vector2(0,-3.5f), b2body.getWorldCenter(), true);
			region = basso.getKeyFrame(tempoDisegno,true);
		}
		return region;
		
	}
	
	public void muovi2(State x, float dt) {
		switch(x) {
		case DOWN:
			default:
			b2body.applyLinearImpulse(new Vector2(0,-2f), b2body.getWorldCenter(), true);
		}
	}
	
	@Override
	public void disegnaDestra() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disegnaMorte() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disegnaAlto() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disegnaBasso() {
		// TODO Auto-generated method stub
		
	}
	
}
