package com.example.huson.mynotebook.base;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.huson.mynotebook.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class BaseHeadFragment extends BaseFragment {
	//menu index
    private static final int MENU1_INDEX = 0;
	private static final int MENU2_INDEX = 1;
	private static final int MENU3_INDEX = 2;
	private static final int MENU4_INDEX = 3;
	private static final int MENU5_INDEX = 4;
	private static final int MENU6_INDEX = 5;
	
	private boolean autoCloseMenu = true;

	public View baseView;

    private RelativeLayout rel_contentArea;
    private Button btn_backButton;
    private RelativeLayout rel_headArea;
    private Button btn_headRightButton;
    private TextView btn_headTitle;
    private LinearLayout lin_menu_background;
    private LinearLayout lin_menu_area;

	private LinearLayout lin_menu_line1;

	private LinearLayout lin_menu_line2;

	private View v_menu_line1_line;

	private LinearLayout lin_menu1;

	private ImageView iv_menu1_icon;

	private TextView tv_menu1_lable;

	private LinearLayout lin_menu2;

	private ImageView iv_menu2_icon;

	private TextView tv_menu2_lable;

	private LinearLayout lin_menu3;

	private ImageView iv_menu3_icon;

	private TextView tv_menu3_lable;

	private LinearLayout lin_menu4;

	private ImageView iv_menu4_icon;

	private TextView tv_menu4_lable;

	private LinearLayout lin_menu5;

	private ImageView iv_menu5_icon;

	private TextView tv_menu5_lable;

	private LinearLayout lin_menu6;

	private ImageView iv_menu6_icon;

	private TextView tv_menu6_lable;
	
	private int curr_menu_index = -1;
    

    public Button getHeadRightButton() {
        return btn_headRightButton;
    }


    public BaseHeadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
		super.onCreateView(inflater,container,savedInstanceState);
        baseView = inflater.inflate(R.layout.base_head, container, false);
        assignHeadViews(baseView);
        initMenuView();
        return baseView;
    }

    public View setContentView(View view) {
        rel_contentArea.addView(view);
        return baseView;
    }
    public void hideHeadArea(){
        rel_headArea.setVisibility(View.GONE);
    }

    public void showBackButton(OnClickListener listener) {

        btn_backButton.setOnClickListener(listener);
        btn_backButton.setVisibility(View.VISIBLE);
    }

    public void showTitle(String title) {
        btn_headTitle.setText(title);
        btn_headTitle.setVisibility(View.VISIBLE);
    }

    public void showHeadRightButton(String text, OnClickListener listener) {
        btn_headRightButton.setText(text);
        btn_headRightButton.setOnClickListener(listener);
        btn_headRightButton.setVisibility(View.VISIBLE);
    }
    /**
     * 显示菜单
     */
    public void showMenu(){
    	if(curr_menu_index > -1){
    		animationShowMenuArea();
    	}
    }
    /**
     * 关闭菜单
     */
    public void closeMenu(){
    	animationHideMenuArea();
    }
    /**
     * 打开或关闭菜单栏
     */
    public void openAndCloseMenu(){
    	if(isShowMenu()){
    		closeMenu();
    	}else{
    		showMenu();
    	}
    }
    /**
     * * 添加一个菜单<br/>
     * 注意：在打开菜单之前必须调用此方法添加至少一个菜单条目
     * @param iconResid
     * 			图标资源id
     * @param label
     * 			菜单名
     * @return 返回这个的菜单下标
     */
    public int addMenuItem(int iconResid,String label){
    	curr_menu_index ++;
    	lin_menu_line1.setVisibility(View.VISIBLE);
    	if(curr_menu_index  > 2){
    		//显示第二行菜单
    		lin_menu_line2.setVisibility(View.VISIBLE);
    		v_menu_line1_line.setVisibility(View.VISIBLE);
    	}
    	switch (curr_menu_index) {
    		case MENU1_INDEX :
				setMenuVisibility(lin_menu1, iv_menu1_icon, tv_menu1_lable,
						iconResid, label);
    			break;
    		case MENU2_INDEX :
    			setMenuVisibility(lin_menu2, iv_menu2_icon, tv_menu2_lable,
						iconResid, label);
    			break;
    		case MENU3_INDEX :
    			setMenuVisibility(lin_menu3, iv_menu3_icon, tv_menu3_lable,
						iconResid, label);
    			break;
    		case MENU4_INDEX :
    			setMenuVisibility(lin_menu4, iv_menu4_icon, tv_menu4_lable,
						iconResid, label);
    			break;
    		case MENU5_INDEX :
    			setMenuVisibility(lin_menu5, iv_menu5_icon, tv_menu5_lable,
						iconResid, label);
    			break;
    		case MENU6_INDEX :
    			setMenuVisibility(lin_menu6, iv_menu6_icon, tv_menu6_lable,
						iconResid, label);
    			break;
			default :
				throw new RuntimeException("The menu only 6 at most!"); 
		}
    	
    	return curr_menu_index;
    }


	private void setMenuVisibility(LinearLayout lin_menu,ImageView iconView,TextView lableView,int iconResid, String label) {
		lin_menu.setEnabled(true);
		lin_menu.setVisibility(View.VISIBLE);
		lin_menu.setTag(label);
		iconView.setBackgroundResource(iconResid);
		lableView.setText(label);
	}

    private void assignHeadViews(View view) {
        rel_contentArea = (RelativeLayout) view.findViewById(R.id.rel_base_contentArea);
        btn_backButton = (Button) view.findViewById(R.id.btn_base_head_back);
        btn_headTitle = (TextView) view.findViewById(R.id.tv_base_head_title);
        btn_headRightButton = (Button) view.findViewById(R.id.btn_base_head_right_button);
        rel_headArea = (RelativeLayout) view.findViewById(R.id.rel_base_headArea);
        lin_menu_background = (LinearLayout) view.findViewById(R.id.lin_base_menuArea);
        lin_menu_area = (LinearLayout) view.findViewById(R.id.lin_menu_Area);
        
        btn_headTitle.setVisibility(View.GONE);
        btn_headRightButton.setVisibility(View.GONE);
        
        /*menus*/
        lin_menu_line1 = (LinearLayout) view.findViewById(R.id.lin_menu_line1);
        lin_menu_line2 = (LinearLayout) view.findViewById(R.id.lin_menu_line2);
        v_menu_line1_line = view.findViewById(R.id.v_menu_line1_dividing_line);
        //默认隐藏第二行菜单
        lin_menu_line2.setVisibility(View.GONE);
		v_menu_line1_line.setVisibility(View.GONE);
        
        lin_menu1 = (LinearLayout) view.findViewById(R.id.lin_menu1);
        iv_menu1_icon = (ImageView) view.findViewById(R.id.iv_menu1_icon);
        tv_menu1_lable = (TextView) view.findViewById(R.id.tv_menu1_lable);
        
        lin_menu2 = (LinearLayout) view.findViewById(R.id.lin_menu2);
        iv_menu2_icon = (ImageView) view.findViewById(R.id.iv_menu2_icon);
        tv_menu2_lable = (TextView) view.findViewById(R.id.tv_menu2_lable);
        
        lin_menu3 = (LinearLayout) view.findViewById(R.id.lin_menu3);
        iv_menu3_icon = (ImageView) view.findViewById(R.id.iv_menu3_icon);
        tv_menu3_lable = (TextView) view.findViewById(R.id.tv_menu3_lable);
        
        lin_menu4 = (LinearLayout) view.findViewById(R.id.lin_menu4);
        iv_menu4_icon = (ImageView) view.findViewById(R.id.iv_menu4_icon);
        tv_menu4_lable = (TextView) view.findViewById(R.id.tv_menu4_lable);
        
        lin_menu5 = (LinearLayout) view.findViewById(R.id.lin_menu5);
        iv_menu5_icon = (ImageView) view.findViewById(R.id.iv_menu5_icon);
        tv_menu5_lable = (TextView) view.findViewById(R.id.tv_menu5_lable);
        
        lin_menu6 = (LinearLayout) view.findViewById(R.id.lin_menu6);
        iv_menu6_icon = (ImageView) view.findViewById(R.id.iv_menu6_icon);
        tv_menu6_lable = (TextView) view.findViewById(R.id.tv_menu6_lable);
        
    }
    
    private void initMenuView(){
    	MyMenuItemOnclickListener menuItemOnclickListener = new MyMenuItemOnclickListener();
    	lin_menu1.setOnClickListener(menuItemOnclickListener);
    	lin_menu2.setOnClickListener(menuItemOnclickListener);
    	lin_menu3.setOnClickListener(menuItemOnclickListener);
    	lin_menu4.setOnClickListener(menuItemOnclickListener);
    	lin_menu5.setOnClickListener(menuItemOnclickListener);
    	lin_menu6.setOnClickListener(menuItemOnclickListener);
    	lin_menu_background.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				openAndCloseMenu();
			}
		});
    }
    /**
     * 设置当菜单页面被选中时  是否自动关闭菜单页面
     * @param autoClossMenu
     */
    protected void autoClossMenuOnMenuItemselected(boolean autoClossMenu){
    	this.autoCloseMenu = autoClossMenu;
    }
    
    protected boolean isShowMenu() {
    	return lin_menu_background.getVisibility() == View.VISIBLE ? true : false;
	}
    
    /**
     * 选中菜单回调函数
     * @param index
     * 			被选中的菜单项的下标
     * @param lable
     * 			被选中的菜单的名称
     */
    protected void onMenuSelected(int index,String lable){
    	
    }
    
    class MyMenuItemOnclickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case  R.id.lin_menu1:
					onMenuSelected(MENU1_INDEX,(String)v.getTag());
					break;
				case  R.id.lin_menu2:
					onMenuSelected(MENU2_INDEX,(String)v.getTag());
					break;
				case  R.id.lin_menu3:
					onMenuSelected(MENU3_INDEX,(String)v.getTag());
					break;
				case  R.id.lin_menu4:
					onMenuSelected(MENU4_INDEX,(String)v.getTag());
					break;
				case  R.id.lin_menu5:
					onMenuSelected(MENU5_INDEX,(String)v.getTag());
					break;
				case  R.id.lin_menu6:
					onMenuSelected(MENU6_INDEX,(String)v.getTag());
					break;
				default :
					break;
			}
			
			if(BaseHeadFragment.this.autoCloseMenu){
				closeMenu();
			}
		}
    }
    
    /**
	 * 动画隐藏菜单栏
	 */
	private void animationHideMenuArea() {
		ObjectAnimator animator = ObjectAnimator.ofFloat(lin_menu_area,
				"translationY", 0, -lin_menu_area.getHeight());
		animator.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				lin_menu_background.setVisibility(View.GONE);
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}
		});

		ObjectAnimator.ofFloat(lin_menu_background, "alpha", 1f, 0f).start();
		animator.start();

	}
	/**
	 * 动画打开菜单栏
	 */
	 private void animationShowMenuArea() {
		ObjectAnimator animator = ObjectAnimator.ofFloat(lin_menu_area,
				"translationY", -lin_menu_area.getHeight(), 0);

		animator.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {

				lin_menu_background.setVisibility(View.VISIBLE);

			}

			@Override
			public void onAnimationEnd(Animator animation) {

			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}
		});
		animator.start();
		ObjectAnimator.ofFloat(lin_menu_background, "alpha", 0f, 1f).start();
	}

}
