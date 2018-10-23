package mydictionary;

import java.sql.*;
import java.util.ArrayList;

class DictionaryManagement {
    ArrayList<String> list = new ArrayList<>();

    private final String  myDriver = "com.mysql.jdbc.Driver";
    private final String myUrl = "jdbc:mysql://localhost/tu_dien";

    void insertDataFromMySql(String nameTable){
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

    void insertData(String nameTable, String wordTarget, String wordExplanation, int id){
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

    String getExplanationDataFrom(String nameTable, int id){
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

    void editMySqlData(String nameTable, int index, String wordTarget, String wordExplanation){
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

    String[] dictionarySearcher(String w){

        ArrayList<String> searchList = new ArrayList<>();
        if ( !w.equals("") ){
            int size = 0, listSize = 0;
            for (int i=0; i<list.size(); i++){
                if (list.get(i).length() >= w.length()){
                    if (list.get(i).substring(0, w.length()).equals(w)){
                        searchList.add(list.get(i));
                        listSize++;
                    }
                    // Neu khong con ket qua phu hop nua hoac du 30 ket qua thi dung vong lap
                    if ( (listSize > 0 && listSize == size) || listSize == 30){
                        break;
                    }
                    size = listSize;
                }
            }

        }
        String[] searchWords = new String[searchList.size()];
        return searchList.toArray(searchWords);
    }

    int getWordIndex(String selectedValue){
        for (int i=0; i<list.size(); i++){
            if (list.get(i).equals(selectedValue)) {
                return i + 1;
            }
        }
        return 0;
    }

    boolean isWordExist(String word){
        for (int i=0; i<list.size(); i++){
            if (list.get(i).equals(word)){
                return true;
            }
        }
        return false;
    }

}
