package com.acer.form;

import com.acer.event.ServerMessageListener;
import com.acer.main.Main;
import com.acer.model.CongViec;
import com.acer.model.Model_Card;
import com.acer.model.ServerListener;
import com.acer.model.StatusType;
import com.acer.socket.ClientSocket;
import com.acer.swing.ScrollBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.awt.Color;
import java.lang.reflect.Type;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

public class Form_Home extends javax.swing.JPanel {

    private int action;

    private java.lang.reflect.Type listType = new TypeToken<List<CongViec>>() {
    }.getType();
//    private ServerListener serverListener;

    public Form_Home(String output, ServerListener serverListener, ClientSocket clientSocket, int action) {
        initComponents();
        this.action = action;
        table.addClientSocket(clientSocket);
        table.addAction(action);
        if (action == 0) {
            jLabel1.setText("ALL TASKS");
        } else {
            jLabel1.setText("MY TASK");
        }
        serverListener.addServerMessageListener(new ServerMessageListener() {
            @Override
            public void onMessageReceived(String message) {
                check(message);
            }

        });
        spTable.setVerticalScrollBar(new ScrollBar());
        spTable.getVerticalScrollBar().setBackground(Color.WHITE);
        spTable.getViewport().setBackground(Color.WHITE);
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        spTable.setCorner(JScrollPane.UPPER_RIGHT_CORNER, p);
        for (CongViec congViec : Main.congViecList) {
            if (action == 0) {
                table.addRow(new Object[]{congViec.getId(), congViec.getTenCongViec(), congViec.getNguoiThucHien(), StatusType.fromString(congViec.getTrangThai())});
            } else {
                if (congViec.getNguoiThucHien().equals(Main.username)) {
                    table.addRow(new Object[]{congViec.getId(), congViec.getTenCongViec(), congViec.getNguoiThucHien(), StatusType.fromString(congViec.getTrangThai())});
                }
            }
        }
    }

    public void check(String message) {
        String[] output = message.split("\\|");
        if (output[0].equals("1")) {
            Main.congViecList.clear();
            Gson gson = new Gson();
            Main.congViecList = gson.fromJson(output[1], listType);
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            for (CongViec congViec : Main.congViecList) {
                if (action == 0) {
                    table.addRow(new Object[]{congViec.getId(), congViec.getTenCongViec(), congViec.getNguoiThucHien(), StatusType.fromString(congViec.getTrangThai())});
                } else {
                    if (congViec.getNguoiThucHien().equals(Main.username)) {
                        table.addRow(new Object[]{congViec.getId(), congViec.getTenCongViec(), congViec.getNguoiThucHien(), StatusType.fromString(congViec.getTrangThai())});
                    }
                }
            }
        }
        System.out.println("Form_home được cập nhật - " + message);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorder1 = new com.acer.swing.PanelBorder();
        jLabel1 = new javax.swing.JLabel();
        spTable = new javax.swing.JScrollPane();
        table = new com.acer.swing.Table();

        panelBorder1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(127, 127, 127));
        jLabel1.setText("Standard Table Design");

        spTable.setBorder(null);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên công việc", "Người làm", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spTable.setViewportView(table);

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorder1Layout.createSequentialGroup()
                        .addComponent(spTable, javax.swing.GroupLayout.DEFAULT_SIZE, 849, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(panelBorder1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spTable, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(panelBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private com.acer.swing.PanelBorder panelBorder1;
    private javax.swing.JScrollPane spTable;
    private com.acer.swing.Table table;
    // End of variables declaration//GEN-END:variables
}
