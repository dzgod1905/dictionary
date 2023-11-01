package Manager;

import Models.Dictionary;
import Models.Word;

import java.io.*;
import java.sql.*;
import java.util.Objects;

public class DictionaryManager {
  private Dictionary dictionary = new Dictionary();

  private void insertFromFile(){
    try (InputStream is = getClass().getResourceAsStream("/dictionaries.txt")) {
      BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));
      String line;
      while ((line = br.readLine()) != null) {
        String[] words = line.split("\t");
        dictionary.addWord(new Word(words[0], words[1]));
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  private void insertFromDatabase() {
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

  public DictionaryManager(){
    insertFromFile();
  }

  public static void main(String[] args) {
    DictionaryManager dictionaryManager = new DictionaryManager();
  }
}
