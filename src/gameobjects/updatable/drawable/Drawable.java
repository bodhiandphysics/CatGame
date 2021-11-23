package gameobjects.updatable.drawable;

import catgame.GameState;
import gameobjects.updatable.Updatable;
import geometry.Point;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ui.ImageManager;
import ui.Screen;

/** Drawable extends Updatable to describe drawable objects */
public abstract class Drawable extends Updatable {

  protected Point position;
  private Screen theScreen;
  protected boolean imageChanged;
  protected boolean positionChanged;
  private ImageView imageView;
  private Image tileImage;
  private ImageManager imageManager;
  /**
   * Registers the drawable to be drawn with gamestate. Also calls the updatable constructor, to
   * register for updates.
   *
   * @param aGameState the Gamestate of the game
   * @param startx xcoord the drawable starts at
   * @param starty ycoord the drawable starts at
   */
  public Drawable(GameState aGameState, Point aPosition) {

    super(aGameState);

    position = aPosition;
    theScreen = theGameState.getScreen();
    imageManager = theGameState.getImageManager();
    theScreen.registerDrawable(this);
  }

  /**
   * This method gives the imageView
   *
   * @return imageView
   */
  public ImageView getImageView() {

    return imageView;
  }

  /** This method sets the imageView */
  public void setImageView(ImageView view) {

    imageView = view;
  }

  public abstract Image getScreenImage();

  /**
   * This method returns that if the image of the object has changed
   *
   * @return a boolean imageChanged
   */
  public boolean getImageChanged() {

    return imageChanged;
  }

  /** This method sets up the instance variable setImageChanged of type boolean */
  public void setImageChanged(boolean value) {

    imageChanged = value;
  }

  /**
   * This method returns that if the position of the object has changed
   *
   * @return a boolean positionChanged
   */
  public boolean getPositionChanged() {

    return positionChanged;
  }

  /** This method sets up the instance variable setPositionChanged of type boolean */
  public void setPositionChanged(boolean value) {

    positionChanged = value;
  }

  /**
   * This method returns the position of the object of type Point
   *
   * @return Point(position) a deep copy of position
   */
  public Point getPosition() {

    return new Point(position);
  }

  public abstract int getzDepth();

  public abstract char getScreenGlyph();
}
