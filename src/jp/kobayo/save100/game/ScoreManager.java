package jp.kobayo.save100.game;

/**
 * Created by kobayo on 2014/12/28.
 */
public class ScoreManager {


	/**
	 * 残り時間などからスコアを算出します。
	 *
	 * @param restTime
	 * @param mustSum
	 * @return
	 */
	public static long getScore(int restTime,int mustSum) {
		return (restTime * mustSum) / 10;
	}

	public static void save() {



	}
}
