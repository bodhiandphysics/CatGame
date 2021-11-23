package gameobjects.updatable.drawable.collidable.collectables;

import catgame.GameState;
import geometry.Point;
import javafx.scene.image.Image;

/** @author Mingyang Li */
public class Treasure extends Collectables {

  public Treasure(GameState aGameState, Point aPosition) {
    super(aGameState, aPosition);
  }

  /**
   * This is used to determine the Treasure should not be drew for the next screen image if it
   * collides with the Cat.
   */
  @Override
  public void collisionWithHero() {
    theGameState.addToScore(2);
    kill();
  }

  /**
   * This method update the Treasure
   *
   * @param deltaT time between updates
   */
  @Override
  public void update(double deltaT) {}

  /**
   * This method gives the zDepth of the Treasure.
   *
   * @return 2 because it goes above tiles, but below pawns
   */
  @Override
  public int getzDepth() {
    return 3;
  }

  /** Provides the gui image of the Treasure */
  @Override
  public Image getScreenImage() {

    return theGameState.getImageManager().getTreasureImage();
  }
  /**
   * This method returns the screen representation of the Treasure.
   *
   * @return 't' for treasure
   */
  @Override
  public char getScreenGlyph() {

    return 't';
  }
}
