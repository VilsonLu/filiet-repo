package main;

import ui.finalUI;
import java.awt.Dimension;

import java.awt.Toolkit;

import javax.swing.JFrame;

import ui.finalUI;
import ui.mainUI;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("FILIET: An Information Extraction System for Filipino Disaster-Related Tweets");
		frame.add(new finalUI());
		finalUI ui = new finalUI();
		frame.setSize(885,535);
        Toolkit t = Toolkit.getDefaultToolkit();
        int x = (int)((t.getScreenSize().getWidth() - frame.getWidth()) / 2);
        int y = (int)((t.getScreenSize().getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
