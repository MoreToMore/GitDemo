package com.text.gitdemo.data;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mengyuanyuan on 2017/6/28.
 */

public class DataManager {
	private final static String BASE_URL = "https://api.github.com/";
	private OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
	private static DataManager inStance;
	private Retrofit.Builder retrofitBuilder;
	private ApiService mApiService;
	private HttpLoggingInterceptor logging =
			new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);
	public  static DataManager getInstance(){
		if(inStance==null){
			inStance = new DataManager();
		}
		return inStance;
	}
	private DataManager(){
		retrofitBuilder = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
		clientBuilder.addInterceptor(logging);
		Retrofit retrofit = retrofitBuilder
				.client(clientBuilder.build())
				.build();
		mApiService = retrofit.create(ApiService.class);
	}
	public Call<List<ResponseUser>> getUserList(){
		return mApiService.getResponseUserList("fs-opensource");
	}
	public Observable<List<ResponseUser>>getUserListObservable(){
		return mApiService.getResponseUserListByRx("fs-opensource");
	}
	public <S> S C (Class<S> s){
		return retrofitBuilder.build().create(s);
	}
}
