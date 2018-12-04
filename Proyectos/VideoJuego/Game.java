package psnbtech;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import psnbtech.entity.Asteroid;
import psnbtech.entity.Entity;
import psnbtech.entity.Player;
import psnbtech.util.Clock;

/**
 * The {@code Game} class is responsible for initializing and running the game.
 * @author Brendan Jones
 *
 */
public class Game extends JFrame {

	/**
	 * The number of frame per second the game should run at.
	 */
	private static final int FRAMES_PER_SECOND = 60;
	
	/**
	 * The number of nanoseconds that should elapse each frame. This is far more
	 * accurate than using milliseconds.
	 */
	private static final long FRAME_TIME = (long)(1000000000.0 / FRAMES_PER_SECOND);
	
	/**
	 * The number of frames that the "current level" message appears for.
	 */
	private static final int DISPLAY_LEVEL_LIMIT = 60;
	
	/**
	 * The value that {@code deathCooldown} will be set to upon player death.
	 */
	private static final int DEATH_COOLDOWN_LIMIT = 200;
	
	/**
	 * The value for {@code deathCooldown} that the Player respawns.
	 */
	private static final int RESPAWN_COOLDOWN_LIMIT = 100;
	
	/**
	 * The value for {@code deathCooldown} that the player becomes vulnerable,
	 * and regains the ability to fire.
	 */
	private static final int INVULN_COOLDOWN_LIMIT = 0;
	
	/**
	 * The value that {@code resetCooldown} is set to when the player loses.
	 */
	private static final int RESET_COOLDOWN_LIMIT = 120;
		
	/**
	 * The WorldPanel instance.
	 */
	private WorldPanel world;
	
	/**
	 * The Clock instance for handling the game updates.
	 */
	private Clock logicTimer;
	
	/**
	 * The Random instance for spawning entities.
	 */
	private Random random;
	
	/**
	 * The list of Entity objects that exist in the game world.
	 */
	private List<Entity> entities;
	
	/**
	 * The list of Entity objects that need to be added to the game world.
	 */
	private List<Entity> pendingEntities;
		
	/**
	 * The Player instance.
	 */
	private Player player;
	
	private int deathCooldown; // CD after death
	
	private int showLevelCooldown; // show the current level briefly after the previous level has been completed
	
	private int restartCooldown; // This timer adds a short delay that must expire before the game can be reset, giving the player time to react.
	
	private int score;
	
	private int lives; //Lives that the Player have
	
	private int level;
	
	private boolean isGameOver; //Whitout lives
	
	private boolean restartGame; //Restar the game press anything 
	
	private Game() {
		//Initialize the window's basic properties.
		super("Asteroids");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		
		add(this.world = new WorldPanel(this), BorderLayout.CENTER);//Create and add the WorldPanel instance to the window.
		
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				//Determine which key was pressed.
				switch(e.getKeyCode()) {
				
				//Indicate that we want to apply thrust to our ship.
				case KeyEvent.VK_W:
				case KeyEvent.VK_UP:
					if(!checkForRestart()) {
						player.setThrusting(true);
					}
					break;
					
				//Indicate that we want to rotate our ship to the left.
				case KeyEvent.VK_A:
				case KeyEvent.VK_LEFT:
					if(!checkForRestart()) {
						player.setRotateLeft(true);
					}
					break;
					
				//Indicate that we want to rotate our ship to the right.
				case KeyEvent.VK_D:
				case KeyEvent.VK_RIGHT:
					if(!checkForRestart()) {
						player.setRotateRight(true);
					}
					break;
					
				//Indicate that we want our ship to fire bullets.
				case KeyEvent.VK_SPACE:
					if(!checkForRestart()) {
						player.setFiring(true);
					}
					break;
					
				//Indicate that we want to pause the game.
				case KeyEvent.VK_P:
					if(!checkForRestart()) {
						logicTimer.setPaused(!logicTimer.isPaused());
					}
					break;
					
				//Handle all other key presses.
				default:
					checkForRestart();
					break;
					
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				switch(e.getKeyCode()) {

				//Indicate that we no long want to apply thrust to the ship.
				case KeyEvent.VK_W:
				case KeyEvent.VK_UP:
					player.setThrusting(false);
					break;
				
				//Indicate that we no longer want to rotate our ship left.
				case KeyEvent.VK_A:
				case KeyEvent.VK_LEFT:
					player.setRotateLeft(false);
					break;

				//Indicate that we no longer want to rotate our ship right.
				case KeyEvent.VK_D:
				case KeyEvent.VK_RIGHT:
					player.setRotateRight(false);
					break;
					
				//Indicate that we no long want to fire bullets.
				case KeyEvent.VK_SPACE:
					player.setFiring(false);
					break;	
				}
			}
		});
		
		//Resize the window to the correct size, position it in the center of the screen, and display it.
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Check the user input to see if the key should be used to restart the game.
	 * @return Whether or not the key restarted the game.
	 */
	private boolean checkForRestart() {
		boolean restart = (isGameOver && restartCooldown <= 0);
		if(restart) {
			restartGame = true;
		}
		return restart;
	}
	
	/**
	 * Starts the game running, and enters the main game loop.
	 */
	private void startGame() {
		//Initialize the engine's variables.
		this.random = new Random();
		this.entities = new LinkedList<Entity>();
		this.pendingEntities = new ArrayList<>();
		this.player = new Player();
		
		//Set the variables to their default values.
		resetGame();
		
		//Create the logic timer and enter the game loop.
		this.logicTimer = new Clock(FRAMES_PER_SECOND);
		while(true) {
			//Get the time that the frame started.
			long start = System.nanoTime();
			
			/*
			 * Update the game once for every cycle that has elapsed. If the game
			 * starts to fall behind, the game will update multiple times for each
			 * frame that is rendered in order to catch up.
			 */
			logicTimer.update();
			for(int i = 0; i < 5 && logicTimer.hasElapsedCycle(); i++) {
				updateGame();
			}
			
			world.repaint(); //Repaint the window.

			
			/*
			 * Determine how many nanoseconds we have left during this cycle,
			 * and sleep until it is time for the next frame to start.
			 */
			long delta = FRAME_TIME - (System.nanoTime() - start);
			if(delta > 0) {
				try {
					Thread.sleep(delta / 1000000L, (int) delta % 1000000);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Update the game entities and states.
	 */
	private void updateGame() {
		
		entities.addAll(pendingEntities);
		pendingEntities.clear();
		
		/*
		 * Decrement the restart cooldown.
		 */
		if(restartCooldown > 0) {
			this.restartCooldown--;
		}
		
		/*
		 * Decrement the show level cooldown.
		 */
		if(showLevelCooldown > 0) {
			this.showLevelCooldown--;
		}
		
		/*
		 * Restart the game if needed.
		 */
		if(isGameOver && restartGame) {
			resetGame();
		}
		
		/*
		 * If the game is currently in progress, and there are no enemies left alive,
		 * we prepare the next level.
		 */
		if(!isGameOver && areEnemiesDead()) {
			//Increment the current level, and set the show level cooldown.
			this.level++;
			this.showLevelCooldown = DISPLAY_LEVEL_LIMIT;
			
			//Reset the entity lists (to remove bullets).
			resetEntityLists();
			
			//Reset the player's entity to it's default state, and re-enable firing.
			player.reset();
			player.setFiringEnabled(true);
			
			//Add the asteroids to the world.
			for(int i = 0; i < level + 2; i++) {
				registerEntity(new Asteroid(random));
			}
		}
		
		/*
		 * If the player has recently died, decrement the cooldown and handle any
		 * special cases when they occur.
		 */
		if(deathCooldown > 0) {
			this.deathCooldown--;
			switch(deathCooldown) {
			
			//Reset the entity to it's default spawn state, and disable firing.
			case RESPAWN_COOLDOWN_LIMIT:
				player.reset();
				player.setFiringEnabled(false);
				break;
			
			//Re-enable the ability to fire, as we're no longer invulnerable.
			case INVULN_COOLDOWN_LIMIT:
				player.setFiringEnabled(true);
				break;
			
			}
		}
		
		/*
		 * Only run any of the update code if we're not currently displaying the
		 * level to the player.
		 */
		if(showLevelCooldown == 0) {
			
			//Iterate through the Entities and update their states.
			for(Entity entity : entities) {
				entity.update(this);
			}
                        
			for(int i = 0; i < entities.size(); i++) {
				Entity a = entities.get(i);
				for(int j = i + 1; j < entities.size(); j++) {
					Entity b = entities.get(j);
					if(i != j && a.checkCollision(b) && ((a != player && b != player) || deathCooldown <= INVULN_COOLDOWN_LIMIT)) {
						a.handleCollision(this, b);
						b.handleCollision(this, a);
					}
				}
			}
			
			//Loop through and remove "dead" entities.
			Iterator<Entity> iter = entities.iterator();
			while(iter.hasNext()) {
				if(iter.next().needsRemoval()) {
					iter.remove();
				}
			}
		}
	}
	
	/**
	 * Set the game's variables to their default values.
	 */
	private void resetGame() {
		this.score = 0;
		this.level = 0;
		this.lives = 3;
		this.deathCooldown = 0;
		this.isGameOver = false;
		this.restartGame = false;
		resetEntityLists();
	}
	
	/**
	 * Removes all entities, with the exception of the player, from the world.
	 */
	private void resetEntityLists() {
		pendingEntities.clear();
		entities.clear();
		entities.add(player);
	}
	
	/**
	 * Determines whether or not any asteroids still exist in the world.
	 * @return Whether or not all of the enemies are dead.
	 */
	private boolean areEnemiesDead() {
		for(Entity e : entities) {
			if(e.getClass() == Asteroid.class) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Updates the game state to reflect a player death.
	 */
	public void killPlayer() {
		//Decrement the number of lives that we still have.
		this.lives--;
		if(lives == 0) {
			this.isGameOver = true;
			this.restartCooldown = RESET_COOLDOWN_LIMIT;
			this.deathCooldown = Integer.MAX_VALUE;
		} else {
			this.deathCooldown = DEATH_COOLDOWN_LIMIT;
		}
		
		//Disable the ability to fire.
		player.setFiringEnabled(false);
	}
	
	/**
	 * Add to the current score.
	 * @param score The number of points to add.
	 */
	public void addScore(int score) {
		this.score += score;
	}
	
	/**
	 * Adds a new entity to the game world.
	 * @param entity The entity to add.
	 */
	public void registerEntity(Entity entity) {
		pendingEntities.add(entity);
	}
	
	/**
	 * Whether or not we are in the game over state.
	 * @return Whether or not the game is over.
	 */
	public boolean isGameOver() {
		return isGameOver;
	}
	
	/**
	 * Determines whether or not the player is invulnerable.
	 * @return Whether or not the player is invulnerable.
	 */
	public boolean isPlayerInvulnerable() {
		return (deathCooldown > INVULN_COOLDOWN_LIMIT);
	}
	
	/**
	 * Determines whether or not the player can be drawn.
	 * @return Whether or not the player can be drawn.
	 */
	public boolean canDrawPlayer() {
		return (deathCooldown <= RESPAWN_COOLDOWN_LIMIT);
	}
	
	/**
	 * Gets the current score.
	 * @return The current score.
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Gets the number of lives remaining.
	 * @return The number of lives remaining.
	 */
	public int getLives() {
		return lives;
	}
	
	/**
	 * Gets the current level.
	 * @return The current level.
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * Gets whether or not the game is paused.
	 * @return Whether or not the game is paused.
	 */
	public boolean isPaused() {
		return logicTimer.isPaused();
	}
	
	/**
	 * Gets whether or not the level is being shown.
	 * @return Whether or not the level is being shown.
	 */
	public boolean isShowingLevel() {
		return (showLevelCooldown > 0);
	}

	/**
	 * Gets the Random instance.
	 * @return The Random instance.
	 */
	public Random getRandom() {
		return random;
	}
	
	/**
	 * Gets the list of Entities in the world.
	 * @return The Entity list.
	 */
	public List<Entity> getEntities() {
		return entities;
	}
	
	/**
	 * Gets the Player instance.
	 * @return
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Entry point of the program. Creates and starts a new game instance.
	 * @param args Unused command line arguments.
	 */
	public static void main(String[] args) {
		Game game = new Game();
		game.startGame();
	}

}
