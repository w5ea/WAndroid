package cn.way.wandroid.json;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.TextView;
import cn.way.wandroid.BaseFragmentActivity;
import cn.way.wandroid.R;

import com.google.gson.Gson;

public class GsonUsageActivity extends BaseFragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gson_usage);
		TextView tv = (TextView) findViewById(R.id.textView1);
		Gson gson = new Gson();
		ArrayList<User> users = new ArrayList<User>();
		for (int i = 0; i < 3; i++) {
			User user = gson.fromJson("{\"name\"=\"wayne\"}", User.class);
			users.add(user);
		}
		String userJsonString = gson.toJson(users);
		tv.setText(userJsonString);
	}
}
