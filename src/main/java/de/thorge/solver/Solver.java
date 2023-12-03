package de.thorge.solver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class Solver {
	public abstract RiddleSolver<?> getFirstRiddleSolver();
	public abstract RiddleSolver<?> getSecondRiddleSolver();

	public Object getSolution(boolean first){
		return (first ? getFirstRiddleSolver() : getSecondRiddleSolver()).getSolution();
	}

	public Object solveRiddle(FileType type, boolean first){
		RiddleSolver<?> riddleSolver = first ? getFirstRiddleSolver() : getSecondRiddleSolver();
		riddleSolver.setSolver(this);
		riddleSolver.setType(type);
		return riddleSolver.solveRiddle();
	}

	public int getDay() {
		String name = getClass().getName();
		return Integer.parseInt(name.substring(name.lastIndexOf(".")).substring("SolverDay".length() + 1));
	}

	public String get(FileType type) {
		try {
			Path resource = getPath(type);
			if (!Files.exists(resource)) {
				return "";
			}
			return Files.readString(resource);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Path getPath(FileType type) {
		if(type == FileType.JAVA){
			return Path.of("./src/main/java/de/thorge/days/" + type.file + getDay() + ".java");
		}
		return Path.of("./src/main/resources/" + getDay() + File.separator + type.file);
	}

	public enum FileType {
		TEST("test.txt"), RESULT("result.txt"), JAVA("SolverDay");

		private final String file;

		FileType(String file) {
			this.file = file;
		}
	}
}
