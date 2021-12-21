import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Player extends ImageManage {
    private int frameIndex = 1;
    private int frameNumber = 7;

    private String status;

    private int speed = 3;
    private boolean Flame = false;
    private int bombNum = 1;

    private String name = null;
    private int heart = 3;

    private boolean GO_UP;
    private boolean GO_RIGHT;
    private boolean GO_DOWN;
    private boolean GO_LEFT;
    private boolean SET_BOMB;

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public Player(double positionX, double positionY, double height, double width, String URL) {
        super(positionX, positionY, height, width, URL);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isFlame() {
        return Flame;
    }

    public void setFlame(boolean flame) {
        Flame = flame;
    }

    public int getBombNum() {
        return bombNum;
    }

    public void setBombNum(int bombNum) {
        this.bombNum = bombNum;
    }

    public ArrayList<Bomb> getListBomb() {
        return listBomb;
    }

    public void setListBomb(ArrayList<Bomb> listBomb) {
        this.listBomb = listBomb;
    }

    private ArrayList<Bomb> listBomb = new ArrayList();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public boolean isSET_BOMB() {
        return SET_BOMB;
    }

    public void setSET_BOMB(boolean SET_BOMB) {
        this.SET_BOMB = SET_BOMB;
    }

    public void setHandle(Scene scene2) {
        scene2.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP: {
                        frameIndex++;
                        GO_UP = true;
                        break;
                    }
                    case DOWN:
                        frameIndex++;
                        GO_DOWN = true;
                        break;
                    case LEFT:
                        frameIndex++;
                        GO_LEFT = true;
                        break;
                    case RIGHT:
                        frameIndex++;
                        GO_RIGHT = true;
                        break;
                    case SPACE:
                        SET_BOMB = true;
                        break;
                    default:
                        frameIndex = 1;
                }
            }
        });
    }

    public boolean outside(double playerPositionX, double playerPositionY, double bombPositionX, double bombPositionY, Map map) {
        playerPositionX += map.getTileSize();
        playerPositionY += map.getTileSize();

        double tileMapSize = map.getTileSize();
        if ( bombPositionX + tileMapSize >= getPositionX() - 20
                || bombPositionX + tileMapSize >= getPositionY() - 20
                || bombPositionX + 20 <= getPositionX() + tileMapSize
                || bombPositionY + 20 <= getPositionY() + tileMapSize) {
            return true;
        }
        else return false;
    }

    public boolean checkCollisionWithBomb(double newPositionX, double newPositionY, Map map) {
        if (getListBomb().size() == 0) return false;
        double tileMapSize = map.getTileSize();
        for (int i = 0; i < getListBomb().size(); i++) {
            Bomb bomb = getListBomb().get(i);
            if (getPositionX() != bomb.getPositionX()) {
                if (bomb.getPositionX() + tileMapSize >= newPositionX
                        && bomb.getPositionY() + tileMapSize >= newPositionY
                        && bomb.getPositionX() <= newPositionX + tileMapSize
                        && bomb.getPositionY()  <= newPositionY + tileMapSize)
                {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean isDie(ListEnemy listEnemy, Map map) {
        double tileMapSize = map.getTileSize();
        for (int i = 0; i < listEnemy.getEnemies().size(); i++) {
            Enemy enemy = listEnemy.getEnemies().get(i);
            if (enemy.getPositionX() + tileMapSize >= getPositionX() - 20
                    && enemy.getPositionY() + tileMapSize >= getPositionY() - 20
                    && enemy.getPositionX() + 20 <= getPositionX() + tileMapSize
                    && enemy.getPositionY() + 20 <= getPositionY() + tileMapSize
            ) {

                return true;
            }
        }
        return false;
    }

    public void setBomb(Map map, Group root2) throws FileNotFoundException {
        double bombPositionX = (int) (getPositionX() / map.getTileSize());
        double bombPositionY = (int) (getPositionY() / map.getTileSize());
        double tileMapSize = map.getTileSize();
        if ((bombPositionX * tileMapSize) + (tileMapSize / 2) > getPositionX()) {
            bombPositionX *= tileMapSize;
        } else {
            bombPositionX = (bombPositionX + 1) * tileMapSize;
        }

        if ((bombPositionY * tileMapSize) + (tileMapSize / 2) > getPositionY()) {
            bombPositionY *= tileMapSize;
        } else {
            bombPositionY = (bombPositionY + 1) * tileMapSize;
        }


        if (map.checkCollisionWithMap(bombPositionX, bombPositionY, root2, null) == 2) {
            Bomb bomb = new Bomb(bombPositionX, bombPositionY, map.getTileSize(), map.getTileSize(), "./image/bomb1.png");
            if (Flame) {
                int flame = bomb.getRadiusExplosion();
                bomb.setRadiusExplosion(flame + 1);
            }
            bomb.setStart(System.currentTimeMillis());
            try {
                bomb.loadImage(root2);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            listBomb.add(bomb);
        }
    }

    public void handlePlayer(Group mapGroup, Map map, ListEnemy listEnemy) {
        double playerPositionX = getPositionX();
        double playerPositionY = getPositionY();
        int speed = getSpeed();
        if (isGO_DOWN()) {
            setStatus("Down");
            try {
                updatePlayer(mapGroup, map, playerPositionX, playerPositionY + speed, getStatus(), listEnemy);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            setGO_DOWN(false);
        }

        if (isGO_UP()) {
            setStatus("Up");
            try {
                updatePlayer(mapGroup, map, playerPositionX, playerPositionY - speed, getStatus(), listEnemy);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            setGO_UP(false);

        }

        if (isGO_LEFT()) {
            setStatus("Left");
            try {
                updatePlayer(mapGroup, map, playerPositionX - speed, playerPositionY, getStatus(), listEnemy);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            setGO_LEFT(false);
        }

        if (isGO_RIGHT()) {
            setStatus("Right");
            try {
                updatePlayer(mapGroup, map, playerPositionX + speed, playerPositionY, getStatus(), listEnemy);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            setGO_RIGHT(false);
        }
        if (getListBomb().size() > getBombNum()) {
            setSET_BOMB(false);
        }

        if (isSET_BOMB()) {
            try {
                setBomb(map, mapGroup);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            setSET_BOMB(false);
        }

        updateListBomb(mapGroup, map, listEnemy);
    }

    public void updateListBomb(Group mapGroup, Map map, ListEnemy listEnemy) {
        if (getListBomb().size() != 0) {
            for (int i = 0; i < getListBomb().size(); i++) {
                Bomb bomb = getListBomb().get(i);
                bomb.setEndl(System.currentTimeMillis());

                long elapsedTime = bomb.getEndl() - bomb.getStart();
                // vượt quá tg thì cho nổ
                if (elapsedTime > bomb.getTimeExplosion()) {
                    mapGroup.getChildren().remove(bomb.imageView);
                    double bombPositionX = bomb.getPositionX();
                    double bombPositionY = bomb.getPositionY();

                    boolean checkFire1 = false, checkFire2 = false, checkFire3 = false, checkFire4 = false;

                    if (elapsedTime > bomb.getTimeExplosion() + 350) {
                        for (int k = 0; k < bomb.getFires().size(); k++) {
                            mapGroup.getChildren().remove(bomb.getFires().get(k).getImageView());
                        }
                        //thực hiện ảnh hưởng tới map và quái
                        try {
                            bomb.Explosion(map, mapGroup, listEnemy, elapsedTime, this);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    } else if (elapsedTime > bomb.getTimeExplosion() + 300) {

                        for (int j = 1; j <= bomb.getRadiusExplosion(); j++) {
                            ImageManage nImage0 = new ImageManage(bombPositionX, bombPositionY, 45, 45, "./image/fire.png");
                            try {
                                nImage0.loadImage(mapGroup);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            bomb.getFires().add(nImage0);
                            if (map.checkCollisionWithMap(bombPositionX + 45 * j, bombPositionY, mapGroup, null) == 2 && !checkFire1) {
                                ImageManage nImage1 = new ImageManage(bombPositionX + 45 * j, bombPositionY, 45, 45, "./image/fireRight.png");
                                try {
                                    nImage1.loadImage(mapGroup);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                bomb.getFires().add(nImage1);
                            } else {
                                checkFire1 = true;
                            }
                            if (map.checkCollisionWithMap(bombPositionX - 45 * j, bombPositionY, mapGroup, null) == 2 && !checkFire2) {
                                ImageManage nImage2 = new ImageManage(bombPositionX - 45 * j, bombPositionY, 45, 45, "./image/fireLeft.png");
                                try {
                                    nImage2.loadImage(mapGroup);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                bomb.getFires().add(nImage2);
                            } else {
                                checkFire2 = true;
                            }
                            if (map.checkCollisionWithMap(bombPositionX, bombPositionY + 45 * j, mapGroup, null) == 2 && !checkFire3) {
                                ImageManage nImage3 = new ImageManage(bombPositionX, bombPositionY + 45 * j, 45, 45, "./image/fireUp.png");
                                try {
                                    nImage3.loadImage(mapGroup);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                bomb.getFires().add(nImage3);
                            } else {
                                checkFire3 = true;
                            }
                            if (map.checkCollisionWithMap(bombPositionX, bombPositionY - 45 * j, mapGroup, null) == 2 && !checkFire4) {
                                ImageManage nImage4 = new ImageManage(bombPositionX, bombPositionY - 45 * j, 45, 45, "./image/fireDown.png");
                                try {
                                    nImage4.loadImage(mapGroup);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                bomb.getFires().add(nImage4);
                            } else {
                                checkFire4 = true;
                            }
                        }
                    } else if (elapsedTime > bomb.getTimeExplosion() + 250) {
                        bomb.setURL("./image/bomb6.png");
                    } else if (elapsedTime > bomb.getTimeExplosion() + 200) {
                        bomb.setURL("./image/bomb5.png");
                    } else if (elapsedTime > bomb.getTimeExplosion() + 150) {
                        bomb.setURL("./image/bomb4.png");
                    } else if (elapsedTime > bomb.getTimeExplosion() + 100) {
                        bomb.setURL("./image/bomb3.png");
                    } else if (elapsedTime > bomb.getTimeExplosion() + 50) {
                        bomb.setURL("./image/bomb2.png");
                    } else if (elapsedTime > bomb.getTimeExplosion() + 0) {
                        bomb.setURL("./image/bomb1.png");
                    }
                    try {
                        bomb.loadImage(mapGroup);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (elapsedTime > bomb.getTimeExplosion() + 350) {
                        getListBomb().remove(i);
                    }
                }
            }
        }
    }


    public void updatePlayer(Group root2, Map map, double newPositionX, double newPositionY, String status, ListEnemy listEnemy) throws FileNotFoundException {

        root2.getChildren().remove(imageView);
        int checkMap = map.checkCollisionWithMap(newPositionX, newPositionY, root2, this);
        if (frameIndex > frameNumber) frameIndex = 1;
        setURL("./image/" + name + "/character" + status + String.valueOf(frameIndex) + ".png");

        if (newPositionX >= -8
                && newPositionX <= 1353
                && newPositionY >= 0 &&
                newPositionY <= 585) {
            // bằng 2 là ko va chạm
            if (checkMap == 2) {
                setPositionX(newPositionX);
                setPositionY(newPositionY);
                // chạy nhanh
            } else if (checkMap == map.getSpeed()) {
                setPositionX(newPositionX);
                setPositionY(newPositionY);
                speed += 2;
                // nổ to
            } else if (checkMap == map.getFlame()) {
                setPositionX(newPositionX);
                setPositionY(newPositionY);
                Flame = true;
                // vào cổng
            } else if (checkMap == map.getGate()) {
                setPositionX(newPositionX);
                setPositionY(newPositionY);
                map.setNextMap(true);
                // nhiều bomb
            } else if (checkMap == map.getBomb()) {
                setPositionY(newPositionY);
                bombNum++;
            }
            loadImage(root2);
        }
    }

}
