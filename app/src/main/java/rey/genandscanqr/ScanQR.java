package rey.genandscanqr;

import ParseQR.mebkm.MeBkm;
import ParseQR.mebkm.MeBkmParser;
import ParseQR.mecard.MeCard;
import ParseQR.mecard.MeCardParser;
import ParseQR.vcard.VCard;
import ParseQR.vcard.VCardParser;
import ParseQR.vevent.VEvent;
import ParseQR.vevent.VEventParser;
import ParseQR.wifi.WifiCard;
import ParseQR.wifi.WifiCardParser;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.Toast;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;

import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Pattern;

public class ScanQR extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback, QRCodeReaderView.OnQRCodeReadListener
{
    private static final int MY_PERMISSION_REQUEST_CAMERA = 0;

    private ViewGroup scan_qr_layout;

    private WebView wv_result;
    private QRCodeReaderView qrCodeReaderView;
    private PointsOverlayView pointsOverlayView;

    // Запрет возврата назад
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        scan_qr_layout = findViewById(R.id.scan_qr_layout);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED)
        {
            initQRCodeReaderView();
        }
        else
        {
            requestCameraPermission();
        }
    }

    private void requestCameraPermission()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))
        {
            ActivityCompat.requestPermissions(ScanQR.this, new String[]{
                    Manifest.permission.CAMERA
            }, MY_PERMISSION_REQUEST_CAMERA);

        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA
            }, MY_PERMISSION_REQUEST_CAMERA);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if (qrCodeReaderView != null)
        {
            qrCodeReaderView.startCamera();
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        if (qrCodeReaderView != null)
        {
            qrCodeReaderView.stopCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        if (requestCode != MY_PERMISSION_REQUEST_CAMERA)
        {
            return;
        }

        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "Разрешение для доступа к камере получено.", Toast.LENGTH_SHORT)
                    .show();
            initQRCodeReaderView();
        }
        else
        {
            Toast.makeText(this, "Разрешение для доступа к камере не получено.", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private String parseTextQR(String input)
    {
        if (Pattern.matches("^smsto:[\\d\\D]+:[\\d\\D]+$", input.toLowerCase()))
        {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(input));
            startActivity(intent);
            String[] sms = input.split(":");
            if (sms.length >= 3)
            {
                return String.format("" +
                                             "<b>СМС</b>\n" +
                                             "Номер: %s\n" +
                                             "Содержание: %s", sms[1], sms[2]);
            }
            else
            {
                return "Ошибка распознования СМС!\n" + input;
            }
        }
        else if (Pattern.matches("^wifi:[\\d\\D]+", input.toLowerCase()))
        {
            WifiCard wifiCard = WifiCardParser.parse(input);
            if (wifiCard != null)
            {
                return "<b>WiFi</b>\n" + wifiCard.getFormatedText();
            }
            else
            {
                return "Ошибка распознования WiFi!\n" + input;
            }
        }
        else if (Pattern.matches("^geo:[\\d\\D]+", input.toLowerCase()))
        {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(input));
            startActivity(intent);
            String[] geolocation = input.split(":")[1].split(",");
            if (geolocation.length >= 2)
            {
                String[] geolocation2 = geolocation[1].split("\\?q=");
                return String.format("" +
                                             "<b>Местоположение</b>\n" +
                                             "Широта: %s\n" +
                                             "Долгота: %s", geolocation[0], geolocation2[0] + (geolocation2.length == 2 ? "\nСтрока поиска: " + geolocation2[1] : ""));
            }
            else
            {
                return "Ошибка распознования местоположения!\n" + input;
            }
        }
        else if (Pattern.matches("^mecard:[\\d\\D]+", input.toLowerCase()))
        {
            MeCard meCard = MeCardParser.parse(input);
            if (meCard != null)
            {
                return "<b>Личная карта</b>\n" + meCard.getFormatedText();
            }
            else
            {
                return "Ошибка распознования личной карты!\n" + input;
            }
        }
        else if (Pattern.matches("^mebkm:[\\d\\D]+", input.toLowerCase()))
        {
            MeBkm mebkm = MeBkmParser.parse(input);
            if (mebkm != null)
            {
                return "<b>Закладка</b>\n" + mebkm.getFormatedText();
            }
            else
            {
                return "Ошибка распознования закладки!\n" + input;
            }
        }
        else if (Pattern.matches("(?:(?:(?:[fh][t][tp][p]?[s]?)[s]*:\\/\\/|www\\.)[^\\.]+\\.[^ \\n]+)", input.toLowerCase()))
        {
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(Uri.parse(input));
//            startActivity(intent);
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("URL", input);
//            intent.putExtra("Title", currentRSSItem.getTitle());
//            intent.putExtra("Date", currentRSSItem.getDate());
//            intent.putExtra("Image", currentRSSItem.getImage());
            this.startActivity(intent);
            return "<b>Ссылка</b>\n<a href='" + input + "'>" + input + "</a>";
        }
        else if (Pattern.matches("^begin:vcalendar[\\d\\D]+end:vcalendar\\s*$", input.toLowerCase()))
        {
            StringReader sin = new StringReader(input.replaceAll(";", ""));
            CalendarBuilder builder = new CalendarBuilder();
            try
            {
                Calendar calendar = builder.build(sin);
                Log.d("Tag", calendar.toString());
                return "<b>Календарное событие</b>\n" + input;
            }
            catch (IOException | ParserException e)
            {
                e.printStackTrace();
                return "Ошибка распознования календарного события!\n" + input;
            }
        }
        else if (Pattern.matches("^begin:vevent[\\d\\D]+end:vevent\\s*$", input.toLowerCase()))
        {
            VEvent vEvent = VEventParser.parse(input);
            if (vEvent != null)
            {
                Log.d("Tag", "" + vEvent.buildString());
                return "<b>Событие</b>\n" + input;
            }
            else
            {
                return "Ошибка распознования события!\n" + input;
            }
        }
        else if (Pattern.matches("^begin:vcard[\\d\\D]+end:vcard\\s*$", input.toLowerCase()))
        {
            VCard vCard = VCardParser.parse(input);
            if (vCard != null)
            {
                Log.d("Tag", "" + vCard.buildString());
            }
            return "<b>vCard</b>\n" + input;
        }
        else if (Pattern.matches("^tel:[\\d\\D]+", input.toLowerCase()))
        {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(input));
            startActivity(intent);
            return "<b>Телефон</b>\n" + input;
        }
        else if (Pattern.matches("^matmsg:to[\\d\\D]+$", input.toLowerCase()))
        {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse(input));
            startActivity(intent);
            return "Email (matmsg)\n" + input;
        }
        else if ((Pattern.matches("^mailto:[\\d\\D]+$", input.toLowerCase())))
        {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse(input));
            startActivity(intent);
            return "Email (mailto)\n" + input;
        }
        else
        {
            return "<b>Текст</b>" + "\n" + input;
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onQRCodeRead(String text, PointF[] points, boolean Visibility)
    {
        if (Visibility)
        {
            if (pointsOverlayView.getVisibility() != View.VISIBLE)
            {
                Log.d("TextQR", '"' + text + '"');
                Log.d("TextQR", parseTextQR(text));
                wv_result.loadDataWithBaseURL(null, "<style>" +
                        "html {background-color:transparent}" +
                        "</style>"
                        + "<pre style='" +
                        "white-space: pre-wrap;" +
                        "white-space: -moz-pre-wrap;" +
                        "white-space: -pre-wrap;" +
                        "white-space: -o-pre-wrap;" +
                        "word-wrap: break-word;" +
                        "color: #ffffff;'>" + parseTextQR(text) + "</pre>", "text/html; charset=utf-8", "UTF-8", null);
                wv_result.getSettings().setJavaScriptEnabled(true);
                wv_result.getSettings().setDomStorageEnabled(true);
                wv_result.setBackgroundColor(0x83000000);
                wv_result.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
                pointsOverlayView.setPoints(points);
                pointsOverlayView.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            pointsOverlayView.setVisibility(View.INVISIBLE);
        }
    }

    private void initQRCodeReaderView()
    {
        View content = getLayoutInflater().inflate(R.layout.content_scan_qr, scan_qr_layout, true);

        qrCodeReaderView = content.findViewById(R.id.qrdecoderview);
        wv_result = content.findViewById(R.id.wv_result);
        SwitchCompat sc_flashlight = content.findViewById(R.id.sc_flashlight);
        pointsOverlayView = content.findViewById(R.id.points_overlay_view);

        qrCodeReaderView.setAutofocusInterval(1000L);
        qrCodeReaderView.setOnQRCodeReadListener(this);
        qrCodeReaderView.setBackCamera();
        sc_flashlight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
            {
                qrCodeReaderView.setTorchEnabled(isChecked);
            }
        });
        qrCodeReaderView.startCamera();
    }
}
