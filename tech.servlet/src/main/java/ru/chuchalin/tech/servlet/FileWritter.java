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
}
