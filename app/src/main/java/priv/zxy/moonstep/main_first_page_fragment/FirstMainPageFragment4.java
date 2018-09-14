package priv.zxy.moonstep.main_first_page_fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.Utils.MyDialog;
import priv.zxy.moonstep.login_activity.LoginActivity;

public class FirstMainPageFragment4 extends Fragment implements OnMenuItemClickListener, OnMenuItemLongClickListener{

    private AppCompatImageButton magicWend;
    private AppCompatImageButton addFriends;
    private AppCompatImageButton sendMessages;
    private View view;
    private ContextMenuDialogFragment mMenuDialogFragment;
    private ArrayList<MenuObject> menuObjects = new ArrayList<>();
    private Context mContext;
    private Activity mActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getContext();
        mActivity = this.getActivity();
        setHasOptionsMenu(true);//在Fragment要想让onCreateOptionsMenu生效必须先调用setHasOptionsMenu的方法
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fg_main_first_subpage4, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void initView() {
        initToolbar();
        initMenuFragment();
        magicWend = view.findViewById(R.id.magicWend);
        addFriends = view.findViewById(R.id.addFriedns);
        sendMessages = view.findViewById(R.id.sendMesssages);

        magicWend.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    magicWend.setPadding(10, 10, 0, 0);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    magicWend.setPadding(-10, -10, 0, 0);
                }
                return false;
            }
        });

        addFriends.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    addFriends.setPadding(10, 10, 0, 0);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    addFriends.setPadding(-10, -10, 0, 0);
                }
                return false;
            }
        });

        sendMessages.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    sendMessages.setPadding(10, 10, 0, 0);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    sendMessages.setPadding(-10, -10, 0, 0);
                }
                return false;
            }
        });
    }

    private void initToolbar() {
        Toolbar mToolbar = view.findViewById(R.id.toolbar);
        TextView mToolBarTextView = view.findViewById(R.id.titleName);
        ((AppCompatActivity)mActivity).setSupportActionBar(mToolbar);
        if (((AppCompatActivity)mActivity).getSupportActionBar() != null) {
            ((AppCompatActivity)mActivity).getSupportActionBar().setHomeButtonEnabled(true);
            ((AppCompatActivity)mActivity).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity)mActivity).getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
//        mToolbar.setNavigationIcon(R.drawable.back_button);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
        mToolBarTextView.setText("称号");
    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }

    private void popupDialog(){
        final MyDialog myDialog = new MyDialog(mActivity);
        myDialog.setTitle("退出提示!");
        myDialog.setContent("退出登陆后我们会继续保留您的账户数据，记得常回来看看哦！");
        myDialog.setNegativeClickLister("取消", new MyDialog.onNegativeClickListener() {
            @Override
            public void onNegativeClick() {
                myDialog.dismiss();
            }
        });
        myDialog.setPositiveClickLister("确认", new MyDialog.onPostiveClickListener() {
            @Override
            public void onPositiveClick() {
                menuObjects.clear();
                        Intent intent = new Intent(mActivity, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
            }
        });
        myDialog.show();

//        Effectstype effect = Effectstype.Newspager;
//        NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(mContext);
//        dialogBuilder
//                .withTitle("退出提示!")                                  //.withTitle(null)  no title
//                .withTitleColor("#f2f2f2")                                  //def
//                .withDividerColor("#4B0082")                              //def
//                .withMessage("退出登陆后我们会继续保留您的账户数据，记得常回来看看哦！")                     //.withMessage(null)  no Msg
//                .withMessageColor("#f2f2f2")                              //def  | withMessageColor(int resid))
//                .withDialogColor("#742DD2")                               //def  | withDialogColor(int resid)
//                .withIcon(getResources().getDrawable(R.mipmap.warning))
//                .withDuration(700)                                          //def
//                .withEffect(effect)                                         //def Effectstype.Slidetop
//                .withButton1Text("取消")                                      //def gone
//                .withButton2Text("确认")                                  //def gone
//                .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
////                .setCustomView(R.layout.custom_view,v.getContext())         //.setCustomView(View or ResId,context)
//                .setButton1Click(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(v.getContext(), "i'm btn1", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .setButton2Click(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        menuObjects.clear();
//                        Intent intent = new Intent(mActivity, LoginActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                    }
//                }).show();
    }

    private List<MenuObject> getMenuObjects(){
        menuObjects.clear();
        MenuObject close = new MenuObject();
        close.setResource(R.mipmap.close);

        MenuObject send = new MenuObject("修改背景图片");
        send.setResource(R.mipmap.change_bg);

        MenuObject like = new MenuObject("修改个人信息");
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.mipmap.personal_info);
        like.setBitmap(b);

        MenuObject addFr = new MenuObject("黑名单");
        BitmapDrawable bd = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.mipmap.backlist));
        addFr.setDrawable(bd);

        MenuObject addFav = new MenuObject("意见反馈");
        addFav.setResource(R.mipmap.feedback);

        MenuObject block = new MenuObject("退出登录");
        block.setResource(R.mipmap.log_out);

        menuObjects.add(close);
        menuObjects.add(send);
        menuObjects.add(like);
        menuObjects.add(addFr);
        menuObjects.add(addFav);
        menuObjects.add(block);
        return menuObjects;
    }

    //初始化选项菜单
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //一旦不设置menu.clear()属性，就会导致activity的的menu和fragment的menu一起显示出来，展示的效果会变成三个点加menu的菜单
        menu.clear();
        mActivity.getMenuInflater().inflate(R.menu.context_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                if (getFragmentManager().findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(getFragmentManager(), ContextMenuDialogFragment.TAG);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        switch(position){
            case 0:

                break;
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 5:
                //这里设置一个弹窗，确认是否登出当前账号
                popupDialog();
                break;
        }
    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {
    }
}