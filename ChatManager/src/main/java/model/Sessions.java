package model;

import historique.MessageHistorique;
import main.ChatManager;

import java.util.ArrayList;

public class Sessions {

    private final ChatManager chatManager;
    private UsersDistants sessionEnCoursDeChat;
    private ArrayList<UsersDistants> listeSessions;
    private ArrayList<MessageHistorique> messageHistoriques;

    public Sessions(ChatManager theChatManager){
        chatManager = theChatManager;
        listeSessions = new ArrayList<>();
        messageHistoriques = new ArrayList<>();
        sessionEnCoursDeChat = null;

    }

    public void addUserDistantToSession(UsersDistants usersDistants){
        listeSessions.add(usersDistants);
        MessageHistorique nouveauMessageHistorique = new MessageHistorique(usersDistants, chatManager);
        messageHistoriques.add(nouveauMessageHistorique);

    }

    public void afficheListeSessions() {
        for (UsersDistants courant : listeSessions) {
            System.out.println(courant.getPseudoActuel());

        }
    }


    public MessageHistorique retrouveUnHistoriqueParSonUser(String loginUser){
        MessageHistorique messageHistoriqueVoulu = null;
        for (MessageHistorique mshist : messageHistoriques){
             if(mshist.userDistant.getLogin().equals(loginUser)){
                 messageHistoriqueVoulu = mshist;
             }
        }
        return messageHistoriqueVoulu;

    }

    public ArrayList<UsersDistants> retourneListeSessions(){
        return listeSessions;
    }

    public void removeUserDistantOfSession(String pseudo) {
    }

    public void definieUserDistantCourant(UsersDistants utilisateurDistantEnChat) {
        sessionEnCoursDeChat = utilisateurDistantEnChat;
    }

    public UsersDistants userDistantSessionCourante() {
        return sessionEnCoursDeChat;
    }
}