package com.acer.swing;

import com.acer.glasspanepopup.GlassPanePopup;
import com.acer.main.Main;
import com.acer.model.CongViec;
import com.acer.model.StatusType;
import com.acer.socket.ClientSocket;
import com.google.gson.Gson;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Table extends JTable {

    private int action;
    private ClientSocket clientSocket;

    public Table() {
        setShowHorizontalLines(true);
        setGridColor(new Color(230, 230, 230));
        setRowHeight(40);
        getTableHeader().setReorderingAllowed(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON1) {
                    return;
                }
                int row = rowAtPoint(e.getPoint());

                // Lấy mô hình dữ liệu của bảng
                DefaultTableModel model = (DefaultTableModel) getModel();

                // Lấy dữ liệu từ các cột trong hàng được click
                Object id = model.getValueAt(row, 0); // Giả sử cột đầu tiên là cột ID
                Object tenCongViec = model.getValueAt(row, 1); // Giả sử cột thứ hai là cột Tên công việc
                Object nguoiLam = model.getValueAt(row, 2);
                Object trangThai = model.getValueAt(row, 3);
                System.out.println(id + " " + tenCongViec + " " + nguoiLam + " " + trangThai);
                if (action == 0) {
                    if (trangThai == StatusType.REJECT) {
                        Object[] options = {"Yes", "No"};
                        int result = JOptionPane.showOptionDialog(null, "Bạn có muốn tiếp tục không?", "Confirmation",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                        if (result == JOptionPane.YES_OPTION) {
                            Gson gson = new Gson();
                            CongViec congViec = new CongViec(Integer.parseInt(id.toString()), tenCongViec.toString(), Main.username, "APPROVED");
                            String gsonData = gson.toJson(congViec);
                            try {
                                clientSocket.sendData("1|" + gsonData + "\n");
                                System.out.println("đã gửi");
                                System.out.println(gsonData);
                            } catch (IOException ex) {
                                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            System.out.println(result + " Đã cập nhật thành công");

                        } else if (result == JOptionPane.NO_OPTION) {
                            System.out.println(result + " Đã hủy");
                        } else {
                            System.out.println(result);
                        }
                    }
                } else {
                    System.out.println("task form");
                    Message message = new Message();
                    message.eventOk(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Gson gson = new Gson();
                            String status = message.status();
                            CongViec congViec;
                            congViec = new CongViec(Integer.parseInt(id.toString()), tenCongViec.toString(), Main.username, status);
                            String gsonData = gson.toJson(congViec);
                            System.out.println(gsonData);
                            try {
                                clientSocket.sendData("1|" + gsonData + "\n");
                            } catch (IOException ex) {
                                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            System.out.println("Báo cáo công việc thành công");
                            GlassPanePopup.closePopupLast();
                        }
                    });
                    GlassPanePopup.showPopup(message);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Sự kiện kiểm tra chuột phải");
                if (e.getButton() == MouseEvent.BUTTON3) {
                    System.out.println("Chuột phải được ấn");
                    int row = rowAtPoint(e.getPoint());
                    DefaultTableModel model = (DefaultTableModel) getModel();
                    Object id = model.getValueAt(row, 0); // Giả sử cột đầu tiên là cột ID
                    Object tenCongViec = model.getValueAt(row, 1); // Giả sử cột thứ hai là cột Tên công việc
                    Object nguoiLam = model.getValueAt(row, 2);
                    Object trangThai = model.getValueAt(row, 3);
                    if (Main.username.equals("admin")) {
                        JPopupMenu popupMenu = new JPopupMenu();

                        // Tạo các menu item trong popup menu
                        JMenuItem deleteMenuItem = new JMenuItem("Delete");

                        // Xử lý sự kiện khi người dùng chọn Delete
                        deleteMenuItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // Lấy mô hình dữ liệu của bảng
                                DefaultTableModel model = (DefaultTableModel) getModel();

                                // Lấy dữ liệu từ các cột trong hàng được click
                                Object id = model.getValueAt(row, 0);
                                try {
                                    clientSocket.sendData("3|" + id + "\n");
                                } catch (IOException ex) {
                                    Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                System.out.println(id);
                            }
                        });

                        // Thêm các menu item vào popup menu
                        popupMenu.add(deleteMenuItem);

                        // Hiển thị popup menu tại vị trí chuột
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }

        });
        getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
                TableHeader header = new TableHeader(o + "");
                if (i1 == 3) {
                    header.setHorizontalAlignment(JLabel.CENTER);
                }
                return header;
            }
        });
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean selected, boolean bln1, int i, int i1) {
                if (i1 != 3) {
                    Component com = super.getTableCellRendererComponent(jtable, o, selected, bln1, i, i1);
                    com.setBackground(Color.WHITE);
                    setBorder(noFocusBorder);
                    if (selected) {
                        com.setForeground(new Color(15, 89, 140));
                    } else {
                        com.setForeground(new Color(102, 102, 102));
                    }
                    return com;
                } else {
                    StatusType type = (StatusType) o;
                    CellStatus cell = new CellStatus(type);
//                    cell.addMouseListener(new MouseAdapter() {
//                        @Override
//                        public void mouseClicked(MouseEvent e) {
//                            System.out.println(cell.getType());
//                        }
//                    });
                    return cell;
                }
            }
        });
    }

    public void addClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void addAction(int action) {
        this.action = action;
    }

    public void addRow(Object[] row) {
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.addRow(row);
    }
}
