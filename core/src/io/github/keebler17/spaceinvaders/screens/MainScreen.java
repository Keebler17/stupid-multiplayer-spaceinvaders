package io.github.keebler17.spaceinvaders.screens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.ValueEventListener;

import io.github.keebler17.spaceinvaders.Assets;
import io.github.keebler17.spaceinvaders.Global;
import io.github.keebler17.spaceinvaders.SpaceInvaders;
import io.github.keebler17.spaceinvaders.obj.Alien;
import io.github.keebler17.spaceinvaders.obj.AlienBullet;
import io.github.keebler17.spaceinvaders.obj.Player;
import io.github.keebler17.spaceinvaders.obj.PlayerBullet;

public class MainScreen implements Screen {
	SpaceInvaders game;

	DatabaseReference ref = Global.ref;
	DatabaseReference root = ref.getParent();
	OrthographicCamera camera;
	SpriteBatch batch;
	ShapeRenderer shape;

	Player player = new Player(1920 / 2, 960, 5, 3, Assets.player);

	ArrayList<Alien> aliens = new ArrayList<Alien>();

	ArrayList<PlayerBullet> receivedBullets = new ArrayList<PlayerBullet>();

	ArrayList<Player> receivedPlayers = new ArrayList<Player>();

	boolean left = true;
	int cycles = 0;

	int x;
	int y;
	int vel;
	
	int velocity = 0;

	BitmapFont font;

	int timeSeconds = 0;

	double curr;
	double last;

	boolean kill = false;

	public int toInt(DataSnapshot s) {
		return Integer.valueOf(s.getValue().toString()); // it returns as object, if i cast it then it is 0. strange.
	}
	
	public MainScreen(SpaceInvaders game) {
		this.game = game;

		root.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snap) {
				receivedBullets.clear();
				receivedPlayers.clear();
				for (DataSnapshot s : snap.getChildren()) {
					if(s.getKey().equals("aliens")) {
						x = toInt(s.child("x"));
						y = toInt(s.child("x"));	
					} else if(s.getKey() != Global.keyStr) {
						int px = toInt(s.child("x"));
						int py = toInt(s.child("y"));
						int pl = toInt(s.child("lives"));
						receivedPlayers.add(new Player(px, py, velocity, pl, Assets.player));
						
						for(DataSnapshot s2 : s.getChildren()) {
							if(!s2.getKey().equals("x") && !s2.getKey().equals("y") && !s2.getKey().equals("lives")) {
								if(s2.child("x") != null) {
									int bx = toInt(s2.child("x"));
									//int by = toInt(s2.child("y"));
									//receivedBullets.add(new PlayerBullet(bx, by, velocity));
								}
							}
						}
					}
				}
			}

			@Override
			public void onCancelled(DatabaseError error) {
				System.out.println(error.getMessage());
			}

		});

		camera = new OrthographicCamera();
		batch = new SpriteBatch();
		shape = new ShapeRenderer();

		camera.setToOrtho(true, 1920, 1080);

		x = Global.ALIEN_WIDTH;
		y = Global.ALIEN_HEIGHT;

		for (int i = 0; i < 44; i++) {
			aliens.add(new Alien(x, y, 2, 10, Assets.alien1));

			if (x + Global.ALIEN_WIDTH <= 1920 - Global.ALIEN_WIDTH) {
				x += Global.ALIEN_WIDTH * 1.5; // adds an alien width and a half to the x, so the
																	// aliens are now spaced by half an alien width
			} else {
				x = Global.ALIEN_WIDTH; // resets the x back to the left
				y += Global.ALIEN_WIDTH / 1.2f; // adds to the y for the next row
			}

			// I just cut and pasted old code and it actually works. No errors. Wow.
			// later note: added global.name
		}

		x = aliens.get(0).x;
		y = aliens.get(0).y;
		vel = aliens.get(0).vel;

		font = new BitmapFont(Gdx.files.internal("arial35.fnt"), true);

		last = System.currentTimeMillis();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0F, 0F, 0F, 0F);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

		camera.update();

		batch.setProjectionMatrix(camera.combined);
		shape.setProjectionMatrix(camera.combined);

		curr = System.currentTimeMillis();

		if (curr > last + 1000) {
			last = curr;
			timeSeconds++;
		}

		batch.begin();

		player.draw(batch);

		for (Alien alien : aliens) {
			alien.draw(batch);
		}

		font.draw(batch, "Score: " + Global.score, 0, 0);
		font.draw(batch, "Time: " + timeSeconds, 300, 0);

		for (int i = 0; i < receivedPlayers.size(); i++) {
			receivedPlayers.get(i).draw(batch);
		}

		batch.end();

		update();

		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		drawLives();
		batch.end();

		push();
	}

	public void push() {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("x", player.x);
		map.put("y", player.y);
		map.put("lives", player.lives);
		map.put("dead", false);
		int i = 0;
		for (PlayerBullet b : Global.playerBullets) {
			map.put("bullet" + i, b);
			i++;
		}

		ref.setValueAsync(map);
		ref.push();
	}

	public void update() {
		
		moveAliens();
		
		getPlayerInput();
		removeDeadObjects();

		checkCollision();

		moveBullets();
		drawBullets();

		shootAliens();

		if (Gdx.input.isKeyJustPressed(Keys.R)) { // resets the game
			batch.dispose();
			shape.dispose();
			font.dispose();
			game.reset();
		}

		if (Global.score == Global.MAX_SCORE) {
			cycles++;
		}

		if (cycles == 5 && !kill) {
			game.setScreen(game.win_screen);
		}

		if (player.lives <= 0 && !kill) {
			kill = true;
			cycles = 0;
		}

		if (kill) {
			cycles++;
			if (cycles >= 2) {
				game.setScreen(game.lose_screen);
			}
		}
	}
	
	public void moveAliens() {
		int x2 = x;
		int y2 = y;
		
		int column = 0;
		int row = 1;
		for(Alien alien : aliens) {
			
			alien.goTo(x2, y2);
			
			x2 += Global.ALIEN_WIDTH * 1.5;
			
			column++;
			if(column % 11 == 0) {
				y2 += Global.ALIEN_WIDTH / 1.2f;
				x2 = x;
				row++;
			}
		}
	}

	public void drawLives() {
		if (player.lives > 0) {
			batch.draw(Assets.player, 1760, 0);
		}

		if (player.lives > 1) {
			batch.draw(Assets.player, 1600, 0);
		}

		if (player.lives > 2) {
			batch.draw(Assets.player, 1440, 0);
		}
	}

	public void shootAliens() {
		for (Alien alien : aliens) {
			if (new Random().nextInt(750) == 0) {
				// alien.shoot();
			}
		}
	}

	public void checkCollision() {
		for (PlayerBullet bullet : Global.playerBullets) {
			for (Alien alien : aliens) {
				if (bullet.hitbox.overlaps(alien.hitbox)) { // alien gets hit by player bullet
					alien.onHit(bullet);
				}
			}
		}

		for (AlienBullet bullet : Global.alienBullets) {
			if (bullet.hitbox.overlaps(player.topBox) || bullet.hitbox.overlaps(player.bottomBox)) { // player gets hit
																										// by alien
																										// bullet
				player.onHit(bullet);
				System.out.println(player.lives);
			}
		}
	}

	public void removeDeadObjects() {
		for (int i = 0; i < Global.playerBullets.size(); i++) {
			if (Global.playerBullets.get(i).dead) {
				Global.playerBullets.remove(Global.playerBullets.get(i));
			}
		}

		for (int i = 0; i < Global.alienBullets.size(); i++) {
			if (Global.alienBullets.get(i).dead) {
				Global.alienBullets.remove(Global.alienBullets.get(i));
			}
		}

		for (int i = 0; i < aliens.size(); i++) {
			if (aliens.get(i).dead) {
				root.child("status").child("alien" + i).setValueAsync(false);
				root.child("status").child("alien" + i).push();
				//aliens.remove(aliens.get(i));
			}
		}
	}

	public void drawBullets() {
		for (PlayerBullet bullet : Global.playerBullets) {
			bullet.draw(shape);
		}

		for(int i = 0; i < receivedBullets.size(); i++) {
			receivedBullets.get(i).draw(shape);
		}
		
		for (AlienBullet bullet : Global.alienBullets) {
			bullet.draw(shape);
		}
	}

	public void moveBullets() {
		for (PlayerBullet bullet : Global.playerBullets) {
			bullet.move();
		}

		for (AlienBullet bullet : Global.alienBullets) {
			bullet.move();
		}
	}

	public void getPlayerInput() {
		if (Gdx.input.isKeyPressed(Keys.LEFT)) { // left key pressed
			player.move(true); // Argument is a boolean for if the player should move left or right.
		}

		if (Gdx.input.isKeyPressed(Keys.RIGHT)) { // right key pressed
			player.move(false);
		}

		if (Gdx.input.isKeyJustPressed(Keys.SPACE) || Gdx.input.isKeyJustPressed(Keys.SPACE)
				|| Gdx.input.isKeyJustPressed(Keys.Z)) {
			player.shoot();
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
		ref.removeValue(new CompletionListener() {

			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				if(error != null) {
					System.out.println(error.toString());
				} else ref.push();
			}
			
		});
		ref.push();
	}
}
