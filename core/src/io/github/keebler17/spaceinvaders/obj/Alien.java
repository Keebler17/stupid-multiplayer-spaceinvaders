package io.github.keebler17.spaceinvaders.obj;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.keebler17.spaceinvaders.Global;

public class Alien {

	public int x;
	public int y;
	public int vel;

	public int value;

	public Sprite sprite;

	public Hitbox hitbox;

	public boolean dead = false;

	public Alien(int x, int y, int vel, int value, Sprite sprite) { // sprite field so i can implement points
		this.x = x;
		this.y = y;
		this.vel = vel;
		this.value = value;
		this.sprite = sprite;

		// NOTE: The alien sprites are all entirely the alien, in a box the perfect
		// size, so I can just get the sprite size.
		hitbox = new Hitbox(x, y, (int) sprite.getWidth(), (int) sprite.getHeight());
	}

	public void shoot() {
		AlienBullet bullet = new AlienBullet(x + 70, y, 5);
		Global.alienBullets.add(bullet);
	}

	public void move(boolean left) {
		if (left) {
			x -= vel;
		} else {
			x += vel;
		}

		hitbox.x = x;
		hitbox.y = y;
	}

	public void goTo(int x, int y) {
		this.x = x;
		hitbox.x = x;
		hitbox.y = y;
	}

	public void onHit(Object o) {
		if (!dead) {
			if (o instanceof PlayerBullet) {
				dead = true;
				((PlayerBullet) o).dead = true;
				Global.score += value;
			}
		}
	}

	public void draw(SpriteBatch batch) {
		if (!dead) {
			batch.draw(sprite, x, y);
		}
	}

}
