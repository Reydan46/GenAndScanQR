package ParseQR.mebkm;


import java.util.StringTokenizer;


public class MeBkmParser
{

    public static MeBkm parse(String wifiCardContent)
    {
        MeBkm wifiCard = new MeBkm();

        if (!wifiCardContent.startsWith(MeBkmCostant.KEY_MEBKM))
        {
            return null;
        }

        wifiCardContent = wifiCardContent.substring(MeBkmCostant.KEY_MEBKM.length());

        StringTokenizer st = new StringTokenizer(wifiCardContent, ";");
        while (st.hasMoreTokens())
        {
            executeParsing(wifiCard, st.nextToken());
        }


        return wifiCard;
    }

    private static void executeParsing(MeBkm wifiCard, String tokenparsing)
    {

        if (tokenparsing.startsWith(MeBkmCostant.KEY_TITLE))
        {
            wifiCard.setTitle(tokenparsing.substring(MeBkmCostant.KEY_TITLE.length()));
        }

        if (tokenparsing.startsWith(MeBkmCostant.KEY_URL))
        {
            wifiCard.setUrl(tokenparsing.substring(MeBkmCostant.KEY_URL.length()));
        }

    }


}
