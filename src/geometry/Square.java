package geometry;
/**
 * Creates a Square to outline the Pawns within the game. 
 * Checks for collision of the Pawns & makes sure that the proper interaction occurs. 
 * @author rylanL
 */
public class Square {
  private final double sideLength;
  private final Point bottomLeft;
  private final Point bottomRight;
  private final Point topLeft;
  private final Point topRight;
/**
 * 
 * @param aTopLeft This is the Topleft of the square. From here it makes the 4 sides. 
 * @param theSideLength How long 1 side of the sqaure is. 
 */
  public Square(Point aTopLeft, double theSideLength) {

    
    sideLength = theSideLength;
    topLeft = new Point(aTopLeft);
    topRight = new Point(aTopLeft.getX() + 1, aTopLeft.getY());
    bottomRight = new Point(aTopLeft.getX() + 1, aTopLeft.getY() + 1);
    bottomLeft = new Point(aTopLeft.getX(), aTopLeft.getY() + 1); //Uses unit vectors to create the sqaure
  }
/**
 * This Creates new TopLeft & SideLengths
 * @param theSquare varaible that is used to set the type Square to it can be used. 
 */
  public Square(Square theSquare) {

    this(theSquare.getTopLeft(), theSquare.getSideLength());
  }
/**
 * This gets the Sidelength of the square 
 * @return 1 sideLength for the sqaure around the character. 
 */
  public double getSideLength() {
    return sideLength;
  }
/**
 * Gets the bottom Left. 
 * @return the x,y that is the bottom left
 */
  public Point getBottomLeft() {

    return bottomLeft;
  }
/**
 * gets the bottom Right.
 * @returnthe x,y that is the bottom right
 */
  public Point getBottomRight() {

    return bottomRight;
  }
/**
 * gets the Top Left.
 * @return x,y that is the top left
 */
  public Point getTopLeft() {

    return topLeft;
  }
/**
 * gets the Top Right
 * @return x,y that is the top left
 */
  public Point getTopRight() {

    return topRight;
  }
/**
 * Checks if intersects occurs between 2 squares. 2 square instersect iff one of their corners is inside the other. 
 * @param otherSquare this is the second square that it using to check with. 
 * @return a boolean whether or not the squares collide. 
 */
  public boolean intersects(Square otherSquare) {

    if (topLeft.inside(otherSquare)
      || topRight.inside(otherSquare)
      || bottomLeft.inside(otherSquare)
      || bottomRight.inside(otherSquare)) return true;
    else return false;
  }
/**
 * This is how the Square move in the game. 
 * It creates a new Square that has new x,y
 * @param direction is the way that the new square is going. 
 * @param distance is how far the new square will be compare to the previous one. 
 * @return The new Sqaure shows 
 */
  public Square translate(UnitVector direction, double distance) {

    return new Square(topLeft.translate(direction, distance), sideLength);
  }
}
