package Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import it.uniroma1.metodologie2018.javabomber.JavaBomber;
import it.uniroma1.metodologie2018.javabomber.entities.Blocco;
import it.uniroma1.metodologie2018.javabomber.entities.Bomb;
import it.uniroma1.metodologie2018.javabomber.entities.BomberMan;
import it.uniroma1.metodologie2018.javabomber.entities.Boss;
import it.uniroma1.metodologie2018.javabomber.entities.Enemy;
import it.uniroma1.metodologie2018.javabomber.entities.EnemyOriginale;
import it.uniroma1.metodologie2018.javabomber.entities.PowerUpAumentaRaggioBomba;
import it.uniroma1.metodologie2018.javabomber.entities.PowerUpAumentaTempo;
import it.uniroma1.metodologie2018.javabomber.entities.PowerUpAumentaVelocità;
import it.uniroma1.metodologie2018.javabomber.entities.PowerUpBomba;
import it.uniroma1.metodologie2018.javabomber.entities.PowerUpDiminuisciRaggioBomba;
import it.uniroma1.metodologie2018.javabomber.entities.PowerUpDiminuisciTempo;
import it.uniroma1.metodologie2018.javabomber.entities.PowerUpDiminuisciVelocità;
import it.uniroma1.metodologie2018.javabomber.entities.PowerUpOltrePassaBombe;
import it.uniroma1.metodologie2018.javabomber.entities.PowerUpSpintaBomba;
import it.uniroma1.metodologie2018.javabomber.entities.PowerUpVita;
import it.uniroma1.metodologie2018.javabomber.entities.Proiettile;
/**
 * Contact Listener
 * @author Edoardo
 *
 */
public class WorldContactListener implements ContactListener{

public boolean sposta = false;
	/**
	 * riconosce quale oggetto è entrato in contatto e usa i metodi di quell'oggetto
	 */
	@Override
	public void beginContact(Contact contact) {
	Fixture A = contact.getFixtureA();
	Fixture B = contact.getFixtureB();
	
	
	
	int cDef = A.getFilterData().categoryBits | B.getFilterData().categoryBits;
	
	if(A.getUserData() == "bomb" || B.getUserData() == "bomb") {
		Fixture body = A.getUserData() == "bomb" ? A: B;
		Fixture object = body == A ? B : A;
		
		if(object.getUserData() instanceof BomberMan) {
			((BomberMan)object.getUserData()).setMorte();
		}
		
		else if(object.getUserData() instanceof Enemy) {
			((Enemy)object.getUserData()).morto();
		}
		
		else if(object.getUserData() instanceof EnemyOriginale) {
			((EnemyOriginale)object.getUserData()).morto();
		}
		
		
		
	}
	
	else if(A.getUserData() instanceof Enemy || B.getUserData() instanceof Enemy) {
		Fixture body = A.getUserData() instanceof Enemy ? A: B;
		Fixture object = body == A ? B : A;
		
		if(object.getUserData() instanceof BomberMan) {
			((BomberMan)object.getUserData()).setMorte();
		}
	}
	
	else if(A.getUserData() instanceof EnemyOriginale || B.getUserData() instanceof EnemyOriginale) {
		Fixture body = A.getUserData() instanceof EnemyOriginale ? A: B;
		Fixture object = body == A ? B : A;
		
		if(object.getUserData() instanceof BomberMan) {
			((BomberMan)object.getUserData()).setMorte();
		}
	}
	
	else if(A.getUserData() instanceof Proiettile || B.getUserData() instanceof Proiettile) {
		Fixture body = A.getUserData() instanceof Proiettile ? A: B;
		Fixture object = body == A ? B : A;
		
		if(object.getUserData() instanceof BomberMan) {
			((BomberMan)object.getUserData()).setMorte();
		}
	}
	
	else if(A.getUserData() instanceof Boss || B.getUserData() instanceof Boss) {
		Fixture body = A.getUserData() instanceof Boss ? A: B;
		Fixture object = body == A ? B : A;
		
		if(object.getUserData() instanceof BomberMan) {
			((BomberMan)object.getUserData()).setMorte();
		}
	}
	
	else if(A.getUserData() instanceof BomberMan || B.getUserData() instanceof BomberMan) {
		Fixture body = A.getUserData() instanceof BomberMan ? A: B;
		Fixture object = body == A ? B : A;
		
		if(object.getUserData() instanceof Bomb) {
			((Bomb)object.getUserData()).lancioBomba=true;
		}
	}
	
	
	/**
	 * attaìraverso gli short capisce quale oggetto è entrato in contatto con cosa
	 */
	switch(cDef) {
	case JavaBomber.ENEMY_BIT | JavaBomber.BLOCK_BIT :
		if(A.getFilterData().categoryBits == JavaBomber.ENEMY_BIT) {
			((Enemy)A.getUserData()).muovi();
			
		}
		else if(B.getFilterData().categoryBits == JavaBomber.ENEMY_BIT) {
			((Enemy)B.getUserData()).muovi();
		
		}
		break;
	case JavaBomber.ENEMY_BIT | JavaBomber.WALL_BIT:
		if(A.getFilterData().categoryBits == JavaBomber.ENEMY_BIT) {
			((Enemy)A.getUserData()).muovi();
			
		}
		else if(B.getFilterData().categoryBits == JavaBomber.ENEMY_BIT) {
			((Enemy)B.getUserData()).muovi();
		
		}
		break;
	case JavaBomber.ENEMY_ORIGINAL | JavaBomber.BLOCK_BIT :
		if(A.getFilterData().categoryBits == JavaBomber.ENEMY_ORIGINAL) {
			((EnemyOriginale)A.getUserData()).muovi();
			
		}
		else if(B.getFilterData().categoryBits == JavaBomber.ENEMY_ORIGINAL) {
			((EnemyOriginale)B.getUserData()).muovi();
		
		}
		break;
	case JavaBomber.BULLET | JavaBomber.BLOCK_BIT :
		if(A.getFilterData().categoryBits == JavaBomber.BULLET) {
			((Proiettile)A.getUserData()).setMorto(true);
			
		}
		else if(B.getFilterData().categoryBits == JavaBomber.BULLET) {
			((Proiettile)B.getUserData()).setMorto(true);
		
		}
		break;
	case JavaBomber.BULLET | JavaBomber.BOMBER_BIT :
		if(A.getFilterData().categoryBits == JavaBomber.BULLET) {
			((Proiettile)A.getUserData()).setMorto(true);
			
		}
		else if(B.getFilterData().categoryBits == JavaBomber.BULLET) {
			((Proiettile)B.getUserData()).setMorto(true);
		
		}
		break;
	case JavaBomber.BULLET | JavaBomber.WALL_BIT :
		if(A.getFilterData().categoryBits == JavaBomber.BULLET) {
			((Proiettile)A.getUserData()).setMorto(true);
			
		}
		else if(B.getFilterData().categoryBits == JavaBomber.BULLET) {
			((Proiettile)B.getUserData()).setMorto(true);
		
		}
		break;
	case JavaBomber.ENEMY_ORIGINAL | JavaBomber.WALL_BIT:
		if(A.getFilterData().categoryBits == JavaBomber.ENEMY_ORIGINAL) {
			((EnemyOriginale)A.getUserData()).muovi();
			
		}
		else if(B.getFilterData().categoryBits == JavaBomber.ENEMY_ORIGINAL) {
			((EnemyOriginale)B.getUserData()).muovi();
		
		}
		break;
	case JavaBomber.BOMB_BIT | JavaBomber.BLOCK_BIT:
	
		if(A.getFilterData().categoryBits == JavaBomber.BLOCK_BIT) {
			((Blocco)A.getUserData()).setRimuovi();
			
		}
		else if(B.getFilterData().categoryBits == JavaBomber.BLOCK_BIT) {
			((Blocco)B.getUserData()).setRimuovi();
		
		}
		break;
	case JavaBomber.BOSS | JavaBomber.BLOCK_BIT:
		if(A.getFilterData().categoryBits == JavaBomber.BOSS) {
			((Boss)A.getUserData()).muovi();
			
		}
		else if(B.getFilterData().categoryBits == JavaBomber.BOSS) {
			((Boss)B.getUserData()).muovi();
		
		}
		break;
	case JavaBomber.BOSS | JavaBomber.WALL_BIT:
		if(A.getFilterData().categoryBits == JavaBomber.BOSS) {
			((Boss)A.getUserData()).muovi();
			
		}
		else if(B.getFilterData().categoryBits == JavaBomber.BOSS) {
			((Boss)B.getUserData()).muovi();
		
		}
		break;
	case JavaBomber.BOMBER_BIT | JavaBomber.POWER_UP :
		if(A.getFilterData().categoryBits == JavaBomber.POWER_UP) {
			if(A.getUserData() instanceof PowerUpBomba) {
				((PowerUpBomba)A.getUserData()).attivazione();
			}
			else if(A.getUserData() instanceof PowerUpVita) {
				((PowerUpVita)A.getUserData()).attivazione();
			}
			else if(A.getUserData() instanceof PowerUpAumentaRaggioBomba) {
				((PowerUpAumentaRaggioBomba)A.getUserData()).attivazione();
			}
			
			else if(A.getUserData() instanceof PowerUpSpintaBomba) {
				((PowerUpSpintaBomba)A.getUserData()).attivazione();
			}
			
			else if(A.getUserData() instanceof PowerUpOltrePassaBombe) {
				((PowerUpOltrePassaBombe)A.getUserData()).attivazione();
			}
			
			else if(A.getUserData() instanceof PowerUpDiminuisciVelocità) {
				((PowerUpDiminuisciVelocità)A.getUserData()).attivazione();
			}
			
			else if(A.getUserData() instanceof PowerUpDiminuisciTempo) {
				((PowerUpDiminuisciTempo)A.getUserData()).attivazione();
			}
			
			else if(A.getUserData() instanceof PowerUpDiminuisciRaggioBomba) {
				((PowerUpDiminuisciRaggioBomba)A.getUserData()).attivazione();
			}
			
			else if(A.getUserData() instanceof PowerUpAumentaVelocità) {
				((PowerUpAumentaVelocità)A.getUserData()).attivazione();
			}
			
			else if(A.getUserData() instanceof PowerUpAumentaTempo) {
				((PowerUpAumentaTempo)A.getUserData()).attivazione();
			}
			else if(B.getUserData() instanceof PowerUpAumentaRaggioBomba) {
				((PowerUpAumentaRaggioBomba)B.getUserData()).attivazione();
			}
			
			else if(B.getUserData() instanceof PowerUpSpintaBomba) {
				((PowerUpSpintaBomba)B.getUserData()).attivazione();
			}
			
			else if(B.getUserData() instanceof PowerUpOltrePassaBombe) {
				((PowerUpOltrePassaBombe)B.getUserData()).attivazione();
			}
			
			else if(B.getUserData() instanceof PowerUpDiminuisciVelocità) {
				((PowerUpDiminuisciVelocità)B.getUserData()).attivazione();
			}
			
			else if(B.getUserData() instanceof PowerUpDiminuisciTempo) {
				((PowerUpDiminuisciTempo)B.getUserData()).attivazione();
			}
			
			else if(B.getUserData() instanceof PowerUpDiminuisciRaggioBomba) {
				((PowerUpDiminuisciRaggioBomba)B.getUserData()).attivazione();
			}
			
			else if(B.getUserData() instanceof PowerUpAumentaVelocità) {
				((PowerUpAumentaVelocità)B.getUserData()).attivazione();
			}
			
			else if(B.getUserData() instanceof PowerUpAumentaTempo) {
				((PowerUpAumentaTempo)B.getUserData()).attivazione();
			}
		
			else if(A.getUserData() instanceof PowerUpBomba) {
				((PowerUpBomba)A.getUserData()).attivazione();
			}
			else if(A.getUserData() instanceof PowerUpVita) {
				((PowerUpVita)A.getUserData()).attivazione();
			}
		
		}
		break;

		}
	
	}
		
	
	
		
		


	

	@Override
	public void endContact(Contact contact) {
	Fixture A = contact.getFixtureA();
	Fixture B = contact.getFixtureB();
	if(A.getUserData() instanceof BomberMan || B.getUserData() instanceof BomberMan) {
		Fixture body = A.getUserData() instanceof BomberMan ? A: B;
		Fixture object = body == A ? B : A;
		
		if(object.getUserData() instanceof Bomb) {

			((Bomb)object.getUserData()).lancioBomba=false;
		}
	}
		
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		Fixture A = contact.getFixtureA();
		Fixture B = contact.getFixtureB();
		
		int cDef = A.getFilterData().categoryBits | B.getFilterData().categoryBits;
		
		if(A.getUserData() == "bomb" || B.getUserData() == "bomb") {
			Fixture body = A.getUserData() == "bomb" ? A: B;
			Fixture object = body == A ? B : A;
		
			if(object.getUserData() instanceof Blocco) {
				((Blocco)object.getUserData()).setRimuovi();
			}
		}
		
		switch(cDef) {
		case JavaBomber.ENEMY_ORIGINAL | JavaBomber.BLOCK_BIT :
			if(A.getFilterData().categoryBits == JavaBomber.ENEMY_ORIGINAL) {
				((EnemyOriginale)A.getUserData()).muovi();
				
			}
			else if(B.getFilterData().categoryBits == JavaBomber.ENEMY_ORIGINAL) {
				((EnemyOriginale)B.getUserData()).muovi();
			
			}
			break;
		case JavaBomber.ENEMY_ORIGINAL | JavaBomber.WALL_BIT:
			if(A.getFilterData().categoryBits == JavaBomber.ENEMY_ORIGINAL) {
				((EnemyOriginale)A.getUserData()).muovi();
				
			}
			else if(B.getFilterData().categoryBits == JavaBomber.ENEMY_ORIGINAL) {
				((EnemyOriginale)B.getUserData()).muovi();
			
			}
			break;
		case JavaBomber.ENEMY_BIT |  JavaBomber.BLOCK_BIT:
			if(A.getFilterData().categoryBits == JavaBomber.ENEMY_BIT) {
				((Enemy)A.getUserData()).muovi();
				
			}
			else if(B.getFilterData().categoryBits == JavaBomber.ENEMY_BIT) {
				((Enemy)B.getUserData()).muovi();
			
			}
			break;
		case JavaBomber.BOSS | JavaBomber.BLOCK_BIT:
			if(A.getFilterData().categoryBits == JavaBomber.BOSS) {
				((Boss)A.getUserData()).muovi();
				
			}
			else if(B.getFilterData().categoryBits == JavaBomber.BOSS) {
				((Boss)B.getUserData()).muovi();
			
			}
			break;
		case JavaBomber.BOSS | JavaBomber.WALL_BIT:
			if(A.getFilterData().categoryBits == JavaBomber.BOSS) {
				((Boss)A.getUserData()).muovi();
				
			}
			else if(B.getFilterData().categoryBits == JavaBomber.BOSS) {
				((Boss)B.getUserData()).muovi();
			
			}
			break;
		case JavaBomber.ENEMY_BIT | JavaBomber.WALL_BIT:
			if(A.getFilterData().categoryBits == JavaBomber.ENEMY_BIT) {
				((Enemy)A.getUserData()).muovi();
				
			}
			else if(B.getFilterData().categoryBits == JavaBomber.ENEMY_BIT) {
				((Enemy)B.getUserData()).muovi();
			
			}
			break;
		}
	}

}
