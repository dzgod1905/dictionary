package Models;

import java.util.*;

public class Dictionary {
  private final ArrayList<Word> wordList = new ArrayList<>();
  private final Word head = new Word();

  public void addWord(Word word) {
    String wordTarget = word.getWordTarget();
    String wordExplain = word.getWordExplain();
    wordTarget = wordTarget.toLowerCase();
    int len = wordTarget.length();
    Word tmp = head;
    int i = 0;
    while (i < len) {
      Word nextChild = tmp.getNextChild(wordTarget.charAt(i));
      if (nextChild == null) {
        nextChild = new Word();
        tmp.setNextChild(wordTarget.charAt(i), nextChild);
      }
      tmp = nextChild;
      i++;
    }
    tmp.setWordTarget(wordTarget);
    tmp.setWordExplain(wordExplain);
    wordList.add(tmp);
  }

  public Word findWordBasic(String wordTarget) {
    wordTarget = wordTarget.toLowerCase();
    for (Word word : wordList) {
      if (word.getWordTarget().equals(wordTarget))
        return word;
    }
    return null;
  }

  public Word findWordAdvance(String wordTarget) {
    wordTarget = wordTarget.toLowerCase();
    int len = wordTarget.length();
    Word tmp = head;
    int i = 0;
    while (i < len) {
      Word nextChild = tmp.getNextChild(wordTarget.charAt(i));
      if (nextChild == null)
        return null;
      tmp = nextChild;
      i++;
    }
    return tmp;
  }

  public ArrayList<Word> searchWord(String search) {
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
      for (Integer i : tmp.getAllChilds())
        queue.add(tmp.getNextChild(i));
    }

    return list;
  }

  public void removeWord(String wordTarget) {
    wordTarget = wordTarget.toLowerCase();
    Word tmp = findWordBasic(wordTarget);
    if (tmp != null) {
      wordList.remove(tmp);
      tmp = findWordAdvance(wordTarget);
      tmp.deleteWord();
    }
  }

  public void changeWord(Word word) {
    String wordTarget = word.getWordTarget().toLowerCase();
    String wordExplain = word.getWordExplain();
    Word tmp = findWordAdvance(wordTarget);
    if (tmp == null)
      return;
    tmp.setWordExplain(wordExplain);
  }

  public void showAll() {
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

  public ArrayList<Word> getWordList() {
    return new ArrayList<>(wordList);
  }
}
