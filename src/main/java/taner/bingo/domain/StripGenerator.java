package taner.bingo.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class StripGenerator {
  private Iterator<int[][]> validTicketLayoutsIterator;

  public StripGenerator(Iterator<int[][]> validTicketLayoutsIterator) {
    this.validTicketLayoutsIterator = validTicketLayoutsIterator;
  }

  private void sortTicketColumns(int[][][] strip) {
    for (int i = 0; i < 6; ++i) {
      for (int j = 0; j < 9; ++j) {
        if (0 == strip[i][0][j]) {
          if (0 == strip[i][1][j] || 0 == strip[i][2][j]) {
            continue;
          }

          if (strip[i][1][j] > strip[i][2][j]) {
            int temp = strip[i][2][j];

            strip[i][2][j] = strip[i][1][j];
            strip[i][1][j] = temp;
          }

          continue;
        }

        if (0 == strip[i][1][j]) {
          if (0 == strip[i][2][j]) {
            continue;
          }

          if (strip[i][0][j] > strip[i][2][j]) {
            int temp = strip[i][2][j];

            strip[i][2][j] = strip[i][0][j];
            strip[i][0][j] = temp;
          }

          continue;
        }

        if (0 == strip[i][2][j]) {
          if (strip[i][0][j] > strip[i][1][j]) {
            int temp = strip[i][1][j];

            strip[i][1][j] = strip[i][0][j];
            strip[i][0][j] = temp;
          }

          continue;
        }

        if (strip[i][0][j] > strip[i][1][j]) {
          if (strip[i][0][j] > strip[i][2][j]) {
            if (strip[i][1][j] > strip[i][2][j]) {
              int temp = strip[i][2][j];

              strip[i][2][j] = strip[i][0][j];
              strip[i][0][j] = temp;
            } else {
              int temp = strip[i][1][j];

              strip[i][1][j] = strip[i][0][j];
              strip[i][0][j] = temp;

              temp = strip[i][2][j];

              strip[i][2][j] = strip[i][1][j];
              strip[i][1][j] = temp;
            }
          } else {
            int temp = strip[i][1][j];

            strip[i][1][j] = strip[i][0][j];
            strip[i][0][j] = temp;
          }
        } else {
          if (strip[i][1][j] > strip[i][2][j]) {
            if (strip[i][0][j] > strip[i][2][j]) {
              int temp = strip[i][2][j];

              strip[i][2][j] = strip[i][1][j];
              strip[i][1][j] = temp;

              temp = strip[i][1][j];

              strip[i][1][j] = strip[i][0][j];
              strip[i][0][j] = temp;
            } else {
              int temp = strip[i][2][j];

              strip[i][2][j] = strip[i][1][j];
              strip[i][1][j] = temp;
            }
          }
        }
      }
    }
  }

  private List<Integer> findRemainingColumns(int[] columnCounts) {
    List<Integer> remainingColumns = new ArrayList<>();
    for (int i = 0; i < 9; ++i) {
      if (0 < columnCounts[i]) {
        remainingColumns.add(i);
      }
    }
    return remainingColumns;
  }

  private int[][] constructTicketLayout(int[] columnCounts) {
    int[][] ticketLayout = new int[3][5];

    Set<Integer> consumedColumns = new HashSet<>();
    int ticketLayout0Index = 0;
    for (int i = 0; i < columnCounts.length; ++i) {
      if (3 == columnCounts[i]) {
        ticketLayout[0][ticketLayout0Index++] = i;

        --columnCounts[i];

        consumedColumns.add(i);
      }
    }

    if (5 > ticketLayout0Index) {
      List<Integer> remainingColumns = findRemainingColumns(columnCounts);

      Collections.shuffle(remainingColumns);

      Iterator<Integer> remainingColumnsIterator = remainingColumns.iterator();

      while (5 > ticketLayout0Index) {
        int column = remainingColumnsIterator.next();

        if (consumedColumns.contains(column)) {
          continue;
        }

        ticketLayout[0][ticketLayout0Index++] = column;

        --columnCounts[column];
      }
    }

    Arrays.sort(ticketLayout[0]);

    consumedColumns = new HashSet<>();
    int ticketLayout1Index = 0;
    for (int i = 0; i < columnCounts.length; ++i) {
      if (2 == columnCounts[i]) {
        ticketLayout[1][ticketLayout1Index++] = i;

        --columnCounts[i];

        consumedColumns.add(i);
      }
    }

    if (5 > ticketLayout1Index) {
      List<Integer> remainingColumns = findRemainingColumns(columnCounts);

      Collections.shuffle(remainingColumns);

      Iterator<Integer> remainingColumnsIterator = remainingColumns.iterator();

      while (5 > ticketLayout1Index) {
        int column = remainingColumnsIterator.next();

        if (consumedColumns.contains(column)) {
          continue;
        }

        ticketLayout[1][ticketLayout1Index++] = column;

        --columnCounts[column];
      }
    }

    Arrays.sort(ticketLayout[1]);

    List<Integer> remainingColumns = findRemainingColumns(columnCounts);
    Iterator<Integer> remainingColumnsIterator = remainingColumns.iterator();

    for (int i = 0; i < 5; ++i) {
      ticketLayout[2][i] = remainingColumnsIterator.next();

      --columnCounts[ticketLayout[2][i]];
    }

    return ticketLayout;
  }

  private int[][][] generateStrip(int[][][] stripLayout) {
    int[][][] strip = new int[6][3][9];
    NumberSource numberSource = new NumberSource();

    for (int i = 0; i < 6; ++i) {
      int[][] ticketLayout = stripLayout[i];
      for (int j = 0; j < 3; ++j) {
        int[] rowLayout = ticketLayout[j];
        int rowLayoutIndex = 0;
        for (int k = 0; k < 9; ++k) {
          if (5 == rowLayoutIndex) {
            continue;
          }

          if (k == rowLayout[rowLayoutIndex]) {
            strip[i][j][k] = numberSource.next(rowLayout[rowLayoutIndex]);

            ++rowLayoutIndex;
          }
        }
      }
    }

    return strip;
  }

  private int[] findColumnCounts(int[][][] strip) {
    int[] columnCounts = new int[9];
    for (int i = 0; i < 6; ++i) {
      if (null == strip[i]) {
        continue;
      }

      for (int j = 0; j < 3; ++j) {
        for (int k = 0; k < 5; ++k) {
          ++columnCounts[strip[i][j][k]];
        }
      }
    }
    return columnCounts;
  }

  public List<int[][][]> generate(int stripCount) {
    List<int[][][]> stripLayoutList = new ArrayList<>();

    for (int stripIndex = 0; stripIndex < stripCount; ++stripIndex) {
      int[][][] stripLayout = new int[6][][];

      stripLayout[0] = validTicketLayoutsIterator.next();

      boolean columnCountsExceedLimits = false;
      do {
        stripLayout[1] = validTicketLayoutsIterator.next();

        int[] columnCounts = findColumnCounts(stripLayout);

        columnCountsExceedLimits = 5 < columnCounts[0];
      } while (columnCountsExceedLimits);

      do {
        stripLayout[2] = validTicketLayoutsIterator.next();

        int[] columnCounts = findColumnCounts(stripLayout);

        columnCountsExceedLimits = 6 < columnCounts[0];

        if (!columnCountsExceedLimits) {
          for (int i = 1; i < 8; ++i) {
            columnCountsExceedLimits = 7 < columnCounts[i];

            if (columnCountsExceedLimits) {
              break;
            }
          }
        }

        if (!columnCountsExceedLimits) {
          columnCountsExceedLimits = 8 < columnCounts[8];
        }
      } while (columnCountsExceedLimits);

      do {
        stripLayout[3] = validTicketLayoutsIterator.next();

        int[] columnCounts = findColumnCounts(stripLayout);

        columnCountsExceedLimits = 7 < columnCounts[0];

        if (!columnCountsExceedLimits) {
          for (int i = 1; i < 8; ++i) {
            columnCountsExceedLimits = 8 < columnCounts[i];

            if (columnCountsExceedLimits) {
              break;
            }
          }
        }

        if (!columnCountsExceedLimits) {
          columnCountsExceedLimits = 9 < columnCounts[8] || columnCounts[8] < 5;
        }
      } while (columnCountsExceedLimits);

      do {
        stripLayout[4] = validTicketLayoutsIterator.next();

        int[] columnCounts = findColumnCounts(stripLayout);

        columnCountsExceedLimits = 8 < columnCounts[0] || columnCounts[0] < 6;

        if (!columnCountsExceedLimits) {
          for (int i = 1; i < 8; ++i) {
            columnCountsExceedLimits = 9 < columnCounts[i] || columnCounts[i] < 7;

            if (columnCountsExceedLimits) {
              break;
            }
          }
        }

        if (!columnCountsExceedLimits) {
          columnCountsExceedLimits = 10 < columnCounts[8] || columnCounts[8] < 8;
        }
      } while (columnCountsExceedLimits);

      int[] columnCounts = findColumnCounts(stripLayout);

      columnCounts[0] = 9 - columnCounts[0];

      for (int i = 1; i < 8; ++i) {
        columnCounts[i] = 10 - columnCounts[i];
      }

      columnCounts[8] = 11 - columnCounts[8];

      stripLayout[5] = constructTicketLayout(columnCounts);

      stripLayoutList.add(stripLayout);
    }

    List<int[][][]> stripList = Collections.synchronizedList(new ArrayList<>(stripCount));
    stripLayoutList.parallelStream().forEach(stripLayout -> {
      int[][][] strip = generateStrip(stripLayout);

      sortTicketColumns(strip);

      stripList.add(strip);
    });

    return stripList;
  }
}
