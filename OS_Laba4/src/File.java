import javax.swing.tree.DefaultMutableTreeNode;

/*Внутри файла начало цепочки секторов, имя и набор методов для работы с файлом
* Каталог наследует файл, позволяя складывать в него другие файлы и каталоги*/
public class File {

    public String name;
    private Sector sector;
    public int size=0;

    public File(String name, Sector sector) {
        this.name = name;
        this.sector = sector;
    }

    public File(String str) {
        this.name = str;
    }

    public String toString() {
        return name;
    }

    public DefaultMutableTreeNode getDefaultMutableTreeNode() {
        DefaultMutableTreeNode TreeNode = new DefaultMutableTreeNode(this, false);
        return TreeNode;
    }

    public void enSelect() {
        if (sector != null) {
            sector.setSelected();
        }
    }

    public Sector getSector() {
        return sector;
    }

    public int getSize(int sectorSize) {
        int result = 0;
        if (sector != null) {
            result = sector.getSize(sectorSize);
        }
        return result;
    }

    public String getName() {
        return name;
    }

    protected void setName(String newName) {
        name = newName;
    }

}
