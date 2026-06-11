package kr.ac.kopo.preferencevoting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity
{
    TextView textTitle;
    ImageView imgv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // MainActivity에서 보낸 Intent를 받아오는 코드
        Intent intent = getIntent();

        // MainActivity에서 putExtra()로 실어 보낸 데이터를 키 이름으로 꺼내는 것
        int[] voteCount = intent.getIntArrayExtra("voteCount");       // int 배열을 꺼낸다
        String[] idolNameArr = intent.getStringArrayExtra("idolNameArr"); // string 배열을 꺼낸다

        // 이미지 리소스 배열
        int[] imgResArr = {
                R.drawable.idol01, R.drawable.idol02, R.drawable.idol03,
                R.drawable.idol04, R.drawable.idol05, R.drawable.idol06,
                R.drawable.idol07, R.drawable.idol08, R.drawable.idol09
        };

        // TextView, RatingBar 배열 생성
        TextView[] textArr = new TextView[idolNameArr.length];
        RatingBar[] ratingArr = new RatingBar[idolNameArr.length];

        // 상단 1위 표시 뷰 연결
        textTitle = findViewById(R.id.text_title);
        imgv = findViewById(R.id.imgv);

        // 투표수 중에서 최댓값(1위) 구하기
        int maxIndex = 0;
        int maxValue = voteCount[0];

        for (int i = 1; i < voteCount.length; i++)
        {
            if (voteCount[i] > maxValue)
            {
                maxValue = voteCount[i];
                maxIndex = i;
            }
        }

        // 상단에 1위 아이돌 이름과 사진 표시
        textTitle.setText(idolNameArr[maxIndex]);
        imgv.setImageResource(imgResArr[maxIndex]);

        // XML에 있는 TextView, RatingBar들의 ID 목록
        int[] textIdArr = {
                R.id.text1, R.id.text2, R.id.text3,
                R.id.text4, R.id.text5, R.id.text6,
                R.id.text7, R.id.text8, R.id.text9
        };
        int[] ratingIdArr = {
                R.id.rating1, R.id.rating2, R.id.rating3,
                R.id.rating4, R.id.rating5, R.id.rating6,
                R.id.rating7, R.id.rating8, R.id.rating9
        };

        // XML에서 TextView, RatingBar 찾아서 배열에 저장
        for (int i = 0; i < textArr.length; i++)
        {
            textArr[i] = findViewById(textIdArr[i]);
            ratingArr[i] = findViewById(ratingIdArr[i]);
        }

        // 투표 결과를 화면에 표시 (이름 + 별점)
        for (int i = 0; i < textArr.length; i++)
        {
            textArr[i].setText(idolNameArr[i]);

            // RatingBar 최대 별 5개이므로 투표수가 5 초과하면 5로 제한
            ratingArr[i].setNumStars(5);
            float rating = Math.min(voteCount[i], 5.0f);
            ratingArr[i].setRating(rating);
        }

        // "돌아가기" 버튼 클릭 시 이전 화면으로 돌아가기
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }
}