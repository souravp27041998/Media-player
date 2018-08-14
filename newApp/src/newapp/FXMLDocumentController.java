package newapp;


import java.io.File;
import static java.util.concurrent.ThreadLocalRandom.current;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Duration;


public class FXMLDocumentController {

    @FXML
    private  Slider timeslider;

    @FXML
    private Button choose;

    @FXML
    private Button play;

    @FXML
    private Button pause;

    @FXML
    private Button stop;

    @FXML
    private Button fast;

    @FXML
    private Button ExtraFast;

    @FXML
    private Button slow;

    @FXML
    private Button extraslow;

    @FXML
    private Button new1;
    @FXML
    private Button normal;

    @FXML
    private Slider volslider;
    @FXML
    private MediaView mv;
    private Media m;
    private MediaPlayer mp;
    private String path=null;
    @FXML
    void handlechooseaction(ActionEvent event) {
        
         FileChooser fc=new FileChooser();
        FileChooser.ExtensionFilter filter=new FileChooser.ExtensionFilter("open a file","*.mp4");
        FileChooser.ExtensionFilter filter1=new FileChooser.ExtensionFilter("open a file","*.mp3");
        FileChooser.ExtensionFilter filter2=new FileChooser.ExtensionFilter("open a file","*.avi");
        FileChooser.ExtensionFilter filter3=new FileChooser.ExtensionFilter("open a file","*.mkv");
        fc.getExtensionFilters().addAll(filter,filter1,filter2,filter3);
        File file=fc.showOpenDialog(null);
        path=file.toURI().toString();
        
        
         try{  
        m=new Media(path);
        mp=new MediaPlayer(m);
        mv.setMediaPlayer(mp);
         

        mp.setAutoPlay(true);
        mv.setSmooth(true);
        
        mp.setOnReady(new Runnable(){
             @Override
             public void run() {
                   DoubleProperty width =mv.fitWidthProperty();
        DoubleProperty height =mv.fitHeightProperty();
        width.bind(Bindings.selectDouble(mv.sceneProperty(),"width"));
        height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
               
        timeslider.setMin(0.0);
        timeslider.setValue(0.0);
        timeslider.setMax(mp.getTotalDuration().toSeconds());
        
        
        volslider.setMin(0);
        volslider.setValue(100);
        volslider.setMax(100);
        
             }
            
        });
         }catch(Exception e){
             Alert alert1=new Alert(Alert.AlertType.ERROR);
             alert1.setContentText(e.toString());
             alert1.showAndWait();
             
         }
         
        
         DropShadow ds=new DropShadow();
         ds.setOffsetX(5.0);
         ds.setOffsetY(5.0);
         ds.setColor(Color.BLUE);
         mv.setEffect(ds);
         
         

    }
    
    @FXML
    void handlenormalaction(ActionEvent event){
        mp.setRate(1);
    }

    @FXML
    void handleextrafastaction(ActionEvent event) {
          mp.setRate(3);
                  
    }

    @FXML
    void handleextraslowaction(ActionEvent event) {
        mp.setRate(0.25);
    }

    @FXML
    void handlefastaction(ActionEvent event) {
          mp.setRate(2);
    }

    @FXML
    void handlenew1action(ActionEvent event) {
        
        mp.dispose();

    }

    @FXML
    void handlepauseaction(ActionEvent event) {
        mp.pause();
    }

    @FXML
    void handleplayaction(ActionEvent event) {
        
          try{
          mp.play();
        }
        catch(Exception e){
                Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle(path);
            alert.setHeaderText(path);
            alert.setContentText("First you should choose a File");
            alert.showAndWait();
        }
        

    
    }

    @FXML
    void handleslowaction(ActionEvent event) {
       
        mp.setRate(0.50);
    }

    @FXML
    void handlestopaction(ActionEvent event) {
          mp.stop();
    }

    @FXML
    void handletimeslider(MouseEvent event) {
         mp.currentTimeProperty().addListener(new ChangeListener<Duration>(){
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                timeslider.setValue(mp.getCurrentTime().toSeconds());
            
                
            }
            
        });
          timeslider.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                mp.seek(Duration.seconds(timeslider.getValue()));
             
            }
            
        });
        
       }
            
    
       
    

    @FXML
    void handlevolslider(MouseEvent event) {

        
          volslider.valueProperty().addListener(new InvalidationListener(){
            @Override
            public void invalidated(Observable observable) {
             //   if(volslider.isValueChanging())
                mp.setVolume(volslider.getValue()/100);
            }
            
        });

    }

}
