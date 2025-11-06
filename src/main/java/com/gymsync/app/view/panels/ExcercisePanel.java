package com.gymsync.app.view.panels;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

import com.gymsync.app.concurrency.threads.ProgressiveCronometer;
import com.gymsync.app.concurrency.threads.RegressiveCronometer;
import com.gymsync.app.config.UIConfig;
import com.gymsync.app.connectivity.connection.ConnectionManager;
import com.gymsync.app.controllers.ControllerFactory;
import com.gymsync.app.controllers.ExcercisePanelController;
import com.gymsync.app.model.entities.Excercise;
import com.gymsync.app.model.entities.Series;
import com.gymsync.app.services.DataConversionService;
import com.gymsync.app.services.ServiceFactory;
import com.gymsync.app.view.PanelFactory;
import com.gymsync.app.view.customComponents.*;
import com.gymsync.app.view.frame.MainFrame;
import com.gymsync.app.view.utils.SessionManager;
import com.gymsync.app.view.utils.UIUtils;

public class ExcercisePanel extends AbstractPanel {

	private static final long serialVersionUID = -6496098303287369644L;

	private ExcercisePanelController controller = null;
	private DataConversionService dataConversionService = null;
	private ConnectionManager connectionManager = null;

	private UIUtils uiUtils = null;

	private MainFrame mainFrame = null;
	private Excercise excercise = null;
	private BackArrowButton backButton = null;
	private DoubleArrowButton backToMain = null;
	private JLabel workoutName = null;
	private JLabel exName = null;
	private JLabel descriptionLabel = null;
	private JTextArea exDescription = null;
	private JScrollPane seriesList = null;
	private JPanel seriesContainer = null;
	private JLabel exTimerName = null;
	private CronometerLabel exTimerLabel = null;
	private ProgressiveCronometer exTimer = null;
	private JLabel breakTimerName = null;
	private CronometerLabel breakTimerLabel = null;
	private RegressiveCronometer breakTimer = null;
	private GradientButton pauseButton = null;
	private SignoutButton signoutButton = null;

	private boolean isRunning = false;
	private boolean isFinished = false;

	private RegressiveCronometer currentSeriesTimer = null;
	private int currentSeriesTimerIndex = 0;
	private ArrayList<RegressiveCronometer> seriesCronometersList = new ArrayList<>();

	public ExcercisePanel(Excercise excercise, MainFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;
		this.excercise = excercise;
		controller = (ExcercisePanelController) ControllerFactory.getInstance()
				.getController("excercisePanelController");
		dataConversionService = (DataConversionService) ServiceFactory.getInstance()
				.getService("dataConversionService");
		connectionManager = new ConnectionManager();
		uiUtils = new UIUtils();
		initialize();
	}

	@Override
	protected void initialize() {
		backButton = new BackArrowButton(UIConfig.PRIMARY_GRADIENT);
		backButton.setBounds(30, 30, 40, 40);
		backButton.addActionListener(e -> {
			controller.refreshUserHistory(SessionManager.getInstance().getCurrentUser());
			mainFrame.showPanel(PanelFactory.getInstance().getPanel("workoutPanel", mainFrame));
		});

		backToMain = new DoubleArrowButton(UIConfig.PRIMARY_GRADIENT);
		backToMain.setBounds(80, 30, 40, 40);
		backToMain.addActionListener(e -> {
			controller.refreshUserHistory(SessionManager.getInstance().getCurrentUser());
			mainFrame.showPanel(PanelFactory.getInstance().getPanel("mainPanel", mainFrame));
		});

		Border line = BorderFactory.createLineBorder(UIConfig.EMIRALD_DARK, 2);
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);

		workoutName = new JLabel(SessionManager.getInstance().getCurrentWorkout().getName(), SwingConstants.CENTER);
		workoutName.setBounds(350, 25, 350, 50);
		workoutName.setBorder(BorderFactory.createCompoundBorder(line, padding));
		workoutName.setForeground(UIConfig.EMIRALD);
		workoutName.setFont(UIConfig.TITLE_FONT);

		exName = new JLabel("Ejercicio: " + excercise.getName(), SwingConstants.CENTER);
		exName.setBounds(750, 25, 350, 50);
		exName.setBorder(BorderFactory.createCompoundBorder(line, padding));
		exName.setForeground(UIConfig.EMIRALD);
		exName.setFont(UIConfig.TITLE_FONT);

		descriptionLabel = new JLabel("Descripción", SwingConstants.CENTER);
		descriptionLabel.setBounds(30, 150, 300, 50);
		descriptionLabel.setForeground(UIConfig.EMIRALD);
		descriptionLabel.setFont(UIConfig.TITLE_FONT);

		exDescription = new JTextArea(excercise.getDescription());
		exDescription.setBounds(30, 200, 300, 100);
		exDescription.setLineWrap(true);
		exDescription.setWrapStyleWord(true);
		exDescription.setForeground(UIConfig.EMIRALD);
		exDescription.setFont(UIConfig.INPUT_FONT);
		exDescription.setOpaque(false);

		exTimerName = new JLabel("Cronómetro de ejercicio", SwingConstants.CENTER);
		exTimerName.setBounds(30, 300, 300, 50);
		exTimerName.setForeground(UIConfig.EMIRALD);
		exTimerName.setFont(UIConfig.SUBTITLE_FONT);

		int[] initialExTime = dataConversionService.secondsToHMS(getInitialSecondsForExercise());
		exTimerLabel = new CronometerLabel(initialExTime[0], initialExTime[1], initialExTime[2]);
		exTimerLabel.setBounds(55, 350, 250, 50);
		exTimer = new ProgressiveCronometer(exTimerLabel, getInitialSecondsForExercise());

		breakTimerName = new JLabel("Cronómetro de descanso", SwingConstants.CENTER);
		breakTimerName.setBounds(30, 430, 300, 50);
		breakTimerName.setForeground(UIConfig.EMIRALD);
		breakTimerName.setFont(UIConfig.SUBTITLE_FONT);

		int[] breakTimeArr = dataConversionService
				.secondsToHMS(SessionManager.getInstance().getCurrentExcercise().getBreakTime());
		breakTimerLabel = new CronometerLabel(breakTimeArr[0], breakTimeArr[1], breakTimeArr[2]);
		breakTimerLabel.setBounds(55, 480, 250, 50);
		breakTimer = new RegressiveCronometer(breakTimerLabel,
				SessionManager.getInstance().getCurrentExcercise().getBreakTime());

		seriesList = new JScrollPane();
		seriesList.setBounds(350, 150, 800, 450);
		seriesList.setOpaque(false);
		seriesList.setBorder(null);
		seriesList.getViewport().setOpaque(false);
		seriesList.getViewport().setBorder(null);
		seriesList.getVerticalScrollBar().setUnitIncrement(16);

		seriesContainer = new JPanel();
		seriesContainer.setLayout(new BoxLayout(seriesContainer, BoxLayout.Y_AXIS));
		seriesContainer.setOpaque(false);

		seriesCronometersList.clear();
		for (int i = 0; i < excercise.getSeries().size(); i++) {
			Series series = excercise.getSeries().get(i);
			SeriesItemPanel seriesPanel = new SeriesItemPanel(series, null);
			CronometerLabel label = seriesPanel.getCronometerLabel();
			RegressiveCronometer crono = new RegressiveCronometer(label, series.getEstimatedDuration());

			final int index = i;
			crono.setOnFinish(() -> handleSeriesCompletion(index, seriesPanel));

			int[] hms = dataConversionService.secondsToHMS(series.getEstimatedDuration());
			SwingUtilities.invokeLater(() -> label.setTime(hms[0], hms[1], hms[2]));

			seriesCronometersList.add(crono);

			if (controller.isCompleted(series, SessionManager.getInstance().getCurrentUser())) {
				seriesPanel.completed();
			}

			seriesPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
			seriesContainer.add(seriesPanel);
			seriesContainer.add(Box.createRigidArea(new Dimension(0, 10)));
		}

		seriesList.setViewportView(seriesContainer);

		pauseButton = new GradientButton("Iniciar", UIConfig.PRIMARY_GRADIENT);
		pauseButton.setBounds(525, 600, 200, 50);
		pauseButton.addActionListener(e -> pauseButtonFunction(exTimer));

		signoutButton = new SignoutButton(UIConfig.TERMINATE_GRADIENT);
		signoutButton.setBounds(1105, 630, 45, 45);
		signoutButton.addActionListener(e -> {
			mainFrame.showPanel(PanelFactory.getInstance().getPanel("loginPanel", mainFrame));
			SessionManager.getInstance().logout();
		});

		add(backButton);
		add(backToMain);
		add(workoutName);
		add(exName);
		add(descriptionLabel);
		add(exDescription);
		add(exTimerName);
		add(exTimerLabel);
		add(breakTimerName);
		add(breakTimerLabel);
		add(seriesList);
		add(pauseButton);
		add(signoutButton);

		for (int i = 0; i < seriesCronometersList.size(); i++) {
			Series series = excercise.getSeries().get(i);
			if (!controller.isCompleted(series, SessionManager.getInstance().getCurrentUser())) {
				currentSeriesTimerIndex = i;
				break;
			}
		}

		if (currentSeriesTimerIndex < seriesCronometersList.size()) {
			currentSeriesTimer = seriesCronometersList.get(currentSeriesTimerIndex);
		}
	}

	private void pauseButtonFunction(ProgressiveCronometer excerciseTimer) {
		if (connectionManager.isOnline()) {
			if (isFinished) {
				controller.refreshUserHistory(SessionManager.getInstance().getCurrentUser());
				SessionManager.getInstance().setCurrentWorkout(
						controller.getUpdatedWorkout(SessionManager.getInstance().getCurrentWorkout()));
				if (controller.isCompletedWorkout(SessionManager.getInstance().getCurrentWorkout())) {
					uiUtils.popAviso(this, "Has completado exitosamente el workout"
							+ SessionManager.getInstance().getCurrentWorkout().getName());
					controller.updateUserLevel(SessionManager.getInstance().getCurrentWorkout(),
							SessionManager.getInstance().getCurrentUser());
					mainFrame.showPanel(PanelFactory.getInstance().getPanel("mainPanel", mainFrame));
				} else {
					SessionManager.getInstance().setCurrentExcercise(controller.getNextExcercise(excercise));
					mainFrame.showPanel(PanelFactory.getInstance().getPanel("excercisePanel", mainFrame));
				}
			} else if (isRunning) {
				excerciseTimer.pause();
				if (currentSeriesTimer != null)
					currentSeriesTimer.pause();
				if (breakTimer != null && breakTimer.isRunning())
					breakTimer.pause();
				isRunning = false;
				pauseButton.setText("Reanudar");
			} else {
				if (excerciseTimer.isPaused())
					excerciseTimer.resume();
				else
					excerciseTimer.start();

				if (currentSeriesTimer != null) {
					if (currentSeriesTimer.isPaused())
						currentSeriesTimer.resume();
					else
						currentSeriesTimer.start();
				}
				if (breakTimer != null && breakTimer.isPaused())
					breakTimer.resume();

				isRunning = true;
				pauseButton.setText("Pausar");
			}
		} else {
			uiUtils.popError(this, "No puedes seguir sin conexión.");
		}
	}

	private int getInitialSecondsForExercise() {
		int totalSeconds = 0;
		for (Series series : excercise.getSeries()) {
			if (controller.isCompleted(series, SessionManager.getInstance().getCurrentUser())) {
				totalSeconds += series.getEstimatedDuration();
				totalSeconds += SessionManager.getInstance().getCurrentExcercise().getBreakTime();
			} else
				break;
		}
		return totalSeconds;
	}

	private void handleSeriesCompletion(int seriesIndex, SeriesItemPanel seriesPanel) {
		Series completedSeries = excercise.getSeries().get(seriesIndex);

		if (!controller.isCompleted(completedSeries, SessionManager.getInstance().getCurrentUser())) {
			try {
				controller.saveCompletedSeries(completedSeries, SessionManager.getInstance().getCurrentUser());

			} catch (Exception e) {
				e.printStackTrace();
				uiUtils.popError(this, "Error al guardar la serie completada.");
			}
		}
		seriesPanel.completed();

		if (seriesIndex + 1 < excercise.getSeries().size()) {
			breakTimer = new RegressiveCronometer(breakTimerLabel,
					SessionManager.getInstance().getCurrentExcercise().getBreakTime());
			breakTimer.setOnFinish(() -> {
				int[] breakTimeArr = dataConversionService
						.secondsToHMS(SessionManager.getInstance().getCurrentExcercise().getBreakTime());
				breakTimerLabel = new CronometerLabel(breakTimeArr[0], breakTimeArr[1], breakTimeArr[2]);
				currentSeriesTimer = null;

				for (int i = seriesIndex + 1; i < seriesCronometersList.size(); i++) {
					Series series = excercise.getSeries().get(i);
					if (!controller.isCompleted(series, SessionManager.getInstance().getCurrentUser())) {
						currentSeriesTimerIndex = i;
						currentSeriesTimer = seriesCronometersList.get(i);
						break;
					}
				}

				if (currentSeriesTimer != null) {
					currentSeriesTimer.start();
				} else {
					exTimer.stop();
					isFinished = true;
					SwingUtilities.invokeLater(() -> pauseButton.setText("Siguiente ejercicio"));
				}
			});

			breakTimer.start();
		} else {
			exTimer.stop();
			for (RegressiveCronometer regressiveCronos : seriesCronometersList)
				regressiveCronos.stop();
			isFinished = true;
			SwingUtilities.invokeLater(() -> pauseButton.setText("Siguiente ejercicio"));
		}
	}
}
