package test.difficulty;

import org.junit.Test;

public class OrderOptimizerSimpleTest extends OrderOptimizerBasis {
	@Test
	public void noExerciseEmptyResult() {
		whenExecute("", "");

		thenOrder("");
	}

	@Test
	public void oneExerciseRequested() {
		whenExecute("A", "A");

		thenOrder("A");
	}

	@Test
	public void twoExercisesWithConstraintOrdered() {
		givenInequalityChain("BA");

		whenExecute("AB", "AB");

		thenOrder("BA");
		thenViolationsCount(0);
		thenConstraintsCount(1);
		thenConstraint("BA", 1);
	}

	@Test
	public void threeExercisesInChainMiddleNotRequestedShortcut() {
		givenInequalityChain("BA");
		givenInequalityChain("CB");
		
		whenExecute("ABC", "AC");

		thenOrder("CA");
		thenViolationsCount(0);
		thenConstraintsCount(1);
		thenConstraint("CA", 1);
	}

	@Test
	public void cycleOfNotRequestedExercisesIgnoredForOrder() {
		givenInequalityChain("BACD");
		givenInequalityChain("CB");

		whenExecute("ABCD", "AD");

		thenOrder("AD");
		thenViolationsCount(0);
		thenConstraintsCount(1);
		thenConstraint("AD", 1);
	}

	@Test
	public void cycleOfDifferentWeightsOptimizedOrder() {
		givenInequalityChain("AB");
		givenInequalityChain("BA");
		givenInequalityChain("BA");

		whenExecute("AB", "AB");

		thenOrder("BA");
		thenViolationsCount(1);
		thenViolation("AB", 1);
		thenConstraintsCount(3);
		thenConstraint("AB", 1);
		thenConstraint("BA", 2);
	}
}
