import java.util.ArrayList;
import java.util.List;

/**
 * Created by lost on 03.10.14.
 */
public class WorldLogic {

    //константы
    final double manSpeed = 12, dogSpeed=7;
    static class Positions{
        public static int bedX = 100, bedY = 500,
                   bowlX = 700, bowlY = 100,
                   manX = 700, manY = 500,
                   freesbiX = 100, freesbiY= 100,
                   manNearBowlX = 700, manNearBowlY = 200;
    }

    //состояния человека и собаки
    enum DogState {sleep, eat, askMan, play }
    enum ManState {idle, fetchFood}
    private DogState dogState = DogState.sleep;
    private ManState manState=ManState.idle;

    //текущие переменные - время и количество еды
    private int time = 60*5; // minutes, 0-1439
    private double foodInBowl = 0; //сколько еды в тарелке
    public int getTime(){return time;}
    public double getFoodInBowl(){return foodInBowl;}

    //экземпляры игровых объектов
    GameObject dog,man;
    ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();


    public WorldLogic(){
        dog = new GameObject(GameObject.Type.dog, Positions.bedX, Positions.bedY, dogSpeed);
        man = new GameObject(GameObject.Type.man, Positions.manX, Positions.manY, manSpeed);
        gameObjects.add(new GameObject(GameObject.Type.bowl, Positions.bowlX, Positions.bowlY, 0));
        gameObjects.add(new GameObject(GameObject.Type.freesbi, Positions.freesbiX, Positions.freesbiY,0));
        gameObjects.add(new GameObject(GameObject.Type.bed, Positions.bedX, Positions.bedY, 0));
        gameObjects.add(man);
        gameObjects.add(dog);
    }

    public void update(){
        time = (time+2)%1440;
        for(int i = 0; i < gameObjects.size();i++)
            gameObjects.get(i).updatePosition();

        //переключение состояния. Меняем состояние собаки и человека и ставим им
        //точку, в которую они движутся (aimX; aimY)
        if(dogState == DogState.sleep && time==8*60){
            dogState = DogState.eat;
            dog.setAimX( Positions.bowlX);
            dog.setAimY( Positions.bowlY);
        }
        else if(dogState == DogState.eat && dog.isMovingFinished() ){
            if(foodInBowl >= 0.5){ //достаточно еды
                foodInBowl -= 0.5;
                dogState = DogState.play;
                dog.setAimX( Positions.freesbiX);
                dog.setAimY( Positions.freesbiY);
            }
            else{
                dogState = DogState.askMan;
                dog.setAimX(Positions.manX);
                dog.setAimY(Positions.manY);
            }
        }
        else if(dogState == DogState.askMan && dog.isMovingFinished()){
            dogState = DogState.eat;
            dog.setAimX(Positions.bowlX);
            dog.setAimY(Positions.bowlY);

            manState = ManState.fetchFood;
            man.setAimX(Positions.manNearBowlX);
            man.setAimY(Positions.manNearBowlY);
        }
        else if(dogState == DogState.play && time == 20*60){
            dogState = DogState.sleep;
            dog.setAimX(Positions.bedX);
            dog.setAimY(Positions.bedY);
        }

        if(manState == ManState.fetchFood && man.isMovingFinished()){
            foodInBowl = 1;
            manState = ManState.idle;
            man.setAimX(Positions.manX);
            man.setAimY(Positions.manY);
        }
    }


}
