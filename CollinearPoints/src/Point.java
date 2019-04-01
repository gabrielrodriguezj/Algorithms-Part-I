import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

/**
 * Data type that represents a point in the plane.
 * 
 * @author gabrielrodriguezj
 * 
 */
public class Point implements Comparable<Point> {
	/**
	 * X-coordinate of the point.
	 */
	private final int x;

	/**
	 * Y-coordinate of the point
	 */
	private final int y;

	/**
	 * Constructs the point (x, y).
	 * 
	 * @param x X-coordinate of the point
	 * @param y Y-coordinate of the point
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Draws this point.
	 */
	public void draw() {
		StdDraw.point(x, y);
	}

	/**
	 * Draws the line segment from this point to that point.
	 * 
	 * @param that Point to draw to
	 */
	public void drawTo(Point that) {
		StdDraw.line(this.x, this.y, that.x, that.y);
	}

	/**
	 * Convert the point in a String representation.
	 */
	public String toString() {
		return "(" + this.x + " , " + this.y + ")";
	}

	/**
	 * Compare two points by y-coordinates, breaking ties by x-coordinates.
	 */
	public int compareTo(Point that) {
		if (this.y < that.y)
			return -1;
		if (this.y > that.y)
			return 1;
		if (this.x < that.x)
			return -1;
		if (this.x > that.x)
			return 1;
		return 0;
	}

	/**
	 * Calculate the slope between this point and that point. Treat the slope of a
	 * horizontal line segment as positive zero; treat the slope of a vertical line
	 * segment as positive infinity; treat the slope of a degenerate line segment
	 * (between a point and itself) as negative infinity.
	 * 
	 * @param that Point to calculate the slope with.
	 * @return Slope between this point and that point.
	 */
	public double slopeTo(Point that) {

		// Degenerate line segment
		if (this.compareTo(that) == 0) {
			return Double.NEGATIVE_INFINITY;
		}

		double dividend = that.y - this.y;
		double divisor = that.x - this.x;

		// Horizontal line segment
		if (dividend == 0) {
			return 0;
		}

		// Vertical line segment
		if (divisor == 0) {
			return Double.POSITIVE_INFINITY;
		}

		return dividend / divisor;
	}

	/**
	 * Compare two points by slopes they make with this point.
	 * 
	 * @return Comparator instance.
	 */
	public Comparator<Point> slopeOrder() {
		return new SlopeCompare();
	}

	/**
	 * Class for implementing the Point's comparator. Compare by the slopes formed
	 * by the points.
	 * 
	 * @author gabrielrodriguezj
	 *
	 */
	private class SlopeCompare implements Comparator<Point> {

		/**
		 * Implementation of the method for comparing the slopes of the Point p and q
		 * with respect to "this" Point.
		 */
		@Override
		public int compare(Point p, Point q) {
			Point current = new Point(x, y);
			double slopeWithP = current.slopeTo(p);
			double slopeWithQ = current.slopeTo(q);

			if (slopeWithP < slopeWithQ)
				return -1;
			if (slopeWithP > slopeWithQ)
				return 1;
			return 0;
		}

	}
}