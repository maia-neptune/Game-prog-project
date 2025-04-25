import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Level1 implements Level {
    private final int level;
    private int score;
    private Background background;
    private Background background2;
    private ArrayList<CatTreat> catTreats;
    private GamePanel panel;

    private long lastTreatSpawnTime = 0;
    private long lastWindGustTime = 0;
    private boolean gustActive = false;
    private long gustStartTime;

    private final int TREAT_INTERVAL = 4000; // 4 seconds
    private final int MAX_TREATS = 10;
    private int treatsSpawned = 0;

    private int fiddleSpeed = 2;
    private long lastFiddleBoostTime;
    private final long FIDDLE_BOOST_INTERVAL = 10000;

    public Level1(GamePanel panel) {
        this.level = 0;
        this.score = 0;
        this.panel = panel;
        this.background = new Background(panel, "images/level1_bg1.png", 10);
        this.background2 = new Background(panel, "images/level1_bg2.png", 10);
        this.catTreats = new ArrayList<>();
        this.lastFiddleBoostTime = System.currentTimeMillis();
    }


    public void drawBackground(Graphics2D g2, Cindy cindy) {
        if (cindy.getWorldX() <2000) {
            background.draw(g2);
        } else {
            background2.draw(g2);
        }

        for (CatTreat treat : catTreats) {
            treat.draw(g2, cindy); 
        }
}
    
    

    public void moveBackground(int direction) {
        background.move(direction);
    }

    public void update(Cindy cindy, Fiddle fiddle) {
        long now = System.currentTimeMillis();

        // === Treat Spawning ===
        if (treatsSpawned < MAX_TREATS && now - lastTreatSpawnTime >= TREAT_INTERVAL) {
            spawnTreat(cindy.getWorldX());
            lastTreatSpawnTime = now;
            treatsSpawned++;
        }

        // === Wind Gusts ===
        if (!gustActive && now - lastWindGustTime > 15000) {
            gustActive = true;
            gustStartTime = now;
            lastWindGustTime = now;
        }

        if (gustActive) {
            cindy.setX(cindy.getX() - 1);
            if (now - gustStartTime > 1000) gustActive = false;
        }

        Iterator<CatTreat> it = catTreats.iterator();
        while (it.hasNext()) {
            CatTreat treat = it.next();
            if (treat.checkCollision(cindy)) {
                it.remove();
                cindy.addScore(2);
            }
        }
        if (now - lastFiddleBoostTime >= FIDDLE_BOOST_INTERVAL) {
            fiddleSpeed++;
            lastFiddleBoostTime = now;
        }

        fiddle.update(cindy);
    }

    private void spawnTreat(int cindyWorldX) {
        Random rand = new Random();
        int x = cindyWorldX + 200;
        int y = rand.nextInt(41) + 230;
        catTreats.add(new CatTreat(x, y, 40, 40));
        System.out.println("Spawned treat at X: " + x); 
    }

    public Background getBackground() {
        return background;
    }

    public ArrayList<CatTreat> getCatTreats() {
        return catTreats;
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public void increaseScore(int n) {
        score += n;
    }
}