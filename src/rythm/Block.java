package rythm;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class Block {
	private int posx;
	private int posy;
	
	private int speed;
	private int score;
	private boolean life;
	
	
	public boolean getLife() {
		return this.life;
	}
	
	public void setLife(boolean life) {
		this.life = life;
	}
	
	public Block() {
		this.posx = 0;
		this.posy = 0;
		this.speed = 0;
		this.score = 0;
		this.life = true;
	}
	
	public Block(int posx, int posy, int speed, int score) {
		this.posx = posx;
		this.posy = posy;
		this.speed = speed;
		this.score = score;
		this.life = true;
	}
	
	@Override
	public void update (GameContainer container, StateBasedGame game, int delta) {
		this.posy = this.posy + speed*delta;
		if(this.posy >= world.height) {
			this.setLife(false); // Tue la case si depasse 
		}
		// Gerer si le joueur colisionne la case
	}

	@Override
	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		/* Méthode exécutée environ 60 fois par seconde */
	}
}
