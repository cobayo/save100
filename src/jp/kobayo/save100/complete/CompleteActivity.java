package jp.kobayo.save100.complete;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import jp.kobayo.save100.R;
import jp.kobayo.save100.common.MenuManager;
import jp.kobayo.save100.top.TopActivity;

/**
 * 100万点達成時画面.
 *
 * Created by Yosuke Kobayashi on 2014/12/30.
 */
public class CompleteActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.complete_m);
		
		try {

			// 前画面からの値を受け取る
			Intent intent = getIntent();
			String currentScore = intent.getStringExtra("currentScore");
			String clearCnt = intent.getStringExtra("clearCnt");

			// 正解数
			TextView cnt = (TextView) findViewById(R.id.clear_cnt);
			cnt.setText(clearCnt);

			// 取ったスコア
			TextView score = (TextView) findViewById(R.id.score);
			score.setText(currentScore);

			// Topへ戻る画ボタン
			ImageView exit = (ImageView) findViewById(R.id.exit);
			exit.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					switch(v.getId()) {

						case R.id.exit:
							backTop();
							break;
						default:
							break;

					}


				}
			});

		} catch (Exception e) {
			Log.e(getClass().getName(), e.getMessage());
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