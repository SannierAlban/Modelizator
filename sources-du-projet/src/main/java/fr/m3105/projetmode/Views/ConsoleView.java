package fr.m3105.projetmode.Views;

import fr.m3105.projetmode.model.Model;
import fr.m3105.projetmode.model.utils.Observer;
import fr.m3105.projetmode.model.utils.Subject;

import java.util.Arrays;

public class ConsoleView implements Observer {
    Model model;
    public ConsoleView(Model m){
        model = m;
    }
    @Override
    public void update(Subject subj) {
        model = (Model) subj;
        System.out.println(Arrays.toString(model.getCenter()));
    }

    @Override
    public void update(Subject subj, Object data) {
    //rien ici
    }
}
