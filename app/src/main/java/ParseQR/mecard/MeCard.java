package ParseQR.mecard;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MeCard
{

    private String name = "";
    private String surname = "";
    private String address = "";
    private String email = "";
    private List<String> telephones = new ArrayList<>();
    private String url = "";
    private String note = "";
    private String date = "";
    private String org = "";
    private String nickname = "";


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public List<String> getTelephones()
    {
        return telephones;
    }

    public void setTelephones(List<String> telephones)
    {
        this.telephones = telephones;
    }

    public void setTelephone(String oldNumber, String newNumber)
    {
        for (int i = 0; i < this.telephones.size(); i++)
        {
            if (this.telephones.get(i).equals(oldNumber))
            {
                this.telephones.set(i, newNumber);
            }
        }
    }

    public void addTelephone(String telephone)
    {

        telephones.add(telephone);
    }

    public void removeTelephone(String telephone)
    {
        for (int i = 0; i < this.telephones.size(); i++)
        {
            if (this.telephones.get(i).equals(telephone))
            {
                this.telephones.remove(i);
                break;
            }
        }
    }

    public void removTelephone(int index)
    {
        this.telephones.remove(index);
    }


    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }


    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public String getDate()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
        Date d_date = new Date();
        try
        {
            d_date = format.parse(date);
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            return sdf.format(d_date.getTime());
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return date;
        }
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getOrg()
    {
        return org;
    }

    public void setOrg(String org)
    {
        this.org = org;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getFormatedText()
    {
        StringBuilder str_telephones = new StringBuilder();
        for (String telephone : telephones)
        {
            str_telephones.append("Телефон: ").append(telephone).append("\n");

        }
        return String.format("" +
                                     "Имя: %s\n" +
                                     "Фамилия: %s\n" +
                                     "Адрес: %s\n" +
                                     "Email: %s\n" +
                                     "%s" +
                                     "Сайт: %s\n" +
                                     "Заметка: %s\n" +
                                     "Дата рождения: %s\n" +
                                     "Организация: %s\n" +
                                     "Ник: %s\n",
                             name, surname, address, email, str_telephones.toString(), url, note, getDate(), org, nickname
        );
    }

    public String buildString()
    {

        StringBuilder meCardString = new StringBuilder();
        meCardString.append(MeCardCostant.KEY_MECARD);

        if (!TextUtils.isEmpty(name))
        {
            if (!TextUtils.isEmpty(surname))
            {
                meCardString.append(MeCardCostant.KEY_NAME)
                        .append(surname)
                        .append(",")
                        .append(name)
                        .append(";");
            }
            else
            {
                meCardString.append(MeCardCostant.KEY_NAME)
                        .append(name)
                        .append(";");
            }
        }

        if (!TextUtils.isEmpty(address))
        {
            meCardString.append(MeCardCostant.KEY_ADDRESS)
                    .append(address)
                    .append(";");
        }

        if (!TextUtils.isEmpty(url))
        {
            meCardString.append(MeCardCostant.KEY_URL)
                    .append(url)
                    .append(";");
        }

        if (!TextUtils.isEmpty(note))
        {
            meCardString.append(MeCardCostant.KEY_NOTE)
                    .append(note)
                    .append(";");
        }

        if (!TextUtils.isEmpty(date))
        {
            meCardString.append(MeCardCostant.KEY_DAY_1)
                    .append(date)
                    .append(";");
        }
        if (!TextUtils.isEmpty(email))
        {
            meCardString.append(MeCardCostant.KEY_EMAIL)
                    .append(email)
                    .append(";");
        }
        if (telephones != null)
        {
            for (String telephone : telephones)
            {

                meCardString.append(MeCardCostant.KEY_TELEPHONE)
                        .append(telephone)
                        .append(";");

            }
        }
        if (!TextUtils.isEmpty(org))
        {
            meCardString.append(MeCardCostant.KEY_ORG)
                    .append(org)
                    .append(";");
        }

        return meCardString.toString();

    }
}
