package inventionsource.com.au.pidemo;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author jurek
 */
public class PiDemo {

    private static final Logger log = LogManager.getLogger(PiDemo.class);

    final static String VERSION = "1.02";
 /*
  Invention Source (c) 2020    
    final static String VERSION = "1.02"; 2020-04-29 Works on win/lin start again
                                         WinScp changed to ssh Openssh agent
  final static String VERSION = "1.01"; 2020-04-26 clean up source
  final static String VERSION = "1.00"; 2020-04-21 Linux
 */

    public static void main(String args[]) throws Exception {

        log.info("Starting pidemo Version: " + VERSION + " <---------------------------");

        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();  
        try {
            gpio.removeAllTriggers();
            gpio.removeAllListeners();
            //
            GpioPinDigitalOutput outYellow = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27,
                                                                        "outYellowPin27",PinState.LOW);
            outYellow.clearProperties();
            outYellow.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
            outYellow.low();
            
            GpioPinDigitalOutput outGreen = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_25,
                                                                        "outGreenPin25",PinState.LOW);
            outGreen.clearProperties();
            outGreen.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
            outYellow.low();
            
            log.info("Added output pins: " + outYellow.getName() + " : "+ outGreen.getName());
            
            GpioPinDigitalInput inBlue = gpio.provisionDigitalInputPin(RaspiPin.GPIO_22,"inPin22",
                                                                        PinPullResistance.PULL_UP);
            inBlue.setShutdownOptions(true);            
            log.info("Added input pin: " + inBlue.getName() );
            inBlue.setDebounce(1000);
            log.info("setDebounce(1000) on: " + inBlue.getName() );
            log.info(
              "------------------------- Output/Input Pins setup done. --------------------------------------");

            //------------------------------------------------------------------------------
            //------------       GpioPinDigitalStateChange       ---------------------------
            //------------------------------------------------------------------------------

            log.info("addListener to Input pin: " + inBlue.getName());
            inBlue.addListener(new GpioPinListenerDigital() {
                 @Override
                 public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                    log.info("--------------  PinDigitalStateChange --------------");
                    
                    String blueStatus= event.getState().toString();
                    String blueStatusStamp =
                            (DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss").format(LocalDateTime.now())) +
                            "-" + blueStatus ; 
                    outGreen.low();
                    outYellow.low();
                    log.info("PinDigitalStateChange now blueStatusStamp: "+ blueStatusStamp);
                       try {
                            if (blueStatus.equalsIgnoreCase("HIGH")){
                                outGreen.high();
                                outYellow.low();
                            }
                            if (blueStatus.equalsIgnoreCase("LOW")){
                                outYellow.high();
                                outGreen.low();
                           }
                            if (!blueStatus.equalsIgnoreCase("LOW") &&
                                    !blueStatus.equalsIgnoreCase("HIGH") ){
                                log.error("PinDigitalStateChange Error somewhere. blueStatus != " +
                                        "LOW && blueStatus != HIGH. blueStatus: " + blueStatus);
                                ToggleLed(outYellow, 1000, 10);
                            }
                        } catch (Exception ex) {
                           // Exception toggle outYellow led on error
                            log.error("Exception Error PinDigitalStateChange blueStatus : " +
                                                blueStatus, ex.getMessage());
                            ToggleLed(outYellow, 500, 20);
                        } 
                    log.info("------  END  --------  PinDigitalStateChange --------------");
                }
            });
            //------------------------------------------------------------------------------
            //------------    END  GpioPinDigitalStateChange     ---------------------------
            //------------------------------------------------------------------------------

             /* testing pin out blink
            int i = 1;
            while(i > 0) {
                outPin.setState(PinState.HIGH);
                log.info(outPin.getName() + " "+ outPin.getState() +
                        " " + inPin.getName() + " " + inPin.getState());
                Thread.sleep(5000);
                outPin.setState(PinState.LOW);
                log.info(outPin.getName() + " "+ outPin.getState() +
                        " " + inPin.getName() + " " + inPin.getState());
                Thread.sleep(5000);
               i--;
            }
            gpio.shutdown();
            */

            // keep program running 
            
            while(true) {
                 Thread.sleep(1000);
            }
            
        } catch (Exception e) {
            log.error("Something went wrong: ", e);             
        } finally {
            gpio.shutdown();
            log.info("pidemo END");
        }
    }

    public static void ToggleLed(GpioPinDigitalOutput outPin, long mills , int count){
        log.error("outPin: " + outPin.getName()+ " toggle mmills: "+ mills +
                        " count: " + count);
        try {
            for (int i=0; i <count; i++){
                outPin.toggle();
                Thread.sleep(mills);
            }
            outPin.low();        
        } catch (Exception e) {
            log.error("Error in toggle: " + outPin.getName()+ "  mills: "+ mills +
                            " count: " + count, e);
        }
    }
}
