package eti.pg.stm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
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
    ImageView img;

    float X1;
    float Y1;
    float X2;
    float Y2;

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
        final double stepWidth = 0.0000205;
        final double stepHeight = 0.0000135;

        img = findViewById(R.id.imageView);



        img.setOnTouchListener((v1, event) -> {

            String X = String.valueOf(event.getY());  //abs cordinates on screen
            String Y = String.valueOf(event.getX());  //abs cordinates on screen

            Double newX = -((Double.parseDouble(X) * stepWidth) - minWidth);
            Double newY = -((Double.parseDouble(Y) * stepHeight) - minHeight);
            //Toast.makeText(this, "X= "+X+" Y= "+Y, Toast.LENGTH_SHORT).show();
            if (!mySwitch.isChecked()) {

                if((event.getX()>X2 || event.getY() >Y2) && (X2 != 0.0 || Y2 != 0.0)) {
                    Toast.makeText(this, "Drugi punkt musi znajdować się PRZED punktem drugim", Toast.LENGTH_SHORT).show();
                }
                else{
                    widthFirstPointId.setText(String.valueOf(newY));
                    heightFirstPointId.setText(String.valueOf(newX));
                    X1 = event.getX();
                    Y1 = event.getY();
                    drawCircle(X1, Y1, X2, Y2);
                }

            }
            else{

                if(event.getX()<X1 || event.getY() <Y1)
                {
                    Toast.makeText(this, "Drugi punkt musi znajdować się ZA punktem pierwszym", Toast.LENGTH_SHORT).show();
                }
                else{
                    widthSecondPointId.setText(String.valueOf(newY));
                    heightSecondPointId.setText(String.valueOf(newX));
                    X2 = event.getX();
                    Y2 = event.getY();
                    drawCircle(X1, Y1, X2, Y2);
                }

            }
            return false;
        });
    }

private void drawCircle(float X1, float Y1, float X2, float Y2){

    BitmapFactory.Options myOptions = new BitmapFactory.Options();
    myOptions.inDither = true;
    myOptions.inScaled = false;
    myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// important
    myOptions.inPurgeable = true;

    Paint paint = new Paint();
    paint.setAntiAlias(true);
    paint.setColor(Color.WHITE);
    paint.setStyle(Paint.Style.STROKE);
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.map,myOptions);

    Bitmap workingBitmap = Bitmap.createBitmap(bitmap);
    Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);
    Canvas canvas = new Canvas(mutableBitmap);
    // Rectangle

    paint.setStrokeWidth(7);
    float leftx = X1;
    float topy = Y1;
    float rightx = X2;
    float bottomy = Y2;

    if(leftx == 0.0 || topy == 0){
        canvas.drawCircle(rightx, bottomy, 5, paint);
    }
    else if(rightx == 0.0 || bottomy == 0){
        canvas.drawCircle(leftx, topy, 5, paint);
    }
    else{
        canvas.drawRect(leftx, topy, rightx, bottomy, paint);
    }
    img.setAdjustViewBounds(true);
    img.setImageBitmap(mutableBitmap);
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