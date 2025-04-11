import java.awt.*;			// need this for GUI objects
import java.awt.event.*;			// need this for Layout Managers
import javax.swing.*;		// need this to respond to GUI events
	
public class GameWindow extends JFrame 
				implements ActionListener,
					   KeyListener,
					   MouseListener
{
	// declare instance variables for user interface objects

	// declare labels 

	private JLabel statusBarL;
	private JLabel keyL;
	private JLabel mouseL;

	// declare text fields

	private JTextField statusBarTF;
	private JTextField keyTF;
	private JTextField mouseTF;

	// declare buttons

	private JButton startB;
	private JButton pauseB;
	private JButton endB;
	private JButton shootB;
	private JButton exitB;

	private Container c;

	private JPanel mainPanel;
	private GamePanel gamePanel;
	

	@SuppressWarnings({"unchecked"})
	public GameWindow() {
 
		setTitle ("Stealthy Monkey");
		setSize (1000, 750);

		// create user interface objects

		// create labels

		statusBarL = new JLabel ("Application Status: ");
		keyL = new JLabel("Key Pressed: ");
		mouseL = new JLabel("Location of Mouse Click: ");

		// create text fields and set their colour, etc.

		statusBarTF = new JTextField (25);
		keyTF = new JTextField (25);
		mouseTF = new JTextField (25);

		statusBarTF.setEditable(false);
		keyTF.setEditable(false);
		mouseTF.setEditable(false);

		statusBarTF.setBackground(Color.CYAN);
		keyTF.setBackground(Color.YELLOW);
		mouseTF.setBackground(Color.GREEN);

		// create buttons

	        startB = new JButton ("Start Game");
	        pauseB = new JButton ("Pause Game");
	        endB = new JButton ("End Game");
	        shootB = new JButton ("Shoot Cat");
			exitB = new JButton ("Exit");

		// add listener to each button (same as the current object)

		startB.addActionListener(this);
		pauseB.addActionListener(this);
		endB.addActionListener(this);
		shootB.addActionListener(this);
		exitB.addActionListener(this);
		
		// create mainPanel

		mainPanel = new JPanel();
		FlowLayout flowLayout = new FlowLayout();
		mainPanel.setLayout(flowLayout);

		GridLayout gridLayout;

		// create the gamePanel for game entities

		gamePanel = new GamePanel();
        gamePanel.setPreferredSize(new Dimension(1000, 800));


		// create infoPanel

		JPanel infoPanel = new JPanel();
		gridLayout = new GridLayout(3, 2);
		infoPanel.setLayout(gridLayout);
		infoPanel.setBackground(Color.ORANGE);

		// add user interface objects to infoPanel
	
		infoPanel.add (statusBarL);
		infoPanel.add (statusBarTF);

		infoPanel.add (keyL);
		infoPanel.add (keyTF);		

		infoPanel.add (mouseL);
		infoPanel.add (mouseTF);

		
		// create buttonPanel

		JPanel buttonPanel = new JPanel();
		gridLayout = new GridLayout(2, 3);
		buttonPanel.setLayout(gridLayout);

		// add buttons to buttonPanel

		buttonPanel.add (startB);
		buttonPanel.add (pauseB);
		buttonPanel.add (endB);
		// buttonPanel.add (shootB);
		buttonPanel.add (exitB);

		// add sub-panels with GUI objects to mainPanel and set its colour

		mainPanel.add(infoPanel);
		mainPanel.add(gamePanel);
		mainPanel.add(buttonPanel);
		mainPanel.setBackground(Color.PINK);

		// set up mainPanel to respond to keyboard and mouse

		gamePanel.addMouseListener(this);
		mainPanel.addKeyListener(this);

		// add mainPanel to window surface

		c = getContentPane();
		c.add(mainPanel);

		// set properties of window

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible(true);

		// set status bar message

		statusBarTF.setText("Application started.");
	}


	// implement single method in ActionListener interface

	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand();
		
		statusBarTF.setText(command + " button clicked.");

		if (command.equals(startB.getText())) {
			gamePanel.startGame();
		}

		if (command.equals(pauseB.getText())) {
			gamePanel.pauseGame();
		}

		if (command.equals(endB.getText()))
			gamePanel.endGame();

		if (command.equals(exitB.getText()))
			System.exit(0);

		mainPanel.requestFocus();
	}


	// implement methods in KeyListener interface

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		String keyText = e.getKeyText(keyCode);
		keyTF.setText(keyText + " pressed.");

		if (keyCode == KeyEvent.VK_LEFT) {
			
		}

		if (keyCode == KeyEvent.VK_RIGHT) {

		}

		if (keyCode == KeyEvent.VK_UP) {
			
		}

		if (keyCode == KeyEvent.VK_DOWN) {
		
		}
		if(keyCode == KeyEvent.VK_SPACE){
			
		}
	}

	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		String keyText = e.getKeyText(keyCode);
		keyTF.setText(keyText + " released.");

		if (keyCode == KeyEvent.VK_LEFT) {
		
		}

		if (keyCode == KeyEvent.VK_RIGHT) {
			
		}

		if (keyCode == KeyEvent.VK_UP) {
			
		}

		if (keyCode == KeyEvent.VK_DOWN) {
			
		}
	}

	public void keyTyped(KeyEvent e) {

	}

	


	// implement methods in MouseListener interface

	public void mouseClicked(MouseEvent e) {

		int x = e.getX();
		int y = e.getY();


		mouseTF.setText("(" + x +", " + y + ")");

	}


	public void mouseEntered(MouseEvent e) {
	
	}

	public void mouseExited(MouseEvent e) {
	
	}

	public void mousePressed(MouseEvent e) {
	
	}

	public void mouseReleased(MouseEvent e) {
	
	}

}