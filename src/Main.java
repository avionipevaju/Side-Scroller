import java.awt.image.BufferedImage;

import rafgfxlib.GameFrame;
import rafgfxlib.GameHost;
import rafgfxlib.Util;

public class Main {

	public static void main(String[] args) {
		//System.out.println("Igrica kida!");
		//GameFrame gf = new SideScroller("Kid A - The Game", 800, 600);
		//gf.initGameWindow();
		
		new GameFont(Strings.FONT_PATH);
		GameHost host = new GameHost(Strings.TITLE, Const.WIDTH, Const.HEIGHT);
		host.setUpdateRate(60);
		
		BufferedImage icon = Util.loadImage("icon.png");
		host.setIcon(icon);
		
		new Menu(host);
		new Game(host);
		
		host.setState(Strings.MENU);


	}

}
