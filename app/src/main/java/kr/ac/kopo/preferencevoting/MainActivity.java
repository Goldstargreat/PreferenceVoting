package kr.ac.kopo.preferencevoting;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setTitle("아이돌 선호도 투표");

        // 투표 수를 저장할 길이 9개의 배열 객체를 생성하고 0으로 초기화 하는 코드를 작성
        final int voteCount[] = new int[9];
        for(int i = 0; i < voteCount.length; i++)
        {
            voteCount[i] = 0;
        }
        // 이미지뷰 객체의 참조값을 저장할 길이 9개의 배열 객체를 생성
        ImageView[] imgvArr = new ImageView[9];
        int[] imgvIdArr = {R.id.imgv1, R.id.imgv2, R.id.imgv3, R.id.imgv4, R.id.imgv5, R.id.imgv6, R.id.imgv7, R.id.imgv8, R.id.imgv9};

        final String[] idolNameArr = {"블랙핑크", "아이브", "방탄소년단", "에스파", "아일릿", "뉴진스", "르세라핌", "스트레이트키즈", "세븐틴"};

        for(int i = 0; i < imgvArr.length; i++)
        {
            final int index;
            index = i;
            imgvArr[index] = findViewById(imgvIdArr[index]);
            imgvArr[index].setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    voteCount[index]++;
                    Toast.makeText(getApplicationContext(),idolNameArr[index] + "총" + voteCount[index] + "표", Toast.LENGTH_SHORT).show();
                }
            });
        }
        Button btnFinish = findViewById(R.id.btn_finish);
        btnFinish.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // ResultActivity를 시작하는 Intent 객체를 생성하고 값을 넣는다
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                intent.putExtra("voteCount", voteCount);
                intent.putExtra("idolNameArr", idolNameArr);
                startActivity(intent);
            }
        });
    }
}