
import processing.core.PApplet;
import processing.core.PImage;
	

public class Juego extends PApplet{
	
	
	
	//Position of cohete
	int xd = 500;
	int yd = 500;
	int vd = 4;
		
	int score = 0;
		
	//Declarate the sprites of the game
	PImage background;
	PImage rocket;
		
		public void settings() {
			  size(1200, 700);//Set the dimensions of the window
			  background = loadImage("C:\\Users\\elisa gomez\\Downloads\\espacio-bicubic.png"); //Load the background
			//Load the sprietes of the rocket
			  rocket = loadImage ("C:\\Users\\elisa gomez\\Downloads\\cohete.png");
                          noLoop();
		}
		
                
		public void draw() {
			//background(0,0,0);
			image(background,0,0);//Set the background image in the position 0,0
			image (rocket, xd, yd);//Set the sprite of the duck in the position x,y
			
			text("Score = " + score, 1100, 40); 
						
			//xd += vd; //Move the dog in a horizontal sense
                        if(key == 'a')
                    xd = xd + 5;
		else
			if(key == 'd')
				yd = yd + 5;
                        try
		{
			Thread.sleep(5);
		}
		catch(Exception ex) {}
		}
		
		public void keyPressed(){
		if(key == 'a')
                    xd = xd + 5;
		else
			if(key == 'd')
				yd = yd + 5;
			
	}	
	public static void main(String[] args){
		String[] appletArgs = new String[] {"juego.Juego"};
		PApplet.main(appletArgs);
	}
}

