import java.util.ArrayList;

public class RAM {

    /*Есть список страниц, которые можно заполнить. Список страниц в процессе, элементы списка находятся в физической
    * памяти в списке. У страниц есть размеры, у памяти тоже.*/

    private ArrayList<Page> List;
    private int capacity;
    private final int PAGE_SIZE = 4;
    private double GRADE_OF_FULLNESS = 0.9;

    public RAM(int size){
        List = new ArrayList<>(size/4);
        capacity = size;
    }

    public boolean has(int pageID) {
        for (Page page: List){
            if(page.getID()==pageID){
                page.incChanges();
                System.out.println("this page is already in RAM" +
                        "\nChanges indicator is increased");
                return true;
            }
        }
        return false;
    }

    public boolean isFull(){
        if(List.size()*PAGE_SIZE<GRADE_OF_FULLNESS*capacity)return false;
        else return true;
    }

    public void addPage(Page page){
        List.add(page);
    }

    public Page findFoolest() {
        Page page = List.get(0);
        int count = List.get(0).getNumberOfChanges();
        for(int i=1;i<List.size();i++){
            if(List.get(i).getNumberOfChanges()<count){
                page=List.get(i);
                count=List.get(i).getNumberOfChanges();
            }
        }
        Page result=page.clone();
        List.remove(page);
        return result;
    }

    public void degradeChanges() {
        for(Page page:List){
            page.decChanges();
        }
    }
}
