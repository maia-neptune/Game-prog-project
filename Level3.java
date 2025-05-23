import java.awt.Graphics2D;

public class Level3 implements Level {

    private final int level;
    private int score;
    private Background background;

    public Level3(GamePanel panel) {
        this.level = 2; // this is Level 3
        this.score = 0;
        this.background = new Background(panel, "images/level3_bg.png", 200);
    }

    public void drawBackground(Graphics2D g2) {
        background.draw(g2); 
    }

    public void scrollVertically(int dy) {
        background.moveDown();
    }

    public void moveBackground(int direction) {
        background.move(direction); 
    }

    public Background getBackground() {
        return this.background;
    }

    public void increaseScore(int n) {
        this.score += n;
    }

    public int getScore() {
        return this.score;
    }

    public int getLevel() {
        return this.level;
    }
}
