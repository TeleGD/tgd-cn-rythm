package rythm;

//import java.util.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

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
	private Block block;
	private Timer timer = new Timer();
	private double speed;
	private ArrayList<Block> blocs = new ArrayList<Block>();
	private static Image goodBlock;
	private static Image badBlock;
	private int k=0;
	private Image background;
	private Music song;
	private boolean collide;
	private int sizeBlock;//La taille d'un bloc, pour la modifier plus facilement si jamais
	private Player player;
	String diff;//Permet d'afficher la difficulté
	
	public Track(int world_width,int world_height,int difficulty,Player player) {
		this.width=(int) (0.8*world_width);
		this.height=world_height;
		this.posX=(int) (0.1*world_width);
		this.posY=0;
		sizeBlock=254*width/1274;//définition de la taille des blocs
		setSpeed(difficulty);
		this.player=player;
		ArrayList<Long> listTime=listBlocks(this.seuil);
		ArrayList<Integer> listRoute=getRoute(listTime);
		System.out.println(width+" "+254*width/1274);
		this.goodBlock = AppLoader.loadPicture("/images/komanjaplsa.png").getScaledCopy(sizeBlock, sizeBlock);
		this.badBlock = AppLoader.loadPicture("/images/Mauvais_Beat.png").getScaledCopy(sizeBlock, sizeBlock);
		for(int u=0;u<listRoute.size();u++) {
			timer.schedule(new TimerTask() {
				  @Override
				  public void run() {
					  block = new Block(posX+width*140*8/15540+listRoute.get(k)*width*254/1554,0,speed,0,false,sizeBlock,sizeBlock);
					  blocs.add(block);
					  k++;					  
					  if (block == null) {
						  System.out.println("AU SECOURS AU SECOURS AU SECOURS");
					  }
				    // Your database code here
				  }
			}, listTime.get(u));
		}
		this.background = AppLoader.loadPicture("/images/HIGHWAY.png").getScaledCopy(this.width, this.height);
		try {
			this.song = new Music("res/songs/paulette.ogg");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static Image getGoodBlock() {
		return goodBlock;
	}
	public static Image getBadBlock() {
		return badBlock;
	}
	//Réglage de la vitesse en fonction de la difficulté choisie
	
	public void setSpeed(int difficulty) {// Sert à adapter la vitesse de descente des tuiles en fonction de la difficulté choisie.
		if(difficulty==0){//Si le niveau est choisi en facile
			this.speed=0.2;
			this.diff="Facile";
		}
		else if(difficulty==2){//Si le niveau choisi est difficile
			this.speed=0.8;
			this.diff="Difficile";
		}
		else {//Si le niveau est choisi en moyen
			this.speed=0.5;
			this.diff="Moyen";
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
		Beat[] beats = lwbd();
		for(Beat b : beats){
			if(b.energy>seuil) {
				instant=b.timeMs;
				instant=instant-(long)(27/30*height/speed);
				listTime.add(instant);
			}
		}
		return listTime;
	}
	
	public ArrayList<Integer> getRoute(ArrayList<Long> listTime) {//Sert à connaitre le numéro de la voie(de 0 à 4) qui devra être emprunté par le bloc en fonction du "numéroé du bloc(comme si les blocs étaient numérotés
		ArrayList<Integer> routes = new ArrayList<>();
		for (int k = 0; k < listTime.size(); k++) {
			long instant = listTime.get(k);
			int i, j = 0;
			boolean droite = false; boolean gauche = false;
			boolean[] envisageable = {true, true, true, true, true};
			boolean encore = true;
			while (encore) {
				j = ThreadLocalRandom.current().nextInt(0, 5);	// Entier dans l'intervalle [0;4]
				// On vérifie qu'il n'y ait pas de bloc dans les deux cases d'à côté :
				i = k-2;
				while (i >= 0 && listTime.get(i) > instant-2*30/(0.5*0.06)) {	// 2 * taille_block / vitesse(pixels/ms)
					if (routes.get(i) == j+1) { droite = true; }
					if (routes.get(i) == j-1) { gauche = true; }
					i--;
				} envisageable[j] = false;
				if (!droite || !gauche) {
					encore = false;
				} else { // On vérifie que choisir une autre route est envisageable
					encore = false;
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

	public void play (GameContainer container, StateBasedGame game){
		/* Méthode exécutée une unique fois au début du jeu */
		song.play();
	}
	
	public void pause (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la mise en pause du jeu */
		song.pause();
	}

	public void resume (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la reprise du jeu */
		song.resume();
	}

	public void stop (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois à la fin du jeu */
		song.stop();
	}

	//@Override
	public void update (GameContainer container, StateBasedGame game, int delta) {
		/* Méthode exécutée environ 60 fois par seconde */
		
		for(int i = blocs.size()-1; i >= 0; i--) {
			/*if(block.collideWithPlayer((int)(player.getPosX()), (int)(player.getPosY()), player.getWidth(), player.getHeight())==true) {
				System.out.println("Collision !!!!"+player.getPosX()+" "+player.getWidth());
			}*/
			block = blocs.get(i);
			if(block!=null) {
				block.update(container, game, delta);
				if (block.getPosY()>this.height) {
					blocs.remove(i);
				}
			}
		}
				
	}

	//@Override
	public void render (GameContainer container, StateBasedGame game, Graphics context) {

		// context.setColor(Color.blue);
		// context.fillRect(this.posX,this.posY,this.width,this.height);
		// context.setColor(Color.white);
		// context.fillRect(this.posX,this.posY,2,this.height);
		// context.fillRect(this.posX+this.width/5,this.posY,2,this.height);
		// context.fillRect(posX+width*2/5,posY,2,height);
		// context.fillRect(posX+width*3/5,posY,2,height);
		// context.fillRect(posX+width*4/5,posY,2,height);
		// context.fillRect(posX+width,posY,2,height);
		// context.fillRect(posX,27*height/30, width, 2);
		background.draw(this.posX, this.posY);
		for(int i = blocs.size()-1; i >= 0; i--) {
			block = blocs.get(i);
			if(block!=null) {
				block.render(container, game, context);
			}			
		context.drawString("Score :\n"+score+"\n"+diff,0,0);
		}
	

		
		/* Méthode exécutée environs 60 fois par seconde */
		
		//time++;
		//context.setColor(Color.white);
		//context.drawString("Time : "+);

		
	}

	
	/*public boolean collideWithBlock(Block block) {
		int posXBlock=(int)(player.getPosX());
		int posYBlock=(int)(player.getPosY());
		return collide;
	}*/
}
