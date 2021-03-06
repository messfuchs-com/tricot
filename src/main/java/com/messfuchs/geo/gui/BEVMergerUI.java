/*
 * Copyright 2017 jurgen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.messfuchs.geo.gui;

import com.messfuchs.geo.models.BEVMerger;
import com.messfuchs.geo.models.CoordinateType;
import java.awt.Image;
import java.awt.Toolkit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Properties;
import java.io.IOException;
import java.util.logging.Level;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.ImageIcon;
import org.cts.CoordinateDimensionException;


/**
 *
 * @author jurgen
 */
public class BEVMergerUI extends javax.swing.JFrame {
    
    private static final Logger LOG = LogManager.getLogger(BEVMerger.class);
    private BEVMerger merger;
    
    private CoordinateType coordinateType;

    /**
     * Creates new form BEVPairUI
     */
    public BEVMergerUI() {
        initComponents();
        this.coordinateType = CoordinateType.Geocentric;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fcInMGI = new javax.swing.JFileChooser();
        fcInETRS = new javax.swing.JFileChooser();
        fcOutMerged = new javax.swing.JFileChooser();
        jFileChooser1 = new javax.swing.JFileChooser();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        txtInMGI = new javax.swing.JTextField();
        btnInMGI = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtInETRS = new javax.swing.JTextField();
        btnInETRS = new javax.swing.JButton();
        btnMerge = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtOutMerged = new javax.swing.JTextField();
        btnOutMerged = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        lblStatus = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mnInMGI = new javax.swing.JMenuItem();
        mnInETRS = new javax.swing.JMenuItem();
        mnOut = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mnMerge = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jRadioButtonMenuItemGeocentric = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItemGeographic = new javax.swing.JRadioButtonMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem_Localisation = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mnAbout = new javax.swing.JMenuItem();

        fcInMGI.setDialogTitle("BEV In MGI");
        fcInMGI.setToolTipText("Choose BEV File containing MGI data");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("BEV MGI File:");

        txtInMGI.setText("0000000000_000_PUNKTART_EP_MGI.csv");
        txtInMGI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtInMGIActionPerformed(evt);
            }
        });

        btnInMGI.setText("...");
        btnInMGI.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnInMGIMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnInMGIMouseExited(evt);
            }
        });
        btnInMGI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInMGIActionPerformed(evt);
            }
        });

        jLabel2.setText("BEV ETRS File:");

        txtInETRS.setText("0000000000_000_PUNKTART_EP_ETRS.csv");
        txtInETRS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtInETRSActionPerformed(evt);
            }
        });

        btnInETRS.setText("...");
        btnInETRS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnInETRSMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnInETRSMouseExited(evt);
            }
        });
        btnInETRS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInETRSActionPerformed(evt);
            }
        });

        btnMerge.setText("Merge");
        btnMerge.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMergeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMergeMouseExited(evt);
            }
        });
        btnMerge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMergeActionPerformed(evt);
            }
        });

        jLabel3.setText("OUT File:");

        txtOutMerged.setText("0000000000_000_PUNKTART_EP_MERGE.csv");
        txtOutMerged.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOutMergedActionPerformed(evt);
            }
        });

        btnOutMerged.setText("...");
        btnOutMerged.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnOutMergedMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnOutMergedMouseExited(evt);
            }
        });
        btnOutMerged.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOutMergedActionPerformed(evt);
            }
        });

        lblStatus.setText("BEV Merger UI");
        lblStatus.setToolTipText("Status Bar");
        lblStatus.setFocusable(false);

        jMenu1.setText("Action");

        mnInMGI.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        mnInMGI.setText("Choose Input MGI File");
        mnInMGI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnInMGIActionPerformed(evt);
            }
        });
        jMenu1.add(mnInMGI);

        mnInETRS.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        mnInETRS.setText("Choose Input ETRS File");
        mnInETRS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnInETRSActionPerformed(evt);
            }
        });
        jMenu1.add(mnInETRS);

        mnOut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        mnOut.setText("Choose Output File");
        mnOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnOutActionPerformed(evt);
            }
        });
        jMenu1.add(mnOut);
        jMenu1.add(jSeparator2);

        mnMerge.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        mnMerge.setText("Merge Input Files");
        mnMerge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnMergeActionPerformed(evt);
            }
        });
        jMenu1.add(mnMerge);
        jMenu1.add(jSeparator3);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem2.setText("Quit");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Options");

        buttonGroup1.add(jRadioButtonMenuItemGeocentric);
        jRadioButtonMenuItemGeocentric.setSelected(true);
        jRadioButtonMenuItemGeocentric.setText("Geocentric Coordinates (X, Y, Z)");
        jRadioButtonMenuItemGeocentric.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItemGeocentricActionPerformed(evt);
            }
        });
        jMenu3.add(jRadioButtonMenuItemGeocentric);

        buttonGroup1.add(jRadioButtonMenuItemGeographic);
        jRadioButtonMenuItemGeographic.setText("Geographic Coordinates (decimal: lat, lon, elev)");
        jRadioButtonMenuItemGeographic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItemGeographicActionPerformed(evt);
            }
        });
        jMenu3.add(jRadioButtonMenuItemGeographic);
        jMenu3.add(jSeparator4);

        jMenuItem_Localisation.setText("Calculate Localisation");
        jMenuItem_Localisation.setEnabled(false);
        jMenuItem_Localisation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_LocalisationActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem_Localisation);

        jMenuBar1.add(jMenu3);

        jMenu2.setText("Help");

        mnAbout.setText("About");
        mnAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnAboutActionPerformed(evt);
            }
        });
        jMenu2.add(mnAbout);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1)
                    .addComponent(btnMerge, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtInMGI))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtInETRS)
                                    .addComponent(txtOutMerged, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnInETRS, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnInMGI, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnOutMerged, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(lblStatus, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtInMGI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInMGI))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtInETRS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInETRS))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtOutMerged, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOutMerged))
                .addGap(18, 18, 18)
                .addComponent(btnMerge)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStatus))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtInETRSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtInETRSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtInETRSActionPerformed

    private void txtOutMergedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOutMergedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOutMergedActionPerformed

    private void btnMergeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMergeActionPerformed
        // TODO add your handling code here:
        this.actionMerge();
    }//GEN-LAST:event_btnMergeActionPerformed

    private void btnInMGIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInMGIActionPerformed
        // TODO add your handling code here:
        this.actionChooseMGI();
    }//GEN-LAST:event_btnInMGIActionPerformed

    private void btnInETRSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInETRSActionPerformed
        // TODO add your handling code here:
        this.actionChooseETRS();
    }//GEN-LAST:event_btnInETRSActionPerformed

    private void btnOutMergedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOutMergedActionPerformed
        // TODO add your handling code here:
        this.actionChooseOut();
    }//GEN-LAST:event_btnOutMergedActionPerformed

    private void txtInMGIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtInMGIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtInMGIActionPerformed

    private void btnInMGIMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInMGIMouseEntered
        // TODO add your handling code here:
        this.lblStatus.setText("Choose MGI file");
    }//GEN-LAST:event_btnInMGIMouseEntered

    private void btnInMGIMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInMGIMouseExited
        // TODO add your handling code here:
        this.lblStatus.setText("");
    }//GEN-LAST:event_btnInMGIMouseExited

    private void btnInETRSMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInETRSMouseEntered
        // TODO add your handling code here:
        this.lblStatus.setText("Choose ETRS file");
    }//GEN-LAST:event_btnInETRSMouseEntered

    private void btnInETRSMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInETRSMouseExited
        // TODO add your handling code here:
        this.lblStatus.setText("");
    }//GEN-LAST:event_btnInETRSMouseExited

    private void btnMergeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMergeMouseEntered
        // TODO add your handling code here:
        this.lblStatus.setText("Merge MGI and ETRS files");
    }//GEN-LAST:event_btnMergeMouseEntered

    private void btnMergeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMergeMouseExited
        // TODO add your handling code here:
        this.lblStatus.setText("");
    }//GEN-LAST:event_btnMergeMouseExited

    private void btnOutMergedMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOutMergedMouseEntered
        // TODO add your handling code here:
        this.lblStatus.setText("Choose Output file");
    }//GEN-LAST:event_btnOutMergedMouseEntered

    private void btnOutMergedMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOutMergedMouseExited
        // TODO add your handling code here:
        this.lblStatus.setText("");
    }//GEN-LAST:event_btnOutMergedMouseExited

    private void mnInETRSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnInETRSActionPerformed
        // TODO add your handling code here:
        this.actionChooseETRS();
    }//GEN-LAST:event_mnInETRSActionPerformed

    private void mnInMGIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnInMGIActionPerformed
        // TODO add your handling code here:
        this.actionChooseMGI();
    }//GEN-LAST:event_mnInMGIActionPerformed

    private void mnOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnOutActionPerformed
        // TODO add your handling code here:
        this.actionChooseOut();
    }//GEN-LAST:event_mnOutActionPerformed

    private void mnMergeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnMergeActionPerformed
        // TODO add your handling code here:
        this.actionMerge();
    }//GEN-LAST:event_mnMergeActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void mnAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnAboutActionPerformed
        // TODO add your handling code here:
        final Properties properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream("app.properties"));
            showMessageDialog(null, "Version: " + properties.getProperty("app.version") + "\n" + "Copyright: 2018 by contact@messfuchs.at");
        } catch (IOException e) {
            showMessageDialog(null, "not implemented yet :-(");
        }
    }//GEN-LAST:event_mnAboutActionPerformed

    private void jRadioButtonMenuItemGeocentricActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItemGeocentricActionPerformed
        // TODO add your handling code here:
        this.coordinateType = CoordinateType.Geocentric;
    }//GEN-LAST:event_jRadioButtonMenuItemGeocentricActionPerformed

    private void jRadioButtonMenuItemGeographicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItemGeographicActionPerformed
        // TODO add your handling code here:
        this.coordinateType = CoordinateType.Geographic;
    }//GEN-LAST:event_jRadioButtonMenuItemGeographicActionPerformed

    private void jMenuItem_LocalisationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_LocalisationActionPerformed
        try {
            // TODO add your handling code here:
            this.merger.calculateLocalisation();
        } catch (CoordinateDimensionException ex) {
            java.util.logging.Logger.getLogger(BEVMergerUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(BEVMergerUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem_LocalisationActionPerformed

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
            java.util.logging.Logger.getLogger(BEVMergerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BEVMergerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BEVMergerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BEVMergerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BEVMergerUI().setVisible(true);
            }
        });
    }
    
    public void actionChooseMGI() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV FILES", "csv");
        this.jFileChooser1.setMultiSelectionEnabled(true);
        this.jFileChooser1.setDialogTitle("Choose MGI File(s)");
        this.jFileChooser1.addChoosableFileFilter(filter);
        int returnVal = this.jFileChooser1.showOpenDialog(this);
        if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
            List<java.io.File> fileList = Arrays.asList(this.jFileChooser1.getSelectedFiles());
            List<String> filePathList = new ArrayList();
            fileList.stream().forEach(file -> {
                filePathList.add(file.getAbsolutePath());
            });
            this.txtInMGI.setText(String.join(";", filePathList));
        } else {
            LOG.warn("File access cancelled by user.");
        }
    }
    
    public void actionChooseETRS() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV FILES", "csv");
        this.jFileChooser1.setMultiSelectionEnabled(true);
        this.jFileChooser1.setDialogTitle("Choose ETRS File(s)");
        this.jFileChooser1.addChoosableFileFilter(filter);
        int returnVal = this.jFileChooser1.showOpenDialog(this);
        if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
            /* java.io.File file = this.jFileChooser1.getSelectedFile();
            this.txtInETRS.setText(file.getAbsolutePath()); */
            List<java.io.File> fileList = Arrays.asList(this.jFileChooser1.getSelectedFiles());
            List<String> filePathList = new ArrayList();
            fileList.stream().forEach(file -> {
                filePathList.add(file.getAbsolutePath());
            });
            this.txtInETRS.setText(String.join(";", filePathList));
        } else {
            LOG.warn("File access cancelled by user.");
        }
    }
    
    public void actionChooseOut() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        this.jFileChooser1.setDialogTitle("Choose Output File");
        this.jFileChooser1.setMultiSelectionEnabled(false);
        this.jFileChooser1.addChoosableFileFilter(filter);
        int returnVal = this.jFileChooser1.showOpenDialog(this);
        if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File file = this.jFileChooser1.getSelectedFile();
            this.txtOutMerged.setText(file.getAbsolutePath());
        } else {
            LOG.warn("File access cancelled by user.");
        }  
    }
    
    private void actionMerge() {
        String mergeText = "";
        this.merger = new BEVMerger(
                this.txtInMGI.getText(), 
                this.txtInETRS.getText(),
                this.txtOutMerged.getText()
        );
        this.merger.setCoordinateType(this.coordinateType);
        
        try {
            mergeText = this.merger.convert();
        } catch (java.io.IOException e)  {
            e.printStackTrace();
            LOG.error("There was an Error");
        } catch (java.lang.IllegalArgumentException e) {
            int i = e.toString().indexOf("Mapping for");
            if (i >= 0) {
                int j = e.toString().indexOf("not found, expected");
                this.lblStatus.setText(e.toString().substring(i, j+9) + ", check your input files");
                showMessageDialog(null, e.toString().substring(i, j+9) + ", check your input files");
            } else {
                this.lblStatus.setText("Error while converting");
                showMessageDialog(null, e);
            } 
        }
        this.jMenuItem_Localisation.setEnabled(this.merger.getCoupledPoints() >= 4);
        this.lblStatus.setText(mergeText);
        if (mergeText.length() > 0) {
            showMessageDialog(null, mergeText);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInETRS;
    private javax.swing.JButton btnInMGI;
    private javax.swing.JButton btnMerge;
    private javax.swing.JButton btnOutMerged;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JFileChooser fcInETRS;
    private javax.swing.JFileChooser fcInMGI;
    private javax.swing.JFileChooser fcOutMerged;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem_Localisation;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItemGeocentric;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItemGeographic;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JMenuItem mnAbout;
    private javax.swing.JMenuItem mnInETRS;
    private javax.swing.JMenuItem mnInMGI;
    private javax.swing.JMenuItem mnMerge;
    private javax.swing.JMenuItem mnOut;
    private javax.swing.JTextField txtInETRS;
    private javax.swing.JTextField txtInMGI;
    private javax.swing.JTextField txtOutMerged;
    // End of variables declaration//GEN-END:variables

}
