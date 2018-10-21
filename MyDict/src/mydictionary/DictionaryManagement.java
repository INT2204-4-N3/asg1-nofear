package mydictionary;

import java.sql.*;
import java.util.ArrayList;

public class DictionaryManagement {
    ArrayList<String> list = new ArrayList<>();

    final static String  myDriver = "com.mysql.jdbc.Driver";
    final static String myUrl = "jdbc:mysql://localhost/tu_dien";

    public void insertDataFromMySql(String nameTable){
        try
        {
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "");
            String query = "SELECT word_target FROM " + nameTable;

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next())
            {
                list.add(rs.getString("word_target"));
            }
            st.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

    public void insertData(String nameTable, String wordTarget, String wordExplanation, int id){
        try
        {

            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "");

            String query = " insert into " + nameTable + " (word_target, word_explanation, id)" + " values (?, ?, ?)";

            PreparedStatement preparedStmt = conn.prepareStatement(query);

            preparedStmt.setNString(1, wordTarget);
            preparedStmt.setNString(2,"<i>" + wordTarget + "</i></b><ul><li><font color='#cc0000'><b>" + wordExplanation);
            preparedStmt.setInt(3, id);

            preparedStmt.execute();

            conn.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }

    public String getExplanationDataFrom(String nameTable, int id){
        String wordExplanation = "";
        try
        {

            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "");

            String query = "SELECT word_explanation FROM " + nameTable + " WHERE id = " + id;

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                wordExplanation = rs.getString("word_explanation");
                break;
            }
            st.close();

        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return wordExplanation;
    }

    public void editMySqlData(String nameTable, int index, String wordTarget, String wordExplanation){
        try
        {

            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "");

            String query1 = "update " + nameTable + " set word_target = ? where id = ?";
            String query2 = "update " + nameTable + " set word_explanation = ? where id = ?";

            PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
            PreparedStatement preparedStmt2 = conn.prepareStatement(query2);

            preparedStmt1.setNString(1, wordTarget);
            preparedStmt1.setInt(2, index);

            preparedStmt2.setNString(1, "<i>" + wordTarget + "</i></b><ul><li><font color='#cc0000'><b>" + wordExplanation);
            preparedStmt2.setInt(2, index);

            preparedStmt1.executeUpdate();
            preparedStmt2.executeUpdate();

            conn.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

    public String[] dictionarySearcher(String w){

        ArrayList<String> searchList = new ArrayList<>();
        if ( !w.equals("") ){
            int size = 0, listSize = 0;
            for (int i=0; i<list.size(); i++){
                if (list.get(i).length() >= w.length()){
                    if (list.get(i).substring(0, w.length()).equals(w)){
                        searchList.add(list.get(i));
                        listSize++;
                    }
                    // Neu khong con ket qua phu hop nua thi dung vong lap
                    if ( listSize > 0 && listSize == size){
                        break;
                    }
                    size = listSize;
                }
            }

        }
        String[] searchWords = new String[searchList.size()];
        return searchList.toArray(searchWords);
    }

    public int getWordIndex(String selectedValue){
        int index = 0;
        for (int i=0; i<list.size(); i++){
            if (list.get(i).equals(selectedValue)) {
                index = i + 1;
                break;
            }
        }
        return index;
    }

    public boolean isWordExist(String word){
        int s = 0;
        for (int i=0; i<list.size(); i++){
            if (list.get(i).equals(word)){
                s++;
            }
        }
        return s != 0;
    }

}
