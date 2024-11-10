package test.tomb;

import org.junit.Test;

import static org.junit.Assert.*;

import tomb.Section;

public class SectionTest {
	Section cut;

	@Test
	public void sectionOpenAtTime() {
		cut = new Section(1, 12);

		assertFalse(cut.isOpenAtTime(11 + 24));
		assertTrue(cut.isOpenAtTime(12 + 24));
		assertTrue(cut.isOpenAtTime(23 + 24));
		assertFalse(cut.isOpenAtTime(24 + 24));
	}

	@Test
	public void sectionOpenBetweenTimes() {
		cut = new Section(1, 5);

		assertFalse(cut.isOpenBetweenTimes(14, 16));
		assertTrue(cut.isOpenBetweenTimes(15, 16));
		assertTrue(cut.isOpenBetweenTimes(16, 19));
		assertFalse(cut.isOpenBetweenTimes(16, 20));
		assertFalse(cut.isOpenBetweenTimes(19, 26));
	}

	@Test
	public void sectionNextOpenTime() {
		cut = new Section(1, 8);

		assertEquals(8 + 16, cut.getNextOpenTime(4 + 16));
		assertEquals(8 + 48, cut.getNextOpenTime(8 + 32));
		assertEquals(8 + 32, cut.getNextOpenTime(0 + 32));
		assertEquals(8 + 32, cut.getNextOpenTime(15 + 16));
	}

	@Test
	public void sectionNextCloseTime() {
		cut = new Section(1, 8);

		assertEquals(32, cut.getNextCloseTime(4 + 16));
		assertEquals(48, cut.getNextCloseTime(8 + 32));
		assertEquals(48, cut.getNextCloseTime(0 + 32));
		assertEquals(32, cut.getNextCloseTime(15 + 16));
	}

	@Test
	public void alwaysOpenSectionNextTimes() {
		cut = new Section(0, true);

		int nextOpenTime = cut.getNextOpenTime(13);
		int nextCloseTime = cut.getNextOpenTime(18);

		assertTrue(nextOpenTime > 100000);
		assertTrue(nextCloseTime > 100000);
	}
}
