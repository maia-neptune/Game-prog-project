import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Beam {

    private int x, y;
    private int width = 20;
    private int height = 40;
    private int speed = 10;
    private Image image;

    public Beam(int x, int y) {
        this.x = x;
        this.y = y;
        this.image = ImageManager.loadBufferedImage("images/beam.png"); 
    }

    public void update() {
        y += speed; // Moves the beam downward
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }

    public boolean isOffScreen() {
        return y > 800; 
    }

    public boolean checkCollision(Cindy cindy) {
        Rectangle beamBounds = new Rectangle(x, y, width, height);
        Rectangle cindyBounds = new Rectangle(cindy.getX(), cindy.getY(), cindy.getWidth(), cindy.getHeight());
        return beamBounds.intersects(cindyBounds);
    }
}
