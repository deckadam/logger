package logger;

public class LogFormatter {
	private String format;
	public LogFormatter(String format) {
		this.format=format;
	}
	
	public boolean setFormat(String format) {
		this.format=format;
		return true;
	}
	
	protected String getFormattedLog(LogObject log) {
		
		
		return format;
	}
	
}
