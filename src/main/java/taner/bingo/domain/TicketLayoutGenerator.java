package taner.bingo.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class TicketLayoutGenerator {
  // When generating tickets in bulk, ticketLayouts may also be pre-generated and read through a file.
  private static final List<int[][]> ticketLayouts = generateTicketLayouts(generateRowLayouts());

  private TicketLayoutGenerator() {
    // No Instantiations
  }

  private static List<int[]> generateRowLayouts() {
    List<int[]> rowLayoutList = new ArrayList<>();
    int[] rowLayout = new int[] {0,1,2,3,4};

    do {
      rowLayoutList.add(rowLayout.clone());

      if (9 == ++rowLayout[4]) {
        rowLayout[4] = rowLayout[3] + 2;

        if (8 == ++rowLayout[3]) {
          rowLayout[3] = rowLayout[2] + 2;
          rowLayout[4] = rowLayout[3] + 1;

          if (7 == ++rowLayout[2]) {
            rowLayout[2] = rowLayout[1] + 2;
            rowLayout[3] = rowLayout[2] + 1;
            rowLayout[4] = rowLayout[3] + 1;

            if (6 == ++rowLayout[1]) {
              rowLayout[1] = rowLayout[0] + 2;
              rowLayout[2] = rowLayout[1] + 1;
              rowLayout[3] = rowLayout[2] + 1;
              rowLayout[4] = rowLayout[3] + 1;

              if (5 == ++rowLayout[0]) {
                break;
              }
            }
          }
        }
      }
    } while (true);

    return rowLayoutList;
  }

  private static boolean isValidTicketLayout(int[][] stripLayout) {
    int[] columns = new int[9];

    for (int i = 0; i < 3; ++i) {
      for (int j = 0; j < 5; ++j) {
        columns[stripLayout[i][j]] = 1;
      }
    }

    for (int i = 0; i < 9; ++i) {
      if (0 == columns[i]) {
        return false;
      }
    }

    return true;
  }

  private static List<int[][]> generateTicketLayouts(List<int[]> rowLayoutList) {
    int[] rows = new int[] {0,1,2};
    List<int[][]> ticketLayoutList = new ArrayList<>();

    do {
      int[][] stripLayout = new int[][] {
        rowLayoutList.get(rows[0]),
        rowLayoutList.get(rows[1]),
        rowLayoutList.get(rows[2])
      };

      if (isValidTicketLayout(stripLayout)) {
        ticketLayoutList.add(stripLayout);
      }

      if (126 == ++rows[2]) {
        rows[2] = rows[1] + 2;

        if (125 == ++rows[1]) {
          rows[1] = rows[0] + 2;
          rows[2] = rows[1] + 1;

          if (124 == ++rows[0]) {
            break;
          }
        }
      }
    } while (true);

    return ticketLayoutList;
  }

  public static Iterator<int[][]> generate() {
    List<int[][]> shuffledTicketLayouts = new ArrayList<>(TicketLayoutGenerator.ticketLayouts.size());

    for (int[][] ticketLayout : TicketLayoutGenerator.ticketLayouts) {
      shuffledTicketLayouts.add(ticketLayout.clone());
    }

    Collections.shuffle(shuffledTicketLayouts);

    return shuffledTicketLayouts.iterator();
  }
}
