package historique;


import main.ChatManager;
import model.UsersDistants;

import java.io.File;
import java.io.IOException;
import java.io.*;

import java.time.LocalDateTime;
import java.util.HashMap;


import java.util.Map;
import java.util.regex.*;




public class MessageHistorique {
    private final File repertoire;
    public final UsersDistants userDistant;

    private static HashMap<String, File> listeFichiers;
    private static final String PATH = ".\\src\\main\\HistoriqueDossier\\";
    private final ChatManager chatManager;
    private File fichierSessionHistorique;
    private String fichierTrouver;


    public MessageHistorique(UsersDistants user, ChatManager theCM) {
        repertoire = new File(PATH);
        chatManager = theCM;
        listeFichiers = new HashMap<String, File>();
        userDistant = user;
        fichierTrouver=null;

    }
    public String toString() {
        return PATH+chatManager.userLogin()+"_"+userDistant.getLogin()+".txt";
    }

    public void creerFichier() throws IOException, NotFileException {
        fichierSessionHistorique = new File(toString());
        System.out.println("création fichierSessionHistorique");
        if(!fichierSessionHistorique.exists()){
            if (!fichierSessionHistorique.createNewFile()) {
                throw new NotFileException("Impossible de créer  le fichier");
            }
        }
    }

    public void ecriturefichier(MomentEcriture moment, String message) throws IOException {
        FileWriter write = new FileWriter(fichierSessionHistorique, true);
        String message_prepare =  moment + ":" + LocalDateTime.now() + ":" + message ;
        write.write(message_prepare);
        write.append("\n");
        write.close();
    }

    private boolean checkNomFichier(String nomfichier) {
        String filtre = "[a-zA-Z0-9]+_[a-zA-Z0-9]+\\.[a-zA-Z]{3}";

        Pattern p = Pattern.compile(filtre);

        Matcher matcher = p.matcher(nomfichier);
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }

    public void listeFichiersDossier() throws NotDirectoryException, CasseNomFichierException {

        File[] files = repertoire.listFiles();
        if (!repertoire.isDirectory()) {
            throw new NotDirectoryException("Le nom renseigné n'est pas un dossier!!!");
        }

        for (int i = 0; i < (files != null ? files.length : 0); i++) {
            if (!checkNomFichier(files[i].getName())) {
                throw new CasseNomFichierException("Modifier le nom du fichier!!!!");
            }
            System.out.println("Ajout fichier"+ files[i].toString());
            listeFichiers.put(files[i].getName(), files[i]);
        }
    }

    public void affichageListeFichierDuDossier(){
        for (Map.Entry<String, File> e : listeFichiers.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }

    public File findfichier(String nomfichier){
        return listeFichiers.get(nomfichier);
    }
    public void lirefichier(String nomFichier) throws IOException, NotFileException {
        File fichier = fichierSessionHistorique;
        if(fichier.exists()) {
            System.out.println(nomFichier);
            FileReader file = new FileReader(nomFichier);
            BufferedReader reader = new BufferedReader(file);
            String line;
            while (( line = reader.readLine() ) != null) {
                System.out.println(line);
            }
            reader.close();
        }else{
            creerFichier();
        }
    }

}
