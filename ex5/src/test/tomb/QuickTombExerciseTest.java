package test.tomb;

import org.junit.Test;

/**
 * ex-post test automation for refactoring after completing the exercise
 */
public class QuickTombExerciseTest extends QuickTombBasis {
	@Test
	public void tomb0ConsolidatedMoveTo2ndSection() {
		givenSectionPeriods(new int[] { 5, 8, 12 });

		whenExecute();

		// only move to section 1 after section 2 opens (reduces the number of steps)
		thenAssertPath(new int[] {
			0, 0,
			8, 0,
			8, 2,
			12, 2,
			12, 4
		});
	}

	@Test
	public void tomb1ConductBackAndForth() {
		givenSectionPeriods(new int[] { 17, 13, 7, 9, 13 });

		whenExecute();

		// conduct back and forth between sections 2 and 4 while waiting for section 5 to open
		thenAssertPath(new int[] {
			0, 0,
			21, 0,
			21, 3,
			27, 3,
			27, 4,
			35, 4,
			35, 3,
			39, 3,
			39, 2,
			49, 2,
			49, 6
		});
	}

	@Test
	public void tomb2LargeNumbersConductBackAndForth() {
		givenSectionPeriods(new int[] { 170000, 130000, 70003, 90000, 130001 });

		whenExecute();

		// conduct back and forth between sections 2 and 4 while waiting for section 5 to open
		thenAssertPath(new int[] {
			0, 0,
			210009, 0,
			210009, 3,
			270000, 3,
			270000, 4,
			350015, 4,
			350015, 3,
			390000, 3,
			390000, 2,
			490021, 2,
			490021, 6
		});
	}

	@Test
	public void tomb3LongMoves() {
		givenSectionPeriods(new int[] { 4, 22, 16, 4, 7, 19, 1, 7, 6, 26 });

		whenExecute();

		thenAssertPath(new int[] {
			0, 0,
			23, 0,
			23, 6,
			35, 6,
			35, 11
		});
	}

	@Test
	public void tomb4LongWaitAtStart() {
		givenSectionPeriods(new int[] { 72063, 579, 29235, 43690, 88575, 60750, 11051, 86884, 25781, 92781 });

		whenExecute();

		thenAssertPath(new int[] {
			0, 0,
			3310143, 0,
			3310143, 5,
			3364130, 5,
			3364130, 6,
			3392657, 6,
			3392657, 8,
			3432897, 8,
			3432897, 11
		});
	}

}
