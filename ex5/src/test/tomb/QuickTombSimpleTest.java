package test.tomb;

import org.junit.Test;

public class QuickTombSimpleTest extends QuickTombBasis {
	@Test
	public void noSectionsDirectMoveToTomb() {
		whenExecute();

		thenAssertPath(new int[] {
			0, 0,
			0, 1
		});
	}

	@Test
	public void oneSectionConsolidateMovesToOne() {
		givenSectionPeriods(new int[] { 3 });

		whenExecute();

		thenAssertPath(new int[] {
			0, 0,
			3, 0,
			3, 2
		});
	}

	@Test
	public void twoSectionsAvoidGreedyBackAndForth() {
		givenSectionPeriods(new int[] { 1, 3 });

		whenExecute();

		// do not move back and forth between sections 0 and 1 while waiting for section 2 to open
		thenAssertPath(new int[] {
			0, 0,
			3, 0,
			3, 3
		});
	}
}
