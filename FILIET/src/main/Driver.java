package main;

import ui.mainUI;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("FILIET");
		frame.add(new mainUI());
		mainUI ui = new mainUI();
		frame.setSize(770,640);
        Toolkit t = Toolkit.getDefaultToolkit();
        int x = (int)((t.getScreenSize().getWidth() - frame.getWidth()) / 2);
        int y = (int)((t.getScreenSize().getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
