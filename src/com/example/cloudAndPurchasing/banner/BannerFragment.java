package com.example.cloudAndPurchasing.banner;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.cloudAndPurchasing.R;

import java.util.ArrayList;


/**
 * ����Զ�������
 * @author wtw
 * @version 
 * @create_date 2014-10-24 ����5:29:01
 */
@SuppressLint("ValidFragment")
public class BannerFragment extends Fragment {
	public static int POINT_WIDE=40;//��Ŀ��
	public static int POINT_NOT_CLICK= R.drawable.buttonred;//��δѡ����ʾ��ͼƬ
	public static int POINT_CLICK=R.drawable.buttongreen;//��ѡ����ʾ��ͼƬ
	
	private View layout;//�����Ĳ��ֶ���
	private ViewPager viewPager;
	private ArrayList<Object> bitmapList;//�����洢��紫��Ĺ��ͼƬ
	private Handler mHandler;//�������Ƹ���ʱ���Զ���ת
	private Boolean isPerson=false;//�����ж��Ƿ��û��л���viewpage
	private ArrayList<ImageView> mPointView=new ArrayList<ImageView>();//�����洢��̬��ӵĵ����

	private onCallBack mCallBack;//ͼƬ�ĵ����Ļص�����
	
	public BannerFragment(){
		
	}
	
	public BannerFragment(ArrayList<Object> bitmapList,onCallBack callBack) {
		// Required empty public constructor
		this.bitmapList=bitmapList;
		mCallBack=callBack;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		layout=inflater.inflate(R.layout.fragment_banner, null);
		intPoint();
		initViewPage();
		scollPageView();
		return layout;
	}
	
	/**
	 * ��ͼƬ���������ж�̬����ӵ�
	 */
	private void intPoint(){
		LinearLayout mPointLayout=(LinearLayout)layout.findViewById(R.id.layout_addPointImage);
		Drawable drawable = getResources().getDrawable(POINT_NOT_CLICK);
		for (int i = 0; i < bitmapList.size(); i++) {
			ImageView pointView=new ImageView(getActivity());
			pointView.setImageDrawable(drawable);
			mPointView.add(pointView);
			LinearLayout.LayoutParams linearParams =new LinearLayout.LayoutParams(POINT_WIDE, POINT_WIDE);
			linearParams.width = POINT_WIDE;// �ؼ��Ŀ�ǿ�����30   
			linearParams.height = POINT_WIDE;// �ؼ��ĸ�ǿ�����20  
			linearParams.setMargins(1, 1, 1, 1);//����imageview�������ؼ��ļ��
			pointView.setLayoutParams(linearParams);//����iamgeview�Ŀ�ߣ��������ؼ�֮��ľ����
			mPointLayout.addView(pointView);
		}
	}

	/**
	 * ����handler,��viewpager����ÿ3���Զ�����һ��
	 */
	private void scollPageView() {
		mHandler=new Handler();
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(!isPerson){//�ж��Ƿ����˹��������ǵĻ���ֹͣ�Զ�����
					int item=viewPager.getCurrentItem();
					viewPager.setCurrentItem(item+1);
				}
				mHandler.postDelayed(this, 3000);//������������run  ������ѭ������
			}
		}, 3000);
	}

	/**
	 * ʵ����viewpage����
	 */
	private void initViewPage() {
		Drawable drawable = getResources().getDrawable(POINT_CLICK);
		mPointView.get(0).setImageDrawable(drawable);//���õ��ͼ��
		viewPager=(ViewPager)layout.findViewById(R.id.viewPager1);
		FragmentManager fm = getChildFragmentManager();
		viewPager.setAdapter(new MyViewPagerAdapter(fm));
		viewPager.setCurrentItem(50000);//����viewpage��50000ҳ
		viewPager.setOnPageChangeListener(new PageChangerListener());//���û���������
	}
	
	/**
	 * viewpage ����������
	 * @author wtw
	 * @version 
	 * @create_date 2014-10-24 ����5:39:28
	 */
	class PageChangerListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {	
			switch (arg0) {
			case ViewPager.SCROLL_STATE_DRAGGING://������ֶ�����������isPersonΪtrue
				isPerson=true;
				break;
			case ViewPager.SCROLL_STATE_IDLE:
				isPerson=false;
				break;
//			case ViewPager.SCROLL_STATE_SETTLING:
//				isPerson=false;
//				break;

			default:
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		/**
		 * ��������ĳһ��ҳ��ʱ��ͬʱ���õײ��ĵ����ɫ�ı仯
		 */
		@Override
		public void onPageSelected(int position) {
			position=position%bitmapList.size();//��viewpage��ҳ����жԹ��ͼƬ��������ȡ�࣬����������Ӧ�ĵ����ɫ��viewpage��ҳ������ѭ������һֱ����
			for (int i = 0; i < bitmapList.size(); i++) {
				if(i==position){
					Drawable drawable = layout.getResources().getDrawable(POINT_CLICK);
					mPointView.get(i).setImageDrawable(drawable);
				}else{
					Drawable drawable = layout.getResources().getDrawable(POINT_NOT_CLICK);
					mPointView.get(i).setImageDrawable(drawable);
				}
			}
			
		}
		
	}

	/**
	 * ��viewpage����������
	 * @author wtw
	 * @version 
	 * @create_date 2014-10-24 ����5:43:49
	 */
	class MyViewPagerAdapter extends FragmentPagerAdapter {

		public MyViewPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int position) {
			final int mPosition=position%bitmapList.size();
			BannerImageFragment bif=new BannerImageFragment(//������ʾͼƬ��Ƭ��
					bitmapList.get(mPosition),//����Ҫ��ʾ��ͼƬ
					new BannerImageFragment.onCallBack() {//���ͼƬ��Ĵ���Ļص�����
				
				@Override
				public void imageViewClick() {
					// TODO Auto-generated method stub
					mCallBack.imageViewClick(mPosition);
				}
			});
			bif.changImage(mPosition,bitmapList.get(mPosition));
			return bif;//����Ҫ��ʾ��Ƭ��
		}

		/**
		 * ����viewpage�ܹ���100000ҳ
		 */
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 100000;
		}
	}
	
	/**
	 * ͼƬ�ĵ���Ļص��ӿ�
	 * @author wtw
	 * @version 
	 * @create_date 2014-10-24 ����5:45:21
	 */
	public interface onCallBack{
		void imageViewClick(int position);
	}
}
