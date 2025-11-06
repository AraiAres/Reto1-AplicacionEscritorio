package com.gymsync.app.concurrency.threads;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicBoolean;
import com.gymsync.app.view.customComponents.CronometerLabel;

public class ProgressiveCronometer implements Runnable {
	private final CronometerLabel label;
	private final AtomicBoolean running = new AtomicBoolean(false);
	private final AtomicBoolean paused = new AtomicBoolean(false);
	private Thread thread;
	private long startTime;
	private long pauseOffset;
	private int initialSeconds;

	public ProgressiveCronometer(CronometerLabel label, int initialSeconds) {
		this.label = label;
		this.initialSeconds = initialSeconds;
	}

	@Override
	public void run() {
		startTime = System.currentTimeMillis() - pauseOffset;

		while (running.get()) {
			if (!paused.get()) {
				long elapsedMillis = System.currentTimeMillis() - startTime;
				int elapsedSeconds = (int) (elapsedMillis / 1000);
				int totalSeconds = initialSeconds + elapsedSeconds;

				int hours = totalSeconds / 3600;
				int minutes = (totalSeconds / 60) % 60;
				int seconds = totalSeconds % 60;

				SwingUtilities.invokeLater(() -> label.setTime(hours, minutes, seconds));
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}
	}

	public void start() {
		if (running.get())
			return;
		running.set(true);
		paused.set(false);
		thread = new Thread(this, "ProgressiveCronometerThread");
		thread.start();
	}

	public void pause() {
		if (!running.get() || paused.get())
			return;
		paused.set(true);
		pauseOffset = System.currentTimeMillis() - startTime;
	}

	public void resume() {
		if (!running.get() || !paused.get())
			return;
		paused.set(false);
		startTime = System.currentTimeMillis() - pauseOffset;
	}

	public void stop() {
		running.set(false);
		paused.set(false);
		pauseOffset = 0;
		if (thread != null && thread.isAlive()) {
			thread.interrupt();
		}
	}

	public boolean isRunning() {
		return running.get() && !paused.get();
	}

	public boolean isPaused() {
		return paused.get();
	}
}
