package kr.ac.kopo.preferencevoting;

import android.content.Intent;
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
{  // 이 클래스는 안드로이드의 화면 역할을 하겠다
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
        // 앱 상단 액션바 제목을 "아이돌 선호도 투표"로 설정.

        // 투표 수를 저장할 길이 9개의 배열 객체를 생성하고 0으로 초기화 하는 코드를 작성
        // 투표 시작 전에는 다 0표이므로
        final int voteCount[] = new int[9];
        for(int i = 0; i < voteCount.length; i++)
        {
            voteCount[i] = 0;
        }
        // 이미지뷰 객체의 참조값을 저장할 길이 9개의 배열 객체를 생성
        ImageView[] imgvArr = new ImageView[9];

        // XML에 정의된 이미지뷰들의 ID 목록
        int[] imgvIdArr = {R.id.imgv1, R.id.imgv2, R.id.imgv3, R.id.imgv4, R.id.imgv5, R.id.imgv6, R.id.imgv7, R.id.imgv8, R.id.imgv9};

        // 아이돌 이름 9개를 문자열 배열로 저장
        final String[] idolNameArr = {"블랙핑크", "아이브", "방탄소년단", "에스파", "아일릿", "뉴진스", "르세라핌", "스트레이트키즈", "세븐틴"};

        // 9번 반복하여 이미지뷰 9개를 하나씩 처리
        for(int i = 0; i < imgvArr.length; i++)
        {
            final int index;
            index = i;
            imgvArr[index] = findViewById(imgvIdArr[index]);
            //  ID로 XML에서 실제 ImageView 객체를 찾아서 배열에 저장

            // 이미지뷰를 클릭했을 때 실행될 동작을 등록
            // 클릭 리스너를 익명 클래스로 만드는 방식
            imgvArr[index].setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    voteCount[index]++; // 클릭한 아이돌의 투표수를 1 증가.
                    Toast.makeText(getApplicationContext(),idolNameArr[index] + "총" + voteCount[index] + "표", Toast.LENGTH_SHORT).show();
                }
            });
        }
        Button btnFinish = findViewById(R.id.btn_finish);
        // XML에 있는 투표 종료 버튼을 java에서 가져온다

        // 종료 버튼 클릭시 실행될 동작 목록
        btnFinish.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // ResultActivity를 시작하는 Intent 객체를 생성하고 값을 넣는다
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);

                // 다음 화면에 데이터를 화물처럼 보내는 것(putExtra)
                intent.putExtra("voteCount", voteCount);
                intent.putExtra("idolNameArr", idolNameArr);

                // 실제로 화면 전환 이행 (이 행에서 ResultActivity가 열린다)
                startActivity(intent);
            }
        });
    }
}