package net.gageot.test.utils;

import com.google.common.util.concurrent.*;

import java.util.concurrent.*;

/**
 * Poor man's profiler.<br/>
 * Note: Making this class test scope in Maven ensures that this code doesn't go in production.
 */
public final class Profiler {
	private static final AtomicLongMap<String> CALL_COUNT = AtomicLongMap.create();
	private static final AtomicLongMap<String> TIMINGS = AtomicLongMap.create();

	private Profiler() {
		// Static class
	}

	public static boolean printCallCount() {
		return printCallCountEvery(1);
	}

	public static boolean printCallCount(String callName) {
		return printCallCountEvery(callName, 1);
	}

	public static boolean printCallCountEvery(int every) {
		return printCallCountEvery("<DEFAULT>", every);
	}

	public static boolean printCallCountEvery(String callName, int every) {
		long callCount = CALL_COUNT.incrementAndGet(callName);
		if (0 == (callCount % every)) {
			debug("%s was called %d times%n", callName, callCount);
			return true;
		}
		return false;
	}

	public static void startTime(final String callName) {
		if (0L == TIMINGS.getAndAdd(callName, -System.nanoTime())) {
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					debug("Timing for: %s=%dms%n", callName, TimeUnit.NANOSECONDS.toMillis(TIMINGS.get(callName)));
				}
			});
		}
	}

	public static void endTime(String callName) {
		if (0L == TIMINGS.getAndAdd(callName, System.nanoTime())) {
			throw new IllegalStateException("startTime() should be called once per endTime() call");
		}
	}

	public static void printStackTrace() {
		new RuntimeException().printStackTrace();
	}

	public static <T> T printIfNull(String callPointName, T value) {
		if (value == null) {
			debug("Null value at %s%n", callPointName);
		}
		return value;
	}

	public static void reset(String callPointName) {
		CALL_COUNT.remove(callPointName);
	}

	public static void resetAll() {
		CALL_COUNT.clear();
	}

	static void debug(String message, Object... arguments) {
		System.out.printf(message, arguments);
	}
}
