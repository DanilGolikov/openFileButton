/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jmeter.control.gui;

import javax.swing.*;


import org.apache.jmeter.control.IncludeController;
import org.apache.jmeter.control.gui.AbstractControllerGui;
import org.apache.jmeter.gui.TestElementMetadata;
import org.apache.jmeter.gui.util.FilePanel;
import org.apache.jmeter.gui.util.MenuFactory;

import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.gui.layout.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

@TestElementMetadata(labelResource = "include_controller")
public class IncludeControllerGui extends AbstractControllerGui implements ActionListener
{

    private static final long serialVersionUID = 240L;

    private static final Logger log = LoggerFactory.getLogger(IncludeController.class);

    private String nowPath;

    private final FilePanel includePanel =
            new FilePanel(JMeterUtils.getResString("include_path"), ".jmx"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * Initializes the gui panel for the ModuleController instance.
     */
    public IncludeControllerGui() {
        init();
    }

    @Override
    public String getLabelResource() {
        return "include_controller";//$NON-NLS-1$
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(TestElement el) {
        super.configure(el);
        IncludeController controller = (IncludeController) el;
        this.includePanel.setFilename(controller.getIncludePath());


    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TestElement createTestElement() {
        IncludeController mc = new IncludeController();
        configureTestElement(mc);
        return mc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modifyTestElement(TestElement element) {
        configureTestElement(element);
        IncludeController controller = (IncludeController)element;
        controller.setIncludePath(this.includePanel.getFilename());
    }



    /**
     * Implements JMeterGUIComponent.clearGui
     */
    @Override
    public void clearGui() {
        super.clearGui();
        includePanel.clearGui();
    }

    @Override
    public JPopupMenu createPopupMenu() {
        JPopupMenu menu = new JPopupMenu();
        MenuFactory.addEditMenu(menu, true);
        MenuFactory.addFileMenu(menu);
        return menu;
    }




    private void init() { // WARNING: called from ctor so must not be overridden (i.e. must be private or final)
        setLayout(new VerticalLayout(9, VerticalLayout.BOTH, VerticalLayout.TOP));
        setBorder(makeBorder());
        add(makeTitlePanel());

        add(includePanel);



        JPanel openPanel = new JPanel(new VerticalLayout());

        JButton openButton = new JButton("Open File");
        openButton.addActionListener(this);
        openButton.setActionCommand("openJMX");
        openButton.setToolTipText("Update: Golikov Daniil");

        openPanel.add(openButton);


        //openPanel.add(new JLabel("Update: Golikov Daniil"));
        add(openPanel);



    }

    @Override
    public void actionPerformed(ActionEvent action) {
        log.debug("Hello from include controller. Command: " + action.getActionCommand() + "\n" + "Input path: " + this.includePanel.getFilename());
        if (action.getActionCommand().equals("openJMX")) {
            try {
                File file = new File (this.includePanel.getFilename());
                Desktop desktop = Desktop.getDesktop();
                desktop.open(file);
            } catch (IOException e) {
                log.error(e.toString());
            }

        }
    }


}
