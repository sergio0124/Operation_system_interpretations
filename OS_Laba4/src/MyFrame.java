import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*    Итак, окно содержит в себе все поля: и диск, и файловую систему.
*     Работа с объектами производится напрямую, чтобы было понятнее и проще.
*     Далее в комментариях я объясню работы каждого из элементов кода*/



public class MyFrame implements TreeSelectionListener {

    final int MAX_CARACITY = 1500;
    final int MIN_CAPASITY = 4;

    final int MAX_SECTOR_SIZE = 8;
    final int MIN_SECTOR_SIZE = 1;

    final int OBJECT_WIDTH = 250;
    final int HEIGHT = 550;
    final int BUTTON_HEIGHT = 30;

    Disk disk;
    FileSystem fileSystem;

    DefaultMutableTreeNode tmpCopy = null;
    DefaultMutableTreeNode tmpReplace = null;
    MyPanel panel;
    JFrame frame;
    JTree Tree;
    JButton deleteButton;
    JButton addFileButton;
    JButton addCatalogButton;
    JButton copyButton;
    JButton pasteButton;
    JButton replaceCopyButton;
    JButton replaceEnterButton;
    JButton enlargeButton;

    public MyFrame() {

        int diskSize;
        int sectorSize;

        /*   Вызываются диалоговые окна, которые позволяют задать размеры диска и его сектора перед началом работы*/

        while (true) {
            try {
                diskSize = Integer.parseInt(JOptionPane.showInputDialog(frame, "Введите размер диска (не более "
                        + MAX_CARACITY + " и не менее " + MIN_CAPASITY + ")"));
                if (diskSize > MAX_CARACITY || diskSize < MIN_CAPASITY) {
                    throw new Exception();
                } else {
                    break;
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Были введены некорректные данные", "Создание диска", JOptionPane.ERROR_MESSAGE);
            }
        }

        while (true) {
            try {
                sectorSize = Integer.parseInt(JOptionPane.showInputDialog(frame, "Введите размер сектора диска (не более "
                        + MAX_SECTOR_SIZE + " и не менее " + MIN_SECTOR_SIZE + ")"));
                if (sectorSize > MAX_SECTOR_SIZE || sectorSize < MIN_SECTOR_SIZE) {
                    throw new Exception();
                } else {
                    break;
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Были введены некорректные данные", "Определение размера сектора", JOptionPane.ERROR_MESSAGE);
            }
        }

        frame = new JFrame();
        frame.setBounds(100, 100, 1000, HEIGHT + 50);
        frame.setTitle("File System");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        disk = new Disk(diskSize, sectorSize);
        fileSystem = new FileSystem();

        panel = new MyPanel(disk);
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.setBackground(Color.WHITE);
        panel.setBounds(10, 10, OBJECT_WIDTH, HEIGHT);
        frame.getContentPane().add(panel);

        DefaultMutableTreeNode TreeNode = fileSystem.getCatalog().getDefaultMutableTreeNode();
        Tree = new JTree(TreeNode);
        Tree.setBounds(OBJECT_WIDTH + 20, 10, OBJECT_WIDTH, HEIGHT);
        JScrollPane treeView = new JScrollPane(Tree);
        treeView.setBounds(OBJECT_WIDTH + 20, 10, OBJECT_WIDTH, HEIGHT);
        frame.getContentPane().add(treeView);
        Tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
        Tree.addTreeSelectionListener(this);


/*      Берем последный выбранный файл, затем вычленяем из него ссылку на объект типа File (все TreeNode созданы
        от объектов этого типа).
        Затем мы удаляем из дерева этот Узел,
        Удаляем объект из фаловой системы,
        Удаляем файл с диска (там с помощью рекурсии удаляются файлы, файлы в каталогах, каталоги же не имеют веса,
        их удалять не нужно)*/
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                        Tree.getLastSelectedPathComponent();
                if (node == null) {
                    return;
                }
                DefaultMutableTreeNode parentNode;
                try {
                    parentNode = (DefaultMutableTreeNode) node.getParent();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return;
                }
                if (node == tmpReplace) {
                    tmpReplace = null;
                }
                parentNode.remove(node);

                Object file = node.getUserObject();

                fileSystem.deleteFile((File) file);
                if(file instanceof Catalog) {
                    disk.delete((Catalog)file);
                }
                else{
                    disk.delete(((File) file).getiNode());
                }
                Tree.updateUI();
                panel.repaint();
            }
        });
        deleteButton.setBounds(2 * OBJECT_WIDTH + 30, 10, OBJECT_WIDTH, BUTTON_HEIGHT);
        deleteButton.setVisible(true);
        frame.getContentPane().add(deleteButton);


        /*Сначала создаются сектора (используется метод Размещения файла в виде связанного списка кластеров:
        то есть создаётся цепочка занятых секторов, конец цепочки передаётся в файл при создании,
        то есть можно распутать список секторов, занятых фалом, из файла)
        Затем ссылка на сектор передаётся файлу при создании,
        Файл добавляется к каталогу, Узел которого мы выделили
        Затем создаётся Узел с файлом, который добавляется к дереву*/
        addFileButton = new JButton("Add File");
        addFileButton.addActionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                    Tree.getLastSelectedPathComponent();
            if (node == null) {
                return;
            }
            System.out.println("addFile");
            Object nodeInfo = node.getUserObject();
            if (nodeInfo instanceof Catalog) {

            } else {
                return;
            }

            int fileSize = 0;
            String fileName = "";

            try {
                fileSize = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter size of the file"));
                fileName = JOptionPane.showInputDialog(frame, "Enter name of the file");
                if (fileSize > disk.getFreeSpace() || fileName.length() > 20 || fileName.length() < 1) {
                    return;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                return;
            }

            INode inode = new INode();
            disk.setSector(inode,fileSize);
            File file = new File(fileName, inode);
            ((Catalog) nodeInfo).add(file);

            panel.repaint();
            node.add(new DefaultMutableTreeNode(file, false));
            Tree.updateUI();
        });
        addFileButton.setBounds(2 * OBJECT_WIDTH + 30, BUTTON_HEIGHT + 20, OBJECT_WIDTH, BUTTON_HEIGHT);
        addFileButton.setVisible(true);
        frame.getContentPane().add(addFileButton);


        /*Тот же принцип, только диск не заполняется, а в итоге получается каталог*/
        addCatalogButton = new JButton("Add Catalog");
        addCatalogButton.addActionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                    Tree.getLastSelectedPathComponent();
            if (node == null) {
                return;
            }
            System.out.println("addFile");
            Object nodeInfo = node.getUserObject();
            if (nodeInfo instanceof Catalog) {
            } else {
                return;
            }

            String fileName = "";

            try {
                fileName = JOptionPane.showInputDialog(frame, "Enter name of the file");
                if (fileName.length() > 20 || fileName.length() < 1) {
                    return;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            Catalog file = new Catalog(fileName);

            ((Catalog) nodeInfo).add(file);
            node.add(new DefaultMutableTreeNode(file));
            panel.repaint();
            Tree.updateUI();
        });
        addCatalogButton.setBounds(2 * OBJECT_WIDTH + 30, 2 * BUTTON_HEIGHT + 30, OBJECT_WIDTH, BUTTON_HEIGHT);
        addCatalogButton.setVisible(true);
        frame.getContentPane().add(addCatalogButton);


        /*Мы копируем выделенный объект, но ссылки на объекты внутри узлов остаются теми же,
        * поэтому нужно будет создавать новые такие же объеты*/
        copyButton = new JButton("Copy");
        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                        Tree.getLastSelectedPathComponent();
                if (node == null) {
                    return;
                }
                try {
                    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return;
                }
                tmpCopy = (DefaultMutableTreeNode) node.clone();
            }
        });
        copyButton.setBounds(2 * OBJECT_WIDTH + 30, BUTTON_HEIGHT * 3 + 40, OBJECT_WIDTH, BUTTON_HEIGHT);
        copyButton.setVisible(true);
        frame.getContentPane().add(copyButton);


        /*Здесь сложная система, которая работает на рекурсии внутри каталога, но связана с диском.
        * Мы в особом методе передаём ссылку на диск, ссылку на клонированный объект, размер сектора.
        * Изнутри созданного каталога мы создаём новый файл, идентивный копированному, если это каталог,
        * то мы параллельно углубляемся внутрь копии и внутрь самого объекта, воспроизводя его на всех уровнях.*/
        pasteButton = new JButton("Paste");
        int finalSectorSize = sectorSize;
        pasteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                        Tree.getLastSelectedPathComponent();
                if (node == null || tmpCopy == null) {
                    System.out.println("tmp is empty");
                    return;
                }

                File parent = (File) node.getUserObject();
                if (parent instanceof Catalog) {

                    File tmp = (File) tmpCopy.getUserObject();
                    if (tmp instanceof Catalog) {
                        Catalog file = new Catalog(tmp.getName());
                        ((Catalog) file).addIntoDisk((Catalog) tmp, disk, finalSectorSize);
                        ((Catalog) parent).add(file);
                        node.add(file.getDefaultMutableTreeNode());

                    } else {

                        Sector sector = disk.setSector(tmp.getSize(finalSectorSize));
                        INode inode = new INode(sector);
                        File file = new File(tmp.getName(), inode);
                        ((Catalog) parent).add(file);
                        node.add(file.getDefaultMutableTreeNode());
                    }


                } else {
                    return;
                }

                panel.repaint();
                Tree.updateUI();
            }
        });
        pasteButton.setBounds(2 * OBJECT_WIDTH + 30, 4 * BUTTON_HEIGHT + 50, OBJECT_WIDTH, BUTTON_HEIGHT);
        pasteButton.setVisible(true);
        frame.getContentPane().add(pasteButton);


        /*Мы копируем ссылку на объект в специально отведенное под это поле*/
        replaceCopyButton = new JButton("Replace Copy");
        replaceCopyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                        Tree.getLastSelectedPathComponent();
                if (node == null) {
                    return;
                }
                try {
                    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return;
                }
                tmpReplace = node;
            }
        });
        replaceCopyButton.setBounds(2 * OBJECT_WIDTH + 30, 5 * BUTTON_HEIGHT + 60, OBJECT_WIDTH, BUTTON_HEIGHT);
        replaceCopyButton.setVisible(true);
        frame.getContentPane().add(replaceCopyButton);


        /*Мы отвязываем ссылки на объект там, где можно, не трогая диск,
        * а потом перевязываем их на другие объекты, куда мы хотим перенести объект*/
        replaceEnterButton = new JButton("Replace Enter");
        replaceEnterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tmpReplace == null) {
                    return;
                }
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                        Tree.getLastSelectedPathComponent();
                if (node == null) {
                    return;
                }
                DefaultMutableTreeNode tmpParent = (DefaultMutableTreeNode) tmpReplace.getParent();
                File fileParent = (File) tmpParent.getUserObject();
                File tmp = (File) tmpReplace.getUserObject();
                tmpParent.remove(tmpReplace);
                node.add(tmpReplace);
                fileSystem.deleteFile(tmp);
                ((Catalog) node.getUserObject()).add(tmp);

                panel.repaint();
                Tree.updateUI();
            }
        });
        replaceEnterButton.setBounds(2 * OBJECT_WIDTH + 30, 6 * BUTTON_HEIGHT + 70, OBJECT_WIDTH, BUTTON_HEIGHT);
        replaceEnterButton.setVisible(true);
        frame.getContentPane().add(replaceEnterButton);


        /*Мы работаем с диском, создавая цепочку секторов новых,
        * а затем мы эту цепочку за конец подсоединяем к концу цепочки внутри файла.
        * Получемтся цепочка удлинённая и связанная*/
        enlargeButton = new JButton("Enlarge file");
        enlargeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                        Tree.getLastSelectedPathComponent();
                if (node == null) {
                    return;
                }
                File file = (File) node.getUserObject();
                if (file instanceof File) {
                } else {
                    return;
                }

                int extraSize = 0;
                try {
                    extraSize = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter size of the file"));
                    if (extraSize > disk.getFreeSpace() || extraSize <= 0) {
                        return;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                    return;
                }

                Sector sector = file.getiNode().getSector();
                disk.enlarge(sector, extraSize);

                disk.disSelect();
                file.enSelect();

                Tree.updateUI();
                panel.repaint();
            }
        });
        enlargeButton.setBounds(2 * OBJECT_WIDTH + 30, 7 * BUTTON_HEIGHT + 80, OBJECT_WIDTH, BUTTON_HEIGHT);
        enlargeButton.setVisible(true);
        frame.getContentPane().add(enlargeButton);

    }

    /*Здесь реализован метод считывания выделения дерева.
    * Этот метод позволяет нам читать последний выбранный Узел файла.*/
    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                Tree.getLastSelectedPathComponent();
        if (node == null) {
            return;
        }
        Object nodeInfo = node.getUserObject();
        if (node.isLeaf()) {
            File file = (File) nodeInfo;
            disk.disSelect();
            file.enSelect();
        } else {
            Catalog file = (Catalog) nodeInfo;
            disk.disSelect();
            file.enSelect();
        }
        panel.repaint();
    }
}
