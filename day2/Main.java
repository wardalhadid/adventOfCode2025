import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.BufferedReader;

public class Main {
	static final String FIRST_ID = "firstId";
	static final String LAST_ID = "lastId";
	static final String FILE_NAME = "./input.txt";

	public static void main(String[] args) {
		List<Map<String, Long>> inputList = getEntriesFromFile(FILE_NAME);
		long challengeOneAnswer = getChallengeOneAnswer(inputList);
		long challengeTwoAnswer = getChallengeTwoAnswer(inputList);

		System.out.println("Challenge one answer: " + challengeOneAnswer);
		System.out.println("Challenge two answer: " + challengeTwoAnswer);
	}

	static Long getChallengeOneAnswer(List<Map<String, Long>> inputList) {
		long challengeOneAnswer = 0;
		for (Map<String, Long> entry : inputList) {
			long firstId = entry.get(FIRST_ID);
			long lastId = entry.get(LAST_ID);
			for (long i = firstId; i <= lastId; i++) {
				boolean isIdValid = checkInvalidPattern(i);
				if (isIdValid)
					continue;
				challengeOneAnswer += i;
			}
		}
		return challengeOneAnswer;
	}

	static Long getChallengeTwoAnswer(List<Map<String, Long>> inputList) {
		long challengeTwoAnswer = 0;
		for (Map<String, Long> entry : inputList) {
			long firstId = entry.get(FIRST_ID);
			long lastId = entry.get(LAST_ID);
			for (long i = firstId; i <= lastId; i++) {
				boolean isIdValid = checkAdditionalInvalidPattern(i);
				if (isIdValid)
					continue;
				challengeTwoAnswer += i;
			}
		}
		return challengeTwoAnswer;
	}

	static boolean checkAdditionalInvalidPattern(long id) {
		boolean isValid = true;

		String idString = String.valueOf(id);
		int length = idString.length();

		int i = 2;

		while (i <= length) {
			if (length % i == 0) {
				int step = length / i;
				int lastIndex = 0;
				List<String> subNumList = new ArrayList<>();
				for (int j = step; j <= length; j += step) {
					subNumList.add(idString.substring(lastIndex, j));
					lastIndex = j;
				}
				Set<String> uniqueSubNumSet = new HashSet<>(subNumList);
				if (uniqueSubNumSet.size() == 1) {
					return false;
				}
			}

			i++;
		}

		return isValid;
	}

	static boolean checkInvalidPattern(long id) {
		boolean isValid = true;
		if (id == 101L)
			return true;
		String idString = String.valueOf(id);
		int length = idString.length();
		if (length % 2 == 0 && idString.substring(0, length / 2)
				.equals(idString.substring(length / 2, length)))
			return false;
		return isValid;
	}

	static List<Map<String, Long>> getEntriesFromFile(String fileName) {
		List<Map<String, Long>> inputList = new ArrayList<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] ids = line.split(",");
				for (String idRanges : ids) {
					Map<String, Long> entries = new HashMap<>();
					String[] entry = idRanges.split("-");
					if (null == entry)
						continue;
					entries.put(FIRST_ID, Long.parseLong(entry[0]));
					entries.put(LAST_ID, Long.parseLong(entry[1]));
					inputList.add(entries);
				}
			}
			reader.close();
		} catch (Exception e) {
			System.out.println("Error reading from file");
		}
		return inputList;
	}
}
