package rey.genandscanqr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.butt_scan_qr).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, ScanQR.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.butt_gen_qr).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, GenQR.class);
                startActivity(intent);
            }
        });

    }

    // Запрет возврата назад
    @Override
    public void onBackPressed()
    {
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        switch (id)
        {
            case R.id.menu_about:
                AlertDialog.Builder dialog;
                String title = getString(R.string.menu_main_about_title);
                String message = getString(R.string.menu_main_about_message);
                String button1String = getString(R.string.menu_main_about_ok);

                // Создаём диалог
                dialog = new AlertDialog.Builder(MainActivity.this, R.style.AppTheme);
                // Заголовок
                dialog.setTitle(title);
                // Сообщение
                dialog.setMessage(message);
                // Кнопка "Да"
                dialog.setPositiveButton(button1String, null);
                // Диалог может быть отменён
                dialog.setCancelable(true);
                // Показываем диалог
                dialog.show();
                return true;
            case R.id.menu_exit:
                //эмулируем нажатие на HOME, сворачивая приложение
                Intent i = new Intent(Intent.ACTION_MAIN);
                i.addCategory(Intent.CATEGORY_HOME);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
