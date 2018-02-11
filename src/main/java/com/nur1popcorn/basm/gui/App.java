/*
 * Copyright (C) Keanu Poeschko - All Rights Reserved
 * Unauthorized copying of this file is strictly prohibited
 *
 * Created by Keanu Poeschko <nur1popcorn@gmail.com>, August 2017
 * This file is part of {BASM}.
 *
 * Do not copy or distribute files of {BASM} without permission of {Keanu Poeschko}
 *
 * Permission to use, copy, modify, and distribute my software for
 * educational, and research purposes, without a signed licensing agreement
 * and for free, is hereby granted, provided that the above copyright notice
 * and this paragraph appear in all copies, modifications, and distributions.
 *
 * {BASM} is based on this document: https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html
 *
 */

package com.nur1popcorn.basm.gui;

import com.nur1popcorn.basm.gui.components.DraggableTabbedPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

import static com.nur1popcorn.basm.BASM.*;

public class App extends JFrame {

    private final JMenu plugins = new JMenu("Plugins");

    public static void main(String[] args) {
        EventQueue.invokeLater(App::new);
    }

    // prevent construction :/
    private App() {
        super(NAME + " - " + VERSION + " developed by " + Arrays.toString(AUTHORS));

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        loadConfig();
        setup();

        setVisible(true);
    }

    private void loadConfig() { //TODO: load config.
        setSize(new Dimension(1270, 840));
    }

    private void setup() {
        final JMenuBar menuBar = new JMenuBar();
        {
            final JMenu file = new JMenu("File");
            file.add(new JMenuItem("Open"));
            file.add(new JMenuItem("Save"));
            file.add(new JMenuItem("Exit"));
            menuBar.add(file);
        }

        {
            menuBar.add(plugins);
        }
        setJMenuBar(menuBar);

        final JSplitPane vPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        {
            final JSplitPane hPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            hPane.setLeftComponent(new JTree());

            JTabbedPane tabPane = new DraggableTabbedPane();
            tabPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

            for(int i = 0; i < 50; i++)
                tabPane.addTab("test" + i, null);

            hPane.setRightComponent(tabPane);
            //TODO: load from config weight.
            hPane.setResizeWeight(0.15);

            vPane.setLeftComponent(hPane);
            vPane.setRightComponent(new JTabbedPane());
            vPane.setResizeWeight(0.75);
        }
        add(vPane, BorderLayout.CENTER);
    }
}
