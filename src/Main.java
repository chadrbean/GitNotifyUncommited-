import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {


	public static void main(String[] args) throws IOException {
		String path = "/home/chad/Documents/github/";
		List<String> gotDirLs = getDirLs(path);
		try {
			checkUnCommited(gotDirLs, path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
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
			System.out.println(path+i);
			new ProcessBuilder("git", "-C", path+i, "status").inheritIO().start().waitFor();
		}
	}
}
