package dk.events.a6.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
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
    private ImageButton backButton;
    private TextView rangeBar_text_view, seek_bar_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_filter);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.colorstatusbar));

        rangeSeekBar = findViewById(R.id.rangeseekbar);
        rangeBar_text_view=findViewById(R.id.range_text);
        seekBar=findViewById(R.id.seek_bar);
        seek_bar_text=findViewById(R.id.textView4);
        backButton = findViewById(R.id.ic_back_arrow);

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


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }
}