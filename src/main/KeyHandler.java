package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	public int Pcode;
	public int Rcode;
	public boolean STRG;
	public int dirP;
	public int dirR;
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		Pcode = e.getKeyCode();
		
		switch(Pcode) {
		case KeyEvent.VK_W:
			dirP = 1;
			break;
		case KeyEvent.VK_S:
			dirP = 2;
			break;
		case KeyEvent.VK_A:
			dirP = 3;
			break;
		case KeyEvent.VK_D:
			dirP = 4;
			break;
		}
		
		
		if (e.getKeyCode() == 17) {
			STRG = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		Rcode = e.getKeyCode();
		
		switch(Rcode) {
		case KeyEvent.VK_W:
			dirR = 1;
			break;
		case KeyEvent.VK_S:
			dirR = 2;
			break;
		case KeyEvent.VK_A:
			dirR = 3;
			break;
		case KeyEvent.VK_D:
			dirR = 4;
			break;
		}
		
		if(e.getKeyCode() == 17) {
			STRG = false;
		}
	}
}