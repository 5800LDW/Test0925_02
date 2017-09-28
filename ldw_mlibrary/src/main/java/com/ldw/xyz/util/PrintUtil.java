package com.ldw.xyz.util;

import com.ldw.xyz.control.Controller;

public class PrintUtil {
	public static void e(String msg) {
		if (Controller.isRelease) {
//			back;
		}else{
			System.err.println(msg);
		}
	}

}
