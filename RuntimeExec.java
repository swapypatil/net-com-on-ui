import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RuntimeExec {
	
	static Process proc;
	
	public StreamWrapper getStreamWrapper(InputStream is, String type){
		return new StreamWrapper(is, type);
	}
	
	public RuntimeExec(Process proc){
		this.proc = proc;	
	}
	
	public class StreamWrapper {
		InputStream is = null;
		String type = null;          
		String message = null;

		public String getMessage() {
				return message;
		}

		StreamWrapper(InputStream is, String type) {
			this.is = is;
			this.type = type;
		}

		public void printOp() {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				StringBuffer buffer = new StringBuffer();
				String line = null;
				while ( (line = br.readLine()) != null) {
					buffer.append(line + "\n");
				}
				message = buffer.toString();
			} catch (IOException ioe) {
				ioe.printStackTrace();  
			}
		}
	}
 

// this is where the action is
	public static void main(String[] args){
		//RuntimeExec rte = new RuntimeExec(proc);
		/*StreamWrapper output;

		try {
			output = getStreamWrapper(proc.getInputStream(), "OUTPUT");
			output.printOp();
			System.out.println("Output: "+output.message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
	}
}