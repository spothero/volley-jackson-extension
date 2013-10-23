package com.spothero.volleyjacksondemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.spothero.volley.JacksonNetwork;
import com.spothero.volley.JacksonRequest;
import com.spothero.volley.JacksonRequestListener;

public class DemoActivity extends Activity {

	private RequestQueue mRequestQueue;
	private ProgressBar mProgressBar;
	private TextView mTvDateData;
	private TextView mTvHeaderData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

		mProgressBar = (ProgressBar)findViewById(R.id.progress);
		mTvDateData = (TextView)findViewById(R.id.tv_date_data);
		mTvHeaderData = (TextView)findViewById(R.id.tv_header_data);

		mRequestQueue = JacksonNetwork.newRequestQueue(getApplicationContext());
    }

	@Override
	protected void onStart() {
		super.onStart();

		mRequestQueue.add(new JacksonRequest<DateReturnType>(Request.Method.GET,
				"http://date.jsontest.com",
				new JacksonRequestListener<DateReturnType>() {
					@Override
					public void onResponse(DateReturnType response, int statusCode, VolleyError error) {
						if (response != null) {
							mTvDateData.setText("Time: " + response.time + "\nDate: " + response.date + "\nMS since epoch: " + response.milliseconds_since_epoch);
						} else {
							mTvDateData.setTextColor(Color.RED);
							mTvDateData.setText("Error parsing data! Check logcat for details");
							error.printStackTrace();
						}
						mProgressBar.setVisibility(View.GONE);
					}

					@Override
					public JavaType getReturnType() {
						return SimpleType.construct(DateReturnType.class);
					}
				})
		);

		mRequestQueue.add(new JacksonRequest<HeadersReturnType>(Request.Method.GET,
				"http://headers.jsontest.com",
				new JacksonRequestListener<HeadersReturnType>() {
					@Override
					public void onResponse(HeadersReturnType response, int statusCode, VolleyError error) {
						if (response != null) {
							mTvHeaderData.setText("Host: " + response.host + "\nAccept Language: " + response.acceptLanguage + "\nReferrer: " + response.referrer + "\nUser Agent: " + response.userAgent + "\nAccept: " + response.accept);
						} else {
							mTvHeaderData.setTextColor(Color.RED);
							mTvHeaderData.setText("Error parsing data! Check logcat for details");
							error.printStackTrace();
						}
						mProgressBar.setVisibility(View.GONE);
					}

					@Override
					public JavaType getReturnType() {
						return SimpleType.construct(HeadersReturnType.class);
					}
				})
		);
	}
}
