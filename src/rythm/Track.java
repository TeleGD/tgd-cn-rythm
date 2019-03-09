package rythm;

//import java.util.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Color;

public class Track {
	private int width;
	private int height;
	private int world_width;
	private int world_height;
	private int posX;
	private int posY;
	private int score;
	private String date;
	private String filename;
	
	public Track(int world_width,int world_height) {
		this.width=8/10*world_height;
		this.posX=1/10*world_height;
		this.posY=0;
	}
	
	
	
	public int lwbd(){
		return 0;
		
	}
	
	public int getScore() {
		return score;
	}

	//@Override
	public void update (GameContainer container, StateBasedGame game, int delta) {
		/* Méthode exécutée environ 60 fois par seconde */
	}

	//@Override
	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		context.setColor(Color.red);
		context.fillRect(posX,posY,width,height);
		context.setColor(Color.white);
		context.fillRect(posX,posY,2,height);
		context.fillRect(posX+width/5,posY,2,height);
		context.fillRect(posX+width*2/5,posY,2,height);
		context.fillRect(posX+width*3/5,posY,2,height);
		context.fillRect(posX+width*4/5,posY,2,height);
		context.fillRect(posX+width,posY,2,height);
		/* Méthode exécutée environ 60 fois par seconde */
	}
}
