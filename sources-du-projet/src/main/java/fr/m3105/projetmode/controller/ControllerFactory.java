package fr.m3105.projetmode.controller;

public class ControllerFactory {
    /**
     * This factory create a scecified ViewController
     * @param controllerName : type of controller you want
     * @return the specified controller, by default it's a fillfaceController
     */
    public ViewController create(String controllerName){
        if (controllerName.equals("face")){
            return new FillFaceController();
        }else if(controllerName.equals("point")){
            return new PointController();
        }else if(controllerName.equals("segment")){
            return new SegmentController();
        }else if(controllerName.equals("facesegment")){
            return new FaceSegmentController();
        }else{
            return null;
        }
    }
}
