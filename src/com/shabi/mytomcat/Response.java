package com.shabi.mytomcat;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * 响应的封装类：将消息写给客服端
 * 
 * @author myViolin
 *
 */
public class Response {
	public Response() {
	}

	/**
	 * 响应动态输出请求
	 * 
	 * @param content
	 * @throws IOException
	 */
	public ByteBuffer writeContent(String content) throws IOException {
		return ByteBuffer.wrap(content.getBytes());
		// sendBuffer.clear();
	}

	/**
	 * 响应静态输出请求
	 * 
	 * @param path
	 * @throws IOException
	 */
	public ByteBuffer writeHtmlFile(String path) throws IOException {
		String htmlContent = FIleUtils.getFileContent(path);
		// System.out.println("---" + htmlContent.getBytes() + "---");
		return ByteBuffer.wrap(htmlContent.getBytes());
		// sendBuffer.clear();
	}

	/**
	 * 响应静态输出请求字节型，万能格式
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public ByteBuffer writeFile(String path) throws IOException {
		// 读取文件
		byte[] buff = new byte[1024];
		buff = FIleUtils.getByteFileContent(path);

		return ByteBuffer.wrap(buff);

		// String htmlContent = FIleUtils.getFileContent(path);
		// System.out.println("---" + htmlContent.getBytes() + "---");
		// return sendBuffer.wrap(htmlContent.getBytes());
	}

}
