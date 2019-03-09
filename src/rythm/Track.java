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
		this.width=(int) (0.8*world_width);
		this.height=world_height;
		this.posX=(int) (0.1*world_width);
		this.posY=0;
		System.out.println(world_width+" "+world_height+" "+width+" "+height+" "+posX+" "+posY);
	}
	
	
	
	public Beat[] lwbd() throws IOException {
		File audioFile = new File("res/songs/paulette.mp3");
		return BeatDetector.detectBeats(audioFile, AudioType.MP3);
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
		context.setColor(Color.blue);
		context.fillRect(this.posX,this.posY,this.width,this.height);
		context.setColor(Color.white);
		context.fillRect(this.posX,this.posY,2,this.height);
		context.fillRect(this.posX+this.width/5,this.posY,2,this.height);
		context.fillRect(posX+width*2/5,posY,2,height);
		context.fillRect(posX+width*3/5,posY,2,height);
		context.fillRect(posX+width*4/5,posY,2,height);
		context.fillRect(posX+width,posY,2,height);
		/* Méthode exécutée environ 60 fois par seconde */
		//System.out.println("Coucou render !"+posX+" "+posY+" "+width+" "+height);
	}
}
