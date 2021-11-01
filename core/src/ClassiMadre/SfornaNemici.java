package ClassiMadre;

import java.awt.Polygon;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import it.uniroma1.metodologie2018.javabomber.entities.Coordinate;

/**
 * classe madre dei nemici
 * @author Edoardo
 *
 */
public class SfornaNemici extends Hidden{
	
	protected Sprite spr;
	
	protected float tempoDellaMorte=0.0f;
	
	protected TextureRegion region;
	
	protected Array<Sprite> spriteDestra;
	protected Array<Sprite> spriteSinistra;
	protected Array<Sprite> spriteAlto;
	protected Array<Sprite> spriteBasso;
	protected Array<Sprite> spriteMorte;
	
	protected Animation<TextureRegion> destra;
	protected Animation<TextureRegion> sinistra;
	protected Animation<TextureRegion> alto;
	protected Animation<TextureRegion> basso;
	protected Animation<TextureRegion> morte;
	
	protected Array<TextureRegion> framesDestra = new Array<TextureRegion>();
	protected Array<TextureRegion> framesSinistra = new Array<TextureRegion>();
	protected Array<TextureRegion> framesAlto = new Array<TextureRegion>();
	protected Array<TextureRegion> framesBasso = new Array<TextureRegion>();
	protected Array<TextureRegion> framesMorte = new Array<TextureRegion>();
	
	protected TextureRegion rightStand;
	protected TextureRegion stop;
	protected TextureRegion downStand;
	protected TextureRegion leftStand;
	protected TextureRegion upStand;
	
	protected Array<State> stati = new Array<>();
	
	protected State currentState;
	protected State previuosState;
	
	protected Fixture fixture;
	
	protected FixtureDef fdef = new FixtureDef();
	
	protected ArrayList<Coordinate> posizioni = new ArrayList<>();
	
	protected World world;
	protected Body b2body;
	
	public static final float PPM =100;   //attrito per pixel
	
	protected float stateTimer;
	
	protected float tempoDisegno = 0;
	
	protected float posizioneX;
	protected float posizioneY;
	
	protected BodyDef bdef = new BodyDef();
	
	protected PolygonShape shape= new PolygonShape();
	
	protected boolean morto=false;
	
	protected float tempotempo=0.0f;
	
	protected TextureAtlas atlasDestra;
	
	protected TextureAtlas atlasBasso;
	
	protected TextureAtlas atlasAlto;
	
	protected TextureAtlas atlas;
	
	public TextureAtlas atlasMorte;
	
	/**
	 * stati del personaggio
	 * @author Edoardo
	 *
	 */
	public enum State {DOWN,LEFT,RIGHT,UP,STOP};
	
	/**
	 * movimento inverso 
	 * @param x
	 * @param dt
	 * @return
	 */
	protected TextureRegion muoviIndietro(State x,float dt) {
		
		
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
	
	/**
	 * ottieni stato corrente
	 * @param muovi
	 * @return
	 */
	protected State getState(boolean muovi) {
		
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
	
	//settaggio morto
	protected void morto() {
		morto=true;
	}
	
	/**
	 * riempimento frames da sprites
	 * @param sprite
	 * @param frames
	 * @return
	 */
	protected Array<TextureRegion> riempiFrames(Array<Sprite> sprite, Array<TextureRegion> frames) {
		for(Sprite e : sprite) {
			frames.add(e);
		}
		return frames;
	}
	//di seguito tutti i getter e setter
	
	public Sprite getSpr() {
		return spr;
	}

	public void setSpr(Sprite spr) {
		this.spr = spr;
	}

	public float getTempoDellaMorte() {
		return tempoDellaMorte;
	}

	public void setTempoDellaMorte(float tempoDellaMorte) {
		this.tempoDellaMorte = tempoDellaMorte;
	}

	public TextureRegion getRegion() {
		return region;
	}

	public void setRegion(TextureRegion region) {
		this.region = region;
	}

	public Array<Sprite> getSpriteDestra() {
		return spriteDestra;
	}

	public void setSpriteDestra(Array<Sprite> spriteDestra) {
		this.spriteDestra = spriteDestra;
	}

	public Array<Sprite> getSpriteSinistra() {
		return spriteSinistra;
	}

	public void setSpriteSinistra(Array<Sprite> spriteSinistra) {
		this.spriteSinistra = spriteSinistra;
	}

	public Array<Sprite> getSpriteAlto() {
		return spriteAlto;
	}

	public void setSpriteAlto(Array<Sprite> spriteAlto) {
		this.spriteAlto = spriteAlto;
	}

	public Array<Sprite> getSpriteBasso() {
		return spriteBasso;
	}

	public void setSpriteBasso(Array<Sprite> spriteBasso) {
		this.spriteBasso = spriteBasso;
	}

	public Array<Sprite> getSpriteMorte() {
		return spriteMorte;
	}

	public void setSpriteMorte(Array<Sprite> spriteMorte) {
		this.spriteMorte = spriteMorte;
	}

	public Animation<TextureRegion> getDestra() {
		return destra;
	}

	public void setDestra(Animation<TextureRegion> destra) {
		this.destra = destra;
	}

	public Animation<TextureRegion> getSinistra() {
		return sinistra;
	}

	public void setSinistra(Animation<TextureRegion> sinistra) {
		this.sinistra = sinistra;
	}

	public Animation<TextureRegion> getAlto() {
		return alto;
	}

	public void setAlto(Animation<TextureRegion> alto) {
		this.alto = alto;
	}

	public Animation<TextureRegion> getBasso() {
		return basso;
	}

	public void setBasso(Animation<TextureRegion> basso) {
		this.basso = basso;
	}

	public Animation<TextureRegion> getMorte() {
		return morte;
	}

	public void setMorte(Animation<TextureRegion> morte) {
		this.morte = morte;
	}

	public Array<TextureRegion> getFramesDestra() {
		return framesDestra;
	}

	public void setFramesDestra(Array<TextureRegion> framesDestra) {
		this.framesDestra = framesDestra;
	}

	public Array<TextureRegion> getFramesSinistra() {
		return framesSinistra;
	}

	public void setFramesSinistra(Array<TextureRegion> framesSinistra) {
		this.framesSinistra = framesSinistra;
	}

	public Array<TextureRegion> getFramesAlto() {
		return framesAlto;
	}

	public void setFramesAlto(Array<TextureRegion> framesAlto) {
		this.framesAlto = framesAlto;
	}

	public Array<TextureRegion> getFramesBasso() {
		return framesBasso;
	}

	public void setFramesBasso(Array<TextureRegion> framesBasso) {
		this.framesBasso = framesBasso;
	}

	public Array<TextureRegion> getFramesMorte() {
		return framesMorte;
	}

	public void setFramesMorte(Array<TextureRegion> framesMorte) {
		this.framesMorte = framesMorte;
	}

	public TextureRegion getRightStand() {
		return rightStand;
	}

	public void setRightStand(TextureRegion rightStand) {
		this.rightStand = rightStand;
	}

	public TextureRegion getStop() {
		return stop;
	}

	public void setStop(TextureRegion stop) {
		this.stop = stop;
	}

	public TextureRegion getDownStand() {
		return downStand;
	}

	public void setDownStand(TextureRegion downStand) {
		this.downStand = downStand;
	}

	public TextureRegion getLeftStand() {
		return leftStand;
	}

	public void setLeftStand(TextureRegion leftStand) {
		this.leftStand = leftStand;
	}

	public TextureRegion getUpStand() {
		return upStand;
	}

	public void setUpStand(TextureRegion upStand) {
		this.upStand = upStand;
	}

	public Array<State> getStati() {
		return stati;
	}

	public void setStati(Array<State> stati) {
		this.stati = stati;
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	public State getPreviuosState() {
		return previuosState;
	}

	public void setPreviuosState(State previuosState) {
		this.previuosState = previuosState;
	}

	public Fixture getFixture() {
		return fixture;
	}

	public void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}

	public FixtureDef getFdef() {
		return fdef;
	}

	public void setFdef(FixtureDef fdef) {
		this.fdef = fdef;
	}

	public ArrayList<Coordinate> getPosizioni() {
		return posizioni;
	}

	public void setPosizioni(ArrayList<Coordinate> posizioni) {
		this.posizioni = posizioni;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Body getB2body() {
		return b2body;
	}

	public void setB2body(Body b2body) {
		this.b2body = b2body;
	}

	public float getStateTimer() {
		return stateTimer;
	}

	public void setStateTimer(float stateTimer) {
		this.stateTimer = stateTimer;
	}

	public float getTempoDisegno() {
		return tempoDisegno;
	}

	public void setTempoDisegno(float tempoDisegno) {
		this.tempoDisegno = tempoDisegno;
	}

	public float getPosizioneX() {
		return posizioneX;
	}

	public void setPosizioneX(float posizioneX) {
		this.posizioneX = posizioneX;
	}

	public float getPosizioneY() {
		return posizioneY;
	}

	public void setPosizioneY(float posizioneY) {
		this.posizioneY = posizioneY;
	}

	public BodyDef getBdef() {
		return bdef;
	}

	public void setBdef(BodyDef bdef) {
		this.bdef = bdef;
	}

	public boolean isMorto() {
		return morto;
	}

	public void setMorto(boolean morto) {
		this.morto = morto;
	}

	public float getTempotempo() {
		return tempotempo;
	}

	public void setTempotempo(float tempotempo) {
		this.tempotempo = tempotempo;
	}

	public TextureAtlas getAtlasDestra() {
		return atlasDestra;
	}

	public void setAtlasDestra(TextureAtlas atlasDestra) {
		this.atlasDestra = atlasDestra;
	}

	public TextureAtlas getAtlasBasso() {
		return atlasBasso;
	}

	public void setAtlasBasso(TextureAtlas atlasBasso) {
		this.atlasBasso = atlasBasso;
	}

	public TextureAtlas getAtlasAlto() {
		return atlasAlto;
	}

	public void setAtlasAlto(TextureAtlas atlasAlto) {
		this.atlasAlto = atlasAlto;
	}

	public static float getPpm() {
		return PPM;
	}
	
	
}
