/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout.Group;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

/**
 *
 * @author SuerteMind
 */
public class NewJPanel extends javax.swing.JPanel {
	
	String STR = "Button is preesed";
	ActionListener listener = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent ae) {
            switch ( ((JRadioButton)ae.getSource()).getText() ) {
            case "RadioButton1" :
                STR = "Button is pressed, RadioButton1 active";
                break;
            case "RadioButton2" :
            	STR = "Button is pressed, RadioButton2 active";
                break;
            default:
            	STR = "Button is pressed, RadioButtons no active";
                break;
            }
        }
    };

    private char c;

    /**
     * Creates new form NewJPanel
     */
    public NewJPanel() {

        initComponents();
//        setEnterKey("a");
        initEnterKey();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jRadioButton1 = new JRadioButton();
        
        
        jRadioButton1.addActionListener(listener);
        jRadioButton1.setSelected(true);

        jRadioButton2 = new JRadioButton();
        
        jRadioButton2.addActionListener(listener);
        jRadioButton2.setSelected(true);
        
        ButtonGroup group = new ButtonGroup();
        group.add(jRadioButton1);
        group.add(jRadioButton2);
        
        setToolTipText("");

        jLabel1.setText("label");
        
        jRadioButton1.setText("RadioButton1");
        
        jRadioButton2.setText("RadioButton2");

        jButton1.setText("button");
        jButton1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addComponent(jRadioButton1)
                .addComponent(jRadioButton2)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    )
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        JOptionPane.showMessageDialog(this, STR);
    }//GEN-LAST:event_jButton1ActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private JRadioButton jRadioButton1;
    private JRadioButton jRadioButton2;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

    private void initEnterKey() {
        jButton1.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == c) {
                    jButton1.doClick();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }
}


