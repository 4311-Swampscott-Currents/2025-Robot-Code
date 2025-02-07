package frc.robot;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.XboxController;

public class Lights extends RobotContainer {

    Spark blinkin = new Spark(0);
    XboxController controller = new XboxController(0);

    public void robotPeriodic() {
        if (controller.getAButton()) {
            blinkin.set(0.87);
        }
        else {
            blinkin.set(-0.99);
        }
        
    }

}
