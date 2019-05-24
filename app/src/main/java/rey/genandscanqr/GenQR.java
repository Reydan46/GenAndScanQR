package rey.genandscanqr;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.util.Calendar;
import java.util.EnumMap;
import java.util.Map;

public class GenQR extends AppCompatActivity
{
    private final int REQUEST_PERMISSION = 0xf0;

    private GenQR self;
    private Snackbar snackbar;
    private Bitmap qrImage;

    private EditText text_qr;
    private TextView text_hint_save;
    private ImageView result_qr;
    private ProgressBar saver;

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
        setContentView(R.layout.activity_gen_qr);
        self = this;

        text_qr = findViewById(R.id.text_qr);
        text_hint_save = findViewById(R.id.text_hint_save);
        Button butt_gen = findViewById(R.id.butt_gen);
        Button butt_clear = findViewById(R.id.butt_clear);
        result_qr = findViewById(R.id.result_qr);
        saver = findViewById(R.id.saver);

        butt_gen.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                self.generateImage();
            }
        });

        butt_clear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                self.reset();
            }
        });

        result_qr.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                self.confirm(new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        saveImage();
                    }
                });
            }
        });

        text_qr.setText("Пример");

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                saveImage();
            } else
            {
                alert("Приложение не имеет доступа для добавления изображений в галерею.");
            }
        }
    }

    private void saveImage()
    {
        if (qrImage == null)
        {
            alert("Нет QR-кода.");
            return;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION
            );
            return;
        }


        String file_name = "qrcode-" + Calendar.getInstance().getTimeInMillis();
        boolean success = true;
        try
        {
            String result = MediaStore.Images.Media.insertImage(
                    getContentResolver(),
                    qrImage,
                    file_name,
                    "Изображение QR-кода"
            );
            if (result == null)
            {
                success = false;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            success = false;
        }

        if (!success)
        {
            alert("Не удалось сохранить QR-код");
        } else
        {
            self.snackbar("QR-код сохранен в галерее.");
        }
    }

    private void alert(String message)
    {
        AlertDialog dlg = new AlertDialog.Builder(self)
                .setTitle("Генератор QR-кодов")
                .setMessage(message)
                .setPositiveButton("ОК", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                })
                .create();
        dlg.show();
    }

    private void confirm(final AlertDialog.OnClickListener yesListener)
    {
        AlertDialog dlg = new AlertDialog.Builder(self)
                .setTitle("Подтверждение")
                .setMessage("Сохранить QR-код?")
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Да", yesListener)
                .create();
        dlg.show();
    }

    private void snackbar(String msg)
    {
        if (self.snackbar != null)
        {
            self.snackbar.dismiss();
        }

        self.snackbar = Snackbar.make(
                findViewById(R.id.mainBody),
                msg, Snackbar.LENGTH_SHORT
        );

        self.snackbar.show();
    }

    private void hideKeyboard(Activity activity)
    {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null)
        {
            inputManager.hideSoftInputFromWindow(findViewById(android.R.id.content).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        }
    }

    private void endEditing()
    {
        text_qr.clearFocus();
        hideKeyboard(GenQR.this);
    }


    private void generateImage()
    {
        final String text = text_qr.getText().toString();
        if (text.trim().isEmpty())
        {
            alert("Сначала введите данные");
            return;
        }

        endEditing();
        showLoadingVisible(true);
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                int size = result_qr.getMeasuredWidth();
                if (size > 1)
                {
                    size = 260;
                }

                Map<EncodeHintType, Object> hintMap = new EnumMap<>(EncodeHintType.class);
                hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                hintMap.put(EncodeHintType.MARGIN, 1);
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                try
                {
                    BitMatrix byteMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, size,
                            size, hintMap
                    );
                    int height = byteMatrix.getHeight();
                    int width = byteMatrix.getWidth();
                    self.qrImage = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                    for (int x = 0; x < width; x++)
                    {
                        for (int y = 0; y < height; y++)
                        {
                            qrImage.setPixel(x, y, byteMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                        }
                    }

                    self.runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            self.showImage(self.qrImage);
                            self.showLoadingVisible(false);
                            self.snackbar("QR-код сгенерирован!");
                        }
                    });
                } catch (WriterException e)
                {
                    e.printStackTrace();
                    alert(e.getMessage());
                }
            }
        }).start();
    }

    private void showLoadingVisible(boolean visible)
    {
        if (visible)
        {
            showImage(null);
        }

        saver.setVisibility(
                (visible) ? View.VISIBLE : View.GONE
        );
    }

    private void reset()
    {
        text_qr.setText("");
        showImage(null);
        endEditing();
    }

    private void showImage(Bitmap bitmap)
    {
        if (bitmap == null)
        {
            result_qr.setImageResource(android.R.color.transparent);
            qrImage = null;
            text_hint_save.setVisibility(View.GONE);
        } else
        {
            result_qr.setImageBitmap(bitmap);
            text_hint_save.setVisibility(View.VISIBLE);
        }
    }
}
