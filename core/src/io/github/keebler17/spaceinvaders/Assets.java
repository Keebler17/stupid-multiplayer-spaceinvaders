package io.github.keebler17.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Assets {
	public static Texture player_t;
	public static Sprite player;
	
	public static Texture alien1_t;
	public static Sprite alien1;
	
	public static void load() {
		player_t = new Texture(Gdx.files.internal("cannon-small.png"));
		player_t.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		player = new Sprite(player_t);
		player.flip(false, true);
		
		alien1_t = new Texture(Gdx.files.internal("invader1-small.png"));
		alien1_t.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		alien1 = new Sprite(alien1_t);
		alien1.flip(false, true);
	}
}
