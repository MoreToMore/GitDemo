package com.text.gitdemo.data;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by mengyuanyuan on 2017/7/6.
 */

public abstract class BaseCallBack implements Callback {
	@Override
	public void onFailure(Call call, Throwable t) {

	}
}
