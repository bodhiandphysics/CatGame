package gameobjects.updatable.controller;

import catgame.GameState;
import gameobjects.updatable.HeatMap;
import gameobjects.updatable.Updatable;
import gameobjects.updatable.drawable.collidable.pawn.Pawn;
import gameobjects.updatable.drawable.tile.Map;
import geometry.Point;
import geometry.UnitVector;

/**
 * Provides the basic interface of Pawn control, whether AI or keyboard. The controller needs to
 * updatable, so that it maintains its own state. The core routine gives the next tile that a Pawn
 * (in this case probably the enemy) should go to. The reason for this abstract super class is to
 * enable different contollerss for different pawns, and to enable me to easilly experiment with
 * different tyes of AI.
 */
public abstract class PawnController extends Updatable {

  protected final Map gameMap;
  protected Pawn thePawn;
  protected HeatMap theHeatMap;
  private final Point position;
  private double heatMapModificationFactor = 0;

  /**
   * Creates a pawn controller
   *
   * @param aGameState the gameState
   * @param aPawn the pawn to control
   */
  public PawnController(GameState aGameState, Pawn aPawn) {

    super(aGameState);
    gameMap = theGameState.getMap();
    theHeatMap = theGameState.getHeatMap();
    thePawn = aPawn;
    position = thePawn.getPosition();
  }

  /**
   * Gets a positon for the pawn to go to.
   *
   * @return a Point on the map.
   */
  public Point getPosition() {

    return position;
  }

  /**
   * AI maintains its own state of the world. This method is called in the main loop on all
   * updatable things (Updatable is an interface, and is implemented by most things in the game!) In
   * SimpleAI this is used to update the heatmap
   *
   * @param deltaT is the variable used to update the game
   */
  public void update(double deltaT) {

    theHeatMap.modifyHeat(deltaT, getHeatMapModificationFactor(), thePawn.getPosition());
  }

  /**
   * Provides the next tile that the Pawn should move to. The core routine of the controller.
   *
   * @return the Tile that the pawn should move to
   */
  public abstract UnitVector getNextDirection(double distance);

  double getHeatMapModificationFactor() {
    return 0;
  }
}
