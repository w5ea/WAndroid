package cn.way.wandroid.graphics;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.widget.TextView;

public class ATextVeiw extends TextView{

	public ATextVeiw(Context context) {
		super(context);
		init();
	}
	private void init(){
		setSingleLine();
		setEllipsize(TruncateAt.MARQUEE);
	}
	@Override
	public boolean isFocused() {
		return true;
	}
}
