package keypress;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Enrollment extends JFrame implements KeyListener, ActionListener

{
	JTextArea displayArea;
    JTextField userIDField;
    JTextField password1;
    JTextField password2;
    JTextField password3;
    
    long time1 = -100;
    long time2 = 0;
    
    ArrayList<Integer> flightTimes = new ArrayList<Integer>();
    ArrayList<Character> enteredPassword = new ArrayList<Character>();
    
    Integer difference = new Integer(0);
    
    public Enrollment(String name) {
        super(name);
    }
    
    private static void createAndShowGUI(Enrollment frame) {
        //Create and set up the window.
        //Enrollment frame = new Enrollment("KeyEventDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        //Set up the content pane.
        frame.addComponentsToPane();
        frame.setSize(450, 500);
        //Display the window.
        //frame.pack();
        frame.setVisible(true);
    }
    
	private void addComponentsToPane() {
        
        JButton button = new JButton("Clear");
        button.addActionListener(this);
        
        JLabel userID = new JLabel("Enter user ID:");
        userIDField = new JTextField(25);
        userIDField.addKeyListener(this);
        
        JLabel pass1Label = new JLabel("Enter keyphrase(1):");
        password1 = new JTextField(25);
        password1.addKeyListener(this);
        
        JLabel pass2Label = new JLabel("Enter keyphrase(2):");
        password2 = new JTextField(25);
        password2.addKeyListener(this);
        
        JLabel pass3Label = new JLabel("Enter keyphrase(3):");
        password3 = new JTextField(25);
        password3.addKeyListener(this);
        
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setSize(450, 500);
         
        getContentPane().add(userID);
        getContentPane().add(userIDField);
        getContentPane().add(pass1Label);
        getContentPane().add(password1);
        getContentPane().add(pass2Label);
        getContentPane().add(password2);
        getContentPane().add(pass3Label);
        getContentPane().add(password3);
        getContentPane().add(displayArea);
        getContentPane().add(button);
    }
	
	/** Handle the key typed event from the text field. */
    
	public void keyTyped(KeyEvent e) {
        //displayInfo(e);
    }
    
	
    /** Handle the key pressed event from the text field. */
   
	public void keyPressed(KeyEvent e) {
        displayInfo(e);
    }
   
    
	/** Handle the key released event from the text field. */
    public void keyReleased(KeyEvent e) {
        displayInfo(e);
    }
     
    /** Handle the button click. */
    public void actionPerformed(ActionEvent e) {
        //Clear the text components.
        displayArea.setText("");
        userIDField.setText("");
         
        //Return the focus to the typing area.
        userIDField.requestFocusInWindow();
    }
    
    private void displayInfo(KeyEvent e) {
        
        //You should only rely on the key char if the event
        //is a key typed event.
        int id = e.getID();
        String keyString = "";
        String differenceString = "";
        String passwordLengthString = "";
        
        if (id == KeyEvent.KEY_TYPED) {
            char c = e.getKeyChar();
            keyString = "key character = '" + c + "'";
        } 
        
        if (id == KeyEvent.KEY_RELEASED) {
        	time1 = System.currentTimeMillis();
        }
             
       
        
        if (id == KeyEvent.KEY_PRESSED) {
        	time2 = System.currentTimeMillis();
        	difference = (int) (time2 - time1);
        	differenceString = "The difference between released and pressed is: " + difference;
        	flightTimes.add(difference);
        	passwordLengthString = "The password length is : " + flightTimes.size();
        } 
        
        if (id == KeyEvent.KEY_PRESSED) 
        	displayArea.setText(keyString + "\n"
                + "    " + differenceString + "\n"
                + "    " + passwordLengthString + "\n"
                + "------------------------\n");
        	displayArea.setCaretPosition(displayArea.getDocument().getLength());
        
    }
    
    public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Enrollment enroll = new Enrollment("Enrollment Module");
				Enrollment.createAndShowGUI(enroll);
				enroll.setVisible(true);			
			}
		});
	}
}
