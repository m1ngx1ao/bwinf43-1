package excursion;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExcursionOrganizer {
	private CandidateDistanceProposer distanceCandidateProposer;
	private ParticipantsByDistanceEvaluator participantsByDistanceEvaluator;
	private TripleDistanceOptimizer tripleDistanceOptimizer;

	public ExcursionOrganizer() {
		distanceCandidateProposer = new CandidateDistanceProposer();
		participantsByDistanceEvaluator = new ParticipantsByDistanceEvaluator();
		tripleDistanceOptimizer = new TripleDistanceOptimizer();
	}

	public ExcursionResult execute(List<Integer> memberLowerBounds, List<Integer> memberUpperBounds) {
		List<Integer> distanceCandidates = distanceCandidateProposer.execute(memberLowerBounds, memberUpperBounds);
		Map<Integer, Set<Integer>> participantsByDistanceCandidates = participantsByDistanceEvaluator.execute(
			memberLowerBounds, memberUpperBounds, distanceCandidates);
		ExcursionResult result = tripleDistanceOptimizer.execute(participantsByDistanceCandidates);
		return result;
	}
}
