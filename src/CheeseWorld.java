//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.*;

//Keyboard and Mouse
//Step 0 - Import
import java.awt.event.*;


//*******************************************************************************
// Class Definition Section

//Step 1 for Keyboard control - implements Keylistener
public class CheeseWorld implements Runnable, KeyListener {

    //Variable Definition Section
    //Declare the variables used in the program
    //You can set their initial values too

    //Sets the width and height of the program window
    final int WIDTH = 1400;
    final int HEIGHT = 800;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;

    public Image cheesePic;
    public Image mousePic;
    public Image tomPic;
    public Image backgroundPic;
    public Image mousetrapPic;
    public Image deadmousePic;
    public SoundFile tomScream;
    public SoundFile1 tomLaugh;
    public SoundFile2 jerryLaugh;

    public Mouse mouse1;
    public Cheese[] cheeseArray;
    public Cheese theCheese;
    public Moustrap[] trapArray;
    public Moustrap theTrap;
    public int time;



    public Player user;
    public Player1 user1;

    public int counter=0;

     // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        CheeseWorld myApp = new CheeseWorld();   //creates a new instance of the game
        new Thread(myApp).start();                 //creates a threads & starts up the code in the run( ) method
    }


    // Constructor Method
    // This has the same name as the class
    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.
    public CheeseWorld() {

        setUpGraphics();

        //Step 3 - Keyboard use.  addKeyListener(this)
        canvas.addKeyListener(this);

        //variable and objects
        //load images
        backgroundPic = Toolkit.getDefaultToolkit().getImage("image.jpg");
        cheesePic = Toolkit.getDefaultToolkit().getImage("cheese.gif"); //load the picture
        mousePic = Toolkit.getDefaultToolkit().getImage("jerry.gif"); //load the picture
        tomPic = Toolkit.getDefaultToolkit().getImage("tomCat.png"); //load the picture
        mousetrapPic = Toolkit.getDefaultToolkit().getImage("Mousetrap.png");
        deadmousePic = Toolkit.getDefaultToolkit().getImage("deadmouse.png");
        //create (construct) the objects needed for the game
        user1 = new Player1 (700, 300,4,4,mousePic);
        theCheese = new Cheese(1200, 900, 0, 0, cheesePic);
        user = new Player(250, 250, 3, 3, tomPic);
        tomScream = new SoundFile("scream.wav");
        tomLaugh = new SoundFile1 ("Laugh.wav");
        jerryLaugh = new SoundFile2("JerryLaugh.wav");






        //construct array
        cheeseArray = new Cheese[10];

        for (int i = 0; i < cheeseArray.length; i++) {

            cheeseArray[i] = new Cheese((int) (Math.random() * 1300) + 1, (int) (Math.random() * 300) + 450, (int) (Math.random() * 3), (int) (Math.random() * 4), cheesePic);

        }
        trapArray = new Moustrap[3];
        for (int i = 0; i < trapArray.length; i++){
            trapArray[i] = new Moustrap((int) (Math.random()*1300)+1, (int) (Math.random()* 150)+585,0,0,mousetrapPic);
        }


    }// CheeseWorld()


//*******************************************************************************
//User Method Section
//
// put your code to do things here.

    // main thread
    // this is the code that plays the game after you set things up

    public void moveThings() {
        //calls the move( ) code in the objects
        user1.move();
        theCheese.move();
        user.move();
        for (int i = 0; i < cheeseArray.length; i++) {
            cheeseArray[i].move();
        }
    }

    public void checkIntersections() {
      if (user1.rec.intersects(user.rec)&& user1.width<130&&user1.isAlive==true){
          user1.isAlive = false;
          tomLaugh.play();

      }
        if (user1.rec.intersects(user.rec)&& user1.width>=130&&user.isAlive==true ){
            user.isAlive = false;
            jerryLaugh.play();
        }


        for (int i = 0; i < cheeseArray.length; i++){
            if (user1.rec.intersects(cheeseArray[i].rec)&& cheeseArray[i].isAlive ==true) {
                user1.width = user1.width + 3;
                counter=counter+1000;

            }

        }
        for (int i = 0; i < cheeseArray.length; i++){
            if (user1.rec.intersects(cheeseArray[i].rec)&& cheeseArray[i].isAlive ==true) {
                user1.height = user1.height + 3;
            }

        }
        for (int i = 0; i < cheeseArray.length; i++){


            if (user1.rec.intersects(cheeseArray[i].rec)) {
                cheeseArray[i].isAlive = false;
            }
        }
        for (int i = 0; i < trapArray.length; i++){
            if (user1.rec.intersects(trapArray[i].rec)&&user1.isAlive==true){
                user1.isAlive = false;
                tomLaugh.play();
            }
        }

        for (int i = 0; i < trapArray.length; i++){
            if(user.rec.intersects(trapArray[i].rec)&& user.isintersecting==false){
                user.isintersecting = true;
                tomScream.play();
                System.out.println("test");

            }
            if(!user.rec.intersects(trapArray[i].rec)){
               //s user.isintersecting=false;

            }
        }



    }

    //paints things on the screen using bufferStrategy
    public void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //put your code to draw things on the screen here.
        g.drawImage(backgroundPic,0,0, 1400,800, null);

        if (user1.isAlive == true){
        g.drawImage(user1.pic, user1.xpos, user1.ypos, user1.width, user1.height, null);}
       if(user1.isAlive==false){

          g.drawImage(deadmousePic, user1.xpos, user1.ypos, user1.width, user1.height, null);

        }
        g.drawImage(theCheese.pic, theCheese.xpos, theCheese.ypos, theCheese.width, theCheese.height, null);
       if (user.isAlive ==true) {
           g.drawImage(user.pic, user.xpos, user.ypos, user.width, user.height, null);
       }

        for (int i = 0; i < trapArray.length; i++){
            g.drawImage(trapArray[i].pic,trapArray[i].xpos,trapArray[i].ypos,trapArray[i].width,trapArray[i].height, null );
        }


        for (int i = 0; i < cheeseArray.length; i++) {
            if (cheeseArray[i].isAlive == true) {
                g.drawImage(cheeseArray[i].pic, cheeseArray[i].xpos, cheeseArray[i].ypos, cheeseArray[i].width, cheeseArray[i].height, null);
            }
        }
        if (user1.isAlive==false) {
            g.setColor(Color.RED);
            g.setFont(new Font("Courier", Font.BOLD, 46));
            g.drawString("TOM WINS: GAME OVER", 450, 400);
        }
        if (user.isAlive==false) {
            g.setColor(Color.RED);
            g.setFont(new Font("Courier", Font.BOLD, 46));
            g.drawString("JERRY WINS: GAME OVER", 450, 400);
        }
        g.setColor(Color.RED);
        g.setFont(new Font("Courier", Font.BOLD, 46));
        g.drawString("SCORE:"+ counter, 10, 34);
        g.dispose();
        bufferStrategy.show();
    }

    public void run() {

        //for the moment we will loop things forever.
        while (true) {

            moveThings();  //move all the game objects
            checkIntersections();
            render();  // paint the graphics
            pause(20); // sleep for 10 ms
            time++;
            counter = counter + 10;
            }


        }


    //Graphics setup method
    public void setUpGraphics() {
        frame = new JFrame("CheeseWorld");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");

    }

    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time) {
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }

    //*******************************************************************************
    //  The Required 3 Keyboard Methods
    //  You need to have all 3 even if you aren't going to use them all
    //*******************************************************************************

    public void keyPressed(KeyEvent event) {
        //This method will do something whenever any key is pressed down.
        //Put if( ) statements here
        char key = event.getKeyChar();     //gets the character of the key pressed
        int keyCode = event.getKeyCode();  //gets the keyCode (an integer) of the key pressed
        System.out.println("Key Pressed: " + key + "  Code: " + keyCode);

    if (keyCode == 68) {
        user.right = true;
    }
    if (keyCode == 83) {
        user.down = true;
    }
    if (keyCode == 87) {
        user.up = true;
    }
    if (keyCode == KeyEvent.VK_A) {
        user.left = true;
    }
    if (user1.isAlive==true) {
        if (keyCode == 39) {
            user1.right = true;
        }
        if (keyCode == 40) {
            user1.down = true;
        }
        if (keyCode == 38) {
            user1.up = true;
        }
        if (keyCode == 37) {
            user1.left = true;
        }
    }

    }//keyPressed()

    public void keyReleased(KeyEvent event) {
        char key = event.getKeyChar();
        int keyCode = event.getKeyCode();
        //This method will do something when a key is released

            if (keyCode == 68) {
                user.right = false;
            }
            if (keyCode == 83) {
                user.down = false;
            }
            if (keyCode == 87) {
                user.up = false;
            }
            if (keyCode == KeyEvent.VK_A) {
                user.left = false;
            }
            if (user1.isAlive==true) {
                if (keyCode == 39) {
                    user1.right = false;
                }
                if (keyCode == 40) {
                    user1.down = false;
                }
                if (keyCode == 38) {
                    user1.up = false;
                }
                if (keyCode == 37) {
                    user1.left = false;

                }
            }

    }//keyReleased()

    public void keyTyped(KeyEvent event) {
    // handles a press of a character key  (any key that can be printed but not keys like SHIFT)
    // we won't be using this method but the definition needs to be in your program
    }//keyTyped()


}//class
