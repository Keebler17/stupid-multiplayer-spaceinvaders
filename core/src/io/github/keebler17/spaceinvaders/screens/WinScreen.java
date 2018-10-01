package io.github.keebler17.spaceinvaders.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.keebler17.spaceinvaders.SpaceInvaders;

public class WinScreen implements Screen {

	SpaceInvaders game;

	OrthographicCamera camera;
	SpriteBatch batch;
	
	BitmapFont font;

	public WinScreen(SpaceInvaders game) {
		this.game = game;

		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(true, 1920, 1080);
		
		font = new BitmapFont(Gdx.files.internal("arial35.fnt"), true);
	}

	@Override
	public void render(float delta) {
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
			font.setColor((float) 10, (float) 181, (float) 22, (float) 1);
			font.draw(batch, "YOU WIN!\nR to restart", 1920 / 2, 1080 / 2);
		batch.end();
		
		if(Gdx.input.isKeyPressed(Keys.R)) {
			batch.dispose();
			font.dispose();
			game.reset();
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {

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
	public void dispose() {

	}

}
