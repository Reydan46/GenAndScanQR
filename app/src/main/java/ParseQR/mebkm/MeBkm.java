package ParseQR.mebkm;

import android.text.TextUtils;

public class MeBkm
{

    private String title = "";
    private String url = "";

    public String getTitle()
    {
        return title;
    }

    void setTitle(String title)
    {
        this.title = title;
    }

    public String getUrl()
    {
        return url;
    }

    void setUrl(String url)
    {
        this.url = url;
    }


    public String getFormatedText()
    {
        return String.format("" +
                                     "Название: %s\n" +
                                     "Ссылка: %s",
                             title, url
        );
    }

    public String buildString()
    {

        StringBuilder wifiCardString = new StringBuilder();
        wifiCardString.append(MeBkmCostant.KEY_MEBKM);

        if (!TextUtils.isEmpty(title))
        {
            wifiCardString.append(MeBkmCostant.KEY_TITLE)
                    .append(title)
                    .append(";");
        }

        if (!TextUtils.isEmpty(url))
        {
            wifiCardString.append(MeBkmCostant.KEY_URL)
                    .append(url)
                    .append(";");
        }

        return wifiCardString.toString();
    }
}
