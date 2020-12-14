package fr.m3105.projetmode.controller;

public class ControllerFactory {
    /**
     * This factory create a scecified ViewController
     * @param s : type of controller you want
     * @return the specified controller, by default it's a fillfaceController
     */
    public ViewController create(String s){
        if (s.equals("face")){
            return new FillFaceController();
        }else if(s.equals("point")){
            return new PointController();
        }else if(s.equals("segment")){
            return new SegmentController();
        }else{
            return null;
        }
    }
}
