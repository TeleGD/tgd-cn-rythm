package rythm;

import java.util.List;
import java.util.ArrayList;

public class Player{
    private String name;
    private List<Track> tracks;
    private double posX;
    private double posY;
    private double speed;

    public Player(){
        this("Trévor Théodule");
    }

    public Player(String name){
        this.name = name;
        this.tracks = new ArrayList<>();
        this.posX = 0.;
        this.posY = 0.;
        this.speed = 0.;
    }

    @Override
	public void update (GameContainer container, StateBasedGame game, int delta) {
		/* Méthode exécutée environ 60 fois par seconde */
	}

	@Override
	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		/* Méthode exécutée environ 60 fois par seconde */
	}
}