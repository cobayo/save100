package jp.kobayo.save100.result;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import jp.kobayo.save100.R;
import jp.kobayo.save100.common.MenuManager;
import jp.kobayo.save100.top.TopActivity;

/**
 * 結果画面
 *
 * Created by kobayo on 2014/12/30.
 */
public class ResultActivity extends Activity implements View.OnClickListener{

	// AdMob用
	private AdView adView;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_m);

		// 前画面からの値を受け取る
		Intent intent = getIntent();
		String currentScore = intent.getStringExtra("currentScore");
		String clearCnt = intent.getStringExtra("clearCnt");

		// 正解数
		TextView cnt = (TextView)findViewById(R.id.clear_cnt);
		cnt.setText(clearCnt);

		// 取ったスコア
		TextView score = (TextView)findViewById(R.id.score);
		score.setText(currentScore);

		// Topへ戻る画像
		ImageView exit = (ImageView)findViewById(R.id.exit);
		exit.setOnClickListener(this);

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
	 * クリック時処理
	 * @param view : View
	 */
	public void onClick(View view) {

		switch(view.getId()) {

			case R.id.exit:
				backTop();
				break;
			default:
				break;

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuManager.createMenu(menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

			case MenuManager.MENU_TOP:
				backTop();
				break;
			case MenuManager.MENU_END:
			default:
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	// Topへ戻ります。
	private void backTop() {

		// Topへ戻る
		Intent intent = new Intent(this, TopActivity.class);
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