import java.awt.image.BufferedImage;

import rafgfxlib.GameFrame;
import rafgfxlib.GameHost;
import rafgfxlib.Util;

public class Main {

	public static void main(String[] args) {
		new GameFont(Strings.FONT_PATH);
		GameHost host = new GameHost(Strings.TITLE, Const.WIDTH, Const.HEIGHT);
		host.setUpdateRate(60);
		
		BufferedImage icon = Util.loadImage("icon.png");
		host.setIcon(icon);
		
		new SplashScreen(host);
		new Menu(host);
		new Game(host);
		new Leaderboard(host);
		new Options(host);
		
		host.setState(Strings.SPLASH);


	}

}
