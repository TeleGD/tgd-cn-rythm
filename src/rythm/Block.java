package rythm;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Color;

public class Block {
	private int posx;
	private int posy;
	
	private double speed;
	private int score;
	private boolean life;
	private boolean aPrendre;
	private int width;
	
	
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
	
	public Block(int posx, int posy, double speed, int score, boolean aPrendre,int width) {
		this.posx = posx;
		this.posy = posy;
		this.speed = speed;
		this.score = score;
		this.life = true;
		this.aPrendre = aPrendre;
		this.width= width;
	}
	
	//@Override
	public void update (GameContainer container, StateBasedGame game, int delta) {
		this.posy = (int) (this.posy + speed*delta);
	}

	//@Override
	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		context.setColor(Color.green);
		context.fillRect(posx,posy,width, 30);
	}
	
	public int getPosY() {
		return this.posy;
	}
}
