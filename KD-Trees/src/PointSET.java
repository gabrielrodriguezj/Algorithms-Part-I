
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;

public class PointSET {

	private TreeSet<Point2D> set;

	/**
	 * Default constructor. Construct an empty set of points.
	 */
	public PointSET() {
		set = new TreeSet<Point2D>();
	}

	/**
	 * Determine is the set is empty.
	 * 
	 * @return True if the set is empty, false in otherwise
	 */
	public boolean isEmpty() {
		return set.isEmpty();
	}

	/**
	 * Returns the number of points in the set.
	 * 
	 * @return number of points in the set
	 */
	public int size() {
		return set.size();
	}

	/**
	 * Add the point to the set (if it is not already in the set).
	 * 
	 * @param p point to be added.
	 */
	public void insert(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}

		if (!set.contains(p)) {
			set.add(p);
		}
	}

	/**
	 * Determine if the provided point is in the set.
	 * 
	 * @param p point to check if it already exists in the set
	 * @return true if the provided point exists in the set, false in otherwise
	 */
	public boolean contains(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}

		return set.contains(p);
	}

	/**
	 * Draw all points to standard draw
	 */
	public void draw() {
		Iterator<Point2D> iter = set.iterator();
		while (iter.hasNext()) {
			Point2D point = iter.next();
			point.draw();
		}
	}

	/**
	 * Return all points that are inside the rectangle (or on the boundary)
	 * 
	 * @param rect provided rectangle
	 * @return list of points inside the provided rectangle
	 */
	public Iterable<Point2D> range(RectHV rect) {
		Iterator<Point2D> iter = set.iterator();
		ArrayList<Point2D> points = new ArrayList<Point2D>();

		while (iter.hasNext()) {
			Point2D point = iter.next();
			if (rect.contains(point)) {
				points.add(point);
			}
		}

		return points;
	}

	//
	/**
	 * Return the nearest neighbor in the set to provided point.
	 * 
	 * @param p provided point
	 * @return nearest point to provided point, null if the set is empty
	 */
	public Point2D nearest(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}

		double distance = Double.MAX_VALUE;
		Point2D nearest = null;

		Iterator<Point2D> iter = set.iterator();
		while (iter.hasNext()) {
			Point2D point = iter.next();
			Double auxDistance = point.distanceTo(p);
			if (auxDistance < distance) {
				distance = auxDistance;
				nearest = point;
			}
		}

		return nearest;
	}

	/**
	 * Unit testing of the methods (optional)
	 * 
	 * @param args arguments for testing
	 */
	public static void main(String[] args) {
		System.out.println(args[0]);
		In in = new In(args[0]);
		double[] n = in.readAllDoubles();

		PointSET ps = new PointSET();
		for (int i = 0; i < n.length / 2; i++) {
			Point2D p = new Point2D(n[i*2], n[i*2 + 1]);
			System.out.print(p);
			
			ps.insert(p);
		}
		
		ps.draw();
	}
}