package com.gcstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;

public class Moeda extends Entity{
	
	public BufferedImage[] Moeda;
	private int frames = 0, maxFrames = 10, index = 0, maxIndex = 3;

	public Moeda(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		
		Moeda = new BufferedImage[4];
		
		Moeda[0] = Game.spritesheet.getSprite(112, 0, 16, 16);
		Moeda[1] = Game.spritesheet.getSprite(128, 0, 16, 16);
		Moeda[2] = Game.spritesheet.getSprite(144, 0, 16, 16);
		Moeda[3] = Game.spritesheet.getSprite(96, 0, 16, 16);
	}
	
	public void tick() {
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex) {
				index = 0;
			}
		}
	}
	
	public void render(Graphics g) {
		sprite = Moeda[index];
		super.render(g);
	}

}
