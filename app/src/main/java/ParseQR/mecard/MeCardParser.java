package ParseQR.mecard;


import java.util.StringTokenizer;


public class MeCardParser {

    public static MeCard parse(String meCardContent) {
        MeCard meCard = new MeCard();

        if (!meCardContent.startsWith(MeCardCostant.KEY_MECARD)) {
            return null;
        }

        meCardContent = meCardContent.substring(MeCardCostant.KEY_MECARD.length());

        StringTokenizer st = new StringTokenizer(meCardContent, ";");
        while (st.hasMoreTokens()) {
            executeParsing(meCard, st.nextToken());
        }


        return meCard;
    }

    private static void executeParsing(MeCard meCard, String tokenparsing) {

        if (tokenparsing.startsWith(MeCardCostant.KEY_NAME)) {
            String name = tokenparsing.substring(MeCardCostant.KEY_NAME.length());
            if (name.contains(",")) {
                String[] part = name.split(",");
                meCard.setSurname(part[0]);
                meCard.setName(part[1]);
            } else {
                meCard.setName(name);
            }

        }

        if (tokenparsing.startsWith(MeCardCostant.KEY_ADDRESS)) {
            meCard.setAddress(tokenparsing.substring(MeCardCostant.KEY_ADDRESS.length()));
        }
        if (tokenparsing.startsWith(MeCardCostant.KEY_TELEPHONE)) {
            meCard.addTelephone(tokenparsing.substring(MeCardCostant.KEY_TELEPHONE.length()));
        }
        if (tokenparsing.startsWith(MeCardCostant.KEY_URL)) {
            meCard.setUrl(tokenparsing.substring(MeCardCostant.KEY_URL.length()));
        }
        if (tokenparsing.startsWith(MeCardCostant.KEY_NOTE)) {
            meCard.setNote(tokenparsing.substring(MeCardCostant.KEY_NOTE.length()));
        }
        if (tokenparsing.startsWith(MeCardCostant.KEY_EMAIL)) {
            meCard.setEmail(tokenparsing.substring(MeCardCostant.KEY_EMAIL.length()));
        }
        if (tokenparsing.startsWith(MeCardCostant.KEY_DAY_1)) {
            meCard.setDate(tokenparsing.substring(MeCardCostant.KEY_DAY_1.length()));
        }
        if (tokenparsing.startsWith(MeCardCostant.KEY_DAY_2)) {
            meCard.setDate(tokenparsing.substring(MeCardCostant.KEY_DAY_2.length()));
        }
        if (tokenparsing.startsWith(MeCardCostant.KEY_ORG)) {
            meCard.setOrg(tokenparsing.substring(MeCardCostant.KEY_ORG.length()));
        }
        if (tokenparsing.startsWith(MeCardCostant.KEY_NICKNAME)) {
            meCard.setNickname(tokenparsing.substring(MeCardCostant.KEY_NICKNAME.length()));
        }
    }


}
