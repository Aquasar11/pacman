package sample.model.game;

import java.util.Random;

public class Map {

    public Map() {
        createMaze();
        removeSomeWalls();
        addBomb();
        addPacmanAndGhostsPosition();
    }

    private void addPacmanAndGhostsPosition() {
        //add pacman to the center
        maze[10][10] = String.valueOf('3');
        //make sure that pacman can enter the game
        maze[10][11] = String.valueOf('0');
        maze[10][9] = String.valueOf('0');
        //add ghosts in the corners
        maze[1][1] = String.valueOf('4');
        maze[1][19] = String.valueOf('5');
        maze[19][1] = String.valueOf('6');
        maze[19][19] = String.valueOf('7');
    }

    private final static int mazeRows = 10;
    private final static int mazeColumns = 10;

    public static int getMazeRows() {
        return mazeRows;
    }

    public static int getMazeColumns() {
        return mazeColumns;
    }

    private void addBomb() {
        int counter = 0;
        while (counter < 5) {
            Random random = new Random();
            int randomCellRow = random.nextInt(mazeRows * 2 - 2) + 1;
            int randomCellColumn = random.nextInt(mazeColumns * 2 - 2) + 1;
            if (maze[randomCellRow][randomCellColumn].equals("0")) {
                maze[randomCellRow][randomCellColumn] = String.valueOf('2');
                counter += 1;
            }
        }
    }

    private void removeSomeWalls() {
        int counter = 0;
        while (counter < 60) {
            Random random = new Random();
            int randomCellRow = random.nextInt(mazeRows * 2 - 2) + 1;
            int randomCellColumn = random.nextInt(mazeColumns * 2 - 2) + 1;
            if (maze[randomCellRow][randomCellColumn].equals("1")) {
                maze[randomCellRow][randomCellColumn] = String.valueOf('0');
                counter += 1;
            }
        }
    }

    private String[][] maze;

    public String[][] getMaze() {
        return maze;
    }

    public void showMaze() {
        StringBuilder mazeRow = new StringBuilder();
        for (int i = 0; i < (mazeRows * 2) + 1; i++) {
            for (int j = 0; j < (mazeColumns * 2) + 1; j++) {
                mazeRow.append(maze[i][j]);
            }
            System.out.println(mazeRow);
            mazeRow.delete(0, mazeRow.length());
        }
    }

    private void createMaze() {
        maze = new String[mazeRows * 2 + 1][mazeColumns * 2 + 1];
        for (int i = 0; i < (mazeRows * 2) + 1; i++) {
            for (int j = 0; j < (mazeColumns * 2) + 1; j++) {
                if (i % 2 == 1 && j % 2 == 1)
                    maze[i][j] = String.valueOf('#');
                else
                    maze[i][j] = String.valueOf('1');
            }
        }
        Random random = new Random();
        int randomCellRow = random.nextInt(mazeRows);
        int randomCellColumn = random.nextInt(mazeColumns);
        initialize(mazeRows, mazeColumns, randomCellRow, randomCellColumn);
    }

    private void initialize(int mazeRows, int mazeColumns, int randomCellRow, int randomCellColumn) {
        maze[randomCellRow * 2 + 1][randomCellColumn * 2 + 1] = String.valueOf('*');
        Random random = new Random();
        int randomNeighbor = random.nextInt(4);
        switch (randomNeighbor) {
            case 0:
                firstNeighborCases(mazeRows, mazeColumns, randomCellRow, randomCellColumn);
                break;
            case 1:
                secondNeighborCases(mazeRows, mazeColumns, randomCellRow, randomCellColumn);
                break;
            case 2:
                thirdNeighborCases(mazeRows, mazeColumns, randomCellRow, randomCellColumn);
                break;
            case 3:
                fourthNeighborCases(mazeRows, mazeColumns, randomCellRow, randomCellColumn);
                break;
        }
    }

    private void firstNeighborCases(int mazeRows, int mazeColumns, int randomCellRow, int randomCellColumn) {
        if (isValid(mazeRows, mazeColumns, randomCellRow + 1, randomCellColumn)) {
            maze[randomCellRow * 2 + 2][randomCellColumn * 2 + 1] = String.valueOf('0');
            initialize(mazeRows, mazeColumns, randomCellRow + 1, randomCellColumn);
        }
        if (isValid(mazeRows, mazeColumns, randomCellRow - 1, randomCellColumn)) {
            maze[randomCellRow * 2][randomCellColumn * 2 + 1] = String.valueOf('0');
            initialize(mazeRows, mazeColumns, randomCellRow - 1, randomCellColumn);
        }
        if (isValid(mazeRows, mazeColumns, randomCellRow, randomCellColumn + 1)) {
            maze[randomCellRow * 2 + 1][randomCellColumn * 2 + 2] = String.valueOf('0');
            initialize(mazeRows, mazeColumns, randomCellRow, randomCellColumn + 1);
        }
        if (isValid(mazeRows, mazeColumns, randomCellRow, randomCellColumn - 1)) {
            maze[randomCellRow * 2 + 1][randomCellColumn * 2] = String.valueOf('0');
            initialize(mazeRows, mazeColumns, randomCellRow, randomCellColumn - 1);
        }
    }

    private void secondNeighborCases(int mazeRows, int mazeColumns, int randomCellRow, int randomCellColumn) {
        if (isValid(mazeRows, mazeColumns, randomCellRow - 1, randomCellColumn)) {
            maze[randomCellRow * 2][randomCellColumn * 2 + 1] = String.valueOf('0');
            initialize(mazeRows, mazeColumns, randomCellRow - 1, randomCellColumn);
        }
        if (isValid(mazeRows, mazeColumns, randomCellRow, randomCellColumn + 1)) {
            maze[randomCellRow * 2 + 1][randomCellColumn * 2 + 2] = String.valueOf('0');
            initialize(mazeRows, mazeColumns, randomCellRow, randomCellColumn + 1);
        }
        if (isValid(mazeRows, mazeColumns, randomCellRow, randomCellColumn - 1)) {
            maze[randomCellRow * 2 + 1][randomCellColumn * 2] = String.valueOf('0');
            initialize(mazeRows, mazeColumns, randomCellRow, randomCellColumn - 1);
        }
        if (isValid(mazeRows, mazeColumns, randomCellRow + 1, randomCellColumn)) {
            maze[randomCellRow * 2 + 2][randomCellColumn * 2 + 1] = String.valueOf('0');
            initialize(mazeRows, mazeColumns, randomCellRow + 1, randomCellColumn);
        }
    }

    private void thirdNeighborCases(int mazeRows, int mazeColumns, int randomCellRow, int randomCellColumn) {
        if (isValid(mazeRows, mazeColumns, randomCellRow, randomCellColumn + 1)) {
            maze[randomCellRow * 2 + 1][randomCellColumn * 2 + 2] = String.valueOf('0');
            initialize(mazeRows, mazeColumns, randomCellRow, randomCellColumn + 1);
        }
        if (isValid(mazeRows, mazeColumns, randomCellRow + 1, randomCellColumn)) {
            maze[randomCellRow * 2 + 2][randomCellColumn * 2 + 1] = String.valueOf('0');
            initialize(mazeRows, mazeColumns, randomCellRow + 1, randomCellColumn);
        }
        if (isValid(mazeRows, mazeColumns, randomCellRow, randomCellColumn - 1)) {
            maze[randomCellRow * 2 + 1][randomCellColumn * 2] = String.valueOf('0');
            initialize(mazeRows, mazeColumns, randomCellRow, randomCellColumn - 1);
        }
        if (isValid(mazeRows, mazeColumns, randomCellRow - 1, randomCellColumn)) {
            maze[randomCellRow * 2][randomCellColumn * 2 + 1] = String.valueOf('0');
            initialize(mazeRows, mazeColumns, randomCellRow - 1, randomCellColumn);
        }
    }

    private void fourthNeighborCases(int mazeRows, int mazeColumns, int randomCellRow, int randomCellColumn) {
        if (isValid(mazeRows, mazeColumns, randomCellRow, randomCellColumn - 1)) {
            maze[randomCellRow * 2 + 1][randomCellColumn * 2] = String.valueOf('0');
            initialize(mazeRows, mazeColumns, randomCellRow, randomCellColumn - 1);
        }
        if (isValid(mazeRows, mazeColumns, randomCellRow + 1, randomCellColumn)) {
            maze[randomCellRow * 2 + 2][randomCellColumn * 2 + 1] = String.valueOf('0');
            initialize(mazeRows, mazeColumns, randomCellRow + 1, randomCellColumn);
        }
        if (isValid(mazeRows, mazeColumns, randomCellRow, randomCellColumn + 1)) {
            maze[randomCellRow * 2 + 1][randomCellColumn * 2 + 2] = String.valueOf('0');
            initialize(mazeRows, mazeColumns, randomCellRow, randomCellColumn + 1);
        }
        if (isValid(mazeRows, mazeColumns, randomCellRow - 1, randomCellColumn)) {
            maze[randomCellRow * 2][randomCellColumn * 2 + 1] = String.valueOf('0');
            initialize(mazeRows, mazeColumns, randomCellRow - 1, randomCellColumn);
        }
    }


    private boolean isValid(int mazeRows, int mazeColumns, int randomCellRow, int randomCellColumn) {
        if (randomCellRow < 0 || randomCellColumn < 0 || randomCellRow > mazeRows - 1 || randomCellColumn > mazeColumns - 1)
            return false;
        return !maze[randomCellRow * 2 + 1][randomCellColumn * 2 + 1].equals("*");
    }
}
