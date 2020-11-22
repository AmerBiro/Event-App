package dk.events.a6.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import dk.events.a6.R;

public class MyFilter extends AppCompatActivity {


    private RangeSeekBar rangeSeekBar;
    private SeekBar seek_bar;
    private TextView textView, textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_filter);
        rangeSeekBar=findViewById(R.id.rangeseekbar);
        textView=findViewById(R.id.range_text);
        seek_bar=findViewById(R.id.seek_bar);
        textView2=findViewById(R.id.textView4);

        seek_bar.setMax(100);
        seek_bar.setMin(1);

        seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                textView2.setText("" +i + " Km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




        rangeSeekBar.setRangeValues(18,90);




        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Number minValue, Number maxValue) {
                Number min_value = bar.getSelectedMinValue();
                Number max_value = bar.getSelectedMaxValue();
                int min = (int) min_value;
                int max = (int) max_value;

                textView.setText( min +"-"+max);

            }
        });



    }
}