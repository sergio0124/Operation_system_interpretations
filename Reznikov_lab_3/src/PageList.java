import java.util.ArrayList;

public class PageList {

    ArrayList<Page> List;

    public PageList() {
        List = new ArrayList<Page>();
    }

    public void addPage(Page page) {
        List.add(page);
    }

    /*Рандомно выбирается страница (ведь мы не занем, что нам понадобится
    Это незнание отражено в рандоме)*/
    public int getPageID() {
        return List.get((int) (Math.random() * List.size())).getID();
    }
}
