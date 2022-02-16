package main;

import logic.Words;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Words words = new Words();

        while (true) {
            System.out.println("Greens:");
            String greens = scanner.nextLine();

            System.out.println("Yellows:");
            String yellows = scanner.nextLine();

            System.out.println("Greys:");
            String grey = scanner.nextLine();

            if (!greens.isBlank()){
                for (char c : greens.toCharArray()) {
                    System.out.println("Index of green " + c + "?");
                    String index = scanner.nextLine();
                    words.removeWordsByGreenLetter(c, Integer.valueOf(index));
                }
            }

            if (!yellows.isBlank()){
                for (char c : yellows.toCharArray()) {
                    words.removeWordsByYellowLetter(String.valueOf(c));
                }
            }

            for (char c : grey.toCharArray()) {
                words.removeWordsByGreyLetter(String.valueOf(c));
            }

            System.out.println("Words left: " + words.getWordsLeft());
            words.update();
        }
    }

}
