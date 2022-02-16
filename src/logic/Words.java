package logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Words {

    private ArrayList<String> words;
    private char [] alphabetArray;
    private HashMap<Character, Integer> alphabet;
    private HashMap<String, Integer> wordScore;

    private String greens = "";
    private String yellows = "";
    private String greys = "";

    public Words() {
        importWords();
        updateAlphabetMap();
        scoreWords();

    }

    private void importWords() {
        words = new ArrayList<>();
        try (Scanner scanner = new Scanner(new FileInputStream("src/logic/words"))) {
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                words.add(scanner.next().replace("\"", ""));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createAlphabetMap() {
        alphabetArray = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        alphabet = new HashMap<>();
        for (char c : alphabetArray) {
            alphabet.put(c, 0);
        }
    }

    public void updateAlphabetMap(){
        createAlphabetMap();
        for (String word : words){
            for (char c : word.toCharArray()){
                alphabet.put(c, alphabet.get(c) + 1);
            }
        }
    }

    public void scoreWords(){
        wordScore = new HashMap<>();
        for (String word : words) {
            wordScore.put(word, 0);
            ArrayList<Character> usedChars = new ArrayList<>();
            for (char c : word.toCharArray()) {
                if (!usedChars.contains(c)) wordScore.put(word, wordScore.get(word) + alphabet.get(c));
                usedChars.add(c);
            }
        }

        LinkedHashMap<String, Integer> wordsByScore = new LinkedHashMap<>();
        wordScore.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> wordsByScore.put(x.getKey(), x.getValue()));

        int i = 0;
        for(Map.Entry<String, Integer> entry : wordsByScore.entrySet()) {
            if (i < 3) {
                System.out.printf("%s: %d %n", entry.getKey(), entry.getValue());
                i++;
            } else break;
        }
    }

    public void removeWordsByYellowLetter(String letter) {
        words.removeIf(word -> !word.contains(letter));
        yellows = yellows.concat(letter);
        System.out.println("Yellows: " + yellows);
    }

    public void removeWordsByGreenLetter(char letter, int placement) {
        words.removeIf(word -> word.charAt(placement) != letter);
        greens = greens.concat(String.valueOf(letter));
        System.out.println("Greens: " + greens);
    }

    public void removeWordsByGreyLetter(String letter) {
        words.removeIf(word -> word.contains(letter));
        greys = greys.concat(letter);
        System.out.println("Greys: " + greys);
    }

    public void update() {
        updateAlphabetMap();
        scoreWords();
    }

    public int getWordsLeft() {
        return words.size();
    }

}
