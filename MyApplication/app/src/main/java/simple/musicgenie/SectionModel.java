package simple.musicgenie;


import java.util.ArrayList;

public class SectionModel {

    String sectionTitle;
    ArrayList<ItemModel> list;

    public SectionModel(String sectionTitle) {

        this.sectionTitle = sectionTitle;

    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public ArrayList<ItemModel> getList() {
        return list;
    }

    public void setList(ArrayList<ItemModel> list) {
        this.list = list;
    }
}
