package rythm;

import java.util.List;
import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import app.AppLoader;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Color;

public class Player {
    private String name;
    private List<Track> tracks;
    private int height, width, actPos;
    private float speed, posX, posY;
    private float[] pos;
    private boolean right, left, isMovingLeft, isMovingRight;
    private Image car;

    public Player(int height, int width){
        this("Trévor Théodule", height, width);
    }

    public Player(String name, int height, int width){
        this.name = name;
        this.tracks = new ArrayList<>();
        this.pos = new float[]{width*0.18f, width*0.34f, width*0.50f, width*0.66f, width*0.82f};
        this.posX = pos[2];
        this.posY = height - 110;
        this.height = height;
        this.width = width;
        this.actPos = 2;
        this.isMovingLeft = false;
        this.isMovingRight = false;
        this.left = false;
        this.right = false;
        this.car = AppLoader.loadPicture("/images/VOITURE.png");
    }

    //@Override
	public void update (GameContainer container, StateBasedGame game, int delta) {
        /* Méthode exécutée environ 60 fois par seconde */
		if(isMovingLeft && speed<0 && posX > pos[actPos]) {
            posX += speed*delta;
            if (posX <= pos[actPos]) {
                isMovingLeft = false;
                isMovingRight = false;
                posX = pos[actPos];
                speed = 0;
                left = false;
                right = false;
            }
		}
		
		if(isMovingRight && speed>0 && posX < pos[actPos]) {
            posX += speed*delta;
            if (posX >= pos[actPos]) {
                isMovingLeft = false;
                isMovingRight = false;
                posX = pos[actPos];
                speed = 0;
                left = false;
                right = false;
            }			
		}
	}

	//@Override
	public void render (GameContainer container, StateBasedGame game, Graphics context) {
        /* Méthode exécutée environ 60 fois par seconde */
        // context.setColor(Color.red);
        // context.fillRect(this.posX-100/2,(25*posY/30), 100, 220);

        car.draw(this.posX-100/2, 25*posY/30);
    }

    public void keyPressed(int key, char c) {
        if (key==Input.KEY_Q && !left && !isMovingLeft) {
            left=true;
            if (actPos-1>=0) {
                actPos -= 1;
                speed = -0.5f;
                isMovingLeft = true;
                isMovingRight = false;
            }
		}
		if (key==Input.KEY_D && !right && !isMovingRight) {
            right=true;
            if (actPos+1<=4) {
                actPos += 1;
                speed = 0.5f;
                isMovingRight = true;
                isMovingLeft = false;
            }
		}
	}

	public void keyReleased(int key, char c) {
		if (key==Input.KEY_Q) {
			left=false;
		}
		if (key==Input.KEY_D) {
			right=false;
		}		
    }
    
    public float getPosX(){
        return this.posX;
    }
}