package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;

/**
 * Utility class which contains method that implements a part of the ray-casting algorithm.
 * 
 * @author Matija Frandolić
 */
public class Util {

	/**
	 * Determines the closest intersection of the given ray and any object
	 * in the given scene that is in front of the observer and colors the pixel.
	 * If no intersection is found, the pixel will be rendered black. Phong's
	 * model is used for coloring.
	 * 
	 * @param scene the scene in which to find the closest intersection of the
	 *              given ray and some object that is in front of the observer 
	 * @param ray   the ray from the observer to the pixel that needs to be colored
	 * @param rgb   the array that will be filled with color code of the pixel
	 */
	public static void tracer(Scene scene, Ray ray, short[] rgb) {
		rgb[0] = 0;
		rgb[1] = 0;
		rgb[2] = 0;
		
		RayIntersection closest = findClosestIntersection(scene, ray);
		if (closest == null) {
			return;
		}
		
		determineColorFor(scene, closest.getPoint(), ray.start, rgb);
	}
	
	/**
	 * Returns the {@link RayIntersection} that is the closest intersection
	 * of the given ray and some object in the scene that is in front of the 
	 * observer.
	 * 
	 * @param scene the scene in which to find the closest intersection of the
	 *              given ray and some object that is in front of the observer 
	 * @param ray   the ray that is used to find the closest intersection with 
	 *              some object in the scene
	 * @return      the {@code RayIntersection} that is the closest intersection
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		RayIntersection closestIntersection = null;
		double closestDistance = Double.MAX_VALUE;
		
		for (GraphicalObject object : scene.getObjects()) {
			RayIntersection intersection = object.findClosestRayIntersection(ray);
			if (intersection == null) {
				continue;
			}
			
			double distance = intersection.getDistance();
			if (distance < closestDistance) {
				closestIntersection = intersection;
				closestDistance = distance;
			}
		}
		
		return closestIntersection;
	}
	
	/**
	 * Determines the color of the pixel according on Phong's model.
	 * 
	 * @param scene             the scene that contains graphical objects and light sources
	 * @param intersectionPoint the closest intersection of ray from the observer and some 
	 *                          object in the scene
	 * @param eye               the position of the observer
	 * @param rgb               the array that will be filled with color code of the pixel
	 */
	private static void determineColorFor(Scene scene, Point3D intersectionPoint, 
		                                  Point3D eye, short[] rgb) {
		rgb[0] = 15;
		rgb[1] = 15;
		rgb[2] = 15;

		for (LightSource ls : scene.getLights()) {
			Point3D lightPoint = ls.getPoint();
			Ray ray = Ray.fromPoints(lightPoint, intersectionPoint);
			RayIntersection closest = findClosestIntersection(scene, ray);
			
			final double TOLERANCE = 1E-8;
			if (closest == null ||
				closest.getDistance() < lightPoint.sub(intersectionPoint).norm() - TOLERANCE) {
				continue;
			}
			
			Point3D l = lightPoint.sub(intersectionPoint).modifyNormalize();
			Point3D n = closest.getNormal();
			Point3D d = ray.direction;
			Point3D r = d.sub(n.scalarMultiply(2 * d.scalarProduct(n))).modifyNormalize();
			Point3D v = eye.sub(intersectionPoint).modifyNormalize();
			
			double diffusePart = Math.max(0, l.scalarProduct(n));
			double reflectivePart = Math.pow(Math.max(0, r.scalarProduct(v)), closest.getKrn());
            
			rgb[0] += ls.getR() * closest.getKdr() * diffusePart;
			rgb[0] += ls.getR() * closest.getKrr() * reflectivePart;
			
			rgb[1] += ls.getG() * closest.getKdg() * diffusePart;
			rgb[1] += ls.getG() * closest.getKrg() * reflectivePart;
			
			rgb[2] += ls.getB() * closest.getKdb() * diffusePart;
			rgb[2] += ls.getB() * closest.getKrb() * reflectivePart;
		}
	}
	
}
