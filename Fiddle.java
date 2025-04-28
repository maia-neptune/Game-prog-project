import java.awt.Graphics;
import java.awt.Image;

public class Fiddle extends GameObject {

    private int x;
    private int y;

    private int worldX = 10; 

    private int width;
    private int height;

    private Animation walkingAnimation;
    private Animation jumpingAnimation;

    private Image fiddle;       // idle or facing left
    private Image fiddleRun;    // moving right
    private Image jumpImage;

    private int direction; // -1 = left, 1 = right, 0 = idle
    private boolean jumping = false;
    private double yVelocity = 0;
    private final double gravity = 0.8;
    private final double jumpStrength = -12;
    private final int groundY = 370;

    public Fiddle(int x, int y, int width, int height) {
        super(x, y, width, height, null);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        loadAssets();
        createAnimations();
    }

    private void loadAssets() {
        fiddle = ImageManager.loadBufferedImage("images/fiddel_300.png");
        fiddleRun = ImageManager.loadBufferedImage("images/fiddel_run.png");
        jumpImage = ImageManager.loadBufferedImage("images/fiddel_jump.png");
    }

    private void createAnimations() {
        walkingAnimation = new Animation(true);
        walkingAnimation.addFrame(fiddle, 100);
        walkingAnimation.addFrame(fiddleRun,200);

        jumpingAnimation = new Animation(false);
        jumpingAnimation.addFrame(fiddle, 100);
        jumpingAnimation.addFrame(jumpImage, 100);
    }

    public void move(int direction) {
        this.direction = direction;
        x += direction * 4;
        worldX += direction * 4;
    
        if (direction != 0) {
            if (!walkingAnimation.isStillActive()) {
                walkingAnimation.start();
            }
        } else {
            walkingAnimation.stop();
        }
    }
    

    // Automatically follows Cindy and jumps if she's above
    public void update(Cindy cindy) {
        // Use worldX difference to always keep chasing
        int worldDistance = cindy.getWorldX() - this.worldX;
    
        if (worldDistance > 0) {
            move(1); // move right
        } else if (worldDistance < 0) {
            move(-1); // move left
        } else {
            move(0); // stop if directly aligned
        }
    
    
        if (!jumping && cindy.getY() < groundY - 10 && Math.abs(worldDistance) < 150) {
            jump();
        }
    
    
        if (jumping) {
            yVelocity += gravity;
            y += (int) yVelocity;
    
            if (y >= groundY) {
                y = groundY;
                yVelocity = 0;
                jumping = false;
            }
        }
    
        walkingAnimation.update();
        jumpingAnimation.update();
    }
    
    public void jump() {
        if (!jumping) {
            yVelocity = jumpStrength;
            jumping = true;
        }
    }

    public void draw(Graphics g, Cindy cindy) {
        int fiddleScreenX = worldX - cindy.getWorldX() + cindy.getX();
        Image currentImage;
    
        if (jumping && jumpingAnimation != null && jumpingAnimation.isStillActive()) {
            currentImage = jumpingAnimation.getImage();
        } else if (direction != 0 && walkingAnimation != null && walkingAnimation.isStillActive()) {
            currentImage = walkingAnimation.getImage();
        } else {
            currentImage = fiddle;
 } 
    
        g.drawImage(currentImage, fiddleScreenX, y, width, height, null);
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    @Override
    public int getX() { return x; }
    @Override
    public int getY() { return y; }
    @Override
    public int getWidth() { return width; }
    @Override
    public int getHeight() { return height; }
}  