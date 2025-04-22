import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

    private SoundManager soundManager;
    private Thread gameThread;
    private boolean isRunning;
    private boolean isPaused;
    private int frameNumber;
	private Cindy cindy;
	private Fiddle fiddle;

    private Level[] levels;
    private int currentLevelIndex = 0;

    public GamePanel() {
        // Initialize level array and load levels
        levels = new Level[3];

        levels[0] = new Level1(this);
        levels[1] = new Level2(this);
        levels[2] = new Level3(this);

		cindy = new Cindy(100, 290, 200, 200);
		fiddle = new Fiddle(10,300,100,100);
    }

    public void createGameEntities() {
     
    }

    public void run() {
        frameNumber = 0;
        try {
            isRunning = true;
            while (isRunning) {
                if (!isPaused) {
                    gameUpdate();
                }
                gameRender(); // triggers repaint
                frameNumber++;
                Thread.sleep(50); // ~20 FPS
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

	public void gameUpdate() {
		if (cindy != null) {
			cindy.update();
		}
		if (fiddle != null && cindy != null) {
			fiddle.update(cindy);
		}
		// if (checkCollisionCindyFiddle(cindy, fiddle)) {
		// 	System.out.println("GAME OVER!");
		// 	endGame();
		// }
	}
	

    public void updateBat(int direction) {
		cindy.move(direction);
		
		if (levels[currentLevelIndex] instanceof Level1 level1) {
			if (direction == 1) {
				level1.moveBackground(1); // move right
			} else if (direction == -1) {
				level1.moveBackground(2); // move left
			}
		}
    }

    public void gameRender() {
        repaint(); // triggers paintComponent()
    }

    @Override
    protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
	
		Level currentLevel = levels[currentLevelIndex];
	
		if (currentLevel instanceof Level1 level1) {
			level1.drawBackground(g2);
		}

		
		if (cindy != null) {
			cindy.draw(g2);
		}

		if(fiddle != null){
			fiddle.draw(g2);
		}

    }

    public void startGame() {
        createGameEntities();
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void pauseGame() {
        if (isRunning) {
            isPaused = !isPaused;
        }
    }

    public void endGame() {
        isRunning = false;
        if (soundManager != null) {
            soundManager.stopClip("snore");
        }
    }

    // Move to next level
    public void nextLevel() {
        if (currentLevelIndex < levels.length - 1) {
            currentLevelIndex++;
        }
    }

    //Restart current level
    public void restartLevel() {
        Level current = levels[currentLevelIndex];
        levels[currentLevelIndex] = new Level1(this); // or Level2/3 accordingly
    }

    //Set a specific level
    public void setLevel(int levelIndex) {
        if (levelIndex >= 0 && levelIndex < levels.length) {
            currentLevelIndex = levelIndex;
        }
    }

	public Cindy getCindy() {
		return cindy;
	}

	private boolean checkCollisionCindyFiddle(Cindy c, Fiddle f) {
		return c.getX() < f.getX() + f.getWidth() &&
			   c.getX() + c.getWidth() > f.getX() &&
			   c.getY() < f.getY() + f.getHeight() &&
			   c.getY() + c.getHeight() > f.getY();
	}
	
	
}
