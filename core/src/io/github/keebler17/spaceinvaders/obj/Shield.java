package io.github.keebler17.spaceinvaders.obj;

public class Shield {
	// 14 by 4
	boolean[] pixels = new boolean[56];
	
	boolean test = true;
	
	public Shield(int[] shield) {
		try {
			int j = 0;
			for(int i : shield) {
				if(i == 0) {
					pixels[j] = false;
				} else if(i == 1) {
					pixels[j] = true;
				} else {
					throw new ShieldException("Integer provided in array must be a 1 or 0!");
				}
				j++;
			}
		} catch(ShieldException e) {
			e.printStackTrace();
		}
	}
}
