package tomb;

public class Section {
	private int position;
	private int period;
	private boolean isAlwaysOpen;
	private boolean isAlwaysClosed;

	public Section(int position, int period) {
		this.position = position;
		this.period = period;
		this.isAlwaysOpen = false;
		this.isAlwaysClosed = false;
	}
	
	public Section(int position, boolean isAlwaysOpen) {
		this.position = position;
		this.period = 0;
		this.isAlwaysOpen = isAlwaysOpen;
		this.isAlwaysClosed = !isAlwaysOpen;
	}

	// GETTERS

	public int getPosition() {
		return position;
	}

	public int getPeriod() {
		return period;
	}

	// OVERRIDES

	@Override
	public int hashCode() {
		return Integer.valueOf(position).hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Section) {
			Section other = (Section) o;
			return position == other.position;
		}
		return false;
	}

	// METHODS

	/**
	 * Each section is closed for the duration of its period, then
	 * it is open for exactly the same amount of time.
	 * This cycle then repeats.
	 * Note that it is assumed that the cuboid blocks or is removed
	 * instantly at the start of the period.
	 * So, for a period of 3, the time the section is open is
	 * [3, 6) and then [9, 12) and so on
	 */
	public boolean isOpenAtTime(int time) {
		if (isAlwaysOpen) {
			return isAlwaysOpen;
		}
		if (isAlwaysClosed) {
			return !isAlwaysClosed;
		}
		return time % (2 * period) >= period;
	}

	public boolean isOpenBetweenTimes(int timeFrom, int timeToInclusively) {
		return isOpenAtTime(timeFrom) && timeToInclusively < getNextCloseTime(timeFrom);
	}

	public int getNextCloseTime(int time) {
		if (isAlwaysOpen || isAlwaysClosed) {
			return Integer.MAX_VALUE;
		}
		return time - time % (2 * period) + 2 * period;
	}
	
	public int getNextOpenTime(int time) {
		return getNextCloseTime(time + period) - period;
	}
}