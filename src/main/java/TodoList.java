import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;


public class TodoList {
    private JList list;
    private JTextField textField1;
    private JButton recoverButton;
    private JButton removeButton;
    private JButton saveButton;
    private JButton addButton;
    private JPanel Todo;

    public static void main(String[] args) {
        JFrame frame = new JFrame("To-Do List");
        frame.setContentPane(new TodoList().Todo);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(true);
    }

    DefaultListModel<String> listModel;
    boolean flag; // for stopping multiple entries to file

    public TodoList() {
        {
            listModel = new DefaultListModel<>();
            flag = true;
        }

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = textField1.getText();
                if (str.equals("") || str.equalsIgnoreCase("Type Here")) {
                    textField1.setText("Type Here"); // show message if text field is empty
                } else {
                    listModel.addElement(str); // add string to list model
                    list.setModel(listModel); // set list model to list
                    textField1.setText(""); // clear text field
                    flag = true;
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int val = list.getModel().getSize(); // get the size of list and store in variable
                PrintWriter writer = null;
                try {
                    writer = new PrintWriter("List.txt");
                    writer.println(val); // save size of the list to file first to help store number of entries to file using loop
                    for (int i = 0; i < val; i++) {
                        writer.println(list.getModel().getElementAt(i)); // get the element from list model via index value
                    }
                } catch (Exception exception) {
                    System.out.println("" + exception);
                } finally {
                    writer.close();
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listModel.removeAllElements();
                list.setModel(listModel);
            }
        });

        recoverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (flag) {
                    BufferedReader bufferedReader = null;
                    try {
                        bufferedReader = new BufferedReader(new FileReader("List.txt"));
                        int val = Integer.parseInt(bufferedReader.readLine()); // read the first line from file (integer value) - number of entries in file
                        for (int i = 0; i < val; i++) {
                            String ss = bufferedReader.readLine(); // read data from file
                            listModel.addElement(ss); // add data to list
                        }
                        list.setModel(listModel); // store list model to list object
                        flag = false; // control multiple entries
                    } catch (Exception exception) {
                        System.out.println("" + exception);
                    } finally {
                        try {
                            bufferedReader.close();
                        } catch (Exception exception) {
                            System.out.println("" + exception);
                        }

                    }
                }
            }
        });
    }
}

