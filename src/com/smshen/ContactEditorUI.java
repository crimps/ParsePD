/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smshen;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import com.smshen.utils.PDM;
import com.smshen.utils.PDMColumn;
import com.smshen.utils.PDMTable;
import com.smshen.utils.Parser;

import java.awt.Frame;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * @author ice
 * @update 2016/12/07 crimps 添加处理cdm格式功能
 */
public class ContactEditorUI extends javax.swing.JFrame {

    private final static String FILE_STUFFIX_CDM = ".cdm";
    private final static String FILE_STUFFIX_PDM = ".pdm";

    /**
     * Creates new form ContactEditorUI
     */
    public ContactEditorUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    // Generated using JFormDesigner Evaluation license - unknown
    private void initComponents() {
        jMenuBar1 = new JMenuBar();
        jMenu3 = new JMenu();
        jMenuItem1 = new JMenuItem();
        jMenuItem2 = new JMenuItem();
        jMenu2 = new JMenu();
        jScrollPane2 = new JScrollPane();
        jTree1 = new JTree();
        jScrollPane3 = new JScrollPane();
        jTable1 = new JTable();

        //======== this ========
        setDefaultCloseOperation(3);
        setExtendedState(6);
        Container contentPane = getContentPane();

        //======== jMenuBar1 ========
        {

            //======== jMenu3 ========
            {
                jMenu3.setText("\u6587\u4ef6");

                //---- jMenuItem1 ----
                jMenuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
                jMenuItem1.setText("\u6253\u5f00");
                jMenuItem1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem1ActionPerformed(e);
                    }
                });
                jMenu3.add(jMenuItem1);

                //---- jMenuItem2 ----
                jMenuItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));
                jMenuItem2.setText("\u9000\u51fa");
                jMenuItem2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem2ActionPerformed(e);
                    }
                });
                jMenu3.add(jMenuItem2);
            }
            jMenuBar1.add(jMenu3);

            //======== jMenu2 ========
            {
                jMenu2.setText("\u5173\u4e8e");
            }
            jMenuBar1.add(jMenu2);
        }
        setJMenuBar(jMenuBar1);

        //======== jScrollPane2 ========
        {
            jScrollPane2.setViewportView(jTree1);
        }

        //======== jScrollPane3 ========
        {

            //---- jTable1 ----
            jTable1.setModel(new DefaultTableModel());
            jScrollPane3.setViewportView(jTable1);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(jScrollPane2)
                .addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
        );
        pack();
        setLocationRelativeTo(getOwner());
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);

        File file = null;
        if (JFileChooser.APPROVE_OPTION == jfc.showOpenDialog(this)) {
            file = jfc.getSelectedFile();
            try {
                PDM p = new PDM();
                if (file.getName().endsWith(FILE_STUFFIX_PDM)) {
                    p = new Parser().pdmParser(file.getPath());
                }
                if (file.getName().endsWith(FILE_STUFFIX_CDM)) {
                    p = new Parser().cdmParse(file.getPath());
                }
                DefaultMutableTreeNode top = new DefaultMutableTreeNode("表");


                for (PDMTable t : p.getTables()) {
                    System.out.println("table-->" + t.getName() + ", code-->" + t.getCode());
                    DefaultMutableTreeNode child = new DefaultMutableTreeNode(t);
                    top.add(child);
                }
                jTree1.setModel(new DefaultTreeModel(top));
                jTree1.addTreeSelectionListener(new TreeSelectionListener() {
                    @Override
                    public void valueChanged(TreeSelectionEvent e) {
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree1
                                .getLastSelectedPathComponent();

                        if (node == null)
                            return;

                        Object object = node.getUserObject();
                        if (node.isLeaf()) {
                            PDMTable pdmt = (PDMTable) object;
                            List<String> keyColumnIdList = new ArrayList<String>();
                            if (null != pdmt.getPrimaryKey() && null != pdmt.getPrimaryKey().getColumns()) {
                                List<PDMColumn> primaryKeyList = pdmt.getPrimaryKey().getColumns();
                                for (PDMColumn keyColumn : primaryKeyList) {
                                    keyColumnIdList.add(keyColumn.getId());
                                }
                            }
                            ArrayList<PDMColumn> cols = pdmt.getColumns();
                            String[] columnNames = {"是否主键", "名称", "CODE", "数据类型", "备注"};
                            cols.trimToSize();
                            Object[][] data = new Object[cols.size()][columnNames.length];

                            int i = 0;
                            for (PDMColumn col : cols) {
                                if (keyColumnIdList.contains(col.getId())) {
                                    data[i][0] = "     ✓";
                                } else {
                                    data[i][0] = "";
                                }
                                data[i][1] = col.getName();
                                data[i][2] = col.getCode();
                                data[i][3] = col.getDataType();
                                data[i][4] = col.getComment();
                                i++;
                            }
                            int s = Frame.MAXIMIZED_BOTH;
                            TableModel dataMode = new DefaultTableModel(data, columnNames);
                            jTable1.setModel(dataMode);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ContactEditorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ContactEditorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ContactEditorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ContactEditorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ContactEditorUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JMenuBar jMenuBar1;
    private JMenu jMenu3;
    private JMenuItem jMenuItem1;
    private JMenuItem jMenuItem2;
    private JMenu jMenu2;
    private JScrollPane jScrollPane2;
    private JTree jTree1;
    private JScrollPane jScrollPane3;
    private JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
