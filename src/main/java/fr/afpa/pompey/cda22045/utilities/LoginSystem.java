package fr.afpa.pompey.cda22045.utilities;

import fr.afpa.pompey.cda22045.utilities.SessionManager;

import java.util.Scanner;

public class LoginSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Demander l'ID utilisateur pour la session
        System.out.print("Entrez votre ID utilisateur pour vous connecter : ");
        int userId = scanner.nextInt();

        boolean isLoggedIn = SessionManager.loginUser(userId);

        if (isLoggedIn) {
            System.out.println("Connexion réussie !");
        } else {
            System.out.println("Échec de la connexion. Veuillez vérifier l'ID.");
        }

        // Exemple d'utilisation après connexion
        Integer currentUserId = SessionManager.getCurrentUserId();
        if (currentUserId != null) {
            System.out.println("Utilisateur actuellement connecté : ID " + currentUserId);
        } else {
            System.out.println("Aucun utilisateur connecté.");
        }

        // Déconnexion
        System.out.print("Entrez '1' pour vous déconnecter : ");
        int logout = scanner.nextInt();
        if (logout == 1) {
            boolean isLoggedOut = SessionManager.logoutUser(userId);
            if (isLoggedOut) {
                System.out.println("Déconnexion réussie.");
            } else {
                System.out.println("Erreur lors de la déconnexion.");
            }
        }
        scanner.close();
    }
}

