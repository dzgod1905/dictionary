package Models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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

  public static ArrayList<Word> searchWord(String search) {
    search = search.toLowerCase();
    ArrayList<Word> list = new ArrayList<>();

    Word tmp = findWordAdvance(search);
    if (tmp == null)
      return null;

    Queue<Word> queue = new LinkedList<>();
    queue.add(tmp);
    while (!queue.isEmpty()) {
      tmp = queue.remove();
      if(tmp.getWordTarget()!=null) list.add(tmp);
      for (int i = 0; i < 26; i++)
        if (tmp.getNextChild(i) != null) queue.add(tmp.getNextChild(i));
    }

    return list;
  }

  public static void removeWord(String wordTarget) {
    wordTarget = wordTarget.toLowerCase();
    Word tmp = findWordBasic(wordTarget);
    if (tmp != null) {
      wordList.remove(tmp);
      tmp = findWordAdvance(wordTarget);
      tmp.deleteWord();
    }
  }

  public static void changeWord(Word word) {
    String wordTarget = word.getWordTarget().toLowerCase();
    String wordExplain = word.getWordExplain();
    Word tmp = findWordAdvance(wordTarget);
    if (tmp == null)
      return;
    tmp.setWordExplain(wordExplain);
  }

  public static void showAll() {
    System.out.println("No       | English                 | Vietnamese");
    int i = 0;
    for (Word word : wordList) {
      i++;
      System.out.print(i);
      for (int tmp = 0; tmp < 9 - String.valueOf(i).length(); tmp++)
        System.out.print(" ");
      System.out.print("| " + word.getWordTarget());
      for (int tmp = 0; tmp < 24 - word.getWordTarget().length(); tmp++)
        System.out.print(" ");
      System.out.println("| " + word.getWordExplain());
    }
  }

  public static void main(String[] args) {
    Word word = new Word("JustLonely", "me");
    Word word1 = new Word("JustLonel", "lone");
    Word word2 = new Word("JuttLonel", "lone");
    addWord(word);
    addWord(word1);
    addWord(word2);
    for (Word tmp : searchWord("Ju")) {
      System.out.println(tmp.getWordTarget());
    }
  }
}
