package com.lukasz;

import java.util.ArrayList;
import java.util.List;

public class Hasher {
    private List<Integer> list;
    private int currentPosition, skipSize;
    private List<Integer> lengths;
    private int firstTimesSecond;
    private String knotHash;

    private final int LIST_LENGTH = 256;

    public Hasher(List<Integer> lengths) {
        this.lengths = lengths;
        list = fillList();
        this.currentPosition = 0;
        this.skipSize = 0;
    }

    public void hash64() {
        for(int i = 0; i < 64; i++) {
            hash();
        }

        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < 16; i++) {
            int xor = list.get(i * 16);
            for(int j = 1; j < 16; j++) {
                xor = xor ^ list.get(i * 16 + j);
                System.out.print("");
            }
            String currentHex = Integer.toHexString(xor);
            if(currentHex.length() == 1) {
                currentHex = "0" + currentHex;
            }
            stringBuilder.append(currentHex);
        }

        knotHash = stringBuilder.toString();
    }

    public void hash() {
        for(int i = 0; i < lengths.size(); i++) {
            int currentLength = lengths.get(i);

            int offset = 0;
            while(offset < currentLength / 2) {
                int leftPosition = findLeftPosition(offset);
                int rightPosition = findRightPosition(offset, currentLength);

                changePositionsInList(leftPosition, rightPosition);

                offset++;
            }

            currentPosition += currentLength + skipSize;
            if(currentPosition >= list.size()) {
                currentPosition -= list.size();
            }
            skipSize++;

        }

        firstTimesSecond = list.get(0) * list.get(1);
    }

    private void changePositionsInList(int leftPosition, int rightPosition) {
        int a = list.get(leftPosition);
        int b = list.get(rightPosition);
        list.set(leftPosition, b);
        list.set(rightPosition, a);
    }

    private List<Integer> fillList() {
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < LIST_LENGTH; i++) {
            list.add(i);
        }

        return list;
    }

    public int getFirstTimesSecond() {
        return firstTimesSecond;
    }

    private int findLeftPosition(int offset) {
        int leftPosition = currentPosition + offset;

        while(leftPosition >= list.size()) {
            leftPosition -= list.size();
        }

        return leftPosition;
    }

    private int findRightPosition(int offset, int currentLength) {
        int rightPosition = currentPosition + currentLength - offset - 1;

        while(rightPosition >= list.size()) {
            rightPosition -= list.size();
        }

        return rightPosition;
    }

    public String getKnotHash() {
        return knotHash;
    }
}

