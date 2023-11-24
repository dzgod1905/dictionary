package Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Word {
    private String wordTarget;
    private String wordExplain;
    private final Map<Integer, Word> nextChild = new HashMap<>();

    public Word() {

    }

    public Word(String wordTarget, String wordExplain) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
    }

    public void deleteWord() {
        this.wordTarget = null;
        this.wordExplain = null;
    }

    public void setWordExplain(String wordExplain) {
        this.wordExplain = wordExplain;
    }

    public void setWordTarget(String wordTarget) {
        this.wordTarget = wordTarget;
    }

    public String getWordTarget() {
        return wordTarget;
    }

    public String getWordExplain() {
        return wordExplain;
    }

    public void setNextChild(int index, Word word) {
        nextChild.put(index, word);
    }

    public Word getNextChild(int index) {
        return nextChild.get(index);
    }

    public List<Integer> getAllChilds() {
        return new ArrayList<>(nextChild.keySet());
    }

    @Override
    public String toString() {
        return String.format("Word: %s\nMeaning:\n%s", wordTarget, wordExplain);
    }

    public static void main(String[] args) {
    }
}
