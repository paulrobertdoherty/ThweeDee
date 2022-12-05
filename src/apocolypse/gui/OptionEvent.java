package apocolypse.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class OptionEvent implements ActionListener {
	private JPanel window = new JPanel();
	private JList list;
	private Rectangle rList = new Rectangle(0, 0, 30, 10);
	JFrame frame = new JFrame("Options");

	public void actionPerformed(ActionEvent e) {
		frame.setSize(new Dimension(700, 500));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
		window.setLayout(null);
		drawButtons();
	}

	private void drawButtons() {
		list = new JList();
		list.setVisibleRowCount(4);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(rList);
		frame.add(new JScrollPane(list));
		System.out.println("It worked!");
	}
}
