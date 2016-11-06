package utils;


import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;

public class GameFont {
	
	
	public GameFont(String path) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		
		try{
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(path)));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	

}
