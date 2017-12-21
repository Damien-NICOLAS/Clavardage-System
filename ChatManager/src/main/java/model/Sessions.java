package model;

import java.util.ArrayList;

public class Sessions {

    private UsersDistants sessionEnCoursDeChat;
    private ArrayList<UsersDistants> listeSessions;

    public Sessions(){
        listeSessions = new ArrayList<>();
        sessionEnCoursDeChat = null;

    }

    public void addUserDistantToSession(UsersDistants usersDistants){
        listeSessions.add(usersDistants);

    }

    public void afficheListeSessions() {
        for (UsersDistants courant : listeSessions) {
            System.out.println(courant.getPseudoActuel());

        }
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