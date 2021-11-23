package gameobjects.updatable;

import catgame.GameState;
import gameobjects.updatable.drawable.tile.Map;
import geometry.Point;

/**
 * This class implements a simple heatmap to improve the quality of the AI. Every Tile on the map is
 * given a heat value, and that value is used to make certain squares more ore less appealkng to the
 * AI. Currently the heat map has infinite memory, but that might change in future iterations. *
 */
public class HeatMap extends Updatable {

  private double[][] map;
  private final Map tileMap;
  private final int length;
  private final int height;
  private final double RELAXATION_CONSTANT =
      10.0; // how fast heat map returns to default, needs to be tuned.

  /**
   * HeatMaps should mirror the game map, so the only constructor provided, simply uses the gamemap
   * as a template to construct the heatmap. Initial values are determined from the gamemap. Every
   * tile has a default heat value (for instance a normal tile would be 1, and a tile by a wall
   * would be 1.5 )
   *
   * @param amap the Map to base the heatmap on *
   */
  public HeatMap(GameState aGameState) {

    super(aGameState);
    tileMap = theGameState.getMap();
    length = tileMap.getLength();
    height = tileMap.getHeight();
    map = new double[length][height];
  }

  /** Set's the heat on every square of the map */
  public void setHeat() {
    for (int i = 0; i < length; i++) {
      for (int j = 0; j < height; j++) {

        map[i][j] = tileMap.getDefaultHeat(new Point(i, j));
      }
    }
  }

  /**
   * Get the current value of the heat of the Tile at position x, y
   *
   * @param x the x coordinate of the tile
   * @param y the y coordinate of the tile
   * @return the current heat of the tile
   */
  public double getHeat(Point thePoint) {

    return map[(int) thePoint.getX()][(int) thePoint.getY()];
  }

  /**
   * Allows client classes to modify the heat of a tile position.
   *
   * @param factor how much to modify heat by. Must be greater than 0. Currently heat is modified
   *     multiplicativly since negative heats or zero heats will break the algorithm. This can
   *     change.
   * @param x x coordinate of the tile position to modify
   * @param y y coordinate of the tile position to modify
   */
  public void modifyHeat(double deltaT, double factor, Point position) {

    int x = (int) position.getX();
    int y = (int) position.getY();

    map[x][y] += map[x][y] * factor * deltaT; // use exponential growth
  }

  /**
   * This method is called in the main loop on all updatable things (Updatable is an interface, and
   * is implemented by most things in the game!) In this class it makes heat return to default
   * value, with an exponential decay with constant RELAXATION_CONSTANT. There are stability
   * problems with this, but I sort of like the random jitter introduced.
   *
   * @param deltaT change in time between updates
   */
  public void update(double deltaT) {

    for (int i = 0; i < length; i++) {
      for (int j = 0; j < height; j++) {

        // Mathematically this implements the differenital equation heat' = (heat - default)/Tau...
        // ie exponential decay
        double deltaHeat = map[i][j] - tileMap.getDefaultHeat(new Point(i, j));
        map[i][j] -= (deltaHeat * deltaT) / RELAXATION_CONSTANT;
      }
    }
  }
}
