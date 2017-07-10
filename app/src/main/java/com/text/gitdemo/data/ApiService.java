package com.text.gitdemo.data;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by mengyuanyuan on 2017/6/28.
 */

public interface ApiService {
	@GET("/users/{user}/repos")
	Call<List<ResponseUser>> getResponseUserList(
			@Path("user") String user
	);
	@GET("/users/{user}/repos")
	Observable<List<ResponseUser>> getResponseUserListByRx(
			@Path("user") String user
	);

}
