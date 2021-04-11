package hr.fer.zemris.java.gui.prim;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JList;
import javax.swing.ListModel;

import org.junit.jupiter.api.Test;

class PrimListModelTest {

	private void checkListElements(JList<Integer> list, int size, Integer ... expected) {
		ListModel<Integer> model = list.getModel();
		for (int i = 0; i < size; i++) {
			assertEquals(expected[i], model.getElementAt(i));
		}
	}
	
	@Test
	public void testNext() {
		PrimListModel model = new PrimListModel();
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);
		
		assertEquals(1, list1.getModel().getSize());
		assertEquals(1, list2.getModel().getSize());
		checkListElements(list1, 1, 1);
		checkListElements(list2, 1, 1);
		
		model.next();
		
		assertEquals(2, list1.getModel().getSize());
		assertEquals(2, list2.getModel().getSize());
		checkListElements(list1, 2, 1, 2);
		checkListElements(list2, 2, 1, 2);
		
		model.next();
		
		assertEquals(3, list1.getModel().getSize());
		assertEquals(3, list2.getModel().getSize());
		checkListElements(list1, 3, 1, 2, 3);
		checkListElements(list2, 3, 1, 2, 3);
		
		model.next();
		
		assertEquals(4, list1.getModel().getSize());
		assertEquals(4, list2.getModel().getSize());
		checkListElements(list1, 4, 1, 2, 3, 5);
		checkListElements(list2, 4, 1, 2, 3, 5);
		
		model.next();
		
		assertEquals(5, list1.getModel().getSize());
		assertEquals(5, list2.getModel().getSize());
		checkListElements(list1, 5, 1, 2, 3, 5, 7);
		checkListElements(list2, 5, 1, 2, 3, 5, 7);
		
		model.next();
		
		assertEquals(6, list1.getModel().getSize());
		assertEquals(6, list2.getModel().getSize());
		checkListElements(list1, 6, 1, 2, 3, 5, 7, 11);
		checkListElements(list2, 6, 1, 2, 3, 5, 7, 11);
		
		model.next();
		
		assertEquals(7, list1.getModel().getSize());
		assertEquals(7, list2.getModel().getSize());
		checkListElements(list1, 6, 1, 2, 3, 5, 7, 11, 13);
		checkListElements(list2, 6, 1, 2, 3, 5, 7, 11, 13);
	}

}
