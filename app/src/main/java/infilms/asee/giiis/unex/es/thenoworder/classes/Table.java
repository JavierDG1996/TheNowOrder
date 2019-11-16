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

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
