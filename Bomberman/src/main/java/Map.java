
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import java.io.FileNotFoundException;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Map {


    // kích thước của 1 tile map
    private final double tileSize = 45;
    // số tile map theo chiều ngang và dọc
    private final int widthTileMap = 31;
    private final int heightTileMap = 14;
    private String URLmap = null;
    private boolean nextMap = false;

    public boolean isNextMap() {
        return nextMap;
    }

    public void setNextMap(boolean nextMap) {
        this.nextMap = nextMap;
    }


    private final int BLANK = 0;
    // item ở trạng thái chưa nổ
    private final int SpeedItem = 3;
    private final int FlameItem = 4;
    private final int BombItem = 5;
    private final int GateItem = 6;

    public int getSpeedItem() {
        return SpeedItem;
    }

    public int getFlameItem() {
        return FlameItem;
    }

    public int getBombItem() {
        return BombItem;
    }


    // item ở trạng thái đã nổ
    private final int Speed = 11;
    private final int Flame = 12;
    private final int Bomb = 13;
    private final int Gate = 14;

    public int getSpeed() {
        return Speed;
    }

    public int getFlame() {
        return Flame;
    }

    public int getBomb() {
        return Bomb;
    }

    public int getGateItem() {
        return GateItem;
    }

    public int getGate() {
        return Gate;
    }

    // list chứa trạng thái map
    private ArrayList<ArrayList<Integer>> listMap = new ArrayList<>();
    private ArrayList<ImageManage> maps = new ArrayList<>();
    public int getBLANK() {
        return BLANK;
    }
    public String getURLmap() {
        return URLmap;
    }

    public void setURLmap(String URLmap) {
        this.URLmap = URLmap;
    }

    public Map() {
    }

    public double getTileSize() {
        return tileSize;
    }

    public int getWidthTileMap() {
        return widthTileMap;
    }

    public int getHeightTileMap() {
        return heightTileMap;
    }

    public ArrayList<ArrayList<Integer>> getListMap() {
        return listMap;
    }

    public void setListMap(ArrayList<ArrayList<Integer>> listMap) {
        this.listMap = listMap;
    }

    // đọc file txt vào arraylist
    private void makeMapFromtxt(String URL) {
        FileInputStream fin = null;
        for (int i = 0; i < heightTileMap; i++) {
            listMap.add(new ArrayList());
        }
        try {
            fin = new FileInputStream(URL);
            int i = 0, j = 0;

            int ch;
            while ((ch = fin.read()) != -1) {
                int tile = Character.getNumericValue(ch);
                if (tile != -1) {
                    j++;
                    listMap.get(i).add(tile);
                    if (j == widthTileMap) {
                        i++;
                        j = 0;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fin != null)
                    fin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<ImageManage> getMaps() {
        return maps;
    }

    public void setMaps(ArrayList<ImageManage> maps) {
        this.maps = maps;
    }

    //load ảnh tile map
    public void loadMap(Group root2) throws FileNotFoundException {
        makeMapFromtxt(URLmap);
        System.out.println(URLmap);
        ImageManage grass = new ImageManage(45, 45, 630, 1350, "./image/grass.png");
        grass.loadImage(root2);
        // load ảnh vào map
        for (int i = 0; i < heightTileMap; i++) {
            for (int j = 0; j < widthTileMap; j++) {
                // tạo 1 tile mới
                int tileNumber = listMap.get(i).get(j);
                if (tileNumber != BLANK) {
                    StringBuffer URLTILE = new StringBuffer("./image/tile");
                    URLTILE.append(String.valueOf(tileNumber));
                    URLTILE.append(".png");

                    ImageManage tileMap = new ImageManage(j * tileSize, i * tileSize,
                            tileSize, tileSize, URLTILE.toString());
                    tileMap.loadImage(root2);
                    maps.add(tileMap);
                }
            }
        }
    }

    int checkCollisionWithMap(double newPositionX, double newPositionY, Group root2, Player player) {
        for (int i = 0; i < heightTileMap; i++) {
            for (int j = 0; j < widthTileMap; j++) {
                int tileNumber = listMap.get(i).get(j);
                if (tileNumber != BLANK
                        || tileNumber == Speed
                        || tileNumber == Flame
                        || tileNumber == Bomb) {
                    if (newPositionX + tileSize > j * tileSize + 10
                            && newPositionY < (i + 1) * tileSize - 8
                            && newPositionX < (j + 1) * tileSize - 10
                            && newPositionY + tileSize > i * tileSize + 8) {
                        // va chạm vào vật phẩm đặc biệt
                        if (tileNumber == Speed && player != null) {
                            listMap.get(i).set(j, 0);
                            for (int k = 0; k < maps.size(); k++) {
                                ImageManage tileMap = maps.get(k);
                                if (tileMap.getPositionX() == j * tileSize
                                        && tileMap.getPositionY() == i * tileSize) {
                                    root2.getChildren().remove(tileMap.getImageView());
                                }
                            }
                            return Speed;
                        } else if (tileNumber == Flame && player != null) {
                            listMap.get(i).set(j, 0);
                            for (int k = 0; k < maps.size(); k++) {
                                ImageManage tileMap = maps.get(k);
                                if (tileMap.getPositionX() == j * tileSize
                                        && tileMap.getPositionY() == i * tileSize) {
                                    root2.getChildren().remove(tileMap.getImageView());
                                }
                            }
                            return Flame;
                        } else if (tileNumber == Bomb && player != null) {
                            listMap.get(i).set(j, 0);
                            for (int k = 0; k < maps.size(); k++) {
                                ImageManage tileMap = maps.get(k);
                                if (tileMap.getPositionX() == j * tileSize
                                        && tileMap.getPositionY() == i * tileSize) {
                                    root2.getChildren().remove(tileMap.getImageView());
                                }
                            }
                            return Bomb;
                        } else if (tileNumber == Gate && player != null) {
                            listMap.get(i).set(j, 0);
                            for (int k = 0; k < maps.size(); k++) {
                                ImageManage tileMap = maps.get(k);
                                if (tileMap.getPositionX() == j * tileSize
                                        && tileMap.getPositionY() == i * tileSize) {
                                    root2.getChildren().remove(tileMap.getImageView());
                                }
                            }
                            return Gate;
                        }
                        return 1;
                        // va chạm map bth
                    }
                }
            }
        }
        return 2;
        // ko va chạm
    }


}
