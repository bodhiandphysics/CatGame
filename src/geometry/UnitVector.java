package geometry;
/**
 * This creates a UnitVector. This is our base Unit that uses gradients for everything within the game. 
 * 
 * @author rylanl
 *
 */
public class UnitVector {

  private final double xHat; //
  private final double yHat; 
  /**
   * This creates the 
   * @param x This is a specific location for the x plain.
   * @param y This is a specific location for the y plain.
   */
  public UnitVector(double x, double y) {

    double norm = Math.sqrt(x * x + y * y);
    if (norm <= .00001) { // if norm is 0, direction is arbitrary so return (1,0)

      xHat = 1.0;
      yHat = 0;
    } else {

      xHat = x / norm;
      yHat = y / norm;
    }
  }
  /**
   * This creates the the ability for the direction to be anywhere within a circle.
   * @param direction now the user can go in any direction. 
   */
  public UnitVector(double direction) {

    xHat = Math.cos(direction);

    yHat = Math.sin(direction);
  }
  /**
   * This assigns the direction in which the xHat is going. 
   * @param direction 
   */
  public UnitVector(UnitVector direction) {

    xHat = direction.xHat();
    yHat = direction.yHat();
  }
  /**
   * This is the variable that we use to be able to creat the gradient 
   * @return xHat for the creatation of gradient. 
   */
  public double xHat() {

    return xHat;
  }
  /**
   * This is the variable that we use to be able ot create the gradient 
   * @return yHat for the creatation of gradient. 
   */
  public double yHat() {

    return yHat;
  }
}
