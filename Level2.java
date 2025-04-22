import java.awt.Graphics2D;

public class Level2 implements Level {

    private final int level;
    private int score;
    private Background background;

    public Level2(GamePanel panel) {
        this.level = 1;
        this.score = 0;
        this.background = new Background(panel, "images/level1_bg1.png", 200);
    }

    public void drawBackground(Graphics2D g2) {
        background.draw(g2);
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

    public int getLevel() {
        return this.level;
    }
}
