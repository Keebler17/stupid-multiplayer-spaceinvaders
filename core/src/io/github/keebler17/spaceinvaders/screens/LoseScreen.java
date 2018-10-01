package io.github.keebler17.spaceinvaders.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.keebler17.spaceinvaders.SpaceInvaders;

public class LoseScreen implements Screen {

	BitmapFont font;
	
	OrthographicCamera camera;
	SpriteBatch batch;
	
	SpaceInvaders game;
	
	public LoseScreen(SpaceInvaders game) {
		camera = new OrthographicCamera();
		camera.setToOrtho(true, 1920, 1080);
		
		batch = new SpriteBatch();
		
		font = new BitmapFont(Gdx.files.internal("arial35.fnt"), true);
		
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
			font.draw(batch, "YOU LOSE!\nR to restart", 1920/2, 1080/2);
		batch.end();
		
		if(Gdx.input.isKeyJustPressed(Keys.R)) { // resets the game
			batch.dispose();
			font.dispose();
			game.reset();
		}
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void show() {
		
	}
	
	@Override
	public void dispose() {
		
	}

}
