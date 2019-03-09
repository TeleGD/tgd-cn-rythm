package rythm;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class Block {
	private int posx;
	private int posy;
	
	private int speed;
	private int score;
	
	public Block() {
		this.posx = 0;
		this.posy = 0;
		this.speed = 0;
		this.score = 0;
	}
	
	public Block(int posx, int posy, int speed, int score) {
		this.posx = posx;
		this.posy = posy;
		this.speed = speed;
		this.score = score;
	}
	
	@Override
	public void update (GameContainer container, StateBasedGame game, int delta) {
		/* Méthode exécutée environ 60 fois par seconde */
	}

	@Override
	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		/* Méthode exécutée environ 60 fois par seconde */
	}
}
