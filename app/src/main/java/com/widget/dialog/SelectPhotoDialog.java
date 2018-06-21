package com.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.hxky.common.R;


/**
 * @author SelectPhotoDialog
 * @ClassName:SelectPhotoDialog
 * @date 2016-07-12
 * @Description:自定义dialog,实现选择图片对话框
 */
public class SelectPhotoDialog extends Dialog {

    private Context context;
    private Button btnCamera, btnPicture, btnCancel;

    private OnOptionClickListener onOptionClickListener;

    public SelectPhotoDialog(Context context) {
        super(context, R.style.DialogNoTitle);
        this.context = context;
        initDialog();
        initListener();
    }

    public SelectPhotoDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        initDialog();
        initListener();
    }

    protected SelectPhotoDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
        initDialog();
        initListener();
    }


    /**
     * 初始化 弹框
     */
    private void initDialog() {
        //初始化 弹框
        View view = LayoutInflater.from(context).inflate(R.layout.main_dialopg_pohot_select, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //获得当前窗体
        Window window = this.getWindow(); //重新设置
        assert window != null;
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.popupWindow_AnimationPreview);
        this.setContentView(view, params);
        btnCamera = (Button) view.findViewById(R.id.btnCamera);
        btnPicture = (Button) view.findViewById(R.id.btnPicture);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);
    }

    private void initListener() {
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectPhotoDialog.this.dismiss();
                if (onOptionClickListener != null)
                    onOptionClickListener.onCameraClick(v);
            }
        });
        btnPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectPhotoDialog.this.dismiss();
                if (onOptionClickListener != null)
                    onOptionClickListener.onPictureClick(v);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectPhotoDialog.this.dismiss();
            }
        });
    }

    public interface OnOptionClickListener {
        /**
         * 照相机点击
         */
        void onCameraClick(View v);

        /**
         * 本地图片点击
         */
        void onPictureClick(View v);

    }

    public void setOnOptionClickListener(OnOptionClickListener onOptionClickListener) {
        this.onOptionClickListener = onOptionClickListener;
    }
}
