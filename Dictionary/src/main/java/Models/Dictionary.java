package Models;

import java.util.ArrayList;

public class Dictionary {
    private final static ArrayList<Word> wordList = new ArrayList<>();
    private final static Word head = new Word();

    public static void addWord(Word word) {
        String wordTarget = word.getWordTarget();
        String wordExplain = word.getWordExplain();
        wordTarget = wordTarget.toLowerCase();
        int len = wordTarget.length();
        Word tmp = head;
        int i = 0;
        while (i < len) {
            Word nextChild = tmp.getNextChild(wordTarget.charAt(i) - 'a');
            if (nextChild == null) {
                nextChild = new Word();
                tmp.setNextChild(wordTarget.charAt(i) - 'a', nextChild);
            }
            tmp = nextChild;
            i++;
        }
        tmp.setWordTarget(wordTarget);
        tmp.setWordExplain(wordExplain);
        if (findWordBasic(wordTarget) == null)
            wordList.add(tmp);
    }

    public static Word findWordBasic(String wordTarget) {
        wordTarget = wordTarget.toLowerCase();
        for (Word word : wordList) {
            if (word.getWordTarget().equals(wordTarget))
                return word;
        }
        return null;
    }

    public static Word findWordAdvance(String wordTarget) {
        wordTarget = wordTarget.toLowerCase();
        int len = wordTarget.length();
        Word tmp = head;
        int i = 0;
        while (i < len) {
            Word nextChild = tmp.getNextChild(wordTarget.charAt(i) - 'a');
            if (nextChild == null)
                return null;
            tmp = nextChild;
            i++;
        }
        return tmp;
    }

    public static void main(String[] args) {
        Word word = new Word("JustLonely", "me");
        addWord(word);
        addWord(word);
        System.out.println(findWordBasic("JustLonely").getWordExplain());
    }
}
