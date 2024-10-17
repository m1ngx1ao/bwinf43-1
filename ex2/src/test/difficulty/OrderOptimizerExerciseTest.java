package test.difficulty;

import org.junit.Test;

/**
 * ex-post test automation for refactoring after completing the exercise
 */
public class OrderOptimizerExerciseTest extends OrderOptimizerBasis {
	@Test
	public void challenge0NoViolations() {
		givenInequalityChain("BADF");
		givenInequalityChain("DFG");
		givenInequalityChain("AEDC");
		givenInequalityChain("GFC");

		whenExecute("ABCDEFG", "BCDEF");

		thenOrder("BEDFC");
		thenViolationsCount(0);
		thenConstraintsCount(7);
		thenConstraint("BD", 1);
		thenConstraint("BE", 1);
		thenConstraint("DC", 1);
		thenConstraint("DF", 2);
		thenConstraint("ED", 1);
		thenConstraint("FC", 1);
	}
	
	@Test
	public void challenge1NoViolations() {
		givenInequalityChain("ABCD");
		givenInequalityChain("AEC");
		givenInequalityChain("CFD");
		givenInequalityChain("EGF");

		whenExecute("ABCDEFG", "ACDFG");

		thenOrder("ACGFD");
		thenViolationsCount(0);
		thenConstraintsCount(7);
		thenConstraint("AC", 2);
		thenConstraint("AG", 1);
		thenConstraint("CD", 1);
		thenConstraint("CF", 1);
		thenConstraint("FD", 1);
		thenConstraint("GF", 1);
	}

	@Test
	public void challenge2ThreeDirectCyclesOrderViolatesThreeInequalities() {
		givenInequalityChain("ABC");
		givenInequalityChain("ECD");
		givenInequalityChain("ECA");
		givenInequalityChain("EFGH");
		givenInequalityChain("HF");
		givenInequalityChain("DE");

		whenExecute("ABCDEFGH", "ABDEFG");

		thenViolationsCount(3);
		// having E -> D twice and D -> E only once, violating latter one minimizes loss
		thenViolation("DE", 1);
		thenConstraintsCount(11);
	}

	@Test
	public void challenge3ThreeDisjointSetsFourCycles() {
		givenInequalityChain("ABC");
		givenInequalityChain("CBDAEFG");
		givenInequalityChain("HIJK");
		givenInequalityChain("ILH");
		givenInequalityChain("MN");
		givenInequalityChain("NM");

		whenExecute("ABCDEFGHIJKLMN", "ABCDEFGHIJKLMN");

		thenViolationsCount(4);
		thenConstraintsCount(15);
	}

	@Test
	public void challenge4LargeListOfConstraintsNoViolations() {
		givenInequalityChain("ABCDEJI");
		givenInequalityChain("BCEDIHK");
		givenInequalityChain("SGHIJ");
		givenInequalityChain("GHSO");
		givenInequalityChain("MNOK");
		givenInequalityChain("KOM");
		givenInequalityChain("PQRFNM");
		givenInequalityChain("SFPNK");
		givenInequalityChain("FTU");
		givenInequalityChain("VWTZ");
		givenInequalityChain("YXZT");
		givenInequalityChain("ZWTVTU");
		givenInequalityChain("KWZY");
		givenInequalityChain("ABDEWZXYU");
		givenInequalityChain("RQKL");
		givenInequalityChain("PFKOXW");

		whenExecute("ABCDEFGHIJKLMNOPQRSTUVWXYZ", "BWINF");

		thenOrder("BIFNW");
		thenViolationsCount(0);
	}
}
