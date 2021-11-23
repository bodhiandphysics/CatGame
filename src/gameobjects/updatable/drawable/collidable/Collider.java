package gameobjects.updatable.drawable.collidable;

import catgame.GameState;
import gameobjects.updatable.Updatable;
import gameobjects.updatable.drawable.collidable.pawn.Hero;
import java.util.ArrayList;

/**
 * Collider is a simple server to check collisions with heros. Objects register to be notified if
 * they collided and if so procdess collisions
 */
public class Collider extends Updatable {

  private ArrayList<Collidable> objectsToCollide;

  /** Constructs the collider server. Should only be called in a level loader */
  public Collider(GameState aGameState) {

    super(aGameState);
    objectsToCollide = new ArrayList<>();
  }

  /**
   * Registers an object to be notified of collions
   *
   * @param object The object to recieve notifications.
   */
  public void registerCollidable(Collidable object) {

    objectsToCollide.add(object);
  }

  // Checks if the object is dead. If dead then remove the object

  private void cullDead() {

    objectsToCollide.removeIf((Collidable object) -> object.isDead());
  }

  /** Loop through the objectsToCollide ArrayList to update every object */
  public void update(double deltaT) {
    cullDead();

    Hero theHero = theGameState.getHero();

    for (Collidable object : objectsToCollide) {
      if (object.collidesWith(theHero)) {

        object.collisionWithHero();
      }
    }
  }
}
