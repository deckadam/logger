package logger;

public class LogFormatter {
	// l stands for level
	// sc stands for senderClass
	// sm stands for senderMethod
	// m stands for message
	// d stands for date (Date is already formatted when log is getting created)
	private String format = "l,sc,sm,m,d";
	private String formatDivider = ",";
	private String standardFormat = "l,sc,sm,m,d";
	private String outputDivider = "->";
	private String[] dataFormat;

	public LogFormatter() {resetFormat();}
	
	public LogFormatter(String format) {
		this.format = format;
		resetFormat();
	}

	public LogFormatter(String format, String formatDivider) {
		this.format = format;
		this.formatDivider = formatDivider;
		resetFormat();
	}

	public LogFormatter(String format, String formatDivider, String divider) {
		this.format = format;
		this.outputDivider = divider;
		this.formatDivider = formatDivider;
	}

	public void resetFormat() {
		setDataFormat();
	}

	public void setFormatDivider(String formatDivider) {
		this.formatDivider = formatDivider;
	}

	public void resetToStandardFormat() {
		this.format = standardFormat;
		resetFormat();
	}

	public String getStandardFormat() {
		return this.standardFormat;
	}

	private void setDataFormat() {
		dataFormat = format.split(formatDivider);
	}

	protected String getFormattedLog(LogObject log) {
		String output = "";
		
		for(int i =0; i<dataFormat.length;i++) {
			String temp =dataFormat[i];
			switch (temp) {
			case "l":
				output+=log.getLevel();
				break;
			case "sc":
				output+=log.getSenderClass();
				break;
			case "sm":
				output+=log.getSenderMethod();
				break;
			case "m":
				output+=log.getMessage();
				break;
			case "d":
				output+=log.getDate();
				break;
			}
			if(i!=dataFormat.length-1)
			output+=" "+outputDivider+" ";
		}
		return output;
	}
}
