package io.github.keebler17.spaceinvaders.obj;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import io.github.keebler17.spaceinvaders.Global;

public class PlayerBullet {
	public int x;
	public int y;
	public int vel;

	public Hitbox hitbox;

	public boolean dead = false;

	public PlayerBullet(int x, int y, int vel) {
		this.x = x;
		this.y = y;
		this.vel = vel;

		hitbox = new Hitbox(x, y, Global.BULLET_WIDTH, Global.BULLET_HEIGHT);
	}

	public PlayerBullet() {}

	public void move() {
		y -= vel;

		if (y <= 0) {
			dead = true;
		}

		hitbox.y = y;
	}

	public void draw(ShapeRenderer shape) {
		shape.begin(ShapeType.Filled);
		shape.setColor(0F, 0.75F, 0F, 0F);
		shape.rect(x, y, Global.BULLET_WIDTH, Global.BULLET_HEIGHT);
		shape.end();
	}

}
