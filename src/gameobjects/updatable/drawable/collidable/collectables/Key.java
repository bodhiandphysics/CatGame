package gameobjects.updatable.drawable.collidable.collectables;

import catgame.GameState;
import geometry.Point;
import javafx.scene.image.Image;

/** Defines a key used to unlock the gate */
public class Key extends Collectables {

  private boolean aKey = true;
  private char drawKey = 'K';

  /**
   * Creates a Key
   *
   * @param aGameState the state of the game
   * @param x where the key starts
   * @param y where the key starts
   */
  public Key(GameState aGameState, Point aPosition) {

    super(aGameState, aPosition);
  }

  @Override
  public void update(double deltaT) {}
  /** What to do if collides with hero... in this case, unlock the gate! */
  @Override
  public void collisionWithHero() {

    theGameState.decrementKeys();
    kill();
  }

  /**
   * What is the zDepth
   *
   * @return 2 for collectables
   */
  @Override
  public int getzDepth() {
    return 3;
  }

  @Override
  public Image getScreenImage() {

    return theGameState.getImageManager().getKeyImage();
  }
  /**
   * What is the screenglyph?
   *
   * @return k for key
   */
  @Override
  public char getScreenGlyph() {
    return 'k';
  }
}
