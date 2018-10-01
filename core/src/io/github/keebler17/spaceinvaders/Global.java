package io.github.keebler17.spaceinvaders;

import java.util.ArrayList;
import java.util.Random;

import com.google.firebase.database.DatabaseReference;

import io.github.keebler17.spaceinvaders.obj.AlienBullet;
import io.github.keebler17.spaceinvaders.obj.PlayerBullet;

public class Global {
	// This is a better way to do global variables... I did not know that last time.
	// with the static keyword, Global.variable can be used. No instances needed.
	
	public static ArrayList<AlienBullet> alienBullets;
	public static ArrayList<PlayerBullet> playerBullets;
	
	public static final int BULLET_WIDTH = 5;
	public static final int BULLET_HEIGHT = 10;
	
	public static int score = 0;
	
	public static final int ALIEN_WIDTH = 110;
	public static final int ALIEN_HEIGHT = 80;
	
	public static String keyStr = "";
	public static int key;
	
	public static DatabaseReference ref;
	
	public static int x;
	
	public static void init() {
		keyStr = "";
		alienBullets = new ArrayList<AlienBullet>();
		playerBullets = new ArrayList<PlayerBullet>();
		score = 0;
		Random r = new Random(System.currentTimeMillis());
		for(int i = 0; i < 8; i++) {
			keyStr += r.nextInt(9);
		}
		System.out.println(keyStr);
		key = Integer.valueOf(keyStr);
	}
	
	public static final int MAX_SCORE = 440;
}
