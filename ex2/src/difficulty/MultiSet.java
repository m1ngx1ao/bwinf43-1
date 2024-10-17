package difficulty;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

/**
 * A MultiSet is a set that allows duplicate elements.
 * 
 * Own definition of this class here as there are no standard multi-sets in Java.
 * 
 * The full name would be MultiHashSet, but MultiSet is used the corresponding interface
 * does not need to be defined for such narrow scope.
 */
public class MultiSet<T extends Comparable<? super T>> {
	Map<T, Integer> map;

	public MultiSet() {
		map = new HashMap<T, Integer>();
	}

	// BASIC OPERATIONS

	/**
	 * Each element is counted as many times as it is in the MultiSet.
	 */
	public int size() {
		int size = 0;
		for (int count : map.values()) {
			size += count;
		}
		return size;
	}

	public int getCountByElement(T e) {
		return map.getOrDefault(e, 0);
	}

	public void add(T e) {
		if (map.containsKey(e)) {
			map.put(e, map.get(e) + 1);
		} else {
			map.put(e, 1);
		}
	}

	public void remove(T e) {
		if (map.containsKey(e)) {
			int count = map.get(e);
			if (count == 1) {
				map.remove(e);
			} else {
				map.put(e, count - 1);
			}
		}
	}

	// ADVANCED OPERATIONS

	public List<T> getSortedUniqueElements() {
		List<T> result = new ArrayList<T>(map.size());
		for (T key : map.keySet()) {
			result.add(key);
		}
		Collections.sort(result);
		return result;
	}

	/**
	 * flat refers to a list where each element is repeated as many times as it is in the MultiSet.
	 */
	public List<T> getFlatList() {
		List<T> result = new LinkedList<T>();
		for (Map.Entry<T, Integer> entry : map.entrySet()) {
			for (int i = 0; i < entry.getValue(); i++) {
				result.add(entry.getKey());
			}
		}
		return result;
	}

	@Override
	public String toString() {
		String result = "";
		for (T e : getSortedUniqueElements()) {
			int eCount = getCountByElement(e);
			result += "\n" + e + (eCount > 1 ? " (" + eCount + ")" : "");
		};
		return result;
	}
}
