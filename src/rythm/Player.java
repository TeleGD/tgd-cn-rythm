package rythm;

import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Graphics;
import java.util.ArrayList;
import java.math.abs;

public class Player{
    private String name;
    private List<Track> tracks;
    private double posX;
    private double posY;
    private double speedX;
    private double speedY;
    private boolean colliding;

    public Player(){
        this("Trévor Théodule");
    }

    public Player(String name){
        this.name = name;
        this.tracks = new ArrayList<>();
        this.posX = 0.;
        this.posY = 0.;
        this.speedX = 0.;
        this.speedY = 0.;
        this.colliding = false;
    }

    //@Override
	public void update (GameContainer container, StateBasedGame game, int delta) {
        /* Méthode exécutée environ 60 fois par seconde */
        double oldPosX = posX;
        double oldPosY = posY;

        double newSpeedX = speedX*delta;
        if(speedX < 0){
            newSpeedX = -speedX*delta;
        }
            double newSpeedY = speedY*delta;
        if(speedY < 0){
            newSpeedY = -speedY*delta;
        }

        if(!colliding){
            posX = newSpeedX;
            posY = newSpeedY;
        }
        else{
            posX = newSpeedX*0.3;
            posY = newSpeedY*0.3;
        }

        speedX = speedX*0.97f;
        speedY = speedY*0.97f;

        bordersCollision(oldPosX, oldPosY);
	}

	//@Override
	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		/* Méthode exécutée environ 60 fois par seconde */
    }
    
    private void bordersCollision(double oldX, double oldY){
        // On regarde si le joueur sors de la poste sur les cotés
        if(Math.abs(posX) > 5){
            if(!colliding){
                speedX = -speedX;
            }
            else{
                speedX = 0;
            }

            posX = oldX;
        }

        // On empêche le joueur d'aller trop loin ou trop haut
        if(posY < -1 || posY > 10){
            if(!colliding){
                speedY = -speedY;
            }
            else{
                speedY = 0;
            }

            posY = oldY;
        }
    }
}