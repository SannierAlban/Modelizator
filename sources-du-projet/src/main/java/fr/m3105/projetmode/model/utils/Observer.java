package fr.m3105.projetmode.model.utils;

public interface Observer {
    public void update(Subject subj);
    public void update(Subject subj, Object data);
}

