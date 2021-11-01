package it.uniroma1.metodologie2018.javabomber.entities;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import ClassiMadre.SfornaNemici;
import ClassiMadre.SfornaNemici.State;
//import Sprites.Enemy.State;
import it.uniroma1.metodologie2018.javabomber.JavaBomber;
import it.uniroma1.metodologie2018.javabomber.interfaces.Particolarità;
import it.uniroma1.metodologie2018.javabomber.screens.PlayScreen;

/**
 * classe Nemico Originale (controllare superclasse)
 * @author Edoardo
 *
 */
public class EnemyOriginale extends SfornaNemici implements Particolarità{
	public EnemyOriginale(World world,PlayScreen screen,float posizioneX,float posizioneY) {
		spriteMorte = new Array<Sprite>();
		atlasDestra = new TextureAtlas("ORIGINALE_DESTRA.pack");
		atlasMorte = new TextureAtlas("ORIGINALE_MORTE.pack");
		atlasAlto = new TextureAtlas("ORIGINALE_ALTO.pack");
		atlasBasso = new TextureAtlas("ORIGINALE_BASSO.pack");
		
		
		
		disegnaDestra();
		disegnaAlto();
		disegnaBasso();
		disegnaMorte();
		
		for(Sprite sp : spriteDestra) {
			Sprite spr2 = new Sprite(sp);
			spr2.flip(true, false);
			framesSinistra.add(spr2);
		}
		
		spr = new Sprite();
		
		//leftStand = new TextureRegion();
		
		framesAlto = riempiFrames(spriteAlto, framesAlto);
		framesBasso = riempiFrames(spriteBasso, framesBasso);
		framesDestra = riempiFrames(spriteDestra, framesDestra);
		framesMorte = riempiFrames(spriteMorte, framesMorte);
		
		
		destra = new Animation<TextureRegion>(0.1f,framesDestra);
		sinistra = new Animation<TextureRegion>(0.1f,framesSinistra);
		alto = new Animation<TextureRegion>(0.1f,framesAlto);
		basso = new Animation<TextureRegion>(0.1f,framesBasso);
		morte = new Animation<TextureRegion>(0.1f,framesMorte);
		
		framesAlto.clear();
		framesBasso.clear();
		framesDestra.clear();
		framesMorte.clear();
		framesSinistra.clear();
		
		stati.add(State.DOWN);
		stati.add(State.LEFT);
		stati.add(State.UP);
		stati.add(State.RIGHT);
		
		this.world=world;
		Random r = new Random();
		
		stateTimer = r.nextInt(4);
		
		stati.shuffle();
		
		currentState = stati.get(0);
		previuosState = stati.get(0);
		
		this.posizioneX=posizioneX;
		this.posizioneY=posizioneY;
		
		defineEnemy(posizioneX,posizioneY);
		
	}
	
	@Override
	public void update(float dt) {
		
		if(morto) {
			tempoDellaMorte+=0.1f;
			PolygonShape shape2 = new PolygonShape();
			shape2.setAsBox((float) (40/BomberMan.PPM), 40/BomberMan.PPM);
			
			
			
			fdef.shape = shape2;
			fdef.filter.categoryBits = JavaBomber.ENEMY_ORIGINAL;
			fdef.filter.maskBits = JavaBomber.BLOCK_BIT | JavaBomber.BOMBER_BIT | JavaBomber.BOMB_BIT | JavaBomber.WALL_BIT;
			b2body.createFixture(fdef).setUserData(this);
			
			
			
			fixture = b2body.createFixture(fdef);
			fixture.setUserData(this);
		}
		
		
		setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
		
	}
	
	@Override
	public void defineEnemy(float posizioneX,float posizioneY) {
		
		bdef.position.set(posizioneX/BomberMan.PPM,posizioneY/BomberMan.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		
		b2body = world.createBody(bdef);

		shape.setAsBox((float) (20/BomberMan.PPM), 21/BomberMan.PPM);
		
		
		
		fdef.shape = shape;
		fdef.filter.categoryBits = JavaBomber.ENEMY_ORIGINAL;
		fdef.filter.maskBits = JavaBomber.BLOCK_BIT | JavaBomber.BOMBER_BIT | JavaBomber.BOMB_BIT | JavaBomber.WALL_BIT;
		b2body.createFixture(fdef).setUserData(this);
		
		
		
		fixture = b2body.createFixture(fdef);
		fixture.setUserData(this);
	}
	
	@Override
	public TextureRegion muovi(State x,float dt) {
		
		
		switch(currentState) {
		case UP:
			b2body.applyLinearImpulse(new Vector2(0,4)	,b2body.getWorldCenter() , true);
			region = alto.getKeyFrame(tempoDisegno,true);
			
			break;
		case RIGHT:
			b2body.applyLinearImpulse(new Vector2(4,0), b2body.getWorldCenter(), true);
			region = destra.getKeyFrame(tempoDisegno,true);
			
		break;
		case LEFT:
			b2body.applyLinearImpulse(new Vector2(-4,0), b2body.getWorldCenter(), true);
			region = sinistra.getKeyFrame(tempoDisegno,true);
			
		break;
		case DOWN:
			default:
			b2body.applyLinearImpulse(new Vector2(0,-4), b2body.getWorldCenter(), true);
			region = basso.getKeyFrame(tempoDisegno,true);
		
			break;
		}
		
		tempoDisegno = currentState == previuosState ? tempoDisegno + dt : 0;
		
		previuosState = currentState;
		return region;
		
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
				b2body.applyLinearImpulse(new Vector2(2,0), b2body.getWorldCenter(), true);
				region = destra.getKeyFrame(tempoDisegno,true);
				stop = leftStand;
			break;
			case DOWN:
				default:
				b2body.applyLinearImpulse(new Vector2(0,2), b2body.getWorldCenter(), true);
				region = alto.getKeyFrame(tempoDisegno,true);
				stop = downStand;
				break;
			}
			
			tempoDisegno = currentState == previuosState ? tempoDisegno + dt : 0;
			
			previuosState = currentState;
			return region;
			
		}
	
	@Override
	public State getState(boolean muovi) {
		
		if(muovi) {
			for(State e : stati) {
				if(e!=currentState) {
					currentState=e;
					stati.shuffle();
					break;
				}
			}
		}
		return currentState;
	}
	
	public void muovi() {
		stati.shuffle();
		int index=-1;
		tempotempo=0.0f;
		for(State e : stati) {
			++index;
			if(e==currentState) {
				break;
			}
		}
		if(index==stati.size-1)index = 0;
		currentState = stati.get(index+1);
	}
	
	public void morto() {
		morto=true;
	}
	
	@Override
	public void disegnaDestra() {
		spriteDestra = atlasDestra.createSprites();
		
	}

	@Override
	public void disegnaMorte() {
		spriteMorte = atlasMorte.createSprites();
		
	}

	@Override
	public void disegnaAlto() {
		spriteAlto = atlasAlto.createSprites();
		
	}

	@Override
	public void disegnaBasso() {
		spriteBasso = atlasBasso.createSprites();
		
	}
}
