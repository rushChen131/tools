package com.jz.tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class Test {

	public static String s = "\"id\"\r\n" +
			"\"real_name\"\r\n" +
			"\"user_name\"\r\n" +
			"\"password\"\r\n" +
			"\"salt\"\r\n" +
			"\"phone\"\r\n" +
			"\"email\"\r\n" +
			"\"locked\"\r\n" +
			"\"description\"\r\n" +
			"\"last_login_time\"\r\n" +
			"\"last_login_ip\"\r\n" +
			"\"login_count\"\r\n" +
			"\"is_customer\"\r\n" +
			"\"company_code\"\r\n" +
			"\"create_time\"\r\n" +
			"\"update_time\"\r\n" +
			"\"create_admin\"\r\n" +
			"\"update_admin\"\r\n" +
			"\"data_scope\"\r\n" +
			"";



	public static void main(String[] args) throws Exception {
//		splitString(s);
		readFile();



	}

	public static void readFile() throws Exception {
		FileInputStream fis = new FileInputStream("D:\\lyd\\1.txt");
		FileChannel  channel = fis.getChannel();
		FileOutputStream fos = new FileOutputStream("D:\\lyd\\2.txt");
		int bufSize = 1000000;//一次读取的字节长度
		FileChannel  outChannel = fos.getChannel();
		ByteBuffer write = ByteBuffer.allocateDirect(1000000);
		ByteBuffer read = ByteBuffer.allocateDirect(1000000);

		readFileByLine(bufSize, channel, read, outChannel, write);
//		 readFileByLine1(bufSize, channel, read, outChannel, write);

		if(channel.isOpen()){
			channel.close();
		}
		if(outChannel.isOpen()){
			outChannel.close();
		}
	}

	public static void readFileByLine1(int bufSize, FileChannel fcin,
									   ByteBuffer rBuffer, FileChannel fcout, ByteBuffer wBuffer) {
		String enter = "\n";
		List<String> dataList = new ArrayList<String>();//存储读取的每行数据
		byte[] lineByte = new byte[0];

//		String encode = "GBK";
		String encode = "UTF-8";
		try {
			//temp：由于是按固定字节读取，在一次读取中，第一行和最后一行经常是不完整的行，因此定义此变量来存储上次的最后一行和这次的第一行的内容，
			//并将之连接成完成的一行，否则会出现汉字被拆分成2个字节，并被提前转换成字符串而乱码的问题
			byte[] temp = new byte[0];
			while (fcin.read(rBuffer) != -1) {//fcin.read(rBuffer)：从文件管道读取内容到缓冲区(rBuffer)
				int rSize = rBuffer.position();//读取结束后的位置，相当于读取的长度
				byte[] bs = new byte[rSize];//用来存放读取的内容的数组
				rBuffer.rewind();//将position设回0,所以你可以重读Buffer中的所有数据,此处如果不设置,无法使用下面的get方法
				rBuffer.get(bs);//相当于rBuffer.get(bs,0,bs.length())：从position初始位置开始相对读,读bs.length个byte,并写入bs[0]到bs[bs.length-1]的区域
				rBuffer.clear();

				int startNum = 0;
				int LF = 10;//换行符
				int CR = 13;//回车符
				boolean hasLF = false;//是否有换行符
				String whiteStr = "";
				for(int i = 0; i < rSize; i++){
					if(bs[i] == LF){
						hasLF = true;
						int tempNum = temp.length;
						int lineNum = i - startNum;
						lineByte = new byte[tempNum + lineNum];//数组大小已经去掉换行符

						System.arraycopy(temp, 0, lineByte, 0, tempNum);//填充了lineByte[0]~lineByte[tempNum-1]
						temp = new byte[0];
						System.arraycopy(bs, startNum, lineByte, tempNum, lineNum);//填充lineByte[tempNum]~lineByte[tempNum+lineNum-1]

						String line = new String(lineByte, 0, lineByte.length, encode);//一行完整的字符串(过滤了换行和回车)
						if (line != null ){
							line = "'"+line+"',";
							writeFileByLine(fcout, wBuffer,line);
							if(i + 1 < rSize && bs[i + 1] == CR){
								startNum = i + 2;
							}else{
								startNum = i + 1;
							}
						}

					}
				}
				if(hasLF){
					temp = new byte[bs.length - startNum];
					System.arraycopy(bs, startNum, temp, 0, temp.length);
				}else{//兼容单次读取的内容不足一行的情况
					byte[] toTemp = new byte[temp.length + bs.length];
					System.arraycopy(temp, 0, toTemp, 0, temp.length);
					System.arraycopy(bs, 0, toTemp, temp.length, bs.length);
					temp = toTemp;
				}
			}
			if(temp != null && temp.length > 0){//兼容文件最后一行没有换行的情况
				String line = new String(temp, 0, temp.length, encode);
				dataList.add(line);
//				System.out.println(line);
				writeFileByLine(fcout, wBuffer, line + enter);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void readFileByLine(int bufSize, FileChannel fcin,
									  ByteBuffer rBuffer, FileChannel fcout, ByteBuffer wBuffer) {
		String enter = "\n";
		List<String> dataList = new ArrayList<String>();//存储读取的每行数据
		byte[] lineByte = new byte[0];

//		String encode = "GBK";
		String encode = "UTF-8";
		try {
			//temp：由于是按固定字节读取，在一次读取中，第一行和最后一行经常是不完整的行，因此定义此变量来存储上次的最后一行和这次的第一行的内容，
			//并将之连接成完成的一行，否则会出现汉字被拆分成2个字节，并被提前转换成字符串而乱码的问题
			byte[] temp = new byte[0];
			while (fcin.read(rBuffer) != -1) {//fcin.read(rBuffer)：从文件管道读取内容到缓冲区(rBuffer)
				int rSize = rBuffer.position();//读取结束后的位置，相当于读取的长度
				byte[] bs = new byte[rSize];//用来存放读取的内容的数组
				rBuffer.rewind();//将position设回0,所以你可以重读Buffer中的所有数据,此处如果不设置,无法使用下面的get方法
				rBuffer.get(bs);//相当于rBuffer.get(bs,0,bs.length())：从position初始位置开始相对读,读bs.length个byte,并写入bs[0]到bs[bs.length-1]的区域
				rBuffer.clear();

				int startNum = 0;
				int LF = 10;//换行符
				int CR = 13;//回车符
				boolean hasLF = false;//是否有换行符
				boolean name = false;
				boolean url = false;
				boolean a = false;
				String sql = "INSERT INTO t_jz_security_permission (`type`, `name`, `url`, `parent_id`, `permission`) VALUES ('menu', '未分配客户', '/remindPool/unallocated', '4', 'jianzhuoDunRemindPool:unallocated:*');";
				for(int i = 0; i < rSize; i++){
					if(bs[i] == LF){

						hasLF = true;
						int tempNum = temp.length;
						int lineNum = i - startNum;
						lineByte = new byte[tempNum + lineNum];//数组大小已经去掉换行符

						System.arraycopy(temp, 0, lineByte, 0, tempNum);//填充了lineByte[0]~lineByte[tempNum-1]
						temp = new byte[0];
						System.arraycopy(bs, startNum, lineByte, tempNum, lineNum);//填充lineByte[tempNum]~lineByte[tempNum+lineNum-1]

						String line = new String(lineByte, 0, lineByte.length, encode);//一行完整的字符串(过滤了换行和回车)
//						line = line.replace("`t_borrower_relative`","t_jz_borrower_relative");
//						line = line.replace("'0'","0");
//						line = line.replace("'1'","1");
						if(line.indexOf("@ShiroPermissions")!=-1) {
							String[] s = line.split("name");
							String str = s[1].split("\\= \"")[1].split("\"\\,")[0];
							dataList.add(str);
							sql = sql.replace("未分配客户", str);
							name= true;
						}else if(line.indexOf("@RequiresPermissions")!=-1) {
							String[] s = line.split("@RequiresPermissions");
							String str = s[1].split("\\(\"")[1].split("\"\\)")[0];
							dataList.add(str);
							sql = sql.replace("jianzhuoDunRemindPool:unallocated:*", str);
							a = true;
						}else if(line.indexOf("@RequestMapping")!=-1) {
							String[] s = line.split("@RequestMapping");
							String str = "/remindPool/"+s[1].split("\\= \"")[1].split("\\\"")[0];
							dataList.add(str);
							sql = sql.replace("/remindPool/unallocated", str);
							url = true;
						}else {
							line = "";
						}
						if(name&url&a) {
							writeFileByLine(fcout, wBuffer,sql.equals("")?"": sql + enter);
							sql = "INSERT INTO t_jz_security_permission (`type`, `name`, `url`, `parent_id`, `permission`) VALUES ('menu', '未分配客户', '/remindPool/unallocated', '4', 'jianzhuoDunRemindPool:unallocated:*');";
							//过滤回车符和换行符
							name = false;
							url = false;
							a = false;

						}
						if(i + 1 < rSize && bs[i + 1] == CR){
							startNum = i + 2;
						}else{
							startNum = i + 1;
						}

//

					}
				}
				if(hasLF){
					temp = new byte[bs.length - startNum];
					System.arraycopy(bs, startNum, temp, 0, temp.length);
				}else{//兼容单次读取的内容不足一行的情况
					byte[] toTemp = new byte[temp.length + bs.length];
					System.arraycopy(temp, 0, toTemp, 0, temp.length);
					System.arraycopy(bs, 0, toTemp, temp.length, bs.length);
					temp = toTemp;
				}
			}
			if(temp != null && temp.length > 0){//兼容文件最后一行没有换行的情况
				String line = new String(temp, 0, temp.length, encode);
				dataList.add(line);
//				System.out.println(line);
				writeFileByLine(fcout, wBuffer, line + enter);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 写到文件上
	 * @param fcout
	 * @param wBuffer
	 * @param line
	 */
	@SuppressWarnings("static-access")
	public static void writeFileByLine(FileChannel fcout, ByteBuffer wBuffer,
									   String line) {
		try {
			fcout.write(wBuffer.wrap(line.getBytes("UTF-8")), fcout.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	public static void splitString(String s) {
		String[] strings = s.split("\"");
		StringBuffer stringBuffer = new StringBuffer("");
		for (String str : strings){
			if (!str.trim().equals("")){
				stringBuffer.append(str).append(",");
			}
		}
		System.out.println(stringBuffer.substring(0,stringBuffer.length()-1));
	}
}
