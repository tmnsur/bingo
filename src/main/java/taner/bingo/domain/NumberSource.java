package taner.bingo.domain;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class NumberSource {
  private int[][] columnNumbers = new int[][] {
    {1,2,3,4,5,6,7,8,9},
    {10,11,12,13,14,15,16,17,18,19},
    {20,21,22,23,24,25,26,27,28,29},
    {30,31,32,33,34,35,36,37,38,39},
    {40,41,42,43,44,45,46,47,48,49},
    {50,51,52,53,54,55,56,57,58,59},
    {60,61,62,63,64,65,66,67,68,69},
    {70,71,72,73,74,75,76,77,78,79},
    {80,81,82,83,84,85,86,87,88,89,90}
  };

  private int[] columnNumbersIndices = new int[] {
    0,0,0,0,0,0,0,0,0
  };

  public NumberSource() {
    Arrays.stream(this.columnNumbers).parallel().forEach(NumberSource::shuffle);
  }

  private static void shuffle(int[] numbers) {
    Random random = ThreadLocalRandom.current();

    for (int i = numbers.length - 1; -1 != i; --i) {
      int index = random.nextInt(i + 1);
      int temp = numbers[index];

      numbers[index] = numbers[i];
      numbers[i] = temp;
    }
  }

  public int next(int columnIndex) {
    return this.columnNumbers[columnIndex][this.columnNumbersIndices[columnIndex]++];
  }
}
