package test.tomb;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;

import tomb.Corridor;
import tomb.QuickTomb;
import tomb.Status;

public class QuickTombBasis {
	QuickTomb cut;
	List<Integer> sectionPeriods;
	Status lastStep;
	List<Status> path;

	@Before
	public void setup() {
		sectionPeriods = new LinkedList<Integer>();
	}

	protected void givenSectionPeriods(int[] sectionPeriods) {
		for (int period : sectionPeriods) {
			this.sectionPeriods.add(period);
		}
	}

	protected void whenExecute() {
		cut = new QuickTomb(new Corridor(sectionPeriods));
		lastStep = cut.execute();
		path = lastStep.getFullPath();
	}

	protected void thenAssertPath(int[] expectedPath) {
		assertEquals(expectedPath.length, path.size() * 2);
		for (int i = 0; i < path.size(); i++) {
			assertEquals(expectedPath[i * 2], path.get(i).getTime());
			assertEquals(expectedPath[i * 2 + 1], path.get(i).getSection().getPosition());
		}
	}
}
