package gameobjects.updatable.drawable.tile;

import geometry.Point;

/**
 * Creates a 2D array. This is the map for the Game
 *
 * @author Rylan Laplante
 */
public class Map {

  // Instances Variables
  private int length;
  private int height;
  private Tile[][] theMap;
  /**
   * This is the constructor that allows the variables to define themselves.
   *
   * @param length of the 2D array will be. This is dynamic & changes every level
   * @param height of the 2D array will be. This is dynamic & changes every level
   */
  public Map(int length, int height) {
    this.height = height;
    this.length = length;
    this.theMap = new Tile[length][height];
  }

  /**
   * This method allows length to define itself and then return this copy version of length.
   *
   * @return This returns the copy of the length.
   */
  public int getLength() {
    return length;
  }
  /**
   * This method allows height to define itself and then return this copy version of height.
   *
   * @return This returns the copy of the height.
   */
  public int getHeight() {
    return height;
  }

  /**
   * This method get the Tile and prompts to a specfic spot on theMap.
   *
   * @param x This is a specific location for the x plain.
   * @param y This is a specific location for the y plain.
   * @return get a Tile and prompts it to a specfic spot on theMap, well casting it into an int so
   *     it can be loaded properly.
   */
  Tile getTile(double x, double y) {

    if (x >= length || y >= height) return null; // if off the map returh null
    return theMap[(int) x][(int) y];
  }

  /**
   * This is the method to set the Tiles in the map.
   *
   * @param x This represents a certain x specific spot on the map.
   * @param y This represents a certain y specific spot on the map.
   * @param atile This is 1 specific Tile spot. This is used to be assigned to a (x,y) on the map.
   */
  public void setTile(int x, int y, Tile atile) {

    if (x >= length || y >= height) return; // if off the map do nothing
    theMap[x][y] = atile;
  }

  /**
   * Gets the default heat at a position
   *
   * @param position Where to get the default heat
   * @return the default heat
   */
  public double getDefaultHeat(Point position) {

    Tile tile = getTile(position.getX(), position.getY());
    if (tile == null) return 100; // if off the board stay away
    return tile.getDefaultHeat();
  }

  /**
   * Sets the default heat at a position.
   *
   * @param position Where to set the heat
   * @param defaultHeat The default heat at position
   */
  public void setDefaultHeat(Point position, double defaultHeat) {

    Tile tile = getTile(position.getX(), position.getY());
    tile.setDefaultHeat(defaultHeat);
  }

  /**
   * Process the hero entering a tile at a given position
   *
   * @param position The position the hero entered
   */
  public void heroEntered(Point position) {

    Tile tile = getTile(position.getX(), position.getY());
    if (tile == null) return; // if off the map, do nothing
    tile.heroEntered();
  }

  /**
   * Is the tile at a specific position a floor (for entry purposes)
   *
   * @param position Where to check if is floor
   * @return floor or not
   */
  public boolean isFloor(Point position) {

    Tile tile = getTile(position.getX(), position.getY());
    if (tile == null) return false; // if off the map, assume its a wall, not a floor
    return tile.isFloor();
  }

  /**
   * Is the tile at a specific position a floor (for entry purposes)
   *
   * @param position Where to check if is floor
   * @return floor or not
   */
  public boolean isWall(Point position) {

    Tile tile = getTile(position.getX(), position.getY());
    if (tile == null) return true; // if off the map, assume its a wall
    return tile.isWall();
  }
}
