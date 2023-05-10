package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;


class ImageWriterTests {
    ImageWriter imageWriter = new ImageWriter("first", 800,500);
    @Test
    void testWriteToImage() {

        for (int i = 0;i<800;i++) {
            for (int j = 0; j < 500; j++) {
                imageWriter.writePixel(i, j, new Color(64, 86, 255));
            }
        }
        for (int i = 0;i<800;i++) {
            for (int j = 0; j < 500; j+=50) {
                imageWriter.writePixel(i, j, new Color(255, 45, 37));
            }
        }
        for (int i = 0;i<800;i+=50) {
            for (int j = 0; j < 500; j++) {
                imageWriter.writePixel(i, j, new Color(255, 45, 37));
            }
        }
        imageWriter.writeToImage();
    }



}