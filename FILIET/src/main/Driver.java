package main;

import java.awt.Toolkit;

import javax.swing.JFrame;

import ui.finalUI;
import ui.mainUI;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("FILIET");
		frame.add(new finalUI());
		finalUI ui = new finalUI();
		frame.setSize(770,640);
        Toolkit t = Toolkit.getDefaultToolkit();
        int x = (int)((t.getScreenSize().getWidth() - frame.getWidth()) / 2);
        int y = (int)((t.getScreenSize().getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
