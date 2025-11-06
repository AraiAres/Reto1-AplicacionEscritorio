package com.gymsync.app.view.panels;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import com.gymsync.app.config.UIConfig;
import com.gymsync.app.connectivity.connection.ConnectionManager;
import com.gymsync.app.controllers.ControllerFactory;
import com.gymsync.app.controllers.LoginPanelController;
import com.gymsync.app.model.entities.User;
import com.gymsync.app.services.exceptions.LoginException;
import com.gymsync.app.view.PanelFactory;
import com.gymsync.app.view.customComponents.GradientButton;
import com.gymsync.app.view.frame.MainFrame;
import com.gymsync.app.view.utils.SessionManager;
import com.gymsync.app.view.utils.UIUtils;

public class LoginPanel extends AbstractPanel {

	private static final long serialVersionUID = 4835782793720711682L;
	private MainFrame mainFrame = null;
	private ConnectionManager connectionManager = null;

	private UIUtils utils = null;
	private JLabel title = null;
	private GradientButton signupButton = null;
	private GradientButton loginButton = null;
	private JTextField userInput = null;
	private JPasswordField passInput = null;

	public LoginPanel(MainFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;
		connectionManager = new ConnectionManager();
		utils = new UIUtils();
		initialize();
	}

	@Override
	protected void initialize() {

		title = new JLabel("LOGIN");
		title.setBounds(450, 150, 150, 70);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setVerticalAlignment(SwingConstants.CENTER);
		title.setBorder(BorderFactory.createLineBorder(UIConfig.EMIRALD_DARK, 2));
		title.setForeground(UIConfig.EMIRALD);
		title.setFont(UIConfig.TITLE_FONT);

		signupButton = new GradientButton("SIGNUP", UIConfig.PRIMARY_GRADIENT);
		signupButton.setBounds(600, 150, 150, 70);
		signupButton.setHorizontalAlignment(SwingConstants.CENTER);
		signupButton.setVerticalAlignment(SwingConstants.CENTER);
		signupButton.setForeground(Color.WHITE);
		signupButton.setFont(UIConfig.TITLE_FONT);
		signupButton.addActionListener(e -> {
			if (connectionManager.isOnline()) {
				mainFrame.showPanel(PanelFactory.getInstance().getPanel("signupPanel", mainFrame));
				clear();
			} else {
				utils.popError(this, "No puedes seguir sin conexión.");
			}
		});

		userInput = new JTextField();
		userInput.setBounds(475, 270, 250, 40);
		userInput.setFont(UIConfig.INPUT_FONT);
		userInput.setBackground(UIConfig.PRIMARY_DARK);
		utils.inputPlaceholder(userInput, "Usuario");
		userInput.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 15));
		userInput.addActionListener(e -> loginButton.doClick());

		passInput = new JPasswordField();
		passInput.setBounds(475, 320, 250, 40);
		passInput.setFont(UIConfig.INPUT_FONT);
		passInput.setBackground(UIConfig.PRIMARY_DARK);
		utils.passPlaceholder(passInput, "Contraseña");
		passInput.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 15));
		passInput.addActionListener(e -> loginButton.doClick());

		loginButton = new GradientButton("Login", UIConfig.PRIMARY_GRADIENT);
		loginButton.setBounds(525, 420, 150, 50);
		loginButton.addActionListener(e -> {
			try {
				login();
			} catch (LoginException le) {
				utils.popError(this, le.getMessage());
			}
		});

		add(title);
		add(signupButton);
		add(userInput);
		add(passInput);
		add(loginButton);

		setFocusable(true);
		requestFocusInWindow();

	}

	private void login() throws LoginException {
		LoginPanelController controller = (LoginPanelController) ControllerFactory.getInstance()
				.getController("loginPanelController");

		User user = controller.validateCredentials(userInput.getText(), passInput.getPassword());
		if (null != user) {
			SessionManager.getInstance().setCurrentUser(user);
			mainFrame.showPanel(PanelFactory.getInstance().getPanel("mainPanel", mainFrame));
			clear();
		} else {
			throw new LoginException();
		}
	}

	private void clear() {
		userInput.setText("");
		passInput.setText("");
	}

}
