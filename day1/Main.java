import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;

public class Main {
	final static String FILE = "./input.txt";
	final static int START_POSITION = 50;

	public static void main(String[] args) {
		List<Integer> inputList = readInput(FILE);
		int challengeOneAnswer = getChallengeOneAnswer(inputList);
		int challengeTwoAnswer = getChallengeTwoAnswer(inputList);
		System.out.println("challenge one answer: " + challengeOneAnswer);
		System.out.println("Challenge two answer: " + challengeTwoAnswer);
	}

	static List<Integer> readInput(String file) {
		List<Integer> inputList = new ArrayList<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				if ('R' == line.charAt(0)) {
					int number = Integer.parseInt(line.substring(1));
					inputList.add(number);
				} else {
					int number = Integer.parseInt(line.substring(1)) * -1;
					inputList.add(number);
				}
			}
			reader.close();
		} catch (Exception e) {
			System.out.println("Error while parsing from file");
		}

		return inputList;
	}

	static int getChallengeOneAnswer(List<Integer> inputList) {
		int currentPosition = START_POSITION;
		int numOfPasses = 0;

		for (Integer move : inputList) {
			currentPosition = getNewPosition(currentPosition, move);

			if (currentPosition == 0) {
				numOfPasses++;
			}
		}

		return numOfPasses;
	}

	static int getChallengeTwoAnswer(List<Integer> inputList) {
		int passesPerTurn = 0;
		int currentPosition = START_POSITION;
		int newPosition = currentPosition;
		for (Integer move : inputList) {
			newPosition = getNewPosition(currentPosition, move);

			if (move < 0) {
				for (int i = 0; i > move; i--) {
					if ((currentPosition + i) % 100 == 0) {
						passesPerTurn++;
					}
				}
			} else {
				for (int i = 0; i < move; i++) {
					if ((currentPosition + i) % 100 == 0) {
						passesPerTurn++;
					}
				}

			}

			currentPosition = newPosition;
		}

		return passesPerTurn;
	}

	static int getNewPosition(int currentPosition, int move) {
		int newPosition = currentPosition;
		newPosition += move;

		if (newPosition > 100) {
			newPosition = newPosition % 100;
		} else if (newPosition < 0) {
			newPosition = (newPosition + 1000000) % 100;
		} else if (newPosition == 100) {
			newPosition = 0;
		}

		return newPosition;
	}
}
