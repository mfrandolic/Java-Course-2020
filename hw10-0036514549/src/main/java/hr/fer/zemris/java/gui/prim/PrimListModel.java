package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Implementation of {@link ListModel} that generates prime numbers incrementally.
 * List contains only number 1 when it is first created. Each call to method
 * {@link PrimListModel#next()} adds next prime number to the list of this model.
 * 
 * @author Matija Frandolić
 */
public class PrimListModel implements ListModel<Integer> {
	
	/**
	 * Current prime number.
	 */
	private int currentPrime;
	
	/**
	 * List of found prime numbers.
	 */
	private List<Integer> primes;
	
	/**
	 * List of listeners.
	 */
	private List<ListDataListener> listeners;
	
	/**
	 * Constructs a new {@code PrimListModel} containing only number 1.
	 */
	public PrimListModel() {
		currentPrime = 1;
		primes = new ArrayList<Integer>();
		primes.add(currentPrime);
		listeners = new ArrayList<ListDataListener>();
	}

	@Override
	public int getSize() {
		return primes.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return primes.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners = new ArrayList<>(listeners);
		listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners = new ArrayList<>(listeners);
		listeners.remove(l);
	}
	
	/**
	 * Generates next prime number and adds it to the list of this model.
	 */
	public void next() {
		int nextPrime = currentPrime;
		boolean found = false;
		
		while (!found) {
			nextPrime++;
			found = true;
			for (int i = 2; i <= Math.sqrt(nextPrime); i++) {
				if (nextPrime % i == 0) {
					found = false;
					break;
				}
			}
		}
		
		int index = primes.size();
		primes.add(nextPrime);
		currentPrime = nextPrime;
		
		ListDataEvent e = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index, index);
		listeners.forEach(l -> l.intervalAdded(e));
	}
 
}
