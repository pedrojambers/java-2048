package com.pedro.game2048;


import java.util.Scanner;

public class Game2048 {
    public static void main(String[] args) {
        Board board = new Board();
        Scanner scanner = new Scanner(System.in);


        while (true) {
            board.printBoard();
            System.out.println("Move (W/A/S/D or Q to quit): ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("Q")) {
                System.out.println("The end.");
                break;
            }

            boolean moved = false;
            if (input.equals("A")) {
                moved = board.moveLeft();
            } else if (input.equals("D")) {
                moved = board.moveRight();
            } else if (input.equals("W")) {
                moved = board.moveUp();
            } else if (input.equals("S")) {
                moved = board.moveDown();
            } else {
                System.out.println("Invalid input. Use W/A/S/D to move or Q to quit.");
            }

            if (!moved) {
                System.out.println("No tiles moved");
            }
        }

        scanner.close();
    }
}
