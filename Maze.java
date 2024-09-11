package Mazeruuner;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


class Maze {
    char[][] maze;

    // Constructor to initialize the maze
    public Maze(int row, int column) {
        this.maze = new char[row][column];
        create(row, column);  // Initialize the maze
    }

    // Method to fill the maze with '0' (walkable paths)
    public void create(int row, int column) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                maze[i][j] = '0';  // Set all cells as walkable paths ('0')
            }
        }
    }

    // Method to print the maze
    public void print() {
        for (int i = 0; i < maze.length; i++) {
            System.out.print("\n");
            for (int j = 0; j < maze[0].length; j++) {
                System.out.print(maze[i][j] + " ");
            }
        }
        System.out.println();  // New line after printing the maze
    }

    // Method to place a random number of monsters ('M') in the maze
    public void placeMonsters() {
        Random random = new Random();

        // Generate a random number of monsters to place (1 to 1/3 of the maze size)
        int numMonsters = random.nextInt((maze.length * maze[0].length) / 3) + 1;
        System.out.println("Number of monsters to be placed: " + numMonsters);

        int monstersPlaced = 0;

        // Loop until the required number of monsters is placed
        while (monstersPlaced < numMonsters) {
            int randomRow = random.nextInt(maze.length);
            int randomCol = random.nextInt(maze[0].length);
            if (maze[randomRow][randomCol] == '0' &&
                !(randomRow == 0 && randomCol == 0) &&  // Not the entrance
                !(randomRow == maze.length - 1 && randomCol == maze[0].length - 1)) {  // Not the exit
                maze[randomRow][randomCol] = 'M';  // Place the monster
                monstersPlaced++;  // Increment the number of monsters placed
            }
        }
    }

    // Method to place the treasure ('T') in the maze
    public void placeTreasure() {
        Random random = new Random();
        int randomRow, randomCol;
        boolean validPlacement;

        do {
            randomRow = random.nextInt(maze.length);
            randomCol = random.nextInt(maze[0].length);
            validPlacement = isValidTreasurePosition(randomRow, randomCol);
        } while (!validPlacement);

        maze[randomRow][randomCol] = 'T';  // Place the treasure in a valid position
    }

    // Method to check if the treasure position is valid
    private boolean isValidTreasurePosition(int row, int col) {
        if (maze[row][col] != '0') return false;  // Must be a walkable path
        if ((row == 0 && col == 0) || (row == maze.length - 1 && col == maze[0].length - 1)) return false;  // Not at the entrance or exit

        // Must be at least half the maze size away from the entrance
        int minDistanceFromEntrance = (maze.length + maze[0].length) / 2;
        return Math.abs(row - 0) + Math.abs(col - 0) >= minDistanceFromEntrance;
    }

    // Class representing a node in the maze
    private static class Node {
        int row, col, steps;
        Node prev;

        Node(int row, int col, int steps, Node prev) {
            this.row = row;
            this.col = col;
            this.steps = steps;
            this.prev = prev;
        }
    }

    // Method to trace and mark the shortest path in the maze
    private void shortpathmaze(Node node) {
        Node cur = node;
        while (cur != null) {
            if (maze[cur.row][cur.col] != 'A' && maze[cur.row][cur.col] != 'T') {
                maze[cur.row][cur.col] = 'P';  // Mark the path
            }
            cur = cur.prev;
        }
        System.out.println("Maze with path:");
        print();
    }

    // Method to find the shortest path using BFS
    public int shortestpath(int row, int col) {
        int rowlen = maze.length;
        int collen = maze[0].length;
        
        boolean[][] visited = new boolean[rowlen][collen];
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        Queue<Node> queue = new LinkedList<>();
        
        queue.add(new Node(row, col, 0, null));
        visited[row][col] = true;

        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            
            for (int[] direction : directions) {
                int nextRow = cur.row + direction[0];
                int nextCol = cur.col + direction[1];

                if (inboundary(nextRow, nextCol) && !visited[nextRow][nextCol] && maze[nextRow][nextCol] != 'M') {
                    if (maze[nextRow][nextCol] == 'T') {
                        shortpathmaze(cur);  // Trace the path when treasure is found
                        return cur.steps + 1;
                    }
                    queue.add(new Node(nextRow, nextCol, cur.steps + 1, cur));
                    visited[nextRow][nextCol] = true;
                }
            }
        }
        return -1;  // Return -1 if no path found
    }

    // Method to check if the position is within maze boundaries
    private boolean inboundary(int i, int j) {
        return i >= 0 && i < maze.length && j >= 0 && j < maze[0].length;
    }
}



