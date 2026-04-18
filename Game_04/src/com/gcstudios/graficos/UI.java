package com.gcstudios.graficos;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.gcstudios.entities.Player;
import com.gcstudios.main.Game;

public class UI {

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(10, 10,250, 30);
		g.setColor(new Color(0,206,6));
		g.fillRect(10, 10, (int)((Player.life/100)*250), 30);
		g.setColor(Color.white);
		g.drawRect(10, 10, 250, 30);//Desenhar borda
		
		g.setColor(Color.white);
		g.setFont(new Font("arial",Font.BOLD,25));
		g.drawString((int)Player.life+" / "+(int)Player.maxLife, 80, 34);
		
		g.drawString("Moedas: "+(int)Player.currentCoins+" / "+(int)Player.maxCoins, (Game.WIDTH*Game.SCALE) - 200, 30);
	}
	
}
