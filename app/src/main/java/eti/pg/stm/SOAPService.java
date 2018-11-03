package eti.pg.stm;

import android.os.AsyncTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class SOAPService extends AsyncTask<Object[], Void, String> {
    private static final String NAMESPACE = "http://example/";
    private static final String METHOD_NAME = "sayHelloWorldFrom";
    private static final String SOAP_ACTION = "http://example/sayHelloWorldFrom";
    private static String URL = "http://10.0.2.2:8081/services/HelloWorld?wsdl";

    public String firstHeighPoint;
    public String firstWidthPoint;
    public String secondHeighPoint;
    public String secondWidthPoint;
    public AsyncResponse delegate = null;

    public SOAPService( String firstWidthPoint, String firstHeighPoint, String secondWidthPoint, String secondHeighPoint) {
        this.firstHeighPoint = firstHeighPoint;
        this.firstWidthPoint = firstWidthPoint;
        this.secondHeighPoint = secondHeighPoint;
        this.secondWidthPoint = secondWidthPoint;
    }

    @Override
    protected String doInBackground(Object[]... objects) {

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        String resultImageInString = new String();

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(request);

        request.addProperty("arg1", firstWidthPoint);
        request.addProperty("arg0", firstHeighPoint);
        request.addProperty("arg3", secondHeighPoint);
        request.addProperty("arg2", secondWidthPoint);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL, 15000);
        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);

            SoapPrimitive resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();

            resultImageInString = resultsRequestSOAP.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultImageInString;
    }


    @Override
    protected void onPostExecute(String resultImageInString) {
        delegate.processFinish(resultImageInString);
    }
}
