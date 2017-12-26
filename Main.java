package com.lukasz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static List<List<Integer>> getInput() {

        List<List<Integer>> inputAsList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter key string.");
        String input = scanner.nextLine();

        for(int i = 0; i < 128; i++) {
            String currentWord = input + "-" + i;
            List<Integer> lengths = new ArrayList<>();
            for (int j = 0; j < currentWord.length(); j++) {
                int currentChar = (int) currentWord.charAt(j);
                lengths.add(currentChar);
            }
            lengths.addAll(Arrays.asList(17, 31, 73, 47, 23));
            inputAsList.add(lengths);
        }

        return inputAsList;
    }

    public static void main(String[] args) {
        List<List<Integer>> input = getInput();
        Solution solution = new Solution(input);

        System.out.println("There are " + solution.getUsedSquares() + " used squares.");
        System.out.println("There are " + solution.getGroupNumber() + " groups.");
    }
}
