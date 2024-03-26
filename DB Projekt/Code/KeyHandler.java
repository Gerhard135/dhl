package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	public int code;
	public int IP;
	public int IR;
	public char nameChar;
	public boolean start = false;
	public boolean space = false;
	public int np = 0;
	
	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyChar() != '\u0000' && nameChar == '\u0000' && e.getKeyChar() != '\n') {	
			nameChar = e.getKeyChar();
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		code = e.getKeyCode();
		
		switch(code) {
		case KeyEvent.VK_A:
			IP = 1;
			break;
		case KeyEvent.VK_S:
			IP = 2;
			break;
		case KeyEvent.VK_D:
			IP = 3;
			break;
		case KeyEvent.VK_ENTER:
			start = true;
			break;
		case KeyEvent.VK_SPACE:
			space = true;
			break;
		case KeyEvent.VK_1:
			np = 1;
			break;
		case KeyEvent.VK_2:
			np = 2;
			break;
		case KeyEvent.VK_LEFT:
			IR = 1;
			break;
		case KeyEvent.VK_DOWN:
			IR = 2;
			break;
		case KeyEvent.VK_RIGHT:
			IR = 3;
			break;
		}
		

		
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}