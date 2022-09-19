package taner.bingo.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class TestStripGenerator {
  @Test
  void test() {
    StripGenerator stripGenerator = new StripGenerator(TicketLayoutGenerator.generate());

    List<int[][][]> stripList = stripGenerator.generate(100);

    for (int[][][] strip : stripList) {
      assertEquals(6, strip.length);

      int[] numbers = new int[90];

      for (int i = 0; i < 6; ++i) {
        assertEquals(3, strip[i].length);

        int numbersInTicket = 0;
        int[] numbersInColumns = new int[9];
        for (int j = 0; j < 3; ++j) {
          assertEquals(9, strip[i][j].length);

          int numbersInRow = 0;

          for (int k = 0; k < 9; ++k) {
            if (0 < strip[i][j][k]) {
              assertEquals(0, numbers[strip[i][j][k] - 1]);

              ++numbersInRow;
              ++numbersInColumns[k];
              ++numbersInTicket;
            }
          }

          assertEquals(5, numbersInRow);
        }

        for (int k = 0; k < 9; ++k) {
          assertTrue(0 < numbersInColumns[k] && numbersInColumns[k] < 4);
        }

        assertEquals(15, numbersInTicket);
      }
    }
  }
}
