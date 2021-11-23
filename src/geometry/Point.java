package geometry;
/**
 * Sets a specific point within the game. 
 * @author rylanl
 *
 */
public class Point {

  private final double xCoord;
  private final double yCoord;
/**
 * use's x,y to create the Point 
 * @param x This is a specific location for the x plain.
 * @param y This is a specific location for the y plain.
 */
  public Point(double x, double y) {

    xCoord = x;
    yCoord = y;
  }
/**
 * gets the X, Y points & creates the point with those. 
 * @param point is an specific x,y on the map
 */
  public Point(Point point) {

    this(point.getX(), point.getY());
  }
/**
 * Gets an XCoord needed to create the point
 * @return that XCoord on the map
 */
  public double getX() {

    return xCoord;
  }
/*
 * Gets an YCoord needed to create the point
 * @return that YCoord on the map
 */
  public double getY() {

    return yCoord;
  }
/**
 * The character themselves r Squares within the game. So this checks if the Point si inside of that. 
 * @param aSquare parameter for the sqaure that is the character. 
 * @return A boolean saying whether the Point is within the Square. 
 */
  public boolean inside(Square aSquare) {

    Point bottomLeft = aSquare.getBottomLeft();
    Point topLeft = aSquare.getTopLeft();
    Point bottomRight = aSquare.getBottomRight();

    if (xCoord <= bottomRight.getX()
        && xCoord >= bottomLeft.getX()
        && yCoord >= topLeft.getY()
        && yCoord <= bottomRight.getY()) return true;
    else return false;
  }
/**
 * This uses the Point to translate movements. 
 * This creates a new Point on the map for the Character to go to. 
 * @param direction This is from the UnitVector Class, & sets the dircetion the point will be made 
 * @param distance This is how far the point will be away from the previous one. 
 * @return A new Point with new x,y coordinates. 
 */
  public Point translate(UnitVector direction, double distance) {

    double newXCoord = xCoord + distance * direction.xHat();
    double newYcoord = yCoord + distance * direction.yHat();

    return new Point(newXCoord, newYcoord);
  }
}
