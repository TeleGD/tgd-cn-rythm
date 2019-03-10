package rythm;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class World extends BasicGameState {

	private int ID;
	private int state;
	private int width;
	private int height;
	private Track track;
	private Player player;

	private int stateMenu;
	private Image fondMenu;
	private Image bouton1;
	private Image bouton2;

	
	public World (int ID) {
		this.ID = ID;
		this.state = 0;
		this.stateMenu = 0;
	}

	@Override
	public int getID () {
		return this.ID;
	}

	@Override

	public void init (GameContainer container, StateBasedGame game) throws SlickException  {
		/* Méthode exécutée une unique fois au chargement du programme */

		this.width = container.getWidth ();
		this.height = container.getHeight ();
		System.out.println(width + " ; " + height);
		this.fondMenu = new Image("res\\images\\fond_retro.png");
		this.bouton1 = new Image ("res\\images\\bouton_jouer.png");
		this.bouton2 = new Image ("res\\images\\bouton_choisir.png");
	}

	@Override
	public void enter (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée à l'apparition de la page */
		if (this.state == 0) {
			this.play (container, game);
		} else if (this.state == 2) {
			this.resume (container, game);
		}
	}

	@Override
	public void leave (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée à la disparition de la page */
		if (this.state == 1) {
			this.pause (container, game);
		} else if(this.state == 3) {
			this.stop (container, game);
			this.state = 0; // TODO: remove
		}
	}

	@Override
	public void update (GameContainer container, StateBasedGame game, int delta) {
		/* Méthode exécutée environ 60 fis par seconde */
		Input input = container.getInput ();
		if (input.isKeyDown (Input.KEY_ESCAPE)) {
			this.setState(1);
			game.enterState (2, new FadeOutTransition (), new FadeInTransition ());
		}
		if(stateMenu == 0) {
			int mx = container.getInput().getMouseX();
            int my = container.getInput().getMouseY();
            boolean isMouseClicked = container.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
            if ( isMouseClicked && mx >=  this.width / 10 && mx <= 2* this.width / 10 && my >= this.height / 4 && my <=  this.height / 10 + this.height / 4  ) {
            	this.stateMenu = 1;
            }
            if ( isMouseClicked && mx >=  this.width / 10 && mx <= 2* this.width / 10 && my >= this.height / 4 + this.height / 10 && my <=  this.height / 4 + 2 *this.height / 10)   {
            	System.out.println("CLIIIIIIC SUR CHOSIR");
            }
		}
		else
		{
			track.update(container,  game,  delta);
			player.update(container,game,delta);
		}
	}

	@Override
	public void render (GameContainer container, StateBasedGame game, Graphics context) {

		/* Méthode exécutée environ 60 fois par seconde */
		if (stateMenu == 0) {
			context.drawImage(this.fondMenu.getScaledCopy(this.width, this.height), 0, 0 );
			
			// bouton jouer sa mère !
			context.drawImage(this.bouton1.getScaledCopy(this.width/10, this.height/10), this.width / 10, this.height / 4);
			
			//bouton choisir le gros son 
			context.drawImage(this.bouton2.getScaledCopy(this.width/10, this.height/10), this.width / 10   , this.height / 4 + height / 10);
			
			 
		}
		else {
			track.render(container,game,context);
			player.render(container, game, context);
		}
	}

	public void play (GameContainer container, StateBasedGame game){
		/* Méthode exécutée une unique fois au début du jeu */
		this.width = container.getWidth ();//
		this.height = container.getHeight ();

		track = new Track(this.width , this.height,1);//Réglage de la difficulté (0,1 ou 2)
		player = new Player(this.height, this.width);
	}

	@Override
	public void keyPressed(int key, char c) {
		player.keyPressed(key,c);
	}

	@Override
	public void keyReleased(int key, char c) {
		player.keyReleased(key, c);
	}

	public void pause (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la mise en pause du jeu */
	}

	public void resume (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la reprise du jeu */
	}

	public void stop (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois à la fin du jeu */
	}

	public void setState (int state) {
		this.state = state;
	}

	public int getState () {
		return this.state;
	}

	public int getWidth () {
		return this.width;
	}

	public int getHeight () {
		return this.height;
	}

}
