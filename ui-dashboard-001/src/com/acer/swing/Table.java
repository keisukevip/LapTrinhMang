package com.acer.swing;

import com.acer.model.StatusType;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Table extends JTable {

    public Table() {
        setShowHorizontalLines(true);
        setGridColor(new Color(230, 230, 230));
        setRowHeight(40);
        getTableHeader().setReorderingAllowed(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = rowAtPoint(e.getPoint());

                // Lấy mô hình dữ liệu của bảng
                DefaultTableModel model = (DefaultTableModel) getModel();

                // Lấy dữ liệu từ các cột trong hàng được click
                Object id = model.getValueAt(row, 0); // Giả sử cột đầu tiên là cột ID
                Object tenCongViec = model.getValueAt(row, 1); // Giả sử cột thứ hai là cột Tên công việc
                Object nguoiLam = model.getValueAt(row, 2);
                Object trangThai = model.getValueAt(row, 3);
                if (trangThai == StatusType.REJECT) {
                    Object[] options = {"Yes", "No"};
                    int result = JOptionPane.showOptionDialog(null, "Bạn có muốn tiếp tục không?", "Confirmation",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                    if (result == JOptionPane.YES_OPTION) {
                        System.out.println(result + " Đã cập nhật thành công");

                    } else if (result == JOptionPane.NO_OPTION) {
                        System.out.println(result + " Đã hủy");
                    } else {
                        System.out.println(result);
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

    public void addRow(Object[] row) {
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.addRow(row);
    }
}
