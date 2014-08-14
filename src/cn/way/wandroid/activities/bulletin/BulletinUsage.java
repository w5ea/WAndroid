package cn.way.wandroid.activities.bulletin;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import cn.way.R;
import cn.way.wandroid.json.GsonUsageActivity;
import cn.way.wandroid.views.BulletinBoardFragment;

public class BulletinUsage extends FragmentActivity {
	BulletinBoardFragment bulletin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bulletin);
		bulletin = (BulletinBoardFragment) getSupportFragmentManager().findFragmentById(R.id.bulletin);
	}
	@Override
	protected void onResume() {
		super.onResume();
		ArrayList<String> bulletins = new ArrayList<String>();
		String text = "sss : ";
		for (int i = 0; i < 10; i++) {
			text += text;
			bulletins.add((i+1)+text);
		}
		bulletin.updateBulletins(bulletins);
		bulletin.startAnimation();
		bulletin.getView().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(BulletinUsage.this, GsonUsageActivity.class));
			}
		});
	}
}
