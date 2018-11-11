package io.github.keebler17.spaceinvaders;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Game;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.github.keebler17.spaceinvaders.screens.LoseScreen;
import io.github.keebler17.spaceinvaders.screens.MainScreen;
import io.github.keebler17.spaceinvaders.screens.WinScreen;

public class SpaceInvaders extends Game {

	public MainScreen main_screen;
	public WinScreen win_screen;
	public LoseScreen lose_screen;

	@Override
	public void create() {
		Global.init();
		Assets.load();
		try {
			
			File f = new File("file.json");
			if(f.exists())
				f.delete();
			f.createNewFile();
				
			PrintWriter s = new PrintWriter(f);
			s.println(json file goes here);
			s.flush();
			s.close();
			// Initialize the app with a service account, granting admin privileges
			FirebaseOptions options = new FirebaseOptions.Builder()
			    .setCredentials(GoogleCredentials.fromStream(new FileInputStream(f)))
			    .setDatabaseUrl("https://spaceinvaders-multiplayer.firebaseio.com/")
			    .build();
			FirebaseApp.initializeApp(options);

			// As an admin, the app has access to read and write all data, regardless of Security Rules
			DatabaseReference ref = FirebaseDatabase.getInstance()
			    .getReference(Global.keyStr);
			f.delete();
			ref.addListenerForSingleValueEvent(new ValueEventListener() {
				
			  @Override
			  public void onDataChange(DataSnapshot dataSnapshot) {
			    Object document = dataSnapshot.getValue();
			    System.out.println(document);
			  }

			  @Override
			  public void onCancelled(DatabaseError error) {
			  }
			});
			
			
			Global.ref = ref;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		win_screen = new WinScreen(this);
		main_screen = new MainScreen(this);
		lose_screen = new LoseScreen(this);
		
		setScreen(main_screen);
		
		
		Map<String, Object> test = new HashMap<String, Object>();
		
		test.put("test", "test");
		
		Global.ref.setValueAsync(test);
		Global.ref.push();
		System.out.println(Global.ref.getKey());
		
	}

	public void reset() {
		main_screen = new MainScreen(this);

		Global.init();
		setScreen(main_screen);
	}
	
	public void dispose() {
		main_screen.dispose();
	}

}
