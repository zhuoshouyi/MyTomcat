package com.shabi.mytomcat;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {
	@SuppressWarnings({ "resource", "null" })
	public static ByteBuffer showTable() throws ClassNotFoundException, SQLException, IOException {
		String dataWeb = ""; // 存放dataWeb.html文件的字符串
		RandomAccessFile aFile;
		ByteBuffer fileBuffer = ByteBuffer.allocate(1024); // 文件Buffer
		String firstHalf; // dataWeb的前半段
		String bottomHalf; // dataWeb的后半段
		String insert = ""; // 插入段

		// 数据库连接
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("Driver loaded");

		Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/students", "root", "");
		System.out.println("Database connected");

		// 读取dataWeb.html文件数据到fileChannel中
		aFile = new RandomAccessFile("htmlfile/dataWeb.html", "rw");
		FileChannel fileChannel = aFile.getChannel();
		int len = fileChannel.read(fileBuffer);

		dataWeb = new String(fileBuffer.array());

		// 将dataWeb从</tr>这里开始分割
		firstHalf = dataWeb.substring(0, dataWeb.indexOf("</table>"));
		bottomHalf = dataWeb.substring(dataWeb.indexOf("</table>"));
		// System.out.println(firstHalf + "\n--------------\n" + bottomHalf);

		// 行数
		int lineNo = 0;

		String sql = "select * from user";
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		// 将取出来的resultSet值存入List链表中。
		List<String> list = new ArrayList<String>();
		while (resultSet.next()) {
			list.add(resultSet.getString(1));
			list.add(resultSet.getString(2));
			list.add(resultSet.getString(3));

			lineNo++;
			// System.out.println(list);
		}

		// 替换掉指定html文件的变量值
		for (int i = 0; i < lineNo * 3; i = i + 3) {
			insert += "<tr>";
			insert += "<td>" + list.get(i) + "</td><td>" + list.get(i + 1) + "</td><td>" + list.get(i + 2) + "</td>";
			insert += "</tr>";
		}
		// System.out.println(insert);

		dataWeb = firstHalf + insert + bottomHalf;

		return ByteBuffer.wrap(dataWeb.getBytes());

	}
}
