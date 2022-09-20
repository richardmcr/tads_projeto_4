package uni9.projetopraticoemsistemas.myhealth.helper.mappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ListMapper {

    public List<String> asListString(String string) {
        List<String> list = new ArrayList<>();
        if (Objects.isNull(string))
            return list;

        Collections.addAll(list, string.split(";"));
        return list;
    }

    public String asString(List<String> list) {
        StringBuilder sb = new StringBuilder();

        for (String string : list) {
            sb.append(string).append(";");
        }

        return sb.toString();
    }
}
