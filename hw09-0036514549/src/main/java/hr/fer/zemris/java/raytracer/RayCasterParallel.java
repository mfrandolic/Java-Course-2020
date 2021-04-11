package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Program that demonstrates rendering of a 3D scene by using ray-casting algorithm
 * whose calculations are parallelized.
 * 
 * @author Matija Frandolić
 */
public class RayCasterParallel {

	/**
	 * Main method of the program.
	 * 
	 * @param args command line arguments (not used)
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), 
		new Point3D(10,0,0), 
		new Point3D(0,0,0), 
		new Point3D(0,0,10), 
		20, 20);
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
				
				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];
				
				Point3D zAxis = view.sub(eye).modifyNormalize();
				Point3D yAxis = viewUp.normalize().modifySub(zAxis.scalarMultiply(
						        zAxis.scalarProduct(viewUp.normalize()))).modifyNormalize();
				Point3D xAxis = zAxis.vectorProduct(yAxis).modifyNormalize(); 
				
				Point3D screenCorner = view.add(xAxis.scalarMultiply(-horizontal / 2))
						                   .modifyAdd(yAxis.scalarMultiply(vertical / 2));
				
				Scene scene = RayTracerViewer.createPredefinedScene();
				
				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new CalculateColor(eye, horizontal, vertical, width, height, 0, 
					height - 1, xAxis, yAxis, screenCorner, scene, red, green, blue, cancel
				));
				pool.shutdown();
				
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}
	
}
