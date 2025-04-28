import java.awt.Graphics;
import java.awt.Image;

public class CatTreat {
    private int x, y, width, height;
    private boolean collected = false;
    private Image image;

    public CatTreat(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = ImageManager.loadBufferedImage("images/cat_treats.png"); // Make sure this exists
    }

    // Draw for level with side-scrolling (Level 1)
    public void draw(Graphics g, Cindy cindy) {
        if (!collected) {
            int screenX = x - cindy.getWorldX() + cindy.getX(); // Align treat with Cindy’s scrolling
            g.drawImage(image, screenX, y, width, height, null);
        }
    }

    // Draw for non-scrolling levels (Level 2)
    public void draw(Graphics g) {
        if (!collected) {
            g.drawImage(image, x, y, width, height, null);
        }
    }

    public boolean checkCollision(Cindy cindy) {
        int cx = (cindy.getLevelIndex() == 0) ? cindy.getWorldX() : cindy.getX();
        int cy = cindy.getY();
    
        return cx < x + width &&
               cx + cindy.getWidth() > x &&
               cy < y + height &&
               cy + cindy.getHeight() > y;
    }

    public void collect() {
        collected = true;
    }

    public boolean isCollected() {
        return collected;
    }
}
