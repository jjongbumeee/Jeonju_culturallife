package com.example.testapi_10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText)findViewById(R.id.editText);
        textView = (TextView)findViewById(R.id.textView);
    }

    // 버튼을 클릭했을 때 쓰레드를 생성하여 해당 함수를 실행하여 텍스트뷰에 데이터 출력

    public void buttonClicked(View v){
        switch( v.getId() ){
            case R.id.button:
                // 쓰레드를 생성하여 돌리는 구간
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            data= getData(); // 하단의 getData 메소드를 통해 데이터를 파싱
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                textView.setText(data);
                            }
                        });
                    }
                }).start();
                break;
        }
    }

    String getData() throws UnsupportedEncodingException {
        StringBuffer buffer = new StringBuffer();
        String str =  editText.getText().toString();
        String location = URLEncoder.encode(str, "UTF-8");

        String queryUrl="http://openapi.jeonju.go.kr/rest/experience/getExperienceList?"
                +"authApiKey=ro%2FXNFjTiuaWfUUOn939KiFkMvs0z915H%2BkR0Te9JF0NPfG4EjF9sAxR2%2B4%2FcqOzu9XlvQaZYyG0F4PniGNdsw%3D%3D"
                +"&dataValue="+location;

        try {
            URL url= new URL(queryUrl); // 문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); // url 위치로 인풋스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();

            // inputstream 으로부터 xml 입력받기
            xpp.setInput( new InputStreamReader(is, "UTF-8") );

            String tag;
            xpp.next();
            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작 단계 \n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName(); // 태그 이름 얻어오기

                        if(tag.equals("list")) ;
                        else if(tag.equals("addr")){
                            buffer.append("주소 : ");
                            xpp.next();

                            // addr 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append(xpp.getText());
                            buffer.append("\n"); // 줄바꿈 문자 추가
                        }
                        else if(tag.equals("dataContent")){
                                buffer.append("정보 : ");
                                xpp.next();
                                buffer.append(xpp.getText());
                                buffer.append("\n");
                            }
                            else if(tag.equals("dataTitle")){
                                buffer.append("이름 :");
                                xpp.next();
                                buffer.append(xpp.getText());
                                buffer.append("\n");
                            }
                            else if(tag.equals("experName")){
                                buffer.append("experName :");
                                xpp.next();
                                buffer.append(xpp.getText());
                                buffer.append("\n");
                            }
                            else if(tag.equals("homepage")){
                                buffer.append("홈페이지 :");
                                xpp.next();
                            buffer.append(xpp.getText());//
                            buffer.append("\n");
                        }
                        else if(tag.equals("reqTime")){
                            buffer.append("요청 시간 :");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("tel")){
                            buffer.append("전화번호 :");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("zipcode")) {
                            buffer.append("우편번호 :");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); // 태그 이름 얻어오기
                        if(tag.equals("list")) buffer.append("\n"); // 첫번째 검색결과종료 후 줄바꿈
                        break;
                }
                eventType= xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        buffer.append("파싱 종료 단계 \n");

        return buffer.toString(); // 파싱 다 종료 후 StringBuffer 문자열 객체 반환
    }
}
