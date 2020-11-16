public class Page{

    private int numberOfChanges = 0;
    private int PID;
    private int ID;

    public Page(int pid, int id){
        PID = pid;
        ID = id;
    }

    public void incChanges(){
        numberOfChanges++;
    }

    public int getNumberOfChanges(){
        return numberOfChanges;
    }

    public int getID() {
        return ID;
    }

    public Page clone(){
        Page tmp = new Page(this.getPID(),this.getID());
        return tmp;
    }

    private int getPID() {
        return PID;
    }

    public void decChanges() {
        numberOfChanges--;
    }
}
