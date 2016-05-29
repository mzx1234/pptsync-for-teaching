package com.newppt.android.logical;

import java.util.List;






import com.newppt.android.entity.PPTFileInfo;
import com.newppt1.android_v5.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PPTListAdapt extends BaseAdapter {
	


	private LayoutInflater _inflater;
	List<Object> _list;
	
	private class MyViewHolder {
		ImageView logoImage;
		TextView showpptName;
		TextView showLastRenewTime;
	}

	public PPTListAdapt(LayoutInflater _inflater, List<Object> _list) {
		this._inflater = _inflater;
		this._list = _list;
	}
	
	
	public void setList(List<Object> list) {
		this._list = list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return _list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(_list.get(position) instanceof String) {
			return null;
		}
		else {
		 PPTFileInfo  pptFileInfo = (PPTFileInfo) _list.get(position);
		 return pptFileInfo.getFilePath();
		}
		
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MyViewHolder holder;

		//������������String�����item��Ϊѧ����
		if(_list.get(position) instanceof String) {
			String subject = (String) _list.get(position);
			convertView = _inflater.inflate(R.layout.pptlist_ada_1, parent, false);
			TextView textView = (TextView) convertView.findViewById(R.id.subject);
			textView.setText(subject);
			convertView.setTag(textView);
		}
		
		//�����Item��Ϊ�ļ���
		else {
			PPTFileInfo pptFileInfo = (PPTFileInfo) _list.get(position);
			convertView = _inflater.inflate(R.layout.pptlist_ada, parent, false);
			holder = new MyViewHolder();
			holder.logoImage = (ImageView) convertView
					.findViewById(R.id.pptlogo);
			holder.showpptName = (TextView) convertView
					.findViewById(R.id.pptfilename);// PPT����
			holder.showLastRenewTime = (TextView) convertView
					.findViewById(R.id.time);// �޸�ʱ��
			holder.showpptName.setText(pptFileInfo.getFileName());
			holder.showLastRenewTime.setText("�޸�ʱ��: "
					+ pptFileInfo.getTime());

			// convertView.setOnClickListener(new MyApplyListener(position));
			convertView.setTag(holder);
		}
				
			

		return convertView;
	}

}
