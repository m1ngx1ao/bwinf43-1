package test.tomb;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

import tomb.Section;
import tomb.Status;

public class StatusTest {

	@Test
	public void sortedOrderAccordingToPriority() {
		Status z = new Status(null, new Section(5, 1), 3);
		Status y = new Status(z, new Section(6, 1), 3);
		Status x = new Status(null, new Section(6, 1), 4);
		List<Status> statuses = Arrays.asList(x, y, z);

		Collections.sort(statuses);

		// best time and best step count (=0)
		assertEquals(z, statuses.get(0));
		// best time, step count 1
		assertEquals(y, statuses.get(1));
		// worst time, step count 0 does not matter
		assertEquals(x, statuses.get(2));
	}

	@Test
	public void equalsAndHashcodeIgnoreStepHistory() {
		Status x = new Status(null, new Section(5, 1), 3);
		Status y = new Status(x, new Section(5, 1), 3);
		Status z = new Status(null, new Section(6, 1), 3);
		Status w = new Status(null, new Section(5, 1), 4);

		assertEquals(x, y);
		assertEquals(x.hashCode(), y.hashCode());
		assertNotEquals(x, z);
		assertNotEquals(x.hashCode(), z.hashCode());
		assertNotEquals(x, w);
		assertNotEquals(x.hashCode(), w.hashCode());
	}

	@Test
	public void identifyStepTypeMoveVsWait() {
		Status x = new Status(null, new Section(5, 1), 3);
		Status y = new Status(x, new Section(5, 1), 4);
		Status z = new Status(x, new Section(6, 1), 3);

		assertFalse(x.wasLastStepMove());
		assertFalse(x.wasLastStepWait());
		assertTrue(y.wasLastStepWait());
		assertFalse(y.wasLastStepMove());
		assertFalse(z.wasLastStepWait());
		assertTrue(z.wasLastStepMove());
	}
}
