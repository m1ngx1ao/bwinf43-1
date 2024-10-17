package difficulty;

import java.util.List;

import java.util.LinkedList;

public class Solution {
	private List<Exercise> order;
	private MultiSet<InequalityPair> violations;
	private MultiSet<InequalityPair> constraints;

	public Solution() {
		order = new LinkedList<Exercise>();
		violations = new MultiSet<InequalityPair>();
		constraints = new MultiSet<InequalityPair>();
	}

	// GETTERS

	public List<Exercise> getOrder() {
		return order;
	}

	public MultiSet<InequalityPair> getViolations() {
		return violations;
	}

	public MultiSet<InequalityPair> getConstraints() {
		return constraints;
	}

	// OVERRIDES

	@Override
	public String toString() {
		String result = "Order of requested exercises:\n";
		for (Exercise exercise : order) {
			result += exercise + " ";
		}
		result += "\n\nThe following inequalities are multi-sets and derived"
			+ "\nfrom shortcutting exercises that were not requested.";
		result += "\n\n" + getPairsToString(violations, "Violations");
		result += "\n\n" + getPairsToString(constraints, "Constraints");
		return result;
	}

	// METHODS

	private String getPairsToString(MultiSet<InequalityPair> pairs, String title) {
		if (pairs.size() == 0) {
			return title + ": none";
		}
		return title + ": " + pairs.getSortedUniqueElements().size() + " unique ones,"
			+ " total " + pairs.size() + pairs.toString();
	}

	public void appendToOrder(Exercise e) {
		order.add(e);
	}
	
	public void addViolation(Exercise easier, Exercise harder) {
		violations.add(new InequalityPair(easier, harder));
	}
	
	public void addConstraint(Exercise easier, Exercise harder) {
		constraints.add(new InequalityPair(easier, harder));
	}
}
