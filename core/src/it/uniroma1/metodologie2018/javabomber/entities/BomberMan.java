package it.uniroma1.metodologie2018.javabomber.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import it.uniroma1.metodologie2018.javabomber.JavaBomber;
import it.uniroma1.metodologie2018.javabomber.screens.PlayScreen;
/**
 * classe Personaggio Principale
 * @author Edoardo
 *
 */
public class BomberMan extends Sprite{
	public enum State{ UP,DOWN,LEFT,RIGHT,STANDING,BOMB};
	public State currentState;
	public State previousState;
	
	public boolean imbattibile=false;
	
	private SpriteBatch batch = new SpriteBatch();		
	
	public boolean morto=false;
	public boolean rinato = false;
	
	public Fixture fixture;
	
	public boolean piazzaBomba=false;
	
	public FixtureDef fdef = new FixtureDef();
	
	public World world;
	public Body b2body;
	public Body bomb;
	public Array<BodyDef> entità = new Array<BodyDef>();
	public static final float PPM =100;   //attrito per pixel
	
	
	
	//Texture BomberMan fermo
	public TextureRegion bombermanRightStand;
	private TextureRegion bomberStop;
	private TextureRegion bombermanDownStand;
	private TextureRegion bombermanLeftStand;
	private TextureRegion bombermanUpStand;
	
	//Animazioni BomberMan
	private Animation<TextureRegion> bomberRight;
	public Animation<TextureRegion> bomberDown;
	public Animation<TextureRegion> bomberLeft;
	private Animation<TextureRegion> bomberUp;
	
	//Frames BomberMan
	private Array<TextureRegion> framesRight = new Array<TextureRegion>();
	private Array<TextureRegion> framesLeft = new Array<TextureRegion>();
	public Array<TextureRegion> framesUp = new Array<TextureRegion>();
	private Array<TextureRegion> framesDown = new Array<TextureRegion>();
	
	//Tempo Gioco
	public float stateTimer;
	
	//Percorso Texture
	private Array<Sprite> regionRightLeft;
	private Array<Sprite> regionUp;
	private Array<Sprite> regionDown;
	
	//Sprite di appoggio
	public Sprite sprite;
	
	public BomberMan(World world,PlayScreen screen) {
		regionRightLeft = screen.getAtlasDestraSinistra().createSprites();
		regionUp = screen.getAtlasUp().createSprites();
		regionDown = screen.getAtlasDown().createSprites();
		
		this.world=world;
		
		currentState = State.STANDING;
		previousState = State.STANDING;
		stateTimer = 0;
		
		//creazione dei frames
		framesRight = riempiFrames(regionRightLeft,framesRight);
		framesDown = riempiFrames(regionDown,framesDown);
		framesUp = riempiFrames(regionUp,framesUp);
		
		//Flippo le texture a destra (per l'animazione a sinistra)
		for(Sprite e : regionRightLeft) {
			sprite = new Sprite(e);
			sprite.flip(true, false);
			framesLeft.add(sprite);
		}
		
		bomberDown = new Animation<TextureRegion>(0.1f,framesDown);
		bomberRight = new Animation<TextureRegion>(0.1f,framesRight);
		bomberLeft = new Animation<TextureRegion>(0.1f,framesLeft);
		bomberUp = new Animation<TextureRegion>(0.08f,framesUp);
		
		framesLeft.clear();
		framesRight.clear();
		framesDown.clear();
		framesUp.clear();
		
		defineBomberMan(40,40);
		
		bomberStop = new TextureRegion(screen.getAtlasDown().createSprite("basso (5)"));
		
		sprite = new Sprite(screen.getAtlasDestraSinistra().createSprite("destra (5)"));
		sprite.flip(true, false);
		
		bombermanLeftStand = new TextureRegion(sprite);
		bombermanRightStand = new TextureRegion(screen.getAtlasDestraSinistra().createSprite("destra (5)"));
		bombermanDownStand = new TextureRegion(screen.getAtlasDown().createSprite("basso (5)"));
		bombermanUpStand = new TextureRegion(screen.getAtlasUp().createSprite("alto (5)"));
		
		setBounds(0,0,21/BomberMan.PPM,42/BomberMan.PPM);
		setRegion(bomberStop);
		
	}
	
	/**
	 * riempimento dei frames 
	 * @param sprite
	 * @param frames
	 * @return
	 */
	public Array<TextureRegion> riempiFrames(Array<Sprite> sprite, Array<TextureRegion> frames) {
		for(Sprite e : sprite) {
			frames.add(e);
		}
		return frames;
	}
	
	/**
	 * getter del frames corrente
	 * @param dt
	 * @return
	 */
	public TextureRegion getFrame(float dt) {
		currentState=getState();
		
		TextureRegion region;
		switch(currentState) {
		case RIGHT:
			region = bomberRight.getKeyFrame(stateTimer,true);
			bomberStop = bombermanRightStand;
			break;
		case LEFT:
			region = bomberLeft.getKeyFrame(stateTimer,true);
			bomberStop = bombermanLeftStand;
			break;
		case DOWN:
			region = bomberDown.getKeyFrame(stateTimer,true);
			bomberStop = bombermanDownStand;
			break;
		case UP:
			region = bomberUp.getKeyFrame(stateTimer,true);
			bomberStop = bombermanUpStand;
			break;
		case STANDING:
			default:
				region = bomberStop;
				break;
		}
		
		stateTimer = currentState == previousState ? stateTimer + dt : 0;
		
		previousState = currentState;
		return region;
		
	}
	
	/**
	 * getter dello stato corrente
	 * @return
	 */
	public State getState() {
		if(b2body.getLinearVelocity().x > 0)
			return State.RIGHT;
		else if(b2body.getLinearVelocity().x <0)
			return State.LEFT;
		else if(b2body.getLinearVelocity().y<0)
			return State.DOWN;
		else if(b2body.getLinearVelocity().y>0)
			return State.UP;
		
		return State.STANDING;
	}
	
	/**
	 * metodo principale 
	 * @param dt
	 */
	public void update(float dt) {
		setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
		setRegion(getFrame(dt));
	}
	
	/**
	 * definisce BomberMan
	 * @param x
	 * @param y
	 */
	public void defineBomberMan(float x, float y) {
		BodyDef bdef = new BodyDef();
		bdef.position.set(x/BomberMan.PPM,x/BomberMan.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		
		b2body = world.createBody(bdef);
		
		
		
		PolygonShape shape2 = new PolygonShape();
		shape2.setAsBox((float) (10.5/BomberMan.PPM), 21/BomberMan.PPM);
		fdef.shape = shape2;
		
		fdef.filter.categoryBits = JavaBomber.BOMBER_BIT;
		//fdef.filter.maskBits = JavaBomber.BLOCK_BIT | JavaBomber.ENEMY_BIT | JavaBomber.BOMB_BIT;
		b2body.createFixture(fdef).setUserData(this);

		fixture = b2body.createFixture(fdef);
		fixture.setUserData(this);
	}
	
	/**
	 * setter della morte del giocatore
	 */
	public void setMorte() {
		
		if(!imbattibile)
			morto=true;
		imbattibile=true;
	}
	
	/**
	 * cambio particolarità del personaggio(immortalità , invulnerabilità bombe ecc)
	 * @param x
	 * @param y
	 */
	public void changeFixture(float x, float y) {
		BodyDef bdef = new BodyDef();
		bdef.position.set(40/BomberMan.PPM,40/BomberMan.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		
		b2body = world.createBody(bdef);
		
		fdef.isSensor = false;
		
		PolygonShape shape2 = new PolygonShape();
		shape2.setAsBox((float) (10.5/BomberMan.PPM), 21/BomberMan.PPM);
		fdef.shape = shape2;
		
		
		fdef.filter.maskBits = JavaBomber.WALL_BIT | JavaBomber.BLOCK_BIT | JavaBomber.POWER_UP;
		
		b2body.createFixture(fdef);
	}
	
	/**
	 * reset dei parametri del personaggio
	 * @param x
	 * @param y
	 */
	public void normal(float x,float y) {
		BodyDef bdef = new BodyDef();
		bdef.position.set(x,y);
		bdef.type = BodyDef.BodyType.DynamicBody;
		
		b2body = world.createBody(bdef);
		PolygonShape shape2 = new PolygonShape();
		shape2.setAsBox((float) (10.5/BomberMan.PPM), 21/BomberMan.PPM);
		fdef.shape = shape2;
		
		fdef.filter.categoryBits = JavaBomber.BOMBER_BIT;
		fdef.filter.maskBits = JavaBomber.WALL_BIT | JavaBomber.BLOCK_BIT | JavaBomber.POWER_UP | JavaBomber.ENEMY_BIT | JavaBomber.BOMB_BIT | JavaBomber.ENEMY_ORIGINAL | JavaBomber.BULLET | JavaBomber.BOSS;
		b2body.createFixture(fdef).setUserData(this);

		fixture = b2body.createFixture(fdef);
		fixture.setUserData(this);
	}
	
	/**
	 * particolarità immune alle bombe
	 * @param x
	 * @param y
	 */
	public void ghostMode(float x,float y) {
		BodyDef bdef = new BodyDef();
		bdef.position.set(x,y);
		bdef.type = BodyDef.BodyType.DynamicBody;
		
		b2body = world.createBody(bdef);
		PolygonShape shape2 = new PolygonShape();
		shape2.setAsBox((float) (10.5/BomberMan.PPM), 21/BomberMan.PPM);
		fdef.shape = shape2;
		
		fdef.filter.categoryBits = JavaBomber.BOMBER_BIT;
		fdef.filter.maskBits = JavaBomber.WALL_BIT | JavaBomber.BLOCK_BIT | JavaBomber.POWER_UP | JavaBomber.ENEMY_BIT | JavaBomber.ENEMY_ORIGINAL;
		b2body.createFixture(fdef).setUserData(this);

		fixture = b2body.createFixture(fdef);
		fixture.setUserData(this);
	}
	
	
}
