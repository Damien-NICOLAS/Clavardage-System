package ui.presenter;

import main.ChatManager;
import model.ListeDesUsagers;
import model.Sessions;
import model.UsersDistants;
import ui.viewer.DialoguePageViewer;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;


public class DialoguePageController {

    private final ChatManager chatManager;

    public DialoguePageController(ChatManager theCM){
        chatManager = theCM;
    }
    public void onSendButton(String text, DialoguePageViewer view) {
        view.ajoutTextArea(text);
        //chatManager.getProtocoleDeCommunication().envoieMessage;

    }

    public void onEnterTouch(String text, DialoguePageViewer view) {
        view.ajoutTextArea(text);
    }

    public void onClickSelectionUserCo(String pseudo, DefaultMutableTreeNode root, JTree listeSessions) {
        UsersDistants newUserDistant = chatManager.accesALaListeDesUsagers().retourneUtilisateurDistantsParSonPseudo(pseudo);
        if (pseudo == chatManager.userPseudo()) {
            System.out.println("mise à jour de la liste");
        } else {
            System.out.println("Sélection" + newUserDistant.getPseudoActuel());
            chatManager.useListSessions().addUserDistantToSession(newUserDistant);
            chatManager.getProtocoleDeCommunication().envoieDeDemandeDeSession(newUserDistant.getLogin());
            //historique créer fichier
            System.out.println("Après ajout : ");
            chatManager.useListSessions().afficheListeSessions();
            System.out.println("Liste des usagers dans la table :" + chatManager.accesALaListeDesUsagers().retourneToutLesUsagersAsString());
            actualiserMenuSessions(root, listeSessions);

        }
    }

    public void onClickSelectionSession(String pseudoUtilisateurDistantEnChat){
        //afficher sur l'interface les messages
        //historique
        chatManager.defenieSessionCourante(pseudoUtilisateurDistantEnChat);

    }

    public void actualiserMenuUsersCo(DefaultMutableTreeNode root) {
        if (!(this.chatManager.accesALaListeDesUsagers().retourneToutLesUsagers().isEmpty())) {

            for (String usersCo : this.chatManager.accesALaListeDesUsagers().retourneToutLesUsagers()) {
                String pseudoUsersCo = this.chatManager.accesALaListeDesUsagers().retourneUnUtilisateurDistantParSonLogin(usersCo).getPseudoActuel();
                System.out.println("Pseudo co : " + pseudoUsersCo);

                if(pseudoUsersCo != null) {
                    if (!(pseudoUsersCo == this.chatManager.userPseudo())) {
                        DefaultMutableTreeNode child = new DefaultMutableTreeNode(pseudoUsersCo);
                        root.add(child);
                    }
                }
            }

        }
    }

    public void actualiserMenuSessions(DefaultMutableTreeNode root, JTree listeSessions) {
        if (!(this.chatManager.accesALaListeDesUsagers().retourneToutLesUsagers().isEmpty())) {

            for (UsersDistants sessionCourante : this.chatManager.useListSessions().retourneListeSessions()) {
                String pseudoUsersSession = sessionCourante.getPseudoActuel();
                System.out.println("Session Activée : " + pseudoUsersSession);

                if (!(pseudoUsersSession.equals(this.chatManager.userPseudo()))) {
                    DefaultMutableTreeNode child = new DefaultMutableTreeNode(pseudoUsersSession);
                    root.add(child);
                }
            }
            listeSessions.updateUI();
        }
    }

    public void onClickStopButtonSession(JTree listeSessions, String pseudo) {
        UsersDistants userSelectionne = chatManager.accesALaListeDesUsagers().retourneUtilisateurDistantsParSonPseudo(pseudo);
        chatManager.getProtocoleDeCommunication().envoieDeFinDeSession(userSelectionne.getLogin());
        chatManager.useListSessions().removeUserDistantOfSession(pseudo);
        listeSessions.updateUI();

    }

    public void show() {
        ListeDesUsagers list = chatManager.accesALaListeDesUsagers();

        System.out.println(list.retourneToutLesUsagersAsString());
    }
}
