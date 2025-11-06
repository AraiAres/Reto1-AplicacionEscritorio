package com.gymsync.app.view.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXDatePicker;

public class UIUtils {

	public void inputPlaceholder(JTextField field, String placeholder) {
		field.setText(placeholder);
		field.setForeground(Color.GRAY);

		field.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (field.getForeground() == Color.GRAY) {
					field.setText("");
					field.setForeground(Color.WHITE);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (field.getText().isEmpty()) {
					field.setText(placeholder);
					field.setForeground(Color.GRAY);
				}
			}
		});
	}

	public void passPlaceholder(JPasswordField field, String placeholder) {
		field.setEchoChar((char) 0);
		field.setText(placeholder);
		field.setForeground(Color.GRAY);

		field.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (field.getForeground() == Color.GRAY) {
					field.setText("");
					field.setForeground(Color.WHITE);
					field.setEchoChar('*');
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (field.getPassword().length == 0) {
					field.setText(placeholder);
					field.setForeground(Color.GRAY);
					field.setEchoChar((char) 0);
				}
			}
		});
	}

	public void datePickerPlaceholder(JXDatePicker picker, String placeholder) {
		picker.setDate(null);
		picker.getEditor().setText(placeholder);
		picker.getEditor().setForeground(Color.GRAY);

		picker.getEditor().addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (picker.getEditor().getText().equals(placeholder))
					picker.getEditor().setText("");
				picker.getEditor().setForeground(Color.WHITE);
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (picker.getEditor().getText().isEmpty()) {
					picker.getEditor().setText(placeholder);
					picker.getEditor().setForeground(Color.GRAY);
				}
			}
		});

		picker.addPropertyChangeListener("date", evt -> {
			if (picker.getDate() != null)
				picker.getEditor().setForeground(Color.WHITE);
		});
	}

	public void popError(Component parent, String message) {
		JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public void popAviso(Component parent, String message) {
		JOptionPane.showMessageDialog(parent, message, "Aviso", JOptionPane.INFORMATION_MESSAGE);
	}

}
