package com.gymsync.app.view.panels;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import com.gymsync.app.config.UIConfig;
import com.gymsync.app.controllers.ControllerFactory;
import com.gymsync.app.controllers.UserHistoryPanelController;
import com.gymsync.app.model.entities.User;
import com.gymsync.app.view.PanelFactory;
import com.gymsync.app.view.customComponents.BackArrowButton;
import com.gymsync.app.view.customComponents.UserHistoryTable;
import com.gymsync.app.view.frame.MainFrame;

public class UserHistoryPanel extends AbstractPanel {

	private static final long serialVersionUID = 7906337963360230248L;

	private MainFrame mainFrame = null;

	private JLabel title = null;
	private BackArrowButton backButton = null;
	private List<Object[]> workoutData = null;
	private UserHistoryTable historyTable = null;
	private JScrollPane scrollPane = null;

	private User user = null;

	public UserHistoryPanel(User user, MainFrame mainFrame) {
		this.user = user;
		this.mainFrame = mainFrame;
		initialize();
	}

	@Override
	protected void initialize() {

		title = new JLabel("Historial");
		title.setBounds(475, 50, 250, 70);
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

		UserHistoryPanelController controller = (UserHistoryPanelController) ControllerFactory.getInstance()
				.getController("userHistoryPanelController");

		workoutData = controller.convertWorkoutsToTableRowType(user.getWorkouts());
		historyTable = new UserHistoryTable(workoutData);
		scrollPane = new JScrollPane(historyTable);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBounds(50, 200, 1100, 400);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		add(scrollPane);
		add(title);
		add(backButton);
	}
}
