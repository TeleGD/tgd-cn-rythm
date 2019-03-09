package rythm;

//import java.util.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Color;

import v4lk.lwbd.BeatDetector;
import v4lk.lwbd.BeatDetector.AudioType;
import v4lk.lwbd.BeatDetector.DetectorSensitivity;
import v4lk.lwbd.decoders.Decoder;
import v4lk.lwbd.decoders.JLayerMp3Decoder;
import v4lk.lwbd.util.Beat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Track {
	private int width;
	private int height;
	//private int world_width;//INUTILISE
	//private int world_height;//INUTILISE
	private int posX;
	private int posY;
	private int score;
	private String date;
	private String filename;
	private int difficulty;//Vaudra 0 au niveau facile, 1 au niveau moyen, 2 au niveau difficile(on laisse tous les beats passer : quelquesoit leur énergie).
	private float seuil;//Détermine fonction de la difficulté le seuil de niveau d'énergie pour les blocs que l'on crée.
	
	
	public Track(int world_width,int world_height) {
		this.width=(int) (0.8*world_width);
		this.height=world_height;
		this.posX=(int) (0.1*world_width);
		this.posY=0;
	}
		private Block block = new Block(posX,30,0,0,false);
	
	
	
	public Beat[] lwbd(){
		try {
			File audioFile = new File("res/songs/paulette.mp3");
			return BeatDetector.detectBeats(audioFile, AudioType.MP3);
		}
		catch(Exception e) {
			return null;
		}
	}
	
	public ArrayList<Long> listBlocks (float seuil) {
		ArrayList<Long> listTime = new ArrayList<Long>();
		Beat[] beats=lwbd();
		for(Beat b : beats){
			if(b.energy>seuil) {
				listTime.add(b.timeMs);			
			}
		}
		return listTime;
	}
	
	//public ;;; genereBlock(int difficulty){
		
		
	//}
	
	public int getScore() {
		return score;
	}

	//@Override
	public void update (GameContainer container, StateBasedGame game, int delta) {
		/* Méthode exécutée environ 60 fois par seconde */
	}

	//@Override
	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		context.setColor(Color.blue);
		context.fillRect(this.posX,this.posY,this.width,this.height);
		context.setColor(Color.white);
		context.fillRect(this.posX,this.posY,2,this.height);
		context.fillRect(this.posX+this.width/5,this.posY,2,this.height);
		context.fillRect(posX+width*2/5,posY,2,height);
		context.fillRect(posX+width*3/5,posY,2,height);
		context.fillRect(posX+width*4/5,posY,2,height);
		context.fillRect(posX+width,posY,2,height);
		block.render(container, game, context);
		/* Méthode exécutée environ 60 fois par seconde */
		//System.out.println("Coucou render !"+posX+" "+posY+" "+width+" "+height);
	}
}
