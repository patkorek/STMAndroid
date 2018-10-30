package eti.pg.stm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String NAMESPACE = "http://example/";
    private static String URL = "http://localhost:8081/services/HelloWorld?wsdl";
    private static final String METHOD_NAME = "sayHelloWorldFrom";
    private static final String SOAP_ACTION =  "http://example/sayHelloWorldFrom";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
