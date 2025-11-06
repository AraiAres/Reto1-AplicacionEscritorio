package com.gymsync.app.concurrency.threads;

import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.SwingUtilities;
import com.gymsync.app.view.customComponents.CronometerLabel;

public class RegressiveCronometer implements Runnable {
	private final CronometerLabel label;
	private final AtomicBoolean running = new AtomicBoolean(false);
	private final AtomicBoolean paused = new AtomicBoolean(false);
	private Thread thread;
	private int remainingSeconds;
	private Runnable onFinish; // Callback when timer finishes

	public RegressiveCronometer(CronometerLabel label, int initialSeconds) {
		this.label = label;
		this.remainingSeconds = initialSeconds;
	}

	public void setOnFinish(Runnable onFinish) {
		this.onFinish = onFinish;
	}

	@Override
	public void run() {
		while (running.get() && remainingSeconds >= 0) {
			// Pause check
			synchronized (this) {
				while (paused.get()) {
					try {
						wait();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						return;
					}
				}
			}

			int hours = remainingSeconds / 3600;
			int minutes = (remainingSeconds / 60) % 60;
			int seconds = remainingSeconds % 60;

			SwingUtilities.invokeLater(() -> label.setTime(hours, minutes, seconds));

			if (remainingSeconds == 0) {
				running.set(false);
				if (onFinish != null)
					onFinish.run();
				break;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}

			remainingSeconds--;
		}
	}

	public void start() {
		if (running.get())
			return;
		running.set(true);
		paused.set(false);
		thread = new Thread(this, "RegressiveCronometerThread");
		thread.start();
	}

	public void pause() {
		paused.set(true);
	}

	public void resume() {
		synchronized (this) {
			paused.set(false);
			notify();
		}
	}

	public void stop() {
		running.set(false);
		paused.set(false);
		if (thread != null && thread.isAlive())
			thread.interrupt();

		SwingUtilities.invokeLater(
				() -> label.setTime(remainingSeconds / 3600, (remainingSeconds / 60) % 60, remainingSeconds % 60));
	}

	public boolean isPaused() {
		return paused.get();
	}

	public boolean isRunning() {
		return running.get() && !paused.get();
	}

	public boolean isFinished() {
		return remainingSeconds <= 0;
	}
}
