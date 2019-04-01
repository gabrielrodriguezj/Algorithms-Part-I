import java.util.Arrays;

/**
 * Class that examines 4 points at a time and checks whether they all lie on the
 * same line segment, returning all such line segments. To check whether the 4
 * points p, q, r, and s are collinear, check whether the three slopes between p
 * and q, between p and r, and between p and s are all equal.
 * 
 * The method segments() should include each line segment containing 4 points
 * exactly once. If 4 points appear on a line segment in the order p->q->r->s,
 * then you should include either the line segment p->s or s->p (but not both)
 * and you should not include subsegments such as p->r or q->r.
 * 
 * **For simplicity, we will not supply any input to BruteCollinearPoints that
 * has 5 or more collinear points.
 * 
 * @author gabrielrodriguezj
 *
 */
public class BruteCollinearPoints {

	/**
	 * Line segments formed by 4 points.
	 */
	private final LineSegment[] segments;

	/**
	 * Finds all line segments containing 4 points.
	 * 
	 * @param points Array of points
	 */
	public BruteCollinearPoints(Point[] points) {

		int NUMBER_POINTS_SEGMENT = 4;

		// Check if the argument is null
		if (points == null)
			throw new IllegalArgumentException("The points array must not be null");

		// Check for null points
		checkForNullPoints(points);

		// Clone the array argument for not modify the original
		Point[] localPoints = points.clone();

		// Sort the points. Its important do it!!!!!! This allow avoid reduce
		// the total of comparisons.
		Arrays.sort(localPoints);

		// Check for repeated points
		checkForRepeatedPoints(localPoints);

		// Initialize the local segment array
		LineSegment[] localSegments = new LineSegment[0];

		// Create the line segments; Sort the points allows this computation be
		// easier
		Point pi, pj, pk, pl = null;
		boolean segmentedFormed = false;
		for (int i = 0; i < localPoints.length - NUMBER_POINTS_SEGMENT + 1; i++) {
			pi = localPoints[i];
			for (int j = i + 1; j < localPoints.length - NUMBER_POINTS_SEGMENT + 2; j++) {
				pj = localPoints[j];
				for (int k = j + 1; k < localPoints.length - NUMBER_POINTS_SEGMENT + 3; k++) {
					pk = localPoints[k];
					for (int l = k + 1; l < localPoints.length - NUMBER_POINTS_SEGMENT + 4; l++) {
						pl = localPoints[l];
						double slope1 = pi.slopeTo(pj);
						double slope2 = pj.slopeTo(pk);
						double slope3 = pk.slopeTo(pl);

						// Avoiding floating point comparison
						int a = Double.compare(slope1, slope2);
						int b = Double.compare(slope2, slope3);
						if (a == 0 && b == 0) {
							segmentedFormed = true;
							break;
						}
					}

					if (segmentedFormed) {
						break;
					}
				}

				if (segmentedFormed) {
					LineSegment ls = new LineSegment(pi, pl);
					LineSegment[] tempSegments = new LineSegment[localSegments.length + 1];

					// Copy the new line segment into the segments array
					for (int k = 0; k < localSegments.length; k++) {
						tempSegments[k] = localSegments[k];
					}
					tempSegments[localSegments.length] = ls;
					localSegments = tempSegments;

					segmentedFormed = false;
				}
			}
		}
		// Segments detected
		segments = localSegments;
	}

	/**
	 * Return the number of line segments.
	 * 
	 * @return number of line segments
	 */
	public int numberOfSegments() {
		return segments.length;
	}

	/**
	 * Return the line segments.
	 * 
	 * @return line segments;
	 */
	public LineSegment[] segments() {
		return segments.clone();
	}

	/**
	 * Method for check if a point is repeated; the array must be sorted.
	 * 
	 * @param points Points for check the segments.
	 */
	private void checkForRepeatedPoints(Point[] points) {
		for (int i = 0; i < points.length - 1; i++) {
			if (points[i].compareTo(points[i + 1]) == 0)
				throw new IllegalArgumentException("The points must not be repetead");
		}
	}

	/**
	 * Method for check if a point is repeated.
	 * 
	 * @param points Points for check the segments.
	 */
	private void checkForNullPoints(Point[] points) {
		for (int i = 0; i < points.length; i++) {
			if (points[i] == null)
				throw new IllegalArgumentException("The points must not be null");
		}
	}
}
