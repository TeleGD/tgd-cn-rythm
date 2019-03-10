package rythm;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import org.newdawn.slick.Color;

public class Block {
	private int posx;
	private int posy;
	private double speed;
	private int score;
	private boolean life;
	private boolean aPrendre;
	private int width, height;
	private Image goodBlock = Track.goodBlock;
	private Image badBlock = Track.badBlock;
	
	
	public boolean getLife() {
		return this.life;
	}
	
	public void setLife(boolean life) {
		this.life = life;
	}
	
	public Block() {
		this(0, 0, 0, 0, true, 50, 50);
	}
	
	public Block(int posx, int posy, double speed, int score, boolean aPrendre, int width, int height) {
		this.posx = posx;
		this.posy = posy;
		this.speed = speed;
		this.score = score;
		this.life = true;
		this.aPrendre = aPrendre;
		this.width = width;
		this.height = height;
	}
	
	//@Override
	public void update (GameContainer container, StateBasedGame game, int delta) {
		this.posy = (int) (this.posy + speed*delta);
	}

	//@Override
	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		// context.setColor(Color.green);
		// context.fillRect(posx,posy,width, 30);

		if(this.score >=0){
			goodBlock.draw(this.posx, this.posy);
		}
		else{
			badBlock.draw(this.posx, this.posy);
		}
	}
	
	public int getPosY() {
		return this.posy;
	}
}
