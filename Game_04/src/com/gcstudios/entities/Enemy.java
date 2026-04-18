package com.gcstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;
import com.gcstudios.world.*;

public class Enemy extends Entity{
	
	public BufferedImage[] ENEMY1_RIGHT;
	public BufferedImage[] ENEMY1_LEFT;
	public static BufferedImage[] ENEMY1_RIGHT_M;
	public static BufferedImage[] ENEMY1_LEFT_M;
	private int frames = 0, maxFrames = 15, index = 0, maxIndex = 3;
	public int framesm = 0, maxFramesm = 10, indexm = 0, maxIndexm = 2;
	
	public boolean right = true, left = false;
	public int dir = 1;
	
	public int vida = 3;

	public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		
		ENEMY1_LEFT = new BufferedImage[4];
		ENEMY1_RIGHT = new BufferedImage[4];
		ENEMY1_LEFT_M = new BufferedImage[3];
		ENEMY1_RIGHT_M = new BufferedImage[3];
		
		ENEMY1_RIGHT[0] = Game.spritesheet.getSprite(32, 32, 16, 16);
		ENEMY1_RIGHT[1] = Game.spritesheet.getSprite(48, 32, 16, 16);
		ENEMY1_RIGHT[2] = Game.spritesheet.getSprite(64, 32, 16, 16);
		ENEMY1_RIGHT[3] = Game.spritesheet.getSprite(80, 32, 16, 16);
		
		ENEMY1_RIGHT_M[0] = Game.spritesheet.getSprite(96, 32, 16, 16);
		ENEMY1_RIGHT_M[1] = Game.spritesheet.getSprite(112, 32, 16, 16);
		ENEMY1_RIGHT_M[2] = Game.spritesheet.getSprite(128, 32, 16, 16);
		
		ENEMY1_LEFT[0] = Game.spritesheet.getSprite(32, 48, 16, 16);
		ENEMY1_LEFT[1] = Game.spritesheet.getSprite(48, 48, 16, 16);
		ENEMY1_LEFT[2] = Game.spritesheet.getSprite(64, 48, 16, 16);
		ENEMY1_LEFT[3] = Game.spritesheet.getSprite(80, 48, 16, 16);
		
		ENEMY1_LEFT_M[0] = Game.spritesheet.getSprite(96, 48, 16, 16);
		ENEMY1_LEFT_M[1] = Game.spritesheet.getSprite(112, 48, 16, 16);
		ENEMY1_LEFT_M[2] = Game.spritesheet.getSprite(128, 48, 16, 16);
		
	}
	
	public void tick() {
		if(World.isFree((int)x, (int)(y+1)) ) {
			y+=1;
		}
		if(right) {
			if(World.isFree((int)(x+speed), (int)y)) {
				x+=speed;
				dir = 1;
				//Para q eles nao caiam das plataformas
				if(World.isFree((int)(x+16), (int)y+1)) {
					right = false;
					left = true;
				}
			}else {
				right = false;
				left = true;
			}
		}
		if(left) {
			if(World.isFree((int)(x-speed), (int)y)) {
				x-=speed;
				dir = -1;
				if(World.isFree((int)(x-16), (int)y+1)) {
					right = true;
					left = false;
				}
			}else {
				right = true;
				left = false;
			}
		}
		
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex) {
				index = 0;
			}
		}
		framesm++;
		if(framesm == maxFramesm) {
			framesm = 0;
			indexm++;
			if(indexm > maxIndexm) {
				indexm = 0;
			}
		}
		
	}
	
	public void render(Graphics g) {
		if(dir == 1) {
			if(vida==0) {
				sprite = ENEMY1_RIGHT_M[indexm];
			}else {
				sprite = ENEMY1_RIGHT[index];
			}
		}else if(dir == -1) {
			if(vida==0) {
				sprite = ENEMY1_LEFT_M[indexm];
			}else {
				sprite = ENEMY1_LEFT[index];
			}
		}
		super.render(g);
	}

}
