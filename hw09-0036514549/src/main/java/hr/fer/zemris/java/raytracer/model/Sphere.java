package hr.fer.zemris.java.raytracer.model;

/**
 * Model of a sphere that is a {@link GraphicalObject} that can exist in
 a {@link Scene}.
 * 
 * @author Matija Frandolić
 */
public class Sphere extends GraphicalObject {

	/**
	 * Center of this sphere.
	 */
	private Point3D center;
	
	/**
	 * Radius of this sphere.
	 */
	private double radius;
	
	/**
	 * Coefficient of the diffuse component for the red color.
	 */
	private double kdr;
	
	/**
	 * Coefficient of the diffuse component for the green color.
	 */
	private double kdg;
	
	/**
	 * Coefficient of the diffuse component for the blue color.
	 */
	private double kdb;
	
	/**
	 * Coefficient of the reflective component for the red color.
	 */
	private double krr;
	
	/**
	 * Coefficient of the reflective component for the green color.
	 */
	private double krg;
	
	/**
	 * Coefficient of the reflective component for the blue color.
	 */
	private double krb;
	
	/**
	 * Shininess factor.
	 */
	private double krn;
	
	/**
	 * Constructs a new {@code Sphere} from the given arguments.
	 * 
	 * @param center center of this sphere
	 * @param radius radius of this sphere
	 * @param kdr    coefficient of the diffuse component for the red color
	 * @param kdg    coefficient of the diffuse component for the green color
	 * @param kdb    coefficient of the diffuse component for the blue color
	 * @param krr    coefficient of the reflective component for the red color
	 * @param krg    coefficient of the reflective component for the green color
	 * @param krb    coefficient of the reflective component for the blue color
	 * @param krn    shininess factor
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, 
		double kdb, double krr, double krg, double krb, double krn) {
		
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {		
		double lambda = ray.direction.scalarProduct(center.sub(ray.start));
		Point3D p = ray.start.add(ray.direction.scalarMultiply(lambda));
		
		double d = center.sub(p).norm();
		if (d > radius) {
			return null;
		}
		
		double distanceFromClosest = lambda - Math.sqrt(radius * radius - d * d); 
		Point3D closest = ray.start.add(ray.direction.scalarMultiply(distanceFromClosest));
		
		return new RayIntersection(closest, distanceFromClosest, true) {
			@Override
			public Point3D getNormal() {
				return closest.sub(center).modifyNormalize();
			}
			@Override
			public double getKdr() {
				return kdr;
			}
			@Override
			public double getKdg() {
				return kdg;
			}
			@Override
			public double getKdb() {
				return kdb;
			}
			@Override
			public double getKrr() {
				return krr;
			}
			@Override
			public double getKrg() {
				return krg;
			}
			@Override
			public double getKrb() {
				return krb;
			}
			@Override
			public double getKrn() {
				return krn;
			}
		};
	}

}
