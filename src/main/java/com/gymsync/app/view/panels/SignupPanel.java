package com.gymsync.app.view.panels;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import org.jdesktop.swingx.JXDatePicker;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.gymsync.app.config.UIConfig;
import com.gymsync.app.controllers.ControllerFactory;
import com.gymsync.app.controllers.SignupPanelController;
import com.gymsync.app.services.ServiceFactory;
import com.gymsync.app.services.DataValidationService;
import com.gymsync.app.view.PanelFactory;
import com.gymsync.app.view.customComponents.GradientButton;
import com.gymsync.app.view.frame.MainFrame;
import com.gymsync.app.view.utils.UIUtils;

public class SignupPanel extends AbstractPanel {

	private static final long serialVersionUID = -4268768590358914572L;

	private UIUtils utils = null;

	private MainFrame mainFrame = null;
	private JLabel title = null;
	private GradientButton loginButton = null;
	private GradientButton signupButton = null;
	private JTextField nameInput = null;
	private JTextField lastnameInput = null;
	private JTextField emailInput = null;
	private JPasswordField passInput = null;
	private JPasswordField confirmPassInput = null;
	private JXDatePicker birthInput = null;

	public SignupPanel(MainFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;
		utils = new UIUtils();
		initialize();
	}

	@Override
	protected void initialize() {

		title = new JLabel("SIGNUP");
		title.setBounds(600, 150, 150, 70);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setVerticalAlignment(SwingConstants.CENTER);
		title.setBorder(BorderFactory.createLineBorder(UIConfig.EMIRALD_DARK, 2));
		title.setForeground(UIConfig.EMIRALD);
		title.setFont(UIConfig.TITLE_FONT);

		loginButton = new GradientButton("LOGIN", UIConfig.PRIMARY_GRADIENT);
		loginButton.setBounds(450, 150, 150, 70);
		loginButton.setHorizontalAlignment(SwingConstants.CENTER);
		loginButton.setVerticalAlignment(SwingConstants.CENTER);
		loginButton.setForeground(Color.WHITE);
		loginButton.setFont(UIConfig.TITLE_FONT);
		loginButton.addActionListener(e -> {
			mainFrame.showPanel(PanelFactory.getInstance().getPanel("loginPanel", mainFrame));
		});

		nameInput = new JTextField();
		nameInput.setBounds(395, 250, 200, 40);
		nameInput.setFont(UIConfig.INPUT_FONT);
		nameInput.setBackground(UIConfig.PRIMARY_DARK);
		utils.inputPlaceholder(nameInput, "Nombre");
		nameInput.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 15));

		lastnameInput = new JTextField();
		lastnameInput.setBounds(605, 250, 200, 40);
		lastnameInput.setFont(UIConfig.INPUT_FONT);
		lastnameInput.setBackground(UIConfig.PRIMARY_DARK);
		utils.inputPlaceholder(lastnameInput, "Apellidos");
		lastnameInput.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 15));

		emailInput = new JTextField();
		emailInput.setBounds(395, 300, 410, 40);
		emailInput.setFont(UIConfig.INPUT_FONT);
		emailInput.setBackground(UIConfig.PRIMARY_DARK);
		utils.inputPlaceholder(emailInput, "Email");
		emailInput.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 15));

		passInput = new JPasswordField();
		passInput.setBounds(395, 350, 200, 40);
		passInput.setFont(UIConfig.INPUT_FONT);
		passInput.setBackground(UIConfig.PRIMARY_DARK);
		utils.passPlaceholder(passInput, "Contraseña");
		passInput.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 15));

		confirmPassInput = new JPasswordField();
		confirmPassInput.setBounds(605, 350, 200, 40);
		confirmPassInput.setFont(UIConfig.INPUT_FONT);
		confirmPassInput.setBackground(UIConfig.PRIMARY_DARK);
		utils.passPlaceholder(confirmPassInput, "Confirmar Contraseña");
		confirmPassInput.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 15));

		birthInput = new JXDatePicker();
		birthInput.setBounds(395, 400, 410, 40);
		birthInput.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
		birthInput.getEditor().setBackground(UIConfig.PRIMARY_DARK);
		birthInput.getEditor().setForeground(Color.WHITE);
		birthInput.getEditor().setFont(UIConfig.INPUT_FONT);
		utils.datePickerPlaceholder(birthInput, "Fecha de nacimiento");
		birthInput.getEditor().setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 15));

		signupButton = new GradientButton("Signup", UIConfig.PRIMARY_GRADIENT);
		signupButton.setBounds(525, 470, 150, 50);
		signupButton.addActionListener(e -> {
			signup();
		});

		add(title);
		add(loginButton);
		add(nameInput);
		add(emailInput);
		add(passInput);
		add(confirmPassInput);
		add(lastnameInput);
		add(birthInput);
		add(signupButton);

		setFocusable(true);
		requestFocusInWindow();

	}

	private void signup() {
		DataValidationService service = (DataValidationService) ServiceFactory.getInstance()
				.getService("dataValidationService");
		SignupPanelController controller = (SignupPanelController) ControllerFactory.getInstance()
				.getController("signupPanelController");

		if (nameInput.getText().isEmpty() || lastnameInput.getText().isEmpty()) {
			utils.popAviso(this, "Nombre y Apellidos son obligatorios");
		} else if (!service.isEmailValid(emailInput.getText())) {
			utils.popAviso(this, "El formato de Email no es valido.");
		} else if (!service.isPasswordValid(new String(passInput.getPassword()))) {
			utils.popAviso(this, "La contraseña debe tener al menos 8 caracteres, una mayúscula y un número.");
		} else if (!String.valueOf(passInput.getPassword()).equals(String.valueOf(confirmPassInput.getPassword()))) {
			utils.popAviso(this, "Las contraseñas no coinciden.");
		} else {

			Date selectedDate = birthInput.getDate();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String formattedDate = sdf.format(selectedDate);
			if (!service.isDateValid(formattedDate)) {
				utils.popAviso(this, "La fecha de nacimiento no es válida.");
				return;
			} else if (controller.isUniqueUser(nameInput.getText(), lastnameInput.getText(), birthInput.getDate())) {

				controller.addNewUserToDB(nameInput.getText(), lastnameInput.getText(), emailInput.getText(),
						new String(passInput.getPassword()), birthInput.getDate(), 0);
				utils.popAviso(this, "Registro exitoso!");
				mainFrame.showPanel(PanelFactory.getInstance().getPanel("loginPanel", mainFrame));
				// clear();
			} else {
				utils.popAviso(this, "Ya existe este usuario!");
			}

		}

	}

}