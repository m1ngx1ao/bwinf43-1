package excursion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

public class ParticipantsByDistanceEvaluator {
	public Map<Integer, Set<Integer>> execute(
		List<Integer> memberLowerBounds, List<Integer> memberUpperBounds, List<Integer> distances
	) {
		Map<Integer, Set<Integer>> result = new HashMap<Integer, Set<Integer>>();
		for (int distance : distances) {
			Set<Integer> participants = new HashSet<Integer>();
			for (int memberIdx = 0; memberIdx < memberLowerBounds.size(); memberIdx++) {
				if (memberLowerBounds.get(memberIdx) <= distance && distance <= memberUpperBounds.get(memberIdx)) {
					participants.add(memberIdx);
				}
			}
			result.put(distance, participants);
		}
		return result;
	}
}
