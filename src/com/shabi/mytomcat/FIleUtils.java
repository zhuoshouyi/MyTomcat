package com.shabi.mytomcat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 文件工具类：读取静态资源
 * 
 * @author myViolin
 *
 */
public class FIleUtils {
	/**
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String getFileContent(String path) throws IOException {
		StringBuffer stringBuffer = new StringBuffer();

		// 字符流
		FileReader fileReader = new FileReader(path);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = null;

		while ((line = bufferedReader.readLine()) != null) {
			stringBuffer.append(line);
		}
		bufferedReader.close();
		fileReader.close();
		return stringBuffer.toString();
	}

	/**
	 * 针对图片信息设置的字节读取静态方法
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "resource", "null" })
	public static byte[] getByteFileContent(String path) throws IOException {
		byte[] buff = new byte[1024];
		ByteBuffer fileBuffer = ByteBuffer.allocate(1024000);

		RandomAccessFile aFile; // 随机访问文件的读取和写入
		aFile = new RandomAccessFile(path, "rw");// 读写方式打开path路径下的文件
		FileChannel fileChannel = aFile.getChannel();// 将文件写入Channel通道
		fileChannel.read(fileBuffer);

		buff = fileBuffer.array();
		return buff;
	}
}
