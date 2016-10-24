package jason.scavenger_hunt;

/**
 * Created by mafaldaborges on 10/17/16.
 */
public class Course {
    private String name;
    private int distance;
    private int yourTime;

    public Course(String name, int distance, int yourTime){
        this.name = name;
        this.distance = distance;
        this.yourTime = yourTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getYourTime() {
        return yourTime;
    }

    public void setYourTime(int yourTime) {
        this.yourTime = yourTime;
    }
}
