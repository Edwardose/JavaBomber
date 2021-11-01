package Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import it.uniroma1.metodologie2018.javabomber.JavaBomber;

/**
 * legenda vita,n° bombe ecc
 * @author Edoardo
 *
 */
public class Hud implements Disposable{
	public Stage stage;
	private Viewport viewport;
	private Integer life;
	
	public Label lifeLabel;
	public Label bombLabel;
	public Label timeLabel;
	public Integer bombe = 1;
	public int countDown = 300;
	public float tempo = 0.0f;
	
	public Table table = new Table();
	
	public Hud(SpriteBatch sb) {
		life = 3;
		
		viewport = new FitViewport(JavaBomber.V_WIDTH,JavaBomber.V_HEIGHT,new OrthographicCamera());
		stage = new Stage(viewport,sb);
		
		
		table.top();
		table.setFillParent(true);
		lifeLabel = new Label(String.format("LIFE : %02d", life), new Label.LabelStyle(new BitmapFont(),Color.WHITE));
		bombLabel = new Label(String.format("BOMBE : %2d", bombe), new Label.LabelStyle(new BitmapFont(),Color.WHITE));
		timeLabel = new Label(String.format("TIME : %3d", countDown), new Label.LabelStyle(new BitmapFont(),Color.WHITE));
		table.add();
		table.add(bombLabel).expandX().padLeft(500).padTop(10);
		table.add();
		table.add(lifeLabel).expandX().padRight(500).padTop(10);
		table.add(timeLabel).expandX().padRight(350).padTop(10);
		
		
		stage.addActor(table);
		
	}
	
	public void update(float dt,boolean aumentaBomba,boolean diminuisciBomba,boolean reset,boolean morte) {
		if(diminuisciBomba) {
			bombe-=1;
			bombLabel.setText(String.format("BOMBE : %2d", bombe));
		}
		if(aumentaBomba) {
			bombe+=1;
			bombLabel.setText(String.format("BOMBE : %2d", bombe));
		}
		
		if(reset) {
			bombe=1;
			bombLabel.setText(String.format("BOMBE : %2d", bombe));
		}
		if(morte) {
			if(life>0) {
				life-=1;
				lifeLabel.setText(String.format("LIFE : %02d", life));
			}
		}
		if(tempo>=3) {
			countDown--;
			timeLabel.setText(String.format("TIME : %3d", countDown));
			tempo=0.0f;
		}
		tempo+=0.1f;
		
		
		
	}
	
	public void diminuisciTempo() {
		countDown-=30;
		timeLabel.setText(String.format("TIME : %3d", countDown));
	}
	
	public void aumentaTempo() {
		countDown+=30;
		timeLabel.setText(String.format("TIME : %3d", countDown));
	}
	
	public void modifiche(boolean aumentaVita,boolean diminuisciVita) {
		if(aumentaVita) {
			life++;
			lifeLabel.setText(String.format("LIFE : %02d", life));
		}
		if(diminuisciVita) {
			life--;
			lifeLabel.setText(String.format("LIFE : %02d", life));
		}
		
	}
	
	
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
	}
	
	
	
}
