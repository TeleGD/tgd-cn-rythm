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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class Track {
	private int width;
	private int height;
	private int posX;
	private int posY;
	private int score;
	private String date;
	private String filename;
	private int difficulty;//Vaudra 0 au niveau facile, 1 au niveau moyen, 2 au niveau difficile(on laisse tous les beats passer : quelquesoit leur énergie).
	private float seuil;//Détermine fonction de la difficulté le seuil de niveau d'énergie pour les blocs que l'on crée.
	//private int time;
	private int yBlock=0;
	private Block block;
	private Timer timer = new Timer();

	
	public Track(int world_width,int world_height) {
		this.width=(int) (0.8*world_width);
		this.height=world_height;
		this.posX=(int) (0.1*world_width);
		this.posY=0;
		
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
				  block = new Block(posX,0,0.5,0,false,width/5);
			    // Your database code here
			  }
		}, 4*1000);
	}
	
	
	
	
	
	public Beat[] lwbd(){
		try {
			File audioFile = new File("res/songs/paulette.mp3");
			return BeatDetector.detectBeats(audioFile, AudioType.MP3);
		}
		catch(Exception e) {
			return null;
		}
	}
	
	public ArrayList<Long> listBlocks (float seuil,int difficulty) {
		ArrayList<Long> listTime = new ArrayList<Long>();
		Long instant;
		Beat[] beats = lwbd();
		for(Beat b : beats){
			if(b.energy > seuil) {
				instant = b.timeMs;
				listTime.add(instant);
			}
		}
		return listTime;
	}
	
	public ArrayList<Integer> getRoute(ArrayList<Long> listTime) {
		ArrayList<Integer> routes = new ArrayList<>();
		for (long instant : listTime) {
			int i, j = 0;
			boolean droite = false; boolean gauche = false;
			boolean[] envisageable = {true, true, true, true, true};
			boolean encore = true;
			while (encore) {
				j = ThreadLocalRandom.current().nextInt(0, 5);	// Entier dans l'intervalle [0;4]
				// On vérifie qu'il n'y ait pas de bloc dans les deux cases d'à côté :
				i = listTime.size();
				while (i >= 0 && listTime.get(i) > instant-2*30/(0.5*0.06)) {	// 2 * taille_block / vitesse(pixels/ms)
					if (routes.get(i) == j+1) { droite = true; }
					if (routes.get(i) == j-1) { gauche = true; }
					i--;
				} if (droite && gauche) {
					encore = false;
				} else { // On vérifie que choisir une autre route est envisageable
					for (boolean c : envisageable) {
						if (c) { encore = true; }
					}
				}
			} routes.add(j);
		} return routes;
	}
	
	
	public int getScore() {
		return score;
	}
	
	//private Block block = new Block(posX,0,1,0,false,(int)(width/5));

	//@Override
	public void update (GameContainer container, StateBasedGame game, int delta) {
		/* Méthode exécutée environ 60 fois par seconde */
		if(block!=null) {
			block.update(container, game, delta);
		}
				
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
		context.fillRect(posX,27*height/30, width, 2);
		if(block!=null) {
			block.render(container, game, context);
		}
		/* Méthode exécutée environ 60 fois par seconde */
		
		//time++;
		//context.setColor(Color.white);
		//context.drawString("Time : "+);
	}
}
