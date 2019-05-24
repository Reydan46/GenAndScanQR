package ParseQR.wifi;


import java.util.StringTokenizer;


public class WifiCardParser
{

    public static WifiCard parse(String wifiCardContent)
    {
        WifiCard wifiCard = new WifiCard();

        if (!wifiCardContent.startsWith(WifiCostant.KEY_WIFI))
        {
            return null;
        }

        wifiCardContent = wifiCardContent.substring(WifiCostant.KEY_WIFI.length());

        StringTokenizer st = new StringTokenizer(wifiCardContent, ";");
        while (st.hasMoreTokens())
        {
            executeParsing(wifiCard, st.nextToken());
        }


        return wifiCard;
    }

    private static void executeParsing(WifiCard wifiCard, String tokenparsing)
    {

        if (tokenparsing.startsWith(WifiCostant.KEY_SID))
        {
            wifiCard.setSid(tokenparsing.substring(WifiCostant.KEY_SID.length()));
        }

        if (tokenparsing.startsWith(WifiCostant.KEY_PASS))
        {
            wifiCard.setPassword(tokenparsing.substring(WifiCostant.KEY_PASS.length()));
        }

        if (tokenparsing.startsWith(WifiCostant.KEY_TYPE))
        {
            wifiCard.setType(tokenparsing.substring(WifiCostant.KEY_TYPE.length()));
        }

        if (tokenparsing.startsWith(WifiCostant.KEY_STATUS_HIDE))
        {
            wifiCard.setHide(tokenparsing.substring(WifiCostant.KEY_STATUS_HIDE.length()));
        }

    }


}
