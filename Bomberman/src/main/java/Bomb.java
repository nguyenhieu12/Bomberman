import javafx.scene.Group;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Bomb extends ImageManage {
    private final double timeExplosion = 5000;
    private long start;
    private long endl;
    private int radiusExplosion = 1;
    private boolean explosion = false;

    public boolean isExplosion() {
        return explosion;
    }

    public void setExplosion(boolean explosion) {
        this.explosion = explosion;
    }

    // kiểm soát ảnh tia lửa
    private ArrayList<ImageManage> fires = new ArrayList<>();

    public ArrayList<ImageManage> getFires() {
        return fires;
    }


    public void setFires(ArrayList<ImageManage> fires) {
        this.fires = fires;
    }

    public int getRadiusExplosion() {
        return radiusExplosion;
    }

    public double getTimeExplosion() {
        return timeExplosion;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEndl() {
        return endl;
    }

    public void setEndl(long endl) {
        this.endl = endl;
    }

    public Bomb(double positionX, double positionY, double height, double width, String URL) {
        super(positionX, positionY, height, width, URL);
    }

    public void fire(long elapsedTime) {

    }

    public void setRadiusExplosion(int radiusExplosion) {
        this.radiusExplosion = radiusExplosion;
    }

    //bomb nổ quái và map
    public void Explosion(Map map, Group root2, ListEnemy listEnemy, long elapsedTime, Player player) throws FileNotFoundException {
        int tileJ = (int) (getPositionX() / map.getTileSize());
        int tileI = (int) (getPositionY() / map.getTileSize());

        double mapTileSize = map.getTileSize();

        for (int k = 0; k < map.getMaps().size(); k++) {
            ImageManage tileMap = map.getMaps().get(k);
            if (tileMap.getPositionX() >= getPositionX() - mapTileSize * radiusExplosion
                    && tileMap.getPositionX() <= getPositionX() + mapTileSize * (radiusExplosion)
                    && tileMap.getPositionY() >= getPositionY() - mapTileSize * radiusExplosion
                    && tileMap.getPositionY() <= getPositionY() + mapTileSize * (radiusExplosion)) {
                if ((tileMap.getPositionX() == getPositionX() || tileMap.getPositionY() == getPositionY())) {
                    // nếu là ô có vật phẩm đặc biệt thì load ảnh mới
                    if (tileMap.getURL().equals("./image/tile3.png")) {
                        tileMap.setURL("./image/speedItem.png");
                        tileMap.loadImage(root2);
                    } else if (tileMap.getURL().equals("./image/tile" + map.getFlameItem() + ".png")) {
                        tileMap.setURL("./image/flameItem.png");
                        tileMap.loadImage(root2);
                    } else if (tileMap.getURL().equals("./image/tile" + map.getBombItem() + ".png")) {
                        tileMap.setURL("./image/bombItem.png");
                        tileMap.loadImage(root2);
                    } else if (tileMap.getURL().equals("./image/tile" + map.getGateItem()+".png")) {
                        tileMap.setURL("./image/gateItem.png");
                        tileMap.loadImage(root2);
                    } else if (tileMap.getURL().equals("./image/tile2.png")) {
                        root2.getChildren().remove(tileMap.getImageView());
                    }
                }
            }
        }

        // bỏ va chạm map với ô đã bị nổ
        int heightMap = map.getHeightTileMap();
        int widthMap = map.getWidthTileMap();
        for (int m = 1; m <= radiusExplosion; m++) {
            if (tileI < heightMap) {
                // đổi trạng thái với vật phẩm đặc biệt
                if (tileJ + m < widthMap && map.getListMap().get(tileI).get(tileJ + m) == map.getSpeedItem()) {
                    map.getListMap().get(tileI).set(tileJ + m, map.getSpeed());
                }
                if (tileJ - m >= 0 && map.getListMap().get(tileI).get(tileJ - m) == map.getSpeedItem()) {
                    map.getListMap().get(tileI).set(tileJ - m, map.getSpeed());
                }
                // Gate
                if (tileJ + m < widthMap && map.getListMap().get(tileI).get(tileJ + m) == map.getGateItem()) {
                    map.getListMap().get(tileI).set(tileJ + m, map.getGate());
                }
                if (tileJ - m >= 0 && map.getListMap().get(tileI).get(tileJ - m) == map.getGateItem()) {
                    map.getListMap().get(tileI).set(tileJ - m, map.getGate());
                }
                // flame
                if (tileJ + m < widthMap && map.getListMap().get(tileI).get(tileJ + m) == map.getFlameItem()) {
                    map.getListMap().get(tileI).set(tileJ + m, map.getFlame());
                }
                if (tileJ - m >= 0 && map.getListMap().get(tileI).get(tileJ - m) == map.getFlameItem()) {
                    map.getListMap().get(tileI).set(tileJ - m, map.getFlame());
                }
                // bomb
                if (tileJ + m < widthMap && map.getListMap().get(tileI).get(tileJ + m) == map.getBombItem()) {
                    map.getListMap().get(tileI).set(tileJ + m, map.getBomb());
                }
                if (tileJ - m >= 0 && map.getListMap().get(tileI).get(tileJ - m) == map.getBombItem()) {
                    map.getListMap().get(tileI).set(tileJ - m, map.getBomb());
                }
                // map bth
                if (tileJ + m < widthMap && map.getListMap().get(tileI).get(tileJ + m) == 2) {
                    map.getListMap().get(tileI).set(tileJ + m, 0);
                }
                if (tileJ - m >= 0 && map.getListMap().get(tileI).get(tileJ - m) == 2) {
                    map.getListMap().get(tileI).set(tileJ - m, 0);
                }
            }
            if (tileJ < widthMap) {
                //speed
                if (tileI + m < heightMap && map.getListMap().get(tileI + m).get(tileJ) == map.getSpeedItem()) {
                    map.getListMap().get(tileI + m).set(tileJ, map.getSpeed());
                }
                if (tileI - m >= 0 && map.getListMap().get(tileI - m).get(tileJ) == map.getSpeedItem()) {
                    map.getListMap().get(tileI - m).set(tileJ, map.getSpeed());
                }
                if (tileI + m < heightMap && map.getListMap().get(tileI + m).get(tileJ) == map.getGateItem()) {
                    map.getListMap().get(tileI + m).set(tileJ, map.getGate());
                }
                if (tileI - m >= 0 && map.getListMap().get(tileI - m).get(tileJ) == map.getGateItem()) {
                    map.getListMap().get(tileI - m).set(tileJ, map.getGate());
                }
                // flame
                if (tileI + m < heightMap && map.getListMap().get(tileI + m).get(tileJ) == map.getFlameItem()) {
                    map.getListMap().get(tileI + m).set(tileJ, map.getFlame());
                }
                if (tileI - m >= 0 && map.getListMap().get(tileI - m).get(tileJ) == map.getFlameItem()) {
                    map.getListMap().get(tileI - m).set(tileJ, map.getFlame());
                }
                // bomb
                if (tileI + m < heightMap && map.getListMap().get(tileI + m).get(tileJ) == map.getBombItem()) {
                    map.getListMap().get(tileI + m).set(tileJ, map.getBomb());
                }
                if (tileI - m >= 0 && map.getListMap().get(tileI - m).get(tileJ) == map.getBombItem()) {
                    map.getListMap().get(tileI - m).set(tileJ, map.getBomb());
                }
                // map bth
                if (tileI + m < heightMap && map.getListMap().get(tileI + m).get(tileJ) == 2) {
                    map.getListMap().get(tileI + m).set(tileJ, 0);
                }
                if (tileI - m >= 0 && map.getListMap().get(tileI - m).get(tileJ) == 2) {
                    map.getListMap().get(tileI - m).set(tileJ, 0);
                }
            }
        }

        // kiểm tra quái có trong phạm vi của bomb ko => có thì xóa
        int radiusExplosion = getRadiusExplosion();
        for (int k = 0; k < listEnemy.getEnemies().size(); k++) {
            Enemy enemy = listEnemy.getEnemies().get(k);
            if (enemy.getPositionX() >= getPositionX() - 45 * radiusExplosion
                    && enemy.getPositionX() <= getPositionX() + 45 * radiusExplosion
                    && enemy.getPositionY() >= getPositionY() - 45 * radiusExplosion
                    && enemy.getPositionY() <= getPositionY() + 45 * radiusExplosion) {
                if (((Math.abs(enemy.getPositionX() - getPositionX()) <= 35))
                        || ((Math.abs(enemy.getPositionY() - getPositionY()) <= 35))) {
                    enemy.setGO_DOWN(false);
                    enemy.setGO_LEFT(false);
                    enemy.setGO_UP(false);
                    enemy.setGO_RIGHT(false);
                    root2.getChildren().remove(enemy.getImageView());
                    listEnemy.getEnemies().remove(k);
                }
            }

        }

        // cho nổ ngay lập tức
        for (int k = 0; k < player.getListBomb().size(); k++) {
            Bomb bomb = player.getListBomb().get(k);
            if (bomb.getPositionX() >= getPositionX() - mapTileSize * radiusExplosion
                    && bomb.getPositionX() <= getPositionX() + mapTileSize * (radiusExplosion)
                    && bomb.getPositionY() >= getPositionY() - mapTileSize * radiusExplosion
                    && bomb.getPositionY() <= getPositionY() + mapTileSize * (radiusExplosion)) {
                if ((bomb.getPositionX() == getPositionX() || bomb.getPositionY() == getPositionY())) {
                    bomb.setExplosion(true);
                }
            }
        }

    }



}
