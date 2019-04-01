/**
 * An immutable data type, representation of a line segments in the plane.
 * 
 * @author gabrielrodriguezj
 *
 */
public class LineSegment {
	/**
	 * First point.
	 */
	private final Point p;

	/**
	 * Second point.
	 */
	private final Point q;

	/**
	 * Constructs the line segment between points p and q.
	 * 
	 * @param p First point
	 * @param q Second point
	 * @throws NullPointerException if either <tt>p</tt> or <tt>q</tt> is
	 *                              <tt>null</tt>
	 */
	public LineSegment(Point p, Point q) {
		if (p == null || q == null) {
			throw new NullPointerException("argument is null");
		}
		this.p = p;
		this.q = q;
	}

	/**
	 * Draws this line segment.
	 */
	public void draw() {
		p.drawTo(q);
	}

	/**
	 * String representation of the line segment.
	 */
	public String toString() {
		return p + " -> " + q;
	}
	
	/**
	 * Throws an exception if called. The hashCode() method is not supported because
	 * hashing has not yet been introduced in this course. Moreover, hashing does
	 * not typically lead to good *worst-case* performance guarantees, as required
	 * on this assignment.
	 *
	 * @throws UnsupportedOperationException if called
	 */
	public int hashCode() {
		throw new UnsupportedOperationException();
	}
}
