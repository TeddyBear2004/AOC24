package de.thorge;

import de.thorge.solver.Solver;

import java.awt.*;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.List;
import java.util.*;

public class Main {
	public static final String URL = "https://adventofcode.com/2024";
	public static final boolean SKIP_BROWSER = false;
	public static final PrintStream PRINT_STREAM = System.out;
	public static final PrintStream EMPTY_STREAM = new PrintStream(new EmptyOutputStream());
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_GREEN = "\u001B[32m";

	public static void main(String[] args) {
		int day = getDay();
		handleWebServer(day);

		for (int i = 1; i <= Math.min(day, 25); i++) {
			runDay(i);
		}
	}

	public static boolean[] runDay(int i) {
		Solver todaysSolver = getSolver(i);

		String result = todaysSolver.get(Solver.FileType.RESULT);
		String test = todaysSolver.get(Solver.FileType.TEST);

		if (result.isBlank() && test.isBlank()) {
			printRed("Überspringe Tag " + i + " wegen leeren Dateien.");
			System.out.println();
			return new boolean[]{false, false};
		}

		boolean[] bo = new boolean[2];
		System.out.print("Tag " + i + ": ");

		bo[0] = foo(todaysSolver, true);

		System.out.print(" || ");

		bo[1] = foo(todaysSolver, false);

		System.out.println();
		return bo;
	}

	public static boolean foo(Solver todaysSolver, boolean first) {
		boolean bool = true;
		Object testSolution = todaysSolver.getSolution(first);

		if (testSolution != null) {
			Object a = null;
			try {
				if (!todaysSolver.get(Solver.FileType.TEST).isBlank())
					a = todaysSolver.solveRiddle(Solver.FileType.TEST, first);
			} catch (Exception e) {
				System.out.println();
				e.printStackTrace(PRINT_STREAM);
			}

			try {
				System.setOut(EMPTY_STREAM);
				Object a1 = todaysSolver.solveRiddle(Solver.FileType.RESULT, first);
				System.setOut(PRINT_STREAM);

				if (Objects.equals(a, testSolution)) {

					printGreen("(" + testSolution + ") " + a1);

				} else {
					bool = false;
					printRed("Erwarteter Wert: " + testSolution + " Errechneter Wert: " + a + " (" + a1 + ")");
				}
			} catch (Exception e) {
				System.setOut(PRINT_STREAM);
				System.out.println();
				e.printStackTrace(PRINT_STREAM);
			}

		} else {
			bool = false;
			printRed(String.valueOf(todaysSolver.solveRiddle(Solver.FileType.RESULT, first)));
		}

		return bool;
	}

	public static List<Integer> getEmptyDays(int maxDay) {
		List<Integer> days = new ArrayList<>();

		for (int i = 1; i <= Math.min(maxDay, 25); i++) {
			Solver solver = getSolver(i);
			if (solver.get(Solver.FileType.RESULT).isBlank()) {
				days.add(i);
			}
		}

		return days;
	}

	private static void handleWebServer(int day) {
		List<Integer> emptyDays = getEmptyDays(day);

		if (emptyDays.isEmpty())
			return;

		Thread[] thread = new Thread[1];
		thread[0] = new Thread(() -> {
			WebServer webServer = new WebServer(day);
			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				webServer.stop();
				thread[0].interrupt();
			}));
		});
		thread[0].start();

		if(SKIP_BROWSER)
			return;

		try {
			Desktop.getDesktop().browse(URI.create(Main.URL + "/day/" + emptyDays.get(0) + "/input"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static void printRed(String s) {
		System.out.print(ANSI_RED + s + ANSI_RESET);
	}

	private static void printGreen(String s) {
		System.out.print(ANSI_GREEN + s + ANSI_RESET);
	}

	private static int getDay() {
		return GregorianCalendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}

	public static Solver getSolver(int day) {
		try {
			@SuppressWarnings("unchecked")
			Class<Solver> aClass = (Class<Solver>) Main.class.getClassLoader().loadClass("de.thorge.days.SolverDay" + day);
			return aClass.getConstructor().newInstance();
		} catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException |
				 NoSuchMethodException e) {
			throw new IllegalArgumentException(e);
		}
	}
}