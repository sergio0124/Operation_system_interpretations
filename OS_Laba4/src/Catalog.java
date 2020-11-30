
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

/*Каталог - единица организации, не имеет размера, не подвязан к цепочке секторов,
* хранит ряд рекурсивных методов, необходимых для удаления, переноса и другого.*/
public class Catalog extends File {

    public ArrayList<File> files = new ArrayList<File>();

    public Catalog(String name) {
        super(name,null);
    }

    public void add(File file) {
        for (File f : files
        ) {
            if (f.getName() == file.getName()) {
                file.setName(file.getName() + " copy");
                add(file);
                return;
            }
        }
        files.add(file);
    }

    @Override
    public DefaultMutableTreeNode getDefaultMutableTreeNode() {
        DefaultMutableTreeNode TreeNode = new DefaultMutableTreeNode(this, true);
        for (File file : files) {
            TreeNode.add(file.getDefaultMutableTreeNode());
        }
        return TreeNode;
    }

    @Override
    public void enSelect() {
        for (File file : files
        ) {
            file.enSelect();
        }
    }

    public void deleteFile(File nodeInfo) {
        if (files.remove(nodeInfo)) {
            return;
        }

        for (File file : files
        ) {
            if (file instanceof Catalog) {
                ((Catalog) file).deleteFile(nodeInfo);
            }
        }
        return;
    }

    public void addIntoDisk(Catalog tmp, Disk disk, int sectorSize) {
        for (File file : ((Catalog) tmp).files
        ) {
            if (file instanceof Catalog) {
                Catalog c = new Catalog(file.getName());
                this.add(c);
                c.addIntoDisk((Catalog) file, disk, sectorSize);
            }
            else if (file instanceof File) {
                Sector sector = disk.setSector(file.getSize(sectorSize));
                File f = new File(file.getName(), sector);
                (this).add(f);
            }
        }

    }
}
