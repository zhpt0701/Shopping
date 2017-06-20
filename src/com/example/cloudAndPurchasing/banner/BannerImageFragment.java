package com.example.cloudAndPurchasing.banner;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.imageloder.BannerImageLoder;
import com.example.cloudAndPurchasing.imageloder.Imageloderinit;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * viewpage ����ӵ�Ƭ�ζ���ÿ�λ���������е���
 * @author wtw
 * @version 
 * @create_date 2014-10-24 ����5:13:44
 */
@SuppressLint("ValidFragment")
public class BannerImageFragment extends Fragment {
	
	private int imagePosition=0;//��־�������ĸ�viewpageҳ��
	private Object mBitmap;//��Ҫ���õ�ͼƬ
	onCallBack callBack;//�ص��ӿڣ���viewpage���������ʱ�����Ҫ��д����ӿڣ��Ӷ��ص�ͼƬ�ĵ���¼�
	
	public BannerImageFragment(){
		
	}
	
	public BannerImageFragment(Object bitmap,onCallBack callBack){
		mBitmap=bitmap;
		this.callBack=callBack;
	}
	
	/**
	 * ʵ����Ƭ�ζ���
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		BannerImageLoder imageloderinit = new BannerImageLoder(getActivity());
		View layout = inflater.inflate(R.layout.fragment_banner_image, null);
		ImageView imageView = (ImageView)layout.findViewById(R.id.imageView1);
		if(mBitmap instanceof String){
			imageloderinit.imageLoader.displayImage((String)mBitmap, imageView);
		}else if(mBitmap instanceof Integer){
			imageView.setBackgroundDrawable(getResources().getDrawable((Integer) mBitmap));
		}
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				callBack.imageViewClick();//������紫��Ľӿڶ���ķ������Ե�����ͼƬ���в���
			}
		});
		return layout;
	}

	/**
	 * ����
	 *
	 *@param position
	 *@param bitmap
	 */
	public void changImage(int position,Object bitmap) {
		// TODO Auto-generated method stub
		mBitmap=bitmap;
	}
	
	/**
	 * �ص��ӿڣ��ڹ��캯���н��е��ú󣬵����߱�����д
	 * @author κ����
	 * @version 
	 * @create_date 2014-10-24 ����5:27:14
	 */
	public interface onCallBack{
		void imageViewClick();
	}


}
