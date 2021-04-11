package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.IRayTracerAnimator;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Program that demonstrates rendering of a 3D scene by using ray-casting algorithm
 * whose calculations are parallelized. The scene is redrawn periodically in order
 * to create an animation of user rotating around the scene.
 * 
 * @author Matija Frandolić
 */
public class RayCasterParallel2 {

	/**
	 * Main method of the program.
	 * 
	 * @param args command line arguments (not used)
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(
			getIRayTracerProducer(), getIRayTracerAnimator(), 30, 30
		);
	}
	
	/**
	 * Returns an object which provides temporal information to GUI showing the scene.
	 * 
	 * @return an object which provides temporal information to GUI showing the scene
	 */
	private static IRayTracerAnimator getIRayTracerAnimator() {
		return new IRayTracerAnimator() {
			long time;
			
			@Override
			public void update(long deltaTime) {
				time += deltaTime;
			}
			
			@Override
			public Point3D getViewUp() { // fixed in time
				return new Point3D(0, 0, 10);
			}
			
			@Override
			public Point3D getView() { // fixed in time
				return new Point3D(-2, 0, -0.5);
			}
			
			@Override
			public long getTargetTimeFrameDuration() {
				return 150; // redraw scene each 150 milliseconds
			}

			@Override
			public Point3D getEye() { // changes in time
				double t = (double) time / 10000 * 2 * Math.PI;
				double t2 = (double) time / 5000 * 2 * Math.PI;
				double x = 50 * Math.cos(t);
				double y = 50 * Math.sin(t);
				double z = 30 * Math.sin(t2);
				return new Point3D(x, y, z);
			}
		};
	}
	
	/**
	 * Returns an object which is capable to create scene snapshots by using 
	 * ray-tracing technique.
	 * 
	 * @return an object which is capable to create scene snapshots by using
	 *         ray-tracing technique
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
						double horizontal, double vertical, int width, int height, 
						long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {
				
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];
				
				Point3D zAxis = view.sub(eye).modifyNormalize();
				Point3D yAxis = viewUp.normalize().modifySub(zAxis.scalarMultiply(
						        zAxis.scalarProduct(viewUp.normalize()))).modifyNormalize();
				Point3D xAxis = zAxis.vectorProduct(yAxis).modifyNormalize(); 
				
				Point3D screenCorner = view.add(xAxis.scalarMultiply(-horizontal / 2))
						                   .modifyAdd(yAxis.scalarMultiply(vertical / 2));
				
				Scene scene = RayTracerViewer.createPredefinedScene2();
				
				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new CalculateColor(eye, horizontal, vertical, width, height, 0, 
					height - 1, xAxis, yAxis, screenCorner, scene, red, green, blue, cancel
				));
				pool.shutdown();
				
				observer.acceptResult(red, green, blue, requestNo);
			}
		};
	}
						
}
