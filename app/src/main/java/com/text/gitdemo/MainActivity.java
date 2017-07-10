package com.text.gitdemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.text.gitdemo.data.BaseObserver;
import com.text.gitdemo.data.DataManager;
import com.text.gitdemo.data.ResponseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

	@BindView(R.id.my_recycler_view)
	RecyclerView myRecyclerView;
	@BindView(R.id.my_iv)
	AppCompatImageView myIv;
	private Unbinder unbinder;
	private List<ResponseUser> data = new ArrayList<>();
	private MyRecyclerViewAdapter adapter;
	private DividerItemDecoration itemDecoration;

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		unbinder = ButterKnife.bind(this);
		myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		for (int i = 0; i < 10; i++) {
			data.add(new ResponseUser(i + "这个可以的", "我就是想试一试" + i + "中文啊"));
		}
		int statusBarHeight = getStatusBarHeight(this);
		ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) myRecyclerView.getLayoutParams();
		layoutParams.topMargin = statusBarHeight;
		myRecyclerView.setLayoutParams(layoutParams);
		itemDecoration = new DividerItemDecoration(this, LinearLayout.VERTICAL);
		itemDecoration.setDrawable(getResources().getDrawable(R.drawable.item_diviler, null));
		myRecyclerView.addItemDecoration(itemDecoration);
		adapter = new MyRecyclerViewAdapter(data);
		myRecyclerView.setAdapter(adapter);
		View decorView = getWindow().getDecorView();
		int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
				| View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
		decorView.setSystemUiVisibility(option);

//		ActionBar actionBar = getActionBar();
//		actionBar.hide();

		getWindow().setStatusBarColor(Color.TRANSPARENT);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setVisibility(View.GONE);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
//				startActivity(new Intent(MainActivity.this, Main2Activity.class));
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
//				DataManager.getInstance().getUserList().enqueue(new Callback<List<ResponseUser>>() {
//					@Override
//					public void onResponse(Call<List<ResponseUser>> call, Response<List<ResponseUser>> response) {
//						List<ResponseUser> body = response.body();
//						data.addAll(body);
//						adapter.notifyDataSetChanged();
//
//					}
//
//					@Override
//					public void onFailure(Call<List<ResponseUser>> call, Throwable t) {
//
//					}
//				});
				final int resouceId = R.mipmap.ic_launcher_round;
				Observable.just(resouceId)
						.map(new Function<Integer, Drawable>() {
							@Override
							public Drawable apply(@NonNull Integer integer) throws Exception {
								return getTheme().getDrawable(integer);
							}
						})
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new Consumer<Drawable>() {
							@Override
							public void accept(@NonNull Drawable drawable) throws Exception {
								myIv.setBackgroundDrawable(drawable);
							}
						});
//				Observable.create(new ObservableOnSubscribe<Drawable>() {
//					@Override
//					public void subscribe(@NonNull ObservableEmitter<Drawable> e) throws Exception {
//						Drawable drawable = getTheme().getDrawable(resouceId);
//						e.onNext(drawable);
//						Log.d("ob",Thread.currentThread().getName());
//					}
//				}).subscribeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribeOn(Schedulers.newThread()).subscribeOn(AndroidSchedulers.mainThread())
//						.observeOn(Schedulers.newThread())
//						.observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Drawable>() {
//					@Override
//					public void accept(@NonNull Drawable drawable) throws Exception {
//						myIv.setBackgroundDrawable(drawable);
//						Log.d("ob",Thread.currentThread().getName());
//					}
//				});
				DataManager.getInstance().getUserListObservable()
						.observeOn(AndroidSchedulers.mainThread())
						.subscribeOn(Schedulers.io())
						.subscribe(new BaseObserver<List<ResponseUser>>() {
							@Override
							public void onNext(@NonNull List<ResponseUser> responseUsers) {
								data.addAll(responseUsers);
								adapter.notifyDataSetChanged();
							}
						});
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbinder.unbind();
	}

	private int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}
}
