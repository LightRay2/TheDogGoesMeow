import java.util.ArrayList;
import java.util.List;

/**
 * Created by lost on 03.10.14.
 */
public class WorldLogic {

    static class Positions{
        public static int bedX = 100, bedY = 500,
                   bowlX = 700, bowlY = 100,
                   manX = 700, manY = 500,
                   freesbiX = 100, freesbiY= 100,
                   manNearBowlX = 700, manNearBowlY = 200;
    }

    enum DogState {sleep, eat, askMan, play }
    enum ManState {idle, fetchFood}

    DogState dogState = DogState.sleep;
    ManState manState=ManState.idle;

    final double manSpeed = 12, dogSpeed=7;
    int time = 60*5; // minutes, 0-1439
    double foodInBowl = 0; //сколько еды в тарелке

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

        //переключение состояния
        if(dogState == DogState.sleep && time==8*60){
            dogState = DogState.eat;
            dog.aimX = Positions.bowlX;
            dog.aimY = Positions.bowlY;
        }
        else if(dogState == DogState.eat && dog.isMovingFinished() ){
            if(foodInBowl >= 0.5){ //достаточно еды
                foodInBowl -= 0.5;
                dogState = DogState.play;
                dog.aimX = Positions.freesbiX;
                dog.aimY = Positions.freesbiY;
            }
            else{
                dogState = DogState.askMan;
                dog.aimX = Positions.manX;
                dog.aimY = Positions.manY;
            }
        }
        else if(dogState == DogState.askMan && dog.isMovingFinished()){
            dogState = DogState.eat;
            dog.aimX = Positions.bowlX;
            dog.aimY = Positions.bowlY;

            manState = ManState.fetchFood;
            man.aimX = Positions.manNearBowlX;
            man.aimY = Positions.manNearBowlY;
        }
        else if(dogState == DogState.play && time == 20*60){
            dogState = DogState.sleep;
            dog.aimX = Positions.bedX;
            dog.aimY = Positions.bedY;
        }

        if(manState == ManState.fetchFood && man.isMovingFinished()){
            foodInBowl = 1;
            manState = ManState.idle;
            man.aimX = Positions.manX;
            man.aimY = Positions.manY;
        }
    }


}
