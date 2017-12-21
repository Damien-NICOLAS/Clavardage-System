package main;

import connectivite.ProtocoleDeCommunication;
import model.ListeDesUsagers;
import model.Sessions;
import model.UserLocal;
import model.UsersDistants;


import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatManager {
    private ProtocoleDeCommunication protocoleDeCommunication;
    private final ListeDesUsagers listeDesUsagers;
    private final Sessions session;
    private UserLocal user;


    public ChatManager(){
        this.protocoleDeCommunication = new ProtocoleDeCommunication(this);
        this.session = new Sessions();
        this.user = new UserLocal(null, null, "vince", "toto");
        this.listeDesUsagers = new ListeDesUsagers(this);
        protocoleDeCommunication.ecouteDuReseauEnUDP();
        protocoleDeCommunication.ecouteDuReseauEnTCP(userPort());

    }

    public ListeDesUsagers accesALaListeDesUsagers(){
        return listeDesUsagers;
    }

    public UserLocal returnUserLocal(){
        return user;
    }

    public String userLogin(){
        return  this.user.useLoginUser();
    }
    public String userPassword(){
        return this.user.usePasswordUser();
    }
    public void addPseudoToUser(String pseudo){
        this.user.userPseudoAdd(pseudo);
    }
    public String userPseudo(){
        return this.user.usePseudoUser();
    }

    public ProtocoleDeCommunication getProtocoleDeCommunication() {
        return protocoleDeCommunication;
    }

    public void addIpadressToUser(String IpAdress){
        this.user.userIPAdressAdd(IpAdress);
    }
    public String userIp(){return this.user.useIpUser();}

    public int userPort(){return  this.user.usePortTCP();}


    public Sessions useListSessions() {
        return session;
    }

    public boolean verificationUnicitePseudo(String pseudo) {
        boolean pseudoUnique = true;
        if (!(this.listeDesUsagers.retourneToutLesUsagers().size() == 1)) {
            Set<String> usersCo = this.listeDesUsagers.retourneToutLesUsagers();
            for (String st: usersCo){
                UsersDistants usersDistantsCourant = this.listeDesUsagers.retourneUnUtilisateurDistantParSonLogin(st);
                if(!(usersDistantsCourant.getLogin().equals(userLogin()))){
                    if(usersDistantsCourant.getPseudoActuel().equals(pseudo)){
                        pseudoUnique = false;
                    }
                }

            }

        }
        return pseudoUnique;
    }

    public boolean verificationCasse(String newPseudo) {
        if (newPseudo != null) {
            String regex = "^[^,;/\\\\.@é\"\"'{}()_+\\-èàç\\^ïëêî!\\?\\.µ$£§¤#~&°]+$"; /*" /^[^@&\"()!_$*€£`+=;?#éèçà,:ù%µ§]+$/";*/
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(newPseudo);
            if (!matcher.matches()) {
                return false;
            }
        }
        return true;
    }


    public void defenieSessionCourante(String pseudoUtilisateurDistantEnChat) {
        UsersDistants utilisateurDistantEnChat = listeDesUsagers.retourneUtilisateurDistantsParSonPseudo(pseudoUtilisateurDistantEnChat);
        session.definieUserDistantCourant(utilisateurDistantEnChat);
    }

    public UsersDistants useSessionCourante(){
        return session.userDistantSessionCourante();
    }
}







