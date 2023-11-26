package Manager;

import Models.Dictionary;
import Models.Word;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class DictionaryManager {
  private final Dictionary dictionary = new Dictionary();

  public void insertFromFile(){
    try (InputStream is = getClass().getResourceAsStream("/dictionaries.txt")) {
      BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));
      String line;
      String wordTarget = null;
      StringBuilder wordExplain = null;
      while ((line = br.readLine()) != null) {
        if (line.contains("\t")) {
          if (wordTarget != null) {
            dictionary.addWord(new Word(wordTarget, wordExplain.toString()));
          }
          String[] words = line.split("\t");
          wordTarget = words[0];
          wordExplain = new StringBuilder(words[1]);
        } else {
          if (wordExplain == null) wordExplain = new StringBuilder();
          wordExplain.append(line);
        }
      }
      if (wordTarget != null) {
        dictionary.addWord(new Word(wordTarget, wordExplain.toString()));
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  public void exportToFile() {
    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("./Dictionary/src/main/resources/dictionaries.txt"))) {
      List<Word> wordList = dictionary.getWordList();
      wordList.sort(Comparator.comparing(Word::getWordTarget));
      for (Word word : wordList) {
        bufferedWriter.write(word.getWordTarget() + "\t" + word.getWordExplain() + "\n");
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  public void insertFromDatabase() {
    String url = "jdbc:postgresql://localhost:5432/dictionary?user=admin&password=admin";
    try (Connection conn = DriverManager.getConnection(url)) {
      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery("select * from dictionary");
      while (rs.next()) {
        dictionary.addWord(new Word(rs.getString(2), rs.getString(3)));
      }
      rs.close();
      st.close();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public void addWord(Word word) {
      dictionary.addWord(word);
  }

  public Word findWordBasic(String wordTarget) {
      return dictionary.findWordBasic(wordTarget);
  }

  public Word findWordAdvance(String wordTarget) {
    return dictionary.findWordAdvance(wordTarget);
  }

  public ArrayList<Word> searchWord(String search) {
    return dictionary.searchWord(search);
  }

  public void removeWord(String wordTarget) {
    dictionary.removeWord(wordTarget);
  }

  public void changeWord(Word word) {
    dictionary.changeWord(word);
  }

  public ArrayList<Word> getWordList() {
    return dictionary.getWordList();
  }

  public int getDictionarySize() {
    return dictionary.size();
  }

  private static DictionaryManager instance;

  private DictionaryManager() {
    insertFromFile();
  }

  public static DictionaryManager getInstance() {
    if (instance == null) {
      instance = new DictionaryManager();
    }
    return instance;
  }

  public static void main(String[] args) {
      DictionaryManager dictionaryManager = new DictionaryManager();
      dictionaryManager.insertFromFile();
      System.out.println(dictionaryManager.findWordAdvance("gaming"));
  }
}
