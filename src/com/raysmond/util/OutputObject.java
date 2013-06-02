package com.raysmond.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;


public class OutputObject {
	public static String objectToString(Object o)
	{
			StringBuffer s=new StringBuffer();
			s.append("{\n");
			printObject(o,s,"    ");
			s.append("}\n");
			return s.toString();
	}
	private static void printObject(Object o,StringBuffer s,String blank){
			Class clazz=o.getClass();
			Field[] fields=clazz.getDeclaredFields();
			for(int i=0;i<fields.length;i++)
			{
				try{
					String value=fields[i].get(o).toString();
					//判断是不是对象
					if(value.indexOf("@")!=-1)
					{
						if(value.startsWith("[L"))//如果是数组
						{
							s.append(blank+fields[i].getName()+"[\n");
							for(int j=0;j<Array.getLength(fields[i].get(o));j++)
							{
								s.append(blank+"    "+fields[i].getName()+"{\n");
								printObject(Array.get(fields[i].get(o),j),s,blank+"        ");
								s.append(blank+"    "+"}\n");
							}
							s.append(blank+"]\n");
						}else{
							s.append(blank+fields[i].getName()+"{\n");
							printObject(fields[i].get(o),s,blank+"    ");
							s.append(blank+"}\n");
						}
					}else{
						s.append(blank+fields[i].getName()+":");
						s.append(value+"\n");
					}
				}catch (Exception e) {
					if(!fields[i].getName().startsWith("this"))
					{
						s.append(blank+fields[i].getName()+":");
						s.append("null\n");
					}
				}
			}
		}
}
