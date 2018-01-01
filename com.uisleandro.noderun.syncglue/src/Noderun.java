import java.io.IOException;
import java.io.InputStream;

public class Noderun {

	public static void main(String[] args) {
		try {
			Process p = Runtime.getRuntime().exec("node js/uis.js");
			
			InputStream is = p.getInputStream();
			
			StringBuffer sb = new StringBuffer();
			
			int i = 0;
			while( (i = is.read()) != -1){
				sb.append((char)i);
			}
			
			System.out.println(sb.toString());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
