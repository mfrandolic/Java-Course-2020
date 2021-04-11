package hr.fer.zemris.java.raytracer;

import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Program that demonstrates rendering of a 3D scene by using ray-casting algorithm.
 * 
 * @author Matija Frandolić
 */
public class RayCaster {

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
				
				short[] rgb = new short[3];
				int offset = 0;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
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
				
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}
	
}
