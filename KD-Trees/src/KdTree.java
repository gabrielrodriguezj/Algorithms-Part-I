import java.util.ArrayList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
	private KdNode root;
	private int size;

	/**
	 * Default constructor. Construct an empty set of points.
	 */
	public KdTree() {
		this.root = null;
		this.size = 0;
	}
	
	/**
	 * Determine is the set is empty.
	 * 
	 * @return True if the set is empty, false in otherwise
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}

	/**
	 * Returns the number of points in the set.
	 * 
	 * @return number of points in the set
	 */
	public int size() {
		return this.size;
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
		
		if (contains(p)) {
			return;
		}
		
		// insert the first element in the tree
		if (this.root == null) {
			KdNode node = new KdNode();
			node.vertical = true;
			node.point = p;
			this.root = node;
			this.size += 1;
		}
		else {
			insert(p, this.root);
		}
	}
	
	private void insert(Point2D p, KdNode currentNode) {
		
		if (currentNode.vertical) {
			if (p.x() <= currentNode.point.x()) {
				
				if (currentNode.left == null) {
					KdNode node = new KdNode();
					node.vertical = !currentNode.vertical;
					node.point = p;
					currentNode.left = node;
					this.size += 1;
				}
				else {
					insert(p, currentNode.left);
				}
				
			} else {
				
				if (currentNode.right == null) {
					KdNode node = new KdNode();
					node.vertical = !currentNode.vertical;
					node.point = p;
					currentNode.right = node;
					this.size += 1;
				}
				else {
					insert(p, currentNode.right);
				}
			}
		} else {
			if (p.y() <= currentNode.point.y()) {
				
				if (currentNode.left == null) {
					KdNode node = new KdNode();
					node.vertical = !currentNode.vertical;
					node.point = p;
					currentNode.left = node;
					this.size += 1;
				}
				else {
					insert(p, currentNode.left);
				}
				
			} else {
				if (currentNode.right == null) {
					KdNode node = new KdNode();
					node.vertical = !currentNode.vertical;
					node.point = p;
					currentNode.right = node;
					this.size += 1;
				}
				else {
					insert(p, currentNode.right);
				}
			}
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
		
		if (this.root == null) {
			return false;
		}
		
		return search(p, this.root);
	}
	
	private boolean search(Point2D p, KdNode currentNode) {
		
		if (currentNode == null) {
			return false;
		}
		
		if (currentNode.point.compareTo(p) == 0) {
			return true;
		}
		
		if (currentNode.left == null  && currentNode.right == null) {
			return false;
		}
		
		boolean findLeft = false;
		boolean findRight = false;
		
		if (currentNode.vertical) {
			
			if (p.x() <= currentNode.point.x()) {
				
				findLeft = search(p, currentNode.left);
			}
			else {
				findRight = search(p, currentNode.right);
			}
			
		}
		else {
			if (p.y() <= currentNode.point.y()) {
				
				findLeft = search(p, currentNode.left);
			}
			else {
				findRight = search(p, currentNode.right);
			}
		}
		
		return findLeft || findRight;
	}

	/**
	 * Draw all points to standard draw
	 */
	public void draw() {
		draw(this.root, null);
	}
	
	private void draw(KdNode currentNode, KdNode parent) {
		if (currentNode == null) {
			return;
		}
		
		// draw the tree's root
		if (parent == null) {
			StdDraw.setPenRadius();
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.line(currentNode.point.x(), 0, currentNode.point.x(), 1);
		}
		else {
			if (currentNode.vertical) {
				double y1 = 0;
				double y2 = 0;
				
				if (currentNode.point.y() <= parent.point.y()) {
					y1 = parent.point.y();
					y2 = 0;
				}
				else {
					y1 = parent.point.y();
					y2 = 1;
				}

				StdDraw.setPenRadius();
				StdDraw.setPenColor(StdDraw.RED);
				StdDraw.line(currentNode.point.x(), y1, currentNode.point.x(), y2);
			}
			else {
				double x1 = 0;
				double x2 = 0;
				
				if (currentNode.point.x() <= parent.point.x()) {
					x1 = parent.point.x();
					x2 = 0;
				}
				else {
					x1 = parent.point.x();
					x2 = 1;
				}

				StdDraw.setPenRadius();
				StdDraw.setPenColor(StdDraw.BLUE);
				StdDraw.line(x1, currentNode.point.y(), x2, currentNode.point.y());
			}
		}
		
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.01);
		currentNode.point.draw();
		
		draw(currentNode.left, currentNode);
		draw(currentNode.right, currentNode);
	}

	/**
	 * Return all points that are inside the rectangle (or on the boundary)
	 * 
	 * @param rect provided rectangle
	 * @return list of points inside the provided rectangle
	 */
	public Iterable<Point2D> range(RectHV rect) {
		ArrayList<Point2D> points = new ArrayList<Point2D>();
		
		if (this.root != null) {
			range(rect, this.root, points);
		}
		
		return points;
	}
	
	private void range(RectHV rect, KdNode currentNode, ArrayList<Point2D> points) {
		
		if (currentNode == null) {
			return;
		}
		
		if (rect.contains(currentNode.point)) {
			points.add(currentNode.point);
		}
		
		if (currentNode.vertical) {
			if (rect.xmin() <= currentNode.point.x()) {
				range(rect, currentNode.left, points);
			}
			
			if (rect.xmax() >= currentNode.point.x()) {
				range(rect, currentNode.right, points);
			}
		}
		else {
			if (rect.ymin() <= currentNode.point.y()) {
				range(rect, currentNode.left, points);
			}
			
			if (rect.ymax() >= currentNode.point.y()) {
				range(rect, currentNode.right, points);
			}
		}	
	}
	
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
		
		if (this.root == null) {
			return null;
		}

		Point2D nearest = nearest(p, this.root);
		
		return nearest;
	}
	
	private Point2D nearest(Point2D p, KdNode currentNode) {
		
		Point2D nearest = null;
		Point2D nearestL = null;
		Point2D nearestR = null;
		
		if (currentNode.left != null) {
			nearestL = nearest(p, currentNode.left);
		}
		
		if (currentNode.right != null) {
			nearestR = nearest(p, currentNode.right);
		}
		
		if (nearestL == null && nearestR == null) {
			nearest = currentNode.point;
		}
		else if (nearestL == null && nearestR != null) {
			nearest = nearestR;
		}
		else if (nearestL != null && nearestR == null) {
			nearest = nearestL;
		}
		else {
			if (nearestL.distanceSquaredTo(p) <= nearestR.distanceSquaredTo(p)) {
				nearest = nearestL;
			}
			else {
				nearest = nearestR;
			}
		}
		
		if (nearest.distanceSquaredTo(p) > currentNode.point.distanceSquaredTo(p)) {
			nearest = currentNode.point;
		}
		
		return nearest;
	}

	/**
	 * Unit testing of the methods (optional)
	 * 
	 * @param args arguments for testing
	 */
	public static void main(String[] args) {

		In in = new In(args[0]);
		double[] n = in.readAllDoubles();
		
		KdTree kdt = new KdTree();
		
		for (int i = 0; i < n.length / 2; i++) {
			Point2D p = new Point2D(n[i*2], n[i*2 + 1]);
			// System.out.println(p);
			
			kdt.insert(p);
		}
		
		kdt.draw();
		
		Point2D p = new Point2D(0.432, 0.422);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.01);
		p.draw();
		StdDraw.setPenColor();
		StdDraw.setPenRadius();
		Point2D nearest = kdt.nearest(p);
		System.out.println(nearest);
		//(0.499, 0.208)
		
	}
	
	private class KdNode {
		
		private KdNode left;
		private KdNode right;
		private boolean vertical;
		private Point2D point;
		
		private KdNode() {
			this.left = null;
			this.right = null;
			this.vertical = false;
			this.point = null;
		}
	}
}
