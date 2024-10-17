package difficulty;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;

public class InputRunner {
	public static void main(String[] args) throws IOException {
		new File("outputs").mkdirs();
		OrderOptimizer orderOptimizer = new OrderOptimizer();
		for (int i = 0; i <= 5; i++) {
			BufferedReader r = new BufferedReader(new FileReader(new File("inputs/schwierigkeiten" + i + ".txt")));
			String[] splitLine = r.readLine().split(" ");
			int chainCount = Integer.parseInt(splitLine[0]);
			int exerciseCount = Integer.parseInt(splitLine[1]);
			List<String> exerciseIds = new ArrayList<String>(exerciseCount);
			for (int eIdx = 0; eIdx < exerciseCount; eIdx++) {
				exerciseIds.add(Character.toString((char)(eIdx + 65)));
			}
			List<List<String>> chains = new ArrayList<List<String>>(chainCount);
			for (int cIdx = 0; cIdx < chainCount; cIdx++) {
				splitLine = r.readLine().split(" ");
				List<String> chain = new LinkedList<String>();
				for (int eIdx = 0; eIdx < splitLine.length; eIdx += 2) {
					chain.add(splitLine[eIdx]);
				}
				chains.add(chain);
			}
			splitLine = r.readLine().split(" ");
			List<String> requestedExerciseIds = Arrays.asList(splitLine);
			r.close();
			System.out.println("Starting challenge " + i + "...");
			long startTime = System.currentTimeMillis();
			Solution resultOrder = orderOptimizer.execute(exerciseIds, chains, requestedExerciseIds);
			long elapsedTimeInMs = System.currentTimeMillis() - startTime;
			FileWriter fw = new FileWriter("outputs/schwierigkeiten" + i + ".txt");
			fw.write(resultOrder.toString());
			fw.flush();
			fw.close();
			System.out.println("   ... done in " + elapsedTimeInMs + " ms: optimized order contains "
				+ resultOrder.getViolations().size() + " violations!");
		}
	}

}
