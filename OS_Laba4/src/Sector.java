import java.util.ArrayList;

/*Единица памяти в диске.
 * Хранит ссылку на следующий в цепочке сектор*/
public class Sector {

    /*Размещение с использованием связанного списка*/
    Sector next;
    SectorMode mode = SectorMode.Unselected;

    public SectorMode getMode() {
        return mode;
    }

    public Sector(Sector next) {
        this.next = next;
    }

    public Sector() {
        next = null;
    }

    public void setNext(Sector sector) {
        next = sector;
    }

    public void setSelected() {
        if (next != null) {
            next.setSelected();
        }
        mode = SectorMode.Selected;
    }

    public void setUnselected() {
        mode = SectorMode.Unselected;
    }

    public Sector getNext() {
        return next;
    }

    public int getSize(int sectorSize) {
        int result = 0;
        if (next == null) {
            return sectorSize;
        } else {
            return next.getSize(sectorSize) + sectorSize;
        }
    }

    public void deleteList() {
        if (next == null) {
            return;
        } else {
            next.deleteList();
            next = null;
            return;
        }

    }

    public void findPlace(ArrayList<Sector> sectors) {
        sectors.add(this);
        if (next!=null) {
            next.findPlace(sectors);
        }
    }
}
