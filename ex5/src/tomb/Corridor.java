package tomb;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.lang.Math;

/**
 * A corridor consists of n sections, each of which can be open or blocked (by the cuboid) at a certain time.
 * 
 * The sections are referred to by their numbers starting from 1, so the last one is n.
 * This is done to accommodate the starting position (section 0 - does not have cuboid)
 * and the destination (the tomb at section n + 1).
 * 
 * Further sections are added at -1 in order to avert checks whether moves lead out of bounds.
 */
public class Corridor {
	private Map<Integer, Section> sectionByPosition;
	private int sectionCountBetweenStartAndTomb;

	public Corridor(List<Integer> sectionPeriods) {
		sectionByPosition = new HashMap<Integer, Section>();
		int sectionsSize = sectionPeriods.size();
		sectionCountBetweenStartAndTomb = sectionsSize;
		sectionByPosition.put(-1, new Section(-1, false));
		sectionByPosition.put(0, new Section(0, true));
		sectionByPosition.put(sectionsSize + 1, new Section(sectionsSize + 1, true));
		for (int sectionPos = 1; sectionPos <= sectionsSize; sectionPos++) {
			Section s = new Section(sectionPos, sectionPeriods.get(sectionPos - 1));
			sectionByPosition.put(sectionPos, s);
		}
	}

	private int getStartPosition() {
		return 0;
	}

	private int getTombPosition() {
		return sectionCountBetweenStartAndTomb + 1;
	}

	public Section getSection(int position) {
		return sectionByPosition.get(position);
	}

	public Section getStartSection() {
		return sectionByPosition.get(getStartPosition());
	}

	public Section getTombSection() {
		return sectionByPosition.get(getTombPosition());
	}

	public List<Section> getNeighbors(Section s) {
		List<Section> result = new LinkedList<Section>();
		for (int dp : new int[] {-1, 1}) {
			result.add(getSection(s.getPosition() + dp));
		}
		return result;
	}
	
	public List<Section> getSectionsInBetween(Section a, Section b) {
		List<Section> result = new LinkedList<Section>();
		int positionA = a.getPosition();
		int positionB = b.getPosition();
		int dp = (int) Math.signum(positionB - positionA);
		for (int p = positionA + dp; p != positionB; p += dp) {
			result.add(getSection(p));
		}
		return result;
	}
}
