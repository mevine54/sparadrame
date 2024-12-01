package fr.afpa.pompey.cda22045.controllers;

import java.util.ArrayList;
import java.util.List;

public class HistoriqueController {
    private List<String> historiqueData;

    public HistoriqueController() {
        historiqueData = new ArrayList<>();
    }

    public List<String> getHistoriqueData() {
        return historiqueData;
    }

    public void addAchat(String achat, boolean allowGrouping) {
        try {
            boolean alreadyExists = false;
            if (allowGrouping) {
                for (int i = 0; i < historiqueData.size(); i++) {
                    if (historiqueData.get(i).split(" - Quantité:")[0].equals(achat.split(" - Quantité:")[0])) {
                        String existingEntry = historiqueData.get(i);
                        String[] parts = existingEntry.split(" - Quantité: | - Prix Total: | - Médecin: | - Client: ");
                        if (parts.length < 3) {
                            continue;
                        }
                        int existingQuantity = Integer.parseInt(parts[1].trim());
                        double existingTotal = Double.parseDouble(parts[2].trim());

                        String[] newParts = achat.split(" - Quantité: | - Prix Total: | - Médecin: | - Client: ");
                        int newQuantity = Integer.parseInt(newParts[1].trim());
                        double newTotal = Double.parseDouble(newParts[2].trim());

                        int updatedQuantity = existingQuantity + newQuantity;
                        double updatedTotal = existingTotal + newTotal;

                        String updatedEntry = parts[0] + " - Quantité: " + updatedQuantity + " - Prix Total: " + updatedTotal;
                        if (parts.length > 3) { // Si c'est un achat via ordonnance, ajouter les informations du médecin et du client
                            updatedEntry += " - Médecin: " + parts[3] + " - Client: " + parts[4];
                        }
                        historiqueData.set(i, updatedEntry);
                        alreadyExists = true;
                        break;
                    }
                }
            }
            if (!alreadyExists) {
                historiqueData.add(achat);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Erreur de format de données lors de l'ajout de l'historique.");
        }
    }

    public void modifyAchat(int index, int newQuantity) {
        if (index >= 0 && index < historiqueData.size()) {
            String selectedAchat = historiqueData.get(index);
            String[] parts = selectedAchat.split(" - Quantité: | - Prix Total: | - Médecin: | - Client: ");
            if (parts.length >= 3) {
                double unitPrice = Double.parseDouble(parts[2].trim()) / Integer.parseInt(parts[1].trim());
                double newTotal = newQuantity * unitPrice;

                String updatedEntry = parts[0] + " - Quantité: " + newQuantity + " - Prix Total: " + newTotal;
                if (parts.length > 3) { // Si c'est un achat via ordonnance, ajouter les informations du médecin et du client
                    updatedEntry += " - Médecin: " + parts[3] + " - Client: " + parts[4];
                }
                historiqueData.set(index, updatedEntry);
            }
        } else {
            throw new IndexOutOfBoundsException("Index invalide pour la modification de l'achat.");
        }
    }
}


