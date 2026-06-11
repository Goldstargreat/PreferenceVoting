package kr.ac.kopo.preferencevoting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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

        // 투표수 중에서 최댓값(1위 득표수) 구하기
        int maxValue = 0;
        for (int i = 0; i < voteCount.length; i++)
        {
            if (voteCount[i] > maxValue)
            {
                maxValue = voteCount[i];
            }
        }

        // 최댓값과 같은 득표수를 가진 아이돌 인덱스를 모두 수집 (공동 1위 대비)
        List<Integer> topList = new ArrayList<Integer>();
        for (int i = 0; i < voteCount.length; i++)
        {
            if (voteCount[i] == maxValue)
            {
                topList.add(i);
            }
        }

        // 공동 1위 여부에 따라 상단 표시 분기
        if (topList.size() > 1)
        {
            // 공동 1위: 이름들을 이어붙여서 표시, 이미지는 기본 아이콘으로
            StringBuilder sb = new StringBuilder("🏆 공동 1위: ");
            for (int i = 0; i < topList.size(); i++)
            {
                sb.append(idolNameArr[topList.get(i)]);
                if (i < topList.size() - 1)
                {
                    sb.append(", ");
                }
            }
            textTitle.setText(sb.toString());
            imgv.setImageResource(android.R.drawable.star_big_on); // 기본 별 아이콘
        }
        else
        {
            // 단독 1위: 기존처럼 이름 + 사진 표시
            int maxIndex = topList.get(0);
            textTitle.setText("🏆 1위: " + idolNameArr[maxIndex]);
            imgv.setImageResource(imgResArr[maxIndex]);
        }

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