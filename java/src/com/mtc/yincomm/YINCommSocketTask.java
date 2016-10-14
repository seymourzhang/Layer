/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.yincomm;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mtc.app.biz.SBYincommManager;
import com.mtc.entity.app.SBYincommData;


/**
 * @ClassName: YINCommSocketTask
 * @Description: 
 * @Date 2015年12月22日 上午11:07:44
 * @Author Yin Guo Xiang
 * 
 */
public class YINCommSocketTask implements Runnable {
	
	private static Logger mLogger =Logger.getLogger(YINCommSocketTask.class);
	
	private static String VALUE_TYPE_FLOAT = "FLOAT";
	private static String VALUE_TYPE_INT = "INT";
	private static String VALUE_TYPE_STRING = "STRING";
	private static String VALUE_TYPE_NULL = "NULL";
	
	private static String DATA_TYPE_NULL = "NULL";
	private static String DATA_TYPE_TEMP = "TEMP";
	private static String DATA_TYPE_HUMI = "HUMI";
	
	private static byte[] HEADBYTE = {0x7B,0x3A,0x29,0x7D};
	
	private static byte TYPE_DATA = 0x01; // 主动上报数据
	private static byte TYPE_KEEPALIVE = 0x02; //设备信息查询
	private static byte TYPE_ACK = 0x03; //时间同步
	
	private static String pattern = "yyyy-MM-dd HH:mm:ss";
	private static SimpleDateFormat df = new SimpleDateFormat(pattern);
	
	private SBYincommManager tSBYincommManager;
	
	public SBYincommManager gettSBYincommManager() {
		return tSBYincommManager;
	}

	public void settSBYincommManager(SBYincommManager tSBYincommManager) {
		this.tSBYincommManager = tSBYincommManager;
	}

	private Socket socket = null;
	
	public YINCommSocketTask(Socket socket){
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			String socketTaskName = df.format(new Date()) ;
			mLogger.info("YINCommSocketTask("+socketTaskName+") start ,线程号====" + Thread.currentThread());
			
			int dataCount = 1;
			InputStream in = socket.getInputStream();
			ByteArrayOutputStream bo = null;
			byte[] buffer = new byte[64];
			int length = 0;
			while ((length=in.read(buffer))!=-1) {
				bo = new ByteArrayOutputStream();
				bo.write(buffer, 0, length);
				long longtime = System.currentTimeMillis();
				byte[] res = bo.toByteArray();
   
				mLogger.info(df.format(new Date(longtime))+"接收帧"+dataCount+"："+ StringHexUtil.bytes2HexString(res));
				
				byte[] response = dealDatas(res);
				
				if(response != null){
					mLogger.info(df.format(new Date(longtime))+"确认帧"+dataCount+"："+ StringHexUtil.bytes2HexString(response));
					OutputStream tOutputStream = socket.getOutputStream();
					tOutputStream.write(response);
					tOutputStream.flush();
				}
				
				mLogger.info("");
				dataCount++;
			}
			mLogger.info("YINCommSocketTask("+socketTaskName+") end   ,线程号====" + Thread.currentThread());
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(socket != null){
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private byte [] dealDatas(byte[] datas){
		byte [] returnData = null;
/*
// 		"7B3A297D840100241F26004900005D3A03E900030001419B333303E90003000242800000"
// 		"7B3A297D04030010000000000000EFD1"
		String res = "" 
				+ "7B3A297D" // 起始符：4字节 ({:)})
		 		+ "8401" // 帧类型：2字节 (b[15]:需确认 b[14]:确认帧 b[13]:保留  b[12-10]:版本号 b[9-8]:加密类型 b[7-0]:消息类型)
		 		+ "0024" // 帧长度：2字节 (帧头+帧负荷长度)
		 		+ "1F260049" // 序列号：4字节 (上行帧时填充 MagicMote 的唯一序列号。下行帧不填数据。)
		 		+ "0000"  // 帧序号：2字节 (帧号用于标识帧的唯一性。除确认帧外，每发送一帧数据，帧号应加 1。确认帧的帧号应该填入需确认帧的帧号。)
		 		+ "C82B"  // 校验码：2字节 (包含整帧（帧头+帧负荷）crc 校验和，计算时本字段填充为 0。源码请参考"crc 计算"这一章节。)
		 		+ "";
		 		
		datas = StringHexUtil.hexString2Bytes(res);
 
 * 
 */
//		String res = "7B3A297D840100301F26004900A206B405E61F26004905E7000405EA28FF9F70741503B803E84198CCCD06405678C824";
		// 请求同步时间
//		res = "7B3A297D"
//			+ "8403"
//			+ "0010"
//			+ "1F260064"
//			+ "0001"
//			+ "17F5";
		
//		datas = StringHexUtil.hexString2Bytes(res);
		
		if(datas == null){
			mLogger.info("Error：数据为空。");
			return returnData;
		}
		if (datas.length<16) {
			mLogger.info("Error：数据长度小于16。");
			return returnData;
		}
		
		// 校验起始符是否正确 datas[0-3]
		if (datas[0] != HEADBYTE[0] || datas[1] != HEADBYTE[1] || datas[2] != HEADBYTE[2] || datas[3] != HEADBYTE[3]) {
			mLogger.info("Error：起始符有误。");
			return returnData;
		}
		// 帧类型  datas[4-5]
		String messageStr1 = StringHexUtil.b2BS(datas[4]);
		String messageStr2 = StringHexUtil.b2BS(datas[5]);
		
		
		String versionNo = messageStr1.substring(3, 6);
//		mLogger.info("messageStr："+messageStr1+messageStr2);
		
		// 是否需要应答帧
		boolean needreturn = messageStr1.substring(0, 1).equals("1")?true:false;
//		mLogger.info("needReturn："+needreturn);
//		mLogger.info("versionNo："+versionNo);
		//0-无加密  1-AES_128  2-AES_196   3-AES_256
		String aesType = messageStr1.substring(6, 8);
//		mLogger.info("aesType："+aesType);
		// 1-主动上报数据   2-设备信息查询   3-时间同步
		int messageType = datas[5]; 
//		mLogger.info("messageType："+messageType);

		// 校验数据的实际长度
		byte [] temp1 = {(byte)0,(byte)0,datas[6],datas[7]};
		if(ByteNumUtil.bytesToInt(temp1) != datas.length){
			mLogger.info("Error：数据标识长度与实际长度不符。");
			return returnData;
		}
		
		// 设备序列号
		byte [] temp2 = {datas[8],datas[9],datas[10],datas[11]};
		String deviceSN = StringHexUtil.bytes2HexString(temp2);
//		mLogger.info("DeviceSN："+ deviceSN);
		
		// 数据序列号
		byte [] temp3 = {(byte)0,(byte)0,datas[12],datas[13]};
		int dataSN = ByteNumUtil.bytesToInt(temp3);
		mLogger.info("数据帧序列号："+ dataSN);
		
		// 数据CRC验证
		byte [] temp4 = datas.clone();
		temp4[14] = 0;
		temp4[15] = 0;
		byte[] crcCal = CRC16_Modbus.getSendBuf(StringHexUtil.bytes2HexString(temp4)); 
//		mLogger.info("crcCal："+ StringHexUtil.bytes2HexString(crcCal));
//		mLogger.info("crcOrigin ："+ StringHexUtil.b2HS(datas[14]) + StringHexUtil.b2HS(datas[15]));
		if(crcCal[0] != datas[14] || crcCal[1] != datas[15]){
			mLogger.info("Error：CRC验证失败。");
			return returnData;
		}
		
		int loopId = 1;
		int IdBit = 0;
		
		// temp-温度  humi-湿度  time-时间同步
		
		
		boolean humiFlag = false;
		
		Float dataValue = 0f;
		int dataId = 0;
		long sensorSN = 0;
		String sensorSTR = null;
		Date dataDate = null;
		
		while((16 + IdBit)<datas.length){
			byte [] temp5 = {(byte)0,(byte)0,datas[16+IdBit],datas[16+IdBit+1]};
			
			int tId = ByteNumUtil.bytesToInt(temp5);
			
			String tName = "";
			String tValueType = "";
			int tValueLength = 0;
			String precision = "";
			if(tId == 1000){
				tName = "温度";
				tValueType = VALUE_TYPE_FLOAT;
				tValueLength = 4;
				precision = "0.1";
				if(!humiFlag){
					dataId = tId;
				}
			}else if(tId == 1005){
				tName = "室外温度";
				tValueType = VALUE_TYPE_FLOAT;
				tValueLength = 4;
				precision = "0.1";
			}else if(tId == 1010){
				tName = "湿度";
				tValueType = VALUE_TYPE_FLOAT;
				tValueLength = 4;
				precision = "0.1";
				
				humiFlag = true;
				dataId = tId;
			}else if(tId == 1015){
				tName = "室外湿度";
				tValueType = VALUE_TYPE_FLOAT;
				tValueLength = 4;
				precision = "0.1";
			}else if(tId == 1020){
				tName = "氨气";
				tValueType = VALUE_TYPE_FLOAT;
				tValueLength = 4;
				precision = "0.1";
			}else if(tId == 1030){
				tName = "硫化氢";
				tValueType = VALUE_TYPE_FLOAT;
				tValueLength = 4;
				precision = "0.1";
			}else if(tId == 1040){
				tName = "二氧化碳";
				tValueType = VALUE_TYPE_FLOAT;
				tValueLength = 4;
				precision = "0.1";
			}else if(tId == 1050){
				tName = "气压";
				tValueType = VALUE_TYPE_FLOAT;
				tValueLength = 4;
				precision = "0.1";
			}else if(tId == 1060){
				tName = "牲畜体温";
				tValueType = VALUE_TYPE_FLOAT;
				tValueLength = 4;
				precision = "0.1";
			}else if(tId == 1500){
				tName = "资产类型";
				tValueType = VALUE_TYPE_INT;
				tValueLength = 4;
			}else if(tId == 1510){
				tName = "MagicMote序列号";
				tValueType = VALUE_TYPE_INT;
				tValueLength = 4;
			}else if(tId == 1511){
				tName = "接口编号";
				tValueType = VALUE_TYPE_INT;
				tValueLength = 2;
			}else if(tId == 1514){
				tName = "传感器序列号";
				tValueType = VALUE_TYPE_INT;
				tValueLength = 8;
			}else if(tId == 1515){
				tName = "耳标序列号";
				tValueType = VALUE_TYPE_INT;
				tValueLength = 4;
			}else if(tId == 1600){
				tName = "时间戳";
				tValueType = VALUE_TYPE_INT;
				tValueLength = 4;
			}else if(tId == 2003){
				tName = "MagicMote供电状态";
				tValueType = VALUE_TYPE_INT;
				tValueLength = 2;
			}else if(tId == 2004){
				tName = "电池电压";
				tValueType = VALUE_TYPE_FLOAT;
				tValueLength = 4;
				precision = "0.01";
			}else if(tId == 2006){
				tName = "运行时间";
				tValueType = VALUE_TYPE_INT;
				tValueLength = 4;
			}else if(tId == 2007){
				tName = "软件版本";
				tValueType = VALUE_TYPE_STRING;
				tValueLength = 0;
			}else if(tId == 2008){
				tName = "硬件版本";
				tValueType = VALUE_TYPE_STRING;
				tValueLength = 0;
			}else{
				mLogger.info("Error：发现未知的数据ID类型。ID="+tId);
				return returnData;
			}
			IdBit += 2;
			
			if(tValueLength == 0){
				if(tValueType.equals(VALUE_TYPE_STRING)){
					byte [] tTempStrLength = new byte[2];
					tTempStrLength[0] = datas[16+IdBit];
					tTempStrLength[1] = datas[16+IdBit+1];
					IdBit += 2;
					tValueLength = ByteNumUtil.bytesToInt(tTempStrLength);
				}else{
					mLogger.info("Error：数据值的长度为正常定义。");
					return returnData;
				}
			}
			
			byte [] tValueBytes = new byte[tValueLength];
			for(int in = 0; in < tValueBytes.length; in ++){
				tValueBytes[in] = datas[16+IdBit+in] ;
			}
			String tRealVal = "";
			if(tValueType == VALUE_TYPE_INT){
				if(tValueLength == 2){
					byte [] intBytes = {(byte)0,(byte)0,tValueBytes[0],tValueBytes[1]};
					tRealVal += ByteNumUtil.bytesToInt(intBytes);
				}else if(tValueLength == 4){
					tRealVal += ByteNumUtil.bytesToInt(tValueBytes);
				}else if(tValueLength == 8){
					tRealVal += ByteNumUtil.bytes2Long(tValueBytes);
				}else{
					mLogger.info("Error：Value类型是Int,但是长度异常。");
					return returnData;
				}
				if(tId == 1600){
					dataDate = new Date(Long.parseLong(tRealVal)*1000);
					tRealVal = df.format(dataDate); 
				}
				if(tId == 1514){
					sensorSN = ByteNumUtil.bytes2Long(tValueBytes);
					sensorSTR = StringHexUtil.bytes2HexString(tValueBytes);
				}
			}else if(tValueType == VALUE_TYPE_FLOAT){
				if(tValueLength == 4){
					tRealVal += Float.intBitsToFloat(ByteNumUtil.bytesToInt(tValueBytes)); 
					
					if(tId == 1000 && !humiFlag){
						dataValue = Float.intBitsToFloat(ByteNumUtil.bytesToInt(tValueBytes));
					}
					if(tId == 1010){
						dataValue = Float.intBitsToFloat(ByteNumUtil.bytesToInt(tValueBytes));
					}
				}else{
					mLogger.info("Error：Value类型是Float,但是长度异常");
				}
			}else if(tValueType == VALUE_TYPE_STRING){
				tRealVal += StringHexUtil.bytes2HexString(tValueBytes);
			}else if(tValueType == VALUE_TYPE_NULL){
				tRealVal += StringHexUtil.bytes2HexString(tValueBytes);
			}
			
			mLogger.debug("(" + StringHexUtil.b2HS(temp5[2]) + StringHexUtil.b2HS(temp5[3]) + ")");
			mLogger.debug(tId + "-" + tName + "-" + tRealVal );
			mLogger.debug("(" + StringHexUtil.bytes2HexString(tValueBytes)+ "," + tValueLength + "字节,"+tValueType + ")");
			mLogger.debug("");
			IdBit += tValueLength;
			
			if((16 + IdBit)==datas.length){
				mLogger.info("Success");
				break;
			}else if((16 + IdBit)>datas.length){
				mLogger.info("Error：数据循环异常结束。");
				break;
			}
			
			loopId ++;
			if(loopId >10){
				mLogger.info("Error：数据循环超过10次，发生异常。");
				return returnData;
			}
		}
		if(needreturn){
			returnData = genResponseByte(messageType,temp3);
		}
		
		
		SBYincommData mSBYincommData = new SBYincommData();
		mSBYincommData.setDataDate(dataDate==null?new Date():dataDate);
		if(messageType == 3){
			mSBYincommData.setDataType("时间同步");
		}else{
			mSBYincommData.setDataType("上报数据");
		}
		mSBYincommData.setMmSn(deviceSN);
		mSBYincommData.setDataSn(dataSN);
		mSBYincommData.setSensorSnHex(sensorSTR);
		mSBYincommData.setSensorSn(sensorSN);
		mSBYincommData.setParaId(dataId);
		mSBYincommData.setParaValue(new BigDecimal(dataValue));
		mSBYincommData.setCreateDate(new Date());
		mSBYincommData.setReceiveData(StringHexUtil.bytes2HexString(datas));
		mSBYincommData.setConfirmData(StringHexUtil.bytes2HexString(returnData));
		
		HashMap<String,Object> tPara = new HashMap<String,Object>();
		tPara.put("SBYincommData", mSBYincommData);
		
		tSBYincommManager.dealSave(tPara);
		
		return returnData;
	}
	
	private byte[] genResponseByte(int messageType,byte[] frameSN){
		byte [] ResponseByte = null;
		if(messageType == 3){
			ResponseByte = new byte[20];
		}else{
			ResponseByte = new byte[16];
		}
		
		// 起始符 4字节
		ResponseByte[0] = HEADBYTE[0];
		ResponseByte[1] = HEADBYTE[1];
		ResponseByte[2] = HEADBYTE[2];
		ResponseByte[3] = HEADBYTE[3];
		
		// 帧类型，2字节
		if(messageType == 3){
			// 时间同步
			ResponseByte[4] = 0x44; // 0100 0100
			ResponseByte[5] = TYPE_ACK;	
		}else{
			ResponseByte[4] = 0x44; // 0100 0100
			ResponseByte[5] = TYPE_DATA;
		}
		
		// 帧长度，2字节
		byte[] len = ByteNumUtil.intToBytes(ResponseByte.length);
		ResponseByte[6] = len[2];
		ResponseByte[7] = len[3];
		// 序列号  4字节
		ResponseByte[8] = 0;
		ResponseByte[9] = 0;
		ResponseByte[10] = 0;
		ResponseByte[11] = 0;
		// 帧序号  2字节
		ResponseByte[12] = frameSN[2];
		ResponseByte[13] = frameSN[3];
		// CRC 2字节
		ResponseByte[14] = 0;
		ResponseByte[15] = 0;
		
		if(messageType == 3){
			int curTimes =  (int)((new Date()).getTime()/1000);
			byte[] curTimeByte = ByteNumUtil.intToBytes(curTimes);
			
			ResponseByte[16] = curTimeByte[0];
			ResponseByte[17] = curTimeByte[1];
			ResponseByte[18] = curTimeByte[2];
			ResponseByte[19] = curTimeByte[3];
		}
		
		byte[] crcAck = CRC16_Modbus.getSendBuf(StringHexUtil.bytes2HexString(ResponseByte));
		ResponseByte[14] = crcAck[0];
		ResponseByte[15] = crcAck[1];
		return ResponseByte;
	}
}
