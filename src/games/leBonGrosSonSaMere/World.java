package games.leBonGrosSonSaMere;

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

import app.AppLoader;

public class World extends BasicGameState {

	private int ID;
	private int state;
	private int width;
	private int height;
	private Track track;
	private Player player;
	private int stateMenu;

	private static Image fondMenu;
	private static Image bouton1;
	private static Image bouton2;

	static {
		World.fondMenu = AppLoader.loadPicture("/images/leBonGrosSonSaMere/fond_retro.png");
		World.bouton1 = AppLoader.loadPicture("/images/leBonGrosSonSaMere/bouton_jouer.png");
		World.bouton2 = AppLoader.loadPicture("/images/leBonGrosSonSaMere/bouton_choisir.png");
	}

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

	public void init (GameContainer container, StateBasedGame game) throws SlickException {
		/* Méthode exécutée une unique fois au chargement du programme */
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
			if ( isMouseClicked && mx >= this.width / 10 && mx <= 2* this.width / 10 && my >= this.height / 4 && my <= this.height / 10 + this.height / 4 ) {
				this.stateMenu = 1;
				track = new Track(this.width , this.height,1,player);
			}
			if ( isMouseClicked && mx >= this.width / 10 && mx <= 2* this.width / 10 && my >= this.height / 4 + this.height / 10 && my <= this.height / 4 + 2 *this.height / 10) {
				System.out.println("CLIIIIIIC SUR CHOSIR");
			}
		}
		else
		{
			track.update(container, game, delta);
			player.update(container,game,delta);
		}
	}

	private boolean collideWithPlayer() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void render (GameContainer container, StateBasedGame game, Graphics context) {

		/* Méthode exécutée environ 60 fois par seconde */
		if (stateMenu == 0) {
			context.drawImage(World.fondMenu.getScaledCopy(this.width, this.height), 0, 0 );

			// bouton jouer sa mère !
			context.drawImage(World.bouton1.getScaledCopy(this.width/10, this.height/10), this.width / 10, this.height / 4);

			//bouton choisir le gros son
			context.drawImage(World.bouton2.getScaledCopy(this.width/10, this.height/10), this.width / 10 , this.height / 4 + height / 10);


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
		track = new Track(this.width , this.height,1,player);//Réglage de la difficulté (0,1 ou 2)
		player = new Player(this.height, this.width);
		track.play(container, game);

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
		track.pause(container,game);
	}

	public void resume (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la reprise du jeu */
		track.resume(container,game);
	}

	public void stop (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois à la fin du jeu */
		track.stop(container,game);
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

	public int getPosXPlayer() {
		return (int)(player.getPosX());
	}

	/* public int getPosYPlayer() {
		return (int)(player.getPosY());
	}

	public int getWidthPlayer() {
		return player.getWidth();
	}

	public int getHeightPlayer() {
		return (int)(player.getHeight());
	} */

}
