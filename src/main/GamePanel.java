import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, Serializable {
	
	final int originalTileSize = 32; // 32x32 tile
	final int scale = 2;
	
	
	public final int tileSize = originalTileSize * scale; //64x64 tile
	public final int maxScreenCol = 28;
	public final int maxSreenRow = 14;
	final int screenWidth = tileSize * maxScreenCol; //1024 pixels
	final int screenHeight = tileSize * maxSreenRow; //768 pixels
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.white);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
