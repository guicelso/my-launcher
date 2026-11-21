// IUartController.aidl
package com.tw.uart;
import com.tw.uart.IUartReceiver;
import android.os.Bundle;

// Declare any non-default types here with import statements

interface IUartController {

    void registerUartControllerCallBack(in IUartReceiver iUartReceiver);

    void unRegisterUartControllerCallBack(in IUartReceiver iUartReceiver);

    /**
    * 传入mBaudRate波特率、版本号、串口号，打开串口
    * 打开成功返回1，失败返回0
    */
    int openUart(in int mBaudRate, in String dev_ttyS);

    /**
    * 关闭串口
    */
    void closeUart();

    /**
    * 向串口写数据
    */
    void wirteUartData(in byte[] data);

    Bundle extendedInterface(in Bundle bundle);

}