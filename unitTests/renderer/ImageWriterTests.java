package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;


class ImageWriterTests {
    ImageWriter imageWriter = new ImageWriter("first", 800,500);
    @Test
    void testWriteToImage() {
        imageWriter.writeToImage();
    }

    @Test
    void testWritePixel() {
        imageWriter.writePixel(10,16, new Color(255,0,0));
    }
}