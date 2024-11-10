package test.tomb;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import tomb.Corridor;
import tomb.Section;

public class CorridorTest {
	List<Integer> sectionPeriods;
	Corridor cut;
	Section dummySectionBeforeStart;
	Section startSection;
	Section firstSection;
	Section secondSection;
	Section thirdSection;
	Section tombSection;

	@Before
	public void setup() {
		sectionPeriods = new LinkedList<Integer>();
		sectionPeriods.add(5);
		sectionPeriods.add(8);
		sectionPeriods.add(12);
		cut = new Corridor(sectionPeriods);
		dummySectionBeforeStart = cut.getSection(-1);
		startSection = cut.getStartSection();
		firstSection = cut.getSection(1);
		secondSection = cut.getSection(2);
		thirdSection = cut.getSection(3);
		tombSection = cut.getSection(4);
	}

	@Test
	public void sectionIdsOneBased() {
		assertEquals(0, startSection.getPosition());
		assertEquals(4, tombSection.getPosition());
		assertEquals(8, secondSection.getPeriod());
	}

	@Test
	public void neighbors() {
		List<Section> neighbors = cut.getNeighbors(firstSection);

		assertEquals(2, neighbors.size());
		assertTrue(neighbors.contains(startSection));
		assertTrue(neighbors.contains(secondSection));
	}

	@Test
	public void neighborsAtStart() {
		List<Section> neighbors = cut.getNeighbors(startSection);

		assertEquals(2, neighbors.size());
		assertTrue(neighbors.contains(firstSection));
		assertTrue(neighbors.contains(dummySectionBeforeStart));
	}

	@Test
	public void inBetweenSectionsLeftToRight() {
		List<Section> neighbors = cut.getSectionsInBetween(firstSection, tombSection);

		assertEquals(2, neighbors.size());
		assertTrue(neighbors.contains(secondSection));
		assertTrue(neighbors.contains(thirdSection));
	}

	@Test
	public void inBetweenSectionsRightToLeft() {
		List<Section> neighbors = cut.getSectionsInBetween(tombSection, startSection);

		assertEquals(3, neighbors.size());
		assertTrue(neighbors.contains(firstSection));
		assertTrue(neighbors.contains(secondSection));
		assertTrue(neighbors.contains(thirdSection));
	}

	@Test
	public void inBetweenSectionsNeighbors() {
		List<Section> neighbors = cut.getSectionsInBetween(tombSection, thirdSection);

		assertEquals(0, neighbors.size());
	}

	@Test
	public void inBetweenSectionsOneSectionToItself() {
		List<Section> neighbors = cut.getSectionsInBetween(secondSection, secondSection);

		assertEquals(0, neighbors.size());
	}
}
