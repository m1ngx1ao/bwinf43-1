package excursion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ExcursionResult {
	public Map<Integer, Set<Integer>> participantsByDistance;
	public Set<Integer> overallParticipants;

	private String setToSortedString(Set<Integer> numberSet) {
		List<Integer> numberList = new ArrayList<Integer>(numberSet);
		Collections.sort(numberList);
		String result = "";
		for (int n : numberList) {
			result += "\n" + n;
		}
		return result;
	}

	@Override
	public String toString() {
		String result = "Excursion distances:";
		result += setToSortedString(participantsByDistance.keySet());
		result += "\n\nIndexes of the " + overallParticipants.size() + " members who participate in any of the distances:";
		result += setToSortedString(overallParticipants);
		for (Entry<Integer, Set<Integer>> ppdEntry : participantsByDistance.entrySet()) {
			result += "\n\nIndexes of the members who participate in excursion of the distance " + ppdEntry.getKey() + ":";
			result += setToSortedString(ppdEntry.getValue());
		}
		return result;
	}
}
