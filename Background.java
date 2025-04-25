import java.awt.Graphics2D;
import java.awt.Image;

public class Background {
  	private Image bgImage;
  	private int bgImageWidth;      		// width of the background (>= panel Width)
    private int bgImageHeight;

	private GamePanel panel;

 	private int bgX;			// X-coordinate of "actual" position

	private int bg1X;			// X-coordinate of first background
	private int bg2X;			// X-coordinate of second background
	private int bgDX;			// size of the background move (in pixels)


  private int bg1Y;			// Y-coordinate of first background
	private int bg2Y;			// Y-coordinate of second background
	private int bgDY;			// size of the background move (in pixels)


  public Background(GamePanel panel, String imageFile, int bgDX) {
    
	this.panel = panel;
    	this.bgImage = ImageManager.loadImage(imageFile);
    	bgImageWidth = bgImage.getWidth(null);	// get width of the background
      bgImageHeight = bgImage.getHeight(null);

	System.out.println ("bgImageWidth = " + bgImageWidth);

	if (bgImageWidth < panel.getWidth())
      		System.out.println("Background width < panel width");

    	this.bgDX = 200; // bgDX;

	bgX = 0;
	bg1X = 0;
	bg2X = bgImageWidth;

  this.bgDY = 8;

	bg1X = -100;
	bg2X = -100;
	bg1Y = 0;
	bg2Y = bgImageHeight;

  }


  public void move (int direction) {

	if (direction == 1)
		moveRight();
	else
	if (direction == -1)
		moveLeft();

  	if (direction == 3)
		moveDown();
  }


  public void moveLeft() {

    bgX = bgX - bgDX;

    bg1X = bg1X - bgDX;
    bg2X = bg2X - bgDX;

    if (bg1X + bgImageWidth <= 0) {
        bg1X = bg2X + bgImageWidth;
    }

    if (bg2X + bgImageWidth <= 0) {
        bg2X = bg1X + bgImageWidth;
    }

    System.out.println("Scrolling left: bg1X=" + bg1X + ", bg2X=" + bg2X);
}



public void moveRight() {
  bgX = bgX + bgDX;
      
  bg1X = bg1X + bgDX;
  bg2X = bg2X + bgDX;

  if (bg1X >= bgImageWidth) {
      bg1X = bg2X - bgImageWidth;
  }

  if (bg2X >= bgImageWidth) {
      bg2X = bg1X - bgImageWidth;
  }

  System.out.println("Scrolling right: bg1X=" + bg1X + ", bg2X=" + bg2X);
}


public void moveDown() {

	//bgX = bgX + bgDX;
				
	bg1Y = bg1Y + bgDY;
	bg2Y = bg2Y + bgDY;

	//String mess = "Moving background right: bgX=" + bgX + " bg1X=" + bg1X + " bg2X=" + bg2X;
	//System.out.println (mess);

	String mess = "Moving background down. bg1Y = " + bg1Y;
	System.out.println (mess);

	if (bg1Y > 0) {
		System.out.println ("Background change: bgX = " + bgX); 
		bg1Y = bgImageHeight * -1;
		bg2Y = 0;
	}

   }

 

   public void draw(Graphics2D g2) {
    int panelWidth = panel.getWidth();
    int panelHeight = panel.getHeight();

    if (panel.getLevel() == 0) {
        // Level 1: Horizontal scrolling
        g2.drawImage(bgImage, bg1X, 0, bgImageWidth, panelHeight, null);
        g2.drawImage(bgImage, bg2X, 0, bgImageWidth, panelHeight, null);
    }

    if(panel.getLevel() == 1) {
      g2.drawImage(bgImage, 0, 0, panelWidth, panelHeight, null);
    }

    if (panel.getLevel() == 2) {
        // Level 3: Vertical scrolling — FIXED
        g2.drawImage(bgImage, 0, bg1Y, panelWidth, bgImageHeight, null);
        g2.drawImage(bgImage, 0, bg2Y, panelWidth, bgImageHeight, null);
    }
}

}
