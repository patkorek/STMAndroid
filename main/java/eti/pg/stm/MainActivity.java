package eti.pg.stm;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    EditText widthFirstPointId;
    EditText heightFirstPointId;
    EditText widthSecondPointId;
    EditText heightSecondPointId;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        widthFirstPointId = findViewById(R.id.widthFirstPoint);
        heightFirstPointId = findViewById(R.id.heighFirstPoint);
        widthSecondPointId = findViewById(R.id.widthSecondPoint);
        heightSecondPointId = findViewById(R.id.heighSecondPoint);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            getCords();
        });

        Switch mySwitch = findViewById(R.id.switch1);


        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mySwitch.setText("Drugi punkt");
                } else {
                    mySwitch.setText("Pierwszy punkt");

                }
            }
        });

        final double minWidth = 19.1190;
        final double minHeight = 50.2896;
        final double maxWidth = 19.0985;
        final double maxHeight = 50.2761;
        final double stepWidth = 0.0000205;
        final double stepHeight = 0.0000135;


        ImageView img = findViewById(R.id.imageView);
        img.setOnTouchListener((v1, event) -> {

            String X = String.valueOf(event.getY());  //abs cordinates on screen
            String Y = String.valueOf(event.getX());  //abs cordinates on screen

            Double newX = -((Double.parseDouble(X) * stepWidth) - minWidth);
            Double newY = -((Double.parseDouble(Y) * stepHeight) - minHeight);

            if (!mySwitch.isChecked()) {
                    widthFirstPointId.setText(String.valueOf(newY));
                    heightFirstPointId.setText(String.valueOf(newX));

            }
            else{
//                if(Double.parseDouble(String.valueOf(widthSecondPointId.getText()))<
//                        Double.parseDouble(String.valueOf(widthFirstPointId.getText()))) {
                    widthSecondPointId.setText(String.valueOf(newY));
                    heightSecondPointId.setText(String.valueOf(newX));
                //}
            }
            return false;
        });
    }

    private void getCords() {


        String widthFirstPoint = widthFirstPointId.getText().toString();
        String heightFirstPoint = heightFirstPointId.getText().toString();
        String widthSecondPoint = widthSecondPointId.getText().toString();
        String heightSecondPoint = heightSecondPointId.getText().toString();


        SOAPService asyncTask = new SOAPService(widthFirstPoint, heightFirstPoint, widthSecondPoint, heightSecondPoint);
        asyncTask.delegate = this;
        asyncTask.execute();
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        // MotionEvent object holds X-Y values
//        if(event.getAction() == MotionEvent.ACTION_DOWN) {
//            String text = "You click at x = " + event.getRawX() + " and y = " + event.getRawY();
//            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
//        }
//
//        return super.onTouchEvent(event);
//    }


    @Override
    public void processFinish(String output) {
        //String encodedImage = Base64.encodeToString(output.getBytes(), Base64.DEFAULT);
        ImageView image = findViewById(R.id.imageView);
        //Bitmap bmp = BitmapFactory.decodeByteArray(output.getBytes(), 0, output.length());
        //image.setImageBitmap(Bitmap.createScaledBitmap(bmp, 50, 50, false));
        try {
            byte[] decodedString = Base64.decode(output, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            image.setImageBitmap(bitmap);
            image.invalidate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
class Params{



}