/**
 * Created by lost on 28.09.14.
 */
public class GameObject {
    public enum Type{bed, bowl, dog, freesbi, man };

    Type type;
    public Type GetType(){return type;}

    double x,y;
    double aimX, aimY;
    double speed;

    public double getX(){return x;}
    public void setX(double v){x = v; }
    public double getY(){return y;}
    public void setY(double v){y = v;}

    public double getAimX(){return aimX;}
    public void setAimX(double v){aimX = v; }
    public double getAimY(){return aimY;}
    public void setAimY(double v){aimY = v;}

    public boolean isMovingFinished(){
        return x == aimX && y == aimY;
    }

    public GameObject(Type type, double startX, double startY, double speed){
        this.type = type;
        x = aimX = startX;
        y = aimY = startY;
        this.speed = speed;
    }

    public void updatePosition(){
        if(speed>0){
            double distanceToMove = Math.sqrt((aimX-x)*(aimX-x)+(aimY-y)*(aimY-y));
            double k = distanceToMove/speed;
            if(k<=1){
                x = aimX;
                y = aimY;
            }
            else if(k>0){
                x = x+(aimX - x)/k;
                y = y+(aimY - y)/k;
            }
        }
    }
}
