package com.gymsync.app;

import java.io.IOException;

import com.gymsync.app.view.frame.MainFrame;

public class App {
	public static void main(String[] args) {

		startBackupProcess();

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainFrame();
			}
		});
	}

	private static void startBackupProcess() {
		try {
			String classpath = System.getProperty("java.class.path");
			String backupClass = "com.gymsync.app.concurrency.BackupProcess";

			ProcessBuilder pb = new ProcessBuilder("java", "-cp", classpath, backupClass);
			pb.inheritIO();
			pb.start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
