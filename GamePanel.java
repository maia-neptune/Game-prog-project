import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

    private SoundManager soundManager;
    private Thread gameThread;
    private boolean isRunning;
    private boolean isPaused;
    private int frameNumber;

    private Level[] levels;
    private int currentLevelIndex = 0;

    public GamePanel() {
        // Initialize level array and load levels
        levels = new Level[3];
        levels[0] = new Level1();
        levels[1] = new Level2();
        levels[2] = new Level3();
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
   
    }

    public void updateBat(int direction) {
    
    }

    public void gameRender() {
        repaint(); // triggers paintComponent()
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Level currentLevel = levels[currentLevelIndex];
        Image bg = currentLevel.getBackground();

        if (bg != null) {
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
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
        levels[currentLevelIndex] = new Level1(); // or Level2/3 accordingly
    }

    //Set a specific level
    public void setLevel(int levelIndex) {
        if (levelIndex >= 0 && levelIndex < levels.length) {
            currentLevelIndex = levelIndex;
        }
    }
}
