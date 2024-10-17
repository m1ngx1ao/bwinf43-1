package test.difficulty;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import difficulty.MultiSet;

public class MultiSetTest {
	MultiSet<Integer> cut;
	
	@Before
	public void setup() {
		cut = new MultiSet<Integer>();
	}

	@Test
	public void addMultipleCardinalities() {
		cut.add(1);
		cut.add(1);
		cut.add(2);
		cut.add(2);
		cut.add(2);
		
		assertEquals(2, cut.getCountByElement(1));
		assertEquals(3, cut.getCountByElement(2));
		assertEquals(5, cut.getFlatList().size());
		assertEquals(2, cut.getSortedUniqueElements().size());
		assertEquals("[1, 2]", cut.getSortedUniqueElements().toString());
	}

	@Test
	public void removeLastAndNotLast() {
		cut.add(1);
		cut.add(1);
		cut.add(2);
		
		cut.remove(2);
		cut.remove(1);
		
		assertEquals(1, cut.getCountByElement(1));
		assertEquals(0, cut.getCountByElement(2));
		assertEquals("[1]", cut.getFlatList().toString());
		assertEquals(1, cut.getSortedUniqueElements().size());
		assertEquals("[1]", cut.getSortedUniqueElements().toString());
	}
}
