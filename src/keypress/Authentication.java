package keypress;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class Authentication extends JFrame {
/*	
	public Authentication() {
		setTitle("Authentication Example");
		setSize(300, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Authentication auth = new Authentication();
				auth.setVisible(true);			
			}
		});
	}
*/
	
	public Authentication() {
		initUI();
	}
	
	public final void initUI() {
		
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		
		panel.setLayout(null);
		
		JButton quitButton = new JButton("Quit");
		quitButton.setBounds(50, 60, 80, 30);
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		
		panel.add(quitButton);
		
		setTitle("Quit button");
		setSize(300, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
/*	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Authentication auth = new Authentication();
				auth.setVisible(true);			
			}
		});
	}
*/	
} 

