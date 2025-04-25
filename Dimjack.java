import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;

public class Dimjack {
    private int x, y;
    private int width, height;
    private Image image;
    private ArrayList<Cannonball> cannonballs;
    private int firedCount = 0;
    private Cindy cindy;
    private long lastFiredTime = 0;
    private final long FIRE_DELAY = 3000; // milliseconds between shots 


    public Dimjack(int x, int y, int h, int w, Cindy cindy) {
        this.x = x;
        this.y = y;
        this.height = h;
        this.width = w;
        this.cindy = cindy;
        this.image = ImageManager.loadBufferedImage("images/DimjackCanon.png");
        this.cannonballs = new ArrayList<>();
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
    
        if (firedCount < 15 && currentTime - lastFiredTime > FIRE_DELAY) {
            cannonballs.add(new Cannonball(x + width / 2, y + height, 
                cindy.getX() + cindy.getWidth() / 2, cindy.getY() + cindy.getHeight() / 2));
            firedCount++;
            lastFiredTime = currentTime; // reset cooldown
        }
    
        Iterator<Cannonball> it = cannonballs.iterator();
        while (it.hasNext()) {
            Cannonball c = it.next();
            c.update();
            if (c.isOffScreen()) {
                it.remove();
            }
        }
    }

    public ArrayList<Cannonball> getCannonballs() {
        return cannonballs;
    }

    public int getFiredCount() {
        return firedCount;
    }
    

    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
        for (Cannonball c : cannonballs) {
            c.draw(g);
        }
    }
}
