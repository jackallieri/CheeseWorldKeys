
import java.awt.*;

public class Player {


    public int xpos;                //the x position
    public int ypos;                //the y position
    public int width;
    public int height;
    public boolean isAlive;            //a boolean to denote if the hero is alive or dead.
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public Rectangle rec;
    public Image pic;

    //movement booleans
    public boolean right;
    public boolean down;
    public boolean left;
    public boolean up;
    public boolean isintersecting;


    public Player(int pXpos, int pYpos, int dxParameter, int dyParameter, Image picParameter) {

        xpos = pXpos;
        ypos = pYpos;
        width = 175;
        height = 225;
        dx = dxParameter;
        dy = dyParameter;
        pic = picParameter;
        isAlive = true;
        rec = new Rectangle(xpos, ypos, width, height);
        isintersecting = false;

    } // constructor

    //move( ) method for a keyboard controlled character
    public void move() {

        if (right) {
            xpos = xpos + dx;
            if (xpos > 1400 - width) {
                xpos = 1400 - width;
            }
        }
        if (left) {
            xpos = xpos - dx;
            if (xpos < 0 ) {
                xpos = 0 ;
            }
        }
        if (up) {
            ypos = ypos - dy;
            if (ypos < 0 ) {
                ypos = 0 ;
            }
        }
        if (down) {
            ypos = ypos + dy;
            if (ypos > 800 - height) {
                ypos = 800 - height;
            }


            //always put this after you've done all the changing of the xpos and ypos values
            rec = new Rectangle(xpos, ypos, width, height);

        }


    }
}