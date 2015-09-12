package jp.kobayo.save100.top;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import jp.kobayo.save100.R;
import jp.kobayo.save100.game.GameActivity;
import jp.kobayo.save100.game.ScoreManager;

/**
 * トップ画面
 *
 * Created by kobayo on 2014/12/30.
 */
public class TopActivity extends Activity {


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.top_m);

		ScoreManager sm = ScoreManager.create(this);

		// ハイスコアを表示
		TextView highScore = (TextView)findViewById(R.id.high_score);
		highScore.setText(sm.getHighScore());

		// 最新スコアを表示
		TextView latestScore = (TextView)findViewById(R.id.latest_score);
		latestScore.setText(sm.getLatestScore());

		// 閉じる。
		sm.close();

		// スタートボタン
		TextView start = (TextView)findViewById(R.id.start);
		start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// ゲーム画面へ遷移
				Intent intent = new Intent(TopActivity.this, GameActivity.class);
				startActivity(intent);

			}
		});

	}



	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
