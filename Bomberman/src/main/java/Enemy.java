import javafx.scene.Group;
import java.io.FileNotFoundException;


public class Enemy extends ImageManage {
    private int frameIndex = 1;
    private int frameNumber = 2;
    private String status;
    private String type;
    private double speed = 1;

    private final String Balloom = "Balloom";
    private final String Oneal = "Oneal";
    private final String Boss = "boss";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Enemy(double positionX, double positionY, double height, double width, String URL, String type) {
        super(positionX, positionY, height, width, URL);
        this.type = type;
        setGO_LEFT(false);
        setGO_RIGHT(false);
        setGO_UP(false);
        setGO_DOWN(false);

    }

    private boolean GO_UP;
    private boolean GO_RIGHT;
    private boolean GO_DOWN;
    private boolean GO_LEFT;

    public int getFrameIndex() {
        return frameIndex;
    }

    public void setFrameIndex(int frameIndex) {
        this.frameIndex = frameIndex;
    }

    public int getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(int frameNumber) {
        this.frameNumber = frameNumber;
    }

    public boolean isGO_UP() {
        return GO_UP;
    }

    public void setGO_UP(boolean GO_UP) {
        this.GO_UP = GO_UP;
    }

    public boolean isGO_RIGHT() {
        return GO_RIGHT;
    }

    public void setGO_RIGHT(boolean GO_RIGHT) {
        this.GO_RIGHT = GO_RIGHT;
    }

    public boolean isGO_DOWN() {
        return GO_DOWN;
    }

    public void setGO_DOWN(boolean GO_DOWN) {
        this.GO_DOWN = GO_DOWN;
    }

    public boolean isGO_LEFT() {
        return GO_LEFT;
    }

    public void setGO_LEFT(boolean GO_LEFT) {
        this.GO_LEFT = GO_LEFT;
    }

    // tới gần thì đuổi
    public boolean checkNear(Player player, Map map) {
        if (getPositionX() + map.getTileSize() > player.getPositionX() - map.getTileSize() * 2
                && getPositionY() + map.getTileSize() > player.getPositionY() - map.getTileSize() * 2
                && getPositionX() < player.getPositionX() + map.getTileSize() * 2
                && getPositionY() < player.getPositionY() + map.getTileSize() * 2) {
            return true;
        } else {
            return false;
        }
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    // quái di chuyển
    public void actionEnemy(Player player, Map map) {
        if (type.equals(Oneal) || type.equals(Boss)) {

            if (checkNear(player, map)) {
                if(frameIndex < 2) {
                    frameIndex++;
                }
                setSpeed(2);
                if (getPositionX() != player.getPositionX()) {
                    if (getPositionX() < player.getPositionX()) {
                        GO_RIGHT = true;
                        GO_LEFT = false;
                    } else {
                        GO_LEFT = true;
                        GO_RIGHT = false;
                    }
                }
                if (getPositionY() != player.getPositionY()) {
                    if (getPositionY() > player.getPositionY()) {
                        GO_UP = true;
                        GO_DOWN = false;
                    } else {
                        GO_DOWN = true;
                        GO_UP = false;
                    }
                }
            }else{
                setSpeed(1);
                frameIndex = 1;
            }
        }
    }

    public void updateEnemy(Group root2, Map map, double newPositionX, double newPositionY) throws FileNotFoundException {
        if(type.equals(Oneal)) status = "Down";
        setURL("./image/" + type + status + String.valueOf(frameIndex) + ".png");
        int checkMap = map.checkCollisionWithMap(newPositionX, newPositionY, root2, null);
        if (newPositionX >= -8
                && newPositionX <= 1353
                && newPositionY >= 0 &&
                newPositionY <= 585) {
            // kiểm tra va chạm với map
            if (checkMap == 2 && !(newPositionX <= 3 * map.getTileSize() && newPositionY <= 4 * map.getTileSize())) {
                setPositionX(newPositionX);
                setPositionY(newPositionY);
            } else if(checkMap == 1 || (newPositionX <= 3 * map.getTileSize() && newPositionY <= 4 * map.getTileSize())){
                if (type.equals("Balloom")) {
                    if (isGO_RIGHT()) {
                        setGO_LEFT(true);
                        setGO_RIGHT(false);
                    } else if(isGO_LEFT()) {
                        setGO_RIGHT(true);
                        setGO_LEFT(false);
                    } else if(isGO_DOWN()) {
                        setGO_UP(true);
                        setGO_DOWN(false);
                    } else {
                        setGO_UP(false);
                        setGO_DOWN(true);
                    }

                }

            }

        }

        loadImage(root2);
    }

}
