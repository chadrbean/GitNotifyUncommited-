import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GitNotifyUncommited {


	public static void main(String[] args) throws IOException {
		String path = "/home/chad/Documents/github/";
		List<String> gotDirLs = getDirLs(path);
		try {
			checkUnCommited(gotDirLs, path);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static List<String> getDirLs(String path) throws IOException {
		Process process = Runtime.getRuntime().exec(
		        new String[]{"bash", "-c", "ls"},
		        null, 
		        new File(path));
	
		List<String> list = new ArrayList<String>();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	    String line = "";
	    // Read lines into array
	    while ((line = reader.readLine()) != null) {
	        list.add(line);
	    }
	    return list;
	}
	
	private static void checkUnCommited(List<String> gotDirLs, String path) throws IOException, InterruptedException {
		for (String i : gotDirLs) {

			ProcessBuilder pb = new ProcessBuilder("git", "-C", path+i, "status");
			Process process = pb.start();
			String result = new String(process.getInputStream().readAllBytes());
			System.out.println();
	        if (!(result.contains("up to date"))) {
	        	System.out.print("Out Of Data Path " + (path+i));
	        } 
	        if (result.contains("Changes not staged for commit")) {
	        	System.out.print("Changes not staged for commit " + (path+i));
	        } 
		}
	}
}
