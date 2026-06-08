package kr.ac.kopo.preferencevoting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity
{
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
        Intent intent = getIntent();
        // MainActivity에서 보낸 Intent를 받아오는 코드

        // MainActivity에서 putExtra()로 실어 보낸 데이터를 키 이름으로 꺼내는 것.
        int[] voteCount = intent.getIntArrayExtra("voteCount");  // int 배열을 꺼낸다
        String[] idolNameArr = intent.getStringArrayExtra("idolNameArr"); // string 배열을 꺼낸다

        // TextView 길이만큼, RatingBar 길이만큼 담을 배열 생성
        TextView[] textArr = new TextView[idolNameArr.length];
        RatingBar[] ratingArr = new RatingBar[idolNameArr.length];

        // XML에 있는 TextView, RatingBar들의 ID 목록
        int[] textIdArr = {R.id.text1, R.id.text2, R.id.text3, R.id.text4, R.id.text5, R.id.text6, R.id.text7, R.id.text8, R.id.text9};
        int[] ratingIdArr = {R.id.rating1, R.id.rating2, R.id.rating3, R.id.rating4, R.id.rating5, R.id.rating6, R.id.rating7, R.id.rating8, R.id.rating9};

        // 길이만큼의 TextView, ratingbar를 XML에서 찾아서 배열에 저장
        for(int i = 0; i < textArr.length; i++)
        {
            textArr[i] = findViewById(textIdArr[i]);
            ratingArr[i] = findViewById(ratingIdArr[i]);
        }

        // ResultActivity에서 투표 결과를 화면에 표시하는 코드.
        for(int i = 0; i < textArr.length; i++)
        {
            textArr[i].setText(idolNameArr[i]);
            ratingArr[i].setRating(voteCount[i]);
        }

        // "돌아가기" 버튼 클릭 시 finish() 호출
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