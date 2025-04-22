import java.awt.Graphics;
import java.awt.Image;

public class Cindy extends GameObject {

    private int x;
    private int y;
    private int width;
    private int height;

    private Animation walkingAnimation;
    private Animation jumpingAnimation;

    private Image footRight;
    private Image footLeft;
    private Image jumpImage;
    private Image crouchImage;

    private int direction; // -1 = left, 1 = right, 0 = idle
    private boolean jumping=false; 
    private double yVelocity = 0;
    private final double gravity = 0.8;
    private final double jumpStrength = -12; // negative goes up
    private final int groundY = 290; // the Y-coordinate of the floor (adjust as needed)
    

    public Cindy(int x, int y, int width, int height) {
        super(x, y, width, height, null);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        loadDefaultAssets();
        createAnimations();
    }

    private void loadDefaultAssets() {
        footRight = ImageManager.loadBufferedImage("images/cindy_level1_right.png");
        footLeft = ImageManager.loadBufferedImage("images/cindy_level1_left.png");
        crouchImage = ImageManager.loadBufferedImage("images/cindy_crouch.png");
        jumpImage = ImageManager.loadBufferedImage("images/cindy_jump.png");
    }

    private void createAnimations() {
        walkingAnimation = new Animation(true);
        walkingAnimation.addFrame(footRight, 100);
        walkingAnimation.addFrame(footLeft, 100);

        jumpingAnimation = new Animation(false);
        jumpingAnimation.addFrame(crouchImage, 100);
        jumpingAnimation.addFrame(jumpImage, 100);
    }

    public void update() {
        if (walkingAnimation != null) walkingAnimation.update();
        if (jumpingAnimation != null) jumpingAnimation.update();
    
        // Always apply gravity if not on the ground
        yVelocity += gravity;
        y += (int) yVelocity;
    
        // Ground collision
        if (y >= groundY) {
            y = groundY;
            yVelocity = 0;
            jumping = false;
        }
    }
    
       
        

    public void draw(Graphics g) {
        Image currentImage;

        if (jumping && jumpingAnimation != null && jumpingAnimation.isStillActive()) {
            currentImage = jumpingAnimation.getImage();
        } else if (direction != 0 && walkingAnimation != null && walkingAnimation.isStillActive()) {
            currentImage = walkingAnimation.getImage();
        } else {
            currentImage = (direction == -1) ? footLeft : footRight;
        }

        g.drawImage(currentImage, x, y, width, height, null);
    }

    public void move(int direction) {
      
            this.direction = direction;
            x += direction * 4;
        
            if (direction != 0) {
                if (!walkingAnimation.isStillActive()) {
                    walkingAnimation.start();
                }
            } else {
                walkingAnimation.stop();
            }
    }

    
    public void jump() {
        if (isOnGround()) {
            yVelocity = jumpStrength;
            jumping = true;
        }
    }

    private boolean isOnGround() {
        return y >= groundY;
    }
    
    
    


    public void land() {
        jumping = false;
        jumpingAnimation.stop();
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
