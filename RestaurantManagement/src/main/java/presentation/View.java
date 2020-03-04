package presentation;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class View extends JFrame {

	private JButton admin;
	private JButton waiter;
	private JButton chef;

	public View() {

		this.admin = new JButton("Admin");
		this.admin.setPreferredSize(new Dimension(100, 50));
		this.waiter = new JButton("Waiter");
		this.waiter.setPreferredSize(new Dimension(100, 50));
		this.chef = new JButton("Chef");
		this.chef.setPreferredSize(new Dimension(100, 50));

		this.setTitle("Restaurant");
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));
		this.setSize(new Dimension(650, 200));

		this.add(admin);
		this.add(waiter);
		this.add(chef);

		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void addAdminButtonListener(ActionListener a) {
		admin.addActionListener(a);
	}

	public void addWaiterButtonListener(ActionListener a) {
		waiter.addActionListener(a);
	}

	public void addChefButtonListener(ActionListener a) {
		chef.addActionListener(a);
	}

}
