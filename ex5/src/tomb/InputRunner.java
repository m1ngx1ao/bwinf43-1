package tomb;

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
		for (int i = 0; i <= 5; i++) {
			BufferedReader r = new BufferedReader(new FileReader(new File("inputs/grabmal" + i + ".txt")));
			int sectionCount = Integer.valueOf(r.readLine());
			List<Integer> sectionPeriods = new ArrayList<Integer>(sectionCount);
			for (int section = 0; section < sectionCount; section++) {
				sectionPeriods.add(Integer.valueOf(r.readLine()));
			}
			r.close();
			QuickTomb quickTomb = new QuickTomb(new Corridor(sectionPeriods));
			System.out.println("Starting tomb number " + i + "...");
			long startTime = System.currentTimeMillis();
			Status finalStatus = quickTomb.execute();
			long elapsedTimeInMs = System.currentTimeMillis() - startTime;
			FileWriter fw = new FileWriter("outputs/grabmal" + i + ".txt");
			fw.write(finalStatus.fullPathToString());
			fw.flush();
			fw.close();
			System.out.println("   ... done in " + elapsedTimeInMs + " ms: Optimal time "
				+ finalStatus.getTime() + " minutes after " + finalStatus.getStepCount() + " instructions!");
		}
	}

}
