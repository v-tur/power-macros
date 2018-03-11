package burp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

public class AddReplacementSetup extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox cboType;
    private JComboBox cboLinkExtract;
    private Replace replaceToEdit;

    //https://stackoverflow.com/questions/4089311/how-can-i-return-a-value-from-a-jdialog-box-to-the-parent-jframe

    public Replace showDialog(){
        setSize(new Dimension(400, 210));
        setResizable(false);
        setVisible(true);
        BurpExtender.getInstance().stdout.println("returning...");
        return replaceToEdit;
    }

    public AddReplacementSetup(Replace replaceToEdit) {
        this.replaceToEdit = replaceToEdit;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        addExtractionsToCombo();
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
    private void addExtractionsToCombo(){
        Map<String, Extraction> extModelMap = BurpExtender.extractTableModel.getExtModelMap();
        for (Map.Entry<String, Extraction> entry:  extModelMap.entrySet()) {
            cboLinkExtract.addItem(entry.getKey());
        }
    }



    private void onOK() {
        // add your code here
        replaceToEdit.addLinkedExtraction(GlobalExtractionModel._getExtraction(cboLinkExtract.getSelectedIndex()));
        BurpExtender.getInstance().stdout.println("in OK. Adding: " + GlobalExtractionModel._getExtraction(cboLinkExtract.getSelectedIndex()).getId());
        setVisible(false);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


    public static void main(String[] args) {
//        AddReplacementSetup dialog = new AddReplacementSetup(replaceToEdit);
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
