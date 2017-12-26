package com.lukasz;

import java.util.*;

public class Solution {
    private List<List<Integer>> input;
    private List<String> knotHashedRows;
    private List<String> binaryRows;
    private int usedSquares;
    private int groupNumber;

    public Solution(List<List<Integer>> input) {
        this.input = input;
        this.usedSquares = 0;
        this.groupNumber = 0;
        knotHashedRows = hashInput();
        binaryRows = convertToBinary(knotHashedRows);
        usedSquares = countUsedSquares(binaryRows);
        groupNumber = countGroups(binaryRows);
    }

    private int countGroups(List<String> binaryRows) {
        Set<Square> counted = new HashSet<>();
        int sum = 0;

        for(int i = 0; i < binaryRows.size(); i++) {
            String currentRow = binaryRows.get(i);
            for(int j = 0; j < currentRow.length(); j++) {
                char currentChar = currentRow.charAt(j);
                if(currentChar == '1') {
                    Square currentSquare = new Square(i, j);
                    if(!counted.contains(currentSquare)) {
                        counted.add( new Square(i, j) );
                        findNeighbors(i, j, counted);
                        sum++;
                    }
                }
            }
        }

        return sum;
    }

    private Set<Square> findNeighbors(int i, int j, Set<Square> counted) {
        try {
            if(binaryRows.get(i-1).charAt(j) == '1' && !counted.contains( new Square (i-1, j) )) {
                counted.add( new Square(i-1, j) );
                findNeighbors(i - 1, j, counted);
            }
        }catch (IndexOutOfBoundsException e) {}
        try {
            if(binaryRows.get(i+1).charAt(j) == '1' && !counted.contains( new Square (i+1, j) )) {
                counted.add( new Square(i+1, j) );
                findNeighbors(i+1, j, counted);
            }
        }catch (IndexOutOfBoundsException e) {}
        try {
            if(binaryRows.get(i).charAt(j-1) == '1' && !counted.contains( new Square (i, j-1) )) {
                counted.add( new Square(i, j-1) );
                findNeighbors(i, j-1, counted);
            }
        }catch (IndexOutOfBoundsException e) {}
        try {
            if(binaryRows.get(i).charAt(j+1) == '1' && !counted.contains( new Square (i, j+1) )) {
                counted.add( new Square(i, j+1) );
                findNeighbors(i, j+1, counted);
            }
        }catch (IndexOutOfBoundsException e) {}

        return counted;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public int getUsedSquares() {
        return usedSquares;
    }

    private int countUsedSquares(List<String> binaryRows) {
        int sum = 0;
        for(int i = 0; i < binaryRows.size(); i++) {
            sum += (int) binaryRows.get(i).chars().filter( ch -> ch == '1' ).count();
        }
        return sum;
    }

    private List<String> convertToBinary(List<String> knotHashedRows) {
        List<String> binaryRows = new ArrayList<>();
        for(int i = 0; i < knotHashedRows.size(); i++) {
            String currentRow = knotHashedRows.get(i);
            StringBuilder binary = new StringBuilder();
            for(int j = 0; j < currentRow.length(); j++) {
                String currentBinary = convertToBinary(currentRow.charAt(j));
                binary.append(currentBinary);
            }
            currentRow = binary.toString();
            binaryRows.add(currentRow);
            System.out.println(currentRow);
        }

        return binaryRows;
    }

    private String convertToBinary(char ch) {
        int currentValue = Integer.parseInt(Character.toString(ch), 16);
        String currentBinary = Integer.toBinaryString(currentValue);
        currentBinary = convertTo4BitBinary(currentBinary);
        return currentBinary;
    }

    private String convertTo4BitBinary(String input) {
        while(input.length() < 4) {
            input = "0" + input;
        }
        return input;
    }

    private List<String> hashInput() {
        List<String> hashes = new ArrayList<>();
        for(int i = 0; i < input.size(); i++) {
            Hasher hasher = new Hasher(input.get(i));
            hasher.hash64();
            String currentHash = hasher.getKnotHash();
            hashes.add(currentHash);
        }

        return hashes;
    }

    class Square {
        int i;
        int j;

        public Square(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public int getI() {
            return i;
        }

        public int getJ() {
            return j;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i, j);
        }

        @Override
        public boolean equals(Object obj) {
            return this.i == ((Square) obj).getI() && this.j == ((Square) obj).getJ();
        }
    }


}
