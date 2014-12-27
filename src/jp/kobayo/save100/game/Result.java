package jp.kobayo.save100.game;

/**
 * Created by kobayo on 2014/12/27.
 */
public enum Result {

	// 上段、下段どちらかのみを選択
	singleSelected,
	// 両方選択し、不正解。
	fail,
	// 両方選択し、正解。
	save;
}
