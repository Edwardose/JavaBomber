package it.uniroma1.metodologie2018.javabomber.screens;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ClassiMadre.PowerUp;
import ClassiMadre.SfornaNemici;
import Scenes.Hud;
import Tools.WorldContactListener;
import it.uniroma1.metodologie2018.javabomber.JavaBomber;
import it.uniroma1.metodologie2018.javabomber.entities.Blocco;
import it.uniroma1.metodologie2018.javabomber.entities.Bomb;
import it.uniroma1.metodologie2018.javabomber.entities.BomberMan;
import it.uniroma1.metodologie2018.javabomber.entities.Boss;
import it.uniroma1.metodologie2018.javabomber.entities.Coordinate;
import it.uniroma1.metodologie2018.javabomber.entities.Enemy;
import it.uniroma1.metodologie2018.javabomber.entities.EnemyOriginale;
import it.uniroma1.metodologie2018.javabomber.entities.PowerUpAumentaRaggioBomba;
import it.uniroma1.metodologie2018.javabomber.entities.PowerUpAumentaTempo;
import it.uniroma1.metodologie2018.javabomber.entities.PowerUpAumentaVelocit‡;
import it.uniroma1.metodologie2018.javabomber.entities.PowerUpBomba;
import it.uniroma1.metodologie2018.javabomber.entities.PowerUpDiminuisciRaggioBomba;
import it.uniroma1.metodologie2018.javabomber.entities.PowerUpDiminuisciTempo;
import it.uniroma1.metodologie2018.javabomber.entities.PowerUpDiminuisciVelocit‡;
import it.uniroma1.metodologie2018.javabomber.entities.PowerUpOltrePassaBombe;
import it.uniroma1.metodologie2018.javabomber.entities.PowerUpSpintaBomba;
import it.uniroma1.metodologie2018.javabomber.entities.PowerUpVita;
import it.uniroma1.metodologie2018.javabomber.entities.Proiettile;
import it.uniroma1.metodologie2018.javabomber.entities.BomberMan.State;
import it.uniroma1.metodologie2018.javabomber.world.B2WorldCreator;
public class PlayScreen implements Screen{
	
	//creazione mondo
	private B2WorldCreator creazioneMondo;
	
	//variabili booleane per la vita
	private boolean aumentaVita=false;
	private boolean diminuisciVita =false;
	
	//cambio particolarit‡ personaggio
	private boolean changeBody=false;
	
	//Gioco
	private JavaBomber game;
	
	//Coordinate(float,float)
	private Coordinate coordinates = new Coordinate(0,0);
	
	//velocit‡ personaggio all'interno del gioco
	private float velocit‡Personaggio=2.0f;
	
	//power_up
	private Array<Coordinate> coordinatePowerUp = new Array<Coordinate>();
	private Array<PowerUp> powerUps = new Array<PowerUp>();
	private PowerUp bonus;
	private float raggioBombaLarghezza=16;
	private float raggioBombaLunghezza=48;
	private boolean bonusOltrePassaBombe=false;
	private boolean bonusSpintaBomba=false;
	private int moltiplicatore=0;

	
	
	//respawn
	private float tempoImbattibilit‡=0.0f;
	private float tempoReSpawn=0.0f;
	
	//nemico
	private Array<SfornaNemici> enemies = new Array<SfornaNemici>();
	public Array<Coordinate> coordinateMorteNemico = new Array<Coordinate>();
	
	//morte
	private float coordinataMorteX;
	private float coordinataMorteY;
	private float tempoMorte=0.0f;
	private boolean rinato = false;
	
	//variabile per i contatti
	private WorldContactListener contatto = new WorldContactListener();
	
	//Box per le collissione delle bombe
	private Shape shape ;
	private Shape shape2 ;
	
	//bomba
	private ArrayList<Body> bodyDaDistruggere = new ArrayList<>();
	private int numeroBombe = 1;
	private float tempoPiazzaBomba=0.0f;
	private boolean bombaPiazzata=false;
	
	//Posizioni del Boss
	private float posizioneBossX=0;
	private float posizioneBossY=0;
	
	//booleano per creare proiettili
	private boolean creaProiettile=false;
	
	//frames delle bombe
	private Array<TextureRegion> framesBomb = new Array<TextureRegion>();
	private Array<TextureRegion> framesBombSopra = new Array<TextureRegion>();
	private Array<TextureRegion> framesBombSotto = new Array<TextureRegion>();
	private Array<TextureRegion> framesBombDestra = new Array<TextureRegion>();
	private Array<TextureRegion> framesBombSinistra = new Array<TextureRegion>();
	private Array<TextureRegion> framesMorte = new Array<TextureRegion>();
	
	//animazione della bomba
	private Animation<TextureRegion> bombAnimation;
	private Animation<TextureRegion> bombAnimationSopra;
	private Animation<TextureRegion> bombAnimationSotto;
	private Animation<TextureRegion> bombAnimationDestra;
	private Animation<TextureRegion> bombAnimationSinistra;
	private Animation<TextureRegion> bombAnimationMorte;
	private float animazioneLancioBomba=0.0f;
	
	//sprite della bomba
	private Array<Sprite> spriteBomb;
	private Array<Sprite> spriteBombaSopra;
	private Array<Sprite> spriteBombaSotto;
	private Array<Sprite> spriteBombaDestra;
	private Array<Sprite> spriteBombaSinistra;
	private Array<Sprite> spriteMorte;
	
	//posizioni delle bombe
	private ArrayList<Coordinate> posizioniBombe = new ArrayList<Coordinate>() ;
	private LinkedHashMap<Coordinate,Body> bodies= new LinkedHashMap<>();
	private boolean aumentaBomba=false;
	private boolean diminuisciBomba=false;
	
	//variabili per i bonus
	private float lancioBombaDistanza = 0.0f;
	
	//TextureAtlas della bomba
	private TextureAtlas atlasUp;
	private TextureAtlas atlasDown;
	private TextureAtlas atlasDestraSinistra;
	private TextureAtlas atlasBomba;
	private TextureAtlas atlasBombaSopra;
	private TextureAtlas atlasBombaSotto;
	private TextureAtlas atlasBombaDestra;
	private TextureAtlas atlasBombaSinistra;
	private TextureAtlas atlasMorte;
	
	//camera
	private OrthographicCamera gamecam;
	private Viewport gamePort;
	
	//legenda vita,bombe,tempo
	private Hud hud;
	
	//batch per disegnare
	private SpriteBatch batch = new SpriteBatch();
	
	//variabili tiled map
	private TmxMapLoader maploader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	//Box2D variabili
	private World world;
	private Box2DDebugRenderer b2dr;
	private BomberMan player;
	
	//Fixture(equipaggiamento o particolarit‡ delle entit‡
	private FixtureDef fdef = new FixtureDef();
	private FixtureDef fdef3 = new FixtureDef();
	
	//TextureAtlas dei powerUp
	private TextureAtlas atlasPowerUp;
	private TextureRegion disegnoPowerUp;
	
	/**
	 * 
	 * @param game
	 */
	public PlayScreen(JavaBomber game) {
		
		//caricano dai file .pack le texture necessarie
		atlasDestraSinistra = new TextureAtlas("MOVE_RIGHT.pack");
		atlasUp = new TextureAtlas("MOVE_UP.pack");
		atlasDown = new TextureAtlas("MOVE_DOWN.pack");
		atlasBomba = new TextureAtlas("BOMBA.pack");
		atlasBombaSopra = new TextureAtlas("BOMBA_ALTO.pack");
		atlasBombaSotto = new TextureAtlas("BOMBA_BASSO.pack");
		atlasBombaDestra = new TextureAtlas("BOMBA_DESTRA.pack");
		atlasBombaSinistra = new TextureAtlas("BOMBA_SINISTRA.pack");
		atlasMorte = new TextureAtlas("MORTE.pack");
		atlasPowerUp = new TextureAtlas("BONUS.pack");
		
		//vettori contenenti diversi Sprite
		spriteBomb=new Array<Sprite>();
		spriteBombaSopra = new Array<Sprite>();
		spriteBombaSotto = new Array<Sprite>();
		spriteBombaDestra = new Array<Sprite>();
		spriteBombaSinistra = new Array<Sprite>();
		spriteMorte = new Array<Sprite>();
		
		//riempimento vettori con Sprite in ordine logico
		animazioneBomba();
		animazioneBombaSopra();
		animazioneBombaSotto();
		animazioneBombaDestra();
		animazioneBombaSinistra();
		animazioneMorte();
		
		//riempimento dei frames dai vettori di Sprite
		framesBomb = riempiFrames(spriteBomb, framesBomb);
		framesBombSopra = riempiFrames(spriteBombaSopra,framesBombSopra);
		framesBombSotto = riempiFrames(spriteBombaSotto, framesBombSotto);
		framesBombDestra = riempiFrames(spriteBombaDestra, framesBombDestra);
		framesBombSinistra = riempiFrames(spriteBombaSinistra, framesBombSinistra);
		framesMorte = riempiFrames(spriteMorte, framesMorte);
		
		//animazioni da frames
		bombAnimation = new Animation<TextureRegion>(0.8f,framesBomb);
		bombAnimationSopra = new Animation<TextureRegion>(0.8f,framesBombSopra);
		bombAnimationSotto = new Animation<TextureRegion>(0.8f, framesBombSotto);
		bombAnimationDestra = new Animation<TextureRegion>(0.8f, framesBombDestra);
		bombAnimationSinistra = new Animation<TextureRegion>(0.8f, framesBombSinistra);
		bombAnimationMorte = new Animation<TextureRegion>(0.8f,framesMorte);
		
		//variabile per la ricerca degli Sprite
		disegnoPowerUp = atlasPowerUp.createSprite("power_up (13)");
		
		this.game = game;
		
		//vista del giocatore
		gamecam = new OrthographicCamera();
		
		//adattamento finestra
		gamePort = new FitViewport(JavaBomber.V_WIDTH/BomberMan.PPM,JavaBomber.V_HEIGHT/BomberMan.PPM,gamecam);
		
		hud = new Hud(game.batch);
		
		//caricamento della mappa da file
		maploader = new TmxMapLoader();
		map = maploader.load("Map_BomberMan2.xml");
		renderer = new OrthogonalTiledMapRenderer(map,1/BomberMan.PPM);
		gamecam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);
		
		world = new World(new Vector2(0,0),true);//gravit‡
		/**
		 * levare il commento per verificare box2d
		 * b2dr = new Box2DDebugRenderer();
		 */
		
		//creazione mondo da tiledMap
		creazioneMondo = new B2WorldCreator(world, map);
		
		//creazione personaggio principale
		player = new BomberMan(world,this);
		
		//creazione nemici e nemici originali
		enemies.add(new Enemy(world,this,300,300));
		enemies.add(new Enemy(world,this,300,100));
		enemies.add(new Enemy(world,this,100,300));
		enemies.add(new EnemyOriginale(world, this, 150, 240));
		
		//impostazione contatti
		world.setContactListener(contatto);
		
		//pulizia dei frames
		framesBomb.clear();
		framesBombDestra.clear();
		framesBombSinistra.clear();
		framesBombSopra.clear();
		framesBombSotto.clear();
		framesMorte.clear();
		
		
		shape = new PolygonShape();
		shape2 = new PolygonShape();
		
		//settaggi per il raggio dell'esplosione della bomba
		((PolygonShape) shape).setAsBox(raggioBombaLunghezza/BomberMan.PPM,raggioBombaLarghezza/BomberMan.PPM);
		((PolygonShape) shape2).setAsBox(raggioBombaLarghezza/BomberMan.PPM,raggioBombaLunghezza/BomberMan.PPM);
		
	}
	
	/**
	 * Riempimento sprite di immagini presenti nel file .pack
	 */
	public void animazioneBomba() {
		spriteBomb.add(new Sprite(atlasBomba.findRegion("bomba1")));
		spriteBomb.add(new Sprite(atlasBomba.findRegion("bomba2")));
		spriteBomb.add(new Sprite(atlasBomba.findRegion("bomba1")));
		spriteBomb.add(new Sprite(atlasBomba.findRegion("bomba3")));
		spriteBomb.add(new Sprite(atlasBomba.findRegion("bomba4")));
		spriteBomb.add(new Sprite(atlasBomba.findRegion("bomba1")));
		spriteBomb.add(new Sprite(atlasBomba.findRegion("bomba5")));
		spriteBomb.add(new Sprite(atlasBomba.findRegion("bomba6")));
		spriteBomb.add(new Sprite(atlasBomba.findRegion("bomba7")));
		spriteBomb.add(new Sprite(atlasBomba.findRegion("bomba8")));
		spriteBomb.add(new Sprite(atlasBomba.findRegion("bomba9")));
		spriteBomb.add(new Sprite(atlasBomba.findRegion("bomba11")));
		spriteBomb.add(new Sprite(atlasBomba.findRegion("bomba12")));
	}
	
	/**
	 * Riempimento sprite di immagini presenti nel file .pack
	 */
	public void animazioneBombaSopra() {
		spriteBombaSopra.add(new Sprite(atlasBombaSopra.findRegion("bomba alto (2)")));
		spriteBombaSopra.add(new Sprite(atlasBombaSopra.findRegion("bomba alto (3)")));
		spriteBombaSopra.add(new Sprite(atlasBombaSopra.findRegion("bomba alto (4)")));
		spriteBombaSopra.add(new Sprite(atlasBombaSopra.findRegion("bomba alto (5)")));
		spriteBombaSopra.add(new Sprite(atlasBombaSopra.findRegion("bomba alto (1)")));
	}
	
	/**
	 * Riempimento sprite di immagini presenti nel file .pack
	 */
	public void animazioneBombaSotto() {
		spriteBombaSotto.add(new Sprite(atlasBombaSotto.findRegion("bomba sotto (4)")));
		spriteBombaSotto.add(new Sprite(atlasBombaSotto.findRegion("bomba sotto (5)")));
		spriteBombaSotto.add(new Sprite(atlasBombaSotto.findRegion("bomba sotto (1)")));
		spriteBombaSotto.add(new Sprite(atlasBombaSotto.findRegion("bomba sotto (2)")));
		spriteBombaSotto.add(new Sprite(atlasBombaSotto.findRegion("bomba sotto (3)")));

	}
	
	/**
	 * Riempimento sprite di immagini presenti nel file .pack
	 */
	public void animazioneBombaDestra() {
		spriteBombaDestra.add(new Sprite(atlasBombaDestra.findRegion("bomba destra (5)")));
		spriteBombaDestra.add(new Sprite(atlasBombaDestra.findRegion("bomba destra (1)")));
		spriteBombaDestra.add(new Sprite(atlasBombaDestra.findRegion("bomba destra (2)")));
		spriteBombaDestra.add(new Sprite(atlasBombaDestra.findRegion("bomba destra (3)")));
		spriteBombaDestra.add(new Sprite(atlasBombaDestra.findRegion("bomba destra (4)")));
	}
	
	/**
	 * Riempimento sprite di immagini presenti nel file .pack
	 */
	public void animazioneBombaSinistra() {
		spriteBombaSinistra.add(new Sprite(atlasBombaSinistra.findRegion("bomba sinistra (1)")));
		spriteBombaSinistra.add(new Sprite(atlasBombaSinistra.findRegion("bomba sinistra (2)")));
		spriteBombaSinistra.add(new Sprite(atlasBombaSinistra.findRegion("bomba sinistra (3)")));
		spriteBombaSinistra.add(new Sprite(atlasBombaSinistra.findRegion("bomba sinistra (4)")));
		spriteBombaSinistra.add(new Sprite(atlasBombaSinistra.findRegion("bomba sinistra (5)")));
	}
	
	/**
	 * Riempimento sprite di immagini presenti nel file .pack
	 */
	public void animazioneMorte() {
		spriteMorte.add(new Sprite(atlasMorte.findRegion("morte (2)")));
		spriteMorte.add(new Sprite(atlasMorte.findRegion("morte (3)")));
		spriteMorte.add(new Sprite(atlasMorte.findRegion("morte (4)")));
		spriteMorte.add(new Sprite(atlasMorte.findRegion("morte (1)")));
	}
	
	/**
	 * getter per tipo di atlas
	 * @return
	 */
	public TextureAtlas getAtlasUp() {
		return atlasUp;
	}
	
	/**
	 * getter per tipo di atlas
	 * @return
	 */
	public TextureAtlas getAtlasDown() {
		return atlasDown;
	}
	
	/**
	 * getter per tipo di atlas
	 * @return
	 */
	public TextureAtlas getAtlasDestraSinistra() {
		return atlasDestraSinistra;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * gestione input da tastiera
	 * @param dt
	 */
	public void handleInput(float dt) {
		if(!player.morto && player !=null) {
		player.b2body.setLinearVelocity(0f, 0f);		//settaggi velocit‡ personaggio
		if(Gdx.input.isKeyPressed(Input.Keys.UP) && player.b2body.getLinearVelocity().y<=1) {	//gestione freccetta su
			player.b2body.applyLinearImpulse(new Vector2(0,velocit‡Personaggio)	,player.b2body.getWorldCenter() , true);
			}
		
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x<=1) //gestione freccetta destra 
			player.b2body.applyLinearImpulse(new Vector2(velocit‡Personaggio,0), player.b2body.getWorldCenter(), true);
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x>=-1) //gestione freccetta sinistra
			player.b2body.applyLinearImpulse(new Vector2(-velocit‡Personaggio,0), player.b2body.getWorldCenter(), true);
		
		
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && player.b2body.getLinearVelocity().y>=-1) //gestione freccetta giu
			player.b2body.applyLinearImpulse(new Vector2(0,-velocit‡Personaggio), player.b2body.getWorldCenter(), true);
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.Y)) {   //ATTENZIONE a premerlo, spawn del boss
			if(controllaBoss(enemies)) {
				enemies.add(new Boss(world,this,200,425));
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {	//con spazio piazzi la bomba
			
			if(!player.morto && player!=null) {
			Coordinate c = new Coordinate(player.b2body.getPosition().x, player.b2body.getPosition().y,0.0f,0.0f);	//coordinate esatte del momento della pressione del tasto spazio
			
			if(posizioniBombe.size() <numeroBombe) {	//gestione numero bombe, si possono piazzare solo tot bombe in un arco di tempo
					diminuisciBomba=true;
					posizioniBombe.add(c);
					
					Bomb b = new Bomb(world,c.x,c.y);
					
					
					bodies.put(c, b.body);   //Mappa<Coordinate,Body> per piazzare bombe nelle posizioni giuste
					}
				}
			
			}
		}
	}
	
	/**
	 * metodo principale (loop del gioco)
	 * @param dt
	 */
	public void update(float dt) {
		hud.modifiche(aumentaVita, diminuisciVita);	//modifiche legenda vita, n∞ bombe ecc
		aumentaVita=false;	
		diminuisciBomba=false;
		
		if(bombaPiazzata) {	//se la bomba Ë piazzata parte un timer per farla scoppiare
			tempoPiazzaBomba+=dt;
		}
		else tempoPiazzaBomba=0.0f;
		
		coordinates.x = player.b2body.getPosition().x/BomberMan.PPM;	//salvataggio delle posizioni del giocatore
		coordinates.y = player.b2body.getPosition().y/BomberMan.PPM;	//salvataggio delle posizioni del giocatore
		
		if(bonusOltrePassaBombe) {
			bodyDaDistruggere.add(player.b2body);	//cambio di body con un altro con altre particolarit‡ (ghostMode)
			player.ghostMode(player.b2body.getPosition().x,player.b2body.getPosition().y);
			bonusOltrePassaBombe=false;
		}
		
		if(player.morto) {
			player.b2body.setActive(false);		//immortalit‡ 
		}
		
		if(rinato) {
			if(!changeBody) {
				world.destroyBody(player.b2body);
				player.changeFixture(coordinates.x/BomberMan.PPM,coordinates.y/BomberMan.PPM ); //cambio di body da immortalit‡
				changeBody=true;
			}
			
		}

		
		
		if(!player.morto && player!=null) {
			player.b2body.setActive(true);				//gestione player in vita
			coordinataMorteX=player.b2body.getPosition().x;
			coordinataMorteY=player.b2body.getPosition().y;
		}
		handleInput(dt);	//gestione input da tastiera
		
		
		
		if(tempoReSpawn>5.0f) {	
			hud.update(dt, false, false, false, true);
			rinato = true;
			tempoReSpawn=0.0f;
			player.morto=false;
			player.b2body.setTransform(40/BomberMan.PPM,40/BomberMan.PPM, player.b2body.getAngle());	//riposizionamento player allo start
			tempoMorte=0.0f;
			
			
		}
		
		if(rinato) {	//per tot secondi appena si rinasce si Ë immortali
			tempoImbattibilit‡+=0.1f;
			
			
			
			
		}
		
		if(tempoImbattibilit‡>5.0f) {	//gestione del cambio di body
			bodyDaDistruggere.add(player.b2body);
			player.normal(player.b2body.getPosition().x,player.b2body.getPosition().y);
			changeBody=false;
			player.imbattibile = false;
			rinato=false;
			tempoImbattibilit‡=0.0f;
			
			
			
			
		}
		
		world.step(1/60f, 6, 2);	//tempo del gioco 
		
		if(player.morto) {	
			tempoReSpawn+=0.1f;		//countdown per respawn
			
			
			
		}
		
		Iterator<Blocco> iii = creazioneMondo.blocchi.iterator();
		
		/**
		 * gestione blocchi quando vengono distrutti
		 */
		while(iii.hasNext()) {
			Blocco b = iii.next();
			if(b.rimuovi) {
				
				Coordinate c = new Coordinate(b.posizioneX*BomberMan.PPM,b.posizioneY*BomberMan.PPM);
				coordinatePowerUp.add(c);
				bodyDaDistruggere.add(b.effe);
				iii.remove();
			}
		}
		
		/**
		 * gestione nemici quando vengono eliminati dalla bomba
		 */
		Iterator<SfornaNemici> ii = enemies.iterator();
		while(ii.hasNext()) {
			SfornaNemici e =  ii.next();
			if(e instanceof Enemy) {
				if(((Enemy)e).isMorto() && ((Enemy)e).getTempoDellaMorte()>1.6f) {
					bodyDaDistruggere.add(((Enemy)e).getB2body());
					ii.remove();
				}
				else if(((Enemy)e).isMorto()) {
					((Enemy)e).getB2body().setActive(false);
				}
			}
			else if(e instanceof EnemyOriginale) {
				if(((EnemyOriginale)e).isMorto() && ((EnemyOriginale)e).getTempoDellaMorte()>1.6f) {
					bodyDaDistruggere.add(((EnemyOriginale)e).getB2body());
					ii.remove();
				}
				else if(((EnemyOriginale)e).isMorto()) {
					((EnemyOriginale)e).getB2body().setActive(false);
				}
			}
			else if(e instanceof Boss) {
				if(((Boss)e).isMorto() && ((Boss)e).getTempoDellaMorte()>1.6f) {
					bodyDaDistruggere.add(((Boss)e).getB2body());
					ii.remove();
				}
				else if(((Boss)e).isMorto()) {
					((Boss)e).getB2body().setActive(false);
				}
			}
			else if(e instanceof Proiettile) {
				if(((Proiettile)e).isMorto()) {
					bodyDaDistruggere.add(((Proiettile)e).getB2body());
					ii.remove();
				}
			}
		}
		
		/**
		 * gestione powerUp, a seconda dei powerUp cambiano i valori dei corrispondenti campi
		 */
		Iterator<PowerUp> objectPowerUp = powerUps.iterator();
		while(objectPowerUp.hasNext()) {
			PowerUp p = objectPowerUp.next();
			if(p instanceof PowerUpBomba) {
				
				if(((PowerUpBomba)p).attiva) {
					numeroBombe++;
					aumentaBomba = true;
					bodyDaDistruggere.add(p.getBody());
					objectPowerUp.remove();
				}
			}
			else if(p instanceof PowerUpVita) {
				
				if(((PowerUpVita)p).attiva) {
					aumentaVita=true;
					bodyDaDistruggere.add(p.getBody());
					objectPowerUp.remove();
				}
			}
			else if(p instanceof PowerUpSpintaBomba) {
				
				if(((PowerUpSpintaBomba)p).attiva) {
					bonusSpintaBomba=true;
					lancioBombaDistanza+=4f;
					animazioneLancioBomba+=0.066f;
					bodyDaDistruggere.add(p.getBody());
					objectPowerUp.remove();
				}
			}
			else if(p instanceof PowerUpOltrePassaBombe) {
				
				if(((PowerUpOltrePassaBombe)p).attiva) {
					bonusOltrePassaBombe=true;
					bodyDaDistruggere.add(p.getBody());
					objectPowerUp.remove();
				}
			}
			else if(p instanceof PowerUpDiminuisciTempo) {
				
				if(((PowerUpDiminuisciTempo)p).attiva) {
					hud.diminuisciTempo();
					bodyDaDistruggere.add(p.getBody());
					objectPowerUp.remove();
				}
			}
			else if(p instanceof PowerUpDiminuisciRaggioBomba) {
				
				if(((PowerUpDiminuisciRaggioBomba)p).attiva) {
					raggioBombaLunghezza-=20;
					bodyDaDistruggere.add(p.getBody());
					objectPowerUp.remove();
				}
			}
			else if(p instanceof PowerUpDiminuisciVelocit‡) {
				
				if(((PowerUpDiminuisciVelocit‡)p).attiva) {
					velocit‡Personaggio-=0.5f;
					bodyDaDistruggere.add(p.getBody());
					objectPowerUp.remove();
				}
			}
			else if(p instanceof PowerUpAumentaVelocit‡) {
				
				if(((PowerUpAumentaVelocit‡)p).attiva) {
					moltiplicatore+=1;
					velocit‡Personaggio+=0.5f*moltiplicatore;
					
					bodyDaDistruggere.add(p.getBody());
					objectPowerUp.remove();
				}
			}
			else if(p instanceof PowerUpAumentaTempo) {
				
				if(((PowerUpAumentaTempo)p).attiva) {
					hud.aumentaTempo();
					bodyDaDistruggere.add(p.getBody());
					objectPowerUp.remove();
				}
			}
			else if(p instanceof PowerUpAumentaRaggioBomba) {
				
				if(((PowerUpAumentaRaggioBomba)p).attiva) {
					raggioBombaLunghezza+=30;
					bodyDaDistruggere.add(p.getBody());
					objectPowerUp.remove();
				}
			}
			
			
		}
		
		/**
		 * body da eliminare dal world
		 */
		Iterator<Body> i = bodyDaDistruggere.iterator();
		while(i.hasNext()) {
			Body b = i.next();
			
			world.destroyBody(b);
			
			i.remove();
			if(hud.bombe<numeroBombe ) {
				aumentaBomba=true;
			
			}
		}
		
		/**
		 * dopo l'esplosione di un blocco , randomicamente appare un bonus
		 */
		Iterator<Coordinate> coord = coordinatePowerUp.iterator();
		while(coord.hasNext()) {
			Coordinate c = coord.next();
			Random r = new Random();
			int casual;
			casual=r.nextInt(10);
			switch(casual) {
			case(0):
				bonus = new PowerUpBomba(world, this, c.x, c.y);
				powerUps.add(bonus);
				world.createBody(bonus.getBodyDef());
			break;
			case(1):
				bonus = new PowerUpVita(world, this, c.x, c.y);
				powerUps.add(bonus);
				world.createBody(bonus.getBodyDef());
			break;
			case(2):
				bonus = new PowerUpAumentaRaggioBomba(world, this, c.x, c.y);
				powerUps.add(bonus);
				world.createBody(bonus.getBodyDef());
			break;
			case(3):
				bonus = new PowerUpAumentaTempo(world, this, c.x, c.y);
				powerUps.add(bonus);
				world.createBody(bonus.getBodyDef());
			break;
			case(4):
				bonus = new PowerUpAumentaVelocit‡(world, this, c.x, c.y);
				powerUps.add(bonus);
				world.createBody(bonus.getBodyDef());
			break;
			case(5):
				bonus = new PowerUpDiminuisciRaggioBomba(world, this, c.x, c.y);
				powerUps.add(bonus);
				world.createBody(bonus.getBodyDef());
			break;
			case(6):
				bonus = new PowerUpDiminuisciTempo(world, this, c.x, c.y);
				powerUps.add(bonus);
				world.createBody(bonus.getBodyDef());
			break;
			case(7):
				bonus = new PowerUpDiminuisciVelocit‡(world, this, c.x, c.y);
				powerUps.add(bonus);
				world.createBody(bonus.getBodyDef());
			break;
			case(8):
				bonus = new PowerUpOltrePassaBombe(world, this, c.x, c.y);
				powerUps.add(bonus);
				world.createBody(bonus.getBodyDef());
			break;
			case(9):
				bonus = new PowerUpSpintaBomba(world, this, c.x, c.y);
				powerUps.add(bonus);
				world.createBody(bonus.getBodyDef());
			break;
			}
			coord.remove();
		}
		

		if(!player.morto) {
		gamecam.position.x = player.b2body.getPosition().x;
		gamecam.position.y = player.b2body.getPosition().y;
		
		player.update(dt);		//richiamo metodo principale del personaggio
		
		
		
		hud.update(dt, aumentaBomba, diminuisciBomba,false,false);		//variabili per modifiche legenda
		diminuisciBomba=false;
		aumentaBomba=false;
		}
		else if(player.morto) {
			hud.update(dt, false, false,true,false);		//reset legenda , causa morte personaggio
			diminuisciBomba=false;
			aumentaBomba=false;
		}

		if(!player.morto) {
			if(controllaProiettile(enemies)) {		//controllo se nell'array dei nemici Ë presente Proiettili
				if(creaProiettile) {		//se non Ë presente creaProiettile = true
					enemies.add(new Proiettile(world,this,posizioneBossX*BomberMan.PPM-40,posizioneBossY*BomberMan.PPM-42));		//creazione proiettile dal boss
					creaProiettile=false;
				}
				
			}
			/**
			 * settaggi velocit‡ e richiami metodi principali dei nemici (update)
			 */
			for(SfornaNemici e : enemies) {
				if(e instanceof Enemy) {
					((Enemy) e).getB2body().setLinearVelocity(0f,0f);
					((Enemy) e).update(dt);
					((Enemy) e).muovi(((Enemy) e).getCurrentState(),dt);
				}
				else if(e instanceof EnemyOriginale) {
					((EnemyOriginale) e).getB2body().setLinearVelocity(0f,0f);
					((EnemyOriginale) e).update(dt);
					((EnemyOriginale) e).muovi(((EnemyOriginale) e).getCurrentState(),dt);		//movimento nemico
				}
				else if(e instanceof Boss) {
					((Boss) e).getB2body().setLinearVelocity(0f,0f);
					((Boss) e).update(dt);
					((Boss) e).muovi(((Boss) e).getCurrentState(),dt);		//movimento nemico
					posizioneBossX=((Boss)e).getPosizioneX();
					posizioneBossY=((Boss)e).getPosizioneY();
					creaProiettile=true;
				}
				else if(e instanceof Proiettile) {
					((Proiettile) e).getB2body().setLinearVelocity(0f,0f);
					((Proiettile) e).update(dt);
					((Proiettile) e).muovi(((Proiettile) e).getCurrentState(),dt); 	//movimento nemico
				}
			}
		}
		else
		{	
			/**
			 * movimenti dei nemici inverso quando muore personaggio
			 */
			for(SfornaNemici e : enemies) {
				if(e instanceof Enemy) {
					((Enemy)e).getB2body().setLinearVelocity(0f,0f);
					((Enemy)e).update(dt);
				
					((Enemy)e).muoviIndietro(((Enemy)e).getCurrentState(),dt);
				}
				else if(e instanceof EnemyOriginale) {
					((EnemyOriginale)e).getB2body().setLinearVelocity(0f,0f);
					((EnemyOriginale)e).update(dt);
				
					((EnemyOriginale)e).muoviIndietro(((EnemyOriginale)e).getCurrentState(),dt);
				}
				else if(e instanceof Boss) {
					((Boss)e).getB2body().setLinearVelocity(0f,0f);
					((Boss)e).update(dt);

				}
			}
		}
		
		//richiamo metodo principale della camera
		gamecam.update();
		
		//renderizza la mappa
		renderer.setView(gamecam);
		
	}
	
	/**
	 * disegni delle entit‡
	 */
	@Override
	public void render(float delta) {
		update(delta);
		
		/**
		 * colore sfondo
		 */
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//da cancellare in futuro
		renderer.render();

		/**
		 * levare il commento per verificare le collisioni con i box2d
		 * b2dr.render(world, gamecam.combined);
		 */

		//assegnare il lavoro del batch alla gpu
		batch.setProjectionMatrix(gamecam.combined);
		
		/**
		 * disegni dei powerUp
		 */
		for(PowerUp p : powerUps) {
			if(p instanceof PowerUpBomba) {
				
				if(!((PowerUpBomba)p).attiva) {
					disegnoPowerUp = atlasPowerUp.createSprite("power_up (13)");
					batch.begin();
					batch.draw(disegnoPowerUp, ((PowerUpBomba) p).posizionamentoX/BomberMan.PPM-(float)0.16, ((PowerUpBomba) p).posizionamentoY/BomberMan.PPM-(float)0.16, 32/BomberMan.PPM,32/BomberMan.PPM);
					batch.end();
				}
		
			}
			else if(p instanceof PowerUpVita) {
				if(!((PowerUpVita)p).attiva) {
					disegnoPowerUp = atlasPowerUp.createSprite("power_up (2)");
					batch.begin();
					batch.draw(disegnoPowerUp, ((PowerUpVita) p).posizionamentoX/BomberMan.PPM-(float)0.16, ((PowerUpVita) p).posizionamentoY/BomberMan.PPM-(float)0.16, 32/BomberMan.PPM,32/BomberMan.PPM);
					batch.end();
				}
			}
			else if(p instanceof PowerUpDiminuisciVelocit‡) {
				if(!((PowerUpDiminuisciVelocit‡)p).attiva) {
					disegnoPowerUp = atlasPowerUp.createSprite("power_up (4)");
					batch.begin();
					batch.draw(disegnoPowerUp, ((PowerUpDiminuisciVelocit‡) p).posizionamentoX/BomberMan.PPM-(float)0.16, ((PowerUpDiminuisciVelocit‡) p).posizionamentoY/BomberMan.PPM-(float)0.16, 32/BomberMan.PPM,32/BomberMan.PPM);
					batch.end();
				}
			}
			else if(p instanceof PowerUpOltrePassaBombe) {
				if(!((PowerUpOltrePassaBombe)p).attiva) {
					disegnoPowerUp = atlasPowerUp.createSprite("power_up (19)");
					batch.begin();
					batch.draw(disegnoPowerUp, ((PowerUpOltrePassaBombe) p).posizionamentoX/BomberMan.PPM-(float)0.16, ((PowerUpOltrePassaBombe) p).posizionamentoY/BomberMan.PPM-(float)0.16, 32/BomberMan.PPM,32/BomberMan.PPM);
					batch.end();
				}
			}
			else if(p instanceof PowerUpDiminuisciRaggioBomba) {
				if(!((PowerUpDiminuisciRaggioBomba)p).attiva) {
					disegnoPowerUp = atlasPowerUp.createSprite("power_up (9)");
					batch.begin();
					batch.draw(disegnoPowerUp, ((PowerUpDiminuisciRaggioBomba) p).posizionamentoX/BomberMan.PPM-(float)0.16, ((PowerUpDiminuisciRaggioBomba) p).posizionamentoY/BomberMan.PPM-(float)0.16, 32/BomberMan.PPM,32/BomberMan.PPM);
					batch.end();
				}
			}
			else if(p instanceof PowerUpDiminuisciTempo) {
				if(!((PowerUpDiminuisciTempo)p).attiva) {
					disegnoPowerUp = atlasPowerUp.createSprite("power_up (1)");
					batch.begin();
					batch.draw(disegnoPowerUp, ((PowerUpDiminuisciTempo) p).posizionamentoX/BomberMan.PPM-(float)0.16, ((PowerUpDiminuisciTempo) p).posizionamentoY/BomberMan.PPM-(float)0.16, 32/BomberMan.PPM,32/BomberMan.PPM);
					batch.end();
				}
			}
			else if(p instanceof PowerUpAumentaTempo) {
				if(!((PowerUpAumentaTempo)p).attiva) {
					disegnoPowerUp = atlasPowerUp.createSprite("power_up (5)");
					batch.begin();
					batch.draw(disegnoPowerUp, ((PowerUpAumentaTempo) p).posizionamentoX/BomberMan.PPM-(float)0.16, ((PowerUpAumentaTempo) p).posizionamentoY/BomberMan.PPM-(float)0.16, 32/BomberMan.PPM,32/BomberMan.PPM);
					batch.end();
				}
			}
			else if(p instanceof PowerUpSpintaBomba) {
				if(!((PowerUpSpintaBomba)p).attiva) {
					disegnoPowerUp = atlasPowerUp.createSprite("power_up (16)");
					batch.begin();
					batch.draw(disegnoPowerUp, ((PowerUpSpintaBomba) p).posizionamentoX/BomberMan.PPM-(float)0.16, ((PowerUpSpintaBomba) p).posizionamentoY/BomberMan.PPM-(float)0.16, 32/BomberMan.PPM,32/BomberMan.PPM);
					batch.end();
				}
			}
			else if(p instanceof PowerUpAumentaVelocit‡) {
				if(!((PowerUpAumentaVelocit‡)p).attiva) {
					disegnoPowerUp = atlasPowerUp.createSprite("power_up (3)");
					batch.begin();
					batch.draw(disegnoPowerUp, ((PowerUpAumentaVelocit‡) p).posizionamentoX/BomberMan.PPM-(float)0.16, ((PowerUpAumentaVelocit‡) p).posizionamentoY/BomberMan.PPM-(float)0.16, 32/BomberMan.PPM,32/BomberMan.PPM);
					batch.end();
				}
			}
			else if(p instanceof PowerUpAumentaRaggioBomba) {
				if(!((PowerUpAumentaRaggioBomba)p).attiva) {
					disegnoPowerUp = atlasPowerUp.createSprite("power_up (10)");
					batch.begin();
					batch.draw(disegnoPowerUp, ((PowerUpAumentaRaggioBomba) p).posizionamentoX/BomberMan.PPM-(float)0.16, ((PowerUpAumentaRaggioBomba) p).posizionamentoY/BomberMan.PPM-(float)0.16, 32/BomberMan.PPM,32/BomberMan.PPM);
					batch.end();
				}
			}
			
		}
		Integer k = 0;
		
		batch.begin();
			
			/**
			 * disegni delle bombe
			 */
			while(k<posizioniBombe.size()) {
				
				
				
				if(posizioniBombe.get(k).tempo<=bombAnimation.getAnimationDuration()) {
					
					if(((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).lancioBomba==true && bonusSpintaBomba && Gdx.input.isKeyPressed(Input.Keys.S) ) {
						posizioniBombe.get(k).x=player.b2body.getPosition().x;
						posizioniBombe.get(k).y=player.b2body.getPosition().y;
						batch.draw(bombAnimation.getKeyFrame(posizioniBombe.get(k).tempo,true), (player.b2body.getPosition().x-(float)0.16), (player.b2body.getPosition().y-(float)0.16), 32/BomberMan.PPM,32/BomberMan.PPM);
					}
					else {
						((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).prova2=false;
						batch.draw(bombAnimation.getKeyFrame(posizioniBombe.get(k).tempo,true), (posizioniBombe.get(k).x-(float)0.16), (posizioniBombe.get(k).y-(float)0.16), 32/BomberMan.PPM,32/BomberMan.PPM);
					}
					
					posizioniBombe.get(k).tempo+=0.1f;
					if(((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).prova) {
						
						switch(((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).currentState) {
						case DESTRA:
							posizioniBombe.get(k).x+=animazioneLancioBomba;
						break;
						case SINISTRA:
							posizioniBombe.get(k).x-=animazioneLancioBomba;
						break;
						case ALTO:
							posizioniBombe.get(k).y+=animazioneLancioBomba;
						break;
						case BASSO:
							posizioniBombe.get(k).y-=animazioneLancioBomba;
						break;
							
						
						}
					}
					
					if(bodies.get(posizioniBombe.get(k)).getUserData() instanceof Bomb) {
						if(((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).lancioBomba==true && bonusSpintaBomba && Gdx.input.isKeyPressed(Input.Keys.X) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
							
							((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).body.setLinearVelocity(0f, 0f);
							((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).body.applyLinearImpulse(new Vector2(lancioBombaDistanza,0)	,player.b2body.getWorldCenter() , true);
							((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).prova=true;
							((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).currentState = Bomb.State.DESTRA;
							((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).lancioBomba=false;
						}
						else if(((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).lancioBomba==true && bonusSpintaBomba && Gdx.input.isKeyPressed(Input.Keys.X) && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
							
							((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).body.setLinearVelocity(0f, 0f);
							((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).body.applyLinearImpulse(new Vector2(0,-lancioBombaDistanza)	,player.b2body.getWorldCenter() , true);
							((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).prova=true;
							((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).currentState = Bomb.State.BASSO;
							((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).lancioBomba=false;
						}
						else if(((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).lancioBomba==true && bonusSpintaBomba && Gdx.input.isKeyPressed(Input.Keys.X) && Gdx.input.isKeyPressed(Input.Keys.UP)) {
							
							((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).body.setLinearVelocity(0f, 0f);
							((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).body.applyLinearImpulse(new Vector2(0,lancioBombaDistanza)	,player.b2body.getWorldCenter() , true);
							((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).prova=true;
							((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).currentState = Bomb.State.ALTO;
							((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).lancioBomba=false;
						}
						else if(((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).lancioBomba==true && bonusSpintaBomba && Gdx.input.isKeyPressed(Input.Keys.X) && Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
							
							((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).body.setLinearVelocity(0f, 0f);
							((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).body.applyLinearImpulse(new Vector2(-lancioBombaDistanza,0)	,player.b2body.getWorldCenter() , true);
							((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).prova=true;
							((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).currentState = Bomb.State.SINISTRA;
							((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).lancioBomba=false;
						}
						else if(((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).lancioBomba==true && bonusSpintaBomba && Gdx.input.isKeyPressed(Input.Keys.S) ) {
							
							bodies.get(posizioniBombe.get(k)).setTransform(new Vector2(player.b2body.getPosition().x,player.b2body.getPosition().y), 0);
							((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).prova2=true;
						}
						
						
					}
					if(posizioniBombe.get(k).tempo>=4.8f && posizioniBombe.get(k).tempo<=8.8f) {
						((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).prova=false;
						((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).prova2=false;
						((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).body.setType(BodyDef.BodyType.StaticBody);
						
						((PolygonShape) shape).setAsBox(raggioBombaLunghezza/BomberMan.PPM,raggioBombaLarghezza/BomberMan.PPM);
						((PolygonShape) shape2).setAsBox(raggioBombaLarghezza/BomberMan.PPM,raggioBombaLunghezza/BomberMan.PPM);
						
						fdef.shape = shape;
						fdef.filter.categoryBits = JavaBomber.BOMB_BIT;
						fdef.isSensor = true;

						fdef3.shape = shape2;
						
						fdef3.isSensor = true;
						
						fdef3.filter.categoryBits = JavaBomber.BOMB_BIT;
						bodies.get(posizioniBombe.get(k)).setActive(true);
						bodies.get(posizioniBombe.get(k)).setType(BodyDef.BodyType.DynamicBody);
						bodies.get(posizioniBombe.get(k));

						bodies.get(posizioniBombe.get(k)).createFixture(fdef).setUserData("bomb");
						
						bodies.get(posizioniBombe.get(k)).createFixture(fdef3).setUserData("bomb");

						if(((Bomb)bodies.get(posizioniBombe.get(k)).getUserData()).prova2) {
							batch.draw(bombAnimation.getKeyFrame(posizioniBombe.get(k).tempo,true), (player.b2body.getPosition().x-(float)0.16), (player.b2body.getPosition().y-(float)0.16), 32/BomberMan.PPM,32/BomberMan.PPM);
						}
						batch.draw(bombAnimationSopra.getKeyFrame(posizioniBombe.get(k).tempoBomba,true), (posizioniBombe.get(k).x-(float)0.16), (posizioniBombe.get(k).y+(float)0.16), 32/BomberMan.PPM,32/BomberMan.PPM);
						batch.draw(bombAnimationSotto.getKeyFrame(posizioniBombe.get(k).tempoBomba,true), (posizioniBombe.get(k).x-(float)0.16), (posizioniBombe.get(k).y-(float)0.48), 32/BomberMan.PPM,32/BomberMan.PPM);
						batch.draw(bombAnimationDestra.getKeyFrame(posizioniBombe.get(k).tempoBomba,true), (posizioniBombe.get(k).x+(float)0.16), (posizioniBombe.get(k).y-(float)0.16), 32/BomberMan.PPM,32/BomberMan.PPM);
						batch.draw(bombAnimationSinistra.getKeyFrame(posizioniBombe.get(k).tempoBomba,true), (posizioniBombe.get(k).x-(float)0.48), (posizioniBombe.get(k).y-(float)0.16), 32/BomberMan.PPM,32/BomberMan.PPM);
						posizioniBombe.get(k).tempoBomba+=0.1f;
					}
					
				}
				else {
					
					if(bodies.containsKey(posizioniBombe.get(k))&& !bodyDaDistruggere.contains(bodies.get(posizioniBombe.get(k)))) {
						
						bodyDaDistruggere.add(bodies.get(posizioniBombe.get(k)));
						bodies.remove(posizioniBombe.get(k));
						posizioniBombe.remove(posizioniBombe.get(k));
						k-=1;
					}
				}
				k+=1;
			}
		
		batch.end();

		
		batch.begin();
		
		/**
		 * disegni dei nemici
		 */
		for(SfornaNemici e : enemies) {
			if(e instanceof Enemy) {
				if(((Enemy)e).isMorto()) {
					batch.draw(((Enemy)e).getMorte().getKeyFrame(((Enemy)e).getTempoDellaMorte()), ((Enemy)e).getPosizioneX()-(float)0.16, ((Enemy)e).getPosizioneY()-(float)0.16,32/BomberMan.PPM,32/BomberMan.PPM);
				}
				else {
					((Enemy)e).setPosizioneX(((Enemy)e).getB2body().getPosition().x);
					((Enemy)e).setPosizioneY(((Enemy)e).getB2body().getPosition().y);
					batch.draw(((Enemy)e).getRegion(), ((Enemy)e).getB2body().getPosition().x-(float)0.16, ((Enemy)e).getB2body().getPosition().y-(float)0.16, 32/BomberMan.PPM,32/BomberMan.PPM);
				}
			}
			else if(e instanceof EnemyOriginale) {
				if(((EnemyOriginale)e).isMorto()) {
					batch.draw(((EnemyOriginale)e).getMorte().getKeyFrame(((EnemyOriginale)e).getTempoDellaMorte()), ((EnemyOriginale)e).getPosizioneX()-(float)0.32, ((EnemyOriginale)e).getPosizioneY()-(float)0.32,80/BomberMan.PPM,80/BomberMan.PPM);
				}
				else {
					((EnemyOriginale)e).setPosizioneX(((EnemyOriginale)e).getB2body().getPosition().x);
					((EnemyOriginale)e).setPosizioneY(((EnemyOriginale)e).getB2body().getPosition().y);
					batch.draw(((EnemyOriginale)e).getRegion(), ((EnemyOriginale)e).getB2body().getPosition().x-(float)0.16, ((EnemyOriginale)e).getB2body().getPosition().y-(float)0.16, 32/BomberMan.PPM,32/BomberMan.PPM);
				}
			}
			else if(e instanceof Boss) {
					((Boss)e).setPosizioneX(((Boss)e).getB2body().getPosition().x);
					((Boss)e).setPosizioneY(((Boss)e).getB2body().getPosition().y);
					batch.draw(((Boss)e).getRegion(), ((Boss)e).getB2body().getPosition().x-(float)0.32, ((Boss)e).getB2body().getPosition().y-(float)0.32, 64/BomberMan.PPM,64/BomberMan.PPM);
				
			}
			else if(e instanceof Proiettile) {
				((Proiettile)e).setPosizioneX(((Proiettile)e).getB2body().getPosition().x);
				((Proiettile)e).setPosizioneY(((Proiettile)e).getB2body().getPosition().y);
				batch.draw(((Proiettile)e).getRegion(), ((Proiettile)e).getB2body().getPosition().x-(float)0.32, ((Proiettile)e).getB2body().getPosition().y-(float)0.32, 64/BomberMan.PPM,64/BomberMan.PPM);
			
			}
		}
		batch.end();
		
		game.batch.setProjectionMatrix(gamecam.combined);
		
		//disegno del personaggio
		if(!player.morto && player !=null) {
			game.batch.begin();
			player.draw(game.batch);
			game.batch.end();
		}
		
		//disegno della morte
		else if(player.morto) {
			batch.begin();
			
			if(tempoMorte <= bombAnimationMorte.getAnimationDuration()) {
				batch.draw(bombAnimationMorte.getKeyFrame(tempoMorte), (coordinataMorteX-(float)0.16), (coordinataMorteY-(float)0.16), 32/BomberMan.PPM,32/BomberMan.PPM);
				tempoMorte+=0.1f;
				
			}
			
			batch.end();
			}
		
		game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		
		//adattamento legenda allo schermo
		hud.stage.draw();
		
	}
	
	
	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	

	/**
	 * metodo per riempire i frames di sprites
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
	 * libera memoria
	 */
	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
		world.dispose();
		b2dr.dispose();
		hud.dispose();
		batch.dispose();
		fdef.shape.dispose();
		fdef3.shape.dispose();
		atlasBomba.dispose();
		atlasBombaDestra.dispose();
		atlasBombaSinistra.dispose();
		atlasBombaSopra.dispose();
		atlasBombaSotto.dispose();
		atlasDestraSinistra.dispose();
		atlasDown.dispose();
	}
	
	
	/**
	 * impostazione numero bombe
	 */
	public void setNumeroBombe() {
		numeroBombe+=1;
	}
	
	/**
	 * getter numero bombe
	 * @return
	 */
	public int getNumeroBombe() {
		return numeroBombe;
	}
	
	/**
	 * controlla proitettili nell'array di nemici
	 * @param x
	 * @return
	 */
	public boolean controllaProiettile(Array<SfornaNemici> x) {
		boolean h = true;
		for (SfornaNemici e : x) {
			if(e instanceof Proiettile) h=false;
		}
		return h;
	}
	
	/**
	 * controlla quanti boss ci sono nell'array di nemici
	 * @param y
	 * @return
	 */
	public boolean controllaBoss(Array<SfornaNemici> y) {
		boolean h = true;
		for(SfornaNemici e : y) {
			if(e instanceof Boss) h=false;
		}
		return h;
	}
}















