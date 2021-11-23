package ui;

import catgame.GameState;
import gameobjects.updatable.Updatable;
import gameobjects.updatable.drawable.Drawable;
import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * This class extends Updatable and implements a screen to draw the game.
 *
 * @author Mingyang Li
 */
public class Screen extends Updatable {
  private int height;
  private int length;
  private ArrayList<Drawable> objectsToDraw;
  private GameState theGameState;
  private Group screenGroup;
  private static final int PIXELCONVERT = 32;
  private Label pauseLabel;
  private Label scoreLabel;

  /**
   * This is the constructor of the class which takes 4 parameters
   *
   * @param aGameState the gamestate of the cuyrrent game
   * @param height the height of the map
   * @param length the length of the map
   */
  public Screen(GameState aGameState, int length, int height) {
    super(aGameState);
    this.theGameState = aGameState;
    this.height = height * PIXELCONVERT;
    this.length = length * PIXELCONVERT;
    screenGroup = new Group();
    objectsToDraw = new ArrayList<>();
  }

  /**
   * Get the length of the screen in pixels
   *
   * @return the length of the screen in pixels
   */
  public int getLength() {

    return length;
  }

  /**
   * Get the height of the screen in pixels
   *
   * @return the height of the screen in pixels
   */
  public int getHeight() {

    return height;
  }

  /**
   * Get the screen group that actually displays the game
   *
   * @return the screen group that represents the gui display
   */
  public Group getScreenGroup() {

    return screenGroup;
  }

  /**
   * This method regeister an object into the screen and it takes one parameters
   *
   * @param aDrawable an object of type Drawable
   */
  public void registerDrawable(Drawable aDrawable) {

    objectsToDraw.add(aDrawable);
    ImageView screenImage = new ImageView();
    screenImage.setImage(aDrawable.getScreenImage());
    aDrawable.setImageView(screenImage);
    screenImage.setTranslateX(aDrawable.getPosition().getX() * PIXELCONVERT);
    screenImage.setTranslateY(aDrawable.getPosition().getY() * PIXELCONVERT);
    screenImage.setViewOrder(aDrawable.getzDepth());
    screenGroup.getChildren().add(screenImage);
  }

  /**
   * This method checks that if an object is dead. If it is dead then its image will be removed from
   * the screen. If it is not dead the its image will be added into the screen.
   */
  private void cullDead() {
    // Go through the Drawable ArrayList to check for each object

    for (Drawable object : objectsToDraw) {

      if (object.isDead()) screenGroup.getChildren().remove(object.getImageView());
    }

    objectsToDraw.removeIf((Drawable object) -> object.isDead());
  }

  /**
   * This method checks if the object's image has changed and if the object's position has changed.
   * If they have changed, then set up the new image and the new position of the object
   *
   * @param deltaT
   */
  public void update(double deltaT) {

    cullDead();

    for (Drawable object : objectsToDraw) {
      if (object.getImageChanged()) object.getImageView().setImage(object.getScreenImage());
      if (object.getPositionChanged()) {
        object.getImageView().setTranslateX(object.getPosition().getX() * PIXELCONVERT);
        object.getImageView().setTranslateY(object.getPosition().getY() * PIXELCONVERT);
      }
    }
  }

  public void showLevelTitle() {

    Label levelTitle = new Label(theGameState.getLevelName());
    levelTitle.setTranslateX(105);
    screenGroup.getChildren().add(levelTitle);
  }

  /** Update the screen to display paused status */
  public void showPause() {

    pauseLabel = new Label("Paused");
    pauseLabel.setTextFill(Color.RED);
    screenGroup.getChildren().add(pauseLabel);
  }

  /** Update the screen to hide paused status */
  public void hidePause() {

    if (pauseLabel != null) screenGroup.getChildren().remove(pauseLabel);
  }

  /** Update the screen to show the current score */
  public void updateScore() {

    int currentScore = theGameState.getScore();

    if (scoreLabel == null) {
      scoreLabel = new Label("Score: " + currentScore);
      scoreLabel.setTextFill(Color.RED);
      screenGroup.getChildren().add(scoreLabel);
      scoreLabel.setViewOrder(0);
      scoreLabel.setTranslateX(50);
    } else scoreLabel.setText("Score: " + currentScore);
  }
}
