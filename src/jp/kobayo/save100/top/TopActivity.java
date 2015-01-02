package jp.kobayo.save100.top;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import jp.kobayo.save100.R;
import jp.kobayo.save100.game.GameActivity;
import jp.kobayo.save100.game.ScoreManager;

/**
 * トップ画面
 *
 * Created by kobayo on 2014/12/30.
 */
public class TopActivity extends Activity implements View.OnClickListener {

	// AdMob用
	private AdView adView;

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
		start.setOnClickListener(this);

		// adView を作成する
		adView = new AdView(this);
		adView.setAdUnitId("ca-app-pub-3092831641029940/5413389516");
		adView.setAdSize(AdSize.BANNER);

		// 中央上部表示
		LinearLayout rootLayout = (LinearLayout) findViewById(R.id.ads);
		rootLayout.addView(adView);

		// 一般的なリクエストを行う
		AdRequest adRequest = new AdRequest.Builder().build();

		// 広告リクエストを行って adView を読み込む
		adView.loadAd(adRequest);

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
		finish();
	}

	@Override
	public void onPause() {
		adView.pause();
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		adView.resume();
	}

	@Override
	public void onDestroy() {
		adView.destroy();
		super.onDestroy();
	}

}
