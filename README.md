Bingo 90 Strip Generator implemented in Java.

## Implementation Notes
This piece of software aims to generate random and unique Bingo90 Strip streams. There are three main parts inside the implementation:

### 1. Ticket Layout Generation
In order to make sure the strip generation output has no repeating tickets, this implementation first generates all possible valid ticket layouts and stores them in a list. This list then shuffled and provided as an input to the next component, the Strip Layout Generator. A ticket layout is valid if and only if:

- Each ticket consists of 9 columns and 3 rows.
- Each ticket row contains five numbers and four blank spaces
- Each ticket column consists of one, two or three numbers and never three blanks.

Example ticket layout:
  +--------------------------+
  | x| x|  | x| x| x|  |  |  |
  |  | x| x| x| x| x|  |  |  |
  |  | x|  | x|  |  | x| x| x|
  +--------------------------+

#### Algorithm:

1. Calculate all combinations of 5 cells selections out of 9.
2. Randomly choose 3 of them. If no other combinations are left then goto step 4.
3. Check if ticket layout rules listed above are OK. If so, add the ticket layout into the valid ticket layouts list and goto step 2, if not goto step 2.
4. Shuffle the ticket layouts list and return it to the Strip Layout Generator.

### 2. Strip Layout Generation
Fetches random ticket layouts supplied by the Ticket Layout Generator. Every time it receives a random ticket layout, it first checkes if the strip layout still valid. A strip layout is valid if and only if it is possible to distribute all Bingo90 numbers (1-90) to all six ticket layouts while keeping the strip structure rules valid. These rules are:

- Each ticket column has rules for the range of numbers it can contain. A strip layout is valid if it allows a valid number distribution among its tickets. Number distribution rules for each ticket is as follows:
-- The first column contains numbers from 1 to 9 (only nine),
-- The second column numbers from 10 to 19 (ten), the third, 20 to 29 and so on up until
-- The last column, which contains numbers from 80 to 90 (eleven).
-- Numbers in the ticket columns are ordered from top to bottom (ASC).
- There can be no duplicate numbers between 1 and 90 in the strip (since you generate 6 tickets with 15 numbers each).
- All Bingo90 numbers (1-90) must be present in a strip.

### 3. Number Distribution
After successful creation and validation of a strip, Number Source distributes Bingo90 numbers (1-90) into the contained ticket cells that are marked as number holders in the ticket layout.

## How to run
Please execute in terminal:

```console
./mvnw compile exec:java
```

in Linux/Unix

or

```console
mvnw.cmd compile exec:java
```

in Windows to run bingo
