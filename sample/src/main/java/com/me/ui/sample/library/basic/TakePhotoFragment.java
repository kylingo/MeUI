package com.me.ui.sample.library.basic;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.me.ui.constant.PermissionConstants;
import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseFragment;
import com.me.ui.sample.library.util.UriCompat;
import com.me.ui.util.PermissionUtils;
import com.me.ui.util.ToastUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author kylingo
 * @since 2018/12/17 13:13
 */
public class TakePhotoFragment extends BaseFragment {

    private static final int REQUEST_CODE_TAKE_PHOTO = 0x110;
    private String mCurrentPhotoPath;
    private ImageView mIvPhoto;
    private String mPermissons = PermissionConstants.STORAGE;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_take_photo;
    }

    @Override
    protected void initView(View view) {
        view.findViewById(R.id.btn_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });

        mIvPhoto = view.findViewById(R.id.iv_take_photo);
    }

    private void checkPermission() {
        boolean result = PermissionUtils.isGranted(mPermissons);
        if (result) {
            takePhotoNoCompress();
        } else {
            PermissionUtils.permission(mPermissons).callback(new PermissionUtils.SimpleCallback() {
                @Override
                public void onGranted() {
                    takePhotoNoCompress();
                }

                @Override
                public void onDenied() {
                    ToastUtils.showShort("No storage permission");
                }
            }).request();
        }
    }

    public void takePhotoNoCompress() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

            String filename = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.CHINA)
                    .format(new Date()) + ".png";
            File file = new File(Environment.getExternalStorageDirectory(), filename);
            mCurrentPhotoPath = file.getAbsolutePath();

            Uri fileUri = UriCompat.getUriForFile(getActivity(), file);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_TAKE_PHOTO) {
            mIvPhoto.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));
        }
    }
}
