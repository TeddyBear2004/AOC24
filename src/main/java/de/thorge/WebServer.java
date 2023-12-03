package de.thorge;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import de.thorge.solver.Solver;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class WebServer implements HttpHandler {
	public static final int PORT = 8880;
	private final int day;
	private final HttpServer server;

	public WebServer(int day) {
		this.day = day;

		try {
			server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
			server.createContext("/day", this);
			server.start();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

		if (exchange.getRequestMethod().equals("POST")) {
			String text = new BufferedReader(
					new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))
					.lines()
					.collect(Collectors.joining("\n"));
			int i = text.indexOf(";");

			int day = Integer.parseInt(text.substring(0, i));
			String fileInput = text.substring(i + 1);

			Solver solver = Main.getSolver(day);
			Path path = solver.getPath(Solver.FileType.RESULT);
			Path test = solver.getPath(Solver.FileType.TEST);

			if (!Files.exists(path.getParent()))
				Files.createDirectory(path.getParent());

			if (!Files.exists(path))
				Files.createFile(path);

			if (!Files.exists(test))
				Files.createFile(test);

			Files.write(path, fileInput.getBytes());

			List<Integer> emptyDays = Main.getEmptyDays(this.day);

			boolean[] booleans;
			try {
				booleans = Main.runDay(day);
			} catch (Exception e) {
				System.out.println();
				e.printStackTrace(Main.PRINT_STREAM);
				booleans = new boolean[]{false, false};
			}

			if (!booleans[1]) {
				try {
					Runtime.getRuntime().exec("cmd /c start idea64.exe " + solver.getPath(Solver.FileType.JAVA).toFile().getAbsolutePath());
					if (!booleans[0]) {
						Desktop.getDesktop().browse(URI.create(Main.URL + "/day/" + day));
						Runtime.getRuntime().exec("cmd /c start idea64.exe " + solver.getPath(Solver.FileType.TEST).toFile().getAbsolutePath());
					}
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}

			if (emptyDays.isEmpty()) {
				stop();
			} else {
				String json = "{\"day\": " + emptyDays.get(0) + "}";
				exchange.getResponseHeaders().add("Content-type", "application/json");
				exchange.sendResponseHeaders(200, json.length());
				OutputStream os = exchange.getResponseBody();
				os.write(json.getBytes());
				os.close();
			}
		}
	}

	public void stop() {
		server.stop(10);
	}
}
