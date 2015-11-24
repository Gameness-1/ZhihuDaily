package com.zhihu.tool;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.Toast;

/**
 * Created by root on 14-12-18.
 * 监测网络工具
 */
public class NetTools {
	private Context context;
	ConnectivityManager manager;
	public NetTools(Context context){
		this.context=context;
	}

	/**
	 * 检测网络是否连接
	 *@param isSetNetWork 判断是否需要设置网络
	 * @return 是否链接网络　true 是 false 否
	 */
	public boolean checkNetworkState(boolean isSetNetWork) {
		boolean flag = false;
		//得到网络连接信息
		manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		//去进行判断网络是否连接
		if (manager.getActiveNetworkInfo() != null) {
			flag = manager.getActiveNetworkInfo().isAvailable();
		}
		if (!flag&&isSetNetWork) {
			setNetwork();
            return false;
		}else if (!flag&&!isSetNetWork){
			return false;
		}
		return true;
	}

	/**
	 * 网络未连接时，调用设置方法
	 */
	public void setNetwork() {
		//Toast.makeText(this, "wifi is closed!", Toast.LENGTH_SHORT).show();

		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("网络提示信息")
				.setMessage("网络不可用，如果继续，请先设置网络！")
				.setPositiveButton("设置", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = null;
						/**
						 * 判断手机系统的版本！如果API大于10 就是3.0+
						 * 因为3.0以上的版本的设置和3.0以下的设置不一样，调用的方法不同
						 */
						if (android.os.Build.VERSION.SDK_INT > 10) {
							intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
						} else {
							intent = new Intent();
							ComponentName component = new ComponentName(
									"com.android.settings",
									"com.android.settings.WirelessSettings");
							intent.setComponent(component);
							intent.setAction("android.intent.action.VIEW");
						}
						context.startActivity(intent);
						Toast.makeText(context, "更新后,请刷新", Toast.LENGTH_LONG).show();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.create()
				.show();
	}

	/**
	 * 网络已经连接，然后去判断是wifi连接还是GPRS连接
	 */
	public void isNetworkAvailable() {

		NetworkInfo.State gprs = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		NetworkInfo.State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		if (gprs == NetworkInfo.State.CONNECTED || gprs == NetworkInfo.State.CONNECTING) {
			Toast.makeText(context, "当前是gprs连接", Toast.LENGTH_LONG).show();
		}
		if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) {
			Toast.makeText(context, "当前是wifi连接", Toast.LENGTH_LONG).show();
		}
	}
}
