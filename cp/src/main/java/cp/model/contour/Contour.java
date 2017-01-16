package cp.model.contour;

/**
 * Created by prombouts on 7/01/2017.
 */
public class Contour {

    private int position;
    private int end;
    private int direction;

    public Contour(int position, int end, int direction) {
        this.position = position;
        this.end = end;
        this.direction = direction;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
