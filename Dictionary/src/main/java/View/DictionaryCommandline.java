package View;

import Manager.DictionaryManager;
import Utils.InputScanner;
import Models.Word;

import java.util.ArrayList;

public class DictionaryCommandline {
    private final DictionaryManager dictManager;
    public DictionaryCommandline() {
        dictManager = new DictionaryManager();
    }

    public void showAllWords() {
        for (Word word: dictManager.getWordList()) System.out.printf("%s - %s\n", word.getWordTarget(), word.getWordExplain());

    }

    private void doRemove() {
        dictManager.removeWord(InputScanner.getLine("Word to be removed: "));
    }

    private void doUpdate() {
        String wordTarget = InputScanner.getLine("Word to be updated: ");
        String wordExplain = InputScanner.getLine("Explanation: ");
        dictManager.changeWord(new Word(wordTarget, wordExplain));
    }

    private void doLookUp() {
        String wordTarget = InputScanner.getLine("Word to be looked up: ");
        Word word = dictManager.findWordAdvance(wordTarget);
        if (word == null) System.out.println("Word not found!");
        else System.out.printf("%s - %s\n", word.getWordTarget(), word.getWordExplain());
    }

    private void doSearch() {
        String wordTarget = InputScanner.getLine("Word to be searched: ");
        ArrayList<Word> wordList = dictManager.searchWord(wordTarget);
        if (wordList == null || wordList.isEmpty())
            System.out.println("No words with such prefix found!");
        else {
            System.out.println("The words found: ");
            for (Word word: wordList) System.out.printf("%s - %s\n", word.getWordTarget(), word.getWordExplain());
        }
    }

    public void insertFromCommandline() {
        int n = InputScanner.getInteger("Number of word to add: ");
        InputScanner.getLine();
        while (n --> 0) {
            String wordTarget = InputScanner.getLine();
            String wordExplain = InputScanner.getLine();
            dictManager.addWord(new Word(wordTarget, wordExplain));
        }
    }
    
    public void dictionaryBasic() {
        int choice;
        System.out.println("Welcome to my Application!!!");
        do {
            System.out.println("""
					[0] Exit
					[1] Add
					[2] Display
					""");
            choice = InputScanner.getIntegerRange("Your action (from 0 to 2): ", 0, 2);
            switch (choice) {
                case 1 -> insertFromCommandline();
                case 2 -> showAllWords();
            }
        } while (choice != 0);
    }
    public void dictionaryAdvanced() { // added later

    }

    // test client
    public static void main(String[] args) {
        DictionaryCommandline dictCmd = new DictionaryCommandline();
        dictCmd.dictionaryBasic();
    }
}

