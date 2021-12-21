import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class Pages {
    private static Scene helpScene;
    private static Scene failedScene;
    private static Button backButton;
    private static Button playAgainButton;
    private static Button backButtonFailed;

    // nút back trang help
    private static String BACK_BUTTON = "-fx-text-fill: #007BFF;" + "-fx-font-weight: bold;"
            + "-fx-background-color: rgba(255, 0, 0, 0);" + "-fx-background-size: 50;"
            + "-fx-font-size: 40;";
    private static final String BACK_BUTTON_HOVER = "-fx-text-fill: #007BFF;" + "-fx-font-weight: bold;"
            + "-fx-background-color: rgba(255, 0, 0, 0);" + "-fx-rotate: -15;"
            + "-fx-font-size: 40;";

    public static void initHelp(Scene startScene, Stage stage) throws FileNotFoundException {
        // ========= trang hướng dẫn ===========

        // tạo button back
        backButton = new Button("Back");
        // set vị trí của button
        backButton.setLayoutX(50);
        backButton.setLayoutY(555);
        // kiểu, cỡ chữ button
        backButton.setFont(new Font("Arial", 30));
        // tạo màu, hình, kiểu chữ cho button
        backButton.setStyle(BACK_BUTTON);
        backButton.setOnMouseEntered(mouseEvent -> backButton.setStyle(BACK_BUTTON_HOVER));
        backButton.setOnMouseExited(mouseEvent -> backButton.setStyle(BACK_BUTTON));

        Group helpRoot = new Group();
        ImageManage helpImage = new ImageManage(0, 0, Main.getHeight(), Main.getWidth(), "./image/help_background.png");
        helpImage.loadImage(helpRoot);

        // các ảnh trong trang hướng dẫn
        ImageManage moveImage = new ImageManage(30, 255, 30, 30, "./image/move.png");
        moveImage.loadImage(helpRoot);

        ImageManage wallBrick = new ImageManage(30, 325, 32, 32, "./image/wall_brick.png");
        wallBrick.loadImage(helpRoot);

        ImageManage helpWall1 = new ImageManage(370, 330, 20, 20, "./image/help_wall1.png");
        helpWall1.loadImage(helpRoot);

        ImageManage helpWall2 = new ImageManage(440, 366, 20, 20, "./image/help_wall2.png");
        helpWall2.loadImage(helpRoot);

        ImageManage helpWall3 = new ImageManage(305, 400, 20, 20, "./image/help_wall1.png");
        helpWall3.loadImage(helpRoot);

        ImageManage arrowImage = new ImageManage(345, 245, 45, 45, "./image/arrow.png");
        arrowImage.loadImage(helpRoot);

        ImageManage spacebar = new ImageManage(365, 280, 52, 52, "./image/spacebar.png");
        spacebar.loadImage(helpRoot);

        ImageManage twisted = new ImageManage(30, 575, 50, 48, "./image/twisted_arrow.png");
        twisted.loadImage(helpRoot);

        ImageManage monster = new ImageManage(35, 425, 25, 28, "./image/monster.png");
        monster.loadImage(helpRoot);

        ImageManage ghost = new ImageManage(360, 425, 33, 33, "./image/OnealDown1.png");
        ghost.loadImage(helpRoot);

        ImageManage pumpkin = new ImageManage(240, 460, 35, 35, "./image/BalloomDown1.png");
        pumpkin.loadImage(helpRoot);

        ImageManage godzilla = new ImageManage(145, 495, 55, 55, "./image/bossDown3.png");
        godzilla.loadImage(helpRoot);

        ImageManage speed = new ImageManage(435, 400, 20, 20, "./image/speedItem.png");
        speed.loadImage(helpRoot);

        ImageManage bomb = new ImageManage(625, 400, 20, 20, "./image/bombItem.png");
        bomb.loadImage(helpRoot);

        ImageManage exp = new ImageManage(845, 400, 20, 20, "./image/flameItem.png");
        exp.loadImage(helpRoot);

        ImageManage gate = new ImageManage(35, 545, 25, 25, "./image/gateItem.png");
        gate.loadImage(helpRoot);

        // text hướng dẫn
        Text text1 = new Text();
        text1.setText("Sử dụng các phím mũi tên          để di chuyển nhân vật");
        text1.setFont(Font.font("Arial", 22));
        text1.setStyle("-fx-font-weight: bold;");
        text1.setFill(Color.RED);
        text1.setLayoutX(65);
        text1.setLayoutY(275);

        Text text2 = new Text();
        text2.setText("Sử dụng phím khoảng trắng          để đặt bomb");
        text2.setFont(Font.font("Arial", 22));
        text2.setStyle("-fx-font-weight: bold;");
        text2.setFill(Color.RED);
        text2.setLayoutX(65);
        text2.setLayoutY(315);

        Text text3 = new Text();
        text3.setText("Những bức tường phá được");
        text3.setFont(Font.font("Arial", 22));
        text3.setStyle("-fx-font-weight: bold;");
        text3.setFill(Color.rgb(255, 55, 0));
        text3.setLayoutX(65);
        text3.setLayoutY(350);

        Text text4 = new Text();
        text4.setText("Những bức tường không phá được");
        text4.setFont(Font.font("Arial", 22));
        text4.setStyle("-fx-font-weight: bold;");
        text4.setFill(Color.rgb(255, 55, 0));
        text4.setLayoutX(65);
        text4.setLayoutY(385); //walls containing items

        Text text5 = new Text();
        text5.setText("Tường chứa vật phẩm     (Tăng tốc     , Tăng số bomb     , Tăng phạm vi nổ     )");
        text5.setFont(Font.font("Arial", 22));
        text5.setStyle("-fx-font-weight: bold;");
        text5.setFill(Color.rgb(255, 55, 0));
        text5.setLayoutX(65);
        text5.setLayoutY(420);

        Text text6 = new Text();
        text6.setText("Cẩn thận với những con ma      chúng có thể tàng hình và đuổi theo bạn");
        text6.setFont(Font.font("Arial", 22));
        text6.setStyle("-fx-font-weight: bold;");
        text6.setFill(Color.PURPLE);
        text6.setLayoutX(65);
        text6.setLayoutY(450);

        Text text7 = new Text();
        text7.setText("Bí ngô hiền lành       chỉ đi lại một chỗ thôi");
        text7.setFont(Font.font("Arial", 22));
        text7.setStyle("-fx-font-weight: bold;");
        text7.setFill(Color.PURPLE);
        text7.setLayoutX(65);
        text7.setLayoutY(485);

        Text text8 = new Text();
        text8.setText("Godzilla       cực kỳ nguy hiểm, hãy thận trọng");
        text8.setFont(Font.font("Arial", 22));
        text8.setStyle("-fx-font-weight: bold;");
        text8.setFill(Color.PURPLE);
        text8.setLayoutX(65);
        text8.setLayoutY(530);

        Text text9 = new Text();
        text9.setText("Cánh cổng sẽ dẫn bạn đến chiến thắng hoặc một nơi nguy hiểm hơn");
        text9.setFont(Font.font("Arial", 22));
        text9.setStyle("-fx-font-weight: bold;");
        text9.setFill(Color.BLUE);
        text9.setLayoutX(65);
        text9.setLayoutY(570);

        helpRoot.getChildren().addAll(text1, text2, text3, text4, text5, text6, text7, text8, text9, backButton);
        // tạo trang hướng dẫn

        helpScene = new Scene(helpRoot, 1395, 630);

        backButton.setOnAction(event -> stage.setScene(startScene));
    }


    public static Scene getFailedScene() {
        return failedScene;
    }

    public static Scene getHelpScene() {
        return helpScene;
    }

}
