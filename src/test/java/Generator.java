import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Generator {
    public static String getCorrectLogin() {
        return "vasya";
    }

    public static String getCorrectPassword() {
        return "qwerty123";
    }

    public static String getCorrectVerCode() {
        return "12345";
    }

    public static String getFstCardNumber() {
        return "5559 0000 0000 0001";
    }

    public static String getSndCardNumber() {
        return "5559 0000 0000 0002";
    }

    public static String getIncorrectCardNumber() {
        return "5559 0000 0000 0003";
    }

    public static String getFstCardId() {
        return "92df3f1c-a033-48e6-8390-206f6b1f56c0";
    }

    public static String getSndCardId() {
        return "0f3f5c2a-249e-4c3d-8287-09f7a039391d";
    }
}
