package fr.m3105.projetmode.utils;

public interface Observer {
    public void update(Subject subj);
    public void update(Subject subj, Object data);
}

