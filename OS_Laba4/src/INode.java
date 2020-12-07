import java.util.ArrayList;

public class INode {
    public ArrayList<Sector> sectors = new ArrayList<>();

    public void setSelected() {
        for (Sector sector:sectors
             ) {
            sector.setSelected();
        }
    }

    public void addSector(Sector sector){
        sectors.add(sector);
    }

    public INode(){}

    public INode(Sector sector){
        sector.findPlace(sectors);
    }

    public int getSize(int sectorSize) {
        if(sectors.size()!=0) {
            return sectors.size()*sectorSize;
        }
        else {
            return 0;
        }
    }

    public Sector getSector() {
        return sectors.get(0);
    }
}
