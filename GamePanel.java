import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

    private SoundManager soundManager;
    private Thread gameThread;
    private boolean isRunning;
    private boolean isPaused;
    private int frameNumber;
    private Cindy cindy;
    private Fiddle fiddle;
    private Dimjack dimjack;
    private Menmon menmon;
    private ArrayList<Beam> beams;
    private BufferedImage screenBuffer;

    private Level[] levels;
    private int currentLevelIndex = 0;

    private int scrollCount = 0;
    private final int MAX_SCROLLS = 10;

    private int score = 0;
    private int bgScrollY = 0;
    public boolean downPressed = false;

    private long level2StartTime = 0;
    private int treatsSpawned = 0;
    private final int MAX_TREATS = 10;
    private final int TREAT_INTERVAL_MS = 3000;

    private ImageIcon level3Gif;
    private boolean showLevel3Intro = false;
    private long level3IntroStartTime = 0;
    private final int LEVEL3_INTRO_DURATION_MS = 5000;

    private BufferedImage startScreenImage;
    private boolean gameStarted = false;

    private ImageIcon level2Gif;
    private boolean showLevel2Intro = false;
    private long level2IntroStartTime = 0;
    private final int LEVEL2_INTRO_DURATION_MS = 5_000; // 10 seconds


    private boolean level3SecondaryGifShown = false;
private ImageIcon level3SecondaryGif;
private boolean showLevel3SecondaryIntro = false;
private long level3StartTime = 0;
private final int LEVEL3_SECONDARY_INTRO_DELAY_MS = 20_000; // 20 seconds
private final int LEVEL3_SECONDARY_INTRO_DURATION_MS = 5000;
private long level3SecondaryIntroStartTime = 0;

    private int lives = 3; 
    private BufferedImage heartImage; 
    private final int HEART_SIZE = 40;  

    private BufferedImage gameOverImage;
    private boolean gameOver = false;
    

    public GamePanel() {
        levels = new Level[3];
        levels[0] = new Level1(this);
        levels[1] = new Level2(this);
        levels[2] = new Level3(this);

        cindy = new Cindy(400, 290, 200, 200, 1);
        fiddle = new Fiddle(10, 370, 120, 120);
        dimjack = new Dimjack(400, 50, 200, 200, cindy);
        menmon = new Menmon(450, 10, 200, 200, "images/menmon.png");
        beams = new ArrayList<>();

        screenBuffer = new BufferedImage(1000, 750, BufferedImage.TYPE_INT_RGB);

        try {
            startScreenImage = ImageManager.loadBufferedImage("images/start.png");
        } catch (Exception e) {
            System.out.println("Could not load start.png");
        }

        try {
            heartImage = ImageManager.loadBufferedImage("images/heart.png"); 
        } catch (Exception e) {
            System.out.println("Could not load heart image");
        }

        try {
            gameOverImage = ImageManager.loadBufferedImage("images/endgame.png");
        } catch (Exception e) {
            System.out.println("Could not load game over image");
        }
        
    }

    public void createGameEntities() {
        if (currentLevelIndex == 0) {
            scrollCount = 0;
            cindy.setLevelIndex(0);
            cindy.setX(400);
            cindy.setY(290);
            fiddle.setX(10);
            fiddle.setY(370);
            menmon.setY(10);
            this.score = cindy.getScore();
            
            SoundManager.getInstance().playClip("level1_2", true);
        }

        if (currentLevelIndex == 1) {
            level2Gif = new ImageIcon("images/Dimjack_open.gif");
            showLevel2Intro = true;
            level2IntroStartTime = System.currentTimeMillis();
            
            SoundManager.getInstance().playClip("level1_2", false);
        }

        if(currentLevelIndex == 2) {
            menmon.setY(100);
          
                level3Gif = new ImageIcon("images/menmon_open.gif");
                showLevel3Intro = true;
                level3IntroStartTime = System.currentTimeMillis();
        
                SoundManager.getInstance().playClip("level3", true);
        }

        if (currentLevelIndex == 2) {
            menmon.setY(100);
            
            level3Gif = new ImageIcon("images/menmon_open.gif");
            showLevel3Intro = true;
            level3IntroStartTime = System.currentTimeMillis();
        
            level3SecondaryGif = new ImageIcon("images/win_scene.gif"); 
            level3StartTime = System.currentTimeMillis(); // record when Level 3 started
            level3SecondaryGifShown = false;
        
            SoundManager.getInstance().playClip("level3", true);
        }

    }

    public void run() {
        frameNumber = 0;
        try {
            isRunning = true;
            while (isRunning) {
                if (!isPaused) {
                    gameUpdate();
                }
                gameRender();
                frameNumber++;
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void gameUpdate() {


        if (currentLevelIndex == 1 && showLevel2Intro) {
            long now = System.currentTimeMillis();
            if (now - level2IntroStartTime < LEVEL2_INTRO_DURATION_MS) {
                return; // Wait for Level 2 GIF
            } else {
                showLevel2Intro = false;
            }
        }
    
        if (currentLevelIndex == 2 && showLevel3Intro) {
            long now = System.currentTimeMillis();
            if (now - level3IntroStartTime < LEVEL3_INTRO_DURATION_MS) {
                return; // Wait for Level 3 GIF
            } else {
                showLevel3Intro = false;
            }
        }
        if (levels[currentLevelIndex] instanceof Level1 level1) {
            if (cindy != null) cindy.update();
            if (fiddle != null && cindy != null) fiddle.update(cindy);
            
            if (checkCollisionCindyFiddle(cindy, fiddle))
            { System.out.println("Fiddle caught Cindy!");
            lives--; // Lose one life
            if (lives > 0) {
                // Reset Cindy and Fiddle position to continue
                cindy.setX(400);
                cindy.setWorldX(400);
                fiddle.setX(10);
                fiddle.setWorldX(10);
            } else {
                endGame(); // No lives left
            }
            }
            cindy.setLevelIndex(0);
            level1.update(cindy, fiddle);
            
            if (cindy.getWorldX()>=2000 && !checkCollisionCindyFiddle(cindy, fiddle)) {
                System.out.println("Level 1 completed!");
                nextLevel(); // Move to next level
                cindy.setLevelIndex(1);
                cindy.setY(400);
            }
        }

    
        // Level 2 Update Logic (Dimjack and cannonballs)
        if (levels[currentLevelIndex] instanceof Level2 level2) {
            cindy.setY(400);
            if (cindy != null && cindy.getLevelIndex() == 1) cindy.update(); // Update Cindy
            if (dimjack != null) { 
                dimjack.update(); // Update Dimjack's movement
    
                ArrayList<Cannonball> balls = dimjack.getCannonballs();
                balls.removeIf(ball -> {
                    if (ball.checkCollision(cindy)) {
                        lives--;
                        SoundManager.getInstance().playClip("cannonball", false); // Play sound on collision
                        return true;
                    }
                    return false;
                });
                level2.update(cindy); // Update level2-specific logic
                System.out.println(dimjack.getFiredCount());

                if (dimjack.getFiredCount()>= 15) {
                    System.out.println("Level 2 completed!");
                    nextLevel(); // Move to next level
                    SoundManager.getInstance().stopClip("level1_2");
                    cindy.setLevelIndex(2);
                }
            }
        }
    

        if (levels[currentLevelIndex] instanceof Level3 level3) {

            long now = System.currentTimeMillis();


if (!level3SecondaryGifShown && (now - level3StartTime >= LEVEL3_SECONDARY_INTRO_DELAY_MS) && lives > 0) {
    showLevel3SecondaryIntro = true;
    level3SecondaryIntroStartTime = now;
    level3SecondaryGifShown = true;
}
            if (cindy != null && cindy.getLevelIndex() == 2) cindy.update(); // Update Cindy in level 3
    

            if (menmon != null) {
                menmon.update(cindy); 
               
                if (Math.random() < 0.05) {
                    beams.add(new Beam(cindy.getX(), menmon.getY() + 180)); 
                    
                }
            }
    
            // Remove off-screen or collided beams
            beams.removeIf(beam -> {
                beam.update();
                return beam.isOffScreen() || beam.checkCollision(cindy);
            });
        }

        if(lives <= 0){
            endGame();
        }
    }
    
    public void updateBat(int direction) {
        if (isPaused) return;


// Level 1 Update Logic
        if (levels[currentLevelIndex] instanceof Level1 level1) {
            Background bg = level1.getBackground();
            if (direction == 1 && cindy.getX() >= 400) {
                if (scrollCount < MAX_SCROLLS) {
                    bg.move(1);
                    scrollCount = cindy.getWorldX() / getWidth();
                    cindy.setX(400);
                    cindy.move(direction);
                    cindy.moveInWorld(direction);
                    fiddle.setX(fiddle.getX() - 200);
                    scrollCount++;
                } 
            } else if (direction == -1 && cindy.getX() <= 200) {
                bg.move(-1);
                cindy.setX(400);
                cindy.moveInWorld(direction);
                 fiddle.setX(fiddle.getX() + 400);
            } else {
                cindy.move(direction);
                cindy.moveInWorld(direction);
            }
        }

    
        // Level 2 Update Logic (no horizontal movement, only vertical for level 2)
        if (levels[currentLevelIndex] instanceof Level2 level2) {
            Background bg = level2.getBackground();
            cindy.setY(400); 
            cindy.move(direction); 
            if (cindy.getX() >= 800) cindy.setX(800);
            if (cindy.getX() <= 0) cindy.setX(0);
        }
    
        // Level 3 Update Logic (Vertical scrolling for level 3)
        if (levels[currentLevelIndex] instanceof Level3 level3) {
            Background bg = level3.getBackground();
            bg.move(3); 
            if (cindy.getY() >= 400) {cindy.setY(400);}
            if(cindy.getX() <=100) cindy.setX(100);
            if (cindy.getX() >= 800) cindy.setX(800);
            cindy.move(direction); 
        }
    }
    
    public void gameRender() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2Buffer = screenBuffer.createGraphics();
        g2Buffer.clearRect(0, 0, screenBuffer.getWidth(), screenBuffer.getHeight());

    
        if (levels[currentLevelIndex] instanceof Level1 level1) {
            if (level1 != null) {
                level1.drawBackground(g2Buffer, cindy);  // Level 1
            }
            if (cindy != null) cindy.draw(g2Buffer);
            if (fiddle != null) fiddle.draw(g2Buffer, cindy);
            
        }

        if (levels[currentLevelIndex] instanceof Level2 level2) {
            // Show GIF intro at the start of Level 2
            if (showLevel2Intro && level2Gif != null) {
                long now = System.currentTimeMillis();
                if (now - level2IntroStartTime < LEVEL2_INTRO_DURATION_MS) {
                    g.drawImage(level2Gif.getImage(),
                                (getWidth() - level2Gif.getIconWidth()) / 2,
                                (getHeight() - level2Gif.getIconHeight()) / 2,
                                this);
                    return; // Skip drawing rest of Level 2 until GIF ends
                } else {
                    showLevel2Intro = false;
                }
            }
        
            // Regular Level 2 rendering
            level2.drawBackground(g2Buffer);
            if (cindy != null) cindy.draw(g2Buffer);
            if (dimjack != null) dimjack.draw(g2Buffer);
          
        }
        

        if (levels[currentLevelIndex] instanceof Level3 level3) {
            if (showLevel3SecondaryIntro && level3SecondaryGif != null) {
                long now = System.currentTimeMillis();
                if (now - level3SecondaryIntroStartTime < LEVEL3_SECONDARY_INTRO_DURATION_MS) {
                    g.drawImage(level3SecondaryGif.getImage(),
                                (getWidth() - level3SecondaryGif.getIconWidth()) / 2,
                                (getHeight() - level3SecondaryGif.getIconHeight()) / 2,
                                this);
                    return; 
                } else {
                    showLevel3SecondaryIntro = false;
                    isRunning = false;  
                    isPaused = true; 
                    return;
                }
            }
            
            // If not showing secondary, continue normal
            if (showLevel3Intro && level3Gif != null) {
                long now = System.currentTimeMillis();
                if (now - level3IntroStartTime < LEVEL3_INTRO_DURATION_MS) {
                    g.drawImage(level3Gif.getImage(),
                                (getWidth() - level3Gif.getIconWidth()) / 2,
                                (getHeight() - level3Gif.getIconHeight()) / 2,
                                this);
                    return; 
                } else {
                    showLevel3Intro = false;
                    return;
                }
            }
            

            level3.drawBackground(g2Buffer);
            if (cindy != null) cindy.draw(g2Buffer);
            if (menmon != null) menmon.draw(g2Buffer);
            for (Beam beam : beams) beam.draw(g2Buffer);
        }

        drawHearts(g2Buffer);

        g.drawImage(screenBuffer, 0, 0, this);
        g2Buffer.dispose();

        if (!gameStarted && startScreenImage != null) {
            g.drawImage(startScreenImage, 0, 0, getWidth(), getHeight(), null);
            return; 
        } 

        if (isPaused) {
            g.drawString("Paused", getWidth() / 2 - 50, getHeight() / 2);
        }
        
        if (gameOver && gameOverImage != null) {
            g.drawImage(gameOverImage, 
                (getWidth() - gameOverImage.getWidth()) / 2, 
                (getHeight() - gameOverImage.getHeight()) / 2, 
                null);
        }
    }

    private void drawHearts(Graphics2D g) {
        // Set the starting X and Y coordinates for the first heart
        int startX = 10;
        int startY = 10;
    
        // Draw the hearts for each remaining life
        for (int i = 0; i < lives; i++) {
            g.drawImage(heartImage, startX + i * (HEART_SIZE + 5), startY, HEART_SIZE, HEART_SIZE, null);
        }
    }
    public void startGame() {
    
        if (gameThread != null && gameThread.isAlive()) {
            isRunning = false;
            try {
                gameThread.join(); // Wait for the old thread to die
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    
        // Reset everything
        lives = 3;
        score = 0;
        gameOver = false;
        gameStarted = true;
        isPaused = false;
        currentLevelIndex = 0;
    
     
        levels = new Level[3];
        levels[0] = new Level1(this);
        levels[1] = new Level2(this);
        levels[2] = new Level3(this);
    
        cindy = new Cindy(400, 290, 200, 200, 1);
        fiddle = new Fiddle(10, 370, 120, 120);
        dimjack = new Dimjack(400, 50, 200, 200, cindy);
        menmon = new Menmon(450, 10, 200, 200, "images/menmon.png");
        beams = new ArrayList<>();
    
        createGameEntities(); // Setup level 1 entities
    
        // Start a new game thread
        gameThread = new Thread(this);
        gameThread.start();
    }    

    public void pauseGame() {
        if (isRunning) {
            isPaused = !isPaused; // toggle pause
        }
    }

    public void endGame() {
        isRunning = false;
        gameOver = true;
        isPaused = false;  // Just to be safe
    
        SoundManager.getInstance().stopAll(); 
    }

    public boolean isRunning(){
        return isRunning;
    }

    public boolean gameStarted(){
        return gameStarted;
    }

    public void nextLevel() {
        if (currentLevelIndex < levels.length - 1) {
            currentLevelIndex++;
            createGameEntities(); 
            try {
                Thread.sleep(1000); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
           
            System.out.println("Game Completed!");
          
        }
    }

    public int getLevel(){
        return this.currentLevelIndex;
    }

    public void restartLevel() {
        Level current = levels[currentLevelIndex];
        levels[currentLevelIndex] = new Level1(this);
    }

    public void setLevel(int levelIndex) {
        if (levelIndex >= 0 && levelIndex < levels.length) currentLevelIndex = levelIndex;
    }

    public Cindy getCindy() {
        return cindy;
    }

    private boolean checkCollisionCindyFiddle(Cindy c, Fiddle f) {
        return c.getWorldX() < f.getWorldX() + f.getWidth() &&
               c.getWorldX() + c.getWidth() > f.getWorldX() &&
               c.getY() < f.getY() + f.getHeight() &&
               c.getY() + c.getHeight() > f.getY();
    }
    
    public int getScrollCount() {
        return scrollCount;
    }

    public int getScore(){
        return cindy.getScore();
    }
    
    

    public int whereIsCindy() {
        return cindy.getX();
    }
}
