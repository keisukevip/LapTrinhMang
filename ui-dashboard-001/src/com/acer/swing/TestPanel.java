package com.acer.swing;

import com.acer.event.EventMenuSelected;
import com.acer.event.ServerMessageListener;
import com.acer.form.Form_1;
import com.acer.form.Form_2;
import com.acer.form.Form_3;
import com.acer.form.Form_Add_Job;
import com.acer.form.Form_Home;
import com.acer.glasspanepopup.GlassPanePopup;
import com.acer.main.Main;
import com.acer.model.CongViec;
import com.acer.model.ServerListener;
import com.acer.socket.ClientSocket;
import com.google.gson.Gson;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class TestPanel extends javax.swing.JPanel {

    private Form_Home home;
    private Form_Home task;
    private Form_2 form2;
    private Form_3 form3;
//    private ServerMessageListener serverMessageListener;

    public TestPanel(Main main, String output, ServerListener serverListener, ClientSocket clientSocket) {
        initComponents();
        setOpaque(false);
        home = new Form_Home(output, serverListener, clientSocket, 0);
        task = new Form_Home(output, serverListener, clientSocket, 1);
        form2 = new Form_2();
        form3 = new Form_3();
        menu2.initMoving(main);
        menu2.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) {
                switch (index) {
                    case 0:
                        setForm(home);
                        break;
                    case 1:
                        setForm(task);
                        break;
                    case 2:
                        if (!Main.username.equals("admin")) {
                            JOptionPane.showMessageDialog(null, "Chỉ có admin mới dùng được chức năng này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                        Form_Add_Job form_Add_Job = new Form_Add_Job();
                        form_Add_Job.eventOk(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                System.out.println("Gọi okay");
                                String text = form_Add_Job.getText().trim();
                                if (text.equals("")) {
                                    GlassPanePopup.closePopupLast();
                                    return;
                                }
                                Gson gson = new Gson();
                                CongViec congViec = new CongViec(-1, text, "", "PENDING");
                                String gsonData = gson.toJson(congViec);
                                System.out.println(gsonData);
                                try {
                                    clientSocket.sendData("2|" + gsonData + "\n");
                                } catch (IOException ex) {
                                    Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                System.out.println("Thêm công việc thành công");
                                GlassPanePopup.closePopupLast();
                            }
                        });
                        GlassPanePopup.showPopup(form_Add_Job);
                        break;
                    case 3:
                        setForm(form3);
                        break;
                    case 12:
                        new Thread(() -> {
                            System.out.println("THoát");
                            main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            System.exit(0);
                        }).start();
                        break;
                    default:
                    // Xử lý khi index không khớp với bất kỳ case nào
                }
            }
        });
        //  set when system open start with home form
        setForm(new Form_Home(output, serverListener, clientSocket, 0));
    }

    private void setForm(JComponent com) {
        panelBorder1.removeAll();
        panelBorder1.add(com);
        panelBorder1.repaint();
        panelBorder1.revalidate();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menu2 = new com.acer.component.Menu();
        header1 = new com.acer.component.Header();
        panelBorder1 = new com.acer.swing.PanelBorder();

        setPreferredSize(new java.awt.Dimension(1180, 657));

        panelBorder1.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(menu2, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(header1, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)
                    .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(header1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, 641, Short.MAX_VALUE))
            .addComponent(menu2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        super.paintComponent(grphcs);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.acer.component.Header header1;
    private com.acer.component.Menu menu2;
    private com.acer.swing.PanelBorder panelBorder1;
    // End of variables declaration//GEN-END:variables
}
