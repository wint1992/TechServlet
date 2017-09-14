package ru.chuchalin.tech.servlet;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileWritter {
	private Path filePath;
	FileChannel channel;
	private boolean channelIsOpen;
	public FileWritter(String part0, String ...pathPart){
		channelIsOpen = false;
		setPath(part0, pathPart);
	}
	private void setPath(String part0, String ...pathPart){
		filePath = Paths.get(part0, pathPart);
	}
	
	public static FileWritter newReletiveFileWritter(String ...pathPart){
		FileWritter fw = new FileWritter(Paths.get("").toAbsolutePath().toString(), pathPart);
		return fw;
	}
	
	public Path getPath(){
		return filePath;
	}
	
	private boolean open(){
		boolean existsDir = Files.exists(filePath.getParent());
		if (!existsDir) try{
			Files.createDirectories(filePath.getParent());
			existsDir = true;
		}catch(Exception e){
			existsDir = false;
		}
		if (existsDir) try{
				channel = (FileChannel) Files.newByteChannel(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
				channelIsOpen = true;
			}catch(Exception e){
				channelIsOpen = false;
			}
		else channelIsOpen = false;
		return channelIsOpen;
	}
	
	private boolean close(){
		boolean isClose = false;
		if (channelIsOpen){
			try{
				if (channel!=null) channel.close();
				isClose = true;
			}catch(IOException e){
				isClose = false;
			}
		}
		else isClose = true;
		return isClose;
	}
	
	public void write(String outpStr){
		if (this.open()){
			ByteBuffer mBuf = ByteBuffer.allocate(outpStr.length()+1);
			for (int i=0; i<outpStr.length(); i++) mBuf.put((byte)outpStr.charAt(i));
			mBuf.put((byte)'\n');
			mBuf.rewind();
			try{
				channel.write(mBuf);
			}catch(IOException e){}
			finally {
				this.close();
			}
		}
	}
	
	
	public static void printStackTrace(StringBuffer s, Throwable e) {
		s.append(e.toString()).append("\n");
		StackTraceElement[] trace = e.getStackTrace();
		for (int i=0; i < trace.length; i++)
			s.append("\tat " + trace[i]).append("\n");
		
		Throwable ourCause = e.getCause();
		if (ourCause != null)
			printStackTraceAsCause(s, trace, ourCause);
    }
	
	private static void printStackTraceAsCause(StringBuffer s,  StackTraceElement[] causedTrace, Throwable ourCause){
		StackTraceElement[] trace = ourCause.getStackTrace();
		int m = trace.length-1, n = causedTrace.length-1;
		while (m >= 0 && n >=0 && trace[m].equals(causedTrace[n])) {
			m--; n--;
			}
		int framesInCommon = trace.length - 1 - m;
		
		s.append("Caused by: " + ourCause).append("\n");
		for (int i=0; i <= m; i++)
			s.append("\tat " + trace[i]).append("\n");
		if (framesInCommon != 0)
			s.append("\t... " + framesInCommon + " more").append("\n");
		
		Throwable ourCause_ = ourCause.getCause();
		if (ourCause_ != null)
			printStackTraceAsCause(s, trace, ourCause_);
	}
}
