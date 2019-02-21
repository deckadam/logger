package logger;

public class LogFormatter {
	// l stands for level
	// sc stands for senderClass
	// sm stands for senderMethod
	// m stands for message
	// d stands for date (Date is already formatted when log is getting created)
	// fn stands for fileName
	// mn stands for module name
	// ln stands for line number
	// c stands for cause
	private String format = "l,sc,sm,m,d,f";
	private String formatDivider = ",";
	private String standardFormat = "l,sc,sm,m,d,f";
	private String outputDivider = "->";
	private String[] dataFormat;

	public LogFormatter() {
		resetFormat();
	}

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
		String[] outputArray = new String[dataFormat.length];
		for (int i = 0; i < dataFormat.length; i++) {
			String temp = dataFormat[i];
			switch (temp) {
			case "l":
				outputArray[i] = getValue(log.getLevel(), i);
				break;
			case "sc":
				outputArray[i] = getValue(log.getSenderClass(), i);
				break;
			case "sm":
				outputArray[i] = getValue(log.getSenderMethod(), i);
				break;
			case "m":
				outputArray[i] = getValue(log.getMessage(), i);
				break;
			case "d":
				outputArray[i] = getValue(log.getDate(), i);
				break;
			case "fn":
				outputArray[i] = getValue(log.getFileName(), i);
				break;
			case "mn":
				outputArray[i] = getValue(log.getModuleName(), i);
				break;
			case "ln":
				outputArray[i] = getValue(log.getLineNumber(), i);
				break;
			case "c":
				outputArray[i] = getValue(log.getCause(), i);
				break;
			}
		}
		String result = "";
		for (String temp : outputArray) {
			if (isNull(temp)) {
				result += temp;
			}
		}
		return result;
	}

	private String getValue(String setVal, int i) {
		String returnValue = null;
		if (isNull(setVal)) {
			if (doDivide(i, dataFormat.length, setVal))
				returnValue = outputDivider + setVal;
			else {
				returnValue = setVal;
			}
		}
		return returnValue;
	}

	private String getValue(int setVal, int i) {
		String returnValue = null;
		if (setVal != -1) {
			if (doDivide(i, dataFormat.length, setVal))
				returnValue = outputDivider + setVal;
			else {
				returnValue = String.valueOf(setVal);
			}
		}
		return returnValue;
	}

	private boolean doDivide(int i, int length, String nextVal) {
		if (i != 0 && i != length - 1 && isNull(nextVal))
			return true;
		return false;
	}

	private boolean doDivide(int i, int length, int nextVal) {
		if (i != 0 && i != length - 1 && isNull(nextVal))
			return true;
		return false;
	}

	private boolean isNull(String val) {
		return !(val == null);
	}

	private boolean isNull(int val) {
		return !(val == -1);
	}

}
