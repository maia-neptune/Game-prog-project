public class Level1 implements Level{
    
    private final int level;
    private int score;

    public Level1(){
        level = 1;
        score = 0;
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
