import java.awt.Graphics;
import java.awt.Image;

public class Cindy extends GameObject {

    private int x, y, width, height;
    private Animation walkingAnimation, jumpingAnimation;

    private Image footRight, footLeft, jumpImage, crouchImage;
    private Image  tfootR, tfootL;
    private Image level2Image;
    private Image level3Image;

    private int score;

    private int direction; // -1 = left, 1 = right, 0 = idle
    private boolean jumping = false;
    private double yVelocity = 0;
    private final double gravity = 0.8;
    private final double jumpStrength = -12;
    private final int groundY = 290;

    private int level_index;
    private int worldX = 400;
    private int worldY = 500;

    public Cindy(int x, int y, int width, int height, int level) {
        super(x, y, width, height, null);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.level_index = level;

        loadDefaultAssets();
        createAnimations();
    }

    private void loadDefaultAssets() {
        footRight = ImageManager.loadBufferedImage("images/cindy_level1_right.png");
        footLeft = ImageManager.loadBufferedImage("images/cindy_level1_left.png");
        crouchImage = ImageManager.loadBufferedImage("images/cindy_crouch.png");
        jumpImage = ImageManager.loadBufferedImage("images/cindy_jump.png");

        level2Image = ImageManager.loadBufferedImage("images/cindy_level_2.png");

        level3Image = ImageManager.loadBufferedImage("images/cindy_level_3.png");
        tfootL = ImageManager.loadBufferedImage("images/cindy3leftfoot.png");
        tfootR = ImageManager.loadBufferedImage("images/cindy3rightfoot.png");
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
        if (level_index == 0) {
            if (walkingAnimation != null) walkingAnimation.update();
            if (jumpingAnimation != null) jumpingAnimation.update();

            yVelocity += gravity;
            y += (int) yVelocity;

            if (y >= groundY) {
                y = groundY;
                yVelocity = 0;
                jumping = false;
            }
        }
    }

    public void draw(Graphics g) {
        Image currentImage;

        switch (level_index) {
            case 0 -> {
                if (jumping && jumpingAnimation != null && jumpingAnimation.isStillActive()) {
                    currentImage = jumpingAnimation.getImage();
                } else if (direction != 0 && walkingAnimation != null && walkingAnimation.isStillActive()) {
                    currentImage = walkingAnimation.getImage();
                } else {
                    currentImage = (direction == -1) ? footLeft : footRight;
                }
            }
            case 1, 2 -> currentImage = level2Image;
            default -> currentImage = footRight;
        }

        g.drawImage(currentImage, x, y, width, height, null);
    }

    public void move(int direction) {
        this.direction = direction;

        if(direction==1){
            this.x = x + 5;
        }
        if(direction==-1){
            this.x = x - 5; 
        }

        if (direction != 0 && walkingAnimation != null) {
            if (!walkingAnimation.isStillActive()) walkingAnimation.start();
        } else {
            walkingAnimation.stop();
        }

        if(level_index == 2){
        if(direction == 3){
            this.y = y + 10;
        }
        if(direction == 1){
            this.x = x +10;
            this.y = y + 10;
        }
        if(direction == -1){
            this.x = x - 10;
            this.y = y + 10;
        }
        }

        System.out.println("Cindy's X: " + worldX + " Y: " + y);
    }


    public void jump() {
        if (isOnGround()) {
            yVelocity = jumpStrength;
            jumping = true;
        }
    }

    public int getScore(){
        return this.score;
    }

    public void addScore(int score) { 
        this.score += score; } 

    private boolean isOnGround() {
        return y >= groundY;
    }

    public void land() {
        jumping = false;
        jumpingAnimation.stop();
    }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }

    public void setLevelIndex(int level) { this.level_index = level; }
    public int getLevelIndex() { return this.level_index; }

    public int getWorldX() { return this.worldX; }
    public void setWorldX(int worldX) { this.worldX = worldX; }

    public int getWorldY() { return this.worldY; }
    public void setWorldY(int worldY) { this.worldY = worldY; }

    @Override public int getX() { return this.x; }
    @Override public int getY() { return this.y; }

    public void moveInWorld(int direction) {
        this.direction = direction;
        worldX += direction * 5;
    }
}
