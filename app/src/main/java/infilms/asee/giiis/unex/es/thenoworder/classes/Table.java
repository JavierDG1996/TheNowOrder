package infilms.asee.giiis.unex.es.thenoworder.classes;

public class Table {

    private String title;
    private int number;
    private int thumbnail;

    public Table(String title, int number, int thumbnail) {
        this.title = title;
        this.number = number;
        this.thumbnail = thumbnail;
    }


    public String getTitle() {
        return title;
    }

    public int getNumber() {
        return number;
    }

    public int getThumbnail() {
        return thumbnail;
    }

}
