import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application {
    private boolean nextMap1 = false;

    // khai báo kích thước màn hình
    private static final double WIDTH = 1395, HEIGHT = 630;

    // màu sắc, phóng to cho các nút start, help, exit
    private static final String START_BUTTON = "-fx-text-fill: blue;" + "-fx-font-weight: bold;"
            + "-fx-background-color: rgba(255, 0, 0, 0);" + "-fx-background-size: 50;"
            + "-fx-font-size: 40;";
    private static final String START_BUTTON_HOVER = "-fx-text-fill: blue;" + "-fx-font-weight: bold;"
            + "-fx-background-color: rgba(255, 0, 0, 0);" + "-fx-background-size: 80;"
            + "-fx-font-size: 50;";

    private static final String HELP_BUTTON = "-fx-text-fill: yellow;" + "-fx-font-weight: bold;"
            + "-fx-background-color: rgba(255, 0, 0, 0);" + "-fx-background-size: 50;"
            + "-fx-font-size: 40;";
    private static final String HELP_BUTTON_HOVER = "-fx-text-fill: yellow;" + "-fx-font-weight: bold;"
            + "-fx-background-color: rgba(255, 0, 0, 0);" + "-fx-background-size: 80;"
            + "-fx-font-size: 50;";

    private static final String EXIT_BUTTON = "-fx-text-fill: red;" + "-fx-font-weight: bold;"
            + "-fx-background-color: rgba(255, 0, 0, 0);" + "-fx-background-size: 50;"
            + "-fx-font-size: 40;";
    private static final String EXIT_BUTTON_HOVER = "-fx-text-fill: red;" + "-fx-font-weight: bold;"
            + "-fx-background-color: rgba(255, 0, 0, 0);" + "-fx-background-size: 80;"
            + "-fx-font-size: 50;";

    // nút chọn nhân vật
    private static final String ZIGGS = "-fx-font-weight: bold;" + "-fx-background-color: white;"
            + "-fx-background-radius: 20;" + "-fx-font-size: 18;" + "-fx-border-color: red;"
            + "-fx-text-fill: red;" + "-fx-border-radius: 20;";

    private static final String ZIGGS_HOVER = "-fx-font-weight: bold;" + "-fx-background-color: white;"
            + "-fx-background-radius: 20;" + "-fx-font-size: 20;" + "-fx-text-fill: red;"
            + "-fx-border-radius: 24;" + "-fx-border-width: 2;" + "-fx-border-color: red;";

    private static final String XIALING = "-fx-font-weight: bold;" + "-fx-background-color: white;"
            + "-fx-background-radius: 20;" + "-fx-font-size: 18;" + "-fx-border-color: #FF1493;"
            + "-fx-text-fill: #FF1493;" + "-fx-border-radius: 20;";

    private static final String XIALING_HOVER = "-fx-font-weight: bold;" + "-fx-background-color: white;"
            + "-fx-background-radius: 20;" + "-fx-font-size: 20;" + "-fx-text-fill: #FF1493;"
            + "-fx-border-radius: 24;" + "-fx-border-width: 2;" + "-fx-border-color: #FF1493;";

    private static final String BACK = "-fx-font-weight: bold;" + "-fx-background-color: white;"
            + "-fx-background-radius: 20;" + "-fx-font-size: 18;" + "-fx-border-color: blue;"
            + "-fx-text-fill: blue;" + "-fx-border-radius: 20;";

    private static final String BACK_HOVER = "-fx-font-weight: bold;" + "-fx-background-color: white;"
            + "-fx-background-radius: 20;" + "-fx-font-size: 20;" + "-fx-text-fill: blue;"
            + "-fx-border-radius: 24;" + "-fx-border-width: 2;" + "-fx-border-color: blue;";

    // nút skip
    private static String SKIP_BUTTON = "-fx-text-fill: red;" + "-fx-font-weight: bold;"
            + "-fx-background-color: rgba(255, 0, 0, 0);" + "-fx-background-size: 50;"
            + "-fx-font-size: 40;";

    @Override
    public void start(Stage stage) throws Exception {
        //tạo một Group chứa các đối tượng
        Group begin = new Group();

        //tạo màn hình, cỡ màn hình
        Scene sceneBegin = new Scene(begin, WIDTH, HEIGHT);

        //tên cửa sổ
        stage.setTitle("BOMBERMAN VERSION 1.5");

        //thêm màn hình vào cửa sổ
        stage.setScene(sceneBegin);

        //show ra nội dung stage
        stage.show();

        // các media
        MediaPlayer startPlayer = TheMedia.getMediaPlayer("./sound/pirates_indian.mp3");
        MediaPlayer mapPlayer1 = TheMedia.getMediaPlayer("./sound/space.mp3");
        MediaPlayer mapPlayer2 = TheMedia.getMediaPlayer("./sound/jingle_bells_indian.mp3");
        MediaPlayer diePlayer = TheMedia.getMediaPlayer("./sound/unravel.mp3");
        MediaPlayer trailerPlayer = TheMedia.getMediaPlayer("./video/trailer.mp4");
        MediaView trailerView = new MediaView(trailerPlayer);

        startPlayer.setAutoPlay(true);

        startPlayer.setOnEndOfMedia(() -> {
            startPlayer.seek(Duration.ZERO);
            startPlayer.play();
        });

        mapPlayer1.setOnEndOfMedia(() -> {
            mapPlayer1.seek(Duration.ZERO);
            mapPlayer1.play();
        });

        mapPlayer2.setOnEndOfMedia(() -> {
            mapPlayer2.seek(Duration.ZERO);
            mapPlayer2.play();
        });

        boolean start = false;
        ImageManage manage = new ImageManage(0, 0, HEIGHT, WIDTH, "./image/start_background.png");
        manage.loadImage(begin);

        Button startButton = new Button("Bắt đầu");

        startButton.setLayoutX(620);
        startButton.setLayoutY(500);

        startButton.setFont(new Font("Arial", 30));
        startButton.setStyle(START_BUTTON);
        startButton.setOnMouseEntered(mouseEvent -> startButton.setStyle(START_BUTTON_HOVER));
        startButton.setOnMouseExited(mouseEvent -> startButton.setStyle(START_BUTTON));

        Button helpButton = new Button("Hướng dẫn");

        // set vị trí của button
        helpButton.setLayoutX(250);
        helpButton.setLayoutY(500);

        helpButton.setFont(new Font("Arial", 30));
        helpButton.setStyle(HELP_BUTTON);
        helpButton.setOnMouseEntered(mouseEvent -> helpButton.setStyle(HELP_BUTTON_HOVER));
        helpButton.setOnMouseExited(mouseEvent -> helpButton.setStyle(HELP_BUTTON));

        Button exitButton = new Button("Thoát");

        exitButton.setLayoutX(960);
        exitButton.setLayoutY(500);
        exitButton.setFont(new Font("Arial", 30));
        exitButton.setStyle(EXIT_BUTTON);
        exitButton.setOnMouseEntered(mouseEvent -> exitButton.setStyle(EXIT_BUTTON_HOVER));
        exitButton.setOnMouseExited(mouseEvent -> exitButton.setStyle(EXIT_BUTTON));

        exitButton.setOnAction(event ->  {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Thoát khỏi trò chơi !");
            alert.setHeaderText("Bạn có thực sự muốn thoát ?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                stage.close();
            }
        });
        begin.getChildren().addAll(startButton, helpButton, exitButton);
        // chuyển sang trang hướng dẫn
        Pages.initHelp(sceneBegin, stage);
        Scene helpScene = Pages.getHelpScene();
        helpButton.setOnAction(e -> stage.setScene(helpScene));

        // video trailer
        Button skipButton = new Button("Skip »");
        skipButton.setLayoutX(1200);
        skipButton.setLayoutY(550);
        skipButton.setFont(new Font("Arial", 30));
        skipButton.setStyle(SKIP_BUTTON);

        Group trailerGroup = new Group(trailerView, skipButton);

        Scene trailerScene = new Scene(trailerGroup, 1395, 630);

        startButton.setOnAction(e -> {
            startPlayer.stop();
            stage.setScene(trailerScene);
            trailerPlayer.play();
        });


        //========== trang chọn nhân vật =========
        Group selectCharacter = new Group();
        Scene sceneSelectCharacter = new Scene(selectCharacter, WIDTH, HEIGHT);
        ImageManage select = new ImageManage(0, 0, HEIGHT, WIDTH, "./image/selectCharacter.png");
        select.loadImage(selectCharacter);

        skipButton.setOnAction(e -> {
            trailerPlayer.stop();
            stage.setScene(sceneSelectCharacter);
        });

        Button back = new Button("Back");
        back.setLayoutX(50);
        back.setLayoutY(570);
        back.setStyle(BACK);
        back.setOnMouseEntered(mouseEvent -> back.setStyle(BACK_HOVER));
        back.setOnMouseExited(mouseEvent -> back.setStyle(BACK));
        back.setOnAction(e -> stage.setScene(sceneBegin));
        // tạo một nhân vật
        Player player = new Player(45, 90, 45, 45, "./image/Ziggs/characterDown1.png");

        Button Ziggs = new Button("Ziggs");
        Ziggs.setLayoutX(170);
        Ziggs.setLayoutY(500);
        Ziggs.setFont(new Font("Arial", 25));
        Ziggs.setStyle(ZIGGS);
        Ziggs.setOnMouseEntered(mouseEvent -> Ziggs.setStyle(ZIGGS_HOVER));
        Ziggs.setOnMouseExited(mouseEvent -> Ziggs.setStyle(ZIGGS));

        Button XiaLing = new Button("XiaLing");
        XiaLing.setLayoutX(650);
        XiaLing.setLayoutY(500);
        XiaLing.setFont(new Font("Arial", 25));
        XiaLing.setStyle(XIALING);
        XiaLing.setOnMouseEntered(mouseEvent -> XiaLing.setStyle(XIALING_HOVER));
        XiaLing.setOnMouseExited(mouseEvent -> XiaLing.setStyle(XIALING));

        selectCharacter.getChildren().addAll(Ziggs, XiaLing, back);

        //back======== trang chứa map1 ===========
        Group map1Group = new Group();


        Button continue1 = new Button("Tiếp tục");
        continue1.setLayoutX(790);
        continue1.setLayoutY(0);
        continue1.setFont(new Font("Arial", 25));
        continue1.setStyle(ZIGGS);
        continue1.setFocusTraversable(false);

        Button pause1 = new Button("Tạm dừng");
        pause1.setLayoutX(940);
        pause1.setLayoutY(0);
        pause1.setFont(new Font("Arial", 25));
        pause1.setStyle(ZIGGS);
        pause1.setFocusTraversable(false);

        Button mute1 = new Button("Tắt tiếng");
        mute1.setLayoutX(1090);
        mute1.setLayoutY(0);
        mute1.setFont(new Font("Arial", 25));
        mute1.setStyle(ZIGGS);
        mute1.setFocusTraversable(false);

        Button unmute1 = new Button("Bật tiếng");
        unmute1.setLayoutX(1240);
        unmute1.setLayoutY(0);
        unmute1.setFont(new Font("Arial", 25));
        unmute1.setStyle(ZIGGS);
        unmute1.setFocusTraversable(false);

        map1Group.getChildren().addAll(continue1, pause1, mute1, unmute1);

        Scene sceneMap1 = new Scene(map1Group, WIDTH, HEIGHT);
        //---------------------- trang chứa map2--------------------------------------
        Group map2Group = new Group();
        Scene sceneMap2 = new Scene(map2Group, WIDTH, HEIGHT);

        //===========================

        ArrayList <ImageManage> hearts = new ArrayList<>();

        Ziggs.setOnAction(event -> {
            stage.setScene(sceneMap1);
            player.setName("Ziggs");
            player.setURL("./image/Ziggs/characterDown1.png");
            ImageManage ziggs = new ImageManage(0, 0, 45, 149, "./image/ZiggsImage.png");
            mapPlayer1.play();
            try {
                ziggs.loadImage(map1Group);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                player.loadImage(map1Group);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            player.setHeart(5);
            for(int i = 0; i < player.getHeart(); i++) {
                ImageManage heartElement = new ImageManage(150 + i * 48, 0, 45, 48, "./image/Heart.png");
                try {
                    heartElement.loadImage(map1Group);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                hearts.add(heartElement);
            }

        });

        XiaLing.setOnAction(event ->  {
            stage.setScene(sceneMap1);
            player.setName("XiaLing");
            player.setURL("./image/XiaLing/characterDown1.png");
            ImageManage xiaLing = new ImageManage(0, 0, 45, 149, "./image/XiaLingImage.png");
            mapPlayer1.play();
            try {
                xiaLing.loadImage(map1Group);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                player.loadImage(map1Group);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            player.setHeart(3);
            for(int i = 0; i < player.getHeart(); i++) {
                ImageManage heartElement = new ImageManage(150 + i * 48, 0, 45, 48, "./image/Heart.png");
                try {
                    heartElement.loadImage(map1Group);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                hearts.add(heartElement);
            }
            player.setSpeed(5);
        });


        // khai báo map
        Map map1 = new Map();
        map1.setURLmap("./map.txt");
        map1.loadMap(map1Group);

        // -------------trang khi chết----------------------------

        Group die = new Group();
        Scene sceneDie = new Scene(die, WIDTH, HEIGHT);
        ImageManage failed = new ImageManage(0, 0, HEIGHT, WIDTH, "./image/failed.png");
        failed.loadImage(die);
        Button continueButton = new Button("Play again");
        continueButton.setLayoutX(650);
        continueButton.setLayoutY(500);
        die.getChildren().add(continueButton);
        continueButton.setOnAction(event -> {
            stage.setScene(sceneMap1);
        });

        // -------------------------------------------------------------------phần character ------------------------------------------------------------------

        player.setHandle(sceneMap1);

        ListEnemy listEnemy = new ListEnemy();
        listEnemy.setUpEnemy(map1, map1Group);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // xử lý khi nhân vật chết
                if (listEnemy.getEnemies().size() != 0 && player.isDie(listEnemy, map1)) {
                    mapPlayer2.stop();
                    //diePlayer.play();
                    if (player.getHeart() <= 1) {
                        stage.setScene(sceneDie);
                        refreshTheMap(map1, map1Group, player, listEnemy);
                        if (player.getName().equals("Ziggs")) {
                            player.setHeart(5);
                        }
                        else {
                            player.setHeart(3);
                        }
                        int i = 0;
                        for(; i < hearts.size(); i++) {
                            try {
                                hearts.get(i).loadImage(map1Group);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                    player.setPositionX(45);
                    player.setPositionY(90);
                    try {
                        player.loadImage(map1Group);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    int heart = player.getHeart();
                    map1Group.getChildren().remove(hearts.get(heart - 1).getImageView());
                    player.setHeart(heart - 1);
                }
                // chuyển sang map tiếp theo
                if (map1.isNextMap()) {
                    mapPlayer1.stop();
                    mapPlayer2.play();
                    if (!nextMap1) {
                        refreshTheMap(map1, map1Group, player, listEnemy);
                        Enemy boss = new Enemy(225, 225, 75, 75, "./image/bossDown1.png", "boss");
                        listEnemy.getEnemies().add(boss);
                        try {
                            boss.loadImage(map1Group);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        nextMap1 = true;
                    }
                    player.handlePlayer(map1Group, map1, listEnemy);
                    listEnemy.updateListEnemy(map1Group, map1, player);

                } else {
                    player.handlePlayer(map1Group, map1, listEnemy);
                    listEnemy.updateListEnemy(map1Group, map1, player);
                }
            }
        };

        pause1.setOnAction(event -> timer.stop());

        continue1.setOnAction(event -> timer.start());

        mute1.setOnAction(event -> mapPlayer1.pause());

        unmute1.setOnAction(event -> mapPlayer1.play());

        timer.start();
    }

    public void refreshTheMap(Map map1, Group map1Group, Player player, ListEnemy listEnemy) {
        map1.getMaps().clear();
        map1.getListMap().clear();

        if (!player.isDie(listEnemy, map1)) {
            map1.setURLmap("./Map2.txt");
        }
        try {
            map1.loadMap(map1Group);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        player.setPositionX(45);
        player.setPositionY(90);
        try {
            player.loadImage(map1Group);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        listEnemy.getEnemies().clear();

        Random generator = new Random();
        int stepI = generator.nextInt(3) + 1;
        int stepJ = generator.nextInt(2) + 1;

        int widthTileMap = map1.getWidthTileMap();
        int heightTileMap = map1.getHeightTileMap();

        double tileSize = map1.getTileSize();
        int index = 0;
        while (listEnemy.getEnemies().size() == 0) {
            for (int i = 4; i < heightTileMap; i += stepI) {
                for (int j = 3; j < widthTileMap; j += stepJ) {
                    if (index >= 10) break;

                    int tileNumber = map1.getListMap().get(i).get(j);
                    if (tileNumber == 0) {
                        index++;
                        int moveDefault = generator.nextInt(2) + 1;
                        int randomVal = generator.nextInt(2) + 1;
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

                        try {
                            elementEnemy.loadImage(map1Group);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        listEnemy.getEnemies().add(elementEnemy);
                    }
                }
            }
        }
    }

    public static double getWidth() {
        return WIDTH;
    }

    public static double getHeight() {
        return HEIGHT;
    }

    public static void main(String args[]) {
        launch(args);
    }

}