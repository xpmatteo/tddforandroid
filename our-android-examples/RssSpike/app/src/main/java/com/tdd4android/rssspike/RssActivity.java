package com.tdd4android.rssspike;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class RssActivity extends ActionBarActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_rss);

    String xml = readResource("/feed.xml");
    String html = getSubString(xml, "<content:encoded><![CDATA[", "]]></content:encoded>");

    WebView view = (WebView) findViewById(R.id.web_view);
    view.loadData(html, "text/html", "utf-8");
  }

  private String getSubString(String string, String startTag, String endTag) {
    int start = string.indexOf(startTag)  + startTag.length();
    int end = string.indexOf(endTag) - endTag.length();
    return string.substring(start, end);
  }

  private String readResource(String resourceName) {
    InputStream inputStream = getClass().getResourceAsStream(resourceName);
    return readAll(inputStream);
  }

  private static String readAll(InputStream stream) {
    try (
        InputStreamReader reader = new InputStreamReader(stream, "utf8");
    ) {
      char[] buffer = new char[100*1000];
      int nread = reader.read(buffer);
      return String.copyValueOf(buffer, 0, nread);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }



  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_rss, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
