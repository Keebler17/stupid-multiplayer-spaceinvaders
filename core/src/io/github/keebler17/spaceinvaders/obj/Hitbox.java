package io.github.keebler17.spaceinvaders.obj;

import java.io.Serializable;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;;

public class Hitbox extends Rectangle implements Serializable {
	
	private static final long serialVersionUID = 8453247326345580904L;

	public Hitbox(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	public void draw(ShapeRenderer shape) {
		shape.begin(ShapeType.Filled);
		shape.rect(x, y, width, height);
		shape.end();
	}
}
