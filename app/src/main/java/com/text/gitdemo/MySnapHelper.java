package com.text.gitdemo;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by mengyuanyuan on 2017/7/2.
 */

public class MySnapHelper extends LinearSnapHelper {


	private OrientationHelper mHorizontalHelper;

	@Override
	public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
		int[] out = new int[2];
		if (layoutManager.canScrollHorizontally()) {
			out[0] = distanceToStart(targetView, getHorizontalHelper(layoutManager));
		} else {
			out[0] = 0;
		}
		return out;

	}

	/**
	 * 这个方法是计算偏移量
	 *
	 * @param targetView
	 * @param helper
	 * @return
	 */
	private int distanceToStart(View targetView, OrientationHelper helper) {
		return helper.getDecoratedStart(targetView) - helper.getStartAfterPadding();
	}

	/**
	 * 获取视图的方向
	 *
	 * @param layoutManager
	 * @return
	 */
	private OrientationHelper getHorizontalHelper(@NonNull RecyclerView.LayoutManager layoutManager) {
		if (mHorizontalHelper == null) {
			mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
		}
		return mHorizontalHelper;
	}

	@Override
	public View findSnapView(RecyclerView.LayoutManager layoutManager) {
		return  findStartView(layoutManager, getHorizontalHelper(layoutManager));
	}
	/**
	 * 找到第一个显示的view
	 * @param layoutManager
	 * @param helper
	 * @return
	 */
	private View findStartView(RecyclerView.LayoutManager layoutManager,
							   OrientationHelper helper) {
		if (layoutManager instanceof LinearLayoutManager) {
			int firstChild = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
			int lastChild = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
			if (firstChild == RecyclerView.NO_POSITION) {
				return null;
			}

			//这是为了解决当翻到最后一页的时候，最后一个Item不能完整显示的问题
			if (lastChild == layoutManager.getItemCount() - 1) {
				return layoutManager.findViewByPosition(lastChild);
			}
			View child = layoutManager.findViewByPosition(firstChild);

			//得到此时需要左对齐显示的条目
			if (helper.getDecoratedEnd(child) >= helper.getDecoratedMeasurement(child) / 2
					&& helper.getDecoratedEnd(child) > 0) {
				return child;
			} else {
				return layoutManager.findViewByPosition(firstChild + 1);
			}
		}
		return super.findSnapView(layoutManager);
	}
}
