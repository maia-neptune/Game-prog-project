import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;

public class Menmon extends GameObject {

    private int x, y, width, height;
    private ArrayList<Beam> beams;

    private int beamCooldown = 0;
    private final int MAX_BEAMS = 10;
    private int beamsFired = 0;

    private Image leftFoot;
    private Image rightFoot;
    private Animation walkingAnimation;

 
    private int directionY; // -1 = up, 1 = down, 0 = idle
    private int worldY = 0; 

    public Menmon(int x, int y, int width, int height, String imagePath) {
        super(x, y, width, height, imagePath);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

   
        this.leftFoot = ImageManager.loadBufferedImage("images/menonleftfoot.png");
        this.rightFoot = ImageManager.loadBufferedImage("images/menonrightfoot.png");

   
        walkingAnimation = new Animation(true);
        walkingAnimation.addFrame(leftFoot, 200);
        walkingAnimation.addFrame(rightFoot, 200);

        this.beams = new ArrayList<>();
    }

   
    public void update(Cindy cindy) {
        boolean isMoving = false;

        // Move vertically toward Cindy
        if (cindy.getY() > y && Math.abs(cindy.getY() - y) > 300) { 
            y += 1;
            isMoving = true;
        } else if (cindy.getY() < y && Math.abs(cindy.getY() - y) > 300) {
            y -= 1;
            isMoving = true;
        }

        if (isMoving) {
            if (!walkingAnimation.isStillActive()) {
                walkingAnimation.start();
            }
        } else {
            walkingAnimation.stop();
        }
        walkingAnimation.update();

   
        if (beamCooldown == 0 && beamsFired < MAX_BEAMS) {
            beams.add(new Beam(cindy.getX(), y + height));
            beamsFired++;
            beamCooldown = 50;
        }

        if (beamCooldown > 0) beamCooldown--;

        // Update and remove off-screen beams
        Iterator<Beam> it = beams.iterator();
        while (it.hasNext()) {
            Beam b = it.next();
            b.update();
            if (b.isOffScreen()) {
                it.remove();
            }
        }
    }

    // Render Menmon on screen
    public void draw(Graphics g) {
        Image currentImage = walkingAnimation.isStillActive() ? walkingAnimation.getImage() : rightFoot;
        g.drawImage(currentImage, x, y, width, height, null);

        for (Beam b : beams) {
            b.draw(g);
        }
    }

  
    public int getWorldY() {
        return worldY;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
