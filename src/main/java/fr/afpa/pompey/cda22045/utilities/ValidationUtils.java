package fr.afpa.pompey.cda22045.utilities;

import java.util.regex.Pattern;

public class ValidationUtils {

    // Validation d'un email
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }

    // Validation d'un numéro de téléphone français (ex : 0123456789)
    public static boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^0[1-9][0-9]{8}$";
        return Pattern.matches(phoneRegex, phoneNumber);
    }

    // Validation d'un nom ou prénom (lettres uniquement, max 30 caractères)
    public static boolean isValidName(String name) {
        String nameRegex = "^[A-Za-zÀ-ÖØ-öø-ÿ \\-]{1,30}$";
        return Pattern.matches(nameRegex, name);
    }

    // Validation d'un code postal (5 chiffres)
    public static boolean isValidPostalCode(String postalCode) {
        String postalCodeRegex = "^[0-9]{5}$";
        return Pattern.matches(postalCodeRegex, postalCode);
    }

    // Validation d'un numéro de sécurité sociale français (15 chiffres)
    public static boolean isValidSocialSecurityNumber(String ssn) {
        String ssnRegex = "^[1-2][0-9]{2}(0[1-9]|1[0-2])[0-9]{8}$";
        return Pattern.matches(ssnRegex, ssn);
    }

    // Validation d'un taux (entre 0 et 100)
    public static boolean isValidRate(double rate) {
        return rate >= 0 && rate <= 100;
    }

    // Validation générique pour vérifier si une chaîne n'est pas vide ou nulle
    public static boolean isNotEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    // Validation d'un champ obligatoire
    public static boolean isFieldValid(String field, String fieldName) {
        if (!isNotEmpty(field)) {
            System.out.println("Le champ '" + fieldName + "' est obligatoire.");
            return false;
        }
        return true;
    }
}
