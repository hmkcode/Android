package simple.music;

import java.util.ArrayList;


public class Segmentor {

    /*
    *
    *       return ArrayList of constituents part of String Seperated by  {seperator}
    *
    * */
    public ArrayList<String> getParts(String str, char seperator) {
        ArrayList<String> all = new ArrayList<>();
        getAll(str, seperator, all);
        for (int i =0;i<all.size();i++){
            if(all.get(i).equals(""))all.remove(i);
        }
        return all;
    }

    String getAll(String str, char seperator, ArrayList<String> list) {
        if (str.indexOf(seperator) == -1) {
            list.add(str);
            return str;
        } else {
            list.add(str.substring(0, str.indexOf(seperator)));
            return getAll(str.substring(str.indexOf(seperator) + 1), seperator,
                    list);
        }
    }

}
