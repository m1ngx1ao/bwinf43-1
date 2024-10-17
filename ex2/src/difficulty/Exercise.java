package difficulty;

/**
 * A vertex of the inequality graph.
 * 
 * An exercise A might be called easier than exercise B more than one time.
 * As a result, the incoming/outgoing edges of the inequality graph are multi-sets.
 */
public class Exercise implements Comparable<Exercise> {
	private String id;
	private MultiSet<Exercise> easierExercises;
	private MultiSet<Exercise> harderExercises;

	public Exercise(String id) {
		this.id = id;
		easierExercises = new MultiSet<Exercise>();
		harderExercises = new MultiSet<Exercise>();
	}

	// GETTERS

	public String getId() {
		return id;
	}
	
	public MultiSet<Exercise> getEasier() {
		return easierExercises;
	}

	public MultiSet<Exercise> getHarder() {
		return harderExercises;
	}

	// OVERRIDES

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Exercise) {
			Exercise e = (Exercise) o;
			return id.equals(e.id);
		}
		return false;
	}

	@Override
	public String toString() {
		return id;
	}

	@Override
	public int compareTo(Exercise e) {
		return id.compareTo(e.id);
	}

	// METHODS

	public void addEasier(Exercise e) {
		easierExercises.add(e);
	}

	public void addHarder(Exercise e) {
		harderExercises.add(e);
	}

	public void removeEasier(Exercise e) {
		easierExercises.remove(e);
	}

	public void removeHarder(Exercise e) {
		harderExercises.remove(e);
	}
}