import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * This class is a client program that takes the name of an input file as a
 * command-line argument; read the input file ( in the following format: An
 * integer n, followed by n pairs of integers (x, y), each between 0 and
 * 32,767.), prints to standard output the line segments that your program
 * discovers, one per line; and draws to standard draw the line segments.
 * 
 * @author gabrielrodriguezj
 *
 */
public class CollinearPointsClient {

	/**
	 * Client void main method.
	 * 
	 * @param args Command-line arguments
	 */
	public static void main(String[] args) {
		// read the n points from a file
		In in = new In(args[0]);
		int n = in.readInt();
		Point[] points = new Point[n];
		for (int i = 0; i < n; i++) {
			int x = in.readInt();
			int y = in.readInt();
			points[i] = new Point(x, y);
		}

		// draw the points
		StdDraw.enableDoubleBuffering();
		StdDraw.setPenRadius(0.01);
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		for (Point p : points) {
			p.draw();
		}
		StdDraw.show();

		// print and draw the line segments
		// BruteCollinearPoints collinear = new BruteCollinearPoints(points);
		FastCollinearPoints collinear = new FastCollinearPoints(points);
		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			segment.draw();
		}
		StdDraw.show();
	}
}
