package com.gymsync.app.view.customComponents;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.gymsync.app.config.UIConfig;

public class UserHistoryTable extends JTable {

	private static final long serialVersionUID = 8539292682505263218L;
	private DefaultTableModel model;

	public UserHistoryTable(List<Object[]> rows) {
		model = new DefaultTableModel(UIConfig.USER_HISTORY_COLS, 0);
		setModel(model);

		if (rows != null) {
			for (Object[] row : rows) {
				model.addRow(row);
			}
		}
		designTabel();
	}

	private void designTabel() {
		setFont(new Font("Segoe UI", Font.PLAIN, 14));
		setRowHeight(28);

		setSelectionBackground(UIConfig.EMIRALD);
		setSelectionForeground(Color.WHITE);

		setShowGrid(false);
		setIntercellSpacing(new java.awt.Dimension(0, 0));

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = -9051234718935528175L;

			@Override
			public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				setHorizontalAlignment(CENTER);
				if (!isSelected) {
					c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
				}
				return c;
			}
		};
		setDefaultRenderer(Object.class, centerRenderer);

		JTableHeader header = getTableHeader();
		header.setBackground(UIConfig.EMIRALD_DARK);
		header.setForeground(Color.WHITE);
		header.setFont(new Font("Segoe UI", Font.BOLD, 16));
		header.setReorderingAllowed(false);

		if (getColumnModel().getColumnCount() > 0) {
			getColumnModel().getColumn(0).setPreferredWidth(150);
			getColumnModel().getColumn(1).setPreferredWidth(50);
			getColumnModel().getColumn(2).setPreferredWidth(80);
			getColumnModel().getColumn(3).setPreferredWidth(120);
			getColumnModel().getColumn(4).setPreferredWidth(200);
		}
	}
}
