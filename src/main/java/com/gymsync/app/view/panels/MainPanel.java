package com.gymsync.app.view.panels;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.gymsync.app.config.UIConfig;
import com.gymsync.app.controllers.ControllerFactory;
import com.gymsync.app.controllers.MainPanelController;
import com.gymsync.app.model.entities.User;
import com.gymsync.app.view.PanelFactory;
import com.gymsync.app.view.customComponents.GradientButton;
import com.gymsync.app.view.customComponents.ProfileButton;
import com.gymsync.app.view.customComponents.SignoutButton;
import com.gymsync.app.view.customComponents.WorkoutListPanel;
import com.gymsync.app.view.frame.MainFrame;
import com.gymsync.app.view.utils.SessionManager;

public class MainPanel extends AbstractPanel {

	private static final long serialVersionUID = -7479444517144905320L;

	private MainFrame mainFrame = null;
	private JLabel title = null;
	private ProfileButton profileButton = null;
	private SignoutButton signoutButton = null;
	private JLabel profileLabel = null;
	private WorkoutListPanel workoutListPanel = null;
	private GradientButton userHistoryButton = null;

	private User user = null;

	public MainPanel(User user, MainFrame mainFrame) {
		this.user = user;
		this.mainFrame = mainFrame;
		initialize();
	}

	@Override
	protected void initialize() {

		title = new JLabel(user.getName() + " " + user.getLastname());
		title.setBounds(350, 30, 500, 70);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setVerticalAlignment(SwingConstants.CENTER);
		title.setForeground(UIConfig.EMIRALD);
		title.setFont(UIConfig.TITLE_FONT);

		profileButton = new ProfileButton(50);
		profileButton.setBounds(1100, 30, 50, 50);
		profileButton.setAlignmentX(CENTER_ALIGNMENT);
		profileButton.addActionListener(e -> {
			mainFrame.showPanel(PanelFactory.getInstance().getPanel("profilePanel", mainFrame));
		});

		profileLabel = new JLabel("Mi Perfil", SwingConstants.CENTER);
		profileLabel.setBounds(1100, 70, 50, 50);
		profileLabel.setFont(UIConfig.LABEL_FONT);
		profileLabel.setAlignmentX(CENTER_ALIGNMENT);

		signoutButton = new SignoutButton(UIConfig.TERMINATE_GRADIENT);
		signoutButton.setBounds(1105, 630, 45, 45);
		signoutButton.setAlignmentX(CENTER_ALIGNMENT);
		signoutButton.addActionListener(e -> {
			mainFrame.showPanel(PanelFactory.getInstance().getPanel("loginPanel", mainFrame));
			SessionManager.getInstance().logout();
		});

		MainPanelController controller = (MainPanelController) ControllerFactory.getInstance()
				.getController("mainPanelController");
		workoutListPanel = new WorkoutListPanel(
				controller.userCompatibleWorkouts(SessionManager.getInstance().getCurrentUser().getLevel()), mainFrame);
		workoutListPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		workoutListPanel.setBounds(0, 130, 1205, 470);

		userHistoryButton = new GradientButton("Historial de Workouts", UIConfig.PRIMARY_GRADIENT);
		userHistoryButton.setBounds(30, 630, 200, 45);
		userHistoryButton.setAlignmentX(CENTER_ALIGNMENT);
		userHistoryButton.addActionListener(e -> {
			mainFrame.showPanel(PanelFactory.getInstance().getPanel("userHistoryPanel", mainFrame));
		});

		add(title);
		add(profileButton);
		add(profileLabel);
		add(signoutButton);
		add(workoutListPanel);
		add(userHistoryButton);
	}

}
