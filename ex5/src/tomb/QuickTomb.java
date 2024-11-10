package tomb;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class QuickTomb {
	private Corridor corridor;

	public QuickTomb(Corridor corridor) {
		this.corridor = corridor;
	}

	/**
	 * Implements Dijkstra, using statuses both as ordering and as a means to keep
	 * track of the path taken.
	 * 
	 * By ordering the statuses according to their time (and the step count as tie-breaker),
	 * it is guaranteed that the current status can be returned as best solution - any other solution
	 * would need at least the same amount of time.
	 */
	public Status execute() {
		Set<Status> seen = new HashSet<Status>();
		PriorityQueue<Status> todo = new PriorityQueue<Status>();
		todo.add(new Status(null, corridor.getStartSection(), 0));
		while (!todo.isEmpty()) {
			Status current = todo.poll();
			if (current.getSection().equals(corridor.getTombSection())) {
				return current;
			}
			if (!seen.contains(current)) {
				seen.add(current);
				todo.addAll(getNextForMove(current));
				todo.addAll(getNextForWait(current));
			}
		}
		return null;
	}
	
	/**
	 * Subsequent status is at the same time (movement assumed to take no time)
	 * but on a section to the right or left
	 * (going left is also necessary in case going back and forth is on the quickest path)
	 * 
	 * The previous status of next statuses is not necessary the current status.
	 * If the current status was also obtained by a move, then we can shortcut
	 * the current status's previous status and the next status. By doing so,
	 * we enable moves beyond one section at a time.
	 */
	private List<Status> getNextForMove(Status current) {
		List<Status> result = new LinkedList<Status>();
		Section curSection = current.getSection();
		int curTime = current.getTime();
		Status previous = current.wasLastStepMove() ? current.getPrevious(): current;
		for (Section s : corridor.getNeighbors(curSection)) {
			if (s.isOpenAtTime(curTime)) {
				result.add(new Status(previous, s, curTime));
			}
		}
		return result;
	}
	
	/**
	 * Subsequent status is at the same section
	 * but at a time when any neighboring section becomes available
	 * (i.e., its cuboid does not block any more).
	 * 
	 * It is important not to expand all subsequent statuses greedily because
	 * there are infinitely many of them.
	 * 
	 * The previous status of next statuses is not necessary the current status.
	 * If the current status was also obtained by a wait, then we can shortcut
	 * the current status's previous status and the next status. By doing so,
	 * we enable waits beyond one time event.
	 * 
	 * Note that no recursive consolidation is needed
	 * as previous statuses have already been consolidated.
	 */
	private List<Status> getNextForWait(Status current) {
		List<Status> result = new LinkedList<Status>();
		Section curSection = current.getSection();
		int curTime = current.getTime();
		List<Integer> nextOpenTimes = getEarliestNextValidTimesNeighborsOpen(curSection, curTime);
		if (nextOpenTimes.isEmpty()) {
			// no further wait possible
			return result;
		}
		int earliestNextTime = Collections.min(nextOpenTimes);
		// by default: current status is previous of next statuses
		Status previous = current;
		Status shortcut = getNewShortcutReplacingWaitMoveWait(current, earliestNextTime);
		if (shortcut != null) {
			// wait-move-wait can be simplified to wait-move => consolidate using a new shortcut status as previous
			previous = shortcut;
		} else if (current.wasLastStepWait()) {
			// two waits in a row => shortcut previous with next
			previous = current.getPrevious();
		}
		result.add(new Status(previous, curSection, earliestNextTime));
		return result;
	}

	/**
	 * Looking at the neighboring sections, the question is when they will open next.
	 * Only return those (valid) openings that occur before the current section closes.
	 */
	private List<Integer> getEarliestNextValidTimesNeighborsOpen(Section s, int time) {
		List<Integer> result = new LinkedList<Integer>();
		for(Section neighbor : corridor.getNeighbors(s)) {
			int t = neighbor.getNextOpenTime(time);
			if (t < s.getNextCloseTime(time)) {
				result.add(t);
			}
		}
		return result;
	}

	/*
	 * try the following consolidation:
	 *
	 * a           a
	 * *           *
	 * b*c    ==>  *
	 *   *         *
	 *   *         n**
	 *
	 * where:
	 *   c = current
	 *   b = beforeMove
	 *   a = aBeforeWaitMove
	 * 	 n = newLongWait (returned if applicable)
	 */
	private Status getNewShortcutReplacingWaitMoveWait(Status current, int nextTime) {
		Status previous = current.getPrevious();
		if (previous == null || !previous.wasLastStepWait() || !current.wasLastStepMove()) {
			return null;
		}
		Section previousSection = previous.getSection();
		if (!previousSection.isOpenBetweenTimes(current.getTime(), nextTime)) {
			return null;
		}
		for (Section s: corridor.getSectionsInBetween(previousSection, current.getSection())) {
			if (!s.isOpenAtTime(nextTime)) {
				return null;
			}
		}
		return new Status(previous.getPrevious(), previousSection, nextTime);
	}
}
