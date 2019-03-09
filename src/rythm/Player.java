package rythm;

import java.util.List;
import java.util.ArrayList;

public class Player{
    private String name;
    private List<Track> tracks;
    private int posX;
    private int posY;
    private int speed;

    public Player(){
        this("Théodule")
    }

    public Player(String name){
        this.name = name;

    }
}