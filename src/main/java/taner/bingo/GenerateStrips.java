package taner.bingo;
import java.util.List;

import taner.bingo.domain.StripGenerator;
import taner.bingo.domain.TicketLayoutGenerator;

public class GenerateStrips {
  // How many times DEFAULT_GENERATED_STRIP_COUNT_IN_EACH_RUN count of unique ticket strips will be generated.
  private static final int DEFAULT_TOTAL_RUNS = 100;

  // Since we generate strips with unique tickets, we have to keep this number low or we may run out of tickets.
  private static final int DEFAULT_GENERATED_STRIP_COUNT_IN_EACH_RUN = 100;

  private static void printStrip(int[][][] strip) {
    System.out.println("+--------------------------+");

    for (int i = 0; i < 6; ++i) {
      for (int j = 0; j < 3; ++j) {
        for (int k = 0; k < 9; ++k) {
          if (0 == strip[i][j][k]) {
            System.out.print("|  ");
          } else {
            System.out.printf("|%2d", strip[i][j][k]);
          }
        }
        System.out.println("|");
      }

      System.out.println("+--------------------------+");
    }
  }

  public static void main(String[] args) {
    int totalRuns;
    if (0 < args.length) {
      try {
        totalRuns = Integer.parseInt(args[0]);

        if (0 > totalRuns) {
          totalRuns = DEFAULT_TOTAL_RUNS;
        }
      } catch (NumberFormatException e) {
        totalRuns = DEFAULT_TOTAL_RUNS;
      }
    } else {
      totalRuns = DEFAULT_TOTAL_RUNS;
    }

    long start = System.currentTimeMillis();
    long elapsed = 0L;
    for (int run = 0; run < totalRuns; ++run) {
      // When generating tickets in bulk, validTicketLayouts may also be pre-generated and read through a file.
      StripGenerator stripGenerator = new StripGenerator(TicketLayoutGenerator.generate());

      // Since we generate strips with unique tickets, we have to keep this number low or we may run out of tickets
      List<int[][][]> stripList = stripGenerator.generate(DEFAULT_GENERATED_STRIP_COUNT_IN_EACH_RUN);

      elapsed += System.currentTimeMillis() - start;

      for (int[][][] strip : stripList) {
        printStrip(strip);

        System.out.println();
      }

      start = System.currentTimeMillis();
    }

    System.out.println(DEFAULT_GENERATED_STRIP_COUNT_IN_EACH_RUN * totalRuns + " strips generated in: " + elapsed + "ms");
  }
}
