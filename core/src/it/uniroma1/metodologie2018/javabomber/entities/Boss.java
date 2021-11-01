package it.uniroma1.metodologie2018.javabomber.entities;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import ClassiMadre.SfornaNemici;
import ClassiMadre.SfornaNemici.State;
import Tools.Costanti;
import it.uniroma1.metodologie2018.javabomber.JavaBomber;
import it.uniroma1.metodologie2018.javabomber.interfaces.Particolarità;
import it.uniroma1.metodologie2018.javabomber.screens.PlayScreen;
/**
 * classe del Boss(controllare super classe)
 * @author Edoardo
 *
 */
public class Boss extends SfornaNemici implements Particolarità{	
	
	public Boss(World world,PlayScreen screen,float posizioneX,float posizioneY) {
		atlas = new TextureAtlas(Costanti.atlasBoss);
		spriteBasso = atlas.createSprites();
		framesBasso = riempiFrames(spriteBasso, framesBasso);
		basso = new Animation<TextureRegion>(0.1f,framesBasso);
		framesBasso.clear();
		this.world=world;
		this.posizioneX=posizioneX;
		this.posizioneY=posizioneY;
		defineEnemy(posizioneX,posizioneY);
		currentState = State.RIGHT;
	}
	
	@Override
	public void defineEnemy(float posizioneX,float posizioneY) {
		bdef.position.set(posizioneX/BomberMan.PPM,posizioneY/BomberMan.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		
		b2body = world.createBody(bdef);
		
		shape.setAsBox((float) (25/BomberMan.PPM), 42/BomberMan.PPM);
		
		
		fdef.isSensor = false;
		fdef.shape = shape;
		fdef.filter.categoryBits = JavaBomber.BOSS;
		fdef.filter.maskBits = JavaBomber.BLOCK_BIT | JavaBomber.BOMB_BIT | JavaBomber.WALL_BIT | JavaBomber.BOMBER_BIT;
		b2body.createFixture(fdef).setUserData(this);
		
		
		
		fixture = b2body.createFixture(fdef);
		fixture.setUserData(this);
		
	}


	@Override
	public void update(float dt) {
		posizioneX=b2body.getPosition().x - getWidth()/2;
		posizioneY= b2body.getPosition().y - getHeight()/2;
		setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
		
	}

	@Override
	public TextureRegion muovi(State x, float dt) {
		switch(currentState) {
		case RIGHT:
			b2body.applyLinearImpulse(new Vector2(4,0), b2body.getWorldCenter(), true);
			region = basso.getKeyFrame(tempoDisegno,true);
			
		break;
		case LEFT:
			default:
			b2body.applyLinearImpulse(new Vector2(-4,0), b2body.getWorldCenter(), true);
			region = basso.getKeyFrame(tempoDisegno,true);

		break;
		}
		
		tempoDisegno = currentState == previuosState ? tempoDisegno + dt : 0;
		
		previuosState = currentState;
		return region;
	}
	
	public void muovi2(State x,float dt) {
		switch(currentState) {
		case RIGHT:
			b2body.applyLinearImpulse(new Vector2(4,0), b2body.getWorldCenter(), true);

			
		break;
		case LEFT:
			default:
			b2body.applyLinearImpulse(new Vector2(-4,0), b2body.getWorldCenter(), true);

			
		break;
		}
		
		tempoDisegno = currentState == previuosState ? tempoDisegno + dt : 0;
		
		previuosState = currentState;
	}
	
	@Override
	public TextureRegion muoviIndietro(State x,float dt) {
		
		
		switch(currentState) {
		case UP:
			b2body.applyLinearImpulse(new Vector2(0,-2)	,b2body.getWorldCenter() , true);
			region = basso.getKeyFrame(tempoDisegno,true);
			stop = upStand;
			break;
		case RIGHT:
			b2body.applyLinearImpulse(new Vector2(-2,0), b2body.getWorldCenter(), true);
			region = sinistra.getKeyFrame(tempoDisegno,true);
			stop = rightStand;
		break;
		case LEFT:
			default:
			b2body.applyLinearImpulse(new Vector2(2,0), b2body.getWorldCenter(), true);
			region = destra.getKeyFrame(tempoDisegno,true);
			stop = leftStand;
		break;
		}
		
		tempoDisegno = currentState == previuosState ? tempoDisegno + dt : 0;
		
		previuosState = currentState;
		return region;
		
	}

	@Override
	public void morto() {
		morto=true;
		
	}

	@Override
	public void muovi() {
		tempotempo = 0.0f;
		Random r = new Random();
		int x = r.nextInt(2);
		if(x==0) currentState=State.RIGHT;
		else currentState=State.LEFT;
		
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
