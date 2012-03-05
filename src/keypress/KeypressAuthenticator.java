package keypress;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class KeypressAuthenticator extends JFrame implements KeyListener, ActionListener

{
	private static final Integer INDIVIDUAL_THRESHOLD = 100;
	
	JTextArea displayArea = new JTextArea(5, 30);
	JTextArea reportArea = new JTextArea();
	
	JTextField userIDField;
	JTextField userIDAuthField;
    JTextField password1;
    JTextField password2;
    JTextField password3;
    JTextField passwordAuth;
    
    JButton authButton = new JButton("Authenticate");
    JButton enrollButton = new JButton("Enroll");
    JButton quitButton = new JButton("Quit");
    JButton clearButton = new JButton("Clear");
    JButton mainMenuButton = new JButton("Main Menu");
    
    long time1 = -1000;
    long time2 = 0;
    int passwordSize;
    int totalThreshold;
    
    String userID;
    String userIDAuth;
    String pass1;
    String pass2;
    String pass3;
    String passAuth;
    String loadedPassword;
    String authenticated;
    
    Integer totalFlightTime = new Integer(0);
    
    ArrayList<Integer> flightTimes = new ArrayList<Integer>();
    ArrayList<Integer> flightTimes1 = new ArrayList<Integer>();
    ArrayList<Integer> flightTimes2 = new ArrayList<Integer>();
    ArrayList<Integer> flightTimes3 = new ArrayList<Integer>();
    ArrayList<Integer> allFlightTimes = new ArrayList<Integer>();
    ArrayList<Integer> variances12 = new ArrayList<Integer>();
    ArrayList<Integer> variances23 = new ArrayList<Integer>();
    ArrayList<Integer> variances13 = new ArrayList<Integer>();
    ArrayList<Integer> variancesAuth = new ArrayList<Integer>();
    ArrayList<Integer> variancesAverage = new ArrayList<Integer>();
    ArrayList<Integer> flightTimesAverage = new ArrayList<Integer>();
    ArrayList<Integer> averageFlightTimesLoaded = new ArrayList<Integer>();
    ArrayList<Integer> flightTimesAuth = new ArrayList<Integer>();
    ArrayList<Long> keyCaptureTimes = new ArrayList<Long>();
    ArrayList<Character> actualPasswordAuth = new ArrayList<Character>();
    ArrayList<Character> enteredPasswordAuth = new ArrayList<Character>();
    ArrayList<String> readEnrollmentFile = new ArrayList<String>();
    ArrayList<String> enrolledUsers = new ArrayList<String>();
    ArrayList<String> loadedUsers = new ArrayList<String>();
    
    Integer difference = new Integer(0);
    
    public KeypressAuthenticator(String name) {
        super(name);
    }
    
    private static void mainMenu(KeypressAuthenticator frame) {
        //Create and set up the window.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setSize(450, 450);
        //Set up the content pane.
        frame.buildMainMenu();
        
        //Display the window.
        //frame.pack();
        frame.setVisible(true);
    }
    
    private void buildMainMenu() {
        
        enrollButton.addActionListener(this);
        
        authButton.addActionListener(this);
        
        quitButton.addActionListener(this);
         
        getContentPane().add(enrollButton);
        getContentPane().add(authButton);
        getContentPane().add(quitButton);
        this.setSize(150, 150);
    }
    
	private void buildEnroller() {
        
        clearButton.addActionListener(this);
        
        JLabel userID = new JLabel("Enter the user ID:");
        userIDField = new JTextField(25);
        userIDField.addActionListener(this);
        userIDField.addKeyListener(this);
        
        JLabel pass1Label = new JLabel("Enter keyphrase(1):");
        password1 = new JTextField(25);
        password1.addActionListener(this);
        password1.addKeyListener(this);
        
        JLabel pass2Label = new JLabel("Enter keyphrase(2):");
        password2 = new JTextField(25);
        password2.addActionListener(this);
        password2.addKeyListener(this);
        
        JLabel pass3Label = new JLabel("Enter keyphrase(3):");
        password3 = new JTextField(25);
        password3.addActionListener(this);
        password3.addKeyListener(this);
        
        displayArea.setEditable(false);
        displayArea.setBorder(BorderFactory.createLineBorder(Color.black));
        displayArea.setSize(450, 100);
        
        mainMenuButton.addActionListener(this);
        
        getContentPane().add(userID);
        getContentPane().add(userIDField);
        getContentPane().add(pass1Label);
        getContentPane().add(password1);
        getContentPane().add(pass2Label);
        getContentPane().add(password2);
        getContentPane().add(pass3Label);
        getContentPane().add(password3);
        getContentPane().add(displayArea);
        getContentPane().add(clearButton);
        getContentPane().add(mainMenuButton);
        this.setSize(415, 600);
    }
	
	private void buildAuthenticator() {
        
        clearButton.addActionListener(this);
        
        JLabel userIDLabel = new JLabel("Enter user ID:");
        userIDAuthField = new JTextField(25);
        userIDAuthField.addActionListener(this);
        userIDAuthField.addKeyListener(this);
        
        JLabel passwordLabel = new JLabel("Enter keyphrase:");
        passwordAuth = new JTextField(25);
        passwordAuth.addActionListener(this);
        passwordAuth.addKeyListener(this);
        
        displayArea.setEditable(false);
        displayArea.setSize(450, 100);
        
        mainMenuButton.addActionListener(this); 
        
        getContentPane().add(userIDLabel);
        getContentPane().add(userIDAuthField);
        getContentPane().add(passwordLabel);
        getContentPane().add(passwordAuth);
        getContentPane().add(displayArea);
        getContentPane().add(mainMenuButton);
        
        this.setSize(400, 300);
    }
	
	private void buildReportGenerator() {
        reportArea.setEditable(false);
        reportArea.setSize(600, 600);
        mainMenuButton.addActionListener(this); 
        
        getContentPane().add(reportArea);
        getContentPane().add(mainMenuButton);
        
        this.setSize(700, 400);
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
        
    	Object src = e.getSource();
    	if(src == enrollButton)
    	{
    		this.getContentPane().removeAll();
    		this.buildEnroller();
    		this.setVisible(true);
    	}
    	else if (src == authButton)
    	{
    		this.getContentPane().removeAll();
    		this.buildAuthenticator();
    		this.setVisible(true);
    		
    	}
    	else if(src == quitButton)
    	{
    		this.getContentPane().removeAll();
    		this.buildReportGenerator();
    		reportArea.setText("This is a test. This is only a test");
    		this.setVisible(true);
    	}
    	else if(src == clearButton)
    	{
    		//Clear the text components.
    		displayArea.setText("");
    		userIDField.setText("");
    		password1.setText("");
    		password2.setText("");
    		password3.setText("");
    		
    		//Return the focus to the typing area.
    		userIDField.requestFocusInWindow();
    	}
    	else if(src == mainMenuButton)
    	{
    		averageFlightTimesLoaded.clear();
    		readEnrollmentFile.clear();
    		flightTimesAuth.clear();
    		variancesAuth.clear();
    		displayArea.setText("");
    		reportArea.setText("");
    		loadedUsers.clear();
    		keyCaptureTimes.clear();
    		this.getContentPane().removeAll();
    		this.buildMainMenu();
    		this.setVisible(true);
    	}
    	else if(src == userIDField)
    	{
    		readEnrollment();
    		
    		System.out.println(readEnrollmentFile.toString());
    		userID = userIDField.getText();
    		displayArea.append(verifyUserIDuniqueOutput(verifyUserIDunique()));
    		System.out.println("User ID:" + userID);
    		displayArea.append("\n" + userIDLengthCheckOutput(userIDLengthCheck()));
    		flightTimes.clear();
    		userIDField.transferFocus();
    	}
    	else if(src == password1)
    	{
    		pass1 = password1.getText();
    		flightTimes.remove(0);
    		flightTimes.remove(flightTimes.size() - 1);
    		flightTimes1 = flightTimes;
    		allFlightTimes.addAll(flightTimes1);
    		System.out.println("Password1:" + pass1);
    		System.out.println("Flight times for Password1:" + flightTimes1.toString());
    		totalFlightTime(flightTimes1);
    		flightTimes.clear();
    		password1.transferFocus();
    	}
    	else if(src == password2)
    	{
    		pass2 = password2.getText();
    		displayArea.append("\n" + passwordsMatchOutput(checkIfPasswordMatches(pass1, pass2)));
    		flightTimes.remove(0);
    		flightTimes.remove(flightTimes.size() - 1);
    		flightTimes2 = flightTimes;
    		allFlightTimes.addAll(flightTimes2);
    		System.out.println("Password2:" + pass2);
    		System.out.println("Flight times for Password2:" + flightTimes2.toString());
    		totalFlightTime(flightTimes2);
    		flightTimes.clear();
    		password2.transferFocus();
    	}
    	else if(src == password3)
    	{
    		pass3 = password3.getText();
    		displayArea.append("\n" + passwordsMatchOutput(checkIfPasswordMatches(pass2, pass3)));
    		passwordSize = pass3.length() - 1;
    		totalThreshold = 150 * 3 * passwordSize;
    		flightTimes.remove(0);
    		flightTimes.remove(flightTimes.size() - 1);
    		flightTimes3 = flightTimes;
    		allFlightTimes.addAll(flightTimes3);
    		generateVariances();
    		averageVariances();
    		averageFlightTimes();
    		totalFlightTime(flightTimes3);
    		
    		displayArea.append("\n" + verifyEnrollment());
    		
    		System.out.println("Password3:" + pass3);
    		System.out.println("Flight times for allFlightTimes:" + allFlightTimes.toString());
    		System.out.println("Variances for 1 and 2:" + variances12.toString());
    		System.out.println("Variances for 2 and 3:" + variances23.toString());
    		System.out.println("Variances for 1 and 3:" + variances13.toString());
    		System.out.println("Total flight time:" + totalFlightTime);
    		
    		displayArea.append("\nIndividual variances 1 to 2 = " + variances12.toString());
    		displayArea.append("\nIndividual variances 2 to 3 = " + variances23.toString());
    		displayArea.append("\nIndividual variances 1 to 3 = " + variances13.toString());
    		displayArea.append("\n-----------------------------------");
    		displayArea.append("\nTotal variance = " + totalVariances());
    		displayArea.append("\n" + indivialVarianceAboveThresholdOutput(individualVarianceAboveThreshold()));
    		displayArea.append("\n" + totalVarianceAboveThresholdOutput(totalVariancesAboveThreshold(totalVariances())));
    		
    		variances12.clear();
        	variances23.clear();
        	variances13.clear();
        	variancesAverage.clear();
        	flightTimes1.clear();
        	flightTimes2.clear();
        	flightTimes3.clear();
        	flightTimesAverage.clear();
        	allFlightTimes.clear();
        	totalFlightTime = 0;
    		flightTimes.clear();
    		readEnrollmentFile.clear();
    		enrolledUsers.clear();
    		password3.transferFocus();
    	}
    	else if(src == userIDAuthField)
    	{
    		readEnrollment();
    		userIDAuth = userIDAuthField.getText();
    		System.out.println("User ID:" + userIDAuth);
    		displayArea.append(verifyUserIDexistsOutput(verifyUserIDexists()));
    		flightTimes.clear();
    		keyCaptureTimes.clear();
    		userIDAuthField.transferFocus();
    	}
    	else if(src == passwordAuth)
    	{
    		passAuth = passwordAuth.getText();
    		displayArea.append("\n" + passwordsMatchOutput(checkIfPasswordMatches(passAuth, loadedPassword)));
    		System.out.println("loadedPassword:" + loadedPassword);
    		System.out.println("PasswordAuth:" + passAuth);
    		flightTimes.remove(0);
    		flightTimes.remove(flightTimes.size() - 1);
    		keyCaptureTimes.set(0, (long) 0);
    		keyCaptureTimes.remove(keyCaptureTimes.size() - 1);
    		totalThreshold = INDIVIDUAL_THRESHOLD * passAuth.length();
    		System.out.println("totalThreshold:" + totalThreshold);
    		authenticated = verifyAuthentication();
    		displayArea.append("\n" + authenticated);
    		
    		System.out.println("User password:" + passAuth);
    		System.out.println("User actual flight times" + flightTimesAuth.toString());
    		System.out.println("User loaded average flight times" + averageFlightTimesLoaded.toString());
    		System.out.println("User flight time variances" + variancesAuth.toString());
    		
    		passwordAuth.transferFocus();
    		
    		try{
    			  Thread.currentThread();
    			  Thread.sleep(1500);
    		}
    		catch(InterruptedException ie){
    			
    		}
    		this.getContentPane().removeAll();
    		this.buildReportGenerator();
    		reportArea.setText(generateReport());
    		this.setVisible(true);
    	}
    }
    
	private void displayInfo(KeyEvent e) {
        
        int id = e.getID();
        
        if (id == KeyEvent.KEY_TYPED) {
            //char c = e.getKeyChar();
            //keyString = "key character = '" + c + "'";
        } 
        
        if (id == KeyEvent.KEY_RELEASED) {
        }
            
        if (id == KeyEvent.KEY_PRESSED) {
        	time2 = System.currentTimeMillis();
        	keyCaptureTimes.add(System.currentTimeMillis());
        	difference = (int) (time2 - time1);
        	flightTimes.add(difference);   
        	time1 = time2;
        } 
        
        if (id == KeyEvent.KEY_PRESSED) { 
        	displayArea.setCaretPosition(displayArea.getDocument().getLength());
    	}
    }
    
    public boolean verifyUserIDunique()
    {
    	for(int i =0; i < readEnrollmentFile.size(); i++)
    	{
    		if(readEnrollmentFile.get(i).equals("*****"))    	
    		{
    			enrolledUsers.add(readEnrollmentFile.get(i + 1));
    		}
    	}
    	
    	for(String user : enrolledUsers)
    	{
    		if(userID.equals(user))
    		{
    			return false;
    	
    		}
    	}
    	return true;
    }
    
    private boolean verifyUserIDexists()
    {
    	for(int i =0; i < readEnrollmentFile.size(); i++)
    	{
    		if(readEnrollmentFile.get(i).equals("*****"))    	
    		{
    			loadedUsers.add(readEnrollmentFile.get(i + 1));
    			
    		}
    	}
    	
    	for(String user : loadedUsers)
    	{
    		if(userIDAuth.equals(user))
    		{
    			loadUserInfo();
    			return true;
    	
    		}
    	}
    	return false;
    }
    
    private void loadUserInfo()
    {
    	for(int i = 0; i < readEnrollmentFile.size(); i++)
    	{
    		if(readEnrollmentFile.get(i).equals("*****"))
    		{
    			if(userIDAuth.equals(readEnrollmentFile.get(i + 1)))
    			{
    				loadedPassword = readEnrollmentFile.get(i + 2);
    				int passAuthSize = loadedPassword.length();
    				
    				System.out.println("IN LOADUSERINFO: passAuthSize = " + passAuthSize);
    				for(int j = (i + 3); j < ((i + 3) + (passAuthSize - 1)); j++)
    				{
    					String valueString = readEnrollmentFile.get(j);
    					Integer valueInt = Integer.parseInt(valueString);
    					averageFlightTimesLoaded.add(valueInt);
    				}
    			}
    		}
    	}
    }
    
    private boolean userIDLengthCheck()
    {
    	if(userID.length() < 8 || userID.length() > 20)
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    }
    
    public void totalFlightTime(ArrayList<Integer> times)
    {
    	Iterator<Integer> iter = times.iterator();
    	
    	while(iter.hasNext()) {
    		totalFlightTime += iter.next();
    	}	
    }
    
    public String verifyEnrollment()
    {
    	if( verifyUserIDunique() &&
    		userIDLengthCheck() &&
    		individualVarianceAboveThreshold() == false &&
    		totalVariancesAboveThreshold(totalVariances()) == false)
    	{
    		writeEnrollment();
    		return "Enrollment Successful!";
    	}
    	else
    	{
    		return "!!!! Enrollment Denied!!!!";
    	}
    	
    }
    
    public String verifyAuthentication()
    {
    	if( checkIfPasswordMatches(passAuth, loadedPassword) &&
    	    verifyVariancesWithinThreshold() == false &&
    	    totalVarianceAuthAboveThreshold(totalVarianceAuth()) == false)
    	{
    		flightTimesAuth = flightTimes;
    		generateVariancesAuth();
    		return "User authenticated";
    	}
    	else
    	{
    		return "User is NOT authenticated";
    	}
    }
    
    private boolean verifyVariancesWithinThreshold()
    {
    	boolean overlimit = false;
    	
    	for( Integer v : variancesAuth)
    	{
    		if(v > INDIVIDUAL_THRESHOLD)
    		{
    			overlimit = true;
    		}
    	}
    	return overlimit;
    }
    
    public void generateVariances()
    {	
    	for(int i = 0; i < passwordSize; i++)
    	{
    		//System.out.println("passwordSize = " + passwordSize);
    		//System.out.println("qllFlightTimes.get(i):" + allFlightTimes.get(i));
    		//System.out.println("qllFlightTimes.get(passwordSize + i):" + allFlightTimes.get(passwordSize + i));
    		int diff = Math.abs(allFlightTimes.get(i) - allFlightTimes.get(passwordSize + i));
    		variances12.add(diff);
    		//System.out.println("Variances12:" + variances12.toString());
    	}
    	
    	for(int i = 0; i < passwordSize; i++)
    	{
    		int diff = Math.abs(allFlightTimes.get(i) - allFlightTimes.get((passwordSize * 2) + i));
    		variances13.add(diff);
    		//System.out.println("Variances13:" + variances13.toString());
    	}
    	
    	for(int i = 0; i < passwordSize; i++)
    	{
    		int diff = Math.abs(allFlightTimes.get(passwordSize + i) - allFlightTimes.get((passwordSize * 2) + i));
    		variances23.add(diff);
    		//System.out.println("Variances23:" + variances23.toString());
    	}
    }
    
    private void generateVariancesAuth()
    {
    	System.out.println("flightTimesAuth:" + flightTimesAuth.toString());
		System.out.println("averageFlightTimesLoaded = " + averageFlightTimesLoaded.toString());
		
    	for(int i = 0; i < passAuth.length() - 1; i++)
    	{
    		int diff = Math.abs(flightTimesAuth.get(i) - averageFlightTimesLoaded.get(i));
    		variancesAuth.add(diff);
    	}
    	System.out.println("VariancesAuth:" + variancesAuth.toString());
    }
    
    private void averageVariances()
    {	
    	int diff12 = 0;
    	int diff23 = 0;
    	int diff13 = 0;
    	int avgDiff = 0;
    	 	
    	for(int i = 0; i < passwordSize; i++)
    	{
    		diff12 = Math.abs(allFlightTimes.get(i) - allFlightTimes.get(passwordSize + i));
    		diff23 = Math.abs(allFlightTimes.get(passwordSize + i) - allFlightTimes.get((passwordSize * 2) + i));
    		diff13 = Math.abs(allFlightTimes.get(i) - allFlightTimes.get((passwordSize * 2) + i));
    		avgDiff = (diff12 + diff23 + diff13)/3;
    		variancesAverage.add(avgDiff);
    	}
    	System.out.println("Average variances : " + variancesAverage);
    }
    
    private void averageFlightTimes()
    {
    	for(int i = 0; i < passwordSize; i++)
    	{
    		int average = 0;
    		int sum = 0;
    		sum += allFlightTimes.get(i);
    		sum += allFlightTimes.get(passwordSize +i);
    		sum += allFlightTimes.get((passwordSize * 2) +i);
    		average = sum/3;
    		System.out.println("Average = " + average);
    		flightTimesAverage.add((int) average);
    	}
    	System.out.println("Average flight times: " + flightTimesAverage);
    }
    
    private int totalVariances()
    {
    	int totalVariance = 0;
    	
    	for( Integer v : variances12)
    	{
    		totalVariance += v;
    	}
    	
    	for( Integer v : variances23)
    	{
    		totalVariance += v;
    	}
    	
    	for( Integer v : variances13)
    	{
    		totalVariance += v;
    	}
    	
    	return totalVariance;
    }
    
    private int totalVarianceAuth()
    {
    	int totalVariance = 0;
    	
    	for( Integer v : variancesAuth)
    	{
    		totalVariance += v;
    	}
    	return totalVariance;
    }
    
    private boolean totalVariancesAboveThreshold(int totalVariances)
    {
    	if(totalVariances > totalThreshold)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    private boolean totalVarianceAuthAboveThreshold(int totalVariance)
    {
    	if(totalVariance > totalThreshold)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    private boolean individualVarianceAboveThreshold()
    {
    	boolean overlimit = false;
    	
    	for( Integer v : variances12)
    	{
    		if(v > INDIVIDUAL_THRESHOLD)
    		{
    			overlimit = true;
    		}
    	}
    	
    	for( Integer v : variances23)
    	{
    		if(v > INDIVIDUAL_THRESHOLD)
    		{
    			overlimit = true;
    		}
    	}
    	
    	for( Integer v : variances13)
    	{
    		if(v > INDIVIDUAL_THRESHOLD)
    		{
    			overlimit = true;
    		}
    	}
    	
    	return overlimit;
    }
    
    private String verifyUserIDuniqueOutput(boolean unique)
    {
    	if(unique)
    	{
    		return "User ID is unique";
    	}
    	else
    	{
    		return "The User ID entered is not Unique";
    	}
    }
    
    private String verifyUserIDexistsOutput(boolean exists)
    {
    	if(exists)
    	{
    		return "User ID found";
    	}
    	else
    	{
    		return "The User ID entered is not exist";
    	}
    }
    
    public String userIDLengthCheckOutput(boolean correctLength)
    {
    	if(correctLength)
    	{
    		return "UserID is an acceptable length";
    	}
    	else
    	{
    		return "Your userID must be between 8 and 20 characters long";
    	}
    }
    
    private String indivialVarianceAboveThresholdOutput(boolean overlimit)
    {
    	if(overlimit)
    	{
    		return "You had an individual variance that was above the threshold of " + INDIVIDUAL_THRESHOLD;
    	}
    	else
    	{
    		return "Individual variances are in an acceptable range";
    	}
    }
    
    private String totalVarianceAboveThresholdOutput(boolean overlimit)
    {
    	if(overlimit)
    	{
    		return "Your total variance was above the threshold of " + totalThreshold;
    	}
    	else
    	{
    		return "Total variance is in an acceptable range";
    	}
    }
    
    private boolean checkIfPasswordMatches(String password1, String password2 )
    {
    	return password1.equals(password2);
    }
    
    public String passwordsMatchOutput(boolean match)
    {
    	if(match)
    	{
    		return "Passwords match";
    	}
    	else
    	{
    		return "Passwords do not Match";
    	}
    }
    
    private String generateReport()
    {
    	LinkedList<String> report = new LinkedList<String>();
    	report.add("AUTHENTICATION REPORT\n");
    	report.add("------------------\n");
    	report.add("Correct Key      Key Pressed     Time Pressed      Correct Delta     Actual Delta    Variance     Accept/ Reject\n");
    	
    	System.out.println("IN GENERATEREPORT: loadedPassword = " + loadedPassword);
    	System.out.println("IN GENERATEREPORT: passAuth = " + passAuth);
    	
    	
    	char[] correctKeys = loadedPassword.toCharArray();
    	char[] keysPressed = passAuth.toCharArray();
    	
    	System.out.println("IN GENERATEREPORT: correctKeys.length = " + correctKeys.length);
    	System.out.println("IN GENERATEREPORT: keysPressed.length = " + keysPressed.length);
    	
    	System.out.println("IN GENERATEREPORT: keyCaptureTimes.size = " + keyCaptureTimes.size());
    	System.out.println("IN GENERATEREPORT: averageFlightTimesLoaded.size = " + averageFlightTimesLoaded.size());
    	System.out.println("IN GENERATEREPORT: flightTimesAuth.size = " + flightTimesAuth.size());
    	System.out.println("IN GENERATEREPORT: variancesAuth.size = " + variancesAuth.size());
    	
    	if(correctKeys.length != keysPressed.length)
    	{
    		return "CRITICAL ERROR: Password lengths DO NOT match!!";
    	}
    	for(int i = 0; i < correctKeys.length; i++)
    	{
    		report.add("     ");
    		report.add(Character.toString(correctKeys[i]));
    		report.add("                      ");
    		report.add(Character.toString(keysPressed[i]));
    		report.add("                 ");
    		
    		if(i == 0)
    		{
    			report.add("            0                   0                     0                       0                 ACCEPT\n");
    		}
    		else
    		{
    			report.add(Long.toString(keyCaptureTimes.get(i)));
        		report.add("     ");
    			report.add(Integer.toString(averageFlightTimesLoaded.get(i - 1)));
    			report.add("                   ");
    			report.add(Integer.toString(flightTimesAuth.get(i - 1)));
    			report.add("                   ");
    			report.add(Integer.toString(variancesAuth.get(i - 1)));
    			report.add("                ");
    			report.add(acceptOrReject(variancesAuth.get(i - 1)) + "\n");
    		}
    	}
    	report.add("-----------------------------------------------\n");
    	report.add("AUTHENTICATION SUMMARY\n");
    	report.add(authenticationSummary());
    	report.add("-----------------------------------------------\n");
    	
    	Iterator<String> itr = report.iterator();
    	
    	String output = "";
    	
		while(itr.hasNext())
		{
			output += itr.next();
		}
		return output;
    }
    
    private String acceptOrReject(int value)
    {
    	if(value > INDIVIDUAL_THRESHOLD)
    	{
    		return "REJECT";
    	}
    	else
    	{
    		return "ACCEPT";
    	}
    }
    
    private String authenticationSummary()
    {
    	String result = "";
    	
    	if(verifyVariancesWithinThreshold())
    	{
    		result += "Authentication failed due to at least one individual variance being beyond the individual threshold of " + INDIVIDUAL_THRESHOLD + "\n";
    	}
    	
    	if(totalVarianceAuthAboveThreshold(totalVarianceAuth()))
    	{
    		result += "Authentication failed due to total variance being beyond the total threshold of " + totalThreshold + "\n";
    	}
    	
    	if(verifyVariancesWithinThreshold() == false && totalVarianceAuthAboveThreshold(totalVarianceAuth()) == false )
    	{
    		result += "Authentication Succeeded!!!\n";
    		result += "You were in both the individual threshold of " + INDIVIDUAL_THRESHOLD + " and the total threshold of " + totalThreshold +"\n";
    		result += "Individual variance was " + variancesAuth.toString() + "\n";
    		result += "Total variance was " + totalVarianceAuth() + "\n";
    	}
    	
    	return result;
    }
    
    private void readEnrollment()
    {
    	{
    		try{
    			
    			FileInputStream fstream = new FileInputStream("template.txt");
    			DataInputStream in = new DataInputStream(fstream);
    			BufferedReader br = new BufferedReader(new InputStreamReader(in));
    			String inputString;
    			while ((inputString = br.readLine()) != null)   {
    			
    				readEnrollmentFile.add(inputString);
    			}
    			in.close();
    		}
    		catch (Exception e){//Catch exception if any
    			System.err.println("Error: " + e.getMessage());
    		}
    	}
    }

    public void writeEnrollment()
    {
    	{
    		try{
    			// Create file 
    			FileWriter fstream = new FileWriter("template.txt", true);
    			BufferedWriter out = new BufferedWriter(fstream);
    			out.write("*****\n");
    			out.write(userID + "\n");
    			out.write(pass3 + "\n");
    			for(Integer time : flightTimesAverage)
    			{
    				out.write(time + "\n");
    			}
    			//Close the output stream
    			out.close();
    		}catch (Exception e){//Catch exception if any
    			System.err.println("Error: " + e.getMessage());
    		}
    	}
    }
    
    public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				KeypressAuthenticator frame = new KeypressAuthenticator("Keypress Authenticator");
				KeypressAuthenticator.mainMenu(frame);
				frame.setVisible(true);			
			}
		});
	}
}
