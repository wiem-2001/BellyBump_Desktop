//package tn.esprit.test;
//
//import tn.esprit.entities.Etablissement;
//import tn.esprit.entities.Medcin;
//import tn.esprit.entities.RendezVous;
//import tn.esprit.services.EtablissementServices;
//import tn.esprit.services.RendezVousServices;
//import tn.esprit.services.MedcinServices;
//import tn.esprit.util.MaConnexion;
//
//import java.sql.Connection;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.List;
//import java.util.Scanner;
//
//public class Main {
//    // Méthode pour afficher les établissements
//    private static void afficherEtablissements(List<Etablissement> etablissements) {
//        System.out.println("Liste des établissements :");
//        for (Etablissement etablissement : etablissements) {
//            System.out.println("ID: " + etablissement.getId());
//            System.out.println("Nom: " + etablissement.getNom());
//            System.out.println("Type: " + etablissement.getType());
//            System.out.println("Localisation: " + etablissement.getLocalisation());
//            System.out.println("/////////");
//        }
//    }
//    public static void main(String[] args) {
//        // Obtenez une instance de connexion à la base de données
//        Connection connection = MaConnexion.getInstance().getCnx();
//        Scanner scanner = new Scanner(System.in);
//        RendezVousServices rendezVousServices = new RendezVousServices(connection);
//
//        EtablissementServices etablissementServices = new EtablissementServices(connection);
//        MedcinServices medcinServices = new MedcinServices(connection);
//
//
//// Gestion etablissement
//
//        System.out.println("Que souhaitez-vous faire ?");
//        System.out.println("1. Ajouter un établissement");
//        System.out.println("2. Mettre à jour l'établissement");
//        System.out.println("3. Afficher tous les établissements");
//        System.out.println("4. Supprimer l'établissement");
//        System.out.print("Votre choix : ");
//        int choix = scanner.nextInt();
//
//        // Traitement en fonction du choix de l'utilisateur
//        switch (choix) {
//            case 1:
//                // Demandez à l'utilisateur de saisir les informations sur l'établissement
//                System.out.println("Veuillez saisir les informations sur l'établissement :");
//
//                System.out.print("Nom : ");
//                // Consommez la nouvelle ligne après nextInt() pour éviter les problèmes de lecture
//                scanner.nextLine();
//                String nomEtablissement = scanner.nextLine();
//
//                System.out.print("Type : ");
//                String typeEtablissement = scanner.nextLine();
//                System.out.print("Localisation : ");
//                String localisationEtablissement = scanner.nextLine();
//
//                // Créez un nouvel établissement avec les informations fournies par l'utilisateur
//                Etablissement nouvelEtablissement = new Etablissement(nomEtablissement, typeEtablissement, localisationEtablissement);
//
//                // Ajoutez le nouvel établissement à la base de données
//                etablissementServices.add(nouvelEtablissement);
//                System.out.println("Établissement ajouté avec succès !");
//                break;
//
//            case 2:
//                // Mise à jour de l'établissement
//                // Demandez à l'utilisateur de saisir l'ID de l'établissement à mettre à jour
//                System.out.print("Veuillez saisir l'ID de l'établissement à mettre à jour : ");
//                int idEtablissement = scanner.nextInt();
//                scanner.nextLine(); // Pour consommer la nouvelle ligne
//
//                // Obtenez l'établissement à partir de son ID
//                Etablissement etablissementAModifier = etablissementServices.getOne(idEtablissement);
//                if (etablissementAModifier != null) {
//                    // Demandez à l'utilisateur de saisir les nouvelles informations pour l'établissement
//                    System.out.println("Veuillez saisir les nouvelles informations pour l'établissement :");
//                    System.out.print("Nouveau nom : ");
//                    String nouveauNomEtablissement = scanner.nextLine();
//                    System.out.print("Nouveau type : ");
//                    String nouveauTypeEtablissement = scanner.nextLine();
//                    System.out.print("Nouvelle localisation : ");
//                    String nouvelleLocalisationEtablissement = scanner.nextLine();
//
//                    // Mettez à jour les informations de l'établissement
//                    etablissementAModifier.setNom(nouveauNomEtablissement);
//                    etablissementAModifier.setType(nouveauTypeEtablissement);
//                    etablissementAModifier.setLocalisation(nouvelleLocalisationEtablissement);
//
//                    // Effectuez la mise à jour dans la base de données
//                    etablissementServices.update(etablissementAModifier);
//                    System.out.println("Établissement mis à jour avec succès !");
//                } else {
//                    System.out.println("Aucun établissement trouvé pour l'ID spécifié.");
//                }
//                break;
//            case 3:
//                System.out.println("Que souhaitez-vous faire avec les établissements ?");
//                System.out.println("1. Afficher tous les établissements");
//                System.out.println("2. Afficher les établissements privés");
//                System.out.println("3. Afficher les établissements publics");
//                System.out.println("4. Supprimer un établissement");
//                System.out.print("Votre choix : ");
//                int choixEtablissement = scanner.nextInt();
//
//                switch (choixEtablissement) {
//                    case 1:
//                        // Obtenir tous les établissements
//                        List<Etablissement> tousLesEtablissements = etablissementServices.getAll();
//                        // Afficher les établissements
//                        afficherEtablissements(tousLesEtablissements);
//                        break;
//
//                    case 2:
//                        // Obtenir les établissements privés
//                        List<Etablissement> etablissementsPrives = etablissementServices.getByType("privé");
//                        // Afficher les établissements privés
//                        afficherEtablissements(etablissementsPrives);
//                        break;
//
//                    case 3:
//                        // Obtenir les établissements publics
//                        List<Etablissement> etablissementsPublics = etablissementServices.getByType("public");
//                        // Afficher les établissements publics
//                        afficherEtablissements(etablissementsPublics);
//                        break;
//                    default:
//                        System.out.println("Choix invalide !");
//                }
//break;
//
//            case 4:
//                // Suppression de l'établissement
//                // Demandez à l'utilisateur de saisir l'ID de l'établissement à supprimer
//                System.out.print("Veuillez saisir l'ID de l'établissement à supprimer : ");
//                int idEtablissementSupprimer = scanner.nextInt();
//                scanner.nextLine(); // Pour consommer la nouvelle ligne
//
//                // Obtenez l'établissement à partir de son ID
//                Etablissement etablissementASupprimer = etablissementServices.getOne(idEtablissementSupprimer);
//                if (etablissementASupprimer != null) {
//                    // Supprimez l'établissement de la base de données
//                    etablissementServices.delete(etablissementASupprimer);
//                    System.out.println("Établissement supprimé avec succès !");
//                } else {
//                    System.out.println("Aucun établissement trouvé pour l'ID spécifié.");
//                }
//                break;
//            default:
//                System.out.println("Choix invalide !");
//        }
//
//        // Fermez le scanner
//        scanner.close();
//
////// Obtenir un établissement par son ID
////        Etablissement etablissementTrouve = etablissementServices.getOne(2);
////        if (etablissementTrouve != null) {
////            System.out.println("Etablissement trouvé : ");
////            System.out.println("ID: " + etablissementTrouve.getId());
////            System.out.println("Nom: " + etablissementTrouve.getNom());
////            System.out.println("Type: " + etablissementTrouve.getType());
////            System.out.println("Localisation: " + etablissementTrouve.getLocalisation());
////        } else {
////            System.out.println("Aucun établissement trouvé pour l'ID spécifié.");
////        }
//// gestion Medcin
////        System.out.println("Que souhaitez-vous faire avec les médecins ?");
////        System.out.println("1. Ajouter un médecin");
////        System.out.println("2. Mettre à jour un médecin");
////        System.out.println("3. Afficher tous les médecins");
////        System.out.println("4. Supprimer un médecin");
////        System.out.print("Votre choix : ");
////        int choixMedcin = scanner.nextInt();
////
////        switch (choixMedcin) {
////            case 1:
////                // Demandez à l'utilisateur de saisir les informations sur le médecin
////                System.out.println("Veuillez saisir les informations sur le médecin :");
////
////                System.out.print("Nom : ");
////                scanner.nextLine();
////                String nomMedcin = scanner.nextLine();
////
////                System.out.print("Prénom : ");
////                String prenomMedcin = scanner.nextLine();
////
////                System.out.print("Spécialité : ");
////                String specialiteMedcin = scanner.nextLine();
////
////                // Créez un nouveau médecin avec les informations fournies par l'utilisateur
////                Medcin nouveauMedcin = new Medcin(nomMedcin, prenomMedcin, specialiteMedcin);
////
////                // Ajoutez le nouveau médecin à la base de données
////                medcinServices.add(nouveauMedcin);
////                System.out.println("Médecin ajouté avec succès !");
////                break;
////
////            case 2:
////                // Mise à jour du médecin
////                System.out.print("Veuillez saisir l'ID du médecin à mettre à jour : ");
////                int idMedcin = scanner.nextInt();
////                scanner.nextLine(); // Pour consommer la nouvelle ligne
////
////                // Obtenez le médecin à partir de son ID
////                Medcin medcinAModifier = medcinServices.getOne(idMedcin);
////                if (medcinAModifier != null) {
////                    // Demandez à l'utilisateur de saisir les nouvelles informations pour le médecin
////                    System.out.println("Veuillez saisir les nouvelles informations pour le médecin :");
////                    System.out.print("Nouveau nom : ");
////                    String nouveauNomMedcin = scanner.nextLine();
////                    System.out.print("Nouveau prénom : ");
////                    String nouveauPrenomMedcin = scanner.nextLine();
////                    System.out.print("Nouvelle spécialité : ");
////                    String nouvelleSpecialiteMedcin = scanner.nextLine();
////
////                    // Mettez à jour les informations du médecin
////                    medcinAModifier.setNom(nouveauNomMedcin);
////                    medcinAModifier.setPrenom(nouveauPrenomMedcin);
////                    medcinAModifier.setSpecialite(nouvelleSpecialiteMedcin);
////
////                    // Effectuez la mise à jour dans la base de données
////                    medcinServices.update(medcinAModifier);
////                    System.out.println("Médecin mis à jour avec succès !");
////                } else {
////                    System.out.println("Aucun médecin trouvé pour l'ID spécifié.");
////                }
////                break;
////
////            case 3:
////                // Obtenir tous les médecins
////                List<Medcin> tousLesMedcins = medcinServices.getAll();
////
////                // Afficher les médecins
////                System.out.println("Liste des médecins :");
////                for (Medcin medcin : tousLesMedcins) {
////                    System.out.println("Nom : " + medcin.getNom());
////                    System.out.println("Prénom : " + medcin.getPrenom());
////                    System.out.println("Spécialité : " + medcin.getSpecialite());
////                    System.out.println("/////////");
////                }
////                break;
////
////            case 4:
////                // Suppression du médecin
////                System.out.print("Veuillez saisir l'ID du médecin à supprimer : ");
////                int idMedcinSupprimer = scanner.nextInt();
////                scanner.nextLine(); // Pour consommer la nouvelle ligne
////
////                // Obtenez le médecin à partir de son ID
////                Medcin medcinASupprimer = medcinServices.getOne(idMedcinSupprimer);
////                if (medcinASupprimer != null) {
////                    // Supprimez le médecin de la base de données
////                    medcinServices.delete(medcinASupprimer);
////                    System.out.println("Médecin supprimé avec succès !");
////                } else {
////                    System.out.println("Aucun médecin trouvé pour l'ID spécifié.");
////                }
////                break;
////
////            default:
////                System.out.println("Choix invalide !");
////        }
//
////gestion rendez-vous
////
////
//
//        System.out.println("Que souhaitez-vous faire avec les rendez-vous ?");
//        System.out.println("1. Ajouter un rendez-vous");
//        System.out.println("2. Mettre à jour un rendez-vous");
//        System.out.println("3. Afficher tous les rendez-vous");
//        System.out.println("4. Supprimer un rendez-vous");
//        System.out.print("Votre choix : ");
//        int choixRendezVous = scanner.nextInt();
//
//// Créez le service pour les rendez-vous en utilisant la connexion à la base de données
//
//
//// Traitez le choix de l'utilisateur
//        switch (choixRendezVous) {
//            case 1:
//                // Demandez à l'utilisateur de saisir les informations sur le rendez-vous
//                System.out.println("Veuillez saisir les informations sur le rendez-vous :");
//
//                System.out.print("Date (AAAA-MM-JJ) : ");
//                String dateRendezVousString = scanner.next();
//                LocalDate dateRendezVous = LocalDate.parse(dateRendezVousString);
//
//
//                // Consommez la nouvelle ligne après next() pour éviter les problèmes de lecture
//                scanner.nextLine();
//                System.out.print("Heure (HH) : ");
//                int heureRendezVous = scanner.nextInt();
//                // Créez un nouveau rendez-vous avec les informations fournies par l'utilisateur
//                RendezVous nouveauRendezVous = new RendezVous(dateRendezVous, heureRendezVous);
//
//                // Ajoutez le nouveau rendez-vous à la base de données
//                rendezVousServices.add(nouveauRendezVous);
//                System.out.println("Rendez-vous ajouté avec succès !");
//                break;
//
//
//            case 2:
//                // Mise à jour du rendez-vous
//                System.out.print("Veuillez saisir l'ID du rendez-vous à mettre à jour : ");
//                int idRendezVous = scanner.nextInt();
//
//                // Obtenez le rendez-vous à partir de son ID
//                RendezVous rendezVousAModifier = rendezVousServices.getOne(idRendezVous);
//                if (rendezVousAModifier != null) {
//                    // Demandez à l'utilisateur de saisir les nouvelles informations pour le rendez-vous
//                    System.out.println("Veuillez saisir les nouvelles informations pour le rendez-vous :");
//                    System.out.print("Nouvelle date (AAAA-MM-JJ) : ");
//                    String nouvelleDateRendezVous = scanner.next();
//                    System.out.print("Nouvelle heure (HH:MM:SS) : ");
//                    String nouvelleHeureRendezVous = scanner.next();
//
//                    // Mettez à jour les informations du rendez-vous
//                    rendezVousAModifier.setDateReservation(LocalDate.parse(nouvelleDateRendezVous));
//                    rendezVousAModifier.setHeure(Integer.parseInt(nouvelleHeureRendezVous));
//
//                    // Effectuez la mise à jour dans la base de données
//                    rendezVousServices.update(rendezVousAModifier);
//                    System.out.println("Rendez-vous mis à jour avec succès !");
//                } else {
//                    System.out.println("Aucun rendez-vous trouvé pour l'ID spécifié.");
//                }
//                break;
//
//            case 3:
//                // Obtenir tous les rendez-vous
//                List<RendezVous> tousLesRendezVous = rendezVousServices.getAll();
//
//                // Afficher les rendez-vous
//                System.out.println("Liste des rendez-vous :");
//                for (RendezVous rendezVous : tousLesRendezVous) {
//                    System.out.println("Date : " + rendezVous.getDateReservation());
//                    System.out.println("Heure : " + rendezVous.getHeure());
//                    System.out.println("/////////");
//                }
//                break;
//
//            case 4:
//                // Suppression du rendez-vous
//                System.out.print("Veuillez saisir l'ID du rendez-vous à supprimer : ");
//                int idRendezVousSupprimer = scanner.nextInt();
//
//                // Obtenez le rendez-vous à partir de son ID
//                RendezVous rendezVousASupprimer = rendezVousServices.getOne(idRendezVousSupprimer);
//                if (rendezVousASupprimer != null) {
//                    // Supprimez le rendez-vous de la base de données
//                    rendezVousServices.delete(rendezVousASupprimer);
//                    System.out.println("Rendez-vous supprimé avec succès !");
//                } else {
//                    System.out.println("Aucun rendez-vous trouvé pour l'ID spécifié.");
//                }
//                break;
//
//            default:
//                System.out.println("Choix invalide !");
//        }
//
//// Fermez le scanner
//        scanner.close();
//
//
//    }
//
//
//
//    }
//
