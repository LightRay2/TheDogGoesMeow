
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
public class FormMain {
    //this.JPa
}


class JavaPaintUI extends JFrame {

    private int tool = 1;
    int currentX, currentY, oldX, oldY;
    Timer timer;
    Panel2 _panel;
    Painter _painter = new Painter(this);
    public JavaPaintUI() {
        initComponents();
        timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                (( Panel2)jPanel2 ).TimerTick();
            }
        }, 32,32);
    }

    private void initComponents() {
        // we want a custom Panel2, not a generic JPanel!
        jPanel2 = new Panel2();

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jPanel2.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                jPanel2MousePressed(evt);
            }
            public void mouseReleased(MouseEvent evt) {
                jPanel2MouseReleased(evt);
            }
        });
        jPanel2.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent evt) {
                jPanel2MouseDragged(evt);
            }
        });

        // add the component to the frame to see it!
        this.setContentPane(jPanel2);
        // be nice to testers..
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }// </editor-fold>



    private void jPanel2MouseDragged(MouseEvent evt) {
        if (tool == 1) {
            currentX = evt.getX();
            currentY = evt.getY();
            oldX = currentX;
            oldY = currentY;
            System.out.println(currentX + " " + currentY);
            System.out.println("PEN!!!!");
        }
    }

    private void jPanel2MousePressed(MouseEvent evt) {
        oldX = evt.getX();
        oldY = evt.getY();
        System.out.println(oldX + " " + oldY);
    }


    //mouse released//
    private void jPanel2MouseReleased(MouseEvent evt) {
        if (tool == 2) {
            currentX = evt.getX();
            currentY = evt.getY();
            System.out.println("line!!!! from" + oldX + "to" + currentX);
        }
    }

    //set ui visible//
    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JavaPaintUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private JPanel jPanel2;
    // End of variables declaration

    // This class name is very confusing, since it is also used as the
    // name of an attribute!
    //class jPanel2 extends JPanel {
    class Panel2 extends JPanel {

        Panel2() {
            // set a preferred size for the custom panel.
            setPreferredSize(new Dimension(800,600));
        }

        double _x = 100;
        WorldLogic world = new WorldLogic();
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            _painter.drawWorld(g, world.gameObjects, world.time, world.foodInBowl);
            /*
            g.drawString("BLAH", 20, 20);
            g.drawRect((int)_x, 200, 200, 200);
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File("images\\bed.png"));
            } catch (IOException e) {

            }
            g.drawImage(img,30+(int)_x,40,null);*/
        }

        public void TimerTick(){
            world.update();
            jPanel2.revalidate();
            jPanel2.repaint();
        }
    }

}

