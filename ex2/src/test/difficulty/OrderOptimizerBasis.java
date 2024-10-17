package test.difficulty;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;

import org.junit.Before;

import difficulty.InequalityPair;
import difficulty.MultiSet;
import difficulty.OrderOptimizer;
import difficulty.Solution;

public class OrderOptimizerBasis {
	OrderOptimizer cut;
	List<Integer> sectionPeriods;
	List<List<String>> chainsOfAscendingDifficulty;
	Solution solution;

	@Before
	public void setup() {
		cut = new OrderOptimizer();
		sectionPeriods = new LinkedList<Integer>();
		chainsOfAscendingDifficulty = new LinkedList<List<String>>();
	}
	
	protected void givenInequalityChain(String inequalities) {
		chainsOfAscendingDifficulty.add(Arrays.asList(inequalities.split("")));
	}
	
	protected void whenExecute(String overallExerciseIds, String requestedExerciseIds) {
		solution = cut.execute(Arrays.asList(overallExerciseIds.split("")), chainsOfAscendingDifficulty,
				Arrays.asList(requestedExerciseIds.split("")));
	}

	protected void thenOrder(String expectedOrder) {
		assertEquals(Arrays.asList(expectedOrder.split("")).toString(), solution.getOrder().toString());
	}

	private void thenInequality(MultiSet<InequalityPair> inequalities, String expectedInequality, int count) {
		String expectedInString = "" + expectedInequality.charAt(0) + " < "
			+ expectedInequality.charAt(1)
			+ (count > 1 ? " (" + count + ")" : "");
		assertTrue(inequalities.toString().contains(expectedInString));
	}
	
	protected void thenViolation(String expectedViolation, int count) {
		thenInequality(solution.getViolations(), expectedViolation, count);
	}
	
	protected void thenConstraint(String expectedConstraint, int count) {
		thenInequality(solution.getConstraints(), expectedConstraint, count);
	}

	protected void thenViolationsCount(int expectedCount) {
		assertEquals(expectedCount, solution.getViolations().size());
	}

	protected void thenConstraintsCount(int expectedCount) {
		assertEquals(expectedCount, solution.getConstraints().size());
	}
}
