package com.gcstudios.entities;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;
import com.gcstudios.world.Camera;
import com.gcstudios.world.World;


public class Player extends Entity{
	
	public boolean right, left;
	//Pulo que aperta 1 vez e pula direto nao precisa ficar segurando
	public boolean jump = false, isJumping = false;
	public int jumpHeight = 30, jumpFrames = 0;
	
	public int dir = 1;
	private double gravity = 0.3;
	private double vspd = 0;
	
	public static double life = 100,maxLife = 100; 
	public static int currentCoins = 0, maxCoins = 0; 
	
	public BufferedImage[] sprite_left;
	public BufferedImage[] sprite_right;
	public BufferedImage[] sprite_left_p;
	public BufferedImage[] sprite_right_p;

	private int frames = 0, maxFrames = 10, index = 0, maxIndex = 3;
	private int framesp = 0, maxFramesp = 25, indexp = 0, maxIndexp = 1;
	public boolean moved = false;	
	
	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
		
		sprite_left = new BufferedImage[4];
		sprite_right = new BufferedImage[4];
		sprite_left_p = new BufferedImage[2];
		sprite_right_p = new BufferedImage[2];
		
		sprite_right[0] = Game.spritesheet.getSprite(32, 0, 16, 16);
		sprite_right[1] = Game.spritesheet.getSprite(48, 0, 16, 16);
		sprite_right[2] = Game.spritesheet.getSprite(64, 0, 16, 16);
		sprite_right[3] = Game.spritesheet.getSprite(80, 0, 16, 16);
		
		sprite_left[0] = Game.spritesheet.getSprite(32, 16, 16, 16);
		sprite_left[1] = Game.spritesheet.getSprite(48, 16, 16, 16);
		sprite_left[2] = Game.spritesheet.getSprite(64, 16, 16, 16);
		sprite_left[3] = Game.spritesheet.getSprite(80, 16, 16, 16);
		
		sprite_right_p[0] = Game.spritesheet.getSprite(32, 0, 16, 16);
		sprite_right_p[1] = Game.spritesheet.getSprite(0, 16, 16, 16);
		sprite_left_p[0] = Game.spritesheet.getSprite(32, 16, 16, 16);
		sprite_left_p[1] = Game.spritesheet.getSprite(0, 32, 16, 16);
	}
	
	public void tick(){
		depth = 2;
		//Gravidade, Caindo
		/*if(World.isFree((int)x, (int)(y+gravity)) && isJumping == false) {
			y+=gravity;
			for(int i = 0; i < Game.entities.size();i++) {
				Entity e = Game.entities.get(i);
				if(e instanceof Enemy) {
					if(Entity.isColidding(this, e)) {
						//Logica de pular no inimigo e causar dano
						isJumping = true;
						jumpHeight = 25;
						
						//Remover vida do Inimigo
						((Enemy)e).vida--;
						
						if(((Enemy)e).vida == 0) {
							//Destruir inimigo
							Game.entities.remove(i);
							break;
						}
					}
				}
			}
		}*/
		
		//NOVO SISTEMA DE GRAVIDADE MAIS FLUIDO
		vspd+=gravity;
		if(!World.isFree((int)x, (int)(y+1)) && jump) {
			vspd = -5;
			jump = false;
		}
		if(!World.isFree((int)x, (int)(y+vspd))) {
			int signVsp = 0;
			if(vspd >= 0) {
				signVsp = 1;
			}else {
				signVsp = -1;
			}
			while(World.isFree((int)x, (int)(y+signVsp))) {
				y = y+signVsp;
			}
			vspd=0;
			for(int i = 0; i < Game.entities.size();i++) {
				Entity e = Game.entities.get(i);
				if(e instanceof Enemy) {
					if(Entity.isColidding(this, e)) {
						//Logica de pular no inimigo e causar dano
						//jump = true;
						vspd=-3;
						
						//Remover vida do Inimigo
						((Enemy)e).vida--;
						
						if(((Enemy)e).vida == 0) {
							//Destruir inimigo
							Game.entities.remove(i);
							break;
						}
					}
				}
			}
		}
		
		y = y+vspd;
		
		if(right && World.isFree((int)(x+speed), (int)y)) {
			x+=speed;
			dir = 1;
			moved = true;
		}else if (left && World.isFree((int)(x-speed), (int)y)) {
			x-=speed;
			dir = -1;
			moved = true;
		}
		//Pulo que aperta 1 vez e pula direto nao precisa ficar segurando
		//E que nao fica pulando infinitamente se segurar a tecla
		//Sistema de pulo com a gravidade antiga
		/*if(jump) {
			//So vai conseguir pular se no pixel abaixo for um bloc de chao
			if(!World.isFree(getX(), getY()+1)) {
				isJumping = true;
				jumpHeight = 40;
			}else {
				jump = false;
			}
		}
		
		if(isJumping) {
			if(World.isFree(getX(), getY()-2)) {
				y-=2;
				jumpFrames+=2;
				if(jumpFrames >= jumpHeight) {
					isJumping = false;
					jump = false;
					jumpFrames = 0;
				}
			}else {
				isJumping = false;
				jump = false;
				jumpFrames = 0;
			}
		}*/
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) {
					index = 0;
				}
			}
		}else {
			framesp++;
			if(framesp == maxFramesp) {
				framesp = 0;
				indexp++;
				if(indexp > maxIndexp) {
					indexp = 0;
				}
			}
		}
		//Detectar Dano
		for(int i = 0; i < Game.entities.size();i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Enemy) {
				if(Entity.isColidding(this, e)) {
					if(Entity.rand.nextInt(100) < 5)
						life--;
				}
			}
		}
		//Coletar a Moeda
		for(int i = 0; i < Game.entities.size();i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Moeda) {
				if(Entity.isColidding(this, e)) {
					currentCoins++;
					Game.entities.remove(i);
				}
			}
		}
		
		Camera.x = Camera.clamp((int)x	- Game.WIDTH / 2, 0, World.WIDTH *16 - Game.WIDTH);
		Camera.y = Camera.clamp((int)y	- Game.HEIGHT / 2, 0, World.HEIGHT *16 - Game.HEIGHT);
		
	}
	
	public void render(Graphics g) {
		if(moved) {
			if(dir == 1) {
				sprite = sprite_right[index];
			}else if (dir == -1) {
				sprite = sprite_left[index];
			}
		}else {
			if(dir == 1) {
				sprite = sprite_right_p[indexp];
			}else if (dir == -1) {
				sprite = sprite_left_p[indexp];
			}
		}
		
		super.render(g);
	}
	

}
