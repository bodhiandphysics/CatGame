package gameobjects.updatable.controller;

import catgame.GameState;
import gameobjects.updatable.drawable.collidable.pawn.Pawn;
import geometry.UnitVector;
import ui.KeyboardHandler;

/**
 * Class provides keyboard controls for the hero. his class provides for keyboard control of the
 * Hero. The design should be easily extended to a event based model. We discovered a BUG. Can't
 * handle a hero bneing moved by another source besides the controller!
 *
 * @author
 */
public class HeroController extends PawnController {

  private double heatMapModificationFactor = .33;
  private UnitVector nextDirection = null;

  /**
   * Used to get the gamestae & pawn into this class.
   *
   * @param aGameState The GameState of the current game
   * @param amap the map of the current game
   * @param startTile Where the pawn begins on the map
   * @param aPawn The hero that is controlled
   */
  public HeroController(GameState aGameState, Pawn aPawn) {

    super(aGameState, aPawn);
  }

  /**
   * Changes the state of the controller when called, having the controller provide a differen next
   * tile. If the new tile is inacessable to the hero, than there is no change. Currently only works
   * with the 4 cardinal directions.
   *
   * @param inputDirection what direction the hero should go in
   */
  public void handleInput(KeyboardHandler.Direction inputDirection) {

    if (inputDirection == KeyboardHandler.Direction.UP) nextDirection = new UnitVector(0, -1);
    if (inputDirection == KeyboardHandler.Direction.DOWN) nextDirection = new UnitVector(0, 1);
    if (inputDirection == KeyboardHandler.Direction.RIGHT) nextDirection = new UnitVector(1, 0);
    if (inputDirection == KeyboardHandler.Direction.LEFT) nextDirection = new UnitVector(-1, 0);
    if (inputDirection == KeyboardHandler.Direction.NOMOVE) nextDirection = null;
  }

  /**
   * Get the modified factor for the heatMap.
   *
   * @return the modified heat map factor.
   */
  @Override
  double getHeatMapModificationFactor() {

    return heatMapModificationFactor;
  }

  /**
   * This uses UnitVectors to move the Hero.
   *
   * @param distance this is the distance that the hero moves
   * @return The UnitVector for nextDirection.
   */
  public UnitVector getNextDirection(double distance) {
    if (nextDirection == null) return nextDirection;
    if (thePawn.canGo(nextDirection, distance)) {
      return new UnitVector(nextDirection);
    } else return null;
  }
}
