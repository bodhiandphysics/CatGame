package gameobjects.updatable.controller;

import catgame.GameState;
import gameobjects.updatable.drawable.collidable.pawn.Pawn;
import geometry.Point;
import geometry.UnitVector;
import java.util.Random;

/**
 * This is a simple, but I think interesting, AI for enemy characters. It chases the down player
 * while at the same time not requiring an O(N) search like A* or Dykstra. Effectively, it uses the
 * heuristic of A* without the search... it goes to the closes nearby Tile to the Hero that can be
 * entered. This creates a number of problems. First, it will cause AI Pawns to hug walls, which
 * seems to me to be unnatural. Moreover, it will cause AI Pawns to get stuck in dead ends. In order
 * to solve this problem, the AI also uses a heatmap. When the AI enters a square, it increases the
 * heat value of that square. When the player enters a square, it decreases the heat value of the
 * square. By setting a default value for every tile's heat, we can also prevent wall hugging. *
 */
public class SimpleAiController extends PawnController {

  private double heatMapModificationFactor =
      3; // This value needs to be iterated for good gameplay.

  /**
   * Calls in 2 needed classes for the AI to function.
   *
   * @param agamestate gives in the necessary access into the Gamestate class
   * @param aPawn gives the necessary access into the Pawn class
   */
  public SimpleAiController(GameState agamestate, Pawn aPawn) {

    super(agamestate, aPawn);
  }

  /**
   * This is used by the AI to know what path to follow on the map.
   *
   * @return heat map modification factor
   */
  @Override
  double getHeatMapModificationFactor() {

    return heatMapModificationFactor;
  }

  /**
   * @param distance is the unit used to know how far to travel inbetween
   * @return the direction for the AI to go.
   */
  public UnitVector getNextDirection(double distance) {

    /* We're going to implement a simple gradient descent algorithm here.  Gradient descent
    is a generalization of newton's method for finding extrema.  Instead of following the
    derivate down the slope, we follow its n-d equivalent, the gradient. */

    Point heroLocation = theGameState.getHero().getPosition();
    Point pawnLocation = thePawn.getPosition();
    double currentX = pawnLocation.getX();
    double currentY = pawnLocation.getY();
    double deltaX = pawnLocation.getX() - heroLocation.getX();
    double deltaY = pawnLocation.getY() - heroLocation.getY();
    double distanceToHero = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));

    // use a slightly wide derivative of heat to sample more values

    double delHX =
        (theHeatMap.getHeat(new Point(currentX + 1, currentY))
                - theHeatMap.getHeat(new Point((currentX - 1), currentY)))
            / 2;

    double delHY =
        (theHeatMap.getHeat(new Point(currentX, currentY + 1))
                - theHeatMap.getHeat(new Point(currentX, (currentY - 1))))
            / 2;

    // this is where the magic happens.  This calculates del d*h= h*del d + d*del h
    // where d = distance and h equals heat (and del is the nabla, not delete).

    double gradiantX =
        theHeatMap.getHeat(new Point(currentX, currentY)) * (deltaX / (distanceToHero))
            + (distanceToHero * delHX);

    double gradiantY =
        theHeatMap.getHeat(new Point(currentX, currentY)) * (deltaY / (distanceToHero))
            + (distanceToHero * delHY);

    UnitVector direction = new UnitVector(-gradiantX, -gradiantY);

    if (!thePawn.canGo(direction, distance)) { // this is unlikely to happen... I hope.
      boolean stuck = true;
      while (stuck) {
        direction =
            new UnitVector(
                2
                    * Math.PI
                    * (new Random().nextInt(360))
                    / 360.0); // if stuck go a random direction

        if (thePawn.canGo(direction, distance))
          stuck = false; // this could end up with a dog repeatedly
        // banging his head into a wall... that would be funny
      }
    }

    return direction;
  }
}
