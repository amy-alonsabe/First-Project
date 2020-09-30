import java.io.FileInputStream;
import java.util.Hashtable;
import java.util.Scanner;

public class ReadIni {
	public static Hashtable<String, String> read_ini(String fileName) {
		Hashtable<String, String> dbIni = new Hashtable<String, String>();
		
		try {
			FileInputStream fis = new FileInputStream(fileName);
			Scanner sc = new Scanner(fis);
			
			while(sc.hasNextLine()) {
				String contentIni[] = sc.nextLine().split("=");
				
				if(contentIni.length > 1) {
					dbIni.put(contentIni[0], contentIni[1]);
				}
			}
			
			sc.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return dbIni;
	}
}
