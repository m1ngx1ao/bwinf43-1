package test.difficulty;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.LinkedList;

import difficulty.Exercise;
import difficulty.InequalityGraph;
import difficulty.MultiSet;

public class InequalityGraphTest {
	InequalityGraph cut;
	Exercise exA;
	Exercise exB;
	Exercise exC;
	Exercise exD;
	
	@Before
	public void setup() {
		// construct a graph with A -> B, B -> C (twice), C -> A, C -> B
		List<List<String>> chains = new LinkedList<List<String>>();
		chains.add(Arrays.asList("ABC".split("")));
		chains.add(Arrays.asList("BCA".split("")));
		chains.add(Arrays.asList("CB".split("")));
		cut = new InequalityGraph(Arrays.asList("ABCD".split("")), chains);
		exA = cut.getExerciseById("A");
		exB = cut.getExerciseById("B");
		exC = cut.getExerciseById("C");
		exD = cut.getExerciseById("D");
	}

	@Test
	public void allExercisesAdded() {
		Set<Exercise> act = cut.getExercises();
		
		assertEquals(4, act.size());
		assertTrue(act.containsAll(Arrays.asList(exA, exB, exC, exD)));
	}

	@Test
	public void allEdgesAdded() {
		MultiSet<Exercise> harderThanA = exA.getHarder();
		MultiSet<Exercise> harderThanB = exB.getHarder();
		MultiSet<Exercise> harderThanC = exC.getHarder();
		MultiSet<Exercise> harderThanD = exD.getHarder();
		MultiSet<Exercise> easierThanA = exA.getEasier();
		MultiSet<Exercise> easierThanB = exB.getEasier();
		MultiSet<Exercise> easierThanC = exC.getEasier();
		MultiSet<Exercise> easierThanD = exD.getEasier();
		
		assertEquals(1, harderThanA.getFlatList().size());
		assertEquals(1, harderThanA.getCountByElement(exB));
		assertEquals(1, easierThanA.getFlatList().size());
		assertEquals(1, easierThanA.getCountByElement(exC));

		assertEquals(2, harderThanB.getFlatList().size());
		assertEquals(2, harderThanB.getCountByElement(exC));
		assertEquals(2, easierThanB.getFlatList().size());
		assertEquals(1, easierThanB.getCountByElement(exA));
		assertEquals(1, easierThanB.getCountByElement(exC));

		assertEquals(2, harderThanC.getFlatList().size());
		assertEquals(1, harderThanC.getCountByElement(exA));
		assertEquals(1, harderThanC.getCountByElement(exB));
		assertEquals(2, easierThanC.getFlatList().size());
		assertEquals(2, easierThanC.getCountByElement(exB));

		assertEquals(0, easierThanD.size());
		assertEquals(0, harderThanD.size());
	}

	@Test
	public void removeWithoutShortcuttingAdjacencies() {
		cut.remove(exB);
		
		assertTrue(cut.getExercises().containsAll(Arrays.asList(exA, exC, exD)));
		assertEquals(3, cut.getExercises().size());
		assertEquals(0, exA.getHarder().size());
		assertEquals(0, exC.getEasier().size());
	}

	@Test
	public void removeWithShortcuttingAdjacencies() {
		cut.removeAndShortcutAdjacencies(exB);
		
		MultiSet<Exercise> harderThanA = exA.getHarder();
		MultiSet<Exercise> harderThanC = exC.getHarder();
		MultiSet<Exercise> easierThanA = exA.getEasier();
		MultiSet<Exercise> easierThanC = exC.getEasier();
		
		assertEquals(2, harderThanA.getFlatList().size());
		assertEquals(2, harderThanA.getCountByElement(exC));
		assertEquals(1, easierThanA.getFlatList().size());
		assertEquals(1, easierThanA.getCountByElement(exC));

		assertEquals(1, harderThanC.getFlatList().size());
		assertEquals(1, harderThanC.getCountByElement(exA));
		assertEquals(2, easierThanC.getFlatList().size());
		assertEquals(2, easierThanC.getCountByElement(exA));
	}

	@Test
	public void noIncomingEdgeIsEasiest() {
		assertEquals(exD, cut.getEasiest());
	}

	@Test
	public void unbalancedCycleMultiEdgeSourceIsEasiest() {
		cut.remove(exA);
		cut.remove(exD);

		assertEquals(exB, cut.getEasiest());
	}

	@Test
	public void stronglyConnectedCompMinimalIncomingEdgeIsEasiest() {
		cut.remove(exD);

		assertEquals(exA, cut.getEasiest());
	}
}
