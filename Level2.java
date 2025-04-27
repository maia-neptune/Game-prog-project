import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Level2 implements Level {

    private final int level;
    private int score;
    private Background background;
    private ArrayList<CatTreat> catTreats = new ArrayList<>();
    private long lastTreatTime = 0;

    private final int TREAT_INTERVAL = 4000; // 4 seconds
    private final int MAX_TREATS = 10;
    private int treatsSpawned = 0;

    public Level2(GamePanel panel) {
        this.level = 1;
        this.score = 0;
        this.background = new Background(panel, "images/level2_bg.png", 200);
    }

    private void spawnTreat() {
    Random rand = new Random();
    int x = rand.nextInt(900) + 50;
    int y = rand.nextInt(200) + 400;
    catTreats.add(new CatTreat(x, y, 40, 40));
    treatsSpawned++;
}

    public void drawBackground(Graphics2D g2) {
    background.draw(g2);
    for (CatTreat treat : catTreats) treat.draw(g2);
    }
    

    public void update(Cindy cindy) {
    long now = System.currentTimeMillis();
    if (treatsSpawned < MAX_TREATS && now - lastTreatTime > TREAT_INTERVAL) {
        spawnTreat();
        lastTreatTime = now;
    }

    Iterator<CatTreat> it = catTreats.iterator();
    while (it.hasNext()) {
        if (it.next().checkCollision(cindy)) {
            it.remove();
           cindy.addScore(2);
           SoundManager.getInstance().playClip("cattreat", false);
        }
    }
}


    public void moveBackground(int direction) {
        background.move(direction);
    }

    public void increaseScore(int n) {
        this.score += n;
    }

    public int getScore() {
        return this.score;
    }

    public Background getBackground() {
        return background;
    }

    public int getLevel() {
        return this.level;
    }
}
