package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * Implementation of {@link LayoutManager2} that is used as layout manager
 * for {@link Calculator}. Grid used by this layout manager is fixed at 5 rows 
 * and 7 columns. Constraints used by this layout manager are instances of the 
 * class {@link RCPosition}. Each constraint can be associated with only one 
 * component and vice versa. Valid strings can also be used as constraints (see 
 * {@link RCPosition#parse(String)}.
 * 
 * @author Matija Frandolić
 */
public class CalcLayout implements LayoutManager2 {

	/**
	 * Amount of space between two cells in the layout.
	 */
	private int gap;
	
	/**
	 * Map that represents layout grid.
	 */
	private Map<RCPosition, Component> grid;
	
	/**
	 * Number of rows in the layout.
	 */
	private static final int ROWS = 5;
	
	/**
	 * Number of columns in the layout.
	 */
	private static final int COLS = 7;
	
	/**
	 * Constructs a new {@code CalcLayout} from the given gap amount.
	 * 
	 * @param gap amount of space between two cells in the layout
	 */
	public CalcLayout(int gap) {
		this.gap = gap;
		grid = new HashMap<RCPosition, Component>();
	}
	
	/**
	 * Constructs a new {@code CalcLayout} with no gaps between cells.
	 */
	public CalcLayout() {
		this(0);
	}
	
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Adds the specified component to the layout, using the specified constraint object.
	 * 
	 * @throws IllegalArgumentException if constraint is not RCPosition or String or if
	 *                                  the format of the given string is invalid
	 * @throws CalcLayoutException      if multiple components are added to the same 
	 *                                  constraint or if multiple constraints are added to 
	 *                                  the same component or if constraint is invalid
	 */
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		Objects.requireNonNull(comp);
		Objects.requireNonNull(constraints);
		
		RCPosition position;
		if (constraints instanceof String) {
			position = RCPosition.parse((String) constraints);			
		} else if (constraints instanceof RCPosition) {
			position = (RCPosition) constraints;
		} else {
			throw new IllegalArgumentException("Constraint must be RCPosition or String.");
		}
		
		int r = position.getRow();
		int c = position.getColumn();
		if (r < 1 || r > ROWS || c < 1 || c > COLS || 
		   (r == 1 && c > 1 && c < COLS - 1)) {
			throw new CalcLayoutException("Illegal constraint.");
		}
		
		if (grid.containsKey(position)) {
			throw new CalcLayoutException("Cannot associate constraint with multiple components.");
		}

		if (grid.containsValue(comp)) {
			throw new CalcLayoutException("Cannot associate component with multiple constraints.");
		}
		
		grid.put(position, comp);
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		grid.values().remove(comp);
	}
	
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return calculateLayoutSize(parent, Component::getPreferredSize);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return calculateLayoutSize(parent, Component::getMinimumSize);
	}
	
	@Override
	public Dimension maximumLayoutSize(Container target) {
		return calculateLayoutSize(target, Component::getMaximumSize);
	}
	
	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
		// do nothing
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
        double cellWidth = (double) (parent.getWidth() - insets.left - insets.right - 
        		                    (COLS - 1) * gap) / COLS;
        double cellHeight = (double) (parent.getHeight() - insets.top - insets.bottom - 
        		                     (ROWS - 1) * gap) / ROWS;
		
        for (Map.Entry<RCPosition, Component> entry : grid.entrySet()) {
			RCPosition position = entry.getKey();
    		int r = position.getRow();
    		int c = position.getColumn();
			Component component = entry.getValue();
        	
        	if (r == 1 && c == 1) {
        		component.setBounds(insets.left, 
        				            insets.top, 
        				            (int) ((COLS - 2) * cellWidth + (COLS - 3) * gap), 
        				            (int) cellHeight);
        	} else {
        		component.setBounds((int) ((c - 1) * (cellWidth + gap) + insets.left), 
        				            (int) ((r - 1) * (cellHeight + gap) + insets.top), 
        				            (int) cellWidth, 
        				            (int) cellHeight);      		
        	}
        }
	}
	
	/**
	 * Calculates the layout size from sizes of all the components currently added
	 * to the layout using the given extractor function to extract the field by which
	 * to calculate the size.
	 * 
	 * @param parent    the component to be laid out
	 * @param extractor function used to extract the field by which to calculate the size
	 * @return          {@code Dimension} that represents calculated layout size
	 */
	private Dimension calculateLayoutSize(Container parent, 
                                          Function<Component, Dimension> extractor) {
		int cellWidth = 0;
		int cellHeight = 0;
		
		for (Map.Entry<RCPosition, Component> entry : grid.entrySet()) {
			RCPosition position = entry.getKey();
			Component component = entry.getValue();
			Dimension componentDimension = extractor.apply(component);
			if (componentDimension == null) {
				continue;
			}
			
			if (position.getRow() == 1 && position.getColumn() == 1) {
				cellWidth = Math.max(cellWidth, 
						            (componentDimension.width - (COLS - 3) * gap) / (COLS - 2));
			} else {
				cellWidth = Math.max(cellWidth, componentDimension.width);
			}
			
			cellHeight = Math.max(cellHeight, componentDimension.height);
		}
		
		Insets insets = parent.getInsets();
		Dimension layoutSize = new Dimension();
		layoutSize.width = COLS * cellWidth + (COLS - 1) * gap + insets.left + insets.right;
		layoutSize.height = ROWS * cellHeight + (ROWS - 1) * gap + insets.top + insets.bottom;
		
		return layoutSize;
	}

}
