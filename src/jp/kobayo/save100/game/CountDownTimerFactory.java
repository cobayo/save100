package jp.kobayo.save100.game;

import android.app.Activity;
import android.os.CountDownTimer;
import android.widget.TextView;
import jp.kobayo.save100.R;

/**
 * Created by kobayo on 2014/12/27.
 */
public class CountDownTimerFactory {

	public static CountDownTimer create(Activity activity) {

		final TextView counter = (TextView)activity.findViewById(R.id.counter);

		return new CountDownTimer(5000,1000){

			// カウントダウン処理
			public void onTick(long millisUntilFinished){
				counter.setText(String.valueOf(millisUntilFinished/1000));
			}

			// カウントが0になった時の処理
			public void onFinish(){
				counter.setText("0");
			}
		};


	}
}
