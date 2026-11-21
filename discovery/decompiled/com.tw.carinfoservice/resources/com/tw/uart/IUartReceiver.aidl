// IUartReceiver.aidl
package com.tw.uart;
import android.os.Bundle;

// Declare any non-default types here with import statements

interface IUartReceiver {

    /**
    * 串口数据更新
    */
    void onUartDataUpdate(in byte[] bytes);

    void onExtendedInterface(in Bundle bundle);
}