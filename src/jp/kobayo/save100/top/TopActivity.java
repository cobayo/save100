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
public class TopActivity extends Activity implements View.OnClickListener{

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.top_m);

		// ハイスコアを表示
		TextView highScore = (TextView)findViewById(R.id.high_score);
		highScore.setText(ScoreManager.getHighScore(this));

		// 最新スコアを表示
		TextView latestScore = (TextView)findViewById(R.id.latest_score);
		latestScore.setText(ScoreManager.getLatestScore(this));

		TextView start = (TextView)findViewById(R.id.start);
		start.setOnClickListener(this);

	}

	/**
	 * スタートボタン押下時
	 *
	 * @param view
	 */
	public void onClick(View view) {

		// ゲーム画面へ遷移
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);
	}
}
