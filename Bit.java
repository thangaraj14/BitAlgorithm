import java.util.HashMap;
import java.util.Scanner;

public class Bit implements Constants {
	static int sum = 0;
	public static HashMap<String, String> map = new HashMap<String, String>();

	static int cells, tempCells = 0;
	static int count = 0;
	static int[] numArray;
	static int square;
	static int[] formatArray = { 0, 1, 2, 3, 4, 9, 10, 11, 5, 6, 7, 12, 8, 13, 14, 15 };

	public static void main(String[] args) {
		loadMap();
		String input = "";
		do {
			System.out.println("Please enter valid binary digits");
			Scanner scan = new Scanner(System.in);
			input = scan.next();
		} while (!validateInput(input));

		printPyramid(input);
		transformBinary(input);
	}

	private static void printPyramid(String input) {
		if (cells != 4)
			return;
		StringBuffer sb = reverseArray(input);
		int[] print = convertIntArray(sb + "");
		StringBuffer printSb = new StringBuffer();
		for (int i = 0; i < formatArray.length; i++) {
			printSb.append(print[formatArray[i]]);
		}

		generateMatrix(cells, printSb + "");
	}

	private static StringBuffer reverseArray(String input) {
		int start = 0, end = 4;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < input.length() / 4; i++) {
			String temp = input.substring(start, end);
			sb.append(new StringBuffer(temp).reverse().toString());
			start += 4;
			end += 4;
		}
		return sb;
	}

	private static void transformBinary(String input) {
		if (checksum(convertIntArray(input)) == 0) {
			generateMatrix(cells, input);
			generateMatrix(1, input);
			return;
		}
		if (checksum(convertIntArray(input)) == square) {
			generateMatrix(cells, input);
			generateMatrix(1, input);
			return;
		}
		StringBuffer sb = new StringBuffer();
		int start = 0, end = 4;
		generateMatrix(cells, input);
		for (int i = 0; i < numArray.length / 4; i++) {
			String strMap = input.substring(start, end);
			sb.append(getMap(strMap));
		}
		transformBinary(sb + "");
	}

	private static int checksum(int[] intArr) {
		int sum = 0;
		for (int i = 0; i < intArr.length; i++) {
			sum += intArr[i];
		}
		return sum;
	}

	private static boolean validateInput(String input) {
		double value = input.length();
		numArray = convertIntArray(input);
		for (int i = 0; i < numArray.length; i++) {
			if (numArray[i] != 0 && numArray[i] != 1) {
				return false;
			}
		}
		return validateCell(value);
	}

	private static int[] convertIntArray(String input) {
		int[] numArray = new int[input.length()];
		for (int i = 0; i < input.length(); i++) {
			numArray[i] = input.charAt(i) - '0';
		}
		return numArray;
	}

	private static boolean validateCell(double value) {
		for (int i = 2; i <= (int) Math.sqrt(value); i += 2) {
			if (Math.pow(i, 2) == value) {
				cells = tempCells = i;
				square = (int) Math.pow(i, 2);
				return true;
			}
		}
		return false;
	}

	private static void generateMatrix(int input, String inputString) {

		StringBuffer sb = reverseArray(inputString);
		numArray = convertIntArray(sb + "");
		sum = calculateTopcap(input);
		for (int j = 0; j < sum; j++) {
			System.out.print(" ");
		}
		System.out.print(CAP + "\n");

		for (int j = 0; j < input; j++) {
			reduceSum();
			sideLines(j + 1, sum, numArray);
			reduceSum();
			bottomLine(j + 1, sum);
		}
		reset();
	}

	private static void reset() {
		increaseSum();
		count = 0;
	}

	private static void increaseSum() {
		cells = tempCells;
	}

	private static void reduceSum() {
		sum--;
	}

	private static void sideLines(int n, int sum, int[] numArray) {
		for (int i = 0; i < sum; i++) {
			System.out.print(" ");
		}
		for (int i = 0; i < n; i++) {
			System.out.print(FRONTSIDELINE);
			printBinaryDigits(numArray);
			System.out.print(BACKSIDELINE);
			int check = i + 1;
			if (check < n) {
				printBinaryDigits(numArray);
			}
		}
		System.out.println();
	}

	private static void printBinaryDigits(int[] numArray) {
		System.out.print(numArray[count++]);
	}

	private static void bottomLine(int n, int sum) {
		for (int i = 0; i < sum; i++) {
			System.out.print(" ");
		}
		for (int i = 0; i < n; i++) {
			System.out.print(STAR);
			System.out.print(BOTTOMLINE);
		}
		System.out.print(STAR + "\n");
	}

	public static int calculateTopcap(int input) {
		int sum = 4;
		double doo = input - 1;
		sum = (int) (sum + (2 * (Math.pow(2, doo) - 1)));
		return sum;
	}

	public static void loadMap() {
		map.put("0000", "0000");
		map.put("0001", "1000");
		map.put("0010", "0001");
		map.put("0011", "0010");
		map.put("0100", "0000");
		map.put("0101", "0010");
		map.put("0110", "1011");
		map.put("0111", "1011");
		map.put("1000", "0100");
		map.put("1001", "0101");
		map.put("1010", "0111");
		map.put("1011", "1111");
		map.put("1100", "1101");
		map.put("1101", "1110");
		map.put("1110", "0111");
		map.put("1111", "1111");
	}

	public static String getMap(String key) {
		if (map.containsKey(key)) {
			return map.get(key);
		}
		return key;
	}
}
