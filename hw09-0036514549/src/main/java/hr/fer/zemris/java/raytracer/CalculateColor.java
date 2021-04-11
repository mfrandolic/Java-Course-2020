package hr.fer.zemris.java.raytracer;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.Scene;

/**
 * Implementation of {@link RecursiveAction} that represents a job for calculating
 * the color of pixels in portion of the screen, from yMin to yMax in full width.
 * 
 * @author Matija Frandolić
 */
public class CalculateColor extends RecursiveAction {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Position of the observer in the scene.
	 */
	private Point3D eye;
	
	/**
	 * Horizontal size of the screen.
	 */
	private double horizontal;
	
	/**
	 * Vertical size of the screen.
	 */
	private double vertical;
	
	/**
	 * Width of the screen in pixels.
	 */
	private int width;
	
	/**
	 * Height of the screen in pixels.
	 */
	private int height;
	
	/**
	 * Minimum y-coordinate that is calculated.
	 */
	private int yMin;
	
	/**
	 * Maximum y-coordinate that is calculated.
	 */
	private int yMax;
	
	/**
	 * Normalized vector that determines the orientation of the x-axis.
	 */
	private Point3D xAxis;
	
	/**
	 * Normalized vector that determines the orientation of the y-axis.
	 */
	private Point3D yAxis;
	
	/**
	 * Point (0, 0) that is positioned in the top left corner of the screen.
	 */
	private Point3D screenCorner;
	
	/**
	 * Scene that contains graphical objects and light sources.
	 */
	private Scene scene;
	
	/**
	 * Array in which the calculated red components of the pixels are placed.
	 */
	private short[] red;
	
	/**
	 * Array in which the calculated green components of the pixels are placed.
	 */
	private short[] green;
	
	/**
	 * Array in which the calculated blue components of the pixels are placed.
	 */
	private short[] blue;
	
	/**
	 * Flag used to indicate that computation should be cancelled 
	 * (when set to {@code true}).
	 */
	private AtomicBoolean cancel;
	
	/**
	 * Default treshold after which to compute directly.
	 */
	private static final int TRESHOLD = 16;
	
	/**
	 * Constructs a new {@code CalculateColor} from the given arguments.
	 * 
	 * @param eye          position of the observer in the scene
	 * @param horizontal   horizontal size of the screen
	 * @param vertical     vertical size of the screen
	 * @param width        width of the screen in pixels
	 * @param height       height of the screen in pixels
	 * @param yMin         minimum y-coordinate that is calculated
	 * @param yMax         maximum y-coordinate that is calculated
	 * @param xAxis        normalized vector that determines the orientation of the x-axis
	 * @param yAxis        normalized vector that determines the orientation of the y-axis
	 * @param screenCorner point (0, 0) that is positioned in the top left corner of the screen
	 * @param scene        scene that contains graphical objects and light sources
	 * @param red          array in which the calculated red components of the pixels are placed
	 * @param green        array in which the calculated green components of the pixels are placed
	 * @param blue         array in which the calculated blue components of the pixels are placed
	 * @param cancel       flag used to indicate that computation should be cancelled 
	 *                     (when set to {@code true})
	 */
	public CalculateColor(Point3D eye, double horizontal, double vertical, int width, int height, 
			int yMin, int yMax, Point3D xAxis, Point3D yAxis, Point3D screenCorner, Scene scene, 
			short[] red, short[] green, short[] blue, AtomicBoolean cancel) {
		
		this.eye = eye;
		this.horizontal = horizontal;
		this.vertical = vertical;
		this.width = width;
		this.height = height;
		this.yMin = yMin;
		this.yMax = yMax;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.screenCorner = screenCorner;
		this.scene = scene;
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.cancel = cancel;
	}

	/**
	 * Calculates the color of pixels in portion of the screen, from yMin to yMax in full width.
	 */
	@Override
	protected void compute() {
		if (yMax - yMin + 1 <= TRESHOLD) {
			computeDirect();
			return;
		}
		invokeAll(
			new CalculateColor(eye, horizontal, vertical, width, height, yMin, 
					yMin + (yMax - yMin) / 2, xAxis, yAxis, screenCorner, scene,
					red, green, blue, cancel),
			new CalculateColor(eye, horizontal, vertical, width, height, 
					yMin + (yMax - yMin) / 2 + 1, yMax, xAxis, yAxis, screenCorner, 
					scene, red, green, blue, cancel)
		);
	}
	
	/**
	 * Directly calculates the color of pixels in portion of the screen.
	 */
	private void computeDirect() {
		short[] rgb = new short[3];
		int offset = width * yMin;
		
		for (int y = yMin; y <= yMax; y++) {
			for (int x = 0; x <= width - 1; x++) {
				if (cancel.get()) {
					return;
				}
				
				Point3D screenPoint = 
					screenCorner.add(xAxis.scalarMultiply(
				                          (double) x / (width - 1) * horizontal))
						        .modifyAdd(yAxis.scalarMultiply(
				                          (double) -y / (height - 1) * vertical));
				
				Ray ray = Ray.fromPoints(eye, screenPoint);
				
				Util.tracer(scene, ray, rgb);

				red[offset] = rgb[0] > 255 ? 255 : rgb[0];
				green[offset] = rgb[1] > 255 ? 255 : rgb[1];
				blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
				
				offset++;
			}
		}
	}
	
}
