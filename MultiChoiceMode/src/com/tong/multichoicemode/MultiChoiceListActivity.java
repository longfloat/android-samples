package com.tong.multichoicemode;

import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MultiChoiceListActivity extends ListActivity {

	private MyAdapter mAdapter = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multichoice);
		String[] items = getResources().getStringArray(R.array.list_items);

		mAdapter = new MyAdapter(items);
		setListAdapter(mAdapter);
		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		getListView().setMultiChoiceModeListener(mMultiChoiceModeListener);
	}

	private MultiChoiceModeListener mMultiChoiceModeListener = new MultiChoiceModeListener() {
		private View mActionbarMultiSelectedView = null;
		private TextView mSelectedCount = null;

		private void inflateView() {
			if (mActionbarMultiSelectedView == null) {
				mActionbarMultiSelectedView = LayoutInflater.from(
						MultiChoiceListActivity.this).inflate(
						R.layout.actionbar_multiselect_multi_listview, null);
				mSelectedCount = (TextView) mActionbarMultiSelectedView
						.findViewById(R.id.actionbar_multiselect_count);
			}
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			// TODO Auto-generated method stub
			inflateView();
			return true;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			// TODO Auto-generated method stub
			for (int i = 0; i < mAdapter.getCount(); i++) {
				mAdapter.setChecked(false, i);
			}
		}

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			// TODO Auto-generated method stub
			if (mActionbarMultiSelectedView == null) {
				inflateView();
			}
			inflateView();
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.context_multichoice, menu);

			mode.setCustomView(mActionbarMultiSelectedView);

			return true;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			// TODO Auto-generated method stub
			switch (item.getItemId()) {
				case R.id.context_action_item1:
					Toast.makeText(MultiChoiceListActivity.this, "context menu 1",
							Toast.LENGTH_SHORT).show();

					return true;

				case R.id.context_action_item2:
					Toast.makeText(MultiChoiceListActivity.this, "context menu 1",
							Toast.LENGTH_SHORT).show();
					return true;

				default:
					return false;
			}
		}

		@Override
		public void onItemCheckedStateChanged(ActionMode mode, int position,
				long id, boolean checked) {
			// TODO Auto-generated method stub
			mSelectedCount.setText(String.valueOf(getListView()
					.getCheckedItemCount()));

			mAdapter.setChecked(checked, position);
			// mode.setTitle("Selected Items");
			// int count = getListView().getCheckedItemCount();
			// if (count == 1) {
			// mode.setSubtitle(count + " item selected.");
			// } else {
			// mode.setSubtitle(count + " items selected.");
			// }
		}
	};

	private class MyAdapter extends BaseAdapter {
		private String[] mItems;
		private boolean[] mChecked;
		
		public MyAdapter(String[] items) {
			this.mItems = items;
			this.mChecked = new boolean[mItems.length];
			for (int i = 0; i < mChecked.length; i++) {
				mChecked[i] = false;
			}
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mItems.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mItems[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public void setChecked(boolean checked, int position) {
			mChecked[position] = checked;
			notifyDataSetChanged();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if (null == convertView) {
				convertView = LayoutInflater.from(MultiChoiceListActivity.this)
						.inflate(R.layout.adapter_item_listview, null);
				holder = new ViewHolder();
				holder.text = (TextView) convertView
						.findViewById(R.id.adapter_item_listview_text);
				holder.linear = (LinearLayout) convertView
						.findViewById(R.id.adapter_item_linear);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.text.setText(mItems[position]);

			if (mChecked[position]) {
				holder.linear.setBackgroundColor(getResources().getColor(
						android.R.color.holo_blue_light));
			} else {
				holder.linear.setBackgroundColor(Color.WHITE);
			}


			return convertView;
		}

		class ViewHolder {
			LinearLayout linear;
			TextView text;
		}

	}
}
