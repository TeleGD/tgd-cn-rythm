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
	private double speed;

	
	public Track(int world_width,int world_height,int difficulty) {
		this.width=(int) (0.8*world_width);
		this.height=world_height;
		this.posX=(int) (0.1*world_width);
		this.posY=0;
		setSpeed(difficulty);
		ArrayList<Long> listTime=listBlocks(this.seuil);
		for(long i : listTime) {
			timer.schedule(new TimerTask() {
				  @Override
				  public void run() {
					  block = new Block(posX,0,speed,0,false,width/5);
				    // Your database code here
				  }
			}, i*100);
			System.out.println(i);
		}
	}
	
	//Réglage de la vitesse en fonction de la difficulté choisie
	
	public void setSpeed(int difficulty) {// Sert à adapter la vitesse de descente des tuiles en fonction de la difficulté choisie.
		if(difficulty==0){//Si le niveau est choisi en facile
			this.speed=0.2;
		}
		else if(difficulty==2){//Si le niveau choisi est difficile
			this.speed=0.8;
		}
		else {//Si le niveau est choisi en moyen
			this.speed=0.5;
		}
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
	
	public ArrayList<Long> listBlocks (float seuil) {//Sert à prendre la lsite des moments forts de la musique et à générer un tableau des moments ou un bloc doit apparaitre dans l'écran//Double ou long ?
		ArrayList<Long> listTime = new ArrayList<Long>();
		Long instant;
		Beat[] beats=lwbd();
		for(Beat b : beats){
			if(b.energy>seuil) {
				instant=b.timeMs;
				instant=instant-(long)(27/30*height/speed);
				listTime.add(instant);	
			}
		}
		return listTime;
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
