public class Cindy extends GameObject {
       
    private int x;
    private int y;
    private int width;
    private int height;
    private String imagePath;
    
    public Cindy(int x, int y, int width, int height, String imagePath){
        super(x, y, width, height, imagePath);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imagePath = imagePath;
    }
    
    public void setX(int x){
        this.x = x;
    }
    
    public void setY(int y){
        this.y = y;
    }
    
    public void setWidth(int width){
        this.width = width;
    }
    
    public void setHeight(int height){
        this.height = height;
    }
    
    public void setImagePath(String imagePath){
        this.imagePath = imagePath;
    }
}
