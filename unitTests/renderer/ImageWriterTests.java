package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ImageWriter class.
 */
class ImageWriterTests {
    ImageWriter imageWriter = new ImageWriter("first", 800,500);

    /**
     * Tests the writeToImage() method of ImageWriter by writing different colors to pixels in the image.
     */
    @Test
    void testWriteToImage() {

        // Fill the entire image with a specific color (64, 86, 255).
        for (int i = 0; i < 800; i++) {
            for (int j = 0; j < 500; j++) {
                imageWriter.writePixel(i, j, new Color(64, 86, 255));
            }
        }

        // Write a different color (255, 45, 37) to every 50th pixel in the vertical direction.
        for (int i = 0; i < 800; i++) {
            for (int j = 0; j < 500; j += 50) {
                imageWriter.writePixel(i, j, new Color(255, 45, 37));
            }
        }

        // Write a different color (255, 45, 37) to every 50th pixel in the horizontal direction.
        for (int i = 0; i < 800; i += 50) {
            for (int j = 0; j < 500; j++) {
                imageWriter.writePixel(i, j, new Color(255, 45, 37));
            }
        }

        // Write the image to a file.
        imageWriter.writeToImage();
    }
}