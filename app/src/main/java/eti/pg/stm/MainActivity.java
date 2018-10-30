package eti.pg.stm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends AppCompatActivity {

    private static final String NAMESPACE = "http://example/";
    private static String URL = "http://localhost:8081/services/HelloWorld?wsdl";
    private static final String METHOD_NAME = "sayHelloWorldFrom";
    private static final String SOAP_ACTION =  "http://example/sayHelloWorldFrom";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        EditText wysokosc = findViewById(R.id.editText);
        EditText szerokosc = findViewById(R.id.editText2);

        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(v ->{
            GetCords(wysokosc, szerokosc);
        });
        TextView textWYs = findViewById(R.id.textView);


        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


        PropertyInfo propInfo=new PropertyInfo();
        propInfo.name="arg0";
        propInfo.type= PropertyInfo.STRING_CLASS;

        request.addProperty(String.valueOf(propInfo), "John Smith");

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);

            SoapPrimitive resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();


            textWYs.setText(resultsRequestSOAP.toString());


        } catch (Exception e) {

        }
    }
    private void GetCords(EditText wysokosc, EditText szerokosc) {
        String wys = wysokosc.getText().toString();
        String szer = szerokosc.getText().toString();
        TextView textWYs = findViewById(R.id.textView);
        TextView textSzer = findViewById(R.id.textView2);
        textWYs.setText(wys);
        textSzer.setText(szer);
    }
}
