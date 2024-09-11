package Mazeruuner;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class Mazeruner {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            // Input for maze dimensions
            System.out.println("Enter the number of rows:");
            int n = sc.nextInt();

            System.out.println("Enter the number of columns:");
            int m = sc.nextInt();

            // Validate input to ensure positive dimensions
            if (n <= 0 || m <= 0) {
                System.out.println("Invalid input: Rows and columns must be positive numbers.");
                return;
            }

            // Create and initialize the maze
            Maze maze = new Maze(n, m);

            // Print the initial maze
            System.out.println("Initial Maze:");
            maze.print();

            // Place random number of monsters in the maze
            maze.placeMonsters();

            // Place treasure in a tough position
            maze.placeTreasure();

            // Print the maze after placing monsters and treasure
            System.out.println("\nMaze with monsters and treasure placed:");
            maze.print();
            
            // Start from row 0 and a random column
            Random random = new Random();
            maze.shortestpath(random.nextInt(m),0);
        }
    }
}

