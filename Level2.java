public class Level2 implements Level{
    
    private final int level;
    private int score;

    public Level2(){
        level = 2;
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
