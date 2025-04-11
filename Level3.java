public class Level3 implements Level{
    
    private final int level;
    private int score;

    public Level3(){
        level = 3;
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
