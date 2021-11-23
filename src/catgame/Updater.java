package catgame;

import gameobjects.updatable.*;
import gameobjects.updatable.Updatable;
import gameobjects.updatable.controller.PawnController;
import gameobjects.updatable.drawable.collidable.Collidable;
import gameobjects.updatable.drawable.collidable.Collider;
import gameobjects.updatable.drawable.collidable.pawn.Hero;
import gameobjects.updatable.drawable.collidable.pawn.Pawn;
import gameobjects.updatable.drawable.tile.Tile;
import java.util.PriorityQueue;
import javafx.animation.AnimationTimer;

/**
 * The updater is a server that maintains the main game loop. Objects register with the updater to
 * recieve notifications that it is time to update. The updater maintains the game clock, and
 * communicates time changes to its clients.
 *
 * @author rylanl
 */
public class Updater {

  PriorityQueue<Updatable> theUpdateQueue;
  GameTimer timer;
  GameState theGameState;
  boolean isPaused = true;

  //  The gametimer maintains the game clock.  Javafx animation timers recored absolute times as
  // longs measured in nanoseconds.  Its more
  // conventient to work in seconds, however, and with time between update (deltat), so we convert
  // to that format.
  private class GameTimer extends AnimationTimer {
    long currentTime = 0;
    double currentDeltaT =
        0; // in case of pause, wait a frame, then restart, so that the delta t is correct
    boolean wasPaused = true;

    @Override
    public void handle(long now) {
      if (wasPaused) {
        wasPaused = false;
      } else {
        long currentDeltaT = (now - currentTime);
        cullDead(); // bury the dead --hopefully that which is already dead can in fact die.
        for (Updatable object : theUpdateQueue) {
          object.update(
              currentDeltaT
                  / 1000000000.0); // now is given in nanoseconds. It's more convenient to work in
          // seconds
        }

        checkForGameEnd();
      }
      currentTime = now;
    }
  }

  /** @param aGameState */

  // ordering of events is complicated.  To simplify it, we store the updatables in a priority
  // queue, with the priority used to
  // determine order
  public Updater(GameState aGameState) {

    theUpdateQueue =
        new PriorityQueue<Updatable>(
            (Updatable object1, Updatable object2) -> {
              int o1Priority = getPriority(object1);
              int o2Priority = getPriority(object2);
              if (o1Priority < o2Priority) return -1;
              if (o1Priority > o2Priority) return 1;
              return 0;
            });
    theGameState = aGameState;
    theGameState.setUpdater(this);
    timer = new GameTimer();
    pause(); // make sure we're not running!
  }

  /* Get the priority... this is here so the ordering is all in one place for easy modification
   * The ordering is Tile-> PawnController -> Hero -> Pawn -> Collidable -> Collider -> HeatMap ->
   * Screen
   *
   */

  private int getPriority(Updatable object) {

    if (object instanceof Tile) return 1;
    if (object instanceof PawnController) return 2;
    if (object instanceof Hero) return 3;
    if (object instanceof Pawn) return 4;
    if (object instanceof Collidable) return 5;
    if (object instanceof Collider) return 6;
    if (object instanceof HeatMap) return 7;
    return 8;
  }

  /**
   * Register an updatable with the updater to recieve notifications
   *
   * @param object the object to register
   */
  public void registerUpdatable(
      Updatable
          object) { // note can't add objects except when updater is paused... sbould fix this.

    theUpdateQueue.add(object);
  }

  private void cullDead() {

    theUpdateQueue.removeIf((Updatable object) -> object.isDead());
  }

  private void pause() {

    timer.stop();
    timer.wasPaused = true;
    ;
  }

  private void play() {
    timer.start();
  }

  /** Toggle between the states paused and unpaused. */
  public void togglePause() {
    if (isPaused) {
      theGameState.getScreen().hidePause();
      play();
      isPaused = false;

    } else {
      pause();
      theGameState.getScreen().showPause();
      isPaused = true;
    }
  }

  /*
   * The updater checks for game end, and closes the program.
   */

  private void checkForGameEnd() {

    if (theGameState.isDone()) {
      pause();
      theGameState.getMainApp().returnToApp();
    }
  }
}
