
import java.awt.Image;
public class Level1 implements Level{
    
    private final int level;
    private int score;
    ImageManager imageManager;
    Image backgroundImage;
    

    public Level1(){
        imageManager = new ImageManager();
        level = 1;
        score = 0;
    }

    public void setBackground(){
        backgroundImage = ImageManager.loadBufferedImage("images\\level1_bg1.png");
    }

    public Image getBackground(){
        return this.backgroundImage;
    }

    public void increaseScore(int n){
        this.score = score + n;
    }

    public int getScore(){
        return this.score;
    }

    public int getLevel(){
        return this.level;
    }
}
