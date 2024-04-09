package com.acer.main;

import com.acer.event.EventMenuSelected;
import com.acer.form.Form_1;
import com.acer.form.Form_2;
import com.acer.form.Form_3;
import com.acer.form.Form_Home;
import com.acer.model.CongViec;
import com.acer.model.TaiKhoan;
import com.acer.socket.ClientSocket;
import com.acer.swing.TestPanel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class Main extends javax.swing.JFrame {

    private String username;
    private ClientSocket clientSocket;
    private Gson gson = new Gson();
    private java.lang.reflect.Type listType = new TypeToken<List<CongViec>>() {
    }.getType();
    public static List<CongViec> congViecList;

    public Main() throws IOException {
        clientSocket = new ClientSocket();
        initComponents();
//        setBackground(new Color(0, 0, 0, 0));

    }

    private void loadForm(String output) throws InterruptedException, IOException {
//        String[] infor = clientSocket.receiveData().split("\\|");
//        Thread.sleep(5000);
//        txtUser.setText(infor[0]);
//        txtPass.setText(infor[1]);
//        System.out.println(infor[0]+" "+infor[1]);
        congViecList = gson.fromJson(output, listType);
        panelLogin.setVisible(false);
        remove(panelLogin);
        TestPanel testPanel = new TestPanel(Main.this, output);
        setLayout(new BorderLayout());
        add(testPanel, BorderLayout.CENTER);
    }

//    private void displayHomeForm() {
//    // Loại bỏ panel hiện tại
//    remove(panelLogin);
//
//    // Tạo mới form Home và thêm vào frame
//    Form_Home homeForm = new Form_Home();
//    add(homeForm, BorderLayout.CENTER);
//
//    // Cập nhật lại giao diện
//    revalidate();
//    repaint();
//}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelLogin = new com.acer.swing.PanelBorder();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmdSignIn = new com.acer.swing.Button();
        txtUser = new com.acer.swing.TextField();
        txtPass = new com.acer.swing.PasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelLogin.setBackground(new java.awt.Color(255, 255, 255));
        panelLogin.setOpaque(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setOpaque(false);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/acer/icon/logo2.png"))); // NOI18N

        cmdSignIn.setBackground(new java.awt.Color(186, 215, 245));
        cmdSignIn.setForeground(new java.awt.Color(255, 255, 255));
        cmdSignIn.setText("Sign up");
        cmdSignIn.setEffectColor(new java.awt.Color(215, 228, 242));
        cmdSignIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSignInActionPerformed(evt);
            }
        });

        txtUser.setLabelText("Username");

        txtPass.setLabelText("Password");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmdSignIn, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                    .addComponent(txtUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(cmdSignIn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelLoginLayout = new javax.swing.GroupLayout(panelLogin);
        panelLogin.setLayout(panelLoginLayout);
        panelLoginLayout.setHorizontalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addGap(410, 410, 410)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(413, Short.MAX_VALUE))
        );
        panelLoginLayout.setVerticalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(154, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cmdSignInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSignInActionPerformed
        String user = txtUser.getText().trim();
        String pass = String.valueOf(txtPass.getPassword());
        boolean action = true;
        if (user.equals("")) {
            txtUser.setHelperText("Hãy nhập tên người dùng");
            txtUser.grabFocus();
            action = false;
        } else {
            txtUser.setHelperText("");
        }
        if (pass.equals("")) {
            txtPass.setHelperText("Hãy nhập mật khẩu");
            if (action) {
                txtPass.grabFocus();
            }
            action = false;
        } else {
            txtPass.setHelperText("");
        }
//        if (action) {
//        SwingWorker<Void, Void> swingWorker = new SwingWorker<Void, Void>() {
//            @Override
//            protected Void doInBackground() throws Exception {
//                Gson gson = new Gson();
//                String outputGson = gson.toJson(new TaiKhoan(user,pass));
//                try {
//                    clientSocket.sendData("0|"+outputGson+"\n");
//                    String output = clientSocket.receiveData();
//                    if (output.equals("0")) {
//                        System.out.println();
//                        System.out.println("Login thất bại");
//                    } else {
//                        loadForm(output);
//                    }
//                } catch (IOException ex) {
//                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                return null;
//            }
//        };
//        swingWorker.execute();
//    }
        if (action) {
            Gson gson = new Gson();
            String outputGson = gson.toJson(new TaiKhoan(user, pass));
            try {
                clientSocket.sendData("0|" + outputGson + "\n");
                String output = clientSocket.receiveData();
                System.out.println(output);
                if (output.equals("0")) {
                    System.out.println();
                    JOptionPane.showMessageDialog(this, "Đăng nhập không thành công! Vui lòng thử lại.", "Thông báo", JOptionPane.ERROR_MESSAGE);
                } else {
                    username = user;
                    loadForm(output);
                }
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_cmdSignInActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Main().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.acer.swing.Button cmdSignIn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private com.acer.swing.PanelBorder panelLogin;
    private com.acer.swing.PasswordField txtPass;
    private com.acer.swing.TextField txtUser;
    // End of variables declaration//GEN-END:variables
}
