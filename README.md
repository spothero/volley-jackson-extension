# volley-jackson-extension

A simple Android library that aims to make accessing and parsing JSON responses from the web as fast and simple as possible.  This library is packaged as a simple extension that connects Google's Volley networking library with FasterXML's Jackson JSON parsing library.  It provides a simple interface to get actual Java POJO's from network calls very efficiently.  Instead of parsing all network responses into a byte array, then parsing that array into a POJO, this library provides a mechanism for parsing the InputStream directly.

## Usage
This library was designed to be as simple to use as possible.  Here are the steps you'll need to follow:

* Include the maven dependencies for this library as well as Jackson's libraries in your build.gradle file.  (Note: if you aren't yet using the awesome gradle build system, you can skip this step and just include the .jar files in your project directly.)

		dependencies {
			compile 'com.spothero.volley:volley-jackson-extension:1.0.1'
			compile 'com.fasterxml.jackson.core:jackson-core:2.2.3'
			compile 'com.fasterxml.jackson.core:jackson-databind:2.2.3'
			compile 'com.fasterxml.jackson.core:jackson-annotations:2.2.3'
		}

* Create the POJO that you would like your request to be parsed as.

		public class DateReturnType {
			public String time;
			public long milliseconds_since_epoch;
			public String date;
		}

* Create a new RequestQueue, where all requests will be processed.

		RequestQueue mRequestQueue = JacksonNetwork.newRequestQueue(context);
		
* Add a new JacksonRequest to your RequestQueue.  This will kick off the process of accessing the network resource and parsing the response into your POJO.  Once it's done, your response will be returned in the onResponse method of your JacksonRequestListener.

		mRequestQueue.add(new JacksonRequest<DateReturnType>(Request.Method.GET,
				"http://date.jsontest.com",
				new JacksonRequestListener<DateReturnType>() {
					@Override
					public void onResponse(DateReturnType response, int statusCode, VolleyError error) {
						if (response != null) {
							Log.d(TAG, "Time: " + response.time + "\nDate: " + response.date + "\nMS since epoch: " + response.milliseconds_since_epoch);
						} else {
							Log.e(TAG, "An error occurred while parsing the data! Stack trace follows:");
							error.printStackTrace();
						}
					}

					@Override
					public JavaType getReturnType() {
						return SimpleType.construct(DateReturnType.class);
					}
				})
		); 

* That's it!  For more information on parsing complex data types, consult Jackson's documentation: [JacksonHome](http://wiki.fasterxml.com/JacksonHome)

## Apps Using this Library
This library is used in our own [SpotHero](https://play.google.com/store/apps/details?id=com.spothero.spothero "SpotHero") app. If you would like to see your app listed here as well, let us know you're using it!

## License
android-volley-extension is released under the Apache 2.0 license.
