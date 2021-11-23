package gameobjects.updatable;

import catgame.GameState;
import catgame.Updater;

/**
 * Updatable is the first of a series of subclasses that provide the core functionality to game
 * objects. Updatate represents objects that change over time in the course of the game. Not all
 * objects that change over time are Updatable, but most are. This might be merged with Drawable.
 * Right now, all updatables are also drawables!
 */
public abstract class Updatable {

  protected GameState theGameState;
  private Updater theUpdater;
  private boolean dead = false;

  /**
   * This constructor actually does a lot of work! It registers the updatable with theGameState,
   * asking to be informed that it is time to update.
   *
   * @param aGameState the gamestate of the game
   */
  public Updatable(GameState aGameState) {

    theGameState = aGameState;
    theUpdater = theGameState.getUpdater();
    dead = false;

    theUpdater.registerUpdatable(this);
  }

  public boolean isDead() {

    return dead;
  }
  /**
   * When an updatable is destroyed, kill is called to deregister the updatable. All registrations
   * are handled in kill and its overrides.
   */
  public void kill() {

    dead = true; // the updater culls dead elements after every update;
  }

  /**
   * update informs the updatable that it needs to update itself. Updates should be in as much as
   * possible independent of ordering.
   *
   * @param deltaT time between updates
   */
  public abstract void update(double deltaT);
}
