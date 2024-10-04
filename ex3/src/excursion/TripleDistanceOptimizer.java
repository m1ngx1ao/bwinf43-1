package excursion;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class TripleDistanceOptimizer {

	public ExcursionResult execute(Map<Integer, Set<Integer>> participantsByCandidateDistances) {
		ExcursionResult result = new ExcursionResult();
		result.participantsByDistance = new HashMap<Integer, Set<Integer>>();
		result.overallParticipants = new HashSet<Integer>();

		List<Integer> candidatesList = new ArrayList<Integer>(participantsByCandidateDistances.keySet());
		if (candidatesList.size() <= 3) {
			result.participantsByDistance = participantsByCandidateDistances;
			for (Set<Integer> participantsByCandidate: participantsByCandidateDistances.values()) {
				result.overallParticipants.addAll(participantsByCandidate);
			}
			return result;
		}

		for (int indexA = 0; indexA < candidatesList.size(); indexA++) {
			int a = candidatesList.get(indexA);
			Set<Integer> aParticipants = participantsByCandidateDistances.get(a);

			for (int indexB = indexA + 1; indexB < candidatesList.size(); indexB++) {
				int b = candidatesList.get(indexB);
				Set<Integer> bParticipants = participantsByCandidateDistances.get(b);
				Set<Integer> abParticipants = new HashSet<Integer>(aParticipants);
				abParticipants.addAll(bParticipants);

				for (int indexC = indexB + 1; indexC < candidatesList.size(); indexC++) {
					int c = candidatesList.get(indexC);
					Set<Integer> cParticipants = participantsByCandidateDistances.get(c);
					Set<Integer> abcParticipants = new HashSet<Integer>(abParticipants);
					abcParticipants.addAll(cParticipants);
	
					if (abcParticipants.size() > result.overallParticipants.size()) {
						result.overallParticipants = abcParticipants;
						result.participantsByDistance.clear();
						result.participantsByDistance.put(a, aParticipants);
						result.participantsByDistance.put(b, bParticipants);
						result.participantsByDistance.put(c, cParticipants);
					}
				}
			}
		}
		return result;
	}
}
