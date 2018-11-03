package eti.pg.stm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity implements AsyncResponse{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            getCords();
        });

        }


    private void getCords() {
        EditText widthFirstPointId = findViewById(R.id.widthFirstPoint);
        EditText heightFirstPointId = findViewById(R.id.heighFirstPoint);
        EditText widthSecondPointId = findViewById(R.id.widthSecondPoint);
        EditText heightSecondPointId = findViewById(R.id.heighSecondPoint);

        String widthFirstPoint = widthFirstPointId.getText().toString();
        String heightFirstPoint = heightFirstPointId.getText().toString();
        String widthSecondPoint = widthSecondPointId.getText().toString();
        String heightSecondPoint = heightSecondPointId.getText().toString();


        SOAPService asyncTask = new SOAPService(widthFirstPoint, heightFirstPoint, widthSecondPoint, heightSecondPoint);
        asyncTask.delegate = this;
        asyncTask.execute();
    }

    @Override
    public void processFinish(String output){
        //String encodedImage = Base64.encodeToString(output.getBytes(), Base64.DEFAULT);
        ImageView image = findViewById(R.id.imageView);
        //Bitmap bmp = BitmapFactory.decodeByteArray(output.getBytes(), 0, output.length());
        //image.setImageBitmap(Bitmap.createScaledBitmap(bmp, 50, 50, false));
        try {
            byte[] decodedString = Base64.decode(output, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);
            image.setImageBitmap(bitmap);
            image.invalidate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
