/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chat_Project.chatApp;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Dell
 */
public class ProfileCellRenderer implements ListCellRenderer {

        String icon_name;

        public ProfileCellRenderer(String icon_name) {
            this.icon_name = icon_name;
        }

        protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
                    isSelected, cellHasFocus);
            renderer.setSize(30, 200);

            renderer.setIcon(Utils.getIcon(icon_name));
            return renderer;
        }

    }