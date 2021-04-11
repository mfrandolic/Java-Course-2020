package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Implementation of {@link IFractalProducer} used to produce a fractal derived
 * from Newton-Raphson iteration.
 * 
 * @author Matija Frandolić
 */
public class NewtonRaphsonFractalProducer implements IFractalProducer {
	
	/**
	 * Thread pool based on {@code ExecutorService} used for implementation
	 * of parallelization.
	 */
	private ExecutorService pool;
	
	/**
	 * Complex polynomial that is used to generate a fractal, in rooted form.
	 */
	private ComplexRootedPolynomial crp;
	
	/**
	 * Complex polynomial that is used to generate a fractal, in standard form.
	 */
	private ComplexPolynomial polynomial;
	
	/**
	 * Constructs a new {@code NewtonRaphsonFractalProducer} from the given
	 * {@link ComplexRootedPolynomial} object.
	 * 
	 * @param crp complex rooted polynomial that is used to generate a fractal
	 */
	public NewtonRaphsonFractalProducer(ComplexRootedPolynomial crp) {
		pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), 
				                            new DaemonicThreadFactory());
		this.crp = crp;
		polynomial = crp.toComplexPolynom();
	}

	/**
	 * Produces the data needed to display a fractal.
	 */
	@Override
	public void produce(double reMin, double reMax, double imMin, double imMax, int width, 
			int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {

		int m = 16 * 16 * 16;
		short[] data = new short[width * height];
		final int yRanges = 8 * Runtime.getRuntime().availableProcessors();
		int numberOfYsByRange = height / yRanges;
		
		List<Future<?>> results = new ArrayList<>();
		
		for(int i = 0; i < yRanges; i++) {
			int yMin = i * numberOfYsByRange;
			int yMax = (i + 1) * numberOfYsByRange - 1;
			if (i == yRanges - 1) {
				yMax = height - 1;
			}
			
			CalculateFractal job = new CalculateFractal(reMin, reMax, imMin, imMax, 
					width, height, yMin, yMax, m, data, cancel, crp);
			results.add(pool.submit(job));
		}

		for(Future<?> job : results) {
			try {
				job.get();
			} catch (InterruptedException | ExecutionException e) {
				// ignore
			}
		}
		
		observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
	}
	
	/**
	 * Implementation of {@link ThreadFactory} that is used to create daemon threads.
	 */
	private static class DaemonicThreadFactory implements ThreadFactory {

		/**
		 * Constructs a new {@code Thread} with daemon flag set to {@code true}.
		 */
		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setDaemon(true);
			return t;
		}
		
	}
	
}
