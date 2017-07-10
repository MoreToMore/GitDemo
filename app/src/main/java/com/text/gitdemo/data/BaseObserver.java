package com.text.gitdemo.data;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by mengyuanyuan on 2017/7/6.
 */

public abstract class BaseObserver<T> implements Observer<T> {


	@Override
	public void onSubscribe(@NonNull Disposable d) {

	}

	@Override
	public void onError(@NonNull Throwable e) {

	}

	@Override
	public void onComplete() {

	}
}
