package difficulty;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

public class InequalityGraph {
	private Set<Exercise> exercises;
	private Map<String, Exercise> idToExercise;

	public InequalityGraph(List<String> exerciseIds, List<List<String>> chainsOfAscendingDifficulty) {
		exercises = new HashSet<Exercise>();
		idToExercise = new HashMap<String, Exercise>();
		for (String excerciseId : exerciseIds) {
			Exercise ex = new Exercise(excerciseId);
			exercises.add(ex);
			idToExercise.put(excerciseId, ex);
		}
		for (List<String> chainOfAscendingDifficulty : chainsOfAscendingDifficulty) {
			Iterator<String> chainIt = chainOfAscendingDifficulty.iterator();
			Exercise current = idToExercise.get(chainIt.next());
			while (chainIt.hasNext()) {
				Exercise next = idToExercise.get(chainIt.next());
				current.addHarder(next);
				next.addEasier(current);
				current = next;
			}
		}
	}

	public Set<Exercise> getExercises() {
		return exercises;
	}

	public Set<Exercise> getExercisesExcept(Set<Exercise> exceptions) {
		Set<Exercise> result = new HashSet<Exercise>();
		for (Exercise e : exercises) {
			if (!exceptions.contains(e)) {
				result.add(e);
			}
		}
		return result;
	}

	public Exercise getExerciseById(String exerciseId) {
		return idToExercise.get(exerciseId);
	}

	public Set<Exercise> getExercisesByIds(List<String> exerciseId) {
		Set<Exercise> result = new HashSet<Exercise>(exerciseId.size());
		for (String id : exerciseId) {
			result.add(idToExercise.get(id));
		}
		return result;
	}

	/**
	 * Removes the exercise from the graph. All edges to and from the exercise are removed.
	 */
	public void remove(Exercise e) {
		for (Exercise easierExercise : e.getEasier().getFlatList()) {
			easierExercise.removeHarder(e);
		}
		for (Exercise harderExercise : e.getHarder().getFlatList()) {
			harderExercise.removeEasier(e);
		}
		idToExercise.remove(e.getId());
		exercises.remove(e);
	}

	/**
	 * Removes the exercise Y from the graph.
	 * 
	 * The adjacency shortcuts are done as follows:
	 * For all edges (X, Y), (X, Z) is added for all Z with (Y, Z)
	 */
	public void removeAndShortcutAdjacencies(Exercise e) {
		for (Exercise easierExercise : e.getEasier().getFlatList()) {
			for (Exercise harderExercise : e.getHarder().getFlatList()) {
				if (!easierExercise.equals(harderExercise)) {
					easierExercise.addHarder(harderExercise);
					harderExercise.addEasier(easierExercise);
				}
			}
		}
		// remove e and its adjacencies in any case (even if harder or easier is empty)
		remove(e);
	}

	/**
	 * The exercise (vertex) with the fewest incoming edges.
	 * As a tie-breaker, the exercise with the most outgoing edges is returned
	 * (desirable for efficiently un-blocking other exercises when removing the easiest).
	 * Further Ties are resolved lexicographically in order to enable repeatable outputs.
	 * 
	 * Note that this comparison between exercises is not based on the exercise's id.
	 * No push-down to exercises comparteTo is possible because lexical order of ids is
	 * already defined there and also needed.
	 * 
	 * @return null if no exercises are present
	 */
	public Exercise getEasiest() {
		if (exercises.isEmpty()) {
			return null;
		}
		Iterator<Exercise> exIt = exercises.iterator();
		Exercise result = exIt.next();
		while (exIt.hasNext()) {
			Exercise exercise = exIt.next();
			int candidateEasierSize = exercise.getEasier().getFlatList().size();
			int resultEasierSize = result.getEasier().getFlatList().size();
			int candidateHarderSize = exercise.getHarder().getFlatList().size();
			int resultHarderSize = result.getHarder().getFlatList().size();
			if (candidateEasierSize < resultEasierSize || candidateEasierSize == resultEasierSize && (
				candidateHarderSize > resultHarderSize || candidateHarderSize == resultHarderSize &&
					exercise.compareTo(result) < 0
			)) {
				result = exercise;
			}
		}
		return result;
	}
}
