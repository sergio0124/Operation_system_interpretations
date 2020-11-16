import java.util.ArrayList;

public class ROM {

    ArrayList<Page> List = new ArrayList<Page>();

    public void addPage(Page page) {
        List.add(page);
    }

    public void updatePage(Page page) {
        for(Page delpage:List){
            if(delpage.getID()== page.getID()){
                delpage=page;
                System.out.println("Page ID = " +page.getID()+" is saved in ROM");
            }
        }
    }

    public Page getPage(int pageID) {
        for(Page page:List){
            if(page.getID()==pageID)return page;
        }
        return null;
    }
}
