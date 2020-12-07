import javax.swing.tree.DefaultMutableTreeNode;

/*Внутри файла начало цепочки секторов, имя и набор методов для работы с файлом
* Каталог наследует файл, позволяя складывать в него другие файлы и каталоги*/
public class File {

    public String name;
    private INode iNode = new INode();

    public File(String name, INode inode) {
        this.name = name;
        this.iNode = inode;
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
        if (iNode != null) {
            iNode.setSelected();
        }
    }

    public INode getiNode() {
        return iNode;
    }

    public int getSize(int sectorSize) {
        int result = 0;
        if (iNode != null) {
            result = iNode.getSize(sectorSize);
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
