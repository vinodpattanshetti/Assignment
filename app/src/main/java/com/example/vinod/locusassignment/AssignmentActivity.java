package com.example.vinod.locusassignment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vinod.locusassignment.databinding.ActivityAssignmentLayoutBinding;
import com.example.vinod.locusassignment.utils.CameraPreview;
import com.example.vinod.locusassignment.utils.JSONResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class AssignmentActivity extends AppCompatActivity {
  private static final int CAMERA_REQUEST = 1888;
  private ActivityAssignmentLayoutBinding mBinder;
  private JSONResponse mJSONResponse;
  private String jsonResponse;
  private JSONObject jsonRootObject;
  private JSONArray jsonArray;
  private JSONObject jsonObject;
  private Camera mCamera;
  private CameraPreview mPreview;

  private Bitmap photo;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinder = DataBindingUtil.setContentView(this, R.layout.activity_assignment_layout);
    mBinder.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });
    mJSONResponse = new JSONResponse();
    jsonResponse = mJSONResponse.getJsonResponse();
    readJSON();
    mBinder.tvSubmit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Toast.makeText(AssignmentActivity.this, "Results are updated", Toast.LENGTH_LONG).show();
        readJSON();
      }
    });
  }

  private void openCameraIntent() {
    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
    startActivityForResult(cameraIntent, CAMERA_REQUEST);
  }

  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == CAMERA_REQUEST) {
      this.photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
    }
  }

  @Override
  protected void onStart() {
    super.onStart();
    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
      requestPermissions(new String[] {Manifest.permission.CAMERA,
                      Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
              3);
    } else {
      initCamera();
    }
  }

  private void initCamera() {
    try {
      mCamera = Camera.open();
      mCamera.setDisplayOrientation(90);
      mPreview = new CameraPreview(getApplicationContext(), mCamera);
      mCamera.startPreview();
    } catch (Exception e) {
      Log.e("Exception", "Camera Exception");
    }
  }

  private void initCamera(final LinearLayout mLinearLayout, final ImageView imageView) {
    mLinearLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        try {
          mCamera = Camera.open();
          mCamera.setDisplayOrientation(90);
          mPreview = new CameraPreview(getApplicationContext(), mCamera);
          mLinearLayout.addView(mPreview);
          mCamera.startPreview();
        } catch (Exception e) {
          Log.e("Exception", "Camera Exception");
        }
      }
    });
    imageView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
          mLinearLayout.setBackgroundColor(ContextCompat.getColor(AssignmentActivity.this, R.color.colorBlue));
      }
    });
  }

  private void readJSON() {
    try {
      mBinder.llItem.removeAllViews();
      jsonRootObject = new JSONObject(jsonResponse);
      jsonArray = jsonRootObject.optJSONArray("assignment");
      for (int i = 0; null != jsonArray && i < jsonArray.length(); i++) {
        try {
          View child = getLayoutInflater().inflate(R.layout.item_layout, null);
          jsonObject = jsonArray.getJSONObject(i);
          //Camera layouts
          LinearLayout cameraView = child.findViewById(R.id.ll_camera_preview);
          ImageView imageView = child.findViewById(R.id.iv_remove_photo);

          initCamera(cameraView, imageView);

          //Radio Group layouts
          TextView radioGroupTitle = child.findViewById(R.id.tv_radio_button_title);
          RadioGroup radioGroup = child.findViewById(R.id.radioGroup);

          //Comment Layout layouts
          ConstraintLayout constraintLayout = child.findViewById(R.id.ll_comment_layout);
          Switch mSwitch = child.findViewById(R.id.sw_comment);

          final EditText editText = child.findViewById(R.id.et_type_comment);
          if("PHOTO".equalsIgnoreCase(jsonObject.getString("type"))) {
            cameraView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
          } else {
            cameraView.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
          }
          if("SINGLE_CHOICE".equalsIgnoreCase(jsonObject.getString("type"))) {
            radioGroupTitle.setVisibility(View.VISIBLE);
            radioGroup.setVisibility(View.VISIBLE);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

              @Override
              public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.rb_option_a) {
                  Toast.makeText(getApplicationContext(), "choice: Option A",
                          Toast.LENGTH_SHORT).show();
                  try {
                    jsonObject.put("id", "choice1");
                  } catch (JSONException e) {
                    e.printStackTrace();
                  }
                } else if(checkedId == R.id.rb_option_b) {
                  Toast.makeText(getApplicationContext(), "choice: Option B",
                          Toast.LENGTH_SHORT).show();
                  try {
                    jsonObject.put("id", "choice2");
                  } catch (JSONException e) {
                    e.printStackTrace();
                  }
                } else {
                  Toast.makeText(getApplicationContext(), "choice: Option C",
                          Toast.LENGTH_SHORT).show();
                  try {
                    jsonObject.put("id", "choice3");
                  } catch (JSONException e) {
                    e.printStackTrace();
                  }
                }
              }
            });
          } else {
            radioGroupTitle.setVisibility(View.GONE);
            radioGroup.setVisibility(View.GONE);
          }
          if ("COMMENT".equalsIgnoreCase(jsonObject.getString("type"))) {
            constraintLayout.setVisibility(View.VISIBLE);
            if (!jsonObject.getString("title").isEmpty()) {
              mSwitch.setChecked(true);
              editText.setVisibility(View.VISIBLE);
              editText.setText(jsonObject.getString("title"));
            } else {
              mSwitch.setChecked(false);
              editText.setVisibility(View.GONE);
            }
            mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                  editText.setVisibility(View.VISIBLE);
                } else {
                  editText.setVisibility(View.GONE);
                }
              }
            });
            jsonObject.put("id", editText.getText().toString());
          } else {
            constraintLayout.setVisibility(View.GONE);
          }
          mBinder.llItem.addView(child);
        } catch (Exception e) {
          Log.e("Exception", "JSON Exception");
        }
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  public void onResume() {
    super.onResume();
    if (mCamera == null) {
      mCamera = Camera.open();
      mCamera.setDisplayOrientation(90);
      if (null != mPreview) {
        mPreview.refreshCamera(mCamera);
      }
    } else {
      initCamera();
    }
  }

  private void releaseCamera() {
    // stop and release camera
    if (mCamera != null) {
      mCamera.stopPreview();
      mCamera.setPreviewCallback(null);
      mCamera.release();
      mCamera = null;
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    // when on Pause, release camera in order to be used from other applications
    releaseCamera();
  }
}
