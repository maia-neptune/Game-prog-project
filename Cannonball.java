import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
public class Cannonball {
    private double x, y;
    private double vx, vy; // velocity components
    private final double speed = 7.0;
    private final int radius = 10;
    private final int screenHeight = 750;

    private Image image;

    public Cannonball(int startX, int startY, int targetX, int targetY) {
        this.x = startX;
        this.y = startY;

        // Direction vector (normalize)
        double dx = targetX - startX;
        double dy = targetY - startY;
        double dist = Math.sqrt(dx * dx + dy * dy);

        this.vx = speed * dx / dist;
        this.vy = speed * dy / dist;

        this.image = ImageManager.loadBufferedImage("images/cannonball.png");
    }

    public void update() {
        x += vx;
        y += vy;
    }

    public boolean isOffScreen() {
        return y > screenHeight;
    }

    public boolean checkCollision(Cindy cindy) {
        Rectangle ballBounds = new Rectangle((int)x, (int)y, radius * 2, radius * 2);
        Rectangle cindyBounds = new Rectangle(cindy.getX()-50, cindy.getY()+50, (cindy.getWidth() - 100), (cindy.getHeight() - 100));
        return ballBounds.intersects(cindyBounds);
    }

    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, radius * 2, radius * 2, null);
    }
}
