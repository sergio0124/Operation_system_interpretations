import java.util.ArrayList;

public class Process {

    ArrayList<Page> List = new ArrayList<Page>();
    private int PID;
    private int numberPages;

    public Process(int count, int PID){
        numberPages = count;
        this.PID = PID;
    }

    public void addPage(Page page){
        List.add(page);
    }

    public int checkSize(){
        return numberPages;
    }

    public int checkPID(){
        return PID;
    }
}
