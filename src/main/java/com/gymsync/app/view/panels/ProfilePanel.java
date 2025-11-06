package com.gymsync.app.view.panels;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jdesktop.swingx.JXDatePicker;

import com.gymsync.app.config.UIConfig;
import com.gymsync.app.connectivity.connection.ConnectionManager;
import com.gymsync.app.controllers.ControllerFactory;
import com.gymsync.app.controllers.ProfilePanelController;
import com.gymsync.app.model.entities.User;
import com.gymsync.app.services.ServiceFactory;
import com.gymsync.app.services.DataValidationService;
import com.gymsync.app.view.PanelFactory;
import com.gymsync.app.view.customComponents.BackArrowButton;
import com.gymsync.app.view.customComponents.GradientButton;
import com.gymsync.app.view.frame.MainFrame;
import com.gymsync.app.view.utils.SessionManager;
import com.gymsync.app.view.utils.UIUtils;

public class ProfilePanel extends AbstractPanel {

	private static final long serialVersionUID = 3685658604197819107L;

	private MainFrame mainFrame = null;
	private ConnectionManager connectionManager = null;
	private UIUtils utils = null;

	private JLabel title = null;
	private BackArrowButton backButton = null;
	private User user = null;

	private JTextField nameInput = null;
	private JTextField lastnameInput = null;
	private JTextField emailInput = null;
	private JPasswordField passInput = null;
	private JXDatePicker birthInput = null;
	private GradientButton updateInfoButton = null;

	private boolean changeInfoMode = false;

	public ProfilePanel(User user, MainFrame mainFrame) {
		this.user = user;
		this.mainFrame = mainFrame;
		connectionManager = new ConnectionManager();
		utils = new UIUtils();
		initialize();
	}

	@Override
	protected void initialize() {

		title = new JLabel("Datos de Perfil");
		title.setBounds(475, 150, 250, 70);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setVerticalAlignment(SwingConstants.CENTER);
		title.setBorder(BorderFactory.createLineBorder(UIConfig.EMIRALD_DARK, 2));
		title.setForeground(UIConfig.EMIRALD);
		title.setFont(UIConfig.TITLE_FONT);

		backButton = new BackArrowButton(UIConfig.PRIMARY_GRADIENT);
		backButton.setBounds(30, 30, 40, 40);
		backButton.setAlignmentX(CENTER_ALIGNMENT);
		backButton.addActionListener(e -> {
			mainFrame.showPanel(PanelFactory.getInstance().getPanel("mainPanel", mainFrame));
		});

		nameInput = new JTextField(user.getName());
		nameInput.setBounds(395, 250, 200, 40);
		nameInput.setFont(UIConfig.INPUT_FONT);
		nameInput.setBackground(UIConfig.PRIMARY_DARK);
		nameInput.setForeground(Color.WHITE);
		nameInput.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 15));
		nameInput.setEditable(false);

		lastnameInput = new JTextField(user.getLastname());
		lastnameInput.setBounds(605, 250, 200, 40);
		lastnameInput.setFont(UIConfig.INPUT_FONT);
		lastnameInput.setBackground(UIConfig.PRIMARY_DARK);
		lastnameInput.setForeground(Color.WHITE);
		lastnameInput.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 15));
		lastnameInput.setEditable(false);

		emailInput = new JTextField(user.getEmail());
		emailInput.setBounds(395, 300, 410, 40);
		emailInput.setFont(UIConfig.INPUT_FONT);
		emailInput.setBackground(UIConfig.PRIMARY_DARK);
		emailInput.setForeground(Color.WHITE);
		emailInput.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 15));
		emailInput.setEditable(false);

		passInput = new JPasswordField(user.getPassword());
		passInput.setBounds(395, 350, 200, 40);
		passInput.setFont(UIConfig.INPUT_FONT);
		passInput.setBackground(UIConfig.PRIMARY_DARK);
		passInput.setForeground(Color.WHITE);
		passInput.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 15));
		passInput.setEditable(false);

		birthInput = new JXDatePicker(user.getBirthDate());
		birthInput.setBounds(605, 350, 200, 40);
		birthInput.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
		birthInput.getEditor().setBackground(UIConfig.PRIMARY_DARK);
		birthInput.getEditor().setForeground(Color.WHITE);
		birthInput.getEditor().setFont(UIConfig.INPUT_FONT);
		birthInput.getEditor().setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 15));
		birthInput.getEditor().setEditable(false);
		birthInput.setEnabled(false);

		updateInfoButton = new GradientButton("Cambiar Datos", UIConfig.TERMINATE_GRADIENT);
		updateInfoButton.setBounds(525, 420, 150, 50);
		updateInfoButton.addActionListener(e -> {
			if (connectionManager.isOnline()) {
				if (!changeInfoMode) {
					setEditableAll(true);
				} else {
					updateInfo();
				}
			} else {
				utils.popError(this, "No puedes seguir sin conexión.");
			}

		});

		add(nameInput);
		add(emailInput);
		add(passInput);
		add(lastnameInput);
		add(birthInput);
		add(title);
		add(backButton);
		add(updateInfoButton);
	}

	private void setEditableAll(boolean editable) {
		changeInfoMode = editable;

		nameInput.setEditable(editable);
		lastnameInput.setEditable(editable);
		emailInput.setEditable(editable);
		passInput.setEditable(editable);
		birthInput.getEditor().setEditable(editable);
		birthInput.setEnabled(editable);
		if (editable) {
			updateInfoButton.setText("Confirmar");
			updateInfoButton.setGradientColors(UIConfig.PRIMARY_GRADIENT);
			passInput.setEchoChar((char) 0);
		} else {
			updateInfoButton.setText("Cambiar Datos");
			updateInfoButton.setGradientColors(UIConfig.TERMINATE_GRADIENT);
			passInput.setEchoChar('*');
		}
		repaint();
		revalidate();
	}

	private void updateInfo() {
		ProfilePanelController controller = (ProfilePanelController) ControllerFactory.getInstance()
				.getController("profilePanelController");

		if (isValidData()) {
			User updatedUser = controller.updateUserData(user, nameInput.getText(), lastnameInput.getText(),
					emailInput.getText(), passInput.getPassword(), birthInput.getDate());
			SessionManager.getInstance().setCurrentUser(updatedUser);
			setEditableAll(false);
		}

	}

	private boolean isValidData() {
		DataValidationService service = (DataValidationService) ServiceFactory.getInstance()
				.getService("dataValidationService");
		if (nameInput.getText().isEmpty() || lastnameInput.getText().isEmpty()) {
			utils.popAviso(this, "Nombre y Apellidos son obligatorios");
			return false;
		} else if (!service.isEmailValid(emailInput.getText())) {
			utils.popAviso(this, "El formato de Email no es valido.");
			return false;
		} else if (!service.isPasswordValid(new String(passInput.getPassword()))) {
			utils.popAviso(this, "La contraseña debe tener al menos 8 caracteres, una mayúscula y un número.");
			return false;
		} else {
			Date selectedDate = birthInput.getDate();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String formattedDate = sdf.format(selectedDate);
			if (!service.isDateValid(formattedDate)) {
				utils.popAviso(this, "La fecha de nacimiento no es válida.");
				return false;
			}
			return true;
		}
	}

	public void resetDataToCurrentUser() {
		this.user = SessionManager.getInstance().getCurrentUser();

		nameInput.setText(user.getName());
		lastnameInput.setText(user.getLastname());
		emailInput.setText(user.getEmail());
		passInput.setText(user.getPassword());
		birthInput.setDate(user.getBirthDate());
	}
}
