package csejeonju2019.go.kr.insta;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class SecondTwoFragment  extends Fragment {
    // Store instance variables


    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.secondtwo_fragment, container, false);
        ImageView second_two=(ImageView)view.findViewById(R.id.Secondtwo);
        second_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://namu.wiki/w/%EB%A9%9C%EB%A1%9C%EA%B0%80%20%EC%B2%B4%EC%A7%88"));
                startActivity(intent);
            }
        });

        return view;
    }
}