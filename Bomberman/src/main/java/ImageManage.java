import javafx.scene.Group;
import javafx.scene.image.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageManage {
    private double positionX;
    private double positionY;
    private double height;
    private double width;
    private String URL;
    private static final int TOLERANCE_THRESHOLD = 0xFF;

    protected ImageView imageView;
    protected Image image;

    public ImageManage(double positionX, double positionY, double height, double width, String URL) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.height = height;
        this.width = width;
        this.URL = URL;
    }

    public ImageManage() {

    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    // tham khảo trên stackoverflow
    private Image makeTransparent(Image inputImage) {
        int W = (int) inputImage.getWidth();
        int H = (int) inputImage.getHeight();
        WritableImage outputImage = new WritableImage(W, H);
        PixelReader reader = inputImage.getPixelReader();
        PixelWriter writer = outputImage.getPixelWriter();
        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                int argb = reader.getArgb(x, y);

                int r = (argb >> 16) & 0xFF;
                int g = (argb >> 8) & 0xFF;
                int b = argb & 0xFF;

                if (r >= TOLERANCE_THRESHOLD
                        && g >= TOLERANCE_THRESHOLD
                        && b >= TOLERANCE_THRESHOLD) {
                    argb &= 0x00FFFFFF;
                }

                writer.setArgb(x, y, argb);
            }
        }

        return outputImage;
    }

    public void loadImage(Group root) throws FileNotFoundException {
        root.getChildren().remove(imageView);
        //tạo một đối tượng ảnh
        Image temp = new Image(new FileInputStream(URL));
        image = makeTransparent(temp);
        //cài đặt image view
        imageView = new ImageView(image);

        //cài đặt vị trí
        imageView.setX(positionX);
        imageView.setY(positionY);

        //cài đặt cỡ ảnh
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);

        //Duy trì tỉ lệ
        imageView.setPreserveRatio(true);
        root.getChildren().add(imageView);
    }



}
