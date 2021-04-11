package hr.fer.zemris.math;

/**
 * This class represents a 2D vector with real-valued components
 * and provides methods for basic vector transformations.
 * 
 * @author Matija Frandolić
 */
public class Vector2D {
	
	/**
	 * X-component of this vector.
	 */
	private double x;
	
	/**
	 * Y-component of this vector.
	 */
	private double y;

	/**
	 * Constructs a new vector from the given components.
	 * 
	 * @param x x-component of this vector
	 * @param y y-component of this vector
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns x-component of this vector.
	 * 
	 * @return x-component of this vector
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Returns y-component of this vector.
	 * 
	 * @return y-component of this vector
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Translates this vector by the given vector.
	 * 
	 * @param offset the vector that is used to translate this vector 
	 */
	public void translate(Vector2D offset) {
		x += offset.x;
		y += offset.y;
	}
	
	/**
	 * Returns a new vector that is created by translating this vector
	 * by the given vector. This vector remains unchanged.
	 * 
	 * @param  offset the vector that is used for translation
	 * @return        a new vector created by translating this vector
	 */
	public Vector2D translated(Vector2D offset) {
		Vector2D other = copy();
		other.translate(offset);
		return other;
	}
	
	/**
	 * Rotates this vector by the given angle.
	 * 
	 * @param angle the angle of rotation
	 */
	public void rotate(double angle) {
		double tmp = x;
		x = x * Math.cos(angle) - y * Math.sin(angle);
		y = tmp * Math.sin(angle) + y * Math.cos(angle);
	}
	
	/**
	 * Returns a new vector that is created by rotating this vector
	 * by the given angle. This vector remains unchanged.
	 * 
	 * @param  angle the angle of rotation
	 * @return       a new vector created by rotating this vector
	 */
	public Vector2D rotated(double angle) {
		Vector2D other = copy();
		other.rotate(angle);
		return other;
	}
	
	/**
	 * Scales this vector by the given scaler.
	 * 
	 * @param scaler the number used for scaling
	 */
	public void scale(double scaler) {
		x *= scaler;
		y *= scaler;
	}
	
	/**
	 * Returns a new vector that is created by scaling this vector
	 * by the given scaler. This vector remains unchanged.
	 * 
	 * @param  scaler the number used for scaling
	 * @return        a new vector created by scaling this vector
	 */
	public Vector2D scaled(double scaler) {
		Vector2D other = copy();
		other.scale(scaler);
		return other;
	}
	
	/**
	 * Returns a new vector that is a copy of this vector.
	 * 
	 * @return a new vector that is a copy of this vector
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}
	
}
