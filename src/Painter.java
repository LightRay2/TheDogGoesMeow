import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lost on 28.09.14.
 */
class Painter {
    Map<GameObject.Type,Image > gameObjectSprites = new HashMap<GameObject.Type,Image >();

    //в конструкторе грузим картинки
    public Painter(Frame parentFrame){
        try{
            gameObjectSprites.put(GameObject.Type.bed, ImageIO.read(new File("images\\bed.png")));
            gameObjectSprites.put(GameObject.Type.bowl, ImageIO.read(new File("images\\bowl.png")));
            gameObjectSprites.put(GameObject.Type.dog, ImageIO.read(new File("images\\dog.png")));
            gameObjectSprites.put(GameObject.Type.freesbi, ImageIO.read(new File("images\\freesbi.png")));
            gameObjectSprites.put(GameObject.Type.man, ImageIO.read(new File("images\\man.png")));
        }catch(IOException e){
            JOptionPane.showMessageDialog(parentFrame, "Ошибка в ходе загрузки изображений");
        }
    }

    //все рисуем
    public void drawWorld(Graphics g, ArrayList<GameObject> gameObjects, int time, double foodCount){
        drawBackground(g);
        drawGameObjects(g, gameObjects);
        drawNight(g,time);
        drawTime(g,time);
        drawFoodCountString(g, foodCount);
    }

    void drawBackground(Graphics g){
        g.setColor(Color.green);
        g.fillRect(0, 0, 800, 600);
    };

    void drawGameObjects(Graphics g, ArrayList<GameObject> gameObjects){
        for(int i = 0; i < gameObjects.size();i++){
            Image img = gameObjectSprites.get(gameObjects.get(i).getType());
            g.drawImage(img,
                    (int)gameObjects.get(i).getX() - img.getWidth(null)/2,
                    (int)gameObjects.get(i).getY() - img.getHeight(null)/2
                    ,null);
        }
    }

    void drawTime(Graphics g,int time){
        g.setColor(Color.orange);
        g.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g.drawString( (time/60<10? "0":"")+Integer.toString(time/60)+":"
                +(time%60<10? "0":"")+ Integer.toString(time%60), 380, 40);
    }

    void drawFoodCountString(Graphics g, double foodCount){
        g.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g.setColor(Color.orange);
        g.drawString("Еды в миске: "+ Integer.toString((int)(foodCount*100))+ "%", 600,40 );
    }

    void drawNight(Graphics g, int time){
        double lightness = 0.2; //night
        if(time > 6*60 && time < 10*60)  //morning
            lightness = 0.2 + 0.8 * (double)(time - 6*60)/240;
        else if(time >= 10*60 && time < 18*60 ) //day
            lightness = 1;
        else if(time >= 18*60 && time < 22*60) //evening
            lightness = 0.2 + 0.8 * (1-(double)(time - 18*60)/240);

        int opacity = (int) (255*(1-lightness));
        g.setColor(new Color(0,0,50,opacity) )  ;
        g.fillRect(0, 0, 800, 600);
    }
}
