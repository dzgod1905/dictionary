package Models;

public class Word {
    private String wordTarget;
    private String wordExplain;
    private final Word[] nextChild = new Word[26];

    public Word() {
    }

    public Word(String wordTarget, String wordExplain) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
    }

    public void setWordExplain(String wordExplain) {
        if (this.wordExplain == null)
            this.wordExplain = wordExplain;
        else {
            this.wordExplain = this.wordExplain.concat("\n");
            this.wordExplain = this.wordExplain.concat(wordExplain);
        }
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
        nextChild[index] = word;
    }

    public Word getNextChild(int index) {
        return nextChild[index];
    }

    public static void main(String[] args) {
        String tmp = "z";
        System.out.println(tmp.charAt(0) - 'a');
    }
}