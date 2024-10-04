package excursion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputRunner {
	public static void main(String[] args) throws IOException {
		new File("outputs").mkdirs();
		ExcursionOrganizer excursionOrganizer = new ExcursionOrganizer();
		for (int i = 1; i <= 7; i++) {
			BufferedReader r = new BufferedReader(new FileReader(new File("inputs/wandern" + i + ".txt")));
			int memberCount = Integer.valueOf(r.readLine());
			List<Integer> memberLowerBounds = new ArrayList<Integer>(memberCount);
			List<Integer> memberUpperBounds = new ArrayList<Integer>(memberCount);
			for (int mIdx = 0; mIdx < memberCount; mIdx++) {
				String[] boundStrings = r.readLine().split(" ");
				memberLowerBounds.add(Integer.valueOf(boundStrings[0]));
				memberUpperBounds.add(Integer.valueOf(boundStrings[1]));
			}
			r.close();
			System.out.println("Starting excursion number " + i + "...");
			long startTime = System.currentTimeMillis();
			ExcursionResult excursionResult = excursionOrganizer.execute(memberLowerBounds, memberUpperBounds);
			long elapsedTimeInMs = System.currentTimeMillis() - startTime;
			FileWriter fw = new FileWriter("outputs/wandern" + i + ".txt");
			fw.write(excursionResult.toString());
			fw.flush();
			fw.close();
			System.out.println("   ... done in " + elapsedTimeInMs + " ms!");
		}
	}

}
