package difficulty;

public class InequalityPair implements Comparable<InequalityPair> {
	private Exercise easier;
	private Exercise harder;

	public InequalityPair(Exercise easier, Exercise harder) {
		this.easier = easier;
		this.harder = harder;
	}

	@Override
	public int hashCode() {
		return easier.hashCode() ^ harder.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof InequalityPair) {
			InequalityPair other = (InequalityPair) o;
			return easier.equals(other.easier) && harder.equals(other.harder);
		}
		return false;
	}

	@Override
	public String toString() {
		return easier + " < " + harder;
	}

	@Override
	public int compareTo(InequalityPair p) {
		int result = easier.compareTo(p.easier);
		if (result == 0) {
			result = harder.compareTo(p.harder);
		}
		return result;
	}
}
