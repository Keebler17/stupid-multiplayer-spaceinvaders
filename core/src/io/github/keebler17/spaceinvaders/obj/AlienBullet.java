package io.github.keebler17.spaceinvaders.obj;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import io.github.keebler17.spaceinvaders.Global;

public class AlienBullet {

	int x;
	int y;
	int vel;

	public Hitbox hitbox;

	public boolean dead = false;

	public AlienBullet(int x, int y, int vel) {
		this.x = x;
		this.y = y;
		this.vel = vel;

		hitbox = new Hitbox(x, y, Global.BULLET_WIDTH, Global.BULLET_HEIGHT);
	}

	public void move() {
		y += vel;
		hitbox.y += vel;
		if (y >= 1920) {
			dead = true;
		}
	}

	public void draw(ShapeRenderer shape) {
		shape.begin(ShapeType.Filled);
		shape.setColor(1F, 1F, 1F, 1F);
		shape.rect(x, y, Global.BULLET_WIDTH, Global.BULLET_HEIGHT);
		shape.end();
	}

}
