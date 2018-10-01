package io.github.keebler17.spaceinvaders.obj;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.keebler17.spaceinvaders.Global;

public class Player implements java.io.Serializable {
	private static final long serialVersionUID = -2250085060987149441L;
	
	public int x;
	public int y;
	public int vel;
	public int lives;

	Sprite sprite;

	// two boxes to avoid https://Keebler17.github.io/Games/FlappyBird/index.html

	public Hitbox topBox;
	public Hitbox bottomBox;

	public Player(int x, int y, int vel, int lives, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.vel = vel;
		this.lives = lives;

		this.sprite = sprite;

		bottomBox = new Hitbox(x, y + 30, 130, 50);
		topBox = new Hitbox(x + 50, y, 30, 30);
	}

	public void move(boolean left) {
		if (left) {
			if (x > 0)
				x -= vel;
				topBox.x -= vel;
				bottomBox.x -= vel;
		}
		else {
			if (x < 1920 - sprite.getWidth())
				x += vel;
				topBox.x += vel;
				bottomBox.x += vel;
		}
	}

	public void draw(SpriteBatch batch) {
		batch.draw(sprite, x, y);
	}

	public void onHit(Object o) {
		lives--;
		if(o instanceof AlienBullet) {
			((AlienBullet) o).dead = true;
		}
	}

	public void shoot() {
		PlayerBullet bullet = new PlayerBullet(x + 70, y, 5);
		Global.playerBullets.add(bullet);
	}
}
