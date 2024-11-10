package tomb;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A Status captures a position (=section) and moment in time of the player in the corridor.
 * In addition, the previous status is referrenced in order to be able to
 * reproduce the complete path that the player has traversed.
 * 
 * The priority of a status is tied to the time - the lower the time, the higher its priority.
 * This is required in order to be able to put statuses into priority queues and evaluating them
 * in the order of ascending time.
 * As tie-breaker, there is the step count (trying to minimize the list of instructions). Here,
 * step means a move or a wait.
 * 
 * For holding statuses in the seen set, the equals and hashCode methods are overridden.
 * For this purpose, statuses are considered to be the same (equal) if they hold the same position (=section) and
 * time. The history how the player arrived there (priorStatus) as well as the step number is irrelevant
 * (from the perspective of the seen set).
 */
public class Status implements Comparable<Status> {
	private Status previous;
	private Section section;
	private int time;
	private int stepCount;

	public Status(Status previous, Section section, int time) {
		this.previous = previous;
		this.section = section;
		this.time = time;
		stepCount = (previous == null) ? 0 : this.previous.stepCount + 1;
	}

	// GETTERS

	public Status getPrevious() {
		return previous;
	}

	public Section getSection() {
		return section;
	}

	public int getTime() {
		return time;
	}

	public int getStepCount() {
		return stepCount;
	}

	// OVERRIDES

	@Override
	public int compareTo(Status other) {
		// lower time comes before (= is smaller than) higher time
		if (time != other.time) {
			return time - other.time;
		}
		// lower step count comes before (= is smaller than) higher step count
		return stepCount - other.stepCount;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Status) {
			Status other = (Status) o;
			return other.time == time && section.equals(other.section);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Integer.valueOf(time).hashCode() ^ section.hashCode();
	}

	@Override
	public String toString() {
		return "[ " + time + " / " + section.getPosition() + " ] ";
	}

	// METHODS

	public boolean wasLastStepMove() {
		return previous != null && !section.equals(previous.getSection());
	}

	public boolean wasLastStepWait() {
		return previous != null && previous.time < time;
	}

	public List<Status> getFullPath() {
		LinkedList<Status> result = new LinkedList<Status>();
		Status current = this;
		while (current != null) {
			result.addFirst(current);
			current = current.previous;
		}
		return result;
	}

	public String fullPathToString() {
		String result = "Path [ time / section ]:";
		Iterator<Status> it = getFullPath().iterator();
		Status current = it.next();
		while (it.hasNext()) {
			Status next = it.next();
			result += "\n" + current + " ";
			if (next.wasLastStepWait()) {
				result += "Wait for " + getSingularPlural(next.getTime() - current.getTime(), "minute") + ".";
			} else if (next.wasLastStepMove()) {
				int dp = next.getSection().getPosition() - current.getSection().getPosition();
				result += "Move " + getSingularPlural(Math.abs(dp), "section")
					+ " to the " + (dp < 0 ? "left" : "right") + ".";
			} else {
				throw new RuntimeException();
			}
			current = next;
		}
		result += "\n" + current + " ARRIVED!";
		return result;
	}

	private String getSingularPlural(int number, String word) {
		return "" + number + " " + word + (number == 1 ? "" : "s");
	}
}
