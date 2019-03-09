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
	private boolean aPrendre;
	
	
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
		this.aPrendre = false;
	}
	
	public Block(int posx, int posy, int speed, int score, boolean aPrendre) {
		this.posx = posx;
		this.posy = posy;
		this.speed = speed;
		this.score = score;
		this.life = true;
		this.aPrendre = aPrendre;
	}
	
	@Override
	public void update (GameContainer container, StateBasedGame game, int delta) {
		this.posy = this.posy + speed*delta;
		if(this.posy - this.height >= world.height) {
			this.setLife(false); // Tue la case si depasse du cadre du jeu
		}
		if(this.posy + this.height >= player.height) {
			
		}
	}

	@Override
	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		context.setColor(Color.green);
		context.fillRect(0, 0, 30, 30);
	}
}
