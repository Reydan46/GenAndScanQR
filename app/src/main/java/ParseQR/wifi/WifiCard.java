package ParseQR.wifi;

import android.text.TextUtils;

public class WifiCard
{

    private String sid = "";
    private String password = "";
    private String type = "";
    private String hide = "";

    public String getSid()
    {
        return sid;
    }

    void setSid(String sid)
    {
        this.sid = sid;
    }

    public String getPassword()
    {
        return password;
    }

    void setPassword(String password)
    {
        this.password = password;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getHide()
    {
        return hide;
    }

    void setHide(String status_hide)
    {
        this.hide = status_hide;
    }

    public String getFormatedText()
    {
        return String.format("" +
                                     "Тип сети: %s\n" +
                                     "Скрытая: %s\n" +
                                     "Имя сети: %s\n" +
                                     "Пароль: %s\n",
                             type, hide.equals("true") ? "Да" : "Нет", sid, password
        );
    }

    public String buildString()
    {

        StringBuilder wifiCardString = new StringBuilder();
        wifiCardString.append(WifiCostant.KEY_WIFI);

        if (!TextUtils.isEmpty(sid))
        {
            wifiCardString.append(WifiCostant.KEY_SID)
                    .append(sid)
                    .append(";");
        }

        if (!TextUtils.isEmpty(password))
        {
            wifiCardString.append(WifiCostant.KEY_PASS)
                    .append(password)
                    .append(";");
        }

        if (!TextUtils.isEmpty(type))
        {
            wifiCardString.append(WifiCostant.KEY_TYPE)
                    .append(type)
                    .append(";");
        }

        if (!TextUtils.isEmpty(hide))
        {
            wifiCardString.append(WifiCostant.KEY_STATUS_HIDE)
                    .append(hide)
                    .append(";");
        }

        return wifiCardString.toString();
    }
}
