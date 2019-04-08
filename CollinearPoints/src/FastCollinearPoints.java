import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class that solve the same problem that the BruteCollinearPoints class solve,
 * but is a faster and sorting-based solution. Given a point p, the following
 * method determines whether p participates in a set of 4 or more collinear
 * points.
 * 
 * @author gabrielrodriguezj
 *
 */
public class FastCollinearPoints {

	/**
	 * Line segments formed by 4 points.
	 */
	private final ArrayList<LineSegment> segments;

	/**
	 * Auxiliary list for save the initial and final points of a line segment
	 */
	private final ArrayList<Point> pointsLineSegment;

	/**
	 * Finds all line segments containing 4 or more points.
	 * 
	 * This is a faster, sorting-based solution. Remarkably, it is possible to solve
	 * the problem much faster than the brute-force solution described. Given a
	 * point p, the following method determines whether p participates in a set of 4
	 * or more collinear points: 1.-Think of p as the origin. 2.-For each other
	 * point q, determine the slope it makes with p. 3.-Sort the points according to
	 * the slopes they makes with p. 4.-Check if any 3 (or more) adjacent points in
	 * the sorted order have equal slopes with respect to p. If so, these points,
	 * together with p, are collinear. Applying this method for each of the n points
	 * in turn yields an efficient algorithm to the problem. The algorithm solves
	 * the problem because points that have equal slopes with respect to p are
	 * collinear, and sorting brings such points together. The algorithm is fast
	 * because the bottleneck operation is sorting.
	 * 
	 * @param points Array of points
	 */
	public FastCollinearPoints(Point[] points) {

		// Check if the argument is null
		if (points == null)
			throw new IllegalArgumentException("The points array must not be null");

		// Check for null points
		checkForNullPoints(points);

		// Array for handle points
		Point[] localPoints = points.clone();

		// Check for null or repeated point
		Arrays.sort(localPoints);
		checkForRepeatedPoints(localPoints);

		// Initialize the list of segments
		segments = new ArrayList<>();

		// Initialize the list of points for determinate line segments repeated
		pointsLineSegment = new ArrayList<>();

		for (int i = 0; i < points.length; i++) {

			/// Make a copy of the array, just sort the copy array, not the original
			localPoints = points.clone();

			// Sort the array elements by the slope respect to the current element
			// (points[i])
			Arrays.sort(localPoints, points[i].slopeOrder());

			// Initial conditions
			double slope = points[i].slopeTo(localPoints[0]);
			int consecutivePoints = 1;
			int beginSubArray = 0;

			for (int j = 1; j < localPoints.length; j++) {

				// Slope of the actual point and the j element in the sorted array
				double slopeTemp = points[i].slopeTo(localPoints[j]);

				int comparison = Double.compare(slope, slopeTemp);

				if (comparison == 0) {
					consecutivePoints++;

					// Condition if the line segments ends in the last element of the array
					if (j == localPoints.length - 1 && consecutivePoints >= 3) {

						// Method for create and evaluate the segment
						generateSegment(consecutivePoints, points[i], localPoints, beginSubArray);

						// Reinitialize the conditions for keep creating line segments
						slope = slopeTemp;
						consecutivePoints = 1;
						beginSubArray = j;

					}
				} else if (consecutivePoints < 3) { // slopeTemp and slope are different
					slope = slopeTemp;
					consecutivePoints = 1;
					beginSubArray = j;
				} else { // consecutivePoints is 3 or greatest

					// Method for create and evaluate the segment
					generateSegment(consecutivePoints, points[i], localPoints, beginSubArray);

					// Reinitialize the conditions for continue creating line segments
					slope = slopeTemp;
					consecutivePoints = 1;
					beginSubArray = j;
				}
			}
		}
	}

	/**
	 * Method to create a segment if it not exist.
	 * 
	 * @param consecutivePoints Number of consecutive points
	 * @param activePoint       Point that is part of the line segment; is iterating
	 *                          over it
	 * @param points            Points ordered respect activePoint by the slope
	 * @param begin             index where begin the points to form the line
	 *                          segment
	 */
	private void generateSegment(int consecutivePoints, Point activePoint, Point[] points, int begin) {

		consecutivePoints++;

		// Create the array will contain the points of the line segment
		Point[] arraySegment = new Point[consecutivePoints];
		arraySegment[0] = activePoint;

		// Take the sub array
		for (int i = 1; i < consecutivePoints; i++) {
			arraySegment[i] = points[begin + i - 1];
		}

		// Sort the array by position
		Arrays.sort(arraySegment);

		// Take the initial and final point of the line segment
		Point p = arraySegment[0];
		Point q = arraySegment[consecutivePoints - 1];
		boolean lineSegmentRepeated = false;

		// Search for p and q repeated
		if (pointsLineSegment.contains(p)) {
			for (int i = 0; i < pointsLineSegment.size(); i += 2) {
				if (pointsLineSegment.get(i) == p && pointsLineSegment.get(i + 1) == q) {
					lineSegmentRepeated = true;
					break;
				}
			}
		}

		if (!lineSegmentRepeated) {
			// Create the line segment taking the first and last element of the sub array
			LineSegment ls = new LineSegment(arraySegment[0], arraySegment[consecutivePoints - 1]);
			segments.add(ls);

			// Save points that register the initial and end points of a line segment
			pointsLineSegment.add(p);
			pointsLineSegment.add(q);
		}
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

	/**
	 * Return the number of line segments.
	 * 
	 * @return number of line segments
	 */
	public int numberOfSegments() {
		return segments.size();
	}

	/**
	 * Return the line segments.
	 * 
	 * @return line segments;
	 */
	public LineSegment[] segments() {
		LineSegment[] arrayLS = new LineSegment[segments.size()];
		for (int i = 0; i < segments.size(); i++) {
			arrayLS[i] = segments.get(i);
		}

		return arrayLS;
	}
}
