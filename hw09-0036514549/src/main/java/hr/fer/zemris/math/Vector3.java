package hr.fer.zemris.math;

/**
 * Model of an immutable 3D vector. All operations on vectors of this class
 * return new vector as a result of the operation.
 * 
 * @author Matija Frandolić
 */
public class Vector3 {
	
	/**
	 * X-component of this vector.
	 */
	private final double x;
	
	/**
	 * Y-component of this vector.
	 */
	private final double y;
	
	/**
	 * Z-component of this vector.
	 */
	private final double z;

	/**
	 * Constructs a new vector from the given components.
	 * 
	 * @param x x-component of this vector
	 * @param y y-component of this vector
	 * @param z z-component of this vector
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Returns the length of this vector.
	 *  
	 * @return the length of this vector
	 */
	public double norm() {
		return Math.sqrt(x * x + y * y + z * z);
	}
	
	/**
	 * Returns a new vector that represents normalized version of this vector.
	 * Zero-vector is returned if this vector is also zero-vector.
	 * 
	 * @return new vector that represents normalized version of this vector
	 */
	public Vector3 normalized() {
		if (isZeroVector(this)) {
			return this;
		}
		return new Vector3(x / norm(), y / norm(), z / norm());
	}
	
	/**
	 * Adds this vector and the given one and returns new vector as a result.
	 * 
	 * @param  other the vector that is added to this one
	 * @return       new vector that is the result of addition
	 */
	public Vector3 add(Vector3 other) {
		return new Vector3(x + other.x, y + other.y, z + other.z);
	}
	
	/**
	 * Subtracts the given vector from this one and returns new vector as a result.
	 * 
	 * @param  other the vector that is subtracted from this one
	 * @return       new vector that is the result of subtraction
	 */
	public Vector3 sub(Vector3 other) {
		return new Vector3(x - other.x, y - other.y, z - other.z);
	}
	
	/**
	 * Returns the dot product (scalar product) of this vector and the given one. 
	 * 
	 * @param  other the vector that is used to calculate dot product with this vector 
	 * @return       dot product of this vector and the given one
	 */
	public double dot(Vector3 other) {
		return x * other.x + y * other.y + z * other.z;
	}
	
	/**
	 * Returns the cross product (vector product) of this vector and the given one.
	 * 
	 * @param  other the vector that is used to calculate cross product with this vector
	 * @return       cross product of this vector and the given one
	 */
	public Vector3 cross(Vector3 other) {
		double resultX = y * other.z - z * other.y;
		double resultY = z * other.x - x * other.z;
		double resultZ = x * other.y - y * other.x;		
		return new Vector3(resultX, resultY, resultZ);
	}
	
	/**
	 * Scales this vector by the given constant and returns the resulting vector.
	 * 
	 * @param  s the constant that is used to scale this vector 
	 * @return   new vector that is the scaled version of the original
	 */
	public Vector3 scale(double s) {
		return new Vector3(s * x, s * y, s * z);
	}
	
	/**
	 * Returns the cosine of angle between this vector and the given one. In the
	 * special case when one or both vectors are zero-vectors, the angle between
	 * them is undefined. However, they can be considered orthogonal since their
	 * dot product is zero, so 0 is returned.
	 * 
	 * @param  other the vector that is used to calculate the cosine of angle from
	 *               this vector
	 * @return       the cosine of angle between this vector and the given one
	 */
	public double cosAngle(Vector3 other) {
		if (isZeroVector(this) || isZeroVector(other)) {
			return 0;
		}
		return dot(other) / (norm() * other.norm());
	}
	
	/**
	 * Returns the x-component of this vector.
	 * 
	 * @return the x-component of this vector
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Returns the y-component of this vector.
	 * 
	 * @return the y-component of this vector
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Returns the z-component of this vector.
	 *
	 * @return the z-component of this vector
	 */
	public double getZ() {
		return z;
	}
	
	/**
	 * Returns the array of length 3 that has components of this vector as its
	 * elements, in this order: x, y, z.
	 * 
	 * @return the array of components of this vector
	 */
	public double[] toArray() {
		return new double[] {x, y, z};
	}
	
	/**
	 * Returns a string representation of this vector in the format "(x, y, z)".
	 * 
	 * @return a string representation of this vector
	 */
	@Override
	public String toString() {
		return String.format("(%f, %f, %f)", x, y, z);
	}
	
	/**
	 * Returns {@code true} if the given vector is zero-vector and {@code false}
	 * otherwise.
	 * 
	 * @param  vector the vector for which to determine if it is zero-vector
	 * @return        {@code true} if the given vector is zero-vector,
	 *                {@code false} otherwise
	 */
	private static boolean isZeroVector(Vector3 vector) {
		final double PRECISION = 1E-8;
		if (Math.abs(vector.x - 0) < PRECISION &&
            Math.abs(vector.y - 0) < PRECISION &&
            Math.abs(vector.z - 0) < PRECISION) {
			return true;
		}
		return false;
	}
	
}
