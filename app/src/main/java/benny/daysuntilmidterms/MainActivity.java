package benny.daysuntilmidterms;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView message, daysLeft, motivation;
    Random random = new Random();
    Date calendar = Calendar.getInstance().getTime();
    String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar);
    String[] motivationText = {"You got this!", "You will make it!", "Keep going!", "Just a little more!", "Let's get it!", "You can do it!"};
    long days;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        message = findViewById(R.id.days);
        motivation = findViewById(R.id.motivation);
        daysLeft = findViewById(R.id.daysLeft);

        message.setText(date);

        calculateDaysUntilMidterms();

        if (days == 1) {
            motivation.setText("Final day!");
        } else if (days < 1){
            motivation.setText("");
        } else {
            int rand = random.nextInt(motivationText.length);
            motivation.setText(motivationText[rand]);
        }

        countDown();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void calculateDaysUntilMidterms() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date futureDate = dateFormat.parse("2025-04-28");
            Date currentDate = new Date();
            if (!currentDate.after(futureDate)) {
                long diff = futureDate.getTime() - currentDate.getTime();
                days = (long) Math.ceil((double) diff / (24 * 60 * 60 * 1000));
            } else {
                days = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void countDown() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date futureDate = dateFormat.parse("2025-04-28");
                    Date currentDate = new Date();
                    if (!currentDate.after(futureDate)) {
                        long diff = futureDate.getTime() - currentDate.getTime();
                        days = (long) Math.ceil((double) diff / (24 * 60 * 60 * 1000));
                        if (days <= 1) {
                            daysLeft.setText(days + " day until midterms");
                        } else {
                            daysLeft.setText(days + " days until midterms");
                        }
                    } else {
                        daysLeft.setText("Midterms are done!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }
}
