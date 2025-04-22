import java.awt.Graphics;
import java.awt.Image;

public class Fiddle extends GameObject {

    private int x;
    private int y;
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
    private final int groundY = 300;

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
        walkingAnimation = new Animation(false);
        walkingAnimation.addFrame(fiddle, 100);
        walkingAnimation.addFrame(fiddleRun, 100);

        jumpingAnimation = new Animation(false);
        jumpingAnimation.addFrame(fiddle, 100);
        jumpingAnimation.addFrame(jumpImage, 100);
    }

    // Automatically follows Cindy and jumps if she's above
    public void update(Cindy cindy) {
        if (cindy.getX() > x) {
            x += 2;
            direction = 1;
        } else if (cindy.getX() < x) {
            x -= 2;
            direction = -1;
        } else {
            direction = 0;
        }

        if (direction != 0) {
            walkingAnimation.start();
        } else {
            walkingAnimation.stop();
        }

        if (!jumping && cindy.getY() < y - 40) {
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

    public void draw(Graphics g) {
        Image currentImage;

        if (jumping && jumpingAnimation != null && jumpingAnimation.isStillActive()) {
            currentImage = jumpingAnimation.getImage();
        } else if (direction != 0 && walkingAnimation != null && walkingAnimation.isStillActive()) {
            currentImage = walkingAnimation.getImage();
        } else {
            currentImage = (direction == -1) ? fiddle : fiddleRun;
        }

        g.drawImage(currentImage, x, y, width, height, null);
    }

    // Getters for collision detection
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
