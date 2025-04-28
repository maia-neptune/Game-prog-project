import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
public class Cannonball {
    private double x, y;
    private double vx, vy; // velocity components
    private final double speed = 7.0;
    private final int screenHeight = 750;

    private int width, height;

    private Image image;

    public Cannonball(int startX, int startY, int targetX, int targetY) {
        this.x = startX;
        this.y = startY;

        this.width = 30; 
        this.height = 30;

        // direction vector
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
        int shrink = 10; 

        if ((int)y > cindy.getY() + cindy.getHeight()) {
            return false;
        }
        
        Rectangle ballBounds = new Rectangle(
            (int)x + shrink, 
            (int)y + shrink, 
            width - (shrink * 2), 
            height - (shrink * 2)
        );
    
        Rectangle cindyBounds = new Rectangle(
            cindy.getX(), 
            cindy.getY()+50, 
            cindy.getWidth(), 
            cindy.getHeight()
        );
    
        return ballBounds.intersects(cindyBounds);
    }

    public void draw(Graphics g) {
        g.drawImage(image, (int)x, (int)y, width, height, null);
    }
}
