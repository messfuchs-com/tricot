package com.messfuchs.geo.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TricotControl {

    private JFrame mainFrame;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private JLabel msglabel;

    private static final int FRAMEWIDTH = 640;
    private static final int FRAMEHEIGHT = 480;
    private static final int FRAMEMARGIN = 12;

    public TricotControl(){
        prepareGUI();
    }
    public static void main(String[] args){
        TricotControl swingLayoutDemo = new TricotControl();
        swingLayoutDemo.showCardLayoutDemo();
    }
    private void prepareGUI(){
        mainFrame = new JFrame("Java SWING Examples");
        mainFrame.setSize(FRAMEWIDTH, FRAMEHEIGHT);
        mainFrame.setLayout(new GridLayout(4, 0));

        controlPanel = new JPanel();

        statusLabel = new JLabel("", JLabel.LEFT);
        statusLabel.setSize(FRAMEWIDTH - FRAMEMARGIN, 10*FRAMEMARGIN);

        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });

        mainFrame.add(controlPanel);
        mainFrame.add(statusLabel);
        mainFrame.setVisible(true);
    }

    private void showCardLayoutDemo(){

        final JPanel inputPanel = new JPanel();
        final JPanel outputPanel = new JPanel();

        // inputPanel.setBackground(Color.BLACK);
        inputPanel.setSize(FRAMEWIDTH - FRAMEMARGIN, 25*FRAMEMARGIN);
        outputPanel.setSize(FRAMEWIDTH - FRAMEMARGIN, 25*FRAMEMARGIN);;

        CardLayout layout = new CardLayout(1, 1);
        layout.setHgap(FRAMEMARGIN);
        layout.setVgap(FRAMEMARGIN);
        inputPanel.setLayout(layout);

        GridBagLayout gsiLayout = new GridBagLayout();
        // gsiLayout.setHgap(FRAMEMARGIN/2);
        // gsiLayout.setVgap(FRAMEMARGIN/2);

        JPanel gsiInPanel = new JPanel();
        gsiInPanel.setLayout(gsiLayout);
        // gsiInPanel.setSize(FRAMEWIDTH - FRAMEMARGIN, 25*FRAMEMARGIN);
        GridBagConstraints gbc = new GridBagConstraints();

        JTextField gsiInFileField = new JTextField("INPUT.GSI", 2);

        final JFileChooser fileDialog = new JFileChooser();
        JButton inFileDialogButton = new JButton("...");
        inFileDialogButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            int returnVal = fileDialog.showOpenDialog(mainFrame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
              java.io.File file = fileDialog.getSelectedFile();
              statusLabel.setText("File Selected :" + file.getName());
              gsiInFileField.setText(file.getName());
            } else {
              statusLabel.setText("Open command cancelled by user." );
            }
          }
        });


        // FileChooser

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 10;
        gbc.gridx = 0;
        gbc.gridy = 0;

        gsiInPanel.add(new JLabel("GSI Input File"), gbc);
        gbc.gridwidth = GridBagConstraints.RELATIVE;
        gsiInPanel.add(gsiInFileField, gbc);
        gsiInPanel.add(inFileDialogButton, gbc);

        // SiteIndicator
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gsiInPanel.add(new JLabel("SiteIndicator "));
        gsiInPanel.add(new JLabel());
        gsiInPanel.add(new JTextField("1"));

        // StationIndicator
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gsiInPanel.add(new JLabel("StationIndicator "));
        gsiInPanel.add(new JLabel());
        gbc.gridwidth = 3;

        gsiInPanel.add(new JTextField("11"));

        JPanel csvInPanel = new JPanel(new FlowLayout());
        csvInPanel.add(new JLabel("Name:"));
        csvInPanel.add(new JTextField(2*FRAMEMARGIN));

        inputPanel.add("GSI", gsiInPanel);
        inputPanel.add("CSV", csvInPanel);

        final DefaultComboBoxModel inputPanelName = new DefaultComboBoxModel();

        inputPanelName.addElement("GSI");
        inputPanelName.addElement("CSV");
        final JComboBox listCombo = new JComboBox(inputPanelName);

        listCombo.setSelectedIndex(0);
        JScrollPane listComboScrollPane = new JScrollPane(listCombo);
        // JButton showButton = new JButton("Show");

        listCombo.addActionListener(new ActionListener() {
            // showButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String data = "";
                if (listCombo.getSelectedIndex() != -1) {
                    CardLayout cardLayout = (CardLayout)(inputPanel.getLayout());
                    cardLayout.show(inputPanel,
                            (String)listCombo.getItemAt(listCombo.getSelectedIndex()));
                }
                statusLabel.setText(data);
            }
        });
        controlPanel.add(listComboScrollPane);
        // controlPanel.add(showButton);
        controlPanel.add(inputPanel);
        mainFrame.setVisible(true);
    }
}
