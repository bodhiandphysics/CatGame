package ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;

/** This class manages all the images of our objects */
public class ImageManager {

  private Image catImage;
  private Image dogImage;
  private Image keyImage;
  private Image treasureImage;
  private Image wallImage;
  private Image floorImage;
  private Image closedGateImage;
  private Image openGateImage;

  /**
   * This method read a image file that is in jpeg format and checks if the size of the image is 32
   * pixel x 32 pixel. If it does not exist then program exits
   */
  public void loadImages() {

    try {

      catImage = new Image(new FileInputStream("images/Cat.jpeg"), 32, 32, true, true);
      dogImage = new Image(new FileInputStream("images/Dog.jpeg"), 32, 32, true, true);
      keyImage = new Image(new FileInputStream("images/Key.jpeg"), 32, 32, true, true);
      treasureImage = new Image(new FileInputStream("images/Treasure.jpeg"), 32, 32, true, true);
      wallImage = new Image(new FileInputStream("images/Wall.jpeg"), 32, 32, true, true);
      floorImage = new Image(new FileInputStream("images/Floor.jpeg"), 32, 32, true, true);
      closedGateImage =
          new Image(new FileInputStream("images/ClosedGate.jpeg"), 32, 32, true, true);
      openGateImage = new Image(new FileInputStream("images/OpenGate.jpeg"), 32, 32, true, true);

    } catch (FileNotFoundException e) {

      e.printStackTrace();
      System.exit(-1);
    }
  }

  // images are large and I only want one copy per game!

  /**
   * This method returns the image of the cat
   *
   * @return catImage the image of cat
   */
  public Image getCatImage() {

    return catImage;
  }

  /**
   * This method returns the image of the dog
   *
   * @return catImage the image of dog
   */
  public Image getDogImage() {

    return dogImage;
  }

  /**
   * This method returns the image of the key
   *
   * @return catImage the image of key
   */
  public Image getKeyImage() {

    return keyImage;
  }

  /**
   * This method returns the image of the Treasure
   *
   * @return catImage the image of Treasure
   */
  public Image getTreasureImage() {

    return treasureImage;
  }

  /**
   * This method returns the image of the wall
   *
   * @return catImage the image of wall
   */
  public Image getWallImage() {

    return wallImage;
  }

  /**
   * This method returns the image of the floor
   *
   * @return catImage the image of floor
   */
  public Image getFloorImage() {

    return floorImage;
  }

  /**
   * This method returns the image of the closed gate
   *
   * @return catImage the image of closed gate
   */
  public Image getClosedGateImage() {

    return closedGateImage;
  }

  /**
   * This method returns the image of the opened gate
   *
   * @return catImage the image of opened gate
   */
  public Image getOpenGateImage() {

    return openGateImage;
  }
}
