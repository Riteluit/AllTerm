package psnbtech;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.Iterator;

import javax.swing.JPanel;

import psnbtech.entity.Entity;
import psnbtech.util.Vector2;

public class WorldPanel extends JPanel {

	/**
	 * The size of the world in pixels.
	 */
	public static final int WORLD_SIZE = 550;
	
	/**
	 * The font used for the large text.
	 */
	private static final Font TITLE_FONT = new Font("Dialog", Font.PLAIN, 25);
	
	/**
	 * The font used for the medium text.
	 */
	private static final Font SUBTITLE_FONT = new Font("Dialog", Font.PLAIN, 15);

	/**
	 * The Game instance.
	 */
	private Game game;
	
	/**
	 * Creates a new WorldPanel instance.
	 * @param game The Game instance.
	 */
	public WorldPanel(Game game) {
		this.game = game;

		//Set the window's size and background color.
		setPreferredSize(new Dimension(WORLD_SIZE, WORLD_SIZE));
		setBackground(Color.BLACK);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); //Required, otherwise rendering gets messy.
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(Color.BLUE); //Set the draw color to white.
		
		//Grab a reference to the current "identity" transformation, so we can reset for each object.
		AffineTransform identity = g2d.getTransform();
		
		/*
		 * Loop through each entity and draw it onto the window.
		 */
		Iterator<Entity> iter = game.getEntities().iterator();
		while(iter.hasNext()) {
			Entity entity = iter.next();
			/*
			 * We should only draw the player if it is not dead, so we need to
			 * ensure that the entity can be rendered.
			 */
			if(entity != game.getPlayer() || game.canDrawPlayer()) {
				Vector2 pos = entity.getPosition(); //Get the position of the entity.
				
				//Draw the entity at it's actual position, and reset the transformation.
				drawEntity(g2d, entity, pos.x, pos.y);
				g2d.setTransform(identity);

                                //Here we need to determine whether or not the entity is close enough
				double radius = entity.getCollisionRadius();
				double x = (pos.x < radius) ? pos.x + WORLD_SIZE
						: (pos.x > WORLD_SIZE - radius) ? pos.x - WORLD_SIZE : pos.x;
				double y = (pos.y < radius) ? pos.y + WORLD_SIZE
						: (pos.y > WORLD_SIZE - radius) ? pos.y - WORLD_SIZE : pos.y;
				
				//Draw the entity at it's wrapped position, and reset the transformation.
				if(x != pos.x || y != pos.y) {
					drawEntity(g2d, entity, x, y);
					g2d.setTransform(identity);
				}
			}	
		}
		
		//Draw the score string in the top left corner if we are still playing.
		if(!game.isGameOver()) {
			g.drawString("Score: " + game.getScore(), 10, 15);
		}
		
		//Draw some overlay text depending on the game state.
		if(game.isGameOver()) {
			drawTextCentered("Game Over", TITLE_FONT, g2d, -25);
			drawTextCentered("Final Score: " + game.getScore(), SUBTITLE_FONT, g2d, 10);
		} else if(game.isPaused()) {
			drawTextCentered("Paused", TITLE_FONT, g2d, -25);
		} else if(game.isShowingLevel()) {
			drawTextCentered("Level: " + game.getLevel(), TITLE_FONT, g2d, -25);
		}
		
		//Draw a ship for each life the player has remaining.
		g2d.translate(15, 30);
		g2d.scale(0.85, 0.85);
		for(int i = 0; i < game.getLives(); i++) {
			g2d.drawLine(-8, 10, 0, -10);
			g2d.drawLine(8, 10, 0, -10);
			g2d.drawLine(-6, 6, 6, 6);
			g2d.translate(30, 0);
		}
	}
	

	private void drawTextCentered(String text, Font font, Graphics2D g, int y) {
		g.setFont(font);
		g.drawString(text, WORLD_SIZE / 2 - g.getFontMetrics().stringWidth(text) / 2, WORLD_SIZE / 2 + y);
	}
	
	private void drawEntity(Graphics2D g2d, Entity entity, double x, double y) {
		g2d.translate(x, y);
		double rotation = entity.getRotation();
		if(rotation != 0.0f) {
			g2d.rotate(entity.getRotation());
		}
		entity.draw(g2d, game);
	}

}
