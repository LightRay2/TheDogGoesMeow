
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
}


class JavaPaintUI extends JFrame {
    //переменные
    private Timer timer;
    private Painter _painter = new Painter(this);
    private JPanel jPanelMain;

    //конструктор
    public JavaPaintUI() {
        initComponents();
        timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                (( Panel2)jPanelMain ).TimerTick();
            }
        }, 32,32);
    }

    private void initComponents() {
        jPanelMain = new Panel2();

        jPanelMain.setBackground(new java.awt.Color(255, 255, 255));
        jPanelMain.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        this.setContentPane(jPanelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    //точка входа
    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JavaPaintUI().setVisible(true);
            }
        });
    }


    //панель, котора по таймеру обновляет мир и запускает перерисовку
    class Panel2 extends JPanel {
        private WorldLogic world = new WorldLogic();
        Panel2() {
            setPreferredSize(new Dimension(800,600));
        }

        public void TimerTick(){
            world.update();
            jPanelMain.revalidate();
            jPanelMain.repaint();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            _painter.drawWorld(g, world.gameObjects, world.getTime(), world.getFoodInBowl());
        }


    }

}

