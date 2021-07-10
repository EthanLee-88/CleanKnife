package com.example.cleanknife;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cleanknife.CleanKnife.CheckNet;
import com.example.cleanknife.CleanKnife.CleanBinder;
import com.example.cleanknife.CleanKnife.FindView;
import com.example.cleanknife.CleanKnife.OnClick;

public class MainActivity extends AppCompatActivity {

    @FindView(R.id.clean_id)
    private Button mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CleanBinder.bind(this);
        mTextView.setText("碧云天");

    }

    @OnClick(R.id.clean_id)
    @CheckNet
    private void showToast(){
        Toast.makeText(this, "碧云天", Toast.LENGTH_SHORT).show();
    }
}