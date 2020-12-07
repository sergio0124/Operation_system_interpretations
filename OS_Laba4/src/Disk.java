import java.awt.*;

/*Содержит массив секторов строгого размера, через диск выполняется создание секторов с помощью рекурсии,
 удаление секторов, отрисовка секторов. */
public class Disk {

    final int ELEMENT_SIDE = 20;
    Sector[] sectors;
    private int sectorSize;
    final int TRY_NUMBER = 3;

    public Disk(int diskSize, int sectorSize) {
        this.sectorSize = sectorSize;
        sectors = new Sector[diskSize / sectorSize];
    }

    public void setSector(INode inode, int size){
        int count = size;
        while(count>0){
            int place = -1;
            for (int i = 0; i < TRY_NUMBER; i++) {
                place = (int) (Math.random() * sectors.length);
                if (sectors[place] == null) {
                    break;
                } else {
                    place = -1;
                }
            }

            if (place == -1) {
                for (int i = 0; i < sectors.length; i++) {
                    if (sectors[i] == null) {
                        place = i;
                        break;
                    }
                }
            }
            sectors[place]=new Sector();
            inode.addSector(sectors[place]);
            count-=sectorSize;
        }
    }

    public Sector setSector(int size) {

        if (size <= 0) {
            return null;
        }

        int place = -1;
        for (int i = 0; i < TRY_NUMBER; i++) {
            place = (int) (Math.random() * sectors.length);
            if (sectors[place] == null) {
                break;
            } else {
                place = -1;
            }
        }

        if (place == -1) {
            for (int i = 0; i < sectors.length; i++) {
                if (sectors[i] == null) {
                    place = i;
                    break;
                }
            }
        }

        sectors[place] = new Sector();
        sectors[place].setNext(setSector(size - sectorSize));
        return sectors[place];
    }


    public int getFreeSpace() {
        int result = 0;
        for (int i = 0; i < sectors.length; i++) {
            if (sectors[i] == null) {
                result++;
            }
        }
        result = result * sectorSize;
        return result;
    }


    public void draw(int panelWidth, Graphics g) {

        int stringLength = (panelWidth - 20) / ELEMENT_SIDE;

        g.setColor(Color.BLUE);
        for (int i = 0; i < sectors.length; i++) {
            if (sectors[i] != null) {
                g.fillRect(10 + (i % stringLength) * ELEMENT_SIDE,
                        10 + (i / stringLength) * ELEMENT_SIDE, ELEMENT_SIDE, ELEMENT_SIDE);
            }
        }

        g.setColor(Color.BLACK);
        for (int i = 0; i < sectors.length; i++) {
            g.drawRect(10 + (i % stringLength) * ELEMENT_SIDE, 10 + (i / stringLength) * ELEMENT_SIDE, ELEMENT_SIDE, ELEMENT_SIDE);
        }

        g.setColor(Color.RED);
        for (int i = 0; i < sectors.length; i++) {
            if (sectors[i] != null && sectors[i].getMode() == SectorMode.Selected) {
                g.fillRect(10 + (i % stringLength) * ELEMENT_SIDE,
                        10 + (i / stringLength) * ELEMENT_SIDE, ELEMENT_SIDE, ELEMENT_SIDE);
            }
        }
    }

    public void disSelect() {
        for (Sector sector : sectors) {
            if (sector != null) {
                sector.setUnselected();
            }
        }
    }

    public void enlarge(Sector sector, int extraSize) {
        if (sector.getNext() != null) {
            this.enlarge(sector.getNext(), extraSize);
            return;
        } else {
            sector.setNext(setSector(extraSize));
        }
    }

    public void delete(File file) {

        if (file instanceof Catalog) {
            for (File f : ((Catalog) file).files
            ) {
                delete(f);
            }
        } else {
            delete(file.getiNode());
        }
        return;
    }

    public void delete(INode inode){
        for (Sector sector: inode.sectors
             ) {
            for(int i=0;i<sectors.length;i++){
                if(sectors[i]==sector){
                    sectors[i]=null;
                    break;
                }
            }
        }
    }

    private void recDeleteSector(Sector sector) {
        if (sector.getNext() != null) {
            recDeleteSector(sector.getNext());
        }
        for (int i = 0; i < sectors.length; i++) {
            if (sectors[i] == sector) {
                sectors[i] = null;
            }
        }
    }

}
