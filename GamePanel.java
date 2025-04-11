import javax.swing.JPanel;

/**
   A component that displays all the game entities
*/

public class GamePanel extends JPanel
		       implements Runnable {

	private SoundManager soundManager;
	private Thread gameThread;
	private boolean isRunning;
	private boolean isPaused;
	private int frameNumber;

	private Level levels[];

	public GamePanel () {
	levels[0] = new Level1();
	levels[1] = new Level2();
	levels[2] = new Level3();
	}
	


	public void createGameEntities() {
	
	

	}


	public void run () {
		frameNumber=0;
		try {
			isRunning = true;
			while (isRunning) {
				if (!isPaused)
					gameUpdate();
				gameRender();
				frameNumber++;
				Thread.sleep (50);	
			}
		}
		catch(InterruptedException e) {}
	}


	public void gameUpdate() {


	}


	public void updateBat (int direction) {
	
	}


	public void gameRender() {


	}



	public void startGame() {				// initialise and start the game thread 

		createGameEntities();
		gameThread = new Thread (this);		
		gameThread.start();

	


	}


	public void pauseGame() {				// pause the game (don't update game entities)
		if (isRunning) {
			if(isPaused)
				isPaused = false;
			else
				isPaused = true;
		}
	}


	public void endGame() {	
		isRunning = false;
	
		soundManager.stopClip ("snore");
	}


}