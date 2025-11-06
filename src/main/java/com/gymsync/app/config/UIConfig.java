package com.gymsync.app.config;

import java.awt.Color;
import java.awt.Font;

public final class UIConfig {

	private UIConfig() {
	}

	public static final String BACKGROUND_IMAGE_PATH = "/images/bg.jpeg";
	public static final String FALLBACK_ICON_PATH = "/images/fallbackIcon.jpg";
	public static final String APP_ICON = "/images/appIcon.png";
	

	// Corporate Colors
	public static final Color PRIMARY_DARK = new Color(0x16, 0x0C, 0x28);
	public static final Color PRIMARY_GOLD = new Color(0xEF, 0xCB, 0x68);
	public static final Color LIGHT_MINT = new Color(0xE1, 0xEF, 0xE6);
	public static final Color GRAY_GREEN = new Color(0xAE, 0xB7, 0xB3);
	public static final Color BLACK_DEEP = new Color(0x00, 0x04, 0x11);
	public static final Color EMIRALD = new Color(0x26A69A);
	public static final Color EMIRALD_DARK = new Color(0x004D40);

	// Gradient Colors For Buttons
	public static final Color[] PRIMARY_GRADIENT = { new Color(0x004D40), new Color(0x26A69A), new Color(0x004D40) };

	public static final Color[] TERMINATE_GRADIENT = { new Color(0x8B0000), new Color(0xE53935), new Color(0x8B0000) };

	public static final Color[] PAUSE_GRADIENT = { new Color(0xA67C00), new Color(0xFFD54F), new Color(0xA67C00) };

	public static final Color[] START_GRADIENT = { new Color(0x1B5E20), new Color(0x66BB6A), new Color(0x1B5E20) };

	// Fonts
	public static final Font TITLE_FONT = new Font("Calibri", Font.BOLD, 32);
	public static final Font SUBTITLE_FONT = new Font("Calibri", Font.BOLD, 22);
	public static final Font BUTTON_FONT = new Font("Segeo UI", Font.BOLD, 16);
	public static final Font LABEL_FONT = new Font("Calibri", Font.PLAIN, 14);
	public static final Font INPUT_FONT = new Font("Segeo UI", Font.BOLD, 14);
	public static final Font WORKOUT_TITLE_FONT = new Font("Segeo UI", Font.BOLD, 15);

	// UI Layout
	public static final int BUTTON_WIDTH = 200;
	public static final int BUTTON_HEIGHT = 50;

	// Company Info
	public static final String COMPANY_NAME = "GymSync";
	public static final String COMPANY_ADDRESS = "Lehendakari Agirre 184, San Inazio, 48015 (Bilbao)";
	public static final String[] USER_HISTORY_COLS = { "Workout", "Nivel", "Tiempo Previsto", "Fecha",
			"Ejercicios Completados" };

	// App Info
	public static final String APP_NAME = "GymSync-App";

}
