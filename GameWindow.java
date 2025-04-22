import java.awt.*;
import java.awt.event.*;			
import javax.swing.*;		

public class GameWindow extends JFrame 
				implements ActionListener,
					   KeyListener,
					   MouseListener
{
	private JLabel statusBarL;
	private JLabel keyL;
	private JLabel mouseL;

	private JTextField statusBarTF;
	private JTextField keyTF;
	private JTextField mouseTF;

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

		statusBarL = new JLabel ("Application Status: ");
		keyL = new JLabel("Key Pressed: ");
		mouseL = new JLabel("Location of Mouse Click: ");

		statusBarTF = new JTextField (25);
		keyTF = new JTextField (25);
		mouseTF = new JTextField (25);

		statusBarTF.setEditable(false);
		keyTF.setEditable(false);
		mouseTF.setEditable(false);

		statusBarTF.setBackground(Color.CYAN);
		keyTF.setBackground(Color.YELLOW);
		mouseTF.setBackground(Color.GREEN);

        startB = new JButton ("Start Game");
        pauseB = new JButton ("Pause Game");
        endB = new JButton ("End Game");
        shootB = new JButton ("Shoot Cat");
		exitB = new JButton ("Exit");

		startB.addActionListener(this);
		pauseB.addActionListener(this);
		endB.addActionListener(this);
		shootB.addActionListener(this);
		exitB.addActionListener(this);

		mainPanel = new JPanel(new BorderLayout());

		gamePanel = new GamePanel();
        gamePanel.setPreferredSize(new Dimension(1000, 550));

		JPanel infoPanel = new JPanel();
		GridLayout gridLayout = new GridLayout(3, 2);
		infoPanel.setLayout(gridLayout);
		infoPanel.setBackground(Color.ORANGE);

		infoPanel.add (statusBarL);
		infoPanel.add (statusBarTF);

		infoPanel.add (keyL);
		infoPanel.add (keyTF);		

		infoPanel.add (mouseL);
		infoPanel.add (mouseTF);

		JPanel buttonPanel = new JPanel();
		gridLayout = new GridLayout(1, 4);
		buttonPanel.setLayout(gridLayout);
		buttonPanel.setPreferredSize(new Dimension(1000, 60));

		buttonPanel.add (startB);
		buttonPanel.add (pauseB);
		buttonPanel.add (endB);
		buttonPanel.add (exitB);

		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(infoPanel, BorderLayout.NORTH);
		bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

		mainPanel.add(gamePanel, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		mainPanel.setBackground(Color.PINK);

		gamePanel.addMouseListener(this);
		mainPanel.addKeyListener(this);

		mainPanel.setFocusable(true);
		mainPanel.requestFocusInWindow();

		c = getContentPane();
		c.add(mainPanel);

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		statusBarTF.setText("Application started.");
	}

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

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		String keyText = e.getKeyText(keyCode);
		keyTF.setText(keyText + " pressed.");

		if (keyCode == KeyEvent.VK_LEFT) {
			gamePanel.getCindy().move(-1);
		}

		if (keyCode == KeyEvent.VK_RIGHT) {
			gamePanel.getCindy().move(1);

		}

		if (keyCode == KeyEvent.VK_UP) {
			gamePanel.getCindy().jump();
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
			gamePanel.getCindy().move(0);
		}

		if (keyCode == KeyEvent.VK_RIGHT) {
			gamePanel.getCindy().move(0);
		}

		if (keyCode == KeyEvent.VK_UP) {
			gamePanel.getCindy().land();
		}

		if (keyCode == KeyEvent.VK_DOWN) {
			
		}
	}

	public void keyTyped(KeyEvent e) {

	}

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
