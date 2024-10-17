package difficulty;

import java.util.List;
import java.util.Set;

public class OrderOptimizer {
	public Solution execute(List<String> exerciseIds,
			List<List<String>> chainsOfAscendingDifficulty, List<String> requestedExerciseIds) {
		InequalityGraph graph = new InequalityGraph(exerciseIds, chainsOfAscendingDifficulty);
		removeExercisesNotRequested(graph, requestedExerciseIds);
		return sortTopologicallyAndProvideSolution(graph);
	}
	
	private void removeExercisesNotRequested(InequalityGraph graph, List<String> requestedExerciseIds) {
		Set<Exercise> exercisesToBeKept = graph.getExercisesByIds(requestedExerciseIds);
		for (Exercise ex : graph.getExercisesExcept(exercisesToBeKept)) {
			graph.removeAndShortcutAdjacencies(ex);
		}
	}

	/**
	 * Sorts the graph topologically and adds constraints incl. violations to the result.
	 * 
	 * The graph will be empty after this method.
	 */
	private Solution sortTopologicallyAndProvideSolution(InequalityGraph graph) {
		Solution result = new Solution();
		while (!graph.getExercises().isEmpty()) {
			Exercise easiest = graph.getEasiest();
			result.appendToOrder(easiest);
			for (Exercise easierThanEasiest : easiest.getEasier().getFlatList()) {
				result.addViolation(easierThanEasiest, easiest);
				result.addConstraint(easierThanEasiest, easiest);
			}
			for (Exercise harderThanEasiest : easiest.getHarder().getFlatList()) {
				result.addConstraint(easiest, harderThanEasiest);
			}
			graph.remove(easiest);
		}
		return result;
	}
}
