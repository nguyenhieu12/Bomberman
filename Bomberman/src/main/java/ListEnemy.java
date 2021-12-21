import javafx.scene.Group;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class ListEnemy {
    private final int sizeListEnemy = 20;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private int seed = 0;

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private int index = 0;

    public int getSizeListEnemy() {
        return sizeListEnemy;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public void updateListEnemy(Group map1Group, Map map, Player player) {
        for (int i = 0; i < getEnemies().size(); i++) {
            Enemy enemy = getEnemies().get(i);
            enemy.actionEnemy(player, map);
            if (enemy.isGO_LEFT()) {
                enemy.setStatus("Left");
                try {
                    enemy.updateEnemy(map1Group, map, enemy.getPositionX() - 0.5, enemy.getPositionY());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            if (enemy.isGO_RIGHT()) {
                enemy.setStatus("Right");
                try {
                    enemy.updateEnemy(map1Group, map, enemy.getPositionX() + 0.5, enemy.getPositionY());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            if (enemy.isGO_DOWN()) {
                enemy.setStatus("Down");
                try {
                    enemy.updateEnemy(map1Group, map, enemy.getPositionX(), enemy.getPositionY() + 0.5);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            if (enemy.isGO_UP()) {
                enemy.setStatus("Up");
                try {
                    enemy.updateEnemy(map1Group, map, enemy.getPositionX(), enemy.getPositionY() - 0.5);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setUpEnemy(Map map, Group root2) throws FileNotFoundException {
        enemies.clear();
        Random generator = new Random();
        int stepI = (int)Math.floor(Math.random()*(3-1+1)+1);
        int stepJ = (int)Math.floor(Math.random()*(3-1+1)+1);

//        int stepI = 2;
//        int stepJ = 3;

        int widthTileMap = map.getWidthTileMap();
        int heightTileMap = map.getHeightTileMap();
        double tileSize = map.getTileSize();

        while(enemies.size() == 0) {
            for (int i = 4; i < heightTileMap; i += stepI) {
                for (int j = 3; j < widthTileMap; j += stepJ) {
                    if (index >= sizeListEnemy) break;

                    int tileNumber = map.getListMap().get(i).get(j);
                    if (tileNumber == 0) {
                        index++;
                        int moveDefault = (int)Math.floor(Math.random()*(2-1+1)+1);
                        int randomVal = (int)Math.floor(Math.random()*(2-1+1)+1);

//                        int moveDefault = 2;
//                        int randomVal = 1;

                        String URL;
                        String type;

                        if (randomVal == 1) {
                            URL = "./image/BalloomDown1.png";
                            type = "Balloom";
                        } else {
                            URL = "./image/OnealDown1.png";
                            type = "Oneal";
                        }
                        Enemy elementEnemy = new Enemy(j * tileSize, i * tileSize, 45, 45, URL, type);
                        if (randomVal == 1) {
                            if (moveDefault == 1) {
                                elementEnemy.setGO_LEFT(true);
                            } else if (moveDefault == 2) {
                                elementEnemy.setGO_DOWN(true);
                            } else if (moveDefault == 3) {
                                elementEnemy.setGO_UP(true);
                            } else {
                                elementEnemy.setGO_RIGHT(true);
                            }

                        }
                        elementEnemy.loadImage(root2);
                        enemies.add(elementEnemy);
                    }
                }
            }
        }

    }
}
