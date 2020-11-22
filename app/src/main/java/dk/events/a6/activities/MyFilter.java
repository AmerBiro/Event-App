package dk.events.a6.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import dk.events.a6.R;

public class MyFilter extends AppCompatActivity {

    private CrystalRangeSeekbar rangeSeekBar;
    private CrystalSeekbar seekBar;

    private TextView rangeBar_text_view, seek_bar_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_filter);

        rangeSeekBar = findViewById(R.id.rangeseekbar);
        rangeBar_text_view=findViewById(R.id.range_text);
        seekBar=findViewById(R.id.seek_bar);
        seek_bar_text=findViewById(R.id.textView4);


        rangeSeekBar.setMinValue(18);
        rangeSeekBar.setMaxValue(90);
        // set listener
        rangeSeekBar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                rangeBar_text_view.setText(String.valueOf(minValue) + "-" + String.valueOf(maxValue));
            }
        });


        seekBar.setMinValue(1);
        seekBar.setMaxValue(40);
        seekBar.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue) {
                seek_bar_text.setText(String.valueOf(minValue) + " Km");
            }
        });



////        rangeSeekBar.setRangeValues(18,90);
////
////        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
////            @Override
////            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Number minValue, Number maxValue) {
////                Number min_value = bar.getSelectedMinValue();
////                Number max_value = bar.getSelectedMaxValue();
////                int min = (int) min_value;
////                int max = (int) max_value;
////
////                textView.setText( min +"-"+max);
////
////            }
////        });
//




    }
}