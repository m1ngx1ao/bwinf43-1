package excursion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;
import java.util.Iterator;

public class CandidateDistanceProposer {
	private Iterator<Integer> getSortedIterator(List<Integer> list) {
		List<Integer> sortedList = new ArrayList<Integer>(list);
		Collections.sort(sortedList);
		return sortedList.iterator();
	}

	/**
	 * Returns a candidate excursion distance for which at least one member would participate.
	 * 
	 * A naive approach would be take all numbers from min(memberLowerBounds) to max(memberUpperBounds).
	 * 
	 * This is averted by the following:
	 * - only take memberLowerBounds
	 *	 - any participant for memberLowerBounds - epsilon is still in, while a new member would participate
	 *	 - (only taking memberUpperBounds would also be an alternative with a similar rationale)
	 *	 -> leads to dropping m/n of the candidates where
	 *		  - m is the number of candidates in the naive approach
	 *		  - n is the number of members
	 * - drop those memberLowerBound for which the next memberLowerBound (in ascending order) comes before the next
	 *   memberUpperBound
	 *	 - reason is that the dropped memberLowerBound is dominated by that next memberLowerBound
	 *	   because it has more participants
	 *	 -> leads to dropping about half of the candidates
	 */
	public List<Integer> execute(List<Integer> memberLowerBounds, List<Integer> memberUpperBounds) {
		List<Integer> result = new LinkedList<Integer>();
		if (memberLowerBounds.size() == 0 || memberUpperBounds.size() == 0) {
			return result;
		}
		Iterator<Integer> lowerBoundIterator = getSortedIterator(memberLowerBounds);
		Iterator<Integer> upperBoundIterator = getSortedIterator(memberUpperBounds);
		int currentLowerBound = lowerBoundIterator.next();
		int nextUpperBound = upperBoundIterator.next();
		while (lowerBoundIterator.hasNext()) {
			int nextLowerBound = lowerBoundIterator.next();
			// only pick lower bounds if no further lower bound comes after it
			// ensures that only local optima are returned,
			// thus reducing the number of candidates by a factor of about 2
			if (nextLowerBound > nextUpperBound) {
				result.add(currentLowerBound);
				while (nextLowerBound > nextUpperBound) {
					nextUpperBound = upperBoundIterator.next();
				}
			}
			currentLowerBound = nextLowerBound;
		}
		// biggest lower bound always part of the candidates
		result.add(currentLowerBound);
		return result;
	}
}
